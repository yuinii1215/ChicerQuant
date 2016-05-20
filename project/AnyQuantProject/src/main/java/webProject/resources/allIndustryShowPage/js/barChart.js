require.config({
    paths: {
        echarts: '../homePage/js/echart'
    }

});

require(
    [
        'echarts',
        'echarts/chart/bar',
        'echarts/chart/line'
    ],

    function (ec) {
        var realstatus,realdata;
        /*使用外部的js调用angularjs控制器中的方法!!!!!!!!!!!*/
        var element=angular.element(document.getElementById("pureBar"));
        var $scope = element.scope();
   //     var STR=JSON.stringify($scope.ktest);


        setTimeout(function() {
            var pureBarChart = ec.init(document.getElementById('pureBar'));

            pureBarChart.showLoading({
                itemStyle: {normal: {color:'#ffffff', label:{show:true}}},
                text: '正在努力加载中...',
                textStyle: {
                   fontSize: 10,
                }
            });


            $.ajaxSettings.async = false;

            var barYData= $scope.industryPure
            var barXData= $scope.allIndustryName;

            var option = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                toolbox: {
                    //显示策略，可选为：true（显示） | false（隐藏），默认值为false
                    show: true,
                    //启用功能，目前支持feature，工具箱自定义功能回调处理
                    feature: {
                        //辅助线标志
                        mark: {show: true},
                        //dataZoom，框选区域缩放，自动与存在的dataZoom控件同步，分别是启用，缩放后退
                        dataZoom: {
                            show: true,
                            title: {
                                dataZoom: '区域缩放',
                                dataZoomReset: '区域缩放后退'
                            }
                        },
                        //数据视图，打开数据视图，可设置更多属性,readOnly 默认数据视图为只读(即值为true)，可指定readOnly为false打开编辑功能
                        dataView: {show: true, readOnly: true},
                        //magicType，动态类型切换，支持直角系下的折线图、柱状图、堆积、平铺转换
                        magicType: {show: true, type: ['line', 'bar']},
                        //restore，还原，复位原始图表
                        restore: {show: true},
                    }
                },
                grid: {
                    left: '2%',
                    right: '2%',
                    bottom: '1%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        data:barXData
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        type: 'bar',

                        itemStyle: {
                            normal: {
                                color: function (params) {
                                        if (params > 0) {
                                            return 'red'
                                        }
                                        else {
                                            return '#617ba1'
                                        }

                                }
                            }
                        },
                        data: barYData,
                    //    itemStyle: {normal: {color:'#617ba1', label:{show:true}}},
                    }
                ]
            };


            pureBarChart.setOption(option);
            pureBarChart.hideLoading();


            setTimeout(function () {
                window.onresize = function () {
                    pureBarChart.resize();
                }
            }, 100);


        },1500);



    });