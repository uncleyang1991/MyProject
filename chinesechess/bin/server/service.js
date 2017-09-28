var md5 = require('../util/md5.js');
var db_query = require('../lib/db_query.js');

var service =  {
    login:function(params,callback){
        var player;
        var result = {success:false,msg:'用户名或密码错误'};
        db_query(
            'select id,username,nickname from player where username=\''+params.username+'\' and password=\''+md5(params.password)+'\'',
            function(query_result){
                if(query_result.length==1){
                    player = query_result[0];
                    result = {success:true};
                }
                callback(result);
            }
        );
    }
}

module.exports = service;