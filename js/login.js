$(document).ready(function(){
    $("input").focus(function() {
        $(this).closest('div').css("border", "2px solid black"); // Change border of parent div on focus
    }).blur(function() {
        $(this).closest('div').css("border", "none"); // Remove border on blur
    });
});