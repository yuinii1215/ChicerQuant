// (function () {
//     $('.hider').click(function () {
//         return $(this).parent('.message').removeClass('blur');
//     });
// }.call(this));

var app=angular.module('myApp',[]);
app.controller("SearchCtrl", function($scope, $http, MyCache) {
    console.log("tester1");
    $scope.url = 'http://115.159.97.98/php/serviceController.php'; // The url of our search

    $scope.test=function() {
        console.log("tester2");
        $http.post($scope.url, {
            "method": "getShareStrategyService"
        }).success(function (data, status) {
            var temp = data;
            console.log("tester2");
            console.log(temp);
        })
            .error(function (data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });
    }
});
