/**
 * Created by QiHan on 2016/7/1.
 */
window.onload=function() {
    var  aboutUS=document.getElementById("aboutUS");
    var  infoDown=document.getElementById("infoDown");
    var  info=document.getElementById("info");

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