var app = angular.module('DateApp', []);
app.controller('dateCtrl', function ($scope, $http) {


    $scope.url = 'http://115.159.97.98/php/serviceController.php'; // The url of our search

    $http.post($scope.url, {"method": "getLatestDateService"}).success(function (data) {
            $scope.latestDate = data;
            console.log($scope.latestDate[1]);
        localStorage.latestDate=$scope.latestDate[1];
        })
        .error(function (data) {
            $scope.error = true;
            $scope.data = data;
            return 'error name';
        });
});