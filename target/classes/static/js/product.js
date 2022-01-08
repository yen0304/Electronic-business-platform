var username=" ";
var producturl="/products/" + window.location.search;
var pid="";

console.log(producturl);


$(document).ready(function(){
  Checklogin();
  loading_product_data();
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
      }
    }
  });
}



function loading_product_data() {
  $.ajax({
    url: producturl,
    dataType: "json",
    success:function(data) {
      console.log(data);
      $("#itemname").text(data.title);
      $("#price").text("$" + data.price);
      $("#num").text("剩餘數量：" + data.num);

      pid=data.id;

      if(data.status == 0){
        alert("此商品已下架");
      }
    }
  });
}

$(document).on("click",'#dec',function(){

  var num = parseInt($("input[name=productnum]").val());
  if(num==1){
    alert("已經是最小值")
  }else{
    $("input[name=productnum]").val(num-1);
  }

});

$(document).on("click",'#inc',function(){
  var num = parseInt($("input[name=productnum]").val());
  $("input[name=productnum]").val(num+1);
});

$(document).on("click",'#addtocart',function(){
  $.ajax({
    url:"/carts/addcart",
    data:{"pid":pid,"amount":$("input[name=productnum]").val()},
    type:"post",
    dateType:"json",
    success:function(res){
      if(res != ""){
        alert("加入成功");
      }else{
        alert("尚未登入");
      }
    }
  });
});