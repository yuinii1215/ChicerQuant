require.config({
    paths: {
        echarts:  '../js/echart'
    }

});
var element;
var $scope;
var array;

var chart3Type="BIAS";
var kchartData;
var axisData=[];
var chart1Data=[];
var chart2Data=[];
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

function checkBoxChanged_MONTH(){
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

require(
    [
        'echarts',
        'echarts/chart/bar',
        'echarts/chart/line',
        'echarts/chart/k',
        'echarts/theme/macarons',

    ],
    function (ec) {
        /*使用外部的js调用angularjs控制器中的方法!!!!!!!!!!!*/
        element=angular.element(document.getElementById("main1"));
        //var $scope = element.scope().$$childTail;
         $scope = element.scope();
        //var $scope = element.module
        array=new Array();
        var length=0;

        setTimeout(function(){
            kchartData= $scope.monthKLineResult;
            for(var item in kchartData) {
                length++;
            }

            for(var item in kchartData){
                axisData[item]=kchartData[item].date;
                chart1Data[item]=[kchartData[item].open,kchartData[item].close,kchartData[item].low,kchartData[item].high];
                var v=kchartData[item].volumn/ 100000000;
                chart2Data[item]= v.toFixed(4);
                chart3Data_RSI6[item]=kchartData[item].RSI6;
                chart3Data_RSI12[item]=kchartData[item].RSI12;
                chart3Data_RSI24[item]=kchartData[item].RSI24;
                chart3Data_BIAS6[item]=kchartData[item].BIAS6;
                chart3Data_BIAS12[item]=kchartData[item].BIAS12;
                chart3Data_BIAS24[item]=kchartData[item].BIAS24;
                chart3Data_K[item]=kchartData[item].K;
                chart3Data_D[item]=kchartData[item].D;
                chart3Data_J[item]=kchartData[item].J;
                chart3Data_DEA[item]=kchartData[item].DEA;
                chart3Data_DIF[item]=kchartData[item].DIF;
                chart3Data_MACD[item]=kchartData[item].MACDBar;
                chart3Data_PMA5=kchartData[item].PMA5_day;
                chart3Data_PMA10=kchartData[item].PMA10_day;
                chart3Data_PMA30=kchartData[item].PMA30_day;

                if (item < length-1) {
                    array[item] = new Array();

                    array[item][0] = kchartData[item].date;
                    array[item][1] = kchartData[item].open*1.0;
                    array[item][2] = kchartData[item].close*1.0;
                    array[item][3] = kchartData[item].low*1.0;
                    array[item][4] = kchartData[item].high*1.0;
                }
            }

            chartLine1="BIAS6";
            chartLine2="BIAS12";
            chartLine3="BIAS24";
            chartLine1_Type='line';
            chartLine2_Type='line';
            chartLine3_Type='line';
            chartLine1_Data=chart3Data_BIAS6;
            chartLine2_Data=chart3Data_BIAS12;
            chartLine3_Data=chart3Data_BIAS24;

            var data0 = splitData(array);
            console.log(data0);

            function splitData(rawData) {
                var categoryData = [];
                var values = []
                for (var i = 0; i < rawData.length; i++) {
                    categoryData.push(rawData[i].splice(0, 1)[0]);
                    values.push(rawData[i])
                }
                return {
                    categoryData: categoryData,
                    values: values
                };
            }
            function calculateMA(dayCount) {
                var result = [];
                for (var i = 0, len = data0.values.length; i < len; i++) {
                    if (i < dayCount) {
                        result.push('-');
                        continue;
                    }
                    var sum = 0;
                    for (var j = 0; j < dayCount; j++) {
                        sum += data0.values[i - j][1];
                    }
                    result.push(sum / dayCount);
                }
                return result;
            }

            var myChart = ec.init(document.getElementById('main1'),'macarons');
            var myChart2 = ec.init(document.getElementById('main2'),'macarons');
            var myChart3 = ec.init(document.getElementById('main3'),'macarons');

            var option = {
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
                    axisPointer: {
                        type: 'line'
                    }
                },
                legend: {
                    left: 0 ,
                    data: ['月K', 'MA5', 'MA10', 'MA20', 'MA30'],
                },
                grid: {
                    bottom: '5%'
                },
                xAxis: {
                    type: 'category',
                    data: data0.categoryData,
                    scale: true,
                    boundaryGap : false,
                    axisLine: {onZero: false},
                    splitLine: {show: false},
                    splitNumber: 20,
                    min: 'dataMin',
                    max: 'dataMax'
                },
                yAxis: {
                    scale: true,
                    splitArea: {
                        show: true
                    }
                },
                dataZoom: [
                    {
                        type: 'inside',
                        start: 50,
                        end: 100
                    },
                    {
                        show: true,
                        type: 'slider',
                        y: '90%',
                        start: 50,
                        end: 100
                    }
                ],
                series: [
                    {
                        name: '月K',
                        type: 'k',
                        data: data0.values,
                    },
                    {
                        name: 'MA5',
                        type: 'line',
                        data: calculateMA(5),
                        smooth: true,
                        lineStyle: {
                            normal: {opacity: 0.5}
                        }
                    },
                    {
                        name: 'MA10',
                        type: 'line',
                        data: calculateMA(10),
                        smooth: true,
                        lineStyle: {
                            normal: {opacity: 0.5}
                        }
                    },
                    {
                        name: 'MA20',
                        type: 'line',
                        data: calculateMA(20),
                        smooth: true,
                        lineStyle: {
                            normal: {opacity: 0.5}
                        }
                    },
                    {
                        name: 'MA30',
                        type: 'line',
                        data: calculateMA(30),
                        smooth: true,
                        lineStyle: {
                            normal: {opacity: 0.5}
                        }
                    },

                ]
            };
            myChart.setOption(option,true);
            var option2 = {
                tooltip: {
                    trigger: 'axis',
                    showDelay: 0             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
                },
                legend: {
                    y: -30,
                    data: ['K线', '成交量(亿)', '统计指标']
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
                    height:30,
                    start: 50,
                    end: 100,
                    top:'5%',
                },
                grid: {
                    y: 2,
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
                                return Math.round(v) + ' 亿'
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
                        name: '成交量(亿)',
                        type: 'line',
                        symbol: 'none',
                        data: chart2Data,


                        markLine: {
                            symbol: 'none',
                            itemStyle: {
                                normal: {
                                    color: '#484891',
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
                    y:-30,
                    data: [ chartLine1,  chartLine2,  chartLine3],
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


            myChart3.setOption(option3,true);
            myChart.connect([myChart2, myChart3]);
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


        },1000);



    });
