var app=angular.module('myApp',[]);
app.controller("SearchCtrl", function($scope, $http, MyCache) {
    //$scope.url = 'http://115.159.106.212/php/serviceController.php';
    $scope.url = 'http://115.159.97.98/php/serviceController.php'; // The url of our search

    //当前日期
    var myDate = new Date();
    myDate.getFullYear();    //获取完整的年份(4位,1970-????)
    myDate.getMonth();       //获取当前月份(0-11,0代表1月)
    myDate.getDate();        //获取当前日(1-31)
    var currentDate=""+myDate.getFullYear()+"-0"+(myDate.getMonth()+1)+"-"+myDate.getDate();
    var d=new Date();
    d.setDate(d.getDate()-30);
    var month=d.getMonth()+1;
    var day = d.getDate();
    if(month<10){
            month = "0"+month;
        }
    if(day<10){
            day = "0"+day;
    }
   var startDate = d.getFullYear()+"-"+month+"-"+day;

    // $http.post($scope.url, {
    //     "name": "sh600000",
    //     "startdate": "2016-01-01",
    //     "enddate":currentDate,
    //     "method": "getStockAmongDateService"
    // }).success(function(data) {
    //     $scope.error = false;
    //     $scope.data = data;
    //     $scope.investData =data;
    // });


    $scope.stockName;

    //$http.post($scope.url, {
    //    "date": "---",
    //    "name": localStorage.singleStockID,
    //    "method": "getStockByNameService"
    //}).success(function (data, status) {
    //        $scope.status = status;
    //        $scope.data = data;
    //        var stockInfo = data;
    //        $scope.result = stockInfo[0];
    //        console.log(stockInfo);
    //    })
    //    .error(function (data, status) {
    //        $scope.data = data || "Request failed";
    //        $scope.status = status;
    //    });

    $scope.stockID="sh600000";

    $http.post($scope.url, {// function getDayLineService($name, $startdate, $enddate){
        "startdate": "2016-01-01",
        "enddate":currentDate,
        "name": $scope.stockID,
        "method": "getDayLineService"
    }).success(function (data, status) {
            $scope.status = status;
            $scope.data = data;
            $scope.dayKLineResult=data;
            var content=data;
            $scope.result=content[19];
            $scope.stockName=$scope.result.stock_name;
            $scope.boardData=data[0];
        })
        .error(function (data, status) {
            $scope.data = data || "Request failed";
            $scope.status = status;
        });


   $scope.changeStock=function(){
       $scope.stockID=document.getElementById('newStock').value;
        $http.post($scope.url, {// function getDayLineService($name, $startdate, $enddate){
            "startdate": "2016-01-01",
            "enddate": currentDate,
            "name":  $scope.stockID,
            "method": "getDayLineService"
        }).success(function (data, status) {
            $scope.status = status;
            $scope.data = data;
            $scope.dayKLineResult = data;
            $scope.boardData=data[0];
            newStock();
        })
            .error(function (data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });
    }
    var newStr_Name;
   $scope.saveCrossStr=function(newStrName){
       // case "saveCrossStrategyService":
       // saveCrossStrategyService($objData->username,$objData->strategyname,$objData->crossstr);
       // break;
      var crossStrName=KType[0]+"&"+KType[1]+"&"+KType[2]+"&"+KType[3];
      newStr_Name=newStrName;

       $http.post($scope.url, {
           "username": localStorage.userName,
           "strategyname": newStr_Name ,
           "crossstr": crossStrName,
           "method": "saveCrossStrategyService"
       }).success(function (data, status) {
           console.log(status);
       })
           .error(function (data, status) {
               $scope.data = data || "Request failed";
               $scope.status = status;
           });
   }
    
    $scope.saveCustomerStr=function(newStrName){
        // case "saveCustomStrategyService":
        // saveCustomStrategyService($objData->username,$objData->strategyname,$objData->type,$objData->buyposint,$objData->sellpoint);
        // break;
        newStr_Name=newStrName;
        $http.post($scope.url, {
            "username": localStorage.userName,
            "strategyname": newStr_Name ,
            "type":newType,
            "buypoint":buyStd,
            "sellpoint":sellStd,
            "method": "saveCustomStrategyService"
        }).success(function (data, status) {
            console.log(data);
            console.log(status);
        })
            .error(function (data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });
    }

    d=new Date();
    d.setDate(d.getDate()-200);
    month=d.getMonth()+1;
    day = d.getDate();
    if(month<10){
        month = "0"+month;
    }
    if(day<10){
        day = "0"+day;
    }
    startDate = d.getFullYear()+"-"+month+"-"+day;





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
    };
});

app.factory('MyCache', function ($cacheFactory) {
    return $cacheFactory('myCache');
})




