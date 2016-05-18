/**
 * Created by QiHan on 2016/5/17.
 */
var app = angular.module('myFavorApp', []);
app.controller('AllStockTableCtrl', function ($scope, $http) {

    $scope.url = 'http://115.159.97.98/php/index.php'; // The url of our search

   // var allStocks=[];
    var stock;
    $http.post($scope.url, {"method": "getAllStocksService"}).
        success(function(data) {
            $scope.error = false;
            $scope.data = data;
            var allStocks =data;
        //    $scope.allStocks = data;
       // console.log($scope.data);
        /*    $.each(data,function(i,result){
                allStocks= "<tr><td>"+result['num']+"</td><td>"+result['title']+"</td><td>"+result['credate']+"</td><td>操作</td></tr>";
                $('.table').append(allStocks);
            });*/

            for(var item in allStocks){
             //   console.log(allStocks[item].stock_id);
                stock="<tr><td>"+allStocks[item].stock_id+"</td><td>"+allStocks[item].stock_name+"</td><td>"+allStocks[item].open+"</td>" +
                    "<td>"+allStocks[item].high+"</td><td>"+allStocks[item].low+"</td><td>"+allStocks[item].close+"</td>" +
                    "<td>"+allStocks[item].volumn+"</td><td>"+allStocks[item].adj_price+"</td>" +
                    "<td>"+allStocks[item].pe_ttm+"</td><td>"+allStocks[item].pb+"</td></tr>";
              $('#tableBody').append($(stock));
        //        document.getElementById('tableBody').innerHTML=stock;
            }


        })
        .
        error(function(data) {
            $scope.error = true;
            $scope.data = data || "Request failed";

        });
});