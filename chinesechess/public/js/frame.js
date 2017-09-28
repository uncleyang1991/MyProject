$('#btn_login').on('click',function(){
    var username = $('#login_username').val().trim();
    var password = $('#login_password').val().trim();
    $.ajax({
        url:'/login',
        type:'post',
        data:{
            username:username,
            password:password
        },
        dataType:'json',
        success:function(msg){
            if(msg.success){
                alert(msg.msg);
            }else{
                alert(msg.msg);
            }
        }
    });
});