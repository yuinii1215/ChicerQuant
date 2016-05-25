require.config({
    paths: {
        echarts: '../homePage/js/echart'
    }

});

require(
    [
        'echarts',
        'echarts/chart/pie',
        'echarts/theme/macarons',
    ],

    function (ec) {
        var realstatus,realdata;
        /*使用外部的js调用angularjs控制器中的方法!!!!!!!!!!!*/
        var element=angular.element(document.getElementById("pieChart1"));
        var $scope = element.scope();
        //     var STR=JSON.stringify($scope.ktest);



        setTimeout(function() {
            var pieChart1 = ec.init(document.getElementById('pieChart1'),'macarons');
            var pieChart2 = ec.init(document.getElementById('pieChart2'),'macarons');

            pieChart1.showLoading({
                itemStyle: {normal: {color:'#ffffff', label:{show:true}}},
                text: '正在努力加载中...',
                textStyle: {
                    fontSize: 10,
                }
            });

            pieChart2.showLoading({
                itemStyle: {normal: {color:'#ffffff', label:{show:true}}},
                text: '正在努力加载中...',
                textStyle: {
                    fontSize: 10,
                }
            });

            $.ajaxSettings.async = false;

            var num= $scope.singleNum;
            var up= $scope.Up;
            var down=$scope.Down;
            var keep=$scope.Keep;

         //   console.log("num:"+parseInt(num));

            var option = {
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    x: 'top',
                    data:['银行','其他']
                },
                series: [
                    {
                        name:'占比',
                        type:'pie',
                        radius: ['50%', '70%'],
                        avoidLabelOverlap: false,
                        label: {
                            normal: {
                                show: false,
                                position: 'center'
                            },
                            emphasis: {
                                show: true,
                                textStyle: {
                                    fontSize: '30',
                                    fontWeight: 'bold'
                                }
                            }
                        },
                        labelLine: {
                            normal: {
                                show: false
                            }
                        },
                        data:[
                            {value:parseInt(num), name:localStorage.singleIndustryID},
                            {value:2380-parseInt(num), name:'其他'},

                        ]
                    }
                ]
            };

            var option2 = {
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    x: 'top',
                    data:['上涨','下跌','持平']
                },
                series: [
                    {
                        name:'占比',
                        type:'pie',
                        radius: ['50%', '70%'],
                        avoidLabelOverlap: false,
                        label: {
                            normal: {
                                show: false,
                                position: 'center'
                            },
                            emphasis: {
                                show: true,
                                textStyle: {
                                    fontSize: '30',
                                    fontWeight: 'bold'
                                }
                            }
                        },
                        labelLine: {
                            normal: {
                                show: false
                            }
                        },
                        data:[
                            {value:parseInt(up), name:"上涨"},
                            {value:parseInt(down), name:'下跌'},
                            {value:parseInt(keep), name:'持平'},

                        ]
                    }
                ]
            };

            pieChart1.setOption(option);
            pieChart1.hideLoading();
            pieChart2.setOption(option2);
            pieChart2.hideLoading();

            setTimeout(function () {
                window.onresize = function () {
                    pieChart1.resize();
                    pieChart2.resize();
                }
            }, 100);

        },1500);

    });


/*      var  colors=['#f6909e','#83dce7'];
 var i=0;
 itemStyle : {
 normal : {
 label: {
 show: false
 },
 labelLine: {
 show: false
 },
 //自定义颜色数组
 color: function () {
 return colors[i++];
 }
 }
 },*/