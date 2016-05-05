var app = angular.module('myApp', []);
app.controller('enterHome', function($scope) {

    $scope.enter = function() {
        $scope.location=".../nav/home.html";
    };
});