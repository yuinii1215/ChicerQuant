var app=angular.module("app",[]);




app.controller("strategycontroller",function($scope,$http){

  $scope.change=function(){
    var stockid=$scope.stockid;
    var nowDate=new Date();
    var year=nowDate.getFullYear();
    var month=nowDate.getMonth()+1;
    var day=nowDate.getDate();

    if (stockid==undefined) {
      stockid='sh600315';
    }
    $scope.url='http://115.159.97.98/php/serviceController.php';
    $http.post($scope.url,{
      'method': 'getMyStrategyDataService',
      'startdate': (year-1)+"-"+month+"-"+day,
      'enddate': year+"-"+month+"-"+day,
      'name': stockid
    }).success(function(response,status){
      //give ctgg

      //get all targetvalues-----最终的结果数组是array
      var array = new Array();
//      if(14 in response){
//        var aha = 1;
//      }
      for(obj in response){
        if(obj != "retmsg"){
            var objvalue = Number(obj)+11;
            if(objvalue in response){
                var c0 = response[obj]["close"];
                var c11 = response[objvalue]["close"];
                var targetvalue = (c11/c0-0.005-1)*100;
                var RSI12value = response[obj]["RSI12"];
                if (RSI12value!=0) {
                  array.push({target:targetvalue,RSI12:RSI12value});
                }

        }
        }
      }

      //filter targetvalues------最终的结果数组是cleanarr,均值是average
      var cleanarr = array;
      var len = array.length;
      var input = 5;
      var average = 0;
      var counter = 0;
      var chartdata=[];
      for(var cursor = 0; cursor<len; ++cursor){
        var cell=[];
        cell.push(cleanarr[cursor]["RSI12"]);
        cell.push(cleanarr[cursor]['target']);
        chartdata.push(cell);
        if(cleanarr[cursor]["target"] < input){
//            cleanarr.splice(cursor,1);
            delete cleanarr[cursor];
        }else {
            average+=cleanarr[cursor]["target"];
            counter++;

        }
      }
      if (counter > 0){
        average = average/counter;
      }


    option={
        title : {
        text: 'RSI'
    },

    legend: {
            data: ['RSI12'],
            left: 'right'
        },
        xAxis : [
            {
                type : 'value',
                scale:true,
                axisLabel : {
                    formatter: '{value}'
                },
                splitLine: {
                    lineStyle: {
                        type: 'dashed'
                    }
                }
            }
        ],
        yAxis : [
            {
                type : 'value',
                scale:true,
                axisLabel : {
                    formatter: '{value} %'
                },
                splitLine: {
                    lineStyle: {
                        type: 'dashed'
                    }
                }
            }
        ],

        series : [{
          name: 'RSI12',
          type:'scatter',
          data: chartdata

        }]

    };

    var chart=echarts.init(document.getElementById('RSI12'));
    chart.setOption(option);
    $scope.ans=average;
    }).error(function(){
      alert("error");
    });
  };

  $scope.change();
});
