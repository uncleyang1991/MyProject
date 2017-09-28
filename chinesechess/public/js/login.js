$('#btn_login').on('click',function(){
    $('#login_error_msg').html('');
    var username = $('#login_username').val();
    var password = $('#login_password').val();
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
                window.location.href = '/floor';
            }else{
                $('#login_error_msg').html(msg.msg);
            }
        }
    });
});