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
        "bStateSave" : true,
        "sScrollY": "360.5px", //支持垂直滚动
        //从服务器处理分页
        "bServerSide": true,
        //查询请求action url
        "ajax": {
            "url": "/room_list",
            "type":"post",
            "dataSrc": "data"
        },
        // 客户端传给服务器的查询参数为sSearch,服务端根据条件查出数据源即可
        //'bFilter':true ,
        //本地搜索
        "searching": false,
        //每页显示多少条数据
        "lengthChange":false,
        //每页显示数量：5条记录
        "iDisplayLength": 10,
        "columns": [
            {"data": "no","width":"15%"},
            {"data": "name","width":"40%"},
            {"data": "owner","width":"30%"},
            {"data": "state","width":"15%"}
        ],

        //语言
        "language": {
            url: 'internation/message_zh_CN.txt'
        },
        //排序
        "sort":false,
        "aaSorting": []
    });
});