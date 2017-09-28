$('#btn_login').on('click',function(){
    var username = $('#login_username').val();
    var password = $('#login_password').val();
    $.ajax({
        url:'/login',
        data:{
            username:username,
            password:password
        },
        dataType:'json',
        success:function(msg){

        }
    });
});