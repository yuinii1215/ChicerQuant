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
      'method': 'getMyStrategyDataService'
      'startdate': (year-1)+"-"+month+"-"day,
      'enddate': year+"-"+month+"-"+day,
      'name': stockid
    }).success(function(response,status){
      //give ctgg

    }).error(function(){
      alert("error");
    });
  }
});
