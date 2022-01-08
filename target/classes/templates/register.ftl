<!DOCTYPE html>
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <title>springboot商城-註冊</title>
  <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.0.0/css/bootstrap.min.css'><link rel="stylesheet" href="/static/css/register.css">

</head>
<body>
<!-- partial:index.partial.html -->
<div class="container-fluid d-flex" id="header">
  <div class="align-self-center d-flex">
    <h1> <a href="/">SpringBoot商城</a></h1>
    <h3 class="align-self-center"><a href="">註冊</a></h3>
  </div>
</div>
<section id="reg">
  <div class="container">
    <form class="row">
      <div class="col-12">
        <h4>註冊資料</h4>
      </div>
      <div class="col-md-6">
        <div class="title">Username</div>
        <input id="username" required="required"/>
      </div>
      <div class="col-md-6">
        <div class="title">Password</div>
        <input id="password" type="password" required="required"/>
      </div>
      <div class="col-12">
        <div class="title">性別</div>
        <input class="form-check-input" type="radio" name="gender" value="1" required="required"/>
        <label class="form-check-label" for="flexRadioDefault1">男</label>
        <input class="form-check-input" type="radio" name="gender" value="0" required="required"/>
        <label class="form-check-label" for="flexRadioDefault1">女</label>
      </div>
      <div class="col-md-6">
        <div class="title">手機</div>
        <input id="phone" required="required"/>
      </div>
      <div class="col-md-6">
        <div class="title">電子郵件</div>
        <input id="email" type="email" required="required"/>
      </div>
      <div class="col-12">
        <button class="btn" id="sub">註冊</button>
      </div>
    </form>
  </div>
</section>
<!-- partial -->
  <script src='https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.min.js'></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js'></script><script  src="/static/js/register.js"></script>

</body>
</html>
