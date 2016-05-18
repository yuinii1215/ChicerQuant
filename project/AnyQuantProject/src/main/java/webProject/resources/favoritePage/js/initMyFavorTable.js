/**
 * Created by QiHan on 2016/5/17.
 */
function initMyFavorTable($scope, $http) {
    $scope.url = 'http://115.159.97.98/php/index.php'; // The url of our search
    //
    //// The function that will be executed on button click (ng-click="search()")
    //$scope.singleStockInfo = function() {
    $http.post($scope.url, {"username" :"张三", "method": "getMyFavorService"}).
        success(function(data, status) {
            $scope.status = status;
            $scope.data = data;
            var myFavorStocks;
            $.each(data,function(i,result){
                myFavorStocks= "<tr><td>"+result['num']+"</td><td>"+result['title']+"</td><td>"+result['credate']+"</td><td>操作</td></tr>";
             $('.table').append(myFavorStocks);
            });

        })
        .
        error(function(data, status) {
            $scope.data = data || "Request failed";
            $scope.status = status;
        });
}