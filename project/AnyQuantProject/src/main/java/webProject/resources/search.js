
function SearchCtrl($scope, $http) {
    $scope.url = 'http://115.159.106.212/php/serviceController.php'; // The url of our search

    // The function that will be executed on button click (ng-click="search()")
    $scope.search = function() {

        // Create the http post request
        // the data holds the keywords
        // The request is a JSON request.
        $http.post($scope.url, {"date":"---", "name" : $scope.keywords, "method": "getStockByNameService","header":"http://115.159.106.212"}).
            success(function(data, status) {
                $scope.status = status;
                $scope.data = data;
                $scope.result = data; // Show result from server in our <pre></pre> element
            })
            .
            error(function(data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });
    };
}