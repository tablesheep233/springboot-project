    var InterValObj; //timer变量，控制时间
    var count = 60; //间隔函数，1秒执行
    var curCount;//当前剩余秒数

    $("#codeBtn").click(function () {
        curCount = count;
        var uphone = $("#uPhone").val();
        if (uphone==null||uphone=="") {
            $("#msg").html("手机号不能为空");
        }else if(checkPhone(uphone)){
            $("#msg").html("该号码已注册");
        } else{
            $.ajax({
                type: "post",
                url: "/user/outSms",
                data: {"uphone": uphone},
                success: function (data) {

                }
            })
            $("#codeBtn").val(curCount + "重新发送");
            $("#codeBtn").attr("disabled", "true");
            InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
        }
    })

    function checkPhone(phone) {
        var p;
        $.ajax({
            type: "post",
            url: "/user/checkPhone",
            async: false,
            cache: false,
            data: {"uphone": phone},
            success: function (data) {
                p = data;
            }
        });
        return p;
    }

    function SetRemainTime() {
        if (curCount == 1) {
            window.clearInterval(InterValObj);//停止计时器
            $("#codeBtn").val("获取验证码");
            $("#codeBtn").removeAttr("disabled");//启用按钮
        }
        else {
            curCount--;
            $("#codeBtn").val(curCount + "重新发送");
        }
    }

    var checkName = function () {

        var username = document.getElementById("username").value;
        if (username != null && username != "") {

            //1.创建异步交互对象
            var xhr = createXmlHttp();

            //4.处理响应结果
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {//对象读取成功
                    if (xhr.status == 200) {//服务器响应正常
                        var msg = document.getElementById("msg");
                        if (xhr.responseText == "have" && document.getElementById("register") != null) {
                            //注册页面判断账号是否存在
                            document.getElementById("username").value = "";
                            msg.innerHTML = " tip:此账号已存在";
                        }
                        // else if (xhr.responseText=="no" && document.getElementById("login")!=null) {
                        //     //登录页面判断账号是否存在
                        //     msg.innerHTML = "<b>账号不存在</b>";
                        // }
                    }
                }
            }
            //2.建立连接
            xhr.open("GET", "/user/checkUserName?username=" + username);
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

        try {// Firefox, Opera 8.0+, Safari
            xmlHttp = new XMLHttpRequest();
        }
        catch (e) {
            try {// Internet Explorer
                xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
            }
            catch (e) {
                try {
                    xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
                }
                catch (e) {
                }
            }
        }

        return xmlHttp;
    }

    var validate = function () {
        var smscode = $("#smscode").val();
        var uphone = $("#uPhone").val();
        var check=true;
        $.ajax({
            type: "post",
            url: "/user/checkSms",
            async: false,
            cache: false,
            data: {"smscode": smscode,"uphone": uphone},
            success: function (data) {
                check = data;
            }
        });
        if (!check.rest) {
            $("#msg").html(check.tip);
            return false;
        }
        var pwd = document.getElementById("password").value;
        var cpwd = document.getElementById("confirmPassword").value;
        if (pwd == cpwd) {
            return true;
        } else {
            document.getElementById("password").value = "";
            document.getElementById("confirmPassword").value = "";
            document.getElementById("msg").innerHTML = " tip:两次密码不相同";
            return false;
        }

    }







