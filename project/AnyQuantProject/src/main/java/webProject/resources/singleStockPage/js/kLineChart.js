require.config({
    paths: {
        echarts: 'build/dist'
    }

});

// 使用
require(
    [
        'echarts',
        'echarts/chart/k', // 使用柱状图就加载bar模块，按需加载
        'echarts/chart/bar',
        'echarts/chart/line'
    ],
    function (ec) {
        var realstatus,realdata;
        /*使用外部的js调用angularjs控制器中的方法!!!!!!!!!!!*/
        var element=angular.element(document.getElementById("main1"));
        //var $scope = element.scope().$$childTail;
        var $scope = element.scope();
        //var $scope = element.module
        var STR=JSON.stringify($scope.ktest);
        var kchartData;
        var axisData=[];
        var chart1Data=[];
        var chart2Data=[];
        var kData=$scope.test;

        setTimeout(function(){
            kchartData=$scope.kLineResult;
            for(var item in kchartData){
                axisData[item]=kchartData[item].date;
                chart1Data[item]=[kchartData[item].open,kchartData[item].close,kchartData[item].low,kchartData[item].high];
                chart2Data[item]=kchartData[item].volumn;
            }

            var myChart = ec.init(document.getElementById('main1'));
            var myChart2 = ec.init(document.getElementById('main2'));
            var myChart3 = ec.init(document.getElementById('main3'));

            var option = {
                title: {
                    //text: '2016年腾讯科技',
                    textStyle: {
                        color: '#000000',
                        fontSize: 20,
                    }
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
                color:['#00448a','#0580b9','#484891'],
                legend: {
                    data: ['K线', '成交量(万)', '统计指标'],
                    textStyle: {
                        color: '#000000',
                    },
                    x: 'right',               // 水平安放位置，默认为全图居中，可选为：
                    // 'center' ¦ 'left' ¦ 'right'
                    // ¦ {number}（x坐标，单位px）
                    y: 'top',

                },
                dataZoom: {
                    y: 250,
                    show: true,
                    realtime: true,
                    start: 50,
                    end: 100
                },
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
                        name: 'K线',
                        type: 'k',
                        data:  chart1Data,// 开盘，收盘，最低，最高
                        itemStyle: {normal: {color:'#ae0000', label:{show:true}}}
                    },
                    {
                        name: '成交量(万)',
                        type: 'line',
                        symbol: 'none',
                        data: []
                    },
                    {
                        name: '统计指标',
                        type: 'bar', data: []

                    }

                ]
            };
            var option2 = {
                tooltip: {
                    trigger: 'axis',
                    showDelay: 0             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
                },
                legend: {
                    y: -30,
                    data: ['K线', '成交量(万)', '统计指标']
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
                grid: {
                    x: 80,
                    y: 5,
                    x2: 20,
                    y2: 40
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
                series: [
                    {
                        name: '成交量(万)',
                        type: 'line',
                        symbol: 'none',
                        data: chart2Data,

                        //    [
                        //    kData[0].volumn, kData[1].volumn,  kData[2].volumn, kData[3].volumn, kData[4].volumn, kData[5].volumn
                        //],
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
                //    data: ['上证指数', '成交金额(万)', '成交量']
                //},
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
                        name: '成交量',
                        type: 'bar',
                        symbol: 'none',
                        data: [
                            kData[0].volumn, kData[1].volumn,  kData[2].volumn, kData[3].volumn, kData[4].volumn, kData[5].volumn],
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

            // 为echarts对象加载数据

            myChart.connect([myChart2, myChart3]);
            myChart3.setOption(option3);
            myChart.setOption(option);
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
