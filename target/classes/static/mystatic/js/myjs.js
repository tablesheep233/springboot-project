var checkName = function () {

    var username = document.getElementById("username").value;
    if(username != null && username != ""){

        //1.创建异步交互对象
        var xhr = createXmlHttp();

        //4.处理响应结果
        xhr.onreadystatechange = function(){
            if(xhr.readyState==4){//对象读取成功
                if(xhr.status==200){//服务器响应正常
                    var msg = document.getElementById("msg");
                    if(xhr.responseText=="have" && document.getElementById("register")!=null){
                        //注册页面判断账号是否存在
                        document.getElementById("username").value = "";
                        msg.innerHTML = "<b>此账号已存在</b>";
                    }else if (xhr.responseText=="no" && document.getElementById("login")!=null) {
                        //登录页面判断账号是否存在
                        msg.innerHTML = "<b>账号不存在</b>";
                    }
                }
            }
        }
        //2.建立连接
        xhr.open("GET","/user/checkUserName?username="+username);
        //3.发送请求
        xhr.send(null);
    }
}

var cleanMsg = function () {

    var msg = document.getElementById("msg");
    //账号合法，清除响应消息
    msg.innerHTML = "";
}

function createXmlHttp() {
    var xmlHttp;

    try{// Firefox, Opera 8.0+, Safari
        xmlHttp = new XMLHttpRequest();
    }
    catch (e) {
        try{// Internet Explorer
            xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
        }
        catch (e) {
            try {
                xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            catch (e) {}
        }
    }

    return xmlHttp;
}