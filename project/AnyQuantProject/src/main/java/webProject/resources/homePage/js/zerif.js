/* =================================
 LOADER
 =================================== */
// makes sure the whole site is loaded
jQuery(window).load(function() {
    // will first fade out the loading animation
    jQuery(".status").fadeOut();
    // will fade out the whole DIV that covers the website.
    jQuery(".preloader").delay(1000).fadeOut("slow");


    if(localStorage.userName==""){
        document.getElementById("name").innerHTML="返回主页";
        $("#down").removeClass("dropdown-menu");
        $("#down").addClass("dropdown-menu hide");

        //data-toggle="dropdown"
        var el=document.querySelector('#tar');
        el.dataset.toggle=null;

        console.log(localStorage.userName);
    }else{
        document.getElementById("name").innerHTML=localStorage.userName;
        $("#down").removeClass("dropdown-menu hide");
        $("#down").addClass("dropdown-menu");
        console.log(localStorage.userName);
    }
})
function welcome(){
    console.log("welcome");
    if(localStorage.userName==""){
        localStorage.userName="";
        window.location.href="../welcome.html";
    }else{

    }
}
function Logout(){
    localStorage.userName="";
    window.location.href="../welcome.html";
}
