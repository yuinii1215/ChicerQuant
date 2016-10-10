/**
 * Created by QiHan on 2016/5/17.
 */
jQuery(window).load(function() {

    $.get('../result.json').done(function(data){

        console.log(data);
        //


    });
});

var app = angular.module('submit', []);
    app.controller('subcon', function ($scope, $http) {
        $scope.url = 'http://anyquant.net:15000/php/serviceController.php'; // The url of our search
        $scope.sub=function (){
          alert('aaa');

          // var $btn = $(".run_algo_click").button("loading");
          //     console.log("click");
              var code = document.getElementById("editor").innerText;
          //
          // //    var code = editor.getValue();
          //     var start = $("#start_time").val();
          //     var end = $("#end_time").val();
          //     start="2015-01-01";
          //     end="2016-01-01";
          //     console.log("code:"+code);
          //     console.log("start:"+start);
          //     console.log("end:"+end);

          $http.post($scope.url, {
            "username": '',
           "startdate":'2016-01-09' ,
           "enddate":'2016-06-01',
           "codestr":code,
           "method": "AService"
        }).success(function (data) {

          console.log(data);


        }).error(function (data) {
           $scope.data = data || "Request failed";
           $scope.status = status;
        });
      }
    });




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
