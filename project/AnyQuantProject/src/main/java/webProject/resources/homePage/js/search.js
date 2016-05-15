/**
 * Created by QiHan on 2016/5/14.
 */
function toSingleStockPage() {
    var parm1=document.getElementById("stockID").value;
    localStorage.singleStockID=parm1;
    window.location.href="../singleStockUI/kLine.html";
}