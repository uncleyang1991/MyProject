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

/* GET home page. */
router.get('/', function(req, res, next) {
    res.render('login', { title: '' });
});


//登陆请求
router.post('/login',function(req,res){
    var params = req.body;
    var player;
    db_query(
        'select id,username,nickname from player where username=\''+params.username+'\' and password=\''+md5(params.password)+'\'',
        function(result){
            if(result.length==1){
                player = result[0];
                req.session.player = player;
                res.send({success:true,msg:player});
            }else{
                res.send({success:false,msg:"用户名或密码错误"});
            }
        }
    );
});

//跳转大厅
router.get('/floor',function(req,res){
    res.render('floor');
});
var service = require('../bin/server/service.js');

/* GET home page. */
router.get('/', function(req, res, next) {
    res.render('index', { title: '中国象棋' });
});

//登陆请求
router.get('/login',function(req,res){
    var params = req.query;
    service.login(
        params,
        function(result){

        }
    );
});
module.exports = router;
