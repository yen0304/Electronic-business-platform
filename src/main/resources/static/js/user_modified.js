$(document).ready(function(){
  Checklogin();
});

var username=" ";
var userdataurl ="/users/";
var puturl="";

function Checklogin() {
  $.ajax({
    url: "/users/session-username",
    dataType: "text",
    success:function(data) {
      //console.log(data);
      //進去到了代表有登入
        //清空登入/註冊的html
        $("#check").empty();
        var html;
        html='<label>#{username}</label><a href="/sign_out">登出</a>';
        html = html.replace(/#{username}/g, data);
        $("#check").append(html);
        //在另外把username記下來顯示，並放入到js變數中，其他function會用到
        username=data;
        //並設定查詢User資料的api URL
        userdataurl=("/users/"+data);
        //驗證已經登入後，在進到下面的function來載入使用者資料
        loading_user_data();
    },error:function(){
        alert("錯誤，尚未登入，將回到首頁");
        window.location.href=("/");
      }
  });
}

//送出修改時如果沒有登入
$(document).on("click",'#modified',function(){
  if(username == " "){
    alert("尚未登入");
  }else{
    //修改成到此頁面的url
    window.location.href ="/users/@"+ username;
  }
});


function loading_user_data() {
  $.ajax({
    url: userdataurl,
    dataType: "json",
    success:function(data) {
      console.log(data);
      $("#username").empty();
      var html ='<div class="title">Username</div>'
          +'<input name="username" value="#{username}" required="required" disabled="disabled"/>'
          +'<button id="edit" type="button">編輯 </button>';
      html = html.replace(/#{username}/g, data[0].username);
      $("#username").append(html);
      //---------------------------------------//
      $("#phone").empty();
      var html ='<div class="title">手機</div>'
          +'<input name="phone" value="#{phone}" required="required" disabled="disabled"/>'
          +'<button id="edit" type="button">編輯 </button>';
      html = html.replace(/#{phone}/g, data[0].phone);
      $("#phone").append(html);
      //---------------------------------------//
      $("#email").empty();
      var html ='<div class="title">電子郵件</div>'
          +'<input name="email" value="#{email}" required="required" disabled="disabled"/>'
          +'<button id="edit" type="button">編輯 </button>';
      html = html.replace(/#{email}/g, data[0].email);
      $("#email").append(html);
      //---------------------------------------//
      if(data[0].gender == "1"){
        $("input[name=gender][value='1']").attr('checked',true);
      }else{
        $("input[name=gender][value='0']").attr('checked',true);
      }
      //---------------------------------------//
      puturl="/users/"+data[0].uid;
    }
  });
}

$(document).on("click",'#edit',function(){
  $(this).prev().attr('disabled', false);
});



$("#save").click(function () {

  var username = $("input[name='username']").val();
  var phone = $("input[name='phone']").val();
  var email = $("input[name='email']").val();
  var gender = 0;
  gender=$("input[name='gender']:checked").val();

  $.ajax({
    url: puturl,
    type: "put",
    contentType:"application/json;charset=UTF-8",
    data:JSON.stringify({"username":username,"gender":gender,"phone":phone,"email":email}),

    success:function(res) {
      alert("資料修改成功");
    }
  });

});