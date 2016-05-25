require.config({
    paths: {
        echarts: '../homePage/js/echart'
    }

});

var element;
var $scope;

var chart3Type="BIAS";
var kchartData;
var axisData=[];
var chart1Data=[];
var chart2Data=[];
var chart3Data=[];
var chart3Data_BIAS6=[];
var chart3Data_BIAS12=[];
var chart3Data_BIAS24=[];

var chart3Data_RSI6 =[];
var chart3Data_RSI12 =[];
var chart3Data_RSI24 =[];

var chart3Data_K=[];
var chart3Data_D=[];
var chart3Data_J=[];
var chart3Data_DEA=[];
var chart3Data_DIF=[];
var chart3Data_MACD=[];
var chart3Data_PMA5=[];
var chart3Data_PMA10=[];
var chart3Data_PMA30=[];

var myChart;
var myChart2;
var myChart3;
var myChart4;
var myChart5;


var chartLine1;
var chartLine2;
var chartLine3;
var chartLine1_Type;
var chartLine2_Type;
var chartLine3_Type;
var chartLine1_Data;
var chartLine2_Data;
var chartLine3_Data;
var echarts;

function checkBoxChanged(){
    lineName=document.forms[0].chart3Type;
    for(i=0;i<=3;i++){
       if(lineName[i].checked) {
           chart3Type=lineName.value;
       }
    }
    var newOption = myChart3.getOption(); // 深拷贝

    switch (chart3Type){
        case 'RSI':
            newOption.series[0].data=chart3Data_RSI6;
            newOption.series[1].data=chart3Data_RSI12;
            newOption.series[2].data=chart3Data_RSI24;
            newOption.series[0].name="RSI6";
            newOption.series[1].name="RSI12";
            newOption.series[2].name="RSI24";
            newOption.series[0].type="line";
            newOption.series[1].type="line";
            newOption.series[2].type="line";
            newOption.legend.data=["RSI6","RSI12","RSI24"];
            break;
        case 'BIAS':
            newOption.series[0].data=chart3Data_BIAS6;
            newOption.series[1].data=chart3Data_BIAS12;
            newOption.series[2].data=chart3Data_BIAS24;
            newOption.series[0].name="BIAS6";
            newOption.series[1].name="BIAS12";
            newOption.series[2].name="BIAS24";
            newOption.series[0].type="line";
            newOption.series[1].type="line";
            newOption.series[2].type="line";
            newOption.legend.data=["BIAS6","BIAS12","BIAS24"];
            break;
        case 'MACD':
            newOption.series[0].data=chart3Data_DIF;
            newOption.series[1].data=chart3Data_DEA;
            newOption.series[2].data=chart3Data_MACD;
            newOption.series[0].name="DIF";
            newOption.series[1].name="DEA";
            newOption.series[2].name="MACD";
            newOption.series[0].type="line";
            newOption.series[1].type="line";
            newOption.series[2].type="bar";
            newOption.legend.data=["DIF","DEA","MACD"];
            break;
        case 'KDJ':
            newOption.series[0].data=chart3Data_K;
            newOption.series[1].data=chart3Data_D;
            newOption.series[2].data=chart3Data_J;
            newOption.series[0].name="K";
            newOption.series[1].name="D";
            newOption.series[2].name="J";
            newOption.series[0].type="line";
            newOption.series[1].type="line";
            newOption.series[2].type="line";
            newOption.legend.data=["K","D","J"];
            break;

    }
    myChart3.setOption(newOption,true);

}


function multiPredictTriggered(){//假定是kdj和rsi
    var KType=['RSI','KDJ','',''];
    var shortLine=[];
    var longLine=[];
    var stasticsNum=0;

    for(var item=0;item<4;item++){
    switch (KType[item]){//shortLine现在用来存储四个数组
        case 'RSI':
            shortLine[stasticsNum]=chart3Data_RSI6;
            longLine[stasticsNum++]=chart3Data_RSI12;
            break;
        case 'BIAS':
            shortLine[stasticsNum]=chart3Data_BIAS6;
            longLine[stasticsNum++]=chart3Data_BIAS12;
            break;
        case 'MACD':
            shortLine[stasticsNum]=chart3Data_DIF;
            longLine[stasticsNum++]=chart3Data_MACD;
            break;
        case 'KDJ':
            shortLine[stasticsNum]=chart3Data_K;
            longLine[stasticsNum++]=chart3Data_D;
            break;

    }
    }

    // console.log(shortLine);
    // console.log(longLine);

    //每次买卖行为至少100股
    var account=10000;
    var accountRange=[];
    var price=chart1Data;
    var dealBehave=[];//记录买入,买入价格,买入价格,卖出价格,剩余资金
    var cnt1=0;
    var cnt2=0;
    var buyPoint=[];
    var sellPoint=[];
    var stockNum=0;
    // for(item in axisData){//每一天生成一套数组
    var item=0;
    console.log(axisData.length);
    for(;item<axisData.length;item++){
        console.log("!!!!!!");
        accountRange[item]=account;
        dealBehave[item]=[false,0,0,false,0,0,account];//买入,买入价格,买入指标,卖出,卖出价格,卖出指标序号(对应于KType数组),帐户剩余
        buyPoint[item]=[axisData[item],'无交易','无指标'];
        sellPoint[item]=[axisData[item],'无交易','无指标'];

        var buyflag=false;
        var sellflag=false;
        for(var i=0;i<stasticsNum;i++){//至少有一个是买入交叉条件

            if ((shortLine[i][item] < longLine[i][item])&&(shortLine[i][item + 1] > longLine[i][item + 1])){
                buyflag=true;
                buyPoint[item]=[axisData[item],price[item], KType[i]];
                dealBehave[item]=[true,price[item], KType[i],false,0,0,account];
                break;
            }
        }
        for(var i=0;i<stasticsNum;i++){//存在至少一个卖出条件同时成立
            if ((shortLine[i][item] > longLine[i][item])&&(shortLine[i][item + 1] < longLine[i][item + 1])){
                buyPoint[item]=[axisData[item],'无交易','无指标'];
                buyflag=false;
                dealBehave[item]=[false,0,0,false,0,0,account];
                break;
            }
        }
        if(buyflag==false){
            for(var i=0;i<stasticsNum;i++){//存在至少一个卖出条件同时成立
                if ((shortLine[i][item] > longLine[i][item])&&(shortLine[i][item + 1] < longLine[i][item + 1])){
                 if(stockNum>0) {
                    sellflag=true;
                     sellPoint[item]=[axisData[item], price[item], KType[i]];
                    dealBehave[item] = [false, 0, 0, true, price[item], KType[i], account];
                }
                break;
            }
        }
        }
        if (buyflag) {//K线突破D
            account = account - price[item] * 100;
            // buyPoint[item]=[axisData[item],price[item]];
            stockNum+=100;
            cnt1++;
        }
        if(sellflag){
            if(stockNum>0) {
                account = account + price[item] * stockNum;
                // sellPoint[item]=[axisData[item], price[item]];
                stockNum-=stockNum;
                cnt2++;
            }
        }
        if(item==shortLine.length-1&&stockNum>0){
            account = account + price[item] * stockNum;
            sellPoint[item]=[axisData[item],price[item],''];
            stockNum-=stockNum;
            accountRange[item]=account;
        }
    }

    //进行买入卖出指标的统计
    //var KType=['RSI','KDJ','',''];
    var fullStastics=['RSI','KDJ','BIAS','MACD'];
    var buyCnt=[0,0,0,0];
    var sellCnt=[0,0,0,0];
    for(var i=0;i<axisData.length;i++){
     switch(buyPoint[i][2]){
         case 'RSI':
             buyCnt[0]=buyCnt[0]+1;
             break;
         case 'KDJ':
             buyCnt[1]=buyCnt[1]+1;
             break;
         case 'BIAS':
             buyCnt[2]=buyCnt[2]+1;
             break;
         case 'MACD':
             buyCnt[3]=buyCnt[3]+1;
             break;
     }
        switch(sellPoint[i][2]){
            case 'RSI':
                sellCnt[0]=sellCnt[0]+1;
                break;
            case 'KDJ':
                sellCnt[1]=sellCnt[1]+1;
                break;
            case 'BIAS':
                sellCnt[2]=sellCnt[2]+1;
                break;
            case 'MACD':
                sellCnt[3]=sellCnt[3]+1;
                break;
        }

    }


    var newOption = myChart.getOption(); // 深拷贝
    myChart = echarts.init(document.getElementById('main1'),'macarons');
    var itemStyle = {
        normal: {
            opacity: 0.8,
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowOffsetY: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
    };
    var newSeries1={
        name: '买入点',
        type: 'scatter',
        itemStyle: {
            normal: {
                color: '#f4e925',
                borderWidth:5,
            }
        },
        data: buyPoint
    };
    var newSeries2={
        name: '卖出点',
        type: 'scatter',
        itemStyle: {
            normal: {
                color: '#9393FF',
                borderWidth:5,
            }
        },
        data: sellPoint

    };
    // newOption.series[0]=newSeries;
    newOption.series=[newOption.series[0],newSeries1,newSeries2];
    myChart.setOption(newOption);

    myChart2 = echarts.init(document.getElementById('main2'),'macarons');
    var option2 = {
        tooltip: {
            trigger: 'axis',
            showDelay: 0             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
        },
        legend: {
            data: ['虚拟资本变动情况'],
            x: 100,
            y: 20
        },
        grid: {
            x: 80,
            y: 40,
            x2: 20,
            y2: 50
        },
        xAxis: [
            {
                type: 'category',
                position: 'bottom',
                boundaryGap: true,
                axisTick: {onGap: false},
                splitLine: {show: false},
                data: axisData,
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: '#000000',
                    }
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                scale: true,
                splitNumber: 3,
                boundaryGap: [0.05, 0.05],
                axisLabel: {
                    formatter: function (v) {
                        return Math.round(v / 10000) + ' 万'
                    }
                },
                splitArea: {show: true},
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: '#000000',
                    }
                }
            }
        ],
        series: [
            {
                name: '虚拟资本变动情况',
                type: 'line',
                symbol: 'none',
                data: accountRange,
                markLine: {
                    symbol: 'none',
                    itemStyle: {
                        normal: {
                            color: '#000000',
                            label: {
                                show: false
                            }
                        }
                    },
                    data: [
                        {type: 'max',
                            name: '最大'}
                    ]
                },
                itemStyle: {normal: {color:'#0072E3', label:{show:true}}},//'#0072E3'
            },
        ]
    };
    myChart2.setOption(option2,true);


    myChart4 = echarts.init(document.getElementById('main4'),'macarons');
    myChart5 = echarts.init(document.getElementById('main5'),'infographic');

    var option4 = {
        title : {
            text: '　买入指标分布',
            x:'center',
            y:'top'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 0,
            y: 100,
            data: fullStastics
        },
        series : [
            {
                name: '买入指标分布',
                type: 'pie',
                radius : '55%',
                center: ['55%', '55%'],
                data:[
                    {value:buyCnt[0], name:fullStastics[0]},
                    {value:buyCnt[1], name:fullStastics[1]},
                    {value:buyCnt[2], name:fullStastics[2]},
                    {value:buyCnt[3], name:fullStastics[3]}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    myChart4.setOption(option4);
    setTimeout(function () {
        window.onresize = function () {
            myChart4.resize();
        }
    }, 200);

    var option5 = {
        title : {
            text: '　卖出指标分布',
            x:'center',
            y:'top'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 0,
            y: 100,
            data: fullStastics
        },
        series : [
            {
                name: '卖出指标分布',
                type: 'pie',
                radius : '55%',
                center: ['55%', '55%'],
                data:[
                    {value:sellCnt[0], name:fullStastics[0]},
                    {value:sellCnt[1], name:fullStastics[1]},
                    {value:sellCnt[2], name:fullStastics[2]},
                    {value:sellCnt[3], name:fullStastics[3]}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    myChart5.setOption(option5);
    setTimeout(function () {
        window.onresize = function () {
            myChart5.resize();
        }
    }, 200);
}

function predictTriggered(KType){
    var shortLine;
    var longLine;

    switch (KType){
        case 'RSI':
            shortLine=chart3Data_RSI6;
            longLine=chart3Data_RSI12;
            break;
        case 'BIAS':
            shortLine=chart3Data_BIAS6;
            longLine=chart3Data_BIAS12;
            break;
        case 'MACD':
            shortLine=chart3Data_DIF;
            longLine=chart3Data_MACD;
            break;
        case 'KDJ':
            shortLine=chart3Data_K;
            longLine=chart3Data_D;
            break;

    }
    //每次买卖行为至少100股
    var account=10000;
    var accountRange=[];
    var price=chart1Data;
    // var shortLine=chart3Data_K;
    // var longLine=chart3Data_D;
    var dealBehave=[];//记录买入,买入价格,买入价格,卖出价格,剩余资金
    var cnt1=0;
    var cnt2=0;
    var buyPoint=[];
    var sellPoint=[];
    var stockNum=0;
    // for(item in axisData){//每一天生成一套数组
    var item=0;
    for(;item<shortLine.length;item++){
        accountRange[item]=account;
        dealBehave[item]=[false,0,false,0,0,account];
        buyPoint[item]=[axisData[item],'无交易'];
        sellPoint[item]=[axisData[item],'无交易'];
        var num1=shortLine[item];
        num1=num1.substr(0,num1.indexOf("."));
        var num2=longLine[item];
        num2=num2.substr(0,num2.indexOf("."));
          //买入100股
                if ((shortLine[item] < longLine[item])&&(shortLine[item + 1] > longLine[item + 1])) {//K线突破D
                    account = account - price[item] * 100;
                    dealBehave[item] = [true, price[item], false, 0, 0, account];
                    // buyPoint[cnt1++]=[axisData[item],price[item]];
                    buyPoint[item]=[axisData[item],price[item]];
                    stockNum+=100;
                    cnt1++;
                } else if((shortLine[item] >longLine[item])&&(shortLine[item + 1] <longLine[item + 1])){
                   if(stockNum>0) {
                       account = account + price[item] * stockNum;
                       dealBehave[item] = [false, 0, true, price[item], 0, account];
                       // sellPoint[cnt2++]=[axisData[item], price[item]];
                       sellPoint[item]=[axisData[item], price[item]];
                       stockNum-=stockNum;
                       cnt2++;
                   }
            }
        if(item==shortLine.length-1&&stockNum>0){
            account = account + price[item] * stockNum;
            sellPoint[cnt2++]=[axisData[item],price[item]];
            stockNum-=stockNum;
            accountRange[item]=account;
        }
    }

    console.log(accountRange);
    console.log(axisData);
    //新建数组,为[日期,收盘价]

    var newOption = myChart.getOption(); // 深拷贝
    myChart = echarts.init(document.getElementById('main1'),'macarons');
    var itemStyle = {
        normal: {
            opacity: 0.8,
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowOffsetY: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
    };
    var newSeries1={
        name: '买入点',
        type: 'scatter',
        itemStyle: {
            normal: {
                color: '#f4e925',
                borderWidth:5,
            }
        },
        data: buyPoint
    };
    var newSeries2={
        name: '卖出点',
        type: 'scatter',
        itemStyle: {
            normal: {
                color: '#9393FF',
                borderWidth:5,
            }
        },
        data: sellPoint

    };
    // newOption.series[0]=newSeries;
    newOption.series=[newOption.series[0],newSeries1,newSeries2];
    myChart.setOption(newOption);

    myChart2 = echarts.init(document.getElementById('main2'),'macarons');
    var option2 = {
        tooltip: {
            trigger: 'axis',
            showDelay: 0             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
        },
        legend: {
            data: ['虚拟资本变动情况'],
            x: 100,
            y: 20
        },
        grid: {
            x: 80,
            y: 40,
            x2: 20,
            y2: 50
        },
        xAxis: [
            {
                type: 'category',
                position: 'bottom',
                boundaryGap: true,
                axisTick: {onGap: false},
                splitLine: {show: false},
                data: axisData,
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: '#000000',
                    }
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                scale: true,
                splitNumber: 3,
                boundaryGap: [0.05, 0.05],
                axisLabel: {
                    formatter: function (v) {
                        return Math.round(v / 10000) + ' 万'
                    }
                },
                splitArea: {show: true},
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: '#000000',
                    }
                }
            }
        ],
        series: [
            {
                name: '虚拟资本',
                type: 'line',
                symbol: 'none',
                data: accountRange,
                markLine: {
                    symbol: 'none',
                    itemStyle: {
                        normal: {
                            color: '#000000',
                            label: {
                                show: false
                            }
                        }
                    },
                    data: [
                        {type: 'max',
                            name: '最大'}
                    ]
                },
                itemStyle: {normal: {color:'#0072E3', label:{show:true}}},//'#0072E3'
            },
        ]
    };
    myChart2.setOption(option2,true);


    myChart4 = echarts.init(document.getElementById('main4'),'macarons');

    var option4 = {
        title : {
            text: '买卖情况统计',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['买入次数','卖出次数']
        },
        series : [
            {
                name: '买卖统计',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:cnt1, name:'买入次数'},
                    {value:cnt2, name:'卖出次数'},
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    myChart4.setOption(option4);
    setTimeout(function () {
        window.onresize = function () {
            myChart4.resize();
        }
    }, 200);
}

// 使用
require(
    [
        'echarts',
        'echarts/chart/k', // 使用柱状图就加载bar模块，按需加载
        'echarts/chart/bar',
        'echarts/chart/line',
        'echarts/theme/macarons',
        'echarts/theme/infographic',
        'echarts/chart/scatter',
        'echarts/chart/pie',

    ],
    function (ec) {
        echarts=ec;
        newStock();
    }
);

function newStock() {
    /*使用外部的js调用angularjs控制器中的方法!!!!!!!!!!!*/
    element=angular.element(document.getElementById("main1"));
    //var $scope = element.scope().$$childTail;
    $scope = element.scope();

    setTimeout(function (){
        kchartData=$scope.dayKLineResult;
        for(var item in kchartData){
            axisData[item] = kchartData[item].date;
            chart1Data[item] = kchartData[item].close;
            chart2Data[item] = kchartData[item].volumn;
            chart3Data_RSI6[item] = kchartData[item].RSI6;
            chart3Data_RSI12[item] = kchartData[item].RSI12;
            chart3Data_RSI24[item] = kchartData[item].RSI24;

            chart3Data_BIAS6[item] = kchartData[item].BIAS6;
            chart3Data_BIAS12[item] = kchartData[item].BIAS12;
            chart3Data_BIAS24[item] = kchartData[item].BIAS24;
            chart3Data_K[item] = kchartData[item].K;
            chart3Data_D[item] = kchartData[item].D;
            chart3Data_J[item] = kchartData[item].J;
            chart3Data_DEA[item] = kchartData[item].DEA;
            chart3Data_DIF[item] = kchartData[item].DIF;
            chart3Data_MACD[item] = kchartData[item].MACDBar;
            chart3Data_PMA5 = kchartData[item].PMA5_day;
            chart3Data_PMA10 = kchartData[item].PMA10_day;
            chart3Data_PMA30 = kchartData[item].PMA30_day;
        }
        //chart3Type="BIAS";
        chartLine1="BIAS6";
        chartLine2="BIAS12";
        chartLine3="BIAS24";
        chartLine1_Type='line';
        chartLine2_Type='line';
        chartLine3_Type='line';
        chartLine1_Data=chart3Data_BIAS6;
        chartLine2_Data=chart3Data_BIAS12;
        chartLine3_Data=chart3Data_BIAS24;

        myChart = echarts.init(document.getElementById('main1'),'macarons');
        // myChart2 = ec.init(document.getElementById('main2'),'macarons');
        myChart3 = echarts.init(document.getElementById('main3'),'macarons');

        var option = {
            title: {
                //text: '2016年腾讯科技',
                textStyle: {
                    color: '#000000',
                    fontSize: 20,
                }
            },
            toolbox: {
                show : true,
                feature : {
                    dataZoom : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            tooltip: {
                trigger: 'axis',
                showDelay: 0,             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
            },
            color:['#00448a','#484891'],
            legend: {
                data: ['收盘价','统计指标'],
                textStyle: {
                    color: '#000000',
                },
                x: 'center',               // 水平安放位置，默认为全图居中，可选为：
                // ¦ {number}（x坐标，单位px）
                y: 'top',

            },
            //dataZoom: {
            //    y: 250,
            //    show: true,
            //    realtime: true,
            //    start: 50,
            //    end: 100
            //},
            grid: {
                x: 80,
                y: 40,
                x2: 20,
                y2: 25
            },
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: true,
                    axisTick: {onGap: false},
                    splitLine: {show: false},
                    data: axisData,
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: '#000000',
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    scale: true,
                    boundaryGap: [0.05, 0.05],
                    splitArea: {show: true},
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: '#000000',
                        }
                    }
                }
            ],
            series: [
                {
                    name: '收盘价',
                    type: 'line',
                    symbol: 'none',
                    data:  chart1Data,
                    itemStyle: {normal: {color:'#ae0000', label:{show:false}}}
                },
                {
                    name: '统计指标',
                    type: 'line', data: []

                }

            ]
        };

        var option3 = {
            tooltip: {
                trigger: 'axis',
                showDelay: 0             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
            },
            legend: {
                data: [ chartLine1,  chartLine2,  chartLine3],
                x: 100,
                y: 20
            },
            dataZoom: {
                y: 200,
                show: true,
                realtime: true,
                start: 50,
                end: 100
            },
            grid: {
                x: 80,
                y: 5,
                x2: 20,
                y2: 30
            },
            xAxis: [
                {
                    type: 'category',
                    position: 'bottom',
                    boundaryGap: true,
                    axisTick: {onGap: false},
                    splitLine: {show: false},
                    data: axisData,
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: '#000000',
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    scale: true,
                    splitNumber: 3,
                    boundaryGap: [0.05, 0.05],
                    axisLabel: {
                        formatter: function (v) {
                            return Math.round(v / 10000) + ' 万'
                        }
                    },
                    splitArea: {show: true},
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: '#000000',
                        }
                    }
                }
            ],
            series: [
                {
                    name: chartLine1,
                    type: chartLine1_Type,
                    symbol: 'none',
                    data: chartLine1_Data,
                    markLine: {
                        symbol: 'none',
                        itemStyle: {
                            normal: {
                                color: '#000000',
                                label: {
                                    show: false
                                }
                            }
                        },
                        data: [
                            {type: 'average', name: '平均值'}
                        ]
                    },
                    itemStyle: {normal: {color:'#0072E3', label:{show:true}}},//'#0072E3'
                },
                {
                    name: chartLine2,
                    type: chartLine1_Type,
                    symbol: 'none',
                    data: chartLine2_Data,
                    markLine: {
                        symbol: 'none',
                        itemStyle: {
                            normal: {
                                color: '#000000',
                                label: {
                                    show: false
                                }
                            }
                        },
                        data: [
                            {type: 'average', name: '平均值'}
                        ]
                    },
                    itemStyle: {normal: {color:'#FF60AF', label:{show:true}}},
                },
                {
                    name: chartLine3,
                    type: chartLine1_Type,
                    symbol: 'none',
                    data: chartLine3_Data,
                    markLine: {
                        symbol: 'none',
                        itemStyle: {
                            normal: {
                                color: '#000000',
                                label: {
                                    show: false
                                }
                            }
                        },
                        data: [
                            {type: 'average', name: '平均值'}
                        ]
                    },
                    itemStyle: {normal: {color:'#484891', label:{show:false}}},//
                }
            ]
        };

        myChart.connect(myChart3);
        myChart3.setOption(option3);
        myChart.setOption(option);
        // 为echarts对象加载数据
        // myChart2.connect([myChart, myChart3]);
        myChart3.connect(myChart);

        setTimeout(function () {
            window.onresize = function () {
                myChart.resize();
                // myChart2.resize();
                myChart3.resize();
            }
        }, 200);
    },1000);
}
