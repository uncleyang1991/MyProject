var mysql = require('mysql');
var dbconfig = require('./config/dbconfig.js');

var pool = mysql.createPool({
    host:dbconfig.host,
    user:dbconfig.user,
    password:dbconfig.password,
    database:dbconfig.database,
    port:dbconfig.port
});

var db_pool = function(sql,callback){
    pool.getConnection(function(err,connection){
        if(err){
            console.log("连接池异常:"+err.message);
        }else {
            connection.query(sql, function (qerr, rows, fields) {
                if (qerr) {
                    console.log("sql异常:"+qerr.message);
                } else {
                    callback(rows,fields);
                }
            });
        }
    });
}
console.log('数据库配置完毕');

module.exports = db_pool;
