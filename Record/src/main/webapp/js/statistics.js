//线图
function lineOption(title,legendData,xAxisData,series){
    var option = {
        title: {
            text: title,
            textStyle:{
                color:'black'
            }
        },
        tooltip: {
            trigger: 'axis'
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data:xAxisData
        },
        yAxis: {
            type: 'value'
        },
        series:series
    };
    if(legendData!=null&&legendData!=''){
        option.legend = {
          data:legendData
        };
    }
    return option
}

//top图
function topOption(title,legendData,yAxisData,series){
    var option = {
        title: {
            text: title
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            minInterval : 1
        },
        yAxis: {
            type: 'category',
            data: yAxisData
        },
        series: series
    };
    if(legendData!=null&&legendData!=''){
        option.legend = {
            data:legendData
        };
    }
    return option
}

//饼图
function pieOption(title,legendData,data){
    var option = {
        title : {
            text: title
        },
        color:['#f60574', '#0b4fd4','#3ad387','#00d3d4','#d5008a'],
        tooltip : {
            trigger: 'item',
            formatter: "{b} : {c} 部 ({d}%)"
        },
        series : [
            {
                type: 'pie',
                radius : '50%',
                center: ['50%', '50%'],
                data:data,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    if(legendData!=null){
        option.legend = {
            orient: 'vertical',
            left: 'left',
            data: legendData
        };
    }
    return option;
}

//雷达图
function radarOption(title,legendData,indicator,data){
    var option = {
        title: {
            text: title
        },
        tooltip: {},
        radar: {
            name: {
                textStyle: {
                    color: '#fff',
                    backgroundColor: '#000',
                    borderRadius: 1,
                    padding: [0, 0]
                }
            },
            center:['50%','55%'],
            indicator: indicator
        },
        series: [{
            type: 'radar',
            data : data
        }]
    };
    if(legendData != null){
        option.legend = {
          data : legendData
        };
    }
    return option;
}

$(function(){

    //初始化观看数量年份日历插件
    $('#watch_time_year_input').datetimepicker({
        startView: 'decade',
        minView: 'decade',
        format: 'yyyy',
        maxViewMode: 2,
        minViewMode:2,
        autoclose: true,
        language:  'zh-CN'
    }).on('changeDate',function(){
        loadWatchTimeTrendChart($('#watch_time_year_input').val());
    });

    //初始化观看数量年份
    $('#watch_time_year_input').val(new Date().getYear()+1900);
    init_statistics_charts();

    //初始化刷新按钮
    $('#refresh_btn').on('click',function(){
        init_statistics_charts();
    });
});

function init_statistics_charts(){
    loadTotalCount();
    loadWatchTimeTrendChart($('#watch_time_year_input').val());
    loadEpisodeTypeTopChart();
    loadMovieTypeTopChart();
    loadAnimationTypeTopChart();
    loadEpisodePerformersTopChart();
    loadMoviePerformersTopChart();
    loadAnimationPerformersTopChart();
    loadEpisodeDramaTypePieChart();
    loadMovieRegionPieChart();
    loadAnimationDramaTypePieChart();
    loadLevelRadarChart();
    loadOperationTable(false);
}

//初始化当前数量
function loadTotalCount(){
    $.ajax({
        url:'/statistics/totalCount.do',
        type:'post',
        dataType:'json',
        success:function(data){
            var ol = $('#total_count_div ol');
            ol.html('');
            var html = '<li>当前数量</li>\n' +
                '<li>剧集：<font color="#c23531">'+data.episode+'</font>&nbsp;部</li>\n' +
                '<li>电影：<font color="#436bc3">'+data.movie+'</font>&nbsp;部</li>\n' +
                '<li>动漫：<font color="#1ec353">'+data.animation+'</font>&nbsp;部</li>';
            ol.html(html);

            ol = $('#watch_state_div ol');
            ol.html('');
            var html = '<li>正在追</li>\n' +
                '<li>剧集：<font color="#c23531">'+data._episode+'</font>&nbsp;部</li>\n' +
                '<li>动漫：<font color="#1ec353">'+data._animation+'</font>&nbsp;部</li>';
            ol.html(html);
        }
    });
}

//加载观看数量趋势图数据
function loadWatchTimeTrendChart(year){
    var watchTimeTrendChart = echarts.init(document.getElementById('watch_time_trend_div'));
    watchTimeTrendChart.showLoading({text: '正在计算数据...'  });
    $.ajax({
        url:'/statistics/watchTimeTrend.do',
        type:'post',
        dataType:'json',
        data:{'year':year},
        success:function(data){
            var series = [{
                name:'剧集',
                type:'line',
                data:new Array(12),
                itemStyle:{
                    normal:{
                        color:'#c23531'
                    }
                }
            },{
                name:'电影',
                type:'line',
                data:new Array(12),
                itemStyle:{
                    normal:{
                        color:'#436BC3'
                    }
                }
            },{
                name:'动漫',
                type:'line',
                data:new Array(12),
                itemStyle:{
                    normal:{
                        color:'#1ec353'
                    }
                }
            }];
            for(var i=0;i<series.length;i++){
                series[i].data.fill(0);
            }
            for(var i=0;i<data.length;i++){
                if(data[i].type=='剧集'){
                    for(var j=1;j<=12;j++){
                        if(j==data[i].month){
                            series[0].data[j-1] = data[i].count;
                        }
                    }
                    continue;
                }
                if(data[i].type=='电影'){
                    for(var j=1;j<=12;j++){
                        if(j==data[i].month){
                            series[1].data[j-1] = data[i].count;
                        }
                    }
                    continue;
                }
                if(data[i].type=='动漫'){
                    for(var j=1;j<=12;j++){
                        if(j==data[i].month){
                            series[2].data[j-1] = data[i].count;
                        }
                    }
                }
            }
            var option = lineOption($('#watch_time_year_input').val()+'年观看数量情况',['剧集','电影','动漫'],['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],series);
            option.tooltip.formatter = function(data){
                var html = data[0].name+'<br>';
                for(var i=0;i<data.length;i++){
                    html += data[i].marker+data[i].seriesName+'：'+data[i].value+'部<br>';
                }
                return html;
            };
            watchTimeTrendChart.setOption(option);
            watchTimeTrendChart.hideLoading();
        }
    });
}

//加载剧集类型排行top图数据
function loadEpisodeTypeTopChart(){
    var episodeTypeTopChart = echarts.init(document.getElementById('episode_type_top_div'));
    episodeTypeTopChart.showLoading({text: '正在计算数据...'  });
    $.ajax({
        url:'/statistics/episodeTypeTop.do',
        type:'post',
        dataType:'json',
        success:function(data){
            var yAxisData = new Array(data.length);
            for(var i=0;i<data.length;i++){
                yAxisData[i] = data[i].type;
            }
            yAxisData.reverse();
            var series = new Array(1);
            var seriesData = new Array(data.length);
            for(var i=0;i<data.length;i++){
                seriesData[i] = data[i].count;
            }
            seriesData.reverse();
            series[0] = {
                name:'数量',
                type:'bar',
                data:seriesData
            };
            var option = topOption('剧集剧情类型排行',null,yAxisData,series);
            option.tooltip.formatter = function(data){
                return data[0].axisValue+"："+data[0].value+"部";
            };
            episodeTypeTopChart.setOption(option);
            episodeTypeTopChart.hideLoading();
        }
    });
}

//加载电影类型排行top图数据
function loadMovieTypeTopChart(){
    var movieTypeTopChart = echarts.init(document.getElementById('movie_type_top_div'));
    movieTypeTopChart.showLoading({text: '正在计算数据...'  });
    $.ajax({
        url:'/statistics/movieTypeTop.do',
        type:'post',
        dataType:'json',
        success:function(data){
            var yAxisData = new Array(data.length);
            for(var i=0;i<data.length;i++){
                yAxisData[i] = data[i].type;
            }
            yAxisData.reverse();
            var series = new Array(1);
            var seriesData = new Array(data.length);
            for(var i=0;i<data.length;i++){
                seriesData[i] = data[i].count;
            }
            seriesData.reverse();
            series[0] = {
                name:'数量',
                type:'bar',
                data:seriesData,
                itemStyle:{
                    normal:{
                        color:'#436BC3'
                    }
                }
            };
            var option = topOption('电影剧情类型排行',null,yAxisData,series);
            option.tooltip.formatter = function(data){
                return data[0].axisValue+"："+data[0].value+"部";
            };
            movieTypeTopChart.setOption(option);
            movieTypeTopChart.hideLoading();
        }
    });
}

//加载动漫类型排行top图数据
function loadAnimationTypeTopChart(){
    var animationTypeTopChart = echarts.init(document.getElementById('animation_type_top_div'));
    animationTypeTopChart.showLoading({text: '正在计算数据...'  });
    $.ajax({
        url:'/statistics/animationTypeTop.do',
        type:'post',
        dataType:'json',
        success:function(data){
            var yAxisData = new Array(data.length);
            for(var i=0;i<data.length;i++){
                yAxisData[i] = data[i].type;
            }
            yAxisData.reverse();
            var series = new Array(1);
            var seriesData = new Array(data.length);
            for(var i=0;i<data.length;i++){
                seriesData[i] = data[i].count;
            }
            seriesData.reverse();
            series[0] = {
                name:'数量',
                type:'bar',
                data:seriesData,
                itemStyle:{
                    normal:{
                        color:'#1ec353'
                    }
                }
            };
            var option = topOption('动漫剧情类型排行',null,yAxisData,series);
            option.tooltip.formatter = function(data){
                return data[0].axisValue+"："+data[0].value+"部";
            };
            animationTypeTopChart.setOption(option);
            animationTypeTopChart.hideLoading();
        }
    });
}

//加载剧集演员排行top图数据
function loadEpisodePerformersTopChart(){
    var episodePerformersTopChart = echarts.init(document.getElementById('episode_performers_top_div'));
    episodePerformersTopChart.showLoading({text: '正在计算数据...'  });
    $.ajax({
        url:'/statistics/episodePerformersTop.do',
        type:'post',
        dataType:'json',
        success:function(data){
            var yAxisData = new Array(data.length);
            for(var i=0;i<data.length;i++){
                yAxisData[i] = data[i].name;
            }
            yAxisData.reverse();
            var series = new Array(1);
            var seriesData = new Array(data.length);
            for(var i=0;i<data.length;i++){
                seriesData[i] = data[i].count;
            }
            seriesData.reverse();
            series[0] = {
                name:'数量',
                type:'bar',
                data:seriesData
            };
            var option = topOption('剧集演员排行',null,yAxisData,series);
            option.tooltip.formatter = function(data){
                return data[0].axisValue+"："+data[0].value+"部";
            };
            episodePerformersTopChart.setOption(option);
            episodePerformersTopChart.hideLoading();
        }
    });
}

//加载电影演员排行top图数据
function loadMoviePerformersTopChart(){
    var moviePerformersTopChart = echarts.init(document.getElementById('movie_performers_top_div'));
    moviePerformersTopChart.showLoading({text: '正在计算数据...'  });
    $.ajax({
        url:'/statistics/moviePerformersTop.do',
        type:'post',
        dataType:'json',
        success:function(data){
            var yAxisData = new Array(data.length);
            for(var i=0;i<data.length;i++){
                yAxisData[i] = data[i].name;
            }
            yAxisData.reverse();
            var series = new Array(1);
            var seriesData = new Array(data.length);
            for(var i=0;i<data.length;i++){
                seriesData[i] = data[i].count;
            }
            seriesData.reverse();
            series[0] = {
                name:'数量',
                type:'bar',
                data:seriesData,
                itemStyle:{
                    normal:{
                        color:'#436BC3'
                    }
                }
            };
            var option = topOption('电影演员排行',null,yAxisData,series);
            option.tooltip.formatter = function(data){
                return data[0].axisValue+"："+data[0].value+"部";
            };
            moviePerformersTopChart.setOption(option);
            moviePerformersTopChart.hideLoading();
        }
    });
}

//加载动漫声优排行top图数据
function loadAnimationPerformersTopChart(){
    var animationPerformersTopChart = echarts.init(document.getElementById('animation_performers_top_div'));
    animationPerformersTopChart.showLoading({text: '正在计算数据...'  });
    $.ajax({
        url:'/statistics/animationPerformersTop.do',
        type:'post',
        dataType:'json',
        success:function(data){
            var yAxisData = new Array(data.length);
            for(var i=0;i<data.length;i++){
                yAxisData[i] = data[i].name;
            }
            yAxisData.reverse();
            var series = new Array(1);
            var seriesData = new Array(data.length);
            for(var i=0;i<data.length;i++){
                seriesData[i] = data[i].count;
            }
            seriesData.reverse();
            series[0] = {
                name:'数量',
                type:'bar',
                data:seriesData,
                itemStyle:{
                    normal:{
                        color:'#1ec353'
                    }
                }
            };
            var option = topOption('动漫声优排行',null,yAxisData,series);
            option.tooltip.formatter = function(data){
                return data[0].axisValue+"："+data[0].value+"部";
            };
            animationPerformersTopChart.setOption(option);
            animationPerformersTopChart.hideLoading();
        }
    });
}

//加载剧集种类饼图数据
function loadEpisodeDramaTypePieChart(){
    var episodeDramaTypePieChart = echarts.init(document.getElementById('episode_drama_type_pie_div'));
    episodeDramaTypePieChart.showLoading({text: '正在计算数据...'  });
    $.ajax({
        url:'/statistics/episodeDramaTypePie.do',
        type:'post',
        dataType:'json',
        success:function(data){
            var pieData = new Array(data.length);
            for(var i=0;i<data.length;i++){
                pieData[i] = {
                    name:data[i].drama_type,
                    value:data[i].count
                }
            }
            var option = pieOption('剧集种类分布',null,pieData);
            episodeDramaTypePieChart.setOption(option);
            episodeDramaTypePieChart.hideLoading();
        }
    });
}

//加载电影地区饼图数据
function loadMovieRegionPieChart(){
    var movieRegionPieChart = echarts.init(document.getElementById('movie_region_pie_div'));
    movieRegionPieChart.showLoading({text: '正在计算数据...'  });
    $.ajax({
        url:'/statistics/movieRegionPie.do',
        type:'post',
        dataType:'json',
        success:function(data){
            var pieData = new Array(data.length);
            for(var i=0;i<data.length;i++){
                pieData[i] = {
                    name:data[i].region,
                    value:data[i].count
                }
            }
            var option = pieOption('电影地区分布',null,pieData);
            movieRegionPieChart.setOption(option);
            movieRegionPieChart.hideLoading();
        }
    });
}

//加载动漫种类饼图数据
function loadAnimationDramaTypePieChart(){
    var animationDramaTypePieChart = echarts.init(document.getElementById('animation_drama_type_pie_div'));
    animationDramaTypePieChart.showLoading({text: '正在计算数据...'  });
    $.ajax({
        url:'/statistics/animationDramaTypePie.do',
        type:'post',
        dataType:'json',
        success:function(data){
            var pieData = new Array(data.length);
            for(var i=0;i<data.length;i++){
                pieData[i] = {
                    name:data[i].drama_type,
                    value:data[i].count
                }
            }
            var option = pieOption('动漫种类分布',null,pieData);
            animationDramaTypePieChart.setOption(option);
            animationDramaTypePieChart.hideLoading();
        }
    });
}

//加载星级雷达图数据
function loadLevelRadarChart(){
    var levelRadarChart = echarts.init(document.getElementById('level_radar_div'));
    levelRadarChart.showLoading({text: '正在计算数据...'  });
    $.ajax({
        url:'/statistics/levelRadar.do',
        type:'post',
        dataType:'json',
        success:function(data){
            var max = data[0].count;
            var seriesData = [
                {
                    name:'剧集'
                },{
                    name:'电影'
                },{
                    name:'动漫'
                }
            ];
            var episodeLevelData = new Array(5);
            var movieLevelData = new Array(5);
            var animationLevelData = new Array(5);
            episodeLevelData.fill(0);
            movieLevelData.fill(0);
            animationLevelData.fill(0);
            for(var i=0;i<data.length;i++){
                if(max<data[i].count){
                    max = data[i].count;
                }
                if(data[i].type == '剧集'){
                    for(j=1;j<=5;j++){
                        if(data[i].level == j){
                            episodeLevelData[j-1] = data[i].count;
                        }
                    }
                    continue;
                }
                if(data[i].type == '电影'){
                    for(j=1;j<=5;j++){
                        if(data[i].level == j){
                            movieLevelData[j-1] = data[i].count;
                        }
                    }
                    continue;
                }
                if(data[i].type == '动漫'){
                    for(j=1;j<=5;j++){
                        if(data[i].level == j){
                            animationLevelData[j-1] = data[i].count;
                        }
                    }
                }
                seriesData[0].value = episodeLevelData;
                seriesData[1].value = movieLevelData;
                seriesData[2].value = animationLevelData;
            }
            var indicator = [
                {name:'一星',max:max},
                {name:'二星',max:max},
                {name:'三星',max:max},
                {name:'四星',max:max},
                {name:'五星',max:max}
            ];
            var option = radarOption('星级统计',null,indicator,seriesData);
            option.tooltip.formatter = function(data){
                return data.data.name+'<br>' +
                    '一星：'+data.data.value[0]+' 部<br>'+
                    '二星：'+data.data.value[1]+' 部<br>'+
                    '三星：'+data.data.value[2]+' 部<br>'+
                    '四星：'+data.data.value[3]+' 部<br>'+
                    '五星：'+data.data.value[4]+' 部<br>';
            };
            levelRadarChart.setOption(option);
            levelRadarChart.hideLoading();
        }
    });
}

//加载操作记录表格数据
function loadOperationTable(isSearch){
    if($.fn.dataTable.isDataTable('#operation_table')){
        $('#operation_table').DataTable().destroy();
    }
    $('#operation_table').dataTable({
        //自动列宽
        'bAutoWidth': false,
        //加载数据时显示正在加载信息
        'bProcessing': true,
        //分页
        'paging': true,
        'bStateSave' : true,
        'sScrollY': '181px', //支持垂直滚动
        //从服务器处理分页
        'bServerSide': true,
        //查询请求action url
        'ajax': {
            'url': '/operation/operationList.do',
            'type':'post',
            'dataSrc': 'data',
            'data':function(d){
                if(isSearch){

                }
            }
        },
        //本地搜索
        'searching': false,
        //每页显示多少条数据
        'lengthChange':false,
        //每页显示数量：5条记录
        'iDisplayLength': 5,
        'bInfo' : false, //是否显示页脚信息，DataTables插件左下角显示记录数
        //'sPaginationType': 'two_button ', //详细分页组，可以支持直接跳转到某页
        'columns': [
            {'data': 'value','width':'100%',render:function(data){
                return data;
            }}
        ],
        //语言
        'language': {
            url: 'internation/message_operation_zh_CN.txt'
        },
        //排序
        'sort':false,
        'aaSorting': []
    });
}