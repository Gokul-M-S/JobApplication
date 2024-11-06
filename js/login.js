$(document).ready(function(){
    $("input").focus(function() {
        $(this).css("border", "2px solid black"); // Change border of parent div on focus
    }).blur(function() {
        $(this).css("border", "none"); // Remove border on blur
    });
    $("#register").click(function(){
        window.location.href="http://localhost:8080/naaukri/registrationPage";
    });
    $("#login").click(function(){
        $.post("http://localhost:8080/naaukri/login",{
            username : $("#username").val(),
            password : $("#password").val()
        }).done(function(data,textStatus,jqXHR){
            $("#username").val("");
            $("#password").val("");
            console.log(data);
            
            if(data == "login"){
                window.location.href="http://localhost:8080/naaukri/userHomepage";
            }
        })
    })
});