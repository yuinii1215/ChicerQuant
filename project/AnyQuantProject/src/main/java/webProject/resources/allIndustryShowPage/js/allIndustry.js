/**
 * Created by QiHan on 2016/5/18.
 */

var app = angular.module('allIndustryApp', []);
app.controller('allIndustryCtrl', function ($scope, $http) {
    $scope.industryPure=[]; /*存所有行业净额*/
    $scope.url = 'http://115.159.97.98/php/serviceController.php'; // The url of our search
    var index=0;
    var industryDetail=[];
    var table="";

    $http.post($scope.url, {"method": "getAllIndustriesService"}).
        success(function (data) {
            $scope.error = false;
            $scope.data = data;
            $scope.industrys =data;
            $scope.allIndustryName=[];

            var length=0;

            for(var item in $scope.industrys) {
                length++;
            }
            for(var item in $scope.industrys) {
                if (item < length-1) {
                    $scope.allIndustryName[item] = $scope.industrys[item].industry;
            //        document.write( $scope.industrys[item].industry);
                    getIndustryPure($scope.industrys[item].industry,item)
                  //  document.write(getIndustryPure($scope.industrys[item].industry,item));
              //      console.log( $scope.industryPure[item]);
                    industryToTable($scope.industrys[item].industry);
                }
            }
       //     console.log(alert( $scope.industryPure));
            $('#allIndustryTable').append($(table));

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


    function  getIndustryPure(industryName,item) {
        $http.post($scope.url, {
            "industry_name":industryName,
            "date": "2015-03-04",
            "method": "getIndustryService"
        }).success(function (data) {
                $scope.error = false;
                $scope.data = data;
                $scope.eachIndustry=data;
            //      document.write(industryName);//亿 小数点后4位
                var pures=$scope.eachIndustry[0].pure/100000000;
                $scope.industryPure[item]= pures.toFixed(4);
             //   console.log( $scope.industryPure[item]);
             //   console.log($scope.eachIndustry[0].industry_name+"\n"+pure.toFixed(4));
            })
            .error(function (data) {
                $scope.error = true;
                $scope.data = data;
                return 'error name';
            });


    }

    function  industryToTable(industryName){
        $http.post($scope.url, {
            "industry_name":industryName,
            "date": "2015-03-04",
            "method": "getIndustryService"
        }).success(function (data) {
                $scope.error = false;
                $scope.data = data;
                $scope.industryDetail=data;
                var pures=$scope.industryDetail[0].pure/100000000;

                var totals=$scope.industryDetail[0].total/100000000;
                var updowns=$scope.industryDetail[0].updown*100;
                var leaderUpdowns=$scope.industryDetail[0].leaderUpdown*100;

                   table="<tr><td>"+$scope.industryDetail[0].industry_name+"</td><td>"+totals.toFixed(4)+"</td><td>"+updowns.toFixed(4)+"%"+"</td>" +
                        "<td>"+pures.toFixed(4)+"</td><td>"+$scope.industryDetail[0].companySum+"</td><td>"+$scope.industryDetail[0].leaderName+"</td>" +
                        "<td>"+$scope.industryDetail[0].leader+"</td><td>"+leaderUpdown.toFixed(4)+"%"+"</td>" +
                        "<td>"+$scope.industryDetail[0].leaderPrice+"</td>/tr>";
                   table+=table;
            // document.getElementById('tableBody').innerHTML=stock;
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