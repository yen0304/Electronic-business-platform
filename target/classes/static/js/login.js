var apiurl={
    loginurl:"/userlogin",
    successloginurl:"/"
}

$("#enter").click(function () {
    var username = $("#username").val();
    var password = $("#password").val();


    $.post({
        url:apiurl.loginurl,
        contentType:"application/json;charset=UTF-8",
        data:JSON.stringify({"username":username,"password":password}),
        success:function(res) {
            //console.log(res);
            window.location.href = apiurl.successloginurl;//正確登入後頁面跳轉
        }
    });

});