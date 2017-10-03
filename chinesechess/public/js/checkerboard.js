var playerInfo;
var zr ;
var ImageShape;

//黑子
var black;
//红子
var red;

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
    require.config({
        packages:[
            {
                name:'zrender',
                location:'src',
                main:'zrender'
            }
        ]
    });
    require(
        [
            'js/lib/src/zrender',
            'js/lib/src/shape/Image'
        ],
        function(zrender,img){
            zr = zrender.init(document.getElementById('checkerboard_div'));
            ImageShape = img;
            init('red');
        }
    );
});

//绘制棋盘元素
function init(identity){
    black  = new Array();
    red  = new Array();

    //棋盘  74*72
    zr.addShape(new ImageShape({
        z:0,
        style:{
            x:0,y:0,
            image:identity=='black'?'img/board_black.png':'img/board_red.png',
            width:683,height:750
        },
        hoverable:false
    }));

    //黑-卒1
    black.push(new ImageShape({
        z:0,
        style:{
            x:16,y:453,
            image:'img/black/zu.png',
            width:60,height:60
        },
        ctype:'cu'
    }));
    //黑-卒2
    black.push(new ImageShape({
        z:0,
        style:{
            x:164,y:453,
            image:'img/black/zu.png',
            width:60,height:60
        },
        ctype:'cu'
    }));
    //黑-卒3
    black.push(new ImageShape({
        z:0,
        style:{
            x:312,y:453,
            image:'img/black/zu.png',
            width:60,height:60
        },
        ctype:'cu'
    }));
    //黑-卒4
    black.push(new ImageShape({
        z:0,
        style:{
            x:460,y:453,
            image:'img/black/zu.png',
            width:60,height:60
        },
        ctype:'cu'
    }));
    //黑-卒5
    black.push(new ImageShape({
        z:0,
        style:{
            x:608,y:453,
            image:'img/black/zu.png',
            width:60,height:60
        },
        ctype:'cu'
    }));
    //黑-炮1
    black.push(new ImageShape({
        z:0,
        style:{
            x:90,y:527,
            image:'img/black/pao.png',
            width:60,height:60
        },
        ctype:'pao'
    }));
    //黑-炮2
    black.push(new ImageShape({
        z:0,
        style:{
            x:534,y:527,
            image:'img/black/pao.png',
            width:60,height:60
        },
        ctype:'pao'
    }));
    //黑-车1
    black.push(new ImageShape({
        z:0,
        style:{
            x:16,y:675,
            image:'img/black/ju.png',
            width:60,height:60
        },
        ctype:'ju'
    }));
    //黑-车2
    black.push(new ImageShape({
        z:0,
        style:{
            x:608,y:675,
            image:'img/black/ju.png',
            width:60,height:60
        },
        ctype:'ju'
    }));
    //黑-马1
    black.push(new ImageShape({
        z:0,
        style:{
            x:90,y:675,
            image:'img/black/ma.png',
            width:60,height:60
        },
        ctype:'ma'
    }));
    //黑-马2
    black.push(new ImageShape({
        z:0,
        style:{
            x:534,y:675,
            image:'img/black/ma.png',
            width:60,height:60
        },
        ctype:'ma'
    }));
    //黑-象1
    black.push(new ImageShape({
        z:0,
        style:{
            x:164,y:675,
            image:'img/black/xiang.png',
            width:60,height:60
        },
        ctype:'xiang'
    }));
    //黑-象2
    black.push(new ImageShape({
        z:0,
        style:{
            x:460,y:675,
            image:'img/black/xiang.png',
            width:60,height:60
        },
        ctype:'xiang'
    }));
    //黑-士1
    black.push(new ImageShape({
        z:0,
        style:{
            x:238,y:675,
            image:'img/black/shi.png',
            width:60,height:60
        },
        ctype:'shi'
    }));
    //黑-士2
    black.push(new ImageShape({
        z:0,
        style:{
            x:386,y:675,
            image:'img/black/shi.png',
            width:60,height:60
        },
        ctype:'shi'
    }));
    //黑-将
    black.push(new ImageShape({
        z:0,
        style:{
            x:312,y:675,
            image:'img/black/jiang.png',
            width:60,height:60
        },
        ctype:'king'
    }));

    //红-兵1
    red.push(new ImageShape({
        z:0,
        style:{
            x:16,y:231,
            image:'img/red/bing.png',
            width:60,height:60
        },
        ctype:'bing'
    }));
    //红-兵2
    red.push(new ImageShape({
        z:0,
        style:{
            x:164,y:231,
            image:'img/red/bing.png',
            width:60,height:60
        },
        ctype:'bing'
    }));
    //红-兵3
    red.push(new ImageShape({
        z:0,
        style:{
            x:312,y:231,
            image:'img/red/bing.png',
            width:60,height:60
        },
        ctype:'bing'
    }));
    //红-兵4
    red.push(new ImageShape({
        z:0,
        style:{
            x:460,y:231,
            image:'img/red/bing.png',
            width:60,height:60
        },
        ctype:'bing'
    }));
    //红-兵5
    red.push(new ImageShape({
        z:0,
        style:{
            x:608,y:231,
            image:'img/red/bing.png',
            width:60,height:60
        },
        ctype:'bing'
    }));
    //红-炮1
    red.push(new ImageShape({
        z:0,
        style:{
            x:90,y:157,
            image:'img/red/pao.png',
            width:60,height:60
        },
        ctype:'pao'
    }));
    //红-炮2
    red.push(new ImageShape({
        z:0,
        style:{
            x:534,y:157,
            image:'img/red/pao.png',
            width:60,height:60
        },
        ctype:'pao'
    }));
    //红-车1
    red.push(new ImageShape({
        z:0,
        style:{
            x:16,y:9,
            image:'img/red/ju.png',
            width:60,height:60
        },
        ctype:'ju'
    }));
    //红-车2
    red.push(new ImageShape({
        z:0,
        style:{
            x:608,y:9,
            image:'img/red/ju.png',
            width:60,height:60
        },
        ctype:'ju'
    }));
    //红-马1
    red.push(new ImageShape({
        z:0,
        style:{
            x:90,y:9,
            image:'img/red/ma.png',
            width:60,height:60
        },
        ctype:'ma'
    }));
    //红-马2
    red.push(new ImageShape({
        z:0,
        style:{
            x:534,y:9,
            image:'img/red/ma.png',
            width:60,height:60
        },
        ctype:'ma'
    }));
    //红-相1
    red.push(new ImageShape({
        z:0,
        style:{
            x:164,y:9,
            image:'img/red/xiang.png',
            width:60,height:60
        },
        ctype:'xiang'
    }));
    //红-相2
    red.push(new ImageShape({
        z:0,
        style:{
            x:460,y:9,
            image:'img/red/xiang.png',
            width:60,height:60
        },
        ctype:'xiang'
    }));
    //红-仕1
    red.push(new ImageShape({
        z:0,
        style:{
            x:238,y:9,
            image:'img/red/shi.png',
            width:60,height:60
        },
        ctype:'shi'
    }));
    //红-仕2
    red.push(new ImageShape({
        z:0,
        style:{
            x:386,y:9,
            image:'img/red/shi.png',
            width:60,height:60
        },
        ctype:'shi'
    }));
    //红-帅
    red.push(new ImageShape({
        z:0,
        style:{
            x:312,y:9,
            image:'img/red/shuai.png',
            width:60,height:60
        },
        ctype:'king'
    }));

    //对换场地
    if(identity=='red'){
        for(var i=0;i<16;i++){
            var temp_x,temp_y;
            temp_x = black[i].style.x;
            temp_y = black[i].style.y;
            black[i].style.x = red[i].style.x;
            black[i].style.y = red[i].style.y;
            red[i].style.x = temp_x;
            red[i].style.y = temp_y;
        }
    }

    for(var i=0;i<16;i++){
        if(identity=='black'){
            black[i].clickable = true;
            black[i].onclick = function(c){
                clickAction(c);
            }
        }else{
            red[i].clickable = true;
            red[i].onclick = function(c){
                clickAction(c);
            }
        }
        zr.addShape(black[i]);
        zr.addShape(red[i]);
    }
}

function clickAction(c){
    console.log(c.target.ctype);
}

function action(socket){
    var roomno = $('head title').html().substr(0,$('head title').html().indexOf('号'));
    //向服务器发送初始化信息
    socket.emit('common',{type:'init',playerId:playerInfo.id,roomno:roomno});
}