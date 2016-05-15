
function SearchCtrl($scope, $http) {

    $scope.test={"abc":[{"date":"2016-05-04"},{"stock_name":"浦发银行"},{"open":"18.06"},{"high":"18.12"},{"low":"17.98"},{"close":"18.06"},{"volumn":"14936600"},{"adj_price":"18.06"},{"turnover":"0"},{"pe_ttm":"6.66"},{"pb":"1.03"},{"industry":"银行"}]};
    //var str1={"abc":[
    //    {"road":"成山路"},
    //    {"residentName":"叶小军"},
    //    {"householder":"叶大伟"},
    //    {"doctor":"陈天明"}]}
    $scope.url = 'http://115.159.106.212/php/serviceController.php'; // The url of our search

    // The function that will be executed on button click (ng-click="search()")
    $scope.singleStockInfo = function() {

        // Create the http post request
        // the data holds the keywords
        // The request is a JSON request.
        $http.post($scope.url, {"date":"---", "name" : $scope.keywords, "method": "getStockByNameService"}).

            success(function(data, status) {
                $scope.status = status;
                $scope.data = data;
                //data=eval("("+data+")");
                var stockInfo=data;
                //stockInfo= $.parseJSON(stockInfo);
                $scope.result = stockInfo[0]; // Show result from server in our <pre></pre> element

        })
            .
            error(function(data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });
    };

    $scope.KlineDataList = function() {

        // Create the http post request
        // the data holds the keywords
        // The request is a JSON request.
        //$name, $startdate, $enddate
        $http.post($scope.url, {"name" : "sh600000","startdate":"2015-01-01","enddate":"2015-01-10", "method": "getStockAmongDateService"}).
            success(function(data, status) {
                $scope.status = status;
                $scope.data = data;
                var stockInfo=data;
                $scope.kLineResult = stockInfo; // Show result from server in our <pre></pre> element

            })
            .
            error(function(data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });
    };
}

