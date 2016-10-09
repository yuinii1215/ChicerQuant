var app = angular.module('benchMarkApp', []);
app.controller('benchMarkCtrl', function ($scope, $http) {

    var array=new Array();
    var benchMark=[];
    $scope.url = 'http://anyquant.net:15000/php/serviceController.php'; // The url of our search
    var startdate="2015-10-01";
    $scope.dayKLineResult=[];
    $scope.monthKLineResult=[];
    $scope.weekKLineResult=[];

    $http.post($scope.url, {"name":"hs300","date": localStorage.latestDate,"method": "getBenchMarkByNameService"}).
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

    $http.post($scope.url, {"name":"hs300","startdate": startdate,"enddate": localStorage.latestDate,"method":"getBenchMarkAmongDateService"}).
        success(function (data) {
            $scope.error = false;
            $scope.data = data;
            $scope.benchMarkInfo =data;
        //    console.log($scope.benchMarkInfo);

        //table
        var count=0;
        var color=[];
        for(var item in $scope.benchMarkInfo) {
            count++;
        }
        for(var item in  $scope.benchMarkInfo) {
            if (item < count-1) {
                array[item] = new Array;

                array[item][0] = $scope.benchMarkInfo[item].date;
                array[item][1] = $scope.benchMarkInfo[item].open;
                array[item][2] = $scope.benchMarkInfo[item].high;
                array[item][3] = $scope.benchMarkInfo[item].low;
                array[item][4] = $scope.benchMarkInfo[item].close;
                array[item][5] = $scope.benchMarkInfo[item].volumn;
                array[item][6] = $scope.benchMarkInfo[item].adj_price;
                color[item]=$scope.benchMarkInfo[item].color;
            }
        }
        var dataSet = array;
        var colorSet =color;
        var i=0;

        $(document).ready(function () {
            $('#table').DataTable({
                data: dataSet,
                "createdRow": function ( row) {
                    $('td', row).eq(3).css( "color", "green");
                    $('td', row).eq(2).css( "color", "red");
                    $('td', row).eq(1).css("color", colorSet[i]);
                    i++;
                },
                columns: [
                    {title: "日期"},
                    {title: "开盘价"},
                    {title: "最高价"},
                    {title: "最低价"},
                    {title: "收盘价"},
                    {title: "成交量"},
                    {title: "后复权价"}
                ]
            });});
        })
        .error(function (data) {
            $scope.error = true;
            $scope.data = data;
            return 'error name';
        });
    $http.post($scope.url, {
        "startdate":  startdate,
        "enddate": localStorage.latestDate,
        "name": "hs300",
        "method": "getDayLineService"
    }).success(function (data) {
            $scope.data = data;
            $scope.dayKLineResult=data;
        })
        .error(function (data) {
            $scope.data = data || "Request failed";
            $scope.status = status;
        });

    $http.post($scope.url, {
        "startdate":  startdate,
        "enddate": localStorage.latestDate,
        "name": "hs300",
        "method": "getWeekLineService"
    }).success(function (data) {
            $scope.data = data;
            $scope.weekKLineResult=data;
        })
        .error(function (data) {
            $scope.data = data || "Request failed";
            $scope.status = status;
        });

    $http.post($scope.url, {
        "startdate":  startdate,
        "enddate": localStorage.latestDate,
        "name": "hs300",
        "method": "getMonthLineService"
    }).success(function (data) {
            $scope.data = data;
            $scope.monthKLineResult=data;
        })
        .error(function (data) {
            $scope.data = data || "Request failed";
            $scope.status = status;
        });



});