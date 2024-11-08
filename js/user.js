$(document).ready(function(){
    
    $("#logout").click(function(){
        $.get("http://localhost:8080/naaukri/logout")
        .done(function(data,textStatus,jqXHR){
            let contentType = jqXHR.getResponseHeader("Content-Type");
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
})

index