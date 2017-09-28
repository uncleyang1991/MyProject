var mysql = require('mysql');
var dbconfig = require('./config/dbconfig.js');
var async = require('async');

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
exports.query = function(sql){
    var result = new Array(1);
    pool.getConnection(function(err,connection){
        if(err){
            console.log("连接池异常:"+err.message);
        }else {
            connection.query(sql, function (qerr, rows, fields) {
                if (qerr) {
                    console.log("sql异常:"+qerr.message);
                } else {
                   result[0] = rows;
                   console.log('li:'+result);
                }
            });
        }
    })
    console.log(result);
}
console.log('数据库配置完毕');
