/**
 * Created by QiHan on 2016/5/18.
 */

var app = angular.module('allIndustryApp', []);
app.controller('allIndustryCtrl', function ($scope, $http) {
    var allIndustryName=[];
    var industrys=[];
    $scope.url = 'http://115.159.97.98/php/serviceController.php'; // The url of our search

    $http.post($scope.url, {"method": "getAllIndustriesService"}).
        success(function (data) {
            $scope.error = false;
            $scope.data = data;
            $scope.industrys =data;
            console.log($scope.industrys);
            var length=0;

            for(var item in $scope.industrys) {
                length++;
            }
            for(var item in $scope.industrys) {
                if (item < length-1) {
                    allIndustryName[item] = $scope.industrys[item].industry;
                    document.write( $scope.industrys[item].industry);
                    getIndustryPure($scope.industrys[item].industry);
                }
            }
        })
        .error(function (data) {
            $scope.error = true;
            $scope.data = data;
            $scope.industry ="hno";
            return 'error name';
        });

//将行业名称一一传出得到所有属性


    function GetDateStr(AddDayCount) {
        var dd = new Date();
        dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
        var y = dd.getFullYear();
        var m = dd.getMonth()+1;//获取当前月份的日期
        var d = dd.getDate();
        return y+"-"+m+"-"+d;
    }

    function  getIndustryPure(industryName) {
        $http.post($scope.url, {
            "industry_name": "银行",
            "date": GetDateStr(-1),
            "method": "getIndustryService"
        }).success(function (data) {
                $scope.error = false;
                $scope.data = data;
                console.log($scope.data);
            })
            .error(function (data) {
                $scope.error = true;
                $scope.data = data;
                return 'error name';
            });
    }

});

/*
 close:"6.7567658698809705"
 companySum :"16"
 date : "2016-05-18"
 industry_name :"银行"
 leader :"sh600036"
 leaderName:"招商银行"
 leaderPrice:"17.82"
 leaderUpdown :"0.0194508009153318"
 max:"6.776053201650601"
 min:"6.641478105097133"
 open:"6.701217939326501"
 pure:"1534320344200"
 total :"7391759463954"
 updown:"0.002814182363252977"
 */