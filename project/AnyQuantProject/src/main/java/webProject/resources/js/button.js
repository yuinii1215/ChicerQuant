var app = angular.module("myApp", []);
app.controller("enterHome", function($scope) {
	$scope.hi = "122";
    $scope.enter = function() {
        $scope.location="../homePage/home.html";
    };
});