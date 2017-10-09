var playerInfo;
var zr ;
var Line;
var Text;
var ImageShape;

//选中的棋子
var selected;
//显示的标记
var sign;
//黑子
var black;
//红子
var red;
//是否为我方回合
var myTurn = true;
//我方身份
var myIdentity = 'black';
//对方信息
var enemyInfo = {};
//我方信息
var weInfo = {};

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
            'js/lib/src/shape/Line',
            'js/lib/src/shape/Text',
            'js/lib/src/shape/Image'
        ],
        function(zrender,line,text,img){
            zr = zrender.init(document.getElementById('checkerboard_div'));
            Line = line;
            Text = text;
            ImageShape = img;
            init(myIdentity);
        }
    );
});

function init(identity){
    init_board(identity);
    init_checker(identity);
    init_info();
}

//线条元素
function line(xStart,yStart,xEnd,yEnd){
    return new Line({
        style:{
            xStart: xStart,
            yStart: yStart,
            xEnd: xEnd,
            yEnd: yEnd,
            strokeColor: '#000',
            lineWidth: 2
        },
        hoverable:false
    });
}

//绘制棋盘元素
function init_board(identity){
    //棋盘背景
    zr.addShape(new ImageShape({
        z:0,
        style:{
            x:0,y:0,
            image:'img/board_background.png',
            width:705,height:780
        },
        hoverable:false,
        clickable:true,
        onclick:function(e){
            clickAction(e);
        }
    }));

    for(var i=0;i<10;i++){
        zr.addShape(line(50,50+i*75,50+8*75,50+i*75));
    }
    zr.addShape(line(50,50,50,50+9*75));
    zr.addShape(line(50+8*75,50,50+8*75,50+9*75));
    for(var i=0;i<7;i++){
        zr.addShape(line(50+(i+1)*75,50,50+(i+1)*75,50+4*75));
        zr.addShape(line(50+(i+1)*75,50+5*75,50+(i+1)*75,50+9*75));
    }
    zr.addShape(line(50+3*75,50,50+5*75,50+2*75));
    zr.addShape(line(50+3*75,50+2*75,50+5*75,50));
    zr.addShape(line(50+3*75,50+7*75,50+5*75,50+9*75));
    zr.addShape(line(50+3*75,50+9*75,50+5*75,50+7*75));

    var sign = function(x,y){
        if(x!=50){
            zr.addShape(line(x-3,y-3,x-3,y-13));
            zr.addShape(line(x-3,y-3,x-13,y-3));
            zr.addShape(line(x-3,y+3,x-13,y+3));
            zr.addShape(line(x-3,y+3,x-3,y+13));
        }
        if(x!=50+8*75){
            zr.addShape(line(x+3,y-3,x+13,y-3));
            zr.addShape(line(x+3,y-3,x+3,y-13));
            zr.addShape(line(x+3,y+3,x+13,y+3));
            zr.addShape(line(x+3,y+3,x+3,y+13));
        }
    };
    sign(50+75,50+2*75);
    sign(50+7*75,50+2*75);
    for(var i=0;i<5;i++){
        sign(50+2*i*75,50+3*75);
    }
    sign(50+75,50+7*75);
    sign(50+7*75,50+7*75);
    for(var i=0;i<5;i++){
        sign(50+2*i*75,50+6*75);
    }
    zr.addShape(new Text({
        style: {
            text: identity=='black'?'楚河':'汉界',
            x: 180,
            y: 85+4*75,
            textFont: 'bold 45px 楷书'
        },
        hoverable:false
    }));
    zr.addShape(new Text({
        style: {
            text: identity=='red'?'楚河':'汉界',
            x: 705-280,
            y: 85+4*75,
            textFont: 'bold 45px 楷书'
        },
        rotation:[Math.PI,705-230,88+4*75],
        hoverable:false
    }));
}

//绘制棋子元素
function init_checker(identity){
    black  = new Array();
    red  = new Array();

    var checker = function(x,y,identity,ctype){
        var img = new ImageShape({
            z:1,
            ctype:ctype,
            hoverable:false,
            cx:x,
            cy:y,
            style:{
                x:x-35,
                y:y-35,
                width:70,
                height:70,
                image:'img/'+identity+'/'+ctype+'.png'
            }
        });
        return img;
    };
    black.push(checker(50,50+6*75,'black','bing'));
    black.push(checker(50+2*75,50+6*75,'black','bing'));
    black.push(checker(50+4*75,50+6*75,'black','bing'));
    black.push(checker(50+6*75,50+6*75,'black','bing'));
    black.push(checker(50+8*75,50+6*75,'black','bing'));
    black.push(checker(50+75,50+7*75,'black','pao'));
    black.push(checker(50+7*75,50+7*75,'black','pao'));
    black.push(checker(50,50+9*75,'black','ju'));
    black.push(checker(50+8*75,50+9*75,'black','ju'));
    black.push(checker(50+75,50+9*75,'black','ma'));
    black.push(checker(50+7*75,50+9*75,'black','ma'));
    black.push(checker(50+2*75,50+9*75,'black','xiang'));
    black.push(checker(50+6*75,50+9*75,'black','xiang'));
    black.push(checker(50+3*75,50+9*75,'black','shi'));
    black.push(checker(50+5*75,50+9*75,'black','shi'));
    black.push(checker(50+4*75,50+9*75,'black','king'));

    red.push(checker(50,50+3*75,'red','bing'));
    red.push(checker(50+2*75,50+3*75,'red','bing'));
    red.push(checker(50+4*75,50+3*75,'red','bing'));
    red.push(checker(50+6*75,50+3*75,'red','bing'));
    red.push(checker(50+8*75,50+3*75,'red','bing'));
    red.push(checker(50+75,50+2*75,'red','pao'));
    red.push(checker(50+7*75,50+2*75,'red','pao'));
    red.push(checker(50,50,'red','ju'));
    red.push(checker(50+8*75,50,'red','ju'));
    red.push(checker(50+75,50,'red','ma'));
    red.push(checker(50+7*75,50,'red','ma'));
    red.push(checker(50+2*75,50,'red','xiang'));
    red.push(checker(50+6*75,50,'red','xiang'));
    red.push(checker(50+3*75,50,'red','shi'));
    red.push(checker(50+5*75,50,'red','shi'));
    red.push(checker(50+4*75,50,'red','king'));

    if(identity=='red'){
        for(var i=0;i<16;i++){
            var temp_cx,temp_cy,temp_x,temp_y;
            temp_cx = black[i].cx;
            temp_cy = black[i].cy;
            temp_x = black[i].style.x;
            temp_y = black[i].style.y;
            black[i].cx = red[i].cx;
            black[i].cy = red[i].cy;
            black[i].style.x = red[i].style.x;
            black[i].style.y = red[i].style.y;
            red[i].cx = temp_cx;
            red[i].cy = temp_cy;
            red[i].style.x = temp_x;
            red[i].style.y = temp_y;
        }
    }

    for(var i=0;i<16;i++){
        zr.addShape(black[i]);
        zr.addShape(red[i]);
    }
}

//绘制右侧信息区域
function init_info(){
    zr.addShape(new ImageShape({
        z:0,
        style:{
            x:725,y:0,
            image:'img/info.png',
            width:350,height:250
        },
        hoverable:false
    }));

    zr.addShape(new ImageShape({
        z:0,
        style:{
            x:725,y:275,
            image:'img/info.png',
            width:350,height:230
        },
        hoverable:false
    }));

    zr.addShape(new ImageShape({
        z:0,
        style:{
            x:725,y:780-250,
            image:'img/info.png',
            width:350,height:250
        },
        hoverable:false
    }));

}

//遍历棋盘上90个点的坐标,用模糊算法来匹配点击位置
function vague(x,y){
    var result = {x:-100,y:-100};
    for(var i=0;i<10;i++){
        for(var j=0;j<9;j++){
            if(x<=50+j*75+75/2&&x>=50+j*75-75/2&&y<=50+i*75+75/2&&y>=50+i*75-75/2){
                result = {x:50+j*75,y:50+i*75};
                break;
            }
        }
    }
    return result;
}

//棋子移动
function move(x,y){
    selected.cx = x;
    selected.cy = y;
    selected.style.x = x-35;
    selected.style.y = y-35;
    zr.render();

    var arr = myIdentity=='black'?red:black;
    for(var i=0;i<arr.length;i++){
        if(arr[i].cx==x&&arr[i].cy==y){
            arr[i].ignore = true;
            zr.render();
            arr.splice(i,1);
            break;
        }
    }
    selected = null;
    checker_sign(-100,-100,false);
}

//棋子移动逻辑
function logic(x,y){
    //所有棋子
    var all;
    //我方和敌方棋子的集合
    var we,enemy;
    if('black'==myIdentity){
        we = black;
        enemy = red;
    }else{
        we = red;
        enemy = black;
    }
    all = we.concat(enemy);

    if('bing'==selected.ctype){
        //兵
        //只能直线移动
        if(!(x==selected.cx||y==selected.cy)){
            return;
        }
        //每次只能移动一格,利用直角坐标系中的两点距离公式计算
        if(Math.abs(Math.sqrt(Math.pow(x-selected.cx,2)+Math.pow(y-selected.cy,2)))>75){
            return;
        }
        //兵不能后退
        if(y<=selected.cy){
            //在过河前不能左右移动
            if(y>=50+5*75&&x==selected.cx){
                move(x,y);
            }
            //过河后可以左右移动
            if(y<=50+4*75){
                move(x,y);
            }
        }
    }else if('pao'==selected.ctype){
        //炮
        var xcount = 0,ycount = 0;
        //只能直线移动
        if(!(x==selected.cx||y==selected.cy)){
            return;
        }
        //判断是否为移动还是攻击
        var isMove = true;
        for(var i=0;i<enemy.length;i++){
            if(enemy[i].cx==x&&enemy[i].cy==y){
                isMove = false;
                break;
            }
        }
        //判断与目标点之间隔了多少个棋子
        for(var i=0;i<all.length;i++){
            if(all[i].cy == y){
                if(x<selected.cx?(all[i].cx>x&&all[i].cx<selected.cx):(all[i].cx>selected.cx&&all[i].cx<x)){
                    xcount++;
                }
            }
            if(all[i].cx == x){
                if(y<selected.cy?(all[i].cy>y&&all[i].cy<selected.cy):(all[i].cy>selected.cy&&all[i].cy<y)){
                    ycount++;
                }
            }
        }
        if(isMove){
            //如果为移动行为
            if(xcount==0&&ycount==0){
                move(x,y);
            }
        }else{
            //如果为攻击行为
            if(xcount==1||ycount==1){
                move(x,y);
            }
        }
    }else if('ju'==selected.ctype){
        //车
        var xcount = 0,ycount = 0;
        //只能直线移动
        if(!(x==selected.cx||y==selected.cy)){
            return;
        }
        //判断与目标点之间隔了多少个棋子
        for(var i=0;i<all.length;i++){
            if(all[i].cy == y){
                if(x<selected.cx?(all[i].cx>x&&all[i].cx<selected.cx):(all[i].cx>selected.cx&&all[i].cx<x)){
                    xcount++;
                }
            }
            if(all[i].cx == x){
                if(y<selected.cy?(all[i].cy>y&&all[i].cy<selected.cy):(all[i].cy>selected.cy&&all[i].cy<y)){
                    ycount++;
                }
            }
        }
        if(xcount==0&&ycount==0){
            move(x,y);
        }
    }else if('ma'==selected.ctype){
        //马
        if(Math.abs(Math.sqrt(Math.pow(x-selected.cx,2)+Math.pow(y-selected.cy,2)))==Math.sqrt(Math.pow(75,2)+Math.pow(75*2,2))){
            var count = 0;
            var direction;
            //判断是否襒马腿
            if(selected.cx-x==75*2){
                //向左移动
                direction = 'left';
            }else if(x-selected.cx==75*2){
                //向右移动
                direction = 'right';
            }else if(selected.cy-y==75*2){
                //向上移动
                direction = 'up';
            }else if(y-selected.cy==75*2){
                //向下移动
                direction = 'down';
            }
            for(var i=0;i<all.length;i++){
                if(direction=='left'&&all[i].cx==selected.cx-75&&all[i].cy==selected.cy){
                    count++;
                    break;
                }else if(direction=='right'&&all[i].cx==selected.cx+75&&all[i].cy==selected.cy){
                    count++;
                    break;
                }else if(direction=='up'&&all[i].cx==selected.cx&&all[i].cy==selected.cy-75){
                    count++;
                    break;
                }else if(direction=='down'&&all[i].cx==selected.cx&&all[i].cy==selected.cy+75){
                    count++;
                    break;
                }
            }
            if(count<1){
                move(x,y);
            }
        }
    }else if('xiang'==selected.ctype){
        //象
        //象不能过河
        if(y<=50+4*75){
            return;
        }
        if(Math.abs(Math.sqrt(Math.pow(x-selected.cx,2)+Math.pow(y-selected.cy,2)))==Math.sqrt(Math.pow(75*2,2)+Math.pow(75*2,2))){
            var count = 0;
            var direction;
            //判断是否撇象眼
            if(selected.cx-75*2==x&&selected.cy-75*2==y){
                //向左上移动
                direction = 'left_up';
            }else if(selected.cx+75*2==x&&selected.cy-75*2==y){
                //向右上移动
                direction = 'right_up';
            }else if(selected.cx-75*2==x&&selected.cy+75*2==y){
                //向左下移动
                direction = 'left_down';
            }else if(selected.cx+75*2==x&&selected.cy+75*2==y){
                //向右下移动
                direction = 'right_down';
            }
            for(var i=0;i<all.length;i++){
                if(direction=='left_up'&&all[i].cx==selected.cx-75&&all[i].cy==selected.cy-75){
                    count++;
                    break;
                }else if(direction=='right-up'&&all[i].cx==selected.cx+75&&all[i].cy==selected.cy-75){
                    count++;
                    break;
                }else if(direction=='left_down'&&all[i].cx==selected.cx-75&&all[i].cy==selected.cy+75){
                    count++;
                    break;
                }else if(direction=='right_down'&&all[i].cx==selected.cx+75&&all[i].cy==selected.cy+75){
                    count++;
                    break;
                }
            }
            if(count<1){
                move(x,y);
            }
        }
    }else if('shi'==selected.ctype){
        //士
        if(x>=50+3*75&&x<=50+5*75&&y>=50+7*75&&y<=50+9*75){
            if(Math.abs(Math.sqrt(Math.pow(x-selected.cx,2)+Math.pow(y-selected.cy,2)))==Math.sqrt(Math.pow(75,2)+Math.pow(75,2))){
                move(x,y);
            }
        }
    }
    else if('king'==selected.ctype){
        //将
        if(x>=50+3*75&&x<=50+5*75&&y>=50+7*75&&y<=50+9*75){
            if(Math.abs(Math.sqrt(Math.pow(x-selected.cx,2)+Math.pow(y-selected.cy,2)))==75){
                move(x,y);
            }
        }
    }
}

//点击棋子事件
function clickAction(e){
    if(!myTurn){
        return;
    }
    var result = vague(e.event.layerX,e.event.layerY);


    var arr = myIdentity=='black'?black:red;
    for(var i=0;i<arr.length;i++){
        if(arr[i].cx==result.x&&arr[i].cy==result.y){
            selected = arr[i];
            checker_sign(result.x,result.y,true);
            return;
        }
    }

    if(selected){
        logic(result.x,result.y);
    }
}

//点击时标记棋子的边框
function checker_sign(x,y,show){
    if(!sign){
        sign = new ImageShape({
            z:2,
            style:{
                x:x-35,
                y:y-35,
                image:'img/sign.png',
                width:70,
                height:70
            },
            hoverable:false,
            ignore:!show
        });
        zr.addShape(sign);
    }else{
        sign.style.x = x-35;
        sign.style.y = y-35;
        zr.render();
    }
}

function action(socket){
    var roomno = $('head title').html().substr(0,$('head title').html().indexOf('号'));
    //向服务器发送初始化信息
    socket.emit('common',{type:'init',playerId:playerInfo.id,roomno:roomno});
}