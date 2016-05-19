/**
 * Created by QiHan on 2016/5/18.
 */

var app = angular.module('myIndustryApp', []);
    app.controller('IndustryCtrl', function ($scope, $http) {

// Accepts form input
     /*   $scope.submit = function () {

            // POSTS data to webservice
            postName($scope.input);

            // GET data from webservice
            var name = getName();

            // DEBUG: Construct greeting
            $scope.greeting = 'Sup ' + name + ' !';

        };*/

        var allIndustry=[];
        $scope.url = 'http://115.159.97.98/php/index.php'; // The url of our search

            $http.post($scope.url, {"method": "getAllIndustriesService"}).
             success(function (data) {
                $scope.error = false;
                $scope.data = data;
                $scope.industry =data;

                    for(var item in industry){
                        allIndustry[item]=industry[item].industry;
       //               document.getElementById('allModule').innerHTML =   allIndustry[item]  ;
                    }

                    console.log($scope.data);

            })
                .error(function (data) {
                $scope.error = true;
                $scope.data = data;
                 $scope.industry ="error";
                  return 'error name';
            });


    });