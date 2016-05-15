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


/*使用外部的js调用angularjs控制器中的方法!!!!!!!!!!!*/
        var element=angular.element(document.getElementById("main1"));
        var controller = element.controller();
        var $scope = element.scope();
        var next=element.$$ChildScope;


        //var func=$scope.KlineDataList();
        //var kData=$scope.test;


        //var element2=document.getElementById("context");

      //  var test =localStorage.result;
     //   var test2=element2.innerHTML;
        console.log(next);
       // document.write(test);

        // 基于准备好的dom，初始化echarts图表
        var myChart = ec.init(document.getElementById('main1'));
        var myChart2 = ec.init(document.getElementById('main2'));
        var myChart3 = ec.init(document.getElementById('main3'));

        //var axisData = [
        //    kData[0].date, kData[1].date,  kData[2].date, kData[3].date, kData[4].date, kData[5].date];

        var axisData=[element2[0].date];

        //for(var item in axisData){
        //    axisData[item]=kData[item].date;
        //}


        var option = {
            title: {
//             text: '2016年腾讯科技',
                textStyle: {
                    color: '#ffffff',
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
            legend: {
                data: ['上证指数', '成交金额(万)', '成交量'],
                textStyle: {
                    color: '#ffffff',
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
                            color: '#ffffff',
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
                            color: '#ffffff',
                        }
                    }
                }
            ],
            series: [
                {
                    name: '腾讯科技',
                    type: 'k',
                    data: [ // 开盘，收盘，最低，最高
                        [kData[0].open, kData[0].close, kData[0].low, kData[0].high],
                        [kData[1].open, kData[1].close, kData[1].low, kData[1].high],
                        [kData[2].open, kData[2].close, kData[2].low, kData[2].high],
                        [kData[3].open, kData[3].close, kData[3].low, kData[3].high],
                        [kData[4].open, kData[4].close, kData[4].low, kData[4].high],
                        [kData[5].open, kData[5].close, kData[5].low, kData[5].high]
                    ]
                },
                {
                    name: '成交金额(万)',
                    type: 'line',
                    symbol: 'none',
                    data: []
                },
                {
                    name: '成交量',
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
                data: ['上证指数', '成交金额(万)', '成交量']
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
                            color: '#ffffff',
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
                            color: '#ffffff',
                        }
                    }
                }
            ],
            series: [
                {
                    name: '成交金额(万)',
                    type: 'line',
                    symbol: 'none',
                    data: [
                        kData[0].volumn, kData[1].volumn,  kData[2].volumn, kData[3].volumn, kData[4].volumn, kData[5].volumn

                    ],
                    markLine: {
                        symbol: 'none',
                        itemStyle: {
                            normal: {
                                color: '#1e90ff',
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
            legend: {
                y: -30,
                data: ['上证指数', '成交金额(万)', '成交量']
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
                            color: '#ffffff',
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
                            color: '#ffffff',
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
                        kData[0].volumn, kData[1].volumn,  kData[2].volumn, kData[3].volumn, kData[4].volumn, kData[5].volumn
                    ],
                    markLine: {
                        symbol: 'none',
                        itemStyle: {
                            normal: {
                                color: '#1e90ff',
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
        myChart3.setOption(option3);

        myChart.setOption(option);

        // 为echarts对象加载数据
        myChart.connect([myChart2, myChart3]);
        myChart2.connect([myChart, myChart3]);
        myChart3.connect([myChart, myChart2]);

        setTimeout(function () {
            window.onresize = function () {
                myChart.resize();
                myChart2.resize();
                myChart3.resize();
            }
        }, 200)

    });
