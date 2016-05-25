var app = angular.module('benchMarkApp', []);
app.controller('benchMarkCtrl', function ($scope, $http) {

    var benchMark=[];
    $scope.url = 'http://115.159.97.98/php/serviceController.php'; // The url of our search

    $http.post($scope.url, {"name":"hs300","date":"2016-05-20","method": "getBenchMarkByNameService"}).
        success(function (data) {
            $scope.error = false;
            $scope.data = data;
            $scope.benchMark =data;
            console.log( $scope.benchMark);

     /*       adj_price:"3068.04"
            benchmark_id : "hs300"
            benchmark_name:"沪深300"
            close: "3068.04"
            date:"2016-05-18"
            high :"3072.61"
            low:"3039.46"
            open: "3071.53"
            volumn :"-318397692"*/
            document.getElementById("kName").innerHTML=$scope.benchMark[0].benchmark_name+" - "+$scope.benchMark[0].benchmark_id,
            document.getElementById("elementTitle").innerHTML=$scope.benchMark[0].benchmark_name;
            document.getElementById("smallTitle").innerHTML="- "+$scope.benchMark[0].benchmark_id;
            document.getElementById("elements").innerHTML="开盘价:"+$scope.benchMark[0].open+"&nbsp&nbsp&nbsp&nbsp"+"收盘价:"+$scope.benchMark[0].close+"<br/>"+
                "最高价:"+ $scope.benchMark[0].high +"&nbsp&nbsp&nbsp&nbsp"+"最低价:"+ $scope.benchMark[0].low +"<br/>"+"后复权价:"+$scope.benchMark[0].adj_price+"<br/>"+"成交量:"+$scope.benchMark[0].volumn;


        })
        .error(function (data) {
            $scope.error = true;
            $scope.data = data;
            return 'error name';
        });

    $http.post($scope.url, {"name":"hs300","startdate":"2015-08-01","enddate":"2015-12-15","method":"getBenchMarkAmongDateService"}).
        success(function (data) {
            $scope.error = false;
            $scope.data = data;
            $scope.benchMarkInfo =data;
     //       console.log( $scope.benchMark);


        })
        .error(function (data) {
            $scope.error = true;
            $scope.data = data;
            return 'error name';
        });


    function GetDateStr(AddDayCount) {
        var dd = new Date();
        dd.setDate(dd.getDate() + AddDayCount);//获取AddDayCount天后的日期
        var y = dd.getFullYear();
        var m = dd.getMonth() + 1;//获取当前月份的日期
        var d = dd.getDate();
        return y + "-" + m + "-" + d;
    }

});