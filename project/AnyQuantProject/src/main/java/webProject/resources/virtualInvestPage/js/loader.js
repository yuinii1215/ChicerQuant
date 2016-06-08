window.onload=function() {
    // var pop=document.getElementById("pop");
    // $("#pop").css({ "width": "100%", "height": $(document).height() }
    // var curtain=document.getElementById("save_con");
    var save_container=document.getElementById("save_con");
    var save_button2=document.getElementById("saveCustomerStrBtn");
    var save_button1=document.getElementById("saveCrossStrBtn");
    var share_button2=document.getElementById("shareCustomerStrBtn");
    var share_button1=document.getElementById("shareCrossStrBtn");
    var back_button=document.getElementById("back_button");
    var confirm_button=document.getElementById("confirm_button");
    var saveConfirmBtn=document.getElementById("signInUp");
    var save_container=document.getElementById("save_con");
    var changeText=document.getElementById("strType");

    var textContent="";
    var chartflag=0;

    save_button1.onclick=function() {//保存交叉线策略,填写策略内容

        KType=['','','',''];
        chartflag=1;
        // save_container.style.display="block";
        textContent="";

        var selectContent=document.getElementById("selecter").options;
        var cnt=0;

        for(i=0;i<=3;i++){
            if(selectContent[i].selected) {
                KType[cnt++]=selectContent[i].value;
                if(cnt>1) {  textContent+=","; }
                textContent += selectContent[i].value + "交叉线";
            }
        }
        changeText.placeholder=textContent;
        show2();
        // $('#save_con').fadeIn();
    }
    save_button2.onclick=function() {
        chartflag=2;
        var temp=[];
        temp[0]=document.getElementById("pt_bias");
        temp[1]=document.getElementById("pt_rsi");
        temp[2]=document.getElementById("pt_kdj");
        temp[3]=document.getElementById("pt_macd");
        textContent="";
        save_container.style.display="block";
        for(var i=0;i<4;i++){
            if(temp[i].checked){
                newType=temp[i].value;
            }}
        buyStd=document.getElementById("buyStd").value;
        sellStd=document.getElementById("sellStd").value;
        textContent="指标:"+newType+",买入标准:"+buyStd+",卖出标准:"+sellStd;
        changeText.placeholder=textContent;
        show2();
        // $('#save_con').fadeIn();
    }
    share_button1.onclick=function() {
         var str_Name=document.getElementById("strName").value;
         KType=['','','',''];
        chartflag=1;
        var selectContent=document.getElementById("selecter").options;
        var cnt=0;

        for(i=0;i<=3;i++){
            if(selectContent[i].selected) {
                KType[cnt++]=selectContent[i].value;
            }
        }
         $scope.shareCrossStr(str_Name);
    }

    share_button2.onclick=function() {
        var str_Name=document.getElementById("strName").value;

        chartflag=2;
        var temp=[];
        temp[0]=document.getElementById("pt_bias");
        temp[1]=document.getElementById("pt_rsi");
        temp[2]=document.getElementById("pt_kdj");
        temp[3]=document.getElementById("pt_macd");
        
        for(var i=0;i<4;i++){
            if(temp[i].checked){
                newType=temp[i].value;
            }}
        buyStd=document.getElementById("buyStd").value;
        sellStd=document.getElementById("sellStd").value;


        $scope.shareCustomerStr(str_Name);

    }


    confirm_button.onclick=function() {//保存交叉线策略,填写策略内容
        var str_Name=document.getElementById("strName").value;
       if(chartflag==1){
           $scope.saveCrossStr(str_Name);
           $scope.showAllStr();
       }else if(chartflag==2){
           $scope.saveCustomerStr(str_Name);
           $scope.showAllStr();
       }
    }
}



