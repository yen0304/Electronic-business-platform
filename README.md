# SpringBoot電商平台實戰(1) 前言

在練習的差不多之後，決定實作一個完整的電商平台「SpringBoot電商平台實戰」，也參考了不少網路上的資源，主要是參考CSDN上的雨醉東風（由於不知能不能引用所以有興趣可以自行查詢）來撰寫api，參考架構，差別最大的是該教學是使用MyBatis做資料庫操作，而我是使用JDBC，預計將來會再嘗試Redis、SpringSecurity、再加上前端完成一個完整的網站。



本來在2021年的10月初已經做好了，但遇到需要實作記住持續登入的功能，雖然SpringSecurity做得到，但部分功能都涉及到session，還有登入時候的POST方法驗證登入，變成了SpringSecurity + 自己做的POST一起登入的問題，還有持續保持登入狀態等問題，將來會再更熟悉SpringSecurity再把他做整合。



## 本例相關工具、環境、語言、配置

開發環境：

- Java 11
- IntelliJ IDEA 2020.3.2
- Spring Boot 2.3.7 RELEASE

工具：

Lombok（[使用教學](https://kucw.github.io/blog/2020/3/java-lombok/)）用途：提高開發效率，自動產生getter以及setter等等。

資料庫：MySql 8.0.22

操作資料庫方法：JDBC

前端模板引擎：Freemarker



其他：

前端：html、css、javascript、jquery、ajax



學習到的新知識：

密碼加密、統一異常處例、AOP、MVC、Spring Session



如果以上有東西不熟悉的話，可以參考本網站個文章來做參考。





## **application.properties**配置

```xml-dtd
#DB Configuration
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/db_store?serverTimezone=Asia/Taipei&characterEncoding=utf-8
spring.datasource.username=root
spring.datasource.password=springboot

#Spring Session
## Session 存儲方式
spring.session.store-type=redis
## Session 過期時間，默認單位為 s
server.servlet.session.timeout=600
## Session 存儲到 Redis 鍵的前綴
spring.session.redis.namespace=test:spring:session


#Freemaker設定
spring.freemarker.cache=false
# 模版後綴名 默認為ftl
spring.freemarker.suffix=.ftl
# 文件編碼
spring.freemarker.charset=UTF-8
# 模版加載的目錄
spring.freemarker.template-loader-path=classpath:/templates/
# 靜態資源訪問路徑
spring.mvc.static-path-pattern=/static/**
# 獲取根目錄路徑
spring.freemarker.request-context-attribute=request
```



## maven設定

本例子先使用了Lombok、jdbc、mysql、跨域註解的commons-codec、Freemarker

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.7.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.example</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>demo</name>
    <description>Demo project for Spring Boot</description>
    <properties>
        <java.version>11</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.session</groupId>
            <artifactId>spring-session-data-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-freemarker</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>

        <dependency>
            <groupId>commons-codec</groupId>
            <artifactId>commons-codec</artifactId>
            <version>1.11</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.22</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>

```



## 規劃資料庫中的用戶數據

首先，先創一個名為db_store的資料庫

```mysql
CREATE DATABASE db_store
USE db_store
```

再來資料表創建相關數據，這邊學到了一個東西，就是鹽值，是將原密碼透過某種加密方式而產生的新密碼名稱，詳細介紹下面會解釋。

```mysql
CREATE TABLE t_user (
    uid INT AUTO_INCREMENT PRIMARY KEY ,
    username VARCHAR(20) UNIQUE NOT NULL,
    password CHAR(32) NOT NULL,
    salt CHAR(60) COMMENT'鹽值',
    gender INT(1) COMMENT '性别:0-女,1-男',
    phone VARCHAR(20),
    email VARCHAR(50),
    avatar VARCHAR(100) COMMENT'頭像',
    is_delete INT(1) COMMENT '是否删除:0-否,1-是',
    created_user VARCHAR(20) COMMENT'創建人',
    created_time DATETIME COMMENT '創建時間',
    modified_user VARCHAR(20)COMMENT'最後修改人',
    modified_time DATETIME COMMENT'最後修改時間',
)
```



## 創建用戶的實體類別

### 基類（Entity）

在這邊，要先創建所有類別的基類，意思就是在將來所有實體的類別，例如商品、用戶等等都會用到這個基類，那這個類別我們把它命名為BaseEntity，詳細功能在本例中就是會記錄上最後修改的時間、使用者等等，大家應該看例子就知道了

在這邊也學到了序列化，簡單說就是在電腦上，我們可以把資料順利的儲存在Ram上面，以便在執行上更順利，詳細的解說我也不太清楚，總之就是（以下網路參考）

1、當想把的內存中的對象狀態保存到一個文件中或者資料庫中時候；

2、當想用套接字在網絡上傳送對象的時候；

3、當想通過RMI傳輸對象的時候；

用上Serializable就對了！

```java
/*全部實體類別的基底類別*/
//implements Serializable序列化
@Data
public class BaseEntity implements Serializable {

    private String created_user ;
    private Date created_time;
    private String modified_user;
    private Date modified_time;
}
```



### User Class

接下來創建User 這個類別，當然繼承了BaseEntity

```java
@Data
public class User extends BaseEntity implements Serializable {

    private Integer uid;
    private String username;
    private String password;
    private String updatepassword;
    private Integer gender;
    private String phone;
    private String email;
    private String avatar;
    private Integer isDelete;
    private String role;
    private Integer enabled;
}
```



### UserRowMapper

```java
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {

        User user=new User();

        //後面result.getxx()，()裡面放的是要取得的資料在資料庫中的名字
        user.setUid(resultSet.getInt("uid"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setSalt(resultSet.getString("Salt"));
        user.setGender(resultSet.getInt("gender"));
        user.setPhone(resultSet.getString("phone"));
        user.setEmail(resultSet.getString("email"));
        user.setAvatar(resultSet.getString("avatar"));
        user.setIsDelete(resultSet.getInt("is_delete"));
        user.setCreated_user(resultSet.getString("created_user"));
        user.setCreated_time(resultSet.getTime("created_time"));
        user.setModified_user(resultSet.getString("modified_user"));
        user.setModified_time(resultSet.getTime("modified_time"));
        return user;
    }
}
```



## 創建用戶、用戶登入、修改數據、查詢數據

### User-Dao



#### Interface

```java
public interface UserDao {

    //創建用戶
    String CreateUser(User user);

    //根據用戶名稱username查詢數據
    List<User> ReadByUsername(String username);
    //根據用戶uid查詢數據
    List<User> ReadByUid(Integer uid);
    //根據用戶uid修改數據
    String UpdateByUid(Integer uid,User user);


}
```



#### Implement

```java
@Component
public class UserImpl implements UserDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public String CreateUser(User user) {
        String sql ="INSERT INTO t_user" +
                "(username,password,salt,gender,phone,email,avatar,is_delete,created_user,created_time,modified_user,modified_time)" +
                "VALUE" +
                "(:userName,:userPassword,:Salt,:Gender,:Phone,:Email,:Avatar,:Is_delete,:Create_user,:Create_time,:Modified_user,:Modified_time)";

        Map<String, Object> map =new HashMap<>();
        map.put("userName",user.getUsername());
        map.put("userPassword",user.getPassword());
        map.put("Salt",user.getSalt());
        map.put("Gender",user.getGender());
        map.put("Phone",user.getPhone());
        map.put("Email",user.getEmail());
        map.put("Avatar",user.getAvatar());
        map.put("Is_delete",user.getIsDelete());
        map.put("Create_user",user.getCreated_user());
        map.put("Create_time",user.getCreated_time());
        map.put("Modified_user",user.getModified_user());
        map.put("Modified_time",user.getModified_time());

        namedParameterJdbcTemplate.update(sql,map);
        return ("註冊成功");
    }

    @Override
    public List<User> ReadByUsername(String username) {
        String sql="SELECT * FROM t_user WHERE username=:userName";

        Map<String,Object>map= new HashMap<>();
        map.put("userName",username);

        List<User> list =namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());

        if(list.size()>0){
            return list;
        }else {
            //如果是空集合[]
            return null;
        }
    }

    @Override
    public List<User> ReadByUid(Integer uid) {
        String sql="SELECT * FROM t_user WHERE uid=:Uid";

        Map<String,Object>map= new HashMap<>();
        map.put("Uid",uid);

        List<User> list =namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());

        if(list.size()>0){
            return list;
        }else {
            //如果是空集合[]
            return null;
        }
    }

    @Override
    public String UpdateByUid(Integer uid, User user) {

        String sql ="UPDATE t_user SET " +
                "phone=:Phone,email=:Email,gender=:Gender ,modified_user=:Modified_user,modified_time=:Modified_time" +
                " WHERE uid=:Uid";

        Map<String, Object> map =new HashMap<>();
        map.put("Gender",user.getGender());
        map.put("Phone",user.getPhone());
        map.put("Email",user.getEmail());
        map.put("Modified_user",user.getModified_user());
        map.put("Modified_time",user.getModified_time());
        map.put("Uid",uid);
        namedParameterJdbcTemplate.update(sql,map);

        return "修改成功";
    }
}
```



### User-Service

這邊學到了一個程式上設計的知識，就是在做創建用戶的service層時，也會順便做ReadByUsername(String username)這個依據用戶名稱查詢資料的方法，目的就是實作我們實際上要避免用戶名稱重複，或是做修改資料的時候找不到帳戶等等的概念。

#### Interface

```java
public interface UserService {

    //創建用戶
    String CreateUser(User user);
    //根據用戶名稱username查詢數據
    List<User> ReadByUsername(String username);
  
    //登入
    List<User> login(String username,String password);
		//修改
    String UpdateByUid(Integer uid,User user);
}
```

#### Implements

這邊學到了一個密碼加密的方法，就是先在後端產生一串隨機的亂碼，就是鹽值（Salt），之後再經過自定義的方法，把使用者的密碼做計算之後在產生一組新的密碼，

簡單來說：

即使別人擁有了鹽值過後，他也沒辦法得知我們在後端驗證演算的方式，我們可以是加上一組固定的數字，加減乘除等等都可以，鹽值經過這些計算之後再到資料庫已儲存的加密結果做驗證。



所以我們來實作這些功能部分需要的工具

#### 鹽值的產生

```java
//https://www.jianshu.com/p/2c62ae005db4 參考網址
public class UUIDutil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
```



#### MD5加密

這邊直接把鹽值再加上使用者輸入的原始密碼加上之後，在做MD5加密產生在資料庫儲存的密碼

```java
@Component
public class MD5util {

    public static String md5(String src ,String salt){
        //這邊使用了springframework的加密方式
        //md5DigestAsHex參數是Bytes，所以透過java String類將字串轉為Bytes
        String result=src+ salt;
        return DigestUtils.md5DigestAsHex(result.getBytes());
    }

}
```

#### 完整程式碼

```java
@Component
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String CreateUser(User user) {
        //先檢查是否有重複名稱
        String username =user.getUsername();
        //如果查詢結果不為null，代表有重複名稱
        if(userDao.ReadByUsername(username) != null){
            //  拋出錯誤
            throw new IllegalArgumentException("用戶名稱重複");
        }
        //如果可以執行到這一行，代表用戶名稱沒有被使用
        //存取當下時間
        Date now =new Date();

        //密碼加密
        String salt = UUIDutil.uuid();
        user.setSalt(salt);
        String md5Password = MD5util.md5(user.getPassword(),salt);
        user.setPassword(md5Password);
        //開始設置其他後台參數，時間，使用者等等
        user.setIsDelete(0);
        user.setCreated_user(username);
        user.setCreated_time(now);
        user.setModified_user(username);
        user.setModified_time(now);

        return userDao.CreateUser(user);
    }

    @Override
    public List<User> login(String username, String password) {

        //新增陣列，來接受userDao.ReadByUsername的回傳資料
        List<User> list=new ArrayList<>();
        list=userDao.ReadByUsername(username);
        //因為只會有一筆，所以在陣列的第0個位置
        User user= list.get(0);

        if(userDao.ReadByUsername(username)== null){
            //  拋出錯誤
            throw new IllegalArgumentException("登入失敗，找不到帳號");
        }

        if(user.getIsDelete()==1){
            throw new IllegalArgumentException("登入失敗，帳號已經被刪除");
        }

        String salt = user.getSalt();
        String md5Password = MD5util.md5(password,salt);
        if(!user.getPassword().equals(md5Password)){
            throw new PasswordExpiredException("密碼輸入錯誤，請重新輸入");
        }

        return list;
    }

    @Override
    public List<User> ReadByUsername(String username) {
        return userDao.ReadByUsername(username);
    }

    @Override
    public String UpdateByUid(Integer uid, User user) {

        if(userDao.ReadByUid(uid)== null){
            //  拋出錯誤
            throw new IllegalArgumentException("獲取數據失敗，請再重新嘗試一次");
        }
        //新增陣列，來接受userDao.ReadByUsername的回傳資料
        List<User> list=new ArrayList<>();
        list=userDao.ReadByUid(uid);
        //因為只會有一筆，所以在陣列的第0個位置
        User olduser= list.get(0);

        if(olduser.getIsDelete()==1){
            throw new IllegalArgumentException("登入失敗，帳號已經被刪除");
        }

        olduser.setEmail(user.getEmail());
        olduser.setPhone(user.getPhone());
        olduser.setGender(user.getGender());
        olduser.setModified_time(new Date());
        olduser.setModified_user(user.getModified_user());

        return userDao.UpdateByUid(uid,olduser);
    }
}
```



### 如何維持登入資訊

在這邊我卡了很久，因為記住登入的方法雖然Spring Security中有這個功能，但是在獲取資料上還沒有學習到，因為USER Controller也需要獲取這些記住的資訊，但也因為在整合上還沒有觸碰到（因為已經做了差不多了），所以打算以後先單獨學習再來做整合，才不會遇到連Service層都需要重做的問題，所以就去了解了Cookie、Session、Token（這邊卡超久去理解QAQ）。



#### Cookie、Session、Token是什麼？

[參考網址](https://codertw.com/%E7%A8%8B%E5%BC%8F%E8%AA%9E%E8%A8%80/712796/#outline__4) 在看完文章以及一些實作的影片之後，也特別推薦這部[Youtube的影片](https://www.youtube.com/watch?v=lNQAl71Abqc)，這次決定用Session，也把目前得知的知識記錄下來，

Cookie：儲存在瀏覽器裡面，裡面存著cookie的名字、cookie值（也因為這樣Cookie 只支持存字符串數據）、在哪一個domain、安全性等等...（上面影片有詳細說明），藉由request header傳遞，藉由設定過期時間來使瀏覽器記住Cookie，這樣關閉瀏覽器在開啟之後就還是會存在在瀏覽器中。



Session：session 是另一種記錄服務器和客戶端會話狀態的機制，其實Session是跟Cookie一樣的東西，只是它儲存在伺服器當中，透過(Key,Value)儲存在伺服器當中，使用者透過儲存在Cookie中的SessionId傳送到伺服器獲取資訊。

SessionId：他其實就是儲存在Cookie中的Cookie Name叫SessionId的Value，裡面記錄一組SessionId。

SessionId的產生[(參考)](https://codertw.com/%E7%A8%8B%E5%BC%8F%E8%AA%9E%E8%A8%80/312110/)，是由伺服器所產生的，再一次對話中都會產生，在Java裡面就是使用

`HttpServletRequest.getSession(ture)`這邊相等於`HttpServletRequest.getSession()`，因為預設是true，這邊的意思就是如果沒有得到SessionID的時候伺服器就會自動產生了。



所以簡單說，使用者的瀏覽器Cookie儲存著一組SessionID（號碼牌），之後使用者再藉由Cookie傳送到伺服器裡面，伺服器就會藉由SessionID，來在伺服器中藉由ke來獲取 value

Token：更複雜，但是更安全，Spring Security就是用這種方式來實現remember me的機制，日後再來更新。



![](explain.png)



#### 透過實做攔截器來維持登入

我們就透過攔截器，來攔截HttpServletRequest中的Session，裡面存放用戶的uid，如果為空，返回302狀態碼，另外也學習到了如何使用HttpServletRequest request以及HttpServletResponse response，本來在想說是不是要新增類別把之後，用變數接住在透過return回傳回去，結果測試了一下根本不用，直接在方法裡面操作request以及response就好。

新增類別：

```java
@Component
public class  LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getSession().getAttribute("uid") == null) {
            System.out.println("session中的uid為null");
            response.setStatus(302);
            return false;
        }
        //System.out.println("session中的uid為= " + request.getSession().getAttribute("uid"));
        return true;
    }
}
```



 透過@Configuration來設定

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

/*
        //在這個patterns裡面就是不用登入的
        List<String> patterns = new ArrayList<>();
        patterns.add("/user_login");
        patterns.add("/static/**");
*/

      //有些之後會用到
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/login_success")
                .addPathPatterns("/carts/**")
                .addPathPatterns("/users/**");
        //.excludePathPatterns(patterns);
    }
}
```

這樣之後只要call 跟/user/**有關的API，就會先被攔截器攔截起來，經過驗證有無獲取user Id之後，才能進行相關操作，甚至在Controller裡面也可以再接住session一次，就可以在Service中跟User.getId()來進一步驗證id是否相同。



### User-Controller

這邊會加上@CrossOrigin(origins = "*")的原因目前還不清楚，只是這個是跨網域專用的，因為沒有加上去時候雖然APITester沒問題，但透過Ajax call API的時候，ajax 會得不到物件的資料，得到的數據是"{'readyState':0,'status':0,'statusText':'error'}"，查了很久才發現加上這個就好了。


```java
@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;


    //用戶註冊
    @PostMapping("/register")
    public String create(@RequestBody User user){
        return userService.CreateUser(user);
    }

    //用戶登入
    @PostMapping("/userlogin")
    public List<User> userlogin(HttpSession session, @RequestBody User user){
        List<User> list =new ArrayList<>();
        list =userService.login(user.getUsername(),user.getPassword());
      	//登入成功後設置session的值
        session.setAttribute("uid",list.get(0).getUid());
        session.setAttribute("username",list.get(0).getUsername());

        return (list);
    }

    @PutMapping("users/{uid}") //根據帳號做修改
    public String update(@PathVariable Integer uid,@RequestBody User user){ //@Path用來取得url路徑的值

        return userService.UpdateByUid(uid,user);
    }

    @GetMapping("users/{username}")
    public List<User> read(@PathVariable String username){ //@Path用來取得url路徑的值

        return userService.ReadByUsername(username);
    }



		//用來獲取session中的username，在前端某些需要的地方會用到。
    @GetMapping("users/session-username")
    public  String getsessionusername(HttpSession session){ //@Path用來取得url路徑的值
        return (String)session.getAttribute("username");
    }

    //用戶登出
    @GetMapping("/sign_out")
    public String signout(HttpSession session){
      //銷毀session中的KV
        session.removeAttribute("uid");
        session.removeAttribute("username");
        return "登出成功";
    }

}
```



### 規劃前端Javascript

#### 登入頁面的Javascript

```javascript
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
            window.location.href = apiurl.successloginurl;//正確登入後頁面跳轉回首頁
        }
    });

});
```



#### 註冊頁面Javascript

```javascript
var apiurl={
    registerurl:"/register",
    registersuccess:"/"
}

$("#sub").click(function () {
  
    var username = $("#username").val();
    var password = $("#password").val();
    var phone = $("#phone").val();
    var email = $("#email").val();
    var gender = 0;
    gender=$("input[name='gender']:checked").val();

    $.post({
        url:apiurl.registerurl,
        contentType:"application/json;charset=UTF-8",
        data:JSON.stringify({"username":username,"password":password,"gender":gender,"phone":phone,"email":email}),
        
      success:function(res) {
            alert("註冊成功");
            window.location.href = apiurl.registersuccess;//正確登入後頁面跳轉
        }
    });

});
```



#### 最重要的Javascript碼，前端得知使否登入

主要透過Checklogin();檢查是否登入來決定html的狀況，例如是登入按鈕還是登出按鈕，

在別的頁面，例如加入購物車時、前往個人商品頁時，都可以先透過/users/session-username這個API，如果進去的話就會反回username，沒有登入的話就會被攔截器返回302 error。



```javascript
//載入頁面時先在入熱門商品資訊（之後文章會寫）
//再來進入Checklogin();檢查是否登入來決定html的狀況，例如是登入按鈕還是登出按鈕
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
```





## @ControllerAdvice統一處理異常

springboot提供了可以用來攔截並處理應用程式中全部Controller所拋出的Exception例外錯誤的註解@ControllerAdvice，由於用法很固定，所以也不多說明，但目前沒有很摸懂各種錯誤詳細的錯誤碼是多少，目前也只把種類（2-5)開頭用正確而已



```java
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handle(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("IllegalArgumentException：" + exception.getMessage());
    }

}

```



## 規劃本專案所有返回Model的Controller

```java
@org.springframework.stereotype.Controller
public class Controller {



    @GetMapping("/login")
    public String loginpage(Model model)
    {
        return "login";
    }

    //登入頁面
    @GetMapping("/login_success")
    public String successpage(Model model)
    {
        return "/login_success";
    }

    //首頁
    @GetMapping("/")
    public String index(Model model)
    {
        return "/index";
    }

    //註冊頁面
    @GetMapping("/register")
    public String register(Model model){ return  "/register";}

    //user修改資料頁面
    @GetMapping("/users/@{username}")
    public String usermodified(Model model){ return  "/user_modified";}

    //商品詳情頁面
    @GetMapping("/product")
    public String products(Model model){ return  "/product";}


    //購物車詳情頁面
    @GetMapping("/mycarts")
    public String mycarts(Model model){ return  "/mycarts";}



}
```
## 部落格文章連結
https://yen0304.github.io/p/springboot%E9%9B%BB%E5%95%86%E5%B9%B3%E5%8F%B0%E5%AF%A6%E6%88%B01%E7%94%A8%E6%88%B6%E8%A8%BB%E5%86%8A%E7%94%A8%E6%88%B6%E7%99%BB%E5%85%A5%E4%BF%AE%E6%94%B9%E8%B3%87%E6%96%99/
