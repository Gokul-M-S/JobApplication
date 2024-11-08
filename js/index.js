
$(document).ready(function() {
    //register url
    $(".register").click(function(){
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
    //login url
    $(".login").click(function(){
        $.get("http://localhost:8080/naaukri/index",{num:2})
        .done(function(data,textStatus,jqXHR){
            console.log(data);
            console.log(textStatus);
            var contentType = jqXHR.getResponseHeader("Content-Type");
            console.log(contentType); 
            if(contentType.startsWith("application/json")){
                window.location.href=getUrl(jqXHR);
            }
        })
        .fail(function(jqXHR, textStatus, errorThrown){
            errorHandle(jqXHR);
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