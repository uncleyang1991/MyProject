var db = require('../lib/db.js');
//所有连接到服务器的用户对象
var players = new Array();
//房间里用户的连接映射表
var rooms = {};

exports.init = function(socket_server){
    socket_server.on('connection',function(socket){
        //用户连接


        //接收用户指令
        socket.on('common',function(common){
            commons(common,socket);
            //console.log(common);
        });
        //断开连接事件
        socket.on('disconnect',function(){
            for(var i=0;i<players.length;i++){
                if(players[i].socket===socket){
                    players.splice(i,1);
                    break;
                }
            }
        });
    });
};

//用户指令处理
function commons(common,socket){
    if('init'==common.type){
        //初始化房间
        var player = {id:common.playerId,socket:socket};
        players.push(player);
        if(!rooms['room'+common.roomno]){
            //如果不存在此房间,说明用户身份为创建房间者
            rooms['room'+common.roomno] = new Array();
        }
        rooms['room'+common.roomno].push(player);
    }


}