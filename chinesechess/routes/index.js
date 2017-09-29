var express = require('express');
var router = express.Router();
var md5 = require('../bin/util/md5.js');
var idtools = require('../bin/util/idtools.js');
var db = require('../bin/lib/db.js');

//登录拦截
router.all('*',function(req,res,next){
    if(!req.session.player){
        if(req.url=='/'||req.url=='/login'){
            next();
        }else{
            res.render('login', { title: '' });
        }
    }else{
        next();
    }
});

//登录页面
router.all('/', function(req, res, next) {
    res.render('login', { title: '登录' });
});

//登陆请求
router.all('/login',function(req,res){
    var params = req.body;
    db.query(
        'select id,username,nickname from player where username=\''+params.username+'\' and password=\''+md5(params.password)+'\'',
        function(result){
            if(result.length==1){
                var player = result[0];
                req.session.player = player;
                res.send({success:true});
            }else{
                res.send({success:false,msg:"用户名或密码错误"});
            }
        }
    );
});

//跳转大厅
router.all('/floor',function(req,res){
    res.render('floor',{title:'大厅'});
});

//大厅公告
router.all('/notice',function(req,res){
    db.query(
        'select content,type from notice where state = 1',
        function(result){
            if(result.length==0){
                res.send({"content":"暂无公告","type":"system"});
            }else{
                res.send(result[0]);
            }
        }
    );
});

//房间列表
router.all('/room_list',function(req,res){
    var params = req.body;
    db.query(
        'select a.* from room a join (select id from room order by state limit '+params.start+','+params.length+') b on a.id=b.id order by no',
        function(result){
            db.query(
                'select count(1) as count from room;',
                function(count_result){
                    var count = count_result[0].count;
                    res.send({"data":result,"recordsTotal":count,"recordsFiltered":count});
                }
            );
        }
    );
});

//创建房间
router.all('/createroom',function(req,res){
    var params = req.body;
    //回调地狱的开端
    db.query(
        'select min(roomno) as minno from roomno where isuse=0',
        function (minno_result) {
            var minno = minno_result[0].minno;
            if(minno){
                db.query(
                    'update roomno set isuse=1 where roomno='+minno,
                    function(update_result){
                        if(update_result.affectedRows){
                            db.query(
                                'insert into room values (' +
                                '\''+idtools.uuid()+'\',' +
                                minno+','+
                                '\''+params.roomName+'\','+
                                '\''+req.session.player.nickname+'\','+
                                '0,now())',
                                function(create_result){
                                    if(create_result.affectedRows){
                                        res.send({success:true});
                                    }else{
                                        //创建房间失败,error
                                        res.send({success:false,msg:'错误,创建房间失败!'});
                                    }
                                }
                            );
                        }else{
                            //创建房间失败,error
                            res.send({success:false,msg:'错误,创建房间失败!'});
                        }
                    }
                );
            }else{
                //无法创建,房间列表已满
                res.send({success:false,msg:'目前房间过多,请稍后再创建'});
            }
        }
    );
});

//加入房间
router.all('/joinroom',function(req,res){
    var params = req.body;
    db.query(
        'select state from room where id=\''+params.roomId+'\'',
        function(state_result){
            if(state_result[0].state==0){
                db.query(
                    'update room set state=1 where id=\''+params.roomId+'\'',
                    function(update_result){
                        if(update_result.affectedRows){
                            res.send({success:true});
                        }else{
                            res.send({success:false,msg:'错误,加入房间失败!'});
                        }
                    }
                );
            }else{
                res.send({success:false,msg:'该房间正在对战中'});
            }
        }
    );
});

//退出大厅
router.all('/logout',function(req,res){
    req.session.player = null;
    res.send({success:true});
});

module.exports = router;
