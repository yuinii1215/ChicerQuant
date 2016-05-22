require.config({
    paths: {
        echarts: 'build/dist'
    }
});

var myChart;

var indexData=[
    //[{"date": "2015-01-01", "close": "30"},
    //{"date": "2015-01-04","close": "16.07"},
    //{"date": "2015-01-05",   "close": "20.13" }],
    //[{"date": "2015-01-01", "close": "10"},
    //    {"date": "2015-01-04","close": "20"},
    //    {"date": "2015-01-05",   "close": "30" }],
];

//var myDate = new Date();
//myDate.getFullYear();    //获取完整的年份(4位,1970-????)
//myDate.getMonth();       //获取当前月份(0-11,0代表1月)
//myDate.setDate(myDate.getDate()-1);        //获取当前日(1-31)
//var currentDate=""+myDate.getFullYear()+"-0"+(myDate.getMonth()+1)+"-"+myDate.getDate();
////起始日期
//var d=new Date();
//d.setDate(d.getDate()-10);
//var month=d.getMonth()+1;
//var day = d.getDate();
//if(month<10){
//    month = "0"+month;
//}
//if(day<10){
//    day = "0"+day;
//}
//var startDate = d.getFullYear()+"-"+month+"-"+day;

var indexDate=[];
//for(var i=0;i<=1;i++){
//    //var DA=d.getDate()+i;
//    var month=d.getMonth()+1;
//    var day = d.getDate()+i;
//    if(month<10){
//        month = "0"+month;
//    }
//    if(day<10){
//        day = "0"+day;
//    }
//    indexDate[i] = d.getFullYear()+"-"+month+"-"+day;
//}
//console.log(indexDate);

function initStockPreviewData(){
    console.log("initStockPreviewData");
    var element2=angular.element(document.getElementById("tablefiller"));
    var $scope2 = element2.scope();
    indexData=$scope2.chartData;

    console.log(indexData);
    var newOption = myChart.getOption(); // 深拷贝
    for(item in indexData){
        newOption.series[0].data[item]=indexData[item].close;
        newOption.xAxis[0].data[item]=indexData[item].date;
    }
    //newOption.series[0].data = [indexData[tableIndex][0].close,indexData[tableIndex][1].close,indexData[tableIndex][2].close];
    myChart.setOption(newOption,true);
    console.log(indexData);
}

var tableIndex=0;

function SingleClick(){
    //function getStockAmongDateService($name, $startdate, $enddate)用来返回一段时间里面的
    var element2=angular.element(document.getElementById("tablefiller"));
    var $scope2 = element2.scope();
    indexData=$scope2.chartData;
    console.log(indexData);

    var newOption = myChart.getOption(); // 深拷贝
    for(item in indexData){
        newOption.series[0].data[item]=indexData[item].close;
        newOption.xAxis[0].data[item]=indexData[item].date;
    }
    myChart.setOption(newOption,true);

}

function DoubleClick(row){
    tableIndex=row;
    //data中取出id
    //allStock2SingleStockPage("sh300000");
    localStorage.singleStockID=$(this).eq(0)[0].firstChild.textContent;
    window.location.href="../singleStockPage/singleStockPage.html";
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
                        data : indexDate//[indexData[tableIndex][0].date,indexData[tableIndex][1].date,indexData[tableIndex][2].date]
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
                        data:[0,0,0,0,0,0,0]
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
