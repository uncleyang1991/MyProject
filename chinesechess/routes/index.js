var express = require('express');
var router = express.Router();
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
