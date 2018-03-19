$(function(){

    //刷新表格数据
    loadAnimationTableData();

    //初始化搜索条件季番选择年份的日历插件
    $('#animation_input_search_item_season_year').datetimepicker({
        startView: 'decade',
        minView: 'decade',
        format: 'yyyy',
        maxViewMode: 2,
        minViewMode:2,
        autoclose: true,
        clearBtn: true,
        language:  'zh-CN'
    });

    //当模态框显示时
    $('#addAnimationModal').on('shown.bs.modal', function () {
        $('#addAnimationModal_input_name').focus();
    });
    //当模态框隐藏时
    $('#addAnimationModal').on('hidden.bs.modal', function () {
        $('#animation_form_add_animation')[0].reset();
        $('#addAnimationModal_input_watchState').removeAttr("disabled");
        $('#addAnimationModal_input_watchState').attr("placeholder","必填");
    });

    //按ESC隐藏模态框
    window.document.onkeydown = function(evt) {
        evt = (evt) ? evt : window.event;
        if (evt.keyCode) {
            if (evt.keyCode == 27) {
                $('#addAnimationModal,#updateAnimationModal').modal('hide');
            }
        }
    };


    //初始化剧集模态框中播出时间和观看时间输入框的日历插件
    $('#addAnimationModal_input_showTime,#addAnimationModal_input_watchTime,#updateAnimationModal_input_showTime,#updateAnimationModal_input_watchTime').datetimepicker({
        format: "yyyy-mm-dd",
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0,
        clearBtn: true
    });

    //添加模态框-同步按钮事件
    $('#addAnimationModal_button_sync').on('click',function(){
        var id = prompt('豆瓣ID：');
        if(id != null && id.trim() != ''){
            $(this).attr("disabled",true);
            $(this).text('正在同步...');
            $.ajax({
                url:'/record/animation/animationInfoPull.do',
                type:'post',
                dataType:'json',
                data:{'id':id.trim()},
                success:function(data){
                    $('#addAnimationModal_input_name').val(data.name);
                    $('#addAnimationModal_input_type').val(data.type);
                    $('#addAnimationModal_input_total').val(data.total);
                    $('#addAnimationModal_input_writer').val(data.writer);
                    if(data.dramaType){
                        $('#addAnimationModal_select_dramaType').val(data.dramaType);
                    }
                    $('#addAnimationModal_input_performers').val(data.performers);
                    if(data.showTime){
                        var showTime = new Date(data.showTime);
                        $('#addAnimationModal_input_showTime').val(showTime.getFullYear()+'-'+(showTime.getMonth()+1)+'-'+showTime.getDate());
                    }
                    $('#addAnimationModal_textarea_introduce').val(data.introduce);

                    $('#addAnimationModal_button_sync').text('同步');
                    $('#addAnimationModal_button_sync').removeAttr('disabled');
                    if(data.success != null&& !data.success){
                        alert('同步失败');
                    }
                },
                error:function(){
                    $('#addAnimationModal_button_sync').text('同步');
                    $('#addAnimationModal_button_sync').removeAttr('disabled');
                    alert('同步失败');
                }
            });
        }
    });

    //初始化添加剧集模态框中观看状态的选择事件
    $('#addAnimationModal_select_watchState').on('change',function(selected){
        if($(selected.target).val()=='正在追'){
            $('#addAnimationModal_input_watchState').removeAttr("disabled");
            $('#addAnimationModal_input_watchState').attr("placeholder","必填");
        }else{
            $('#addAnimationModal_input_watchState').val('');
            $('#addAnimationModal_input_watchState').attr("disabled","disabled");
            $('#addAnimationModal_input_watchState').removeAttr("placeholder");
        }
    });
    //初始化修改剧集模态框中观看状态的选择事件
    $('#updateAnimationModal_select_watchState').on('change',function(selected){
        if($(selected.target).val()=='正在追'){
            $('#updateAnimationModal_input_watchState').removeAttr("disabled");
            $('#updateAnimationModal_input_watchState').attr("placeholder","必填");
        }else{
            $('#updateAnimationModal_input_watchState').val('');
            $('#updateAnimationModal_input_watchState').attr("disabled","disabled");
            $('#updateAnimationModal_input_watchState').removeAttr("placeholder");
        }
    });

    //初始化添加剧集模态框中清除按钮事件
    $('#addAnimationModal_button_clear').on('click',function(){
        $('#animation_form_add_animation')[0].reset();
        $('#addAnimationModal_input_watchState').removeAttr("disabled");
        $('#addAnimationModal_input_watchState').attr("placeholder","必填");
    });

    //添加剧集模态框提交按钮事件
    $("#addAnimationModal_button_submit").on('click',function(){
        var obj = {};

        obj.name = $('#addAnimationModal_input_name').val().trim();
        obj.type = $('#addAnimationModal_input_type').val().trim();
        obj.total = $('#addAnimationModal_input_total').val().trim();
        obj.writer = $('#addAnimationModal_input_writer').val().trim();
        obj.dramaType = $('#addAnimationModal_select_dramaType').val();
        obj.level = $('#addAnimationModal_select_level').val();
        obj.performers = $('#addAnimationModal_input_performers').val().trim();
        obj.showTime = $('#addAnimationModal_input_showTime').val();
        obj.watchTime = $('#addAnimationModal_input_watchTime').val();
        obj.state = $('#addAnimationModal_select_state').val();
        if($('#addAnimationModal_select_watchState').val()=='正在追'){
            obj.watchState = '该看第'+$('#addAnimationModal_input_watchState').val().trim()+'集了';
        }else{
            obj.watchState = '已看完'
        }
        obj.introduce = $('#addAnimationModal_textarea_introduce').val().trim();

        $.ajax({
            url:'/record/animation/addAnimation.do',
            data:obj,
            type:'post',
            dataType:'json',
            success:function(result){
                if(result.success){
                    $('#animation_table').DataTable().ajax.reload();
                    $('#addAnimationModal').modal('hide');
                    $('#animation_form_add_animation')[0].reset();
                }else{
                    alert(result.msg);
                }
            }
        });
    });

    //剧集-清除条件按钮事件
    $('#animation_button_clear_item').on('click',function(){
        $('#animation_form_search_item')[0].reset();
    });

    //剧集-条件搜索按钮事件
    $('#animation_button_search').on('click',function(){
        loadAnimationTableData(true);
    });

    //单击条目事件
    $('#animation_table').on('click','tr',function(){
        var id = $(this).children().children('input[type=hidden]').val();
        if(id){
            $.ajax({
                url:'/record/animation/getAnimationInfo.do',
                type:'post',
                dataType:'json',
                data:{'id':id},
                success:function(data){
                    if(data.success&&!data.success){
                        alert(data.msg);
                    }
                    var animation = data;
                    $('#updateAnimationModal_input_id').val(id);
                    $('#updateAnimationModal_input_name').val(animation.name);
                    $('#updateAnimationModal_input_type').val(animation.type);
                    $('#updateAnimationModal_input_total').val(animation.total);
                    $('#updateAnimationModal_input_writer').val(animation.writer);
                    $('#updateAnimationModal_input_performers').val(animation.performers);
                    var showTime = new Date(animation.showTime);
                    $('#updateAnimationModal_input_showTime').val(showTime.getFullYear()+'-'+(showTime.getMonth()+1)+'-'+showTime.getDate());
                    var watchTime = new Date(animation.watchTime);
                    $('#updateAnimationModal_input_watchTime').val(watchTime.getFullYear()+'-'+(watchTime.getMonth()+1)+'-'+watchTime.getDate());

                    if(animation.watchState === '已看完'){
                        $('#updateAnimationModal_input_watchState').val('');
                        $('#updateAnimationModal_input_watchState').attr("disabled","disabled");
                        $('#updateAnimationModal_input_watchState').removeAttr("placeholder");
                        $('#updateAnimationModal_select_watchState').val(animation.watchState);
                    }else{
                        $('#updateAnimationModal_input_watchState').removeAttr("disabled");
                        $('#updateAnimationModal_input_watchState').attr("placeholder","必填");
                        $('#updateAnimationModal_select_watchState').val('正在追');
                        $('#updateAnimationModal_input_watchState').val(animation.watchState.substring(3,animation.watchState.indexOf('集了')));
                    }
                    $('#updateAnimationModal_textarea_introduce').val(animation.introduce);
                    $('#updateAnimationModal_select_dramaType').val(animation.dramaType);
                    $('#updateAnimationModal_select_level').val(animation.level);
                    $('#updateAnimationModal_select_state').val(animation.isEnd);
                    $('#updateAnimationModal').modal('show');
                }
            });
        }
    });

    //剧集-信息更新按钮
    $('#updateAnimationModal_button_submit').on('click',function(){
        var obj = {};

        obj.id = $('#updateAnimationModal_input_id').val();
        obj.name = $('#updateAnimationModal_input_name').val().trim();
        obj.type = $('#updateAnimationModal_input_type').val().trim();
        obj.total = $('#updateAnimationModal_input_total').val().trim();
        obj.writer = $('#updateAnimationModal_input_writer').val().trim();
        obj.dramaType = $('#updateAnimationModal_select_dramaType').val();
        obj.level = $('#updateAnimationModal_select_level').val();
        obj.performers = $('#updateAnimationModal_input_performers').val().trim();
        obj.showTime = $('#updateAnimationModal_input_showTime').val();
        obj.watchTime = $('#updateAnimationModal_input_watchTime').val();
        obj.state = $('#updateAnimationModal_select_state').val();
        if($('#updateAnimationModal_select_watchState').val()=='正在追'){
            obj.watchState = '该看第'+$('#updateAnimationModal_input_watchState').val().trim()+'集了';
        }else{
            obj.watchState = '已看完'
        }
        obj.introduce = $('#updateAnimationModal_textarea_introduce').val().trim();
        $.ajax({
            url:'/record/animation/updateAnimation.do',
            data:obj,
            type:'post',
            dataType:'json',
            success:function(result){
                if(result.success){
                    $('#animation_table').DataTable().draw(false);
                    $('#updateAnimationModal').modal('hide');
                }else{
                    alert(result.msg);
                }
            }
        });
    });
});

function loadAnimationTableData(isSearch){
    if($.fn.dataTable.isDataTable('#animation_table')){
        $('#animation_table').DataTable().destroy();
    }
    $('#animation_table').dataTable({
        //自动列宽
        'bAutoWidth': false,
        //加载数据时显示正在加载信息
        'bProcessing': true,
        //分页
        'paging': true,
        'bStateSave' : false,
        'sScrollY': '715px', //支持垂直滚动
        //从服务器处理分页
        'bServerSide': true,
        //查询请求action url
        'ajax': {
            'url': '/record/animation/animationList.do',
            'type':'post',
            'dataSrc': 'data',
            'data':function(d){
                if(isSearch){
                    d.name = '%'+$('#animation_input_search_item_name').val().trim()+'%';
                    d.type = '%'+$('#animation_input_search_item_type').val().trim()+'%';
                    d.dramaType = $('#animation_input_search_item_dramaType').val();
                    d.performers = '%'+$('#animation_input_search_item_performers').val().trim()+'%';
                    d.state = $('#animation_input_search_item_state').val();
                    d.seasonYear = $('#animation_input_search_item_season_year').val();
                    d.seasonSeason = $('#animation_input_search_item_season_season').val();
                    d.watchState = $('#animation_input_search_item_watchState').val();
                }
            }
        },
        // 客户端传给服务器的查询参数为sSearch,服务端根据条件查出数据源即可
        //'bFilter':true ,
        //本地搜索
        'searching': false,
        //每页显示多少条数据
        'lengthChange':false,
        //每页显示数量：14条记录
        'iDisplayLength': 14,
        'bInfo' : true, //是否显示页脚信息，DataTables插件左下角显示记录数
        'sPaginationType': 'full_numbers', //详细分页组，可以支持直接跳转到某页
        'columns': [
            {'data': 'name','width':'14%',render:function(data){
                return '&nbsp;&nbsp;'+data;
            }},
            {'data': 'watchState','width':'8%',render:function(data){
                var html;
                if('已看完' === data){
                    html = '<font color="red">'+data+'</font>';
                }else{
                    html = '<font color="green">'+data+'</font>';
                }
                return '&nbsp;&nbsp;'+html;
            }},
            {'data': 'type','width':'9%',render:function(data){
                return '&nbsp;&nbsp;'+data;
            }},
            {'data': 'dramaType','width':'5%',render:function(data){
                return '&nbsp;&nbsp;'+data;
            }},
            {'data': 'performers','width':'25%',render:function(data){
                if(data.length>32){
                    data = data.substr(0,32)+'...';
                }
                return '&nbsp;&nbsp;'+data;
            }},
            {'data': 'total','width':'7%',render:function(data){
                if(data === 0){
                    return '&nbsp;&nbsp;未知';
                }
                return  '&nbsp;&nbsp;'+data+' 集';
            }},
            {'data': 'isEnd','width':'6%',render:function(data){
                var html;
                if('完结' === data){
                    html = '<font color="red">'+data+'</font>';
                }else{
                    html = '<font color="green">'+data+'</font>';
                }
                return '&nbsp;&nbsp;'+html;
            }},
            {'data': 'showTime','width':'9%',render:function(data){
                var date = new Date(data);
                return '&nbsp;&nbsp;'+date.getFullYear()+'年'+(date.getMonth()+1)+'月'+date.getDate()+'日';
            }},
            {'data': 'watchTime','width':'9%',render:function(data){
                var date = new Date(data);
                return '&nbsp;&nbsp;'+date.getFullYear()+'年'+(date.getMonth()+1)+'月'+date.getDate()+'日';
            }},
            {'data': 'level','width':'10%',render:function(data){
                var html = '';
                for(var i=0;i<data;i++){
                    html+='<img src="img/star.png">';
                }
                return '&nbsp;'+html;
            }},
            {'data': 'id','width':'0%',render:function(data){
                return '<input type="hidden" value='+data+'>';
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
}