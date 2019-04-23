$(function () {
    $("#douban_button_search").on('click', function () {
        $("#douban_div_search_items").html('');
        var name = $("#douban_input_search").val();
        var isCheck = true;
        if (!name) {
            isCheck = false;
        } else if (name.trim().length <= 0) {
            isCheck = false;
        }
        if (isCheck) {
            $.ajax({
                url: '/record/douban/search.do',
                type: 'post',
                dataType: 'json',
                data: {'name': name.trim()},
                success: function (data) {
                    if (data.length == 0) {
                        $("#douban_div_search_items").html('<p>未搜索到 <font color="red">' + name + '</font></p>');
                    } else {
                        var html = '';
                        for (var i = 0, len = data.length; i < len; i++) {
                            var item = data[i];
                            html += '<div name="item" style="float:left">' +
                                        '<img id="img_'+item.id+'" src="' + item.img + '" style="cursor: pointer;border:3px solid #ccc" onclick="clickItemAction('+item.id+',this)" data-clipboard-text="">' +
                                         '<span>'+item.title+'</span>';
                            html += '</div>';
                        }
                        $("#douban_div_search_items").html(html);
                    }
                }
            });
        } else {
            $("#douban_input_search").val('');
        }
    });
});

function clickItemAction(id,me){
     var $me = $(me);
     $me.attr("data-clipboard-text",id);
     $("img").css("border","3px solid #ccc");
     $me.css("border","3px solid blue");
     new ClipboardJS('#img_'+id);
}