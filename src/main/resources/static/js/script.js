$(document).ready(function(){
  showHotList();
  Checklogin();
});

function showHotList() {
  $("#hot-list").empty();
  $.ajax({
    url: "/products/list/hot",
    dataType: "json",
    success:function(data) {
      hotlist=data;
      console.log(hotlist);
      for (var i = 0; i < hotlist.length; i++) {
        var html ='<div class="content col-md-2">'
            +'<div class="title">#{title}</div>'
            +'<div class="price">$#{price}</div><a class="d-flex justify-content-end" href="#{href}">查看商品</a>'
            +'</div>';
        html = html.replace(/#{title}/g, hotlist[i].title);
        html = html.replace(/#{price}/g, hotlist[i].price);
        html = html.replace(/#{href}/g, "/product?id=" + hotlist[i].id);

        $("#hot-list").append(html);
      }
    }
  });
}

var username=" ";

function Checklogin() {
  $.ajax({
    url: "/users/session-username",
    dataType: "text",
    success:function(data) {
      console.log(data);
        var html;
        html='<label>#{username}</label><a href="/sign_out">登出</a>';
        html = html.replace(/#{username}/g, data);
        $("#check").append(html);
        username=data;
    },error:function (){
      //alert("沒登入");
    }
  });
}

//在頁面Checklogin()時，會依據有無登入來設定js變數username的值，如果沒有設置到的話代表沒有登入，
//所以按下修改資料的超連結按鈕時，就會進行阻擋反饋
$(document).on("click",'#modified',function(){
  if(username == " "){
    alert("尚未登入");
  }else{
    window.location.href ="/users/@"+ username;
  }
});