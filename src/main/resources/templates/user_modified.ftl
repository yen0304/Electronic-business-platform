<!DOCTYPE html>
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <title>CodePen - SpingBoot商城－修改資料（帳戶中心)</title>
  <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.0.0/css/bootstrap.min.css'><link rel="stylesheet" href="/static/css/user_modified.css">

</head>
<body>
<!-- partial:index.partial.html -->
<div class="navbar navbar-light bg-light navbar-expand-lg">
  <div class="container-fluid"><a class="navbar-brand">SpingBoot商城</a>
    <div class="nav-item">資料修改</div>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation"> <span class="navbar-toggler-icon"></span></button>
    <div class="collapse navbar-collapse justify-content-end" id="navbarText">
      <ul class="navbar-nav">
        <li class="nav-item"><a class="nav-link" active="active" aria-current="page" href="#">購物車</a></li>
        <li class="nav-item dropdown"><a class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false" href="#">會員中心</a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li>
              <button class="dropdown-item" id="modified">修改資料</button>
            </li>
            <li>
              <button class="dropdown-item" href="#">查看訂單</button>
            </li>
            <li>
              <button class="dropdown-item" id="check"><a href="/register">註冊</a><a href="/login">登入</a></button>
            </li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</div>
<section id="reg">
  <div class="container">
    <form class="row">
      <div class="col-12">
        <h4>修改基本資料</h4>
      </div>
      <div class="col-md-6" id="username">
        <div class="title">Username</div>
        <input name="username" value="資料載入中..." required="required" disabled="disabled"/>
        <button id="edit" type="button">編輯 </button>
      </div>
      <div class="col-md-6" id="gender">
        <div class="title">性別 </div>
        <input class="form-check-input" type="radio" name="gender" value="1"/>
        <label class="form-check-label" for="flexRadioDefault1">男</label>
        <input class="form-check-input" type="radio" name="gender" value="0"/>
        <label class="form-check-label" for="flexRadioDefault1">女</label>
      </div>
      <div class="col-md-6" id="phone">
        <div class="title">手機</div>
        <input name="phone" value="資料載入中..." required="required" disabled="disabled"/>
        <button id="edit" type="button">編輯 </button>
      </div>
      <div class="col-md-6" id="email">
        <div class="title">電子郵件</div>
        <input name="email" value="資料載入中..." required="required" disabled="disabled"/>
        <button id="edit" type="button">編輯 </button>
      </div>
      <div class="col-12">
        <button class="btn" id="save" type="button">儲存</button>
      </div>
    </form>
  </div>
</section>
<!-- partial -->
  <script src='https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.0.0/js/bootstrap.min.js'></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js'></script><script  src="/static/js/user_modified.js"></script>

</body>
</html>
