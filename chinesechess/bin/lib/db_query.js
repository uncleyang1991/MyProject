var mysql = require('mysql');
var dbconfig = require('./config/dbconfig.js');

/**
 * 数据库查询
 * @type {Pool}
 */
var pool = mysql.createPool({
    host:dbconfig.host,
    user:dbconfig.user,
    password:dbconfig.password,
    database:dbconfig.database,
    port:dbconfig.port
});

/**
 * 数据库查询
 * @param sql
 * @param callback
 */
var db_query = function(sql,callback){
    pool.getConnection(function(err,connection){
        if(err){
            console.log("连接池异常:"+err.message);
        }else {
            connection.query(sql, function (qerr, rows, fields) {
                if (qerr) {
                    console.log("sql异常:"+qerr.message);
                } else {
                    callback(rows);
                }
            });
        }
    });
}
console.log('数据库配置完毕');

module.exports = db_query;
