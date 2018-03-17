$(function(){

    //刷新表格数据
    loadEpisodeTableData();

    //初始化搜索条件季番选择年份的日历插件
    $('#episode_input_search_item_season_year').datetimepicker({
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
    $('#addEpisodeModal').on('shown.bs.modal', function () {
        $('#addEpisodeModal_input_name').focus();
    });
    //当模态框隐藏时
    $('#addEpisodeModal').on('hidden.bs.modal', function () {
        $('#episode_form_add_episode')[0].reset();
        $('#addEpisodeModal_input_watchState').removeAttr("disabled");
        $('#addEpisodeModal_input_watchState').attr("placeholder","必填");
    });

    //按ESC隐藏模态框
    window.document.onkeydown = function(evt) {
        evt = (evt) ? evt : window.event;
        if (evt.keyCode) {
            if (evt.keyCode == 27) {
                $('#addEpisodeModal,#updateEpisodeModal').modal('hide');
            }
        }
    };


    //初始化剧集模态框中播出时间和观看时间输入框的日历插件
    $('#addEpisodeModal_input_showTime,#addEpisodeModal_input_watchTime,#updateEpisodeModal_input_showTime,#updateEpisodeModal_input_watchTime').datetimepicker({
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
    $('#addEpisodeModal_button_sync').on('click',function(){
        var id = prompt('豆瓣ID：');
        if(id != null && id.trim() != ''){
            $(this).attr("disabled",true);
            $(this).text('正在同步...');
            $.ajax({
                url:'/record/episode/episodeInfoPull.do',
                type:'post',
                dataType:'json',
                data:{'id':id.trim()},
                success:function(data){
                    $('#addEpisodeModal_input_name').val(data.name);
                    $('#addEpisodeModal_input_type').val(data.type);
                    $('#addEpisodeModal_input_total').val(data.total);
                    $('#addEpisodeModal_input_writer').val(data.writer);
                    if(data.dramaType){
                        $('#addEpisodeModal_select_dramaType').val(data.dramaType);
                    }
                    $('#addEpisodeModal_input_performers').val(data.performers);
                    if(data.showTime){
                        var showTime = new Date(data.showTime);
                        $('#addEpisodeModal_input_showTime').val(showTime.getFullYear()+'-'+(showTime.getMonth()+1)+'-'+showTime.getDate());
                    }
                    $('#addEpisodeModal_textarea_introduce').val(data.introduce);

                    $('#addEpisodeModal_button_sync').text('同步');
                    $('#addEpisodeModal_button_sync').removeAttr('disabled');
                    if(data.success != null&& !data.success){
                        alert('同步失败');
                    }
                },
                error:function(){
                    $('#addEpisodeModal_button_sync').text('同步');
                    $('#addEpisodeModal_button_sync').removeAttr('disabled');
                    alert('同步失败');
                }
            });
        }
    });

    //初始化添加剧集模态框中观看状态的选择事件
    $('#addEpisodeModal_select_watchState').on('change',function(selected){
        if($(selected.target).val()=='正在追'){
            $('#addEpisodeModal_input_watchState').removeAttr("disabled");
            $('#addEpisodeModal_input_watchState').attr("placeholder","必填");
        }else{
            $('#addEpisodeModal_input_watchState').val('');
            $('#addEpisodeModal_input_watchState').attr("disabled","disabled");
            $('#addEpisodeModal_input_watchState').removeAttr("placeholder");
        }
    });
    //初始化修改剧集模态框中观看状态的选择事件
    $('#updateEpisodeModal_select_watchState').on('change',function(selected){
        if($(selected.target).val()=='正在追'){
            $('#updateEpisodeModal_input_watchState').removeAttr("disabled");
            $('#updateEpisodeModal_input_watchState').attr("placeholder","必填");
        }else{
            $('#updateEpisodeModal_input_watchState').val('');
            $('#updateEpisodeModal_input_watchState').attr("disabled","disabled");
            $('#updateEpisodeModal_input_watchState').removeAttr("placeholder");
        }
    });

    //初始化添加剧集模态框中清除按钮事件
    $('#addEpisodeModal_button_clear').on('click',function(){
        $('#episode_form_add_episode')[0].reset();
        $('#addEpisodeModal_input_watchState').removeAttr("disabled");
        $('#addEpisodeModal_input_watchState').attr("placeholder","必填");
    });

    //添加剧集模态框提交按钮事件
    $("#addEpisodeModal_button_submit").on('click',function(){
        var obj = {};

        obj.name = $('#addEpisodeModal_input_name').val().trim();
        obj.type = $('#addEpisodeModal_input_type').val().trim();
        obj.total = $('#addEpisodeModal_input_total').val().trim();
        obj.writer = $('#addEpisodeModal_input_writer').val().trim();
        obj.dramaType = $('#addEpisodeModal_select_dramaType').val();
        obj.level = $('#addEpisodeModal_select_level').val();
        obj.performers = $('#addEpisodeModal_input_performers').val().trim();
        obj.showTime = $('#addEpisodeModal_input_showTime').val();
        obj.watchTime = $('#addEpisodeModal_input_watchTime').val();
        obj.state = $('#addEpisodeModal_select_state').val();
        if($('#addEpisodeModal_select_watchState').val()=='正在追'){
            obj.watchState = '该看第'+$('#addEpisodeModal_input_watchState').val().trim()+'集了';
        }else{
            obj.watchState = '已看完'
        }
        obj.introduce = $('#addEpisodeModal_textarea_introduce').val().trim();

        $.ajax({
            url:'/record/episode/addEpisode.do',
            data:obj,
            type:'post',
            dataType:'json',
            success:function(result){
                if(result.success){
                    $('#episode_table').DataTable().ajax.reload();
                    $('#addEpisodeModal').modal('hide');
                    $('#episode_form_add_episode')[0].reset();
                }else{
                    alert(result.msg);
                }
            }
        });
    });

    //剧集-清除条件按钮事件
    $('#episode_button_clear_item').on('click',function(){
        $('#episode_form_search_item')[0].reset();
    });

    //剧集-条件搜索按钮事件
    $('#episode_button_search').on('click',function(){
        loadEpisodeTableData(true);
    });

    //条目单击事件
    $('#episode_table').on('click','tr',function(){
        var id = $(this).children().children("input[type=hidden]").val();
        if(id){
            $.ajax({
                url:'/record/episode/getEpisodeInfo.do',
                type:'post',
                dataType:'json',
                data:{'id':id},
                success:function(data){
                    if(data.success&&!data.success){
                        alert(data.msg);
                    }
                    var episode = data;
                    $('#updateEpisodeModal_input_id').val(id);
                    $('#updateEpisodeModal_input_name').val(episode.name);
                    $('#updateEpisodeModal_input_type').val(episode.type);
                    $('#updateEpisodeModal_input_total').val(episode.total);
                    $('#updateEpisodeModal_input_writer').val(episode.writer);
                    $('#updateEpisodeModal_input_performers').val(episode.performers);
                    var showTime = new Date(episode.showTime);
                    $('#updateEpisodeModal_input_showTime').val(showTime.getFullYear()+'-'+(showTime.getMonth()+1)+'-'+showTime.getDate());
                    var watchTime = new Date(episode.watchTime);
                    $('#updateEpisodeModal_input_watchTime').val(watchTime.getFullYear()+'-'+(watchTime.getMonth()+1)+'-'+watchTime.getDate());

                    if(episode.watchState === '已看完'){
                        $('#updateEpisodeModal_input_watchState').val('');
                        $('#updateEpisodeModal_input_watchState').attr("disabled","disabled");
                        $('#updateEpisodeModal_input_watchState').removeAttr("placeholder");
                        $('#updateEpisodeModal_select_watchState').val(episode.watchState);
                    }else{
                        $('#updateEpisodeModal_input_watchState').removeAttr("disabled");
                        $('#updateEpisodeModal_input_watchState').attr("placeholder","必填");
                        $('#updateEpisodeModal_select_watchState').val('正在追');
                        $('#updateEpisodeModal_input_watchState').val(episode.watchState.substring(3,episode.watchState.indexOf('集了')));
                    }
                    $('#updateEpisodeModal_textarea_introduce').val(episode.introduce);
                    $('#updateEpisodeModal_select_dramaType').val(episode.dramaType);
                    $('#updateEpisodeModal_select_level').val(episode.level);
                    $('#updateEpisodeModal_select_state').val(episode.isEnd);
                    $('#updateEpisodeModal').modal('show');
                }
            });
        }
    });

    //剧集-信息更新按钮
    $('#updateEpisodeModal_button_submit').on('click',function(){
        var obj = {};

        obj.id = $('#updateEpisodeModal_input_id').val();
        obj.name = $('#updateEpisodeModal_input_name').val().trim();
        obj.type = $('#updateEpisodeModal_input_type').val().trim();
        obj.total = $('#updateEpisodeModal_input_total').val().trim();
        obj.writer = $('#updateEpisodeModal_input_writer').val().trim();
        obj.dramaType = $('#updateEpisodeModal_select_dramaType').val();
        obj.level = $('#updateEpisodeModal_select_level').val();
        obj.performers = $('#updateEpisodeModal_input_performers').val().trim();
        obj.showTime = $('#updateEpisodeModal_input_showTime').val();
        obj.watchTime = $('#updateEpisodeModal_input_watchTime').val();
        obj.state = $('#updateEpisodeModal_select_state').val();
        if($('#updateEpisodeModal_select_watchState').val() === '正在追'){
            obj.watchState = '该看第'+$('#updateEpisodeModal_input_watchState').val().trim()+'集了';
        }else{
            obj.watchState = '已看完'
        }
        obj.introduce = $('#updateEpisodeModal_textarea_introduce').val().trim();
        $.ajax({
            url:'/record/episode/updateEpisode.do',
            data:obj,
            type:'post',
            dataType:'json',
            success:function(result){
                if(result.success){
                    $('#episode_table').DataTable().draw(false);
                    $('#updateEpisodeModal').modal('hide');
                }else{
                    alert(result.msg);
                }
            }
        });
    });
});

function loadEpisodeTableData(isSearch){
    if($.fn.dataTable.isDataTable('#episode_table')){
        $('#episode_table').DataTable().destroy();
    }
    $('#episode_table').dataTable({
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
            'url': '/record/episode/episodeList.do',
            'type':'post',
            'dataSrc': 'data',
            'data':function(d){
                if(isSearch){
                    d.name = '%'+$('#episode_input_search_item_name').val().trim()+'%';
                    d.type = '%'+$('#episode_input_search_item_type').val().trim()+'%';
                    d.dramaType = $('#episode_input_search_item_dramaType').val();
                    d.performers = '%'+$('#episode_input_search_item_performers').val().trim()+'%';
                    d.state = $('#episode_input_search_item_state').val();
                    d.seasonYear = $('#episode_input_search_item_season_year').val();
                    d.seasonSeason = $('#episode_input_search_item_season_season').val();
                    d.watchState = $('#episode_input_search_item_watchState').val();
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
            {'data': 'name','width':'15%',render:function(data){
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
            {'data': 'type','width':'8%',render:function(data){
                return '&nbsp;&nbsp;'+data;
            }},
            {'data': 'dramaType','width':'5%',render:function(data){
                return '&nbsp;&nbsp;'+data;
            }},
            {'data': 'performers','width':'25%',render:function(data){
                if(data.length>29){
                    data = data.substr(0,29)+'...';
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
            {'data': 'level','width':'12%',render:function(data){
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