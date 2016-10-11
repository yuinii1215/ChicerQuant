var app=angular.module('myApp',[]);
var codelist;
app.controller("SearchCtrl", function($scope, $http, MyCache) {
    $scope.url = 'http://anyquant.net:15000/php/serviceController.php';
        $http.post($scope.url, {
            "method": "getShareStrategyService"
        }).success(function (data, status) {
            var temp = data;
            var tmp=data;
            var str_cnt=0;
            $scope.usrmessages=[];
            for(item in tmp){
                if (tmp[item]!="success") {
                    if (item%2==0) {
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
            // console.log($scope.usrmessages);

        })
            .error(function (data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });

    $http.post($scope.url, {
        "method": "getAllRecordsService"
    }).success(function (data, status) {
        var temp = data;
        var tmp=data;
        var str_cnt=0;
        $scope.strmessages=[];

        var array = new Array();

        for(item in tmp){
            $scope.strmessages[item]={
                "ratioVal":tmp[item].ratio,
                "usernameVal":tmp[item].username









            };
            array[item][0] = item;
            array[item][2] = tmp[item].ratio;

            array[item][1] = tmp[item].username;
            array[item][3] = 10-item;
            array[item][4] = "付费查看";
            
            $http.post($scope.url, {
                "method": "getCodeService",
                "username":""+tmp[item].username
            }).success(function (data, status) {
                var temp=data;
                console.log(temp);
                codelist[item]=temp;

            })
                .error(function (data, status) {
                    $scope.data = data || "Request failed";
                    $scope.status = status;
                });
        }



        var dataSet = array;

        $(document).ready(function () {
            var selected = [];
            $('#table').DataTable({
                "rowCallback": function (row, data) {
                    if ($.inArray(data.DT_RowId, selected) !== -1) {
                        $(row).addClass('selected');
                    }
                },
                data: dataSet,
                columns: [
                    {title: "排名"},
                    {title: "用户名"},
                    {title: "收益率"},
                    {title: "定价金额"},
                    {title: "策略内容"}

                ]
            });
        })


        console.log(codelist);
        console.log($scope.strmessages);
    })
        .error(function (data, status) {
            $scope.data = data || "Request failed";
            $scope.status = status;
        });


    $scope.buyStr=function(indexVal){
        $http.post($scope.url, {
            "method": "getCode",
            "username":""+indexVal
        }).success(function (data, status) {
            var temp = data;
            console.log(temp);
        })
            .error(function (data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });
    }

});
app.factory('MyCache', function ($cacheFactory) {
    return $cacheFactory('myCache');
});

$('#gotoStrategyBtn').click(function (e) {
    $('#competetionBoard').fadeOut(300);
    $('#strategyBoard').fadeTo(1, 300);
});

$('#gotoCompetetionBtn').click(function (e) {
    $('#strategyBoard').fadeOut(300);
    $('#competetionBoard').fadeTo(1, 300);
});

function buyStr(indexVal){
    $http.post($scope.url, {
        "method": "getCode",
        "username":""+indexVal
    }).success(function (data, status) {
        var temp = data;
        console.log(temp);
    })
        .error(function (data, status) {
            $scope.data = data || "Request failed";
            $scope.status = status;
        });
}

var strList=["买入点: kdj黄金交叉点"+"<br/>"+"卖出点: kdj死亡交叉点",
             "买入点: 上涨0.5%"+"" + "<br/>"+"买入点: 下降0.5%",
             "买入点: 上涨0.8%"+"<br/>"+"买入点: 下降0.4%",
             "买入点: kdj&macd黄金交叉点"+"<br/>"+"卖出点: kdj&macd死亡交叉点",
             "买入点: 上涨0.5%"+"<br/>"+"买入点: 下降0.5%",
             "买入点: kdj黄金交叉点"+"<br/>"+"卖出点: kdj死亡交叉点",
             "买入点: 上涨0.5%"+"<br/>"+"买入点: 下降0.5%",
             "买入点: 上涨0.8%"+"<br/>"+"买入点: 下降0.4%",
             "买入点: kdj&macd黄金交叉点"+"<br/>"+"卖出点: kdj&macd死亡交叉点",
             "买入点: 上涨0.5%"+"<br/>"+"买入点: 下降0.5%",
];
var selectedBtnID;

$('.insertBtn').click(function(){
    // 下面这行代码就是你要的ID属性
    selectedBtnID=$(this).attr("value");
    var price=10.00-selectedBtnID;
    conductBuyStrategy(price);
});


function conductBuyStrategy(price){
    swal({
        title: "您将支付 "+price+".00 元",
        text: "您确认付费查看本策略吗?",
        type: "warning",
        showCancelButton: true,
        cancelButtonText: "取消",
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定" ,
        closeOnConfirm: false }, function(){
        // buyStr(1);
        swal("支付成功!", "您可以查看该策略内容!", "success");
        $('#insertBtn'+selectedBtnID).html(strList[selectedBtnID-1]);
        $('#insertBtn'+selectedBtnID).css("color","greenyellow");
    });

}
