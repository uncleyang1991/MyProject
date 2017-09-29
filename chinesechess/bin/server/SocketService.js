var db = require('../lib/db.js');
var socket_server = require('socket.io')(global.server);
//所有连接到服务器的用户对象
var players = {};
exports.init = function(){
    socket_server.on('connection',function(socket){
        console.log('新用户连接');

        //断开连接事件
        socket.on('disconnect',function(){
            console.log('用户断开');
        });
    });
}