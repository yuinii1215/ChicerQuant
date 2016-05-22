var app=angular.module('myApp',[]);
app.controller("SearchCtrl", function($scope, $http, MyCache) {
    $scope.url = 'http://115.159.97.98/php/serviceController.php'; // The url of our search

//START
    var array=new Array();
    var count=0;
    var allStocks=[];
    var firstStockID;
    var myDate = new Date();
    myDate.getFullYear();    //获取完整的年份(4位,1970-????)
    myDate.getMonth();       //获取当前月份(0-11,0代表1月)
    myDate.setDate(myDate.getDate()-1);        //获取当前日(1-31)
    var currentDate=""+myDate.getFullYear()+"-0"+(myDate.getMonth()+1)+"-"+myDate.getDate();
    //起始日期
    var d=new Date();
    d.setDate(d.getDate()-10);
    var month=d.getMonth()+1;
    var day = d.getDate();
    if(month<10){
        month = "0"+month;
    }
    if(day<10){
        day = "0"+day;
    }
    var startDate = d.getFullYear()+"-"+month+"-"+day;

    $http.post($scope.url, {"method": "getAllStocksService"}).
        success(function(data) {
            $scope.error = false;
            $scope.data = data;
            $scope.allStocks =data;

            //   console.log($scope.allStocks);
            for(var item in $scope.allStocks) {
                count++;
            }

            for(var item in  $scope.allStocks) {
                if (item < count-1) {
                    array[item] = new Array;
                    array[item][0] = $scope.allStocks[item].stock_id;
                    array[item][1] = $scope.allStocks[item].stock_name;
                    array[item][2] = $scope.allStocks[item].open;
                    array[item][3] = $scope.allStocks[item].high;
                    array[item][4] = $scope.allStocks[item].low;
                    array[item][5] = $scope.allStocks[item].close;
                    array[item][6] = $scope.allStocks[item].volumn;
                    array[item][7] = $scope.allStocks[item].adj_price;
                    array[item][8] = $scope.allStocks[item].pe_ttm;
                    array[item][9] = $scope.allStocks[item].pb;
                }
            }


            var dataSet = array;

            $(document).ready(function () {
                var selected = [];
                $('#table').DataTable({
                    "rowCallback": function( row, data ) {
                        if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                            $(row).addClass('selected');
                        }
                    },
                    data: dataSet,
                    columns: [
                        {title: "股票代码"},
                        {title: "股票简称"},
                        {title: "开盘价"},
                        {title: "最高价"},
                        {title: "最低价"},
                        {title: "收盘价"},
                        {title: "成交量"},
                        {title: "后复权价"},
                        {title: "市盈率"},
                        {title: "市净率"},
                    ]
                });
                $('#table tbody').on('dblclick', 'tr', function () {
                    console.log("doubleClick");
                    var id = this.id;
                    var index = $.inArray(id, selected);
                    var rowIndex=$(this).index();//0,1,2,3....
                    var stockID=$(this).eq(0)[0].firstChild.textContent;
                    allStock2SingleStockPage(stockID);//
                    $(this).toggleClass('selected');
                } );
                $('#table tbody').on('click', 'tr', function () {
                    console.log("singleClick");
                    var id = this.id;
                    var index = $.inArray(id, selected);
                    var row=$(this).index();
                    firstStockID=array[row][0];
                    $http.post($scope.url, {
                            "name": firstStockID,
                            "startdate":startDate ,
                            "enddate": currentDate,
                            "method": "getStockAmongDateService"
                        }).success(function (data, status) {
                                $scope.status = status;
                                $scope.data = data;
                                $scope.chartData=data;
                                //var stockInfo = data[0];
                                initStockPreviewData();//调用linechart的初始化方法

                            })
                            .error(function (data, status) {
                                $scope.data = data || "Request failed";
                                $scope.status = status;
                            });
                    SingleClick();

                    $(this).toggleClass('selected');
                } );
            });
            //   console.log($scope.table);
            firstStockID=array[0][0];
            console.log("here!!");
            console.log(firstStockID);

            //
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
            //console.log(startDate);
            //console.log(currentDate);

            $http.post($scope.url, {
                "name": firstStockID,
                "startdate":startDate ,
                "enddate": currentDate,
                "method": "getStockAmongDateService"
            }).success(function (data, status) {
                    $scope.status = status;
                    $scope.data = data;
                    $scope.chartData=data;
                    console.log($scope.chartData);
                    //var stockInfo = data[0];
                    initStockPreviewData();//调用linechart的初始化方法

                })
                .error(function (data, status) {
                    $scope.data = data || "Request failed";
                    $scope.status = status;
                });


        })
        .
        error(function(data) {
            $scope.error = true;
            $scope.data = data || "Request failed";

        });//END

    $scope.preview=function() {
        console.log("i AM PREVIEW,TAHNKS for call");
    }
});

app.factory('MyCache', function ($cacheFactory) {
    return $cacheFactory('myCache');
})




