$(function(){

    //刷新表格数据
    loadMovieTableData();

    //当模态框显示时
    $('#addMovieModal').on('shown.bs.modal', function () {
        $('#addMovieModal_input_name').focus();
    });
    //当模态框隐藏时
    $('#addMovieModal').on('hidden.bs.modal', function () {
        $('#movie_form_add_movie')[0].reset();
    });

    //按ESC隐藏模态框
    window.document.onkeydown = function(evt) {
        evt = (evt) ? evt : window.event;
        if (evt.keyCode) {
            if (evt.keyCode == 27) {
                $('#addMovieModal,#updateMovieModal').modal('hide');
            }
        }
    };


    //初始化剧集模态框中播出时间和观看时间输入框的日历插件
    $('#addMovieModal_input_showTime,#addMovieModal_input_watchTime,#updateMovieModal_input_showTime,#updateMovieModal_input_watchTime').datetimepicker({
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
    $('#addMovieModal_button_sync').on('click',function(){
        var id = prompt('豆瓣ID：');
        if(id != null && id.trim() != ''){
            $(this).attr("disabled",true);
            $(this).text('正在同步...');
            $.ajax({
                url:'/record/movie/movieInfoPull.do',
                type:'post',
                dataType:'json',
                data:{'id':id.trim()},
                success:function(data){
                    $('#addMovieModal_input_name').val(data.name);
                    $('#addMovieModal_input_type').val(data.type);
                    $('#addMovieModal_input_duration').val(data.duration);
                    $('#addMovieModal_input_director').val(data.director);
                    $('#addMovieModal_input_region').val(data.region);
                    $('#addMovieModal_input_performers').val(data.performers);
                    if(data.showTime){
                        var showTime = new Date(data.showTime);
                        $('#addMovieModal_input_showTime').val(showTime.getFullYear()+'-'+(showTime.getMonth()+1)+'-'+showTime.getDate());
                    }
                    $('#addMovieModal_textarea_introduce').val(data.introduce);

                    $('#addMovieModal_button_sync').text('同步');
                    $('#addMovieModal_button_sync').removeAttr('disabled');
                    if(data.success != null&& !data.success){
                        alert('同步失败');
                    }
                },
                error:function(){
                    $('#addMovieModal_button_sync').text('同步');
                    $('#addMovieModal_button_sync').removeAttr('disabled');
                    alert('同步失败');
                }
            });
        }
    });

    //初始化添加剧集模态框中清除按钮事件
    $('#addMovieModal_button_clear').on('click',function(){
        $('#movie_form_add_movie')[0].reset();
    });

    //添加剧集模态框提交按钮事件
    $("#addMovieModal_button_submit").on('click',function(){
        var obj = {};

        obj.name = $('#addMovieModal_input_name').val().trim();
        obj.type = $('#addMovieModal_input_type').val().trim();
        obj.duration = $('#addMovieModal_input_duration').val().trim();
        obj.director = $('#addMovieModal_input_director').val().trim();
        obj.region = $('#addMovieModal_input_region').val().trim();
        obj.level = $('#addMovieModal_select_level').val();
        obj.performers = $('#addMovieModal_input_performers').val().trim();
        obj.showTime = $('#addMovieModal_input_showTime').val();
        obj.watchTime = $('#addMovieModal_input_watchTime').val();
        obj.introduce = $('#addMovieModal_textarea_introduce').val().trim();

        $.ajax({
            url:'/record/movie/addMovie.do',
            data:obj,
            type:'post',
            dataType:'json',
            success:function(result){
                if(result.success){
                    $('#movie_table').DataTable().ajax.reload();
                    $('#addMovieModal').modal('hide');
                    $('#movie_form_add_movie')[0].reset();
                }else{
                    alert(result.msg);
                }
            }
        });
    });

    //剧集-清除条件按钮事件
    $('#movie_button_clear_item').on('click',function(){
        $('#movie_form_search_item')[0].reset();
    });

    //剧集-条件搜索按钮事件
    $('#movie_button_search').on('click',function(){
        loadMovieTableData(true);
    });

    //单击条目事件
    $('#movie_table').on('click','tr',function(){
        var id = $(this).children().children('input[type=hidden]').val();
        if(id){
            $.ajax({
                url:'/record/movie/getMovieInfo.do',
                type:'post',
                dataType:'json',
                data:{'id':id},
                success:function(data){
                    if(data.success&&!data.success){
                        alert(data.msg);
                    }
                    var movie = data;
                    $('#updateMovieModal_input_id').val(id);
                    $('#updateMovieModal_input_name').val(movie.name);
                    $('#updateMovieModal_input_type').val(movie.type);
                    $('#updateMovieModal_input_region').val(movie.region);
                    $('#updateMovieModal_input_duration').val(movie.duration);
                    $('#updateMovieModal_input_director').val(movie.director);
                    $('#updateMovieModal_input_performers').val(movie.performers);
                    var showTime = new Date(movie.showTime);
                    $('#updateMovieModal_input_showTime').val(showTime.getFullYear()+'-'+(showTime.getMonth()+1)+'-'+showTime.getDate());
                    var watchTime = new Date(movie.watchTime);
                    $('#updateMovieModal_input_watchTime').val(watchTime.getFullYear()+'-'+(watchTime.getMonth()+1)+'-'+watchTime.getDate());
                    $('#updateMovieModal_textarea_introduce').val(movie.introduce);
                    $('#updateMovieModal_select_level').val(movie.level);
                    $('#updateMovieModal').modal('show');
                }
            });
        }
    });

    //剧集-信息更新按钮
    $('#updateMovieModal_button_submit').on('click',function(){
        var obj = {};

        obj.id = $('#updateMovieModal_input_id').val();
        obj.name = $('#updateMovieModal_input_name').val().trim();
        obj.type = $('#updateMovieModal_input_type').val().trim();
        obj.duration = $('#updateMovieModal_input_duration').val().trim();
        obj.director = $('#updateMovieModal_input_director').val().trim();
        obj.region = $('#updateMovieModal_input_region').val().trim();
        obj.level = $('#updateMovieModal_select_level').val();
        obj.performers = $('#updateMovieModal_input_performers').val().trim();
        obj.showTime = $('#updateMovieModal_input_showTime').val();
        obj.watchTime = $('#updateMovieModal_input_watchTime').val();
        obj.introduce = $('#updateMovieModal_textarea_introduce').val().trim();
        $.ajax({
            url:'/record/movie/updateMovie.do',
            data:obj,
            type:'post',
            dataType:'json',
            success:function(result){
                if(result.success){
                    $('#movie_table').DataTable().draw(false);
                    $('#updateMovieModal').modal('hide');
                }else{
                    alert(result.msg);
                }
            }
        });
    });
});

function loadMovieTableData(isSearch){
    if($.fn.dataTable.isDataTable('#movie_table')){
        $('#movie_table').DataTable().destroy();
    }
    $('#movie_table').dataTable({
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
            'url': '/record/movie/movieList.do',
            'type':'post',
            'dataSrc': 'data',
            'data':function(d){
                if(isSearch){
                    d.name = '%'+$('#movie_input_search_item_name').val().trim()+'%';
                    d.type = '%'+$('#movie_input_search_item_type').val().trim()+'%';
                    d.region = '%'+$('#movie_input_search_item_region').val().trim()+'%';
                    d.director = '%'+$('#movie_input_search_item_director').val().trim()+'%';
                    d.performers = '%'+$('#movie_input_search_item_performers').val().trim()+'%';
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
            {'data': 'name','width':'12%',render:function(data){
                return '&nbsp;&nbsp;'+data;
            }},
            {'data': 'type','width':'10%',render:function(data){
                return '&nbsp;&nbsp;'+data;
            }},
            {'data': 'region','width':'5%',render:function(data){
                return '&nbsp;&nbsp;'+data;
            }},
            {'data': 'director','width':'10%',render:function(data){
                if(data.length>10){
                    data = data.substr(0,10)+'...';
                }
                return '&nbsp;&nbsp;'+data;
            }},
            {'data': 'performers','width':'30%',render:function(data){
                if(data.length>38){
                    data = data.substr(0,38)+'...';
                }
                return '&nbsp;&nbsp;'+data;
            }},
            {'data': 'duration','width':'7%',render:function(data){
                return  '&nbsp;&nbsp;'+data+' 分钟';
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