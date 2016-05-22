require.config({
    paths: {
        echarts: '../homePage/js/echart'
    }

});

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
        var element=angular.element(document.getElementById("main1"));
        //var $scope = element.scope().$$childTail;
        var $scope = element.scope();
        //var $scope = element.module
        var kchartData=[];
        var axisData=[];
        var chart1Data=[];
        var chart2Data=[];
        var array=new Array();
        var length=0;
        $scope.benchMarkInfo=[];

        setTimeout(function(){
            kchartData= $scope.benchMarkInfo;
            for(var item in $scope.benchMarkInfo) {
                length++;
            }

            for(var item in kchartData){
                axisData[item]=kchartData[item].date;
                chart1Data[item]=[kchartData[item].open,kchartData[item].close,kchartData[item].low,kchartData[item].high];
                var v=kchartData[item].volumn/ 100000000;
                chart2Data[item]= v.toFixed(4);

                if (item < length-1) {
                    array[item] = new Array();

                    array[item][0] = kchartData[item].date;
                    array[item][1] = kchartData[item].open*1.0;
                    array[item][2] = kchartData[item].close*1.0;
                    array[item][3] = kchartData[item].low*1.0;
                    array[item][4] = kchartData[item].high*1.0;
                }
            }

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
                    data: ['日K', 'MA5', 'MA10', 'MA20', 'MA30'],
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
                        name: '日K',
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
            myChart.setOption(option);
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
            myChart2.setOption(option2);

            var option3 = {
                tooltip: {
                    trigger: 'axis',
                    showDelay: 0             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
                },
                //legend: {
                //    y: -30,
                //    data: ['上证指数', '成交金额(万)', '成交量(亿)']
                //},
                toolbox: {
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
                    y: 200,
                    show: true,
                    realtime: true,
                    start: 50,
                    end: 100
                },
                grid: {
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
                            formatter: function (params) {
                                return Math.round(params) + ' 亿'
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
                        name: '成交量（亿）',
                        type: 'bar',
                        symbol: 'none',
                        data:  chart2Data,
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
                        itemStyle: {normal: {color:'#484891', label:{show:true}}},
                    }
                ]
            };

            myChart.connect([myChart2]);
      //      myChart3.setOption(option3);

            // 为echarts对象加载数据
            myChart2.connect([myChart]);
    //        myChart3.connect([myChart, myChart2]);

            setTimeout(function () {
                window.onresize = function () {
                    myChart.resize();
                    myChart2.resize();
       //             myChart3.resize();
                }
            }, 200);


        },1000);



    });
