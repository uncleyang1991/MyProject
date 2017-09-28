var md5 = require('../util/md5.js');
var db = require('../lib/db_query.js');

exports.login = function(params,callback){
    /*db_query(
        'select id,username,nickname from player where username=\''+params.username+'\' and password=\''+md5(params.password)+'\'',
        function(query_result,p){
            if(query_result.length==1){
                var player = query_result[0];
                result = {success:true,msg:player};
            }else{
                var result = {success:false,msg:'用户名或密码错误'};
            }
            callback(result,params.req,params.res);
        }
    );*/
    db.query('select * from player');
};
