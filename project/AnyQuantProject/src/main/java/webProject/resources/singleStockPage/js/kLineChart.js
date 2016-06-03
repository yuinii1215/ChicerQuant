require.config({
    paths: {
        echarts:  '../js/echart'
    }

});

var element;
var $scope;
var array;
var echarts;

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

var chartLine1;
var chartLine2;
var chartLine3;
var chartLine1_Type;
var chartLine2_Type;
var chartLine3_Type;
var chartLine1_Data;
var chartLine2_Data;
var chartLine3_Data;

function checkBoxChanged(){
    var lineName=document.forms[0].chart3Type;

    for(var i=0;i<=3;i++){
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
            newOption.series[0].data=chart3Data_MACD;
            newOption.series[1].data=chart3Data_DIF;
            newOption.series[2].data=chart3Data_DEA;
            newOption.series[0].name="MACD";
            newOption.series[1].name="DIF";
            newOption.series[2].name="DEA";
            newOption.series[0].type="bar";
            newOption.series[1].type="line";
            newOption.series[2].type="line";
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

var tabFlag;
function tabChanged(KType){
    var lineName=document.forms[0].chart3Type;
    lineName[0].checked=true;
    
    tabFlag=KType;

    document.forms[0].chart3Type="BIAS";

    var tabName;
    switch (KType){
        case 'day':
            tabName="日K";
            kchartData=$scope.dayKLineResult;
            break;
        case 'week':
            tabName="周K";
            kchartData=$scope.weekKLineResult;
            break;
        case 'month':
            tabName="月K";
            kchartData=$scope.monthKLineResult;
            break;
    }
    paint();
}


require(
    [
        'echarts',
        'echarts/chart/k', // 使用柱状图就加载bar模块，按需加载
        'echarts/chart/bar',
        'echarts/chart/line',
        'echarts/theme/macarons',
    ],
    function (ec) {
        /*使用外部的js调用angularjs控制器中的方法!!!!!!!!!!!*/
        echarts=ec;
        element = angular.element(document.getElementById("main1"));
        //var $scope = element.scope().$$childTail;
        $scope = element.scope();

        setTimeout(function(){
            kchartData=$scope.dayKLineResult;
            paint();
        },1000);
    });


function paint(){
    // kchartData=$scope.dayKLineResult;
    axisData=[];
    chart1Data=[];
    chart2Data=[];
    chart3Data_RSI6=[];
    chart3Data_RSI12=[];
    chart3Data_RSI24=[];
    chart3Data_BIAS6=[];
    chart3Data_BIAS12=[];
    chart3Data_BIAS24=[];
    chart3Data_K=[];
    chart3Data_D=[];
    chart3Data_J=[];
    chart3Data_DEA=[];
    chart3Data_DIF=[];
    chart3Data_MACD=[];

    var item1=0;
    var totalLength=0;
    for(var item in kchartData){
        totalLength++;
    }

    for(var item in kchartData) {
        if(item<totalLength-1){
            var noDealFlag = kchartData[item].open;
        if (noDealFlag!='0') {
            axisData[item1] = kchartData[item].date;
            chart1Data[item1] = [kchartData[item].open, kchartData[item].close, kchartData[item].low, kchartData[item].high];
            chart2Data[item1] = kchartData[item].volumn;
            chart3Data_RSI6[item1] = kchartData[item].RSI6;
            chart3Data_RSI12[item1] = kchartData[item].RSI12;
            chart3Data_RSI24[item1] = kchartData[item].RSI24;

            chart3Data_BIAS6[item1] = kchartData[item].BIAS6;
            chart3Data_BIAS12[item1] = kchartData[item].BIAS12;
            chart3Data_BIAS24[item1] = kchartData[item].BIAS24;
            chart3Data_K[item1] = kchartData[item].K;
            chart3Data_D[item1] = kchartData[item].D;
            chart3Data_J[item1] = kchartData[item].J;
            chart3Data_DEA[item1] = kchartData[item].DEA;
            chart3Data_DIF[item1] = kchartData[item].DIF;
            chart3Data_MACD[item1] = kchartData[item].MACDBar;
            chart3Data_PMA5[item1] = kchartData[item].PMA5_day;
            chart3Data_PMA10[item1] = kchartData[item].PMA10_day;
            chart3Data_PMA30[item1] = kchartData[item].PMA30_day;
            item1++;
        }
    }
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
    myChart2 = echarts.init(document.getElementById('main2'),'macarons');
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
                // dataZoom : {show: true},
                // dataView : {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore : {show: true},
                // saveAsImage : {show: true}
            },
            x: '40',               // 水平安放位置，默认为全图居中，可选为：
            // ¦ {number}（x坐标，单位px）
            y: 'top',
        },
        tooltip: {
            trigger: 'axis',
            showDelay: 0,             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
            formatter: function (params) {
                var res = params[0].name;
                res += '<br/>' + params[0].seriesName;
                res += '<br/>  开盘 : ' + params[0].value[0] + '  最高 : ' + params[0].value[3];
                res += '<br/>  收盘 : ' + params[0].value[1] + '  最低 : ' + params[0].value[2];
                return res;
            }
        },
        legend: {
            data: ['K线', '5日均线','10日均线','30日均线'],
            textStyle: {
                color: '#000000',
            },
            x: 'center',               // 水平安放位置，默认为全图居中，可选为：
            // ¦ {number}（x坐标，单位px）
            y: 'top',

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
        // color:['#5B5B00','#F9F900','#EAC100'],
        series: [
            {
                name: '5日均线',
                type: 'line',
                symbol: 'none',
                data: chart3Data_PMA5
            },
            {
                name: '10日均线',
                type: 'line',
                symbol: 'none',
                data: chart3Data_PMA10
            },
            {
                name: '30日均线',
                type: 'line',
                symbol: 'none',
                data: chart3Data_PMA30
            },
            {
                name: 'K线',
                type: 'k',
                data:  chart1Data,// 开盘，收盘，最低，最高
                itemStyle: {normal: {label:{show:false}}}
            }
        ]
    };
    var option2 = {
        tooltip: {
            trigger: 'axis',
            showDelay: 0             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
        },
        legend: {
            data: ['成交量(万)'],
            x: 'center',               // 水平安放位置，默认为全图居中，可选为：
            // ¦ {number}（x坐标，单位px）
            y: 'top',
        },
        toolbox: {
            y: -30,
            show: true,
            feature: {
                mark: {show: true},
                dataZoom: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        dataZoom: {
            show: true,
            realtime: true,
            start: 50,
            end: 100
        },
        xAxis: [
            {
                type: 'category',
                position: 'top',
                boundaryGap: true,
                axisLabel: {show: false},
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
        color: ['#AAB3FF'],
        series: [
            {
                name: '成交量(万)',
                type: 'bar',
                symbol: 'none',
                data: chart2Data,
                markLine: {
                    symbol: 'none',
                    itemStyle: {
                        normal: {
                            color: '#0072e3',
                            label: {
                                show: false
                            }
                        }
                    },
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            }
        ]
    };
    myChart2.setOption(option2,true);

    var option3 = {
        tooltip: {
            trigger: 'axis',
            showDelay: 0             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
        },
        legend: {
            data: [ chartLine1,  chartLine2,  chartLine3],
            x: 'center',
            y:20,
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
                itemStyle: {normal: {color:'#99CEFF', label:{show:false}}},//'#99CEFF'
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
                itemStyle: {normal: {color:'#0072E3', label:{show:false}}},//
            }
        ]
    };

    myChart.connect([myChart2,myChart3]);
    myChart3.setOption(option3,true);
    myChart.setOption(option,true);
    // 为echarts对象加载数据
    myChart2.connect([myChart, myChart3]);
    myChart3.connect([myChart, myChart2]);

    setTimeout(function () {
        window.onresize = function () {
            myChart.resize();
            myChart2.resize();
            myChart3.resize();
        }
    }, 200);
}