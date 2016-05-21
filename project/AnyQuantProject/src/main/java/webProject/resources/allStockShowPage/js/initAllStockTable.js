/**
 * Created by QiHan on 2016/5/17.
 */
var app = angular.module('myAllStockApp', []);
app.controller('AllStockTableCtrl', function ($scope, $http) {

    $scope.url = 'http://115.159.97.98/php/serviceController.php'; // The url of our search

    var array=new Array();
    var count=0;
    var allStocks=[];
    
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
            });
            //   console.log($scope.table);
        

        })
        .
        error(function(data) {
            $scope.error = true;
            $scope.data = data || "Request failed";

        });
});
/*
*       <th>股票代码</th> <!--id-->
 <th>股票简称</th> <!--name-->
 <th>开盘价</th>
 <th>最高价</th>
 <th>最低价</th>
 <th>收盘价</th>
 <th>成交量</th> <!--volum-->
 <th>后复权价</th> <!--adj_price-->
 <th>市盈率</th> <!--pe-->
 <th>市净率</th> <!--pb-->*/