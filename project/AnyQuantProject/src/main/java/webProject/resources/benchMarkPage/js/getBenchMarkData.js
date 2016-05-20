var app = angular.module('benchMarkApp', []);
app.controller('benchMarkCtrl', function ($scope, $http) {

    var benchMark=[];
    $scope.url = 'http://115.159.97.98/php/serviceController.php'; // The url of our search

    $http.post($scope.url, {"name":"hs300","date":"2016-05-18","method": "getBenchMarkByNameService"}).
        success(function (data) {
            $scope.error = false;
            $scope.data = data;
            $scope.benchMark =data;
            console.log(  $scope.benchMark);
     /*       adj_price:"3068.04"
            benchmark_id : "hs300"
            benchmark_name:"沪深300"
            close: "3068.04"
            date:"2016-05-18"
            high :"3072.61"
            low:"3039.46"
            open: "3071.53"
            volumn :"-318397692"*/


        })
        .error(function (data) {
            $scope.error = true;
            $scope.data = data;
            $scope.industry ="hno";
            return 'error name';
        });

    $http.post($scope.url, {"name":"","startdate":"2015-01-01","enddate":"2015-12-15","method":"getBenchMarkAmongDateService"}).
        success(function (data) {
            $scope.error = false;
            $scope.data = data;
            $scope.benchMark =data;
     //       console.log( $scope.benchMark);


        })
        .error(function (data) {
            $scope.error = true;
            $scope.data = data;
            $scope.industry ="hno";
            return 'error name';
        });


});