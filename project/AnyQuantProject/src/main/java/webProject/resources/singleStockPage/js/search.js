var app=angular.module('myApp',[]);
app.controller("SearchCtrl", function($scope, $http, MyCache) {
    //$scope.url = 'http://115.159.106.212/php/serviceController.php';
    $scope.url = 'http://115.159.97.98/php/serviceController.php'; // The url of our search

    //当前日期
    var myDate = new Date();
    myDate.getFullYear();    //获取完整的年份(4位,1970-????)
    myDate.getMonth();       //获取当前月份(0-11,0代表1月)
    myDate.getDate();        //获取当前日(1-31)
    var currentDate=""+myDate.getFullYear()+"-"+myDate.getMonth()+"-"+myDate.getDate();



    $http.post($scope.url, {
        "date": "---",
        "name": localStorage.singleStockID,
        "method": "getStockByNameService"
    }).success(function (data, status) {
            $scope.status = status;
            $scope.data = data;
            //data=eval("("+data+")");
            var stockInfo = data;
            //stockInfo= $.parseJSON(stockInfo);
            $scope.result = stockInfo[0];
        })
        .error(function (data, status) {
            $scope.data = data || "Request failed";
            $scope.status = status;
        });
    //};

    //$scope.getterK = MyCache.get('GRACE');
    $scope.getterK = [];



    $http.post($scope.url, {
        "name": localStorage.singleStockID,
        "startdate": "2015-01-01",
        "enddate": "2015-03-01",
        "method": "getStockAmongDateService"
    }).success(function (data, status) {
            $scope.status = status;
            $scope.data = data;
            $scope.kLineResult=data;

           // console.log($scope.data);
            var stockInfo=data;
           // $scope.kLineResult = stockInfo[0];

            var STR=JSON.stringify(stockInfo[0]);
            $scope.ktest=STR;
            //console.log($scope.ktest);
            //$scope.getterK=data;

        })
        .error(function (data, status) {
            $scope.data = data || "Request failed";
            $scope.status = status;
        });

    $scope.test = {
        "0": {
            "date": "2015-01-01",
            "stock_name": "浦发银行",
            "open": "15.69",
            "high": "15.69",
            "low": "15.69",
            "close": "15.69",
            "volumn": "0",
            "adj_price": "14.9942",
            "turnover": "0",
            "pe_ttm": "0",
            "pb": "0",
            "industry": "银行"
        },
        "1": {
            "date": "2015-01-04",
            "stock_name": "浦发银行",
            "open": "15.94",
            "high": "16.25",
            "low": "15.56",
            "close": "16.07",
            "volumn": "513568700",
            "adj_price": "15.35735",
            "turnover": "0",
            "pe_ttm": "6.530156",
            "pb": "1.295751",
            "industry": "银行"
        },
        "2": {
            "date": "2015-01-05",
            "stock_name": "浦发银行",
            "open": "16",
            "high": "16.68",
            "low": "15.82",
            "close": "16.13",
            "volumn": "511684500",
            "adj_price": "15.41469",
            "turnover": "0",
            "pe_ttm": "6.55454",
            "pb": "1.300589",
            "industry": "银行"
        },
        "3": {
            "date": "2015-01-06",
            "stock_name": "浦发银行",
            "open": "15.9",
            "high": "16.17",
            "low": "15.53",
            "close": "15.81",
            "volumn": "385716800",
            "adj_price": "15.10888",
            "turnover": "0",
            "pe_ttm": "6.424504",
            "pb": "1.274787",
            "industry": "银行"
        },
        "4": {
            "date": "2015-01-07",
            "stock_name": "浦发银行",
            "open": "15.87",
            "high": "15.88",
            "low": "15.2",
            "close": "15.25",
            "volumn": "330627100",
            "adj_price": "14.57371",
            "turnover": "0",
            "pe_ttm": "6.196942",
            "pb": "1.229632",
            "industry": "银行"
        },
        "5": {
            "date": "2015-01-08",
            "stock_name": "浦发银行",
            "open": "15.2",
            "high": "16.25",
            "low": "15.11",
            "close": "15.43",
            "volumn": "491999900",
            "adj_price": "14.74573",
            "turnover": "0",
            "pe_ttm": "6.270084",
            "pb": "1.244146",
            "industry": "银行"
        },
        "retmsg": "success"
    }

    $http.post($scope.url, {//暂时还有问题
        "date": currentDate,
        "offset": 30,
        "method": "getRelativeDateService"
    }).success(function (data, status) {
            $scope.status = status;
            $scope.startDate = data;
            //data=eval("("+data+")");
            var stockInfo = data;

            $scope.startDate = stockInfo;
            console.log($scope.startDate);

        })
        .error(function (data, status) {
            $scope.data = data || "Request failed";
            $scope.status = status;
        });

    //var stock;
    //$http.post($scope.url, {//$objData->name,$objData->startdate,$objData->enddate
    //    "name": localStorage.singleStockID,
    //    "startdate":$scope.startDate ,
    //    "enddate": currentDate,
    //    "method": "getStockAmongDateService"
    //}).success(function (data, status) {
    //        $scope.status = status;
    //        //data=eval("("+data+")");
    //        var tableData=data;
    //        console.log(tableData[0]);
    //        //console.log($scope.startDate );
    //        console.log(currentDate );
    //        for(var item in tableData){
    //            console.log(tableData[item].date);
    //            stock="<tr><td>"+tableData[item].stock_name+"</td><td>"+tableData[item].date+"</td><td>"+tableData[item].open+"</td>" +
    //                "<td>"+tableData[item].high+"</td><td>"+tableData[item].low+"</td><td>"+tableData[item].close+"</td>" +
    //                "<td>"+tableData[item].volumn+"</td><td>"+tableData[item].adj_price+"</td>" +
    //                "<td>"+tableData[item].pe_ttm+"</td><td>"+tableData[item].pb+"</td></tr>";
    //            $('#tbody').append($(stock));
    //            //        document.getElementById('tableBody').innerHTML=stock;
    //        }
    //
    //    })
    //    .error(function (data, status) {
    //        $scope.data = data || "Request failed";
    //        $scope.status = status;
    //    });




});

app.factory('MyCache', function ($cacheFactory) {
    return $cacheFactory('myCache');
})




