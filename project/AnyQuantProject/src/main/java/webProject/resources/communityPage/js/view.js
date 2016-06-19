var app=angular.module('myApp',[]);
app.controller("SearchCtrl", function($scope, $http, MyCache) {
    $scope.url = 'http://115.159.97.98/php/serviceController.php'; // The url of our search
    
        $http.post($scope.url, {
            "method": "getShareStrategyService"
        }).success(function (data, status) {
            var temp = data;
            console.log(temp);
            var tmp=data;
            var str_cnt=0;
            $scope.usrmessages=[];
            for(item in tmp){
                if (tmp[item]!="success") {
                    if (tmp[item].strategytype) {
                        $scope.usrmessages[str_cnt++] = {
                            "textalign": "right",
                            "side": "other",
                            "userName": tmp[item].username,
                            "strName": tmp[item].strategyname,
                            "strType": tmp[item].strategytype,
                            "buypoint": tmp[item].buypoint+"%",
                            "sellpoint": tmp[item].sellpoint+"%"
                        };
                    } else {
                        $scope.usrmessages[str_cnt++] = {
                            "side": "you",
                            "textalign": "left",
                            "userName": tmp[item].username,
                            "strName": tmp[item].strategyname,
                            "strType": tmp[item].crossstr,
                            "buypoint": "黄金交叉点",
                            "sellpoint": "死亡交叉点"
                        };
                    }
                }
            }
        })
            .error(function (data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });
    // }
});
app.factory('MyCache', function ($cacheFactory) {
    return $cacheFactory('myCache');
})
