/**
 *
 * @author cxworks
 *
 *
 */
var app=angular.module('singleStrategy', []);
app.controller('kLine',function($scope,$http){

  $scope.test=function(){
    var stockid=$scope.stockid;
    var nowDate=new Date();
    var year=nowDate.getFullYear();
    var month=nowDate.getMonth()+1;
    var day=nowDate.getDate();
    var startdate=year+"-"+(month-1)+"-"+day;
    if (month-1==0) {
      startdate=(year-1)+"-12-"+day;
    }
    if (stockid==undefined) {
      stockid='sh600315';
    }
    $scope.url='http://115.159.97.98/php/serviceController.php';
    $http.post($scope.url,{
      'method': 'getPolyAmongDateService',
      'startdate': startdate,
      'enddate': year+"-"+month+"-"+day,
      'name': stockid
    }).success(
      function(response,status){
        var value=[];
        var date=[];
        for (obj in response) {
          if (obj!="retmsg") {
            date.push(response[obj]["date"]);
            value.push(response[obj]["poly"]);
          }
        }
        $scope.polyoption = {

            title: {
                left: 'center',
                text: 'Poly Line Chart',
            },
            legend: {
                top: 'bottom',
                data:['poly','real close']
            },
            toolbox: {
                show: true,
                feature: {
                    dataView: {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: date
            },
            yAxis: {
                type: 'value',
                boundaryGap: false
            },

            series: [
                {
                    name:'close',
                    type:'line',
                    smooth:true,
                    data: value
                }
            ]
        };


        var polychart=echarts.init(document.getElementById('poly'));
        polychart.setOption($scope.polyoption);
    }



    ).error(
      function(){
        alert("net error");
      }
    );
};




$scope.change=function(){
           $scope.url="http://115.159.97.98/php/serviceController.php";
           var id=$scope.stockid;
           var nowDate=new Date();
           var year=nowDate.getFullYear();
           var month=nowDate.getMonth()+1;
           var day=nowDate.getDate();


           $http.post($scope.url, {
           	"method": "getStockAmongDateService",
           	"startdate": (year)+"-"+(month-3)+"-"+day,
           	"enddate": year+"-"+month+"-"+day,
           	"name": id
           }).success(function(response,status){



           		var data00=spliData(response);
           		var chinese=response["0"]["stock_name"];
function spliData(rawData) {
    var categoryData = [];
    var values = [];

    for (obj in rawData) {
        if(obj!="retmsg"){
        	categoryData.push(rawData[obj]["date"]);
        	var val=[];
        	val.push(rawData[obj]["open"]);
        	val.push(rawData[obj]["close"]);
        	val.push(rawData[obj]["low"]);
        	val.push(rawData[obj]["high"]);
        	values.push(val);
        }
    }
    return {
        categoryData: categoryData,
        values: values
    };
}

function calculateMA(dayCount) {
    var result = [];
    for (var i = 0, len = data00.values.length; i < len; i++) {
        if (i < dayCount) {
            result.push('-');
            continue;
        }
        var sum = 0;
        for (var j = 0; j < dayCount; j++) {
            sum += Number(data00.values[i-j][1]);
        }
        result.push(sum / dayCount);
    }
    return result;
}



$scope.klineoption = {
    title: {
        text: chinese,
        left: 0
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'line'
        }
    },
    legend: {
        data: ['日K', 'MA5', 'MA10', 'MA20','MA30']
    },
    grid: {
        left: '10%',
        right: '10%',
        bottom: '15%'
    },
    xAxis: {
        type: 'category',
        data: data00.categoryData,
        scale: true,
        boundaryGap : false,
        axisLine: {onZero: false},
        splitLine: {show: false},
        splitNumber: 5,
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
            type: 'candlestick',
            data: data00.values,
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
        }

    ]
};
var chart=echarts.init(document.getElementById('kline'));
chart.setOption($scope.klineoption);

           }).error();



$scope.test();


};

  $scope.stockid='sh600310';
  $scope.change();
});
