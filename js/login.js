$(document).ready(function(){
    $("input").focus(function() {
        $(this).css("border", "2px solid black"); // Change border of parent div on focus
    }).blur(function() {
        $(this).css("border", "none"); // Remove border on blur
    });
    $("#register").click(function(){
        $.get("http://localhost:8080/naaukri/index",{num:1})
        .done(function(data,textStatus,jqXHR){
            var contentType = jqXHR.getResponseHeader("Content-Type"); 
            if(contentType.startsWith("application/json")){
                window.location.href =getUrl(jqXHR);
            }
        })
        .fail(function(jqXHR, textStatus, errorThrown){
            errorHandle(jqXHR);
        })
    });
    $("#login").click(function(){
        $.post("http://localhost:8080/naaukri/login",
            {
            username : $("#username").val(),
            password : $("#password").val()
            }
        )
        .done(function(data,textStatus,jqXHR){
            $("#username").val("");
            $("#password").val("");
            console.log(data);
            alert(data);
            if(data == "login"){
                window.location.href="http://localhost:8080/naaukri/userHomepage";
            }else{
                alert(data);
            }
        })
    })
    function getUrl(jqXHR){
        let jqXHRParse = JSON.parse(jqXHR.responseText);
        return jqXHRParse.url;
    }
    function errorHandle(jqXHR){
        let status = jqXHR.status;
        if(status >=400 && status<500){
            alert(status+" Error occured contact the team !!");
        }else if(status>=500 && status<=510){
            alert("Error Occured in server side try again later!!");
        }
    }
});