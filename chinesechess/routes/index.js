var express = require('express');
var router = express.Router();
var md5 = require('../bin/util/md5.js');
var db_query = require('../bin/lib/db_query.js');

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
router.get('/', function(req, res, next) {
    res.render('login', { title: '登录' });
});

//登陆请求
router.post('/login',function(req,res){
    var params = req.body;
    db_query(
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
router.get('/floor',function(req,res){
    res.render('floor',{title:'大厅'});
});



module.exports = router;
