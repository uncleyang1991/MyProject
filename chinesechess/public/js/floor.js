$(function(){
    $('#bulletin').liMarquee({
        direction: 'left',
        drag:false
    });

    $('#room_list').dataTable({
        //自动列宽
        "bAutoWidth": false,
        //加载数据时显示正在加载信息
        "bProcessing": true,
        //分页
        "paging": true,
        //从服务器处理分页
        "bServerSide": true,
        //查询请求action url
        "ajax": {
            "url": "/room_list",
            "type":"post",
            "dataSrc": "data",
            "data": function ( d ) {

            }
        },
        // 客户端传给服务器的查询参数为sSearch,服务端根据条件查出数据源即可
        //'bFilter':true ,
        //本地搜索
        "searching": false,
        //每页显示多少条数据
        "lengthChange":false,
        //每页显示数量：5条记录
        "iDisplayLength": 5,
        "columns": [
            {"data": "a"},
            {"data": "b"},
            {"data": "c"}
        ],
        "columnDefs": [
        {
            "targets": 0,
            "data":"a",
            "width":"12%"
        },{
            "targets":1,
            "data":"b",
            "width":"11%"
        },{
            "targets": 2,
            "data":"c",
            "width":"10%"
        }],

        //语言
        "language": {
            url: 'internation/message_zh_CN.txt'
        },
        //排序
        "sort":false,
        "aaSorting": []
    });
});