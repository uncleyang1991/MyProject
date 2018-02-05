$(function(){

    $.get("statistics.html",function(data){
        $("#statistics").html(data);
    });

    $('a[href="#statistics"]').on('shown.bs.tab',function(){
        init_statistics_charts();
    });

    $('a[href="#episode"]').on('shown.bs.tab',function(){
        if($.fn.dataTable.isDataTable('#episode_table')){
            return;
        }
        $.get("episode.html",function(data){
            $("#episode").html(data);
        });
    });

    $('a[href="#movie"]').on('shown.bs.tab',function(){
        if($.fn.dataTable.isDataTable('#movie_table')){
            return;
        }
        $.get("movie.html",function(data){
            $("#movie").html(data);
        });
    });

    $('a[href="#animation"]').on('shown.bs.tab',function(){
        if($.fn.dataTable.isDataTable('#animation_table')){
            return;
        }
        $.get("animation.html",function(data){
            $("#animation").html(data);
        });
    });
});