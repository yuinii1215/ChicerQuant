/**
 * Created by QiHan on 2016/5/17.
 */
function initMyFavorTable($scope, $http) {
    $scope.url = 'http://115.159.97.98/php/serviceController.php'; // The url of our search


    $http.post($scope.url, {"username": "cx", "method": "getMyFavorService"}).success(function (data, status) {
            $scope.status = status;
            $scope.data = data;


        })
        .error(function (data, status) {
            $scope.data = data || "Request failed";
            $scope.status = status;
        });

    function GetDateStr(AddDayCount) {
        var dd = new Date();
        dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
        var y = dd.getFullYear();
        var m = dd.getMonth()+1;//获取当前月份的日期
        var d = dd.getDate();
        return y+"-"+m+"-"+d;
    }


    function getSingleDetail(stockName) {
            $http.post($scope.url, {
                "name":stockName,
                "date":GetDateStr(-1),
                "method": "getStockByNameService"
            }).success(function (data, status) {
                    $scope.status = status;
                    $scope.data = data;


                })
                .error(function (data, status) {
                    $scope.data = data || "Request failed";
                    $scope.status = status;
                });


        }

    getStockByNameService($name, $date)


}