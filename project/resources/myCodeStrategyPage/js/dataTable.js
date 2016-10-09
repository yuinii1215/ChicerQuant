/**
 * Created by QiHan on 2016/5/17.
 */
jQuery(window).load(function() {

    $.get('../result.json').done(function(data){

        console.log(data);
        //


    });
});

    //1 2 0 5 15 22 9 11




    //function getSingleDetail(stockName) {
    //        $http.post($scope.url, {
    //            "name": stockName,
    //            "date": localStorage.latestDate,
    //            "method": "getStockByNameService"
    //        }).success(function (data, status) {
    //                $scope.status = status;
    //                $scope.data = data;
    //                $scope.singleStock = data;
    //
    //                //      console.log( $scope.singleStock);
    //
    //                var temp  =Object.keys(  $scope.singleStock[0] );
    //                //        console.log( $scope.singleStock);
    //                //         console.log(temp.length);
    //
    //                if(temp.length>2) {
    //                    array[count + 1] = new Array;
    //                    array[count + 1][0] = stockName;
    //                    array[count + 1][1] = $scope.singleStock[0].stock_name;
    //
    //                    array[count + 1][2] = $scope.singleStock[0].open;
    //                    array[count + 1][3] = $scope.singleStock[0].high;
    //                    array[count + 1][4] = $scope.singleStock[0].low;
    //                    array[count + 1][5] = $scope.singleStock[0].close;
    //                    array[count + 1][6] = $scope.singleStock[0].volumn;
    //                    array[count + 1][7] = $scope.singleStock[0].adj_price;
    //
    //                    //          count++;
    //                } else{
    //                    array[count + 1] = new Array;
    //                    array[count + 1][0] = "-";
    //                    array[count + 1][1] = "-";
    //
    //                    array[count + 1][2] =  "-";
    //                    array[count + 1][3] =  "-";
    //                    array[count + 1][4] =  "-";
    //                    array[count + 1][5] =  "-";
    //                    array[count + 1][6] =  "-";
    //                    array[count + 1][7] =  "-";
    //                    //     countOther++;
    //                }
    //                count++;
    //
    //                if(count==length-2){
    //                    var dataSet = array;
    //
    //                    $(document).ready(function () {
    //                        var selected = [];
    //                        $('#table').DataTable({
    //                            // 设置红绿
    //                            /*              "createdRow": function ( row, data, index ) {
    //                             if ( data[5].replace(/[\$,]/g, '') * 1 < 150000 ) {
    //                             $('td', row).eq(5).css( "color", "red");
    //                             }
    //                             },
    //                             */
    //                            "rowCallback": function (row, data) {
    //                                if ($.inArray(data.DT_RowId, selected) !== -1) {
    //                                    $(row).addClass('selected');
    //                                }
    //                            },
    //                            data: dataSet,
    //                            columns: [
    //                                {title: "年化收益率"},
    //                                {title: "基准年化收益率"},
    //                                {title: "阿尔法"},
    //                                {title: "贝塔"},
    //                                {title: "夏普比率"},
    //                                {title: "收益波动比率"},
    //                                {title: "信息比率"},
    //                                {title: "最大回撤"},
    //                            ]
    //                        });
    //
    //                    });
    //                }
    //            })
    //            .error(function (data, status) {
    //                $scope.data = data || "Request failed";
    //                $scope.status = status;
    //            });
    //    }
