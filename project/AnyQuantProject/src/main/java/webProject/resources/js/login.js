window.onload=function() {

    var pop=document.getElementById("pop");
    $("#pop").css({ "width": "100%", "height": $(document).height() });
    var loginContainer=document.getElementById("loginContainer");
    var sign_button=document.getElementById("sign_button");
    var back_button=document.getElementById("back_button");
    var signInUp=document.getElementById("signInUp");

    var  aboutUS=document.getElementById("aboutUS");
    var  infoDown=document.getElementById("infoDown");
    var  info=document.getElementById("info");



    sign_button.onclick=function() {
        pop.style.display="block";
        signInUp.style.display="block";
        $('#signInUp').fadeIn();
    }
    back_button.onclick=function() {
        pop.style.display="none";
        signInUp.style.display="none";
    }

    aboutUS.onclick=function() {
        $("#info").removeClass("fadeInDown animated");
        $("#info").addClass("fadeInUp animated");
        $('#info').fadeIn();
        info.style.display="block";
        window.location.href= "#info";
    }

    infoDown.onclick=function() {
        $("#info").removeClass("fadeInUp animated");
        $("#info").addClass("fadeInDown animated");
        $('#info').fadeOut();
        info.style.display="none";


    }

}

//建立与"myApp"标识的 ng-app 的联系
var app = angular.module('myApp', []);
//建立与 "myApp"标识的Html中 的 "SearchCtrl"标识的 ng-controller的联系
app.controller("loginCtrl", function ($scope, $http) {
    $scope.url = 'http://115.159.97.98/php/serviceController.php';

    $scope.back=function(){
        backStep();
    }

    //http请求发送（异步），这里用读取本地的一个json文件进行了模拟，真实情况下改成网址即可（post或get）
    $scope.verify=function() {
        var tip= document.getElementById("tip");
        tip.style.display = "none";

      var userName=document.getElementById("username").value;
      var password=document.getElementById("password").value;
        console.log("verifying");
        if(document.getElementById("login").checked){
            //login recognize
            console.log("login_button");

            $http.post($scope.url, {
                    "username": userName,
                    "password": password,
                    "method": "verifyPasswordService"
                })
                .success(function (response) {
                    var tip= document.getElementById("tip");
                    tip.style.display = "none";

                    var login_state = response;
                    var successRequest = login_state.retmsg;
                    var login_flag = login_state[0].qualified;
                    console.log(login_flag);
               if (successRequest=="success") {
                    if (login_flag == "true") {
                        localStorage.userName=userName;
                        enterSuccess();
                    } else {
                        console.log("not success");

                        var tip= document.getElementById("tip");
                        tip.innerHTML="帐号不存在/密码错误,请重试!";
                        tip.style.display = "inline";
                        setTimeout(invisible,1500);
        /*                        $.extend($.gritter.options, {
                                    time: 1500,
                                });
                                    // clean the wrapper position class
                                    $('#gritter-notice-wrapper').attr('class', '');
                                    // global setting override
                                    $.extend($.gritter.options, {
                                        position: '' + $(this).attr('id') + '' // possibilities: bottom-left, bottom-right, top-left, top-right
                                    });
                                    $.gritter.options.position = "bottom-right";
                                    $.gritter.add({
                                        title: $(this).find('span.title').text(), // could be simpler, just for demo purposes
                                        text: "您好！" + "</br>" + "帐号不存在/密码错误,请重试",
                                    });

                        alert("帐号不存在/密码错误,请重试");*/
                    }
                }else {
                   var tip= document.getElementById("tip");
                   tip.innerHTML="登录请求失败,请重试!";
                   tip.style.display = "inline";
                   setTimeout(invisible,1500);
   /*                $.extend($.gritter.options, {
                       time: 1500,
                   });
                   // clean the wrapper position class
                   $('#gritter-notice-wrapper').attr('class', '');
                   // global setting override
                   $.extend($.gritter.options, {
                       position: '' + $(this).attr('id') + '' // possibilities: bottom-left, bottom-right, top-left, top-right
                   });
                   $.gritter.options.position = "bottom-right";
                   $.gritter.add({
                       title: $(this).find('span.title').text(), // could be simpler, just for demo purposes
                       text: "您好！" + "</br>" + "登录请求失败,请重试",
                   });
                 alert("登录请求失败,请重试"); */
               }
                });

        }else{// signUpService($username,$password)
            console.log("sign up");

            var signUpResult=1;

            $http.post($scope.url, {//$objData->username,$objData->password
                    "username": userName,
                    "password": password,
                    "method": "signUpService"
                })
                //返回函数
                .success(function (response) {
                    var tip= document.getElementById("tip");
                    tip.style.display = "none";

                    var sign_up_state=response;
                    var sign_flag=sign_up_state.operation;

                    if(sign_flag=="success"){
                        var tip= document.getElementById("tip");
                        tip.innerHTML="感谢注册本网站！" + "</br>" + "您已经注册成功,请返回登录";
                        tip.style.display = "inline";
                        setTimeout(invisible,1500);

       /*                 $.extend($.gritter.options, {
                            time: 1500,
                        });
                        // clean the wrapper position class
                        $('#gritter-notice-wrapper').attr('class', '');
                        // global setting override
                        $.extend($.gritter.options, {
                            position: '' + $(this).attr('id') + '' // possibilities: bottom-left, bottom-right, top-left, top-right
                        });
                        $.gritter.options.position = "bottom-right";
                        $.gritter.add({
                            title: $(this).find('span.title').text(), // could be simpler, just for demo purposes
                            text: "感谢注册本网站！" + "</br>" + "您已经注册成功,请返回登录",
                        });
                    alert("您已经注册成功,请返回登录");*/

                        //删除用户的方法
                        //$http.post($scope.url, {
                        //        "username": userName,
                        //        "method": "removeUserService"
                        //    })
                        //    //返回函数
                        //    .success(function (response) {
                        //        console.log("remove user");
                        //    });
                    }else{

                        var tip= document.getElementById("tip");
                        tip.innerHTML="该用户已存在，请重新输入!";
                        tip.style.display = "inline";
                        setTimeout(invisible,1500);
     /*                   $.extend($.gritter.options, {
                            time: 1500,
                        });
                        // clean the wrapper position class
                        $('#gritter-notice-wrapper').attr('class', '');
                        // global setting override
                        $.extend($.gritter.options, {
                            position: '' + $(this).attr('id') + '' // possibilities: bottom-left, bottom-right, top-left, top-right
                        });
                        $.gritter.options.position = "bottom-right";
                        $.gritter.add({
                            title: $(this).find('span.title').text(), // could be simpler, just for demo purposes
                            text: "您好！" + "</br>" + "该用户已存在，请重试",
                        });
                    alert("该用户已存在，请重新输入"); */
                    }

                });



        }
    };

    $scope.cleanText=function(){//清除input内部的内容
        document.getElementById("username").value = "";
        document.getElementById("password").value = "";
    }

    function invisible(){
        var tip= document.getElementById("tip");
        tip.style.display = "none";
    }
});
