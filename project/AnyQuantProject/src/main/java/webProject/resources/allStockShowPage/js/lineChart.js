require.config({
    paths: {
        echarts: 'build/dist'
    }
});

var myChart;

var indexData=[
    [{"date": "2015-01-01", "close": "30"},
    {"date": "2015-01-04","close": "16.07"},
    {"date": "2015-01-05",   "close": "20.13" }],
    [{"date": "2015-01-01", "close": "10"},
        {"date": "2015-01-04","close": "20"},
        {"date": "2015-01-05",   "close": "30" }],
];

var tableIndex=0;

function SingleClick(row){
    //function getStockAmongDateService($name, $startdate, $enddate)用来返回一段时间里面的
    tableIndex=row-1;
    var newOption = myChart.getOption(); // 深拷贝
    newOption.series[0].data = [indexData[tableIndex][0].close,indexData[tableIndex][1].close,indexData[tableIndex][2].close];
    myChart.setOption(newOption,true);
}

function DoubleClick(row){
    tableIndex=row-1;
    allStock2SingleStockPage("sh300000");
}
// 使用
require(
    [
        'echarts',
        'echarts/chart/line'
    ],
    function (ec) {
        myChart = ec.init(document.getElementById('main1'));
        setTimeout(function(){


            var option = {
                title : {
                    text: '近期走势',
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['收盘价']
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        boundaryGap : false,
                        data : [indexData[tableIndex][0].date,indexData[tableIndex][1].date,indexData[tableIndex][2].date]
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'收盘价',
                        type:'line',
                        smooth:true,
                        itemStyle: {normal: {areaStyle: {type: 'default'}}},
                        data:[indexData[tableIndex][0].close,indexData[tableIndex][1].close,indexData[tableIndex][2].close]
                            //[10, 12, 21, 54, 260, 830, 710]
                    }
                ]
            };


            myChart.setOption(option);

            // 为echarts对象加载数据



            setTimeout(function () {
                window.onresize = function () {
                    myChart.resize();

                }
            }, 200);

        },1000);



    }
);
