$(function(){

    //加载大厅公告
    $.ajax({
        url:'/notice',
        dataType:'json',
        success:function(msg){
            if('system'==msg.type){
                $("#floor_notice").html('<font color="red">系统公告：'+msg.content+'</font>');
            }
            $('#bulletin').liMarquee({
                direction: 'left',
                drag:false
            });
        }
    });

    //加载房间列表
    $('#room_list').dataTable({
        //自动列宽
        'bAutoWidth': false,
        //加载数据时显示正在加载信息
        'bProcessing': true,
        //分页
        'paging': true,
        'bStateSave' : true,
        'sScrollY': '360.5px', //支持垂直滚动
        //从服务器处理分页
        'bServerSide': true,
        //查询请求action url
        'ajax': {
            'url': '/room_list',
            'type':'post',
            'dataSrc': 'data'
        },
        // 客户端传给服务器的查询参数为sSearch,服务端根据条件查出数据源即可
        //'bFilter':true ,
        //本地搜索
        'searching': false,
        //每页显示多少条数据
        'lengthChange':false,
        //每页显示数量：5条记录
        'iDisplayLength': 10,
        //加载完数据后的回调函数
        'fnInfoCallback':function(){
            $('#room_list tbody').on( 'click', 'tr', function () {
                if('暂时没有房间'==$(this).text()){
                    return;
                }
                $('#room_list tbody tr').removeClass('selected');
                $(this).addClass('selected');
            });
        },
        'columns': [
            {'data': 'no','width':'15%'},
            {'data': 'name','width':'40%'},
            {'data': 'owner','width':'30%'},
            {'data': 'state','width':'15%',render:function(data){
                return data?'<font color="red">对战中</font>':'<font color="green">等待中</font>';
            }},
            {'data': 'id',render:function(data){
                return '<input type="hidden" value="'+data+'"/>';
            }}
        ],

        //语言
        'language': {
            url: 'internation/message_zh_CN.txt'
        },
        //排序
        'sort':false,
        'aaSorting': []
    });

    //刷新房间按钮事件
    $('#btn_flush_room').on('click',function(){
        roomListReload();
    });
    //退出大厅按钮事件
    $('#btn_logout').on('click',function(){
        $('#modal_logout').modal('show');
    });
    //退出大厅模态框确定按钮事件
    $('#btn_modal_logout_enter').on('click',function(){
        $.ajax({
            url:'/logout',
            success:function(msg){
                if(msg.success)
                    window.location.href="/";
            }
        });
    });
    //创建房间按钮事件
    $('#btn_createroom').on('click',function(){
        $('#input_modal_createroom_name').val('');
        $('#modal_createroom').modal('show');
    });
    //创建房间模态框确定按钮事件
    $('#btn_modal_createroom_enter').on('click',function(){
        var name = $('#input_modal_createroom_name').val().trim();
        $.ajax({
            url:'/createroom',
            type:'post',
            data:{roomName:name},
            success:function(msg){
                if(msg.success){
                    roomListReload();
                    $('#input_modal_createroom_name').val('');
                    $('#modal_createroom').modal('hide');
                    //创建成功后直接进入游戏界面
                    window.location.href='/checkerboard';
                }else{
                    alert(msg.msg);
                }
            }
        });
    });
    //加入房间按钮事件
    $('#btn_joinroom').on('click',function(){
        var trs = $('#room_list tbody tr');
        var select;
        for(var i=0;i<trs.length;i++){
            if($(trs[i]).hasClass('selected')){
                select = $(trs[i]);
                break;
            }
        }
        if(select){
            var roomId = $(select.children()[4]).children()[0].value;
            $.ajax({
                url:'/joinroom',
                type:'post',
                data:{roomId:roomId},
                success:function(msg){
                    if(msg.success){
                        roomListReload();
                        //加入房间成功


                    }else{
                        alert(msg.msg);
                    }

                }
            });
        }
    });
});

//房间列表刷新
function roomListReload(){
    $('#room_list').DataTable().ajax.reload();
}