var playerInfo;
$(function(){
    /*
    //获取用户信息
    $.ajax({
        url:'/playerInfo',
        success:function(msg){
            if(msg.success){
                playerInfo = msg.msg;
                //连接socket服务器
                var socket = io();
                action(socket);
            }else{
                alert('信息拉取出错!');
                window.location.href='/';
            }
        }
    });
    */
});

function action(socket){
    var roomno = $('head title').html().substr(0,$('head title').html().indexOf('号'));
    //向服务器发送初始化信息
    socket.emit('common',{type:'init',playerId:playerInfo.id,roomno:roomno});
}