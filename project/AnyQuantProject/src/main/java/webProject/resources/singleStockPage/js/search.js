
function SearchCtrl($scope, $http) {


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
        $http.post($scope.url, {"name" : $scope.keywords,"startdate":"2015-01-01","enddate":"2015-01-10", "method": "getStockAmongDateService"}).
            success(function(data, status) {
                $scope.status = status;
                $scope.data = data;
                //data=eval("("+data+")");
                var stockInfo=data;
                //stockInfo= $.parseJSON(stockInfo);
                $scope.kLineResult = stockInfo; // Show result from server in our <pre></pre> element

            })
            .
            error(function(data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });
    };
}

