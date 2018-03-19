$(function(){
    var method = {};

    method.login = function(){
        var username = $('#username_input').val();
        var password = $('#password_input').val();
        if(username !== null && username.trim() !== '' && password !== null && password.trim() !== ''){
            $.ajax({
                url:'/record/main/login.do',
                type:'post',
                dataType:'json',
                data:{
                    username:username.trim(),
                    password:password.trim()
                },
                success:function(data){
                    if(data.success){
                        location.href = "/record/main.html";
                    }else{
                        alert(data.msg);
                    }
                }
            });
        }
    };

    $('#username_input,#password_input').on('keypress',function(event){
        if(event.keyCode === 13){
            method.login();
        }
    });

    $('#login_button').on('click',function(){
        method.login();
    });
});