var app = angular.module('eachIndustryApp', []);
app.controller('eachIndustryTableCtrl', function ($scope, $http) {

    $scope.url = 'http://115.159.97.98/php/serviceController.php'; // The url of our search

    var array=new Array();
    var count=-1;
    var length=0;
    var allStocks=[];
//     localStorage.singleIndustryID
    console.log(  localStorage.singleIndustryID);
    $http.post($scope.url, {"industry_name":localStorage.singleIndustryID,"method": "getStocksByIndustryService"}).
        success(function(data) {
            $scope.error = false;
            $scope.data = data;
            $scope.allStocks =data;
            console.log($scope.allStocks);

            for(var item in $scope.allStocks) {
                length++;
            }
      //      console.log(length);
            for(var item in  $scope.allStocks) {
                if (item < length-1) {
           //         console.log($scope.allStocks[item].stock_id);
                    getSingleDetail($scope.allStocks[item].stock_id);
                }
            }

        })
        .
        error(function(data) {
            $scope.error = true;
            $scope.data = data || "Request failed";

        });


    function GetDateStr(AddDayCount) {
        var dd = new Date();
        dd.setDate(dd.getDate() + AddDayCount);//获取AddDayCount天后的日期
        var y = dd.getFullYear();
        var m = dd.getMonth() + 1;//获取当前月份的日期
        var d = dd.getDate();
        return y + "-" + m + "-" + d;
    }

    function getSingleDetail(stockName) {
        $http.post($scope.url, {
            "name": stockName,
            "date": GetDateStr(-1),
            "method": "getStockByNameService"
        }).success(function (data, status) {
                $scope.status = status;
                $scope.data = data;
                $scope.singleStock=data;
console.log(  $scope.singleStock);
                array[count+1]=new Array;
                array[count+1][0]=stockName;
                array[count+1][1]=$scope.singleStock[0].stock_name;
                array[count+1][2]=$scope.singleStock[0].open;
                array[count+1][3]=$scope.singleStock[0].high;
                array[count+1][4]=$scope.singleStock[0].low;
                array[count+1][5]=$scope.singleStock[0].close;
                array[count+1][6]=$scope.singleStock[0].volumn;
                array[count+1][7]=$scope.singleStock[0].adj_price;
                array[count+1][8]=$scope.singleStock[0].pe_ttm;
                array[count+1][9]=$scope.singleStock[0].pb;
                array[count+1][10]=$scope.singleStock[0].industry;

                count++;
                if(count==length-2){
                    count = 0;
                    var dataSet =array;

                    $(document).ready(function() {
                        var selected = [];
                        $('#table').DataTable( {
                            // 设置红绿
                            /*              "createdRow": function ( row, data, index ) {
                             if ( data[5].replace(/[\$,]/g, '') * 1 < 150000 ) {
                             $('td', row).eq(5).css( "color", "red");
                             }
                             },
                             */
                            "rowCallback": function( row, data ) {
                                if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                                    $(row).addClass('selected');
                                }
                            },
                            data:dataSet,
                            columns: [
                                {title: "股票代码"},
                                {title: "股票简称"},
                                {title: "开盘价"},
                                {title: "最高价"},
                                {title: "最低价"},
                                {title: "收盘价"},
                                {title: "成交量"},
                                {title: "后复权价"},
                                {title: "市盈率"},
                                {title: "市净率"},
                                {title: "所属行业"},
                            ]
                        } );
                        $('#table tbody').on('click', 'tr', function (row) {
                            var id = this.id;
                            var index = $.inArray(id, selected);
                            console.log(index);

                            if ( index === -1 ) {
                                selected.push( id );

                                var rowIndex=$(this).index();
                                console.log( rowIndex);

                                localStorage.singleStockID=$(this).eq(0)[0].firstChild.textContent;
                                console.log( localStorage.singleStockID);
                                window.location.href="../singleStockPage/singleStockPage.html";
                            } else {
                                selected.splice( index, 1 );
                            }

                            $(this).toggleClass('selected');
                        } );
                    } );
                }
            })
            .error(function (data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });
    }
});