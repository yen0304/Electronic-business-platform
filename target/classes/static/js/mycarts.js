$(document).ready(function(){
  Checklogin();
  loading_cart_data();
});


function Checklogin() {
  $.ajax({
    url: "/users/session-username",
    dataType: "text",
    success:function(data) {
      console.log(data);
      if(data != ""){
        $("#check").empty();
        var html;
        html='<label>#{username}</label><a href="/sign_out">登出</a>';
        html = html.replace(/#{username}/g, data);
        $("#check").append(html);
      }else {
        alert("登入訊息已過期，請重新登入");
        window.location.href="/";
      }
    }
  });
}

function loading_cart_data() {
  $("#info").empty();
  $.ajax({
    url: "/carts/" ,
    dataType: "json",
    success:function(data) {
      console.log(data);
      for(var i = 0 ;i < data.length ;i++ ){
        var producturl="product?=" + data[i].pid;
        var num=data[i].num;
        var title=data[i].title;
        var price=data[i].price;
        
        var html=
    '<div class="row">'+
      '<div class="col-3">'+
        '<div class="title">#{title}</div>'+
        '<div class="img">#{img}</div>'+
      '</div>'+
      '<div class="col-2">商品價錢'+
        '<div class="price">#{price}</div>'+
      '</div>'+
     '<div class="col-3">數量'+
        '<div class="num" id="num">#{num}</div>'+
      '</div>'+
      '<div class="col-2">總和'+
        '<div class="totalprice">#{total}</div>'+
      '</div>'+
      '<div class="col-2">'+
        '<button class="btn btn-danger">刪除</button>'+
      '</div>'+
    '</div>';
        
        html = html.replace(/#{title}/g, title);
        html = html.replace(/#{price}/g, price);
        html = html.replace(/#{num}/g, num);
        html = html.replace(/#{total}/g, num*price);
        
        $("#info").append(html);
        
      }
    }
  });
}