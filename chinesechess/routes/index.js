var express = require('express');
var router = express.Router();
var service = require('../bin/server/service.js');

/* GET home page. */
router.get('/', function(req, res, next) {

    res.render('index', { title: '' });
});

//登陆请求
router.post('/login',function(req,res){
    var params = req.body;
    params.req = req;
    params.res = res;
    service.login(
        params,
        function(result,q,s){
            s.send(result);
        }
    );
});
module.exports = router;
