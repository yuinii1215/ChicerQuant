var app=angular.module('myApp',[]);
app.controller("SearchCtrl", function($scope, $http, MyCache) {
    //$scope.url = 'http://115.159.106.212/php/serviceController.php';
    $scope.url = 'http://anyquant.net:15000/php/serviceController.php'; // The url of our search

    //当前日期
    var myDate = new Date();
    myDate.getFullYear();    //获取完整的年份(4位,1970-????)
    myDate.getMonth();       //获取当前月份(0-11,0代表1月)
    myDate.getDate();        //获取当前日(1-31)
    var currentDate=""+myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate();
    var d=new Date();
    d.setDate(d.getDate()-60);
    var month=d.getMonth()+1;
    var day = d.getDate();
    if(month<10){
            month = "0"+month;
        }
    if(day<10){
            day = "0"+day;
    }
   var startDate = d.getFullYear()+"-"+month+"-"+day;

    $http.post($scope.url, {
        "name": localStorage.singleStockID,
        "startdate": startDate,
        "enddate":currentDate,
        "method": "getStockAmongDateService"
    }).success(function(data) {
        $scope.error = false;
        $scope.data = data;
        $scope.tableData =data;

     //   console.log($scope.tableData);
        var array=new Array();
        var count=0;
        var tableData=[];

        for(var item in $scope.tableData) {
            count++;
        }
        var color=[];
        for(var item in  $scope.tableData) {
            if (item < count-1) {
                array[item] = new Array;
                //array[item][0] = $scope.tableData[item].stock_id;
                //array[item][1] = $scope.tableData[item].stock_name;
                array[item][0] = $scope.tableData[item].date;
                array[item][1] = $scope.tableData[item].open;
                array[item][2] = $scope.tableData[item].high;
                array[item][3] = $scope.tableData[item].low;
                array[item][4] = $scope.tableData[item].close;
                array[item][5] = $scope.tableData[item].volume;
                array[item][6] = $scope.tableData[item].adj_price;
                //array[item][7] = $scope.tableData[item].pe_ttm;
                array[item][7] = $scope.tableData[item].pb;
                color[item]=$scope.tableData[item].color;
            }
        }

        var dataSet = array;
        var colorSet =color;
        var i=0;
        $(document).ready(function () {
            var selected = [];
            $('#mytable').DataTable({
                data: dataSet,
                // 设置红绿
                "createdRow": function ( row) {
                    $('td', row).eq(3).css( "color", "green");
                    $('td', row).eq(2).css( "color", "red");
                    $('td', row).eq(1).css("color", colorSet[i]);
                    i++;
                },

                columns: [
                    //{title: "股票代码"},
                    //{title: "股票简称"},
                    {title: "日期"},
                    {title: "开盘价"},
                    {title: "最高价"},
                    {title: "最低价"},
                    {title: "收盘价"},
                    {title: "成交量"},
                    {title: "后复权价"},
                    //{title: "市盈率"},
                    {title: "市净率"},
                ]
        });});});

    // $http.post($scope.url, {
    //    "username": localStorage.userName,
    //     "name": localStorage.singleStockID,
    //     "date": "2016-06-06",
    //     "method": "getStockByNameService"
    // }).success(function (data, status) {
    //         var temp=data;
    //     console.log("testing!!!!!!");
    //         console.log(temp);
    //     if(localStorage.userName==""){
    //         $scope.favorStateContent = "关注";
    //     }
    //     else {
    //   //          console.log(temp);
    //             if (temp[0].favor) {
    //                 $scope.favorStateContent = "取消关注";
    //             } else {
    //                 $scope.favorStateContent = "关注";
    //             }
    //             console.log($scope.favorStateContent);
    //         }
    //    })
    //    .error(function (data, status) {
    //        $scope.data = data || "Request failed";
    //        $scope.status = status;
    //    });


    $http.post($scope.url, {// function getDayLineService($name, $startdate, $enddate){
        "startdate": startDate,
        "enddate":currentDate,
        "name": localStorage.singleStockID,
        "method": "getDayLineService"
    }).success(function (data, status) {
            $scope.status = status;
            $scope.data = data;
            $scope.dayKLineResult=data;
            var content=data;
            var len=0;
            for(var item in content){
            len++;
            }
            len=len-2;
            $scope.result=content[len];
            $scope.stockName=$scope.result.stock_name;
             $scope.showResult="日期: "+$scope.result.date+"   股票名: "+$scope.result.stock_name+"   开盘价: "+$scope.result.open+"   收盘价: "
                 +$scope.result.close+"   最高价: "+$scope.result.high+"   最低价: "+$scope.result.low
                 +"   成交量: "+$scope.result.volume+"   市盈率: "+$scope.result.adj_price+"   市净率: "+$scope.result.pb
                 +"   行业名: "+$scope.result.industry;


        $http.post($scope.url, {
            "username": localStorage.userName,
            "name": localStorage.singleStockID,
            "date":  $scope.result.date,
            "method": "getStockByNameService"
        }).success(function (data, status) {
            var temp=data;

            if(localStorage.userName==""){
                $scope.favorStateContent = "关注";
            }
            else {
                //          console.log(temp);
                if (temp[0].favor) {
                    $scope.favorStateContent = "取消关注";
                } else {
                    $scope.favorStateContent = "关注";
                }
            }
            if(temp.state=="open"){
                $scope.openState="";
            }else{
                $scope.openState="(已停盘)";
            }
        })
            .error(function (data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });



    }).error(function (data, status) {
            $scope.data = data || "Request failed";
            $scope.status = status;
        });

    d=new Date();
    d.setDate(d.getDate()-100);
    month=d.getMonth()+1;
    day = d.getDate();
    if(month<10){
        month = "0"+month;
    }
    if(day<10){
        day = "0"+day;
    }
    startDate = d.getFullYear()+"-"+month+"-"+day;

    $http.post($scope.url, {// function getDayLineService($name, $startdate, $enddate){
        "startdate": startDate,
        "enddate":currentDate,
        "name": localStorage.singleStockID,
        "method": "getWeekLineService"
    }).success(function (data, status) {
            $scope.weekKLineResult=data;
            //console.log($scope.weekKLineResult);
        })
        .error(function (data, status) {
            $scope.data = data || "Request failed";
            $scope.status = status;
        });

    var config = {
        headers: {
            'Authorization': 'Bearer e3943f512c19291ea5b9f45971a5f9861b2d0e33874f420d639a39da1f35bcea',
            "Access-Control-Allow-Origin": "*"
        },
        data: {
            "field": "",
            "publishBeginTime": "20160509100000",
            "publishEndTime": "20160520000000"
        }
    };

    //https://api.wmcloud.com/data/v1/api/subject/getThemesByNewsTimeLF.json?field=&publishBeginTime=20150609100000&publishEndTime=20150609180000
    $http.get('https://api.wmcloud.com/data/v1/api/subject/getThemesByNewsTimeLF.json', config).success(function (data, status) {
        $scope.news=data;
        console.log( $scope.news);
    })
        .error(function (data, status) {
            $scope.data = data || "Request failed";
            $scope.status = status;
        });


    $http.post($scope.url, {// function getFavorNumByStockService($name, $startdate, $enddate){
        "name": localStorage.singleStockID,
        "method": "getFavorNumByStockService"
    }).success(function (data) {
            $scope.favorNum=data;
         //   console.log( $scope.favorNum[0].favornum);
            document.getElementById("hotNum").innerText=$scope.favorNum[0].favornum;
        })
        .error(function (data, status) {
            $scope.data = data || "Request failed";
            $scope.status = status;
        });





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

    $http.post($scope.url, {// function getDayLineService($name, $startdate, $enddate){
        "startdate": startDate,
        "enddate":currentDate,
        "name": localStorage.singleStockID,
        "method": "getMonthLineService"
    }).success(function (data, status) {
            $scope.monthKLineResult=data;
            //console.log($scope.monthKLineResult);
        })
        .error(function (data, status) {
            $scope.data = data || "Request failed";
            $scope.status = status;
        });

    $scope.favorStateContent="关注";

    $scope.favorButtonHandle=function(){
        if($scope.favorStateContent=="关注"){
            if( localStorage.userName==""){

                $.extend($.gritter.options, {
                    time: 4000,
                });
                // clean the wrapper position class
                $('#gritter-notice-wrapper').attr('class', '');
                // global setting override
                $.extend($.gritter.options, {
                    position: '' + $(this).attr('id') + '' // possibilities: bottom-left, bottom-right, top-left, top-right
                });
                $.gritter.options.position = "bottom-right";
                $.gritter.add({
                    title: $(this).find('span.title').text(), // could be simpler, just for demo purposes
                    text: "您好！" + "</br>" + "请先返回主页登录",
                });

            }else {
                document.getElementById("favorState").innerText = "取消关注";
                $scope.favorStateContent = "取消关注";

                $http.post($scope.url, {
                    // "username": "hmy14",
                    "username": localStorage.userName,
                    "name": localStorage.singleStockID,
                    "method": "addMyFavorService"
                }).success(function (data, status) {
                        $scope.status = status;
                        $scope.data = data;

                    })
                    .error(function (data, status) {
                        $scope.data = data || "Request failed";
                        $scope.status = status;
                    });
            }
        }else{
            document.getElementById("favorState").innerText="关注";
            $scope.favorStateContent="关注";
            $http.post($scope.url, {
                "username": localStorage.userName,
                "name": localStorage.singleStockID,
                "method": "cancelMyFavorService"
            }).success(function (data, status) {
                    $scope.status = status;
                    $scope.data = data;

                })
                .error(function (data, status) {
                    $scope.data = data || "Request failed";
                    $scope.status = status;
                });
        }
    };

    $scope.filterDateHandle=function(){
        startDate=document.getElementById("startfilter").value;
        var endDate=document.getElementById("endfilter").value;

        console.log(startDate);
        //起止日期比较
        var date1=new Date(startDate);
        var date2=new Date(endDate);
        if(Date.parse(date1)<=Date.parse(date2)){

            $http.post($scope.url, {
                "startdate": startDate,
                "enddate":endDate,
                "name": localStorage.singleStockID,
                "method": "getDayLineService"
            }).success(function (data, status) {
                $scope.status = status;
                $scope.data = data;
                $scope.dayKLineResult=data;
                tabChanged(tabFlag);
            })
                .error(function (data, status) {
                    $scope.data = data || "Request failed";
                    $scope.status = status;
                });

            $http.post($scope.url, {
                "startdate": startDate,
                "enddate":endDate,
                "name": localStorage.singleStockID,
                "method": "getWeekLineService"
            }).success(function (data, status) {
                $scope.weekKLineResult=data;
                //console.log($scope.weekKLineResult);
            })
                .error(function (data, status) {
                    $scope.data = data || "Request failed";
                    $scope.status = status;
                });

            $http.post($scope.url, {
                "startdate": startDate,
                "enddate":endDate,
                "name": localStorage.singleStockID,
                "method": "getMonthLineService"
            }).success(function (data, status) {
                $scope.monthKLineResult=data;
                //console.log($scope.monthKLineResult);
            })
                .error(function (data, status) {
                    $scope.data = data || "Request failed";
                    $scope.status = status;
                });


        }else{
            alert("日期区间错误:筛选的起始日期应该早于或等于结束日期");
        }
    }


    $scope.test = {
        "0": {
            "date": "2015-01-01",
            "stock_name": "浦发银行",
            "open": "15.69",
            "high": "15.69",
            "low": "15.69",
            "close": "15.69",
            "volume": "0",
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
            "volume": "513568700",
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
            "volume": "511684500",
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
            "volume": "385716800",
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
            "volume": "330627100",
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
            "volume": "491999900",
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
