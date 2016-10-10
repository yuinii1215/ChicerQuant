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

        $("#tar").removeClass("caret");
        $("#tar").addClass("caret hide");

        $("#icon").removeClass("fa fa-user");
        $("#icon").addClass("fa fa-user hide");

        console.log(localStorage.userName);
    }else{

        document.getElementById("name").innerHTML=localStorage.userName;
        $("#icon").removeClass("fa fa-user hide");
        $("#icon").addClass("fa fa-user");

        $("#tar").removeClass("caret hide");
        $("#tar").addClass("caret");

        $("#down").removeClass("dropdown-menu hide");
        $("#down").addClass("dropdown-menu");
        console.log(localStorage.userName);
    }

    localStorage.latestDate="2016-09-30";


})
function welcome(){
    console.log("welcome");
    if(localStorage.userName==""){
        localStorage.userName="";
        window.location.href="../welcome.html";
    }else{
        window.location.href="../welcome.html";
    }
}
function Logout(){
    localStorage.userName="";
    window.location.href="../welcome.html";
}

function goToAPIPage(){
    window.location.href="apiPage/api.html";
}


