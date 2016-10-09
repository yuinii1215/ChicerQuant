var app=angular.module('myApp',[]);
app.controller("SearchCtrl", function($scope, $http, MyCache) {
    //$scope.url = 'http://115.159.106.212/php/serviceController.php';
    $scope.url = 'http://anyquant.net:15000/php/serviceController.php'; // The url of our search

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
       var crossStrName="";
       var new_cnt=0;
       for(var item=0;item<4;item++){

           if(KType[item]!="") {
               console.log(KType[item]);
               if(new_cnt>0) {
                   crossStrName += "&" + KType[item];
               }
               else{
                   crossStrName +=  KType[item];
                   new_cnt++;
               }
           }

       }
   // crossStrName=KType[0]+"&"+KType[1]+"&"+KType[2]+"&"+KType[3];
      newStr_Name=newStrName;

       $http.post($scope.url, {
           "username": localStorage.userName,
           "strategyname": newStr_Name ,
           "crossstr": crossStrName,
           "share":0,
           "method": "saveCrossStrategyService"
       }).success(function (data, status) {
           //{operation: "success", duplicate: false}
           var tmp=data;
           if(tmp.operation=="success"){
               if(tmp.duplicate==true){//提示重新命名
                   swal("策略重名,请重新命名");
               }else {
                   swal("策略保存成功");
               }
           }else{
               swal("策略保存失败,请重试");
           }
       })
           .error(function (data, status) {
               $scope.data = data || "Request failed";
               $scope.status = status;
           });
   }

    $scope.showAllStr=function(){
        $scope.strManager =[];
        str_cnt=0;
        $http.post($scope.url, {
            "username": localStorage.userName,
            "method": "getCustomStrategyService"
        }).success(function (data, status) {
            var myCustomStr=data;

            for(var item in myCustomStr) {
                console.log("1");
                console.log(myCustomStr[item]);
                if (myCustomStr[item]!="success") {
                    $scope.strManager[str_cnt] = {
                        "strName": myCustomStr[item].strategyname,
                        "strType": myCustomStr[item].strategytype,
                        "buypoint": myCustomStr[item].buypoint,
                        "sellpoint": myCustomStr[item].sellpoint
                    };
                    str_cnt++;
                }
            }

            $http.post($scope.url, {
                "username": localStorage.userName,
                "method": "getCrossStrategyService"
            }).success(function (data, status) {
                var myCrossStr=data;
                for(var item in myCrossStr) {
                    console.log("2");
                    console.log(myCrossStr[item]);
                    if (myCrossStr[item]!="success") {
                        if ((myCrossStr[item].strategyname != "") && (myCrossStr[item].crossstr != "&&&")) {
                            $scope.strManager[str_cnt++] = {
                                "strName": myCrossStr[item].strategyname,
                                "strType": myCrossStr[item].crossstr,
                                "buypoint": "黄金交叉点",
                                "sellpoint": "死亡交叉点"
                            };
                        }
                    }
                }
            })
                .error(function (data, status) {
                    $scope.data = data || "Request failed";
                    $scope.status = status;
                });
        })
            .error(function (data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });
    }
    $scope.saveCustomerStr=function(newStrName){
       
        newStr_Name=newStrName;
        $http.post($scope.url, {
            "username": localStorage.userName,
            "strategyname": newStr_Name ,
            "type":newType,
            "buypoint":buyStd,
            "sellpoint":sellStd,
            "share":0,
            "method": "saveCustomStrategyService"
        }).success(function (data, status) {
            console.log("save customer success");
            var tmp=data;
            if(tmp.operation=="success"){
                if(tmp.duplicate==true){//提示重新命名
                    swal("已有重名策略,请重新命名");
                }else {
                    swal("保存策略成功");
                }
            }else{
                swal("保存策略失败,请重试");
            }
        })
            .error(function (data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });
    }

    $scope.shareCrossStr=function(newStrName){
        newStr_Name=generateMixed(10);
        console.log(newStr_Name);
        var crossStrName="";
        var new_cnt=0;
        for(var item=0;item<4;item++){

            if(KType[item]!="") {
                console.log(KType[item]);
                if(new_cnt>0) {
                    crossStrName += "&" + KType[item];
                }
                else{
                    crossStrName +=  KType[item];
                    new_cnt++;
                }
            }

        }
        // crossStrName=KType[0]+"&"+KType[1]+"&"+KType[2]+"&"+KType[3];
        newStr_Name=newStrName;

        $http.post($scope.url, {
            "username": localStorage.userName,
            "strategyname": newStr_Name ,
            "crossstr": crossStrName,
            "share":1,
            "method": "saveCrossStrategyService"
        }).success(function (data, status) {
             // console.log("share cross success");
            swal("分享成功!"+"\n"+"去Chicer策略社区看看吧^_^");

        })
            .error(function (data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });
    }
    $scope.shareCustomerStr=function(newStrName){
        newStr_Name=generateMixed(11);
        console.log(newStr_Name);
        newStr_Name=newStrName;
        $http.post($scope.url, {
            "username": localStorage.userName,
            "strategyname": newStr_Name,
            "type":newType,
            "buypoint":buyStd,
            "sellpoint":sellStd,
            "share":1,
            "method": "saveCustomStrategyService"
        }).success(function (data, status) {
            console.log("返回值");
            console.log(data);
            swal("分享成功!"+"\n"+"去Chicer策略社区看看吧^_^");
        })
            .error(function (data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });
    }

    $scope.strManager =[];
    var str_cnt=0;

    $http.post($scope.url, {
            "username": localStorage.userName,
            "method": "getCustomStrategyService"
        }).success(function (data, status) {
             var myCustomStr=data;
            console.log("1");
            for(var item in myCustomStr) {
                // console.log("1");
                // console.log(myCustomStr[item]);
                if (myCustomStr[item]!="success") {
                $scope.strManager[str_cnt++] = {
                    "strName": myCustomStr[item].strategyname,
                    "strType": myCustomStr[item].strategytype,
                    "buypoint": myCustomStr[item].buypoint+"%",
                    "sellpoint": myCustomStr[item].sellpoint+"%"
                };
            }
            }
            
            $http.post($scope.url, {
                "username": localStorage.userName,
                "method": "getCrossStrategyService"
            }).success(function (data, status) {
                var myCrossStr=data;
                for(var item in myCrossStr) {
                    console.log("2");
                    // console.log(myCrossStr[item]);
                    if (myCrossStr[item]!="success") {
                        if ((myCrossStr[item].strategyname != "") && (myCrossStr[item].crossstr != "&&&")) {
                            $scope.strManager[str_cnt++] = {
                                "strName": myCrossStr[item].strategyname,
                                "strType": myCrossStr[item].crossstr,
                                "buypoint": "黄金交叉点",
                                "sellpoint": "死亡交叉点"
                            };
                        }
                    }
                }
            })
                .error(function (data, status) {
                    $scope.data = data || "Request failed";
                    $scope.status = status;
                });
        })
            .error(function (data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });






    // $http.post($scope.url, {
    //     "username": localStorage.userName,
    //     "method": "getCrossStrategyService"
    // }).success(function (data, status) {
    //     var myCrossStr=data;
    //     for(var item in myCrossStr) {
    //         console.log("2");
    //         console.log(myCrossStr[item]);
    //         if((myCrossStr[item].strategyname!="")&&(myCrossStr[item].crossstr!="&&&")){
    //             $scope.strManager[str_cnt++] = {
    //             "strName": myCrossStr[item].strategyname,
    //             "strType": myCrossStr[item].strategytype,
    //             "buypoint": "黄金交叉点",
    //             "sellpoint": "死亡交叉点"
    //           };
    //        }
    //     }
    // })
    //     .error(function (data, status) {
    //         $scope.data = data || "Request failed";
    //         $scope.status = status;
    //     });

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




