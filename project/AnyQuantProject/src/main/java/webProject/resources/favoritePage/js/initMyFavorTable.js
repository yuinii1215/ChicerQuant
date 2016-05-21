/**
 * Created by QiHan on 2016/5/17.
 */
var app = angular.module('myFavorApp', []);
    app.controller('myFavorCtrl', function ($scope, $http) {
        $scope.url = 'http://115.159.97.98/php/serviceController.php'; // The url of our search
        var favorStocks=[];
       // addFavorStock("sh600006");
        var singleStock=[];
        var array=new Array();
        var length=0;
        var count =-1;
        $http.post($scope.url, {"username": "cx", "method": "getMyFavorService"}).success(function (data, status) {
                $scope.status = status;
                $scope.data = data;
                $scope.favorStocks = data;
                console.log($scope.favorStocks);


                for(var item in  $scope.favorStocks) {
                    length++;
                }

                for(var item in  $scope.favorStocks){
                    if (item < length-1) {
                        console.log($scope.favorStocks[item].stock_id);
                        getSingleDetail($scope.favorStocks[item].stock_id);
                    }

                }

            })
            .error(function (data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });

        function GetDateStr(AddDayCount) {
            var dd = new Date();
            dd.setDate(dd.getDate() + AddDayCount);//获取AddDayCount天后的日期
            var y = dd.getFullYear();
            var m = dd.getMonth() + 1;//获取当前月份的日期
            var d = dd.getDate();
            return y + "-" + m + "-" + d;
        }


        function getSingleDetail(stockName) {
            $http.post($scope.url, {
                "name": stockName,
                "date": GetDateStr(-1),
                "method": "getStockByNameService"
            }).success(function (data, status) {
                    $scope.status = status;
                    $scope.data = data;
                    $scope.singleStock=data;

                    array[count+1]=new Array;
                    array[count+1][0]=stockName;
                    array[count+1][1]=$scope.singleStock[0].stock_name;
                    array[count+1][2]=$scope.singleStock[0].open;
                    array[count+1][3]=$scope.singleStock[0].high;
                    array[count+1][4]=$scope.singleStock[0].low;
                    array[count+1][5]=$scope.singleStock[0].close;
                    array[count+1][6]=$scope.singleStock[0].volumn;
                    array[count+1][7]=$scope.singleStock[0].adj_price;
                    array[count+1][8]=$scope.singleStock[0].pe_ttm;
                    array[count+1][9]=$scope.singleStock[0].pb;
                    array[count+1][10]=$scope.singleStock[0].industry;

                        count++;
                    if(count==length-2){
                        count = 0;
                        var dataSet =array;

                        $(document).ready(function() {
                            var selected = [];
                            $('#table').DataTable( {
                                "rowCallback": function( row, data ) {
                                    if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                                        $(row).addClass('selected');
                                    }
                                },
                                data:dataSet,
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
                                    {title: "所属行业"},
                                ]
                            } );
                            $('#table tbody').on('click', 'tr', function () {
                                var id = this.id;
                                var index = $.inArray(id, selected);

                                if ( index === -1 ) {
                                    selected.push( id );
                                } else {
                                    selected.splice( index, 1 );
                                }

                                $(this).toggleClass('selected');
                            } );
                        } );
                    }
                })
                .error(function (data, status) {
                    $scope.data = data || "Request failed";
                    $scope.status = status;
                });
        }

        function addFavorStock(stockName){
            $http.post($scope.url, {
                "name": stockName,
                "username":"cx",
                "method": "addMyFavorService"
            }).success(function (data, status) {
                    $scope.status = status;
                    $scope.data = data;

                    console.log(  $scope.data );
            })
                .error(function (data, status) {
                    $scope.data = data || "Request failed";
                    $scope.status = status;
                });
        }


});