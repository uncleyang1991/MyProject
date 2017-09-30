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
    init();
});

//绘制棋盘元素
function init(){
    var canvas = new fabric.Canvas('checkerboard_canvas');
    canvas.hoverCursor = "pointer";


    var rect = new fabric.Rect({
        left: 100,
        top: 100,
        fill: 'red',
        width: 20,
        height: 20
    });
    canvas.add(rect);
    console.log(fabric);
   /* fabric.Image.fromURL('./img/board.png', function(img) {
        canvas.backgroundImage = img;
        canvas.backgroundImage.width = 550;
        canvas.backgroundImage.height = 550;
        canvas.add(img).renderAll();
    });*/


}

function action(socket){
    var roomno = $('head title').html().substr(0,$('head title').html().indexOf('号'));
    //向服务器发送初始化信息
    socket.emit('common',{type:'init',playerId:playerInfo.id,roomno:roomno});
}