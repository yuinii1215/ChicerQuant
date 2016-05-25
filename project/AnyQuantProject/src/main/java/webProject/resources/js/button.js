function enterSuccess() {
    location.href="homePage/home.html";
}
function enter() {
    localStorage.userName="";
    location.href="homePage/home.html";
}
function backStep() {
    window.location.href="welcome.html";
}
function enterLog_Reg() {
   location.href="login.html";
}

function signUpHint() {
    alert("您已成功注册,请返回登录");
}

function test(){
    var userName=document.getElementById("username").value;
    var password=document.getElementById("password").value;

   if(document.getElementById("login").checked){
       //login recognize
       console.log("login");
       //function(userName,password){ return legal/illegal;}

       $http.post($scope.url, {//$objData->username,$objData->password
           "userName": currentDate,
           "password": password,
           "method": "verifyPasswordService"
       }).success(function (data, status) {
               $scope.status = status;
               $scope.legalUser = data;
               console.log($scope.legalUser);
           })
           .error(function (data, status) {
               $scope.data = data || "Request failed";
               $scope.status = status;
           });

       if(1){
           enter();

       }

   }else{
       //sign up service
       console.log("sign up");
       //function(userName,password){ return success/fail;}
       var signUpResult=1;
       signUpHint();
   }


}


