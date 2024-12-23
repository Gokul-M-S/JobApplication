$(document).ready(function(){
    let user = $("#user");
    let hr = $("#hr");

    // Toggle visibility based on userType selection
    $("#userType").change(function(){
        let userTypeVal = $(this).val();
        if(userTypeVal == 1){ // Job seeker
            user.show();
            hr.hide();
        } else if(userTypeVal == 2){ // HR
            user.hide();
            hr.show();
        } else {
            user.hide();
            hr.hide();
        }
    });

    // Redirect to login page
    $("#login").click(function(){
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
        .fail(function(){
            errorHandle(jqXHR);
        })
    });
    // Exprience is how many years
    $("#Fresher").click(function(){
        $("#yearsDisplay").hide();
    });
    $("#Exprience").click(function(){
        $("#yearsDisplay").css("display", "inline-flex");
    });
    
    // Register button click event
    $('#register').click(function(){
        let list = new FormData(); // Create a new FormData object

        list.append('emailId', $("#emailId").val()); // Correct key as string
        list.append('userName', $("#username").val());
        list.append('password', $("#password").val());
        let rePassword = $("#rePassword").val();
        list.append('phoneNumber', $("#phoneNumber").val()); // Append phone number
        let userType = $("#userType").val();
        list.append('userType', userType);
        list.append('years',$("#years").val());
        if(userType == 1){ // Job seeker
            list.append('experience', $("input[name='Exprience']:checked").val()); // Get selected experience radio
            let resume = $('#resume')[0].files[0];
            list.append('resume',resume); // Append the file
            list.append("resumeName",resume.name);
        } else if(userType == 2){ // HR
            list.append('companyName', $("#companyName").val()); // Correct jQuery syntax
            list.append('companyAddress', $("#companyAddress").val());
        }

        // Send POST request to server using Ajax
        $.ajax({
            url: 'http://localhost:8080/naaukri/registration', // Update with your actual server endpoint
            type: 'POST',
            data: list, // Send FormData object
            processData: false, // Prevent jQuery from processing the data
            contentType: false, // Ensure correct content type for file upload
            success: function(data) {
                alert(data); // Alert the server response
                $('#registrationForm').get(0).reset();
                hr.hide();
                user.hide();
            },
            error: function(jqXHR, textStatus, errorThrown){
                alert("An error occurred: " + textStatus);
            }
        });
    });
});
