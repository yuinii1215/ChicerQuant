function enter() {
    window.location.href="homePage/home.html";
}
function enterLog_Reg() {
    window.location.href="login.html";
}

function signUpHint() {
    //A NEW VIEW IS SHOWED
    alert("您已成功注册,请返回登录");
}

function test(){
    var userName=document.getElementById("username").value;
    var password=document.getElementById("password").value;

   if(document.getElementById("login").checked){
       //login recognize
       console.log("login");
       //function(userName,password){ return legal/illegal;}
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


