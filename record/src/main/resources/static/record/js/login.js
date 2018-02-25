$(function(){
    var method = {};

    method.login = function(){
        var password = $('#password_input').val();
        if(password !== null && password.trim() !== ''){
            $.ajax({
                url:'/record/main/login.do',
                type:'post',
                dataType:'json',
                data:{
                    username:'uy',
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

    $('#password_input').on('keypress',function(event){
        if(event.keyCode === 13){
            method.login();
        }
    });

    $('#enter_btn').on('click',function(){
        method.login();
    });
});