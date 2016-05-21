require.config({
    paths: {
        echarts: '../homePage/js/echart'
    }

});

require(
    [
        'echarts',
        'echarts/chart/pie'
    ],

    function (ec) {
        var realstatus,realdata;
        /*使用外部的js调用angularjs控制器中的方法!!!!!!!!!!!*/
        var element=angular.element(document.getElementById("pieChart1"));
        var $scope = element.scope();
        //     var STR=JSON.stringify($scope.ktest);


        setTimeout(function() {
            var pieChart1 = ec.init(document.getElementById('pieChart1'));

            pieChart1.showLoading({
                itemStyle: {normal: {color:'#ffffff', label:{show:true}}},
                text: '正在努力加载中...',
                textStyle: {
                    fontSize: 10,
                }
            });

            $.ajaxSettings.async = false;

            var num= $scope.singleNum;
            console.log("num:"+parseInt(num));
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

            pieChart1.setOption(option);
            pieChart1.hideLoading();

            setTimeout(function () {
                window.onresize = function () {
                    pieChart1.resize();
                }
            }, 100);


        },1500);

    });