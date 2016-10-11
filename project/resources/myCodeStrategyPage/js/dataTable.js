/**
 * Created by QiHan on 2016/5/17.
 */
//jQuery(window).load(function() {
//
//    $.get('../result.json').done(function(data){
//
//        console.log(data);
//        //
//
//
//    });
//});

var app = angular.module('submit', []);
    app.controller('subcon', function ($scope, $http) {
        $scope.url = 'http://anyquant.net:15000/php/serviceController.php'; // The url of our search
        $scope.sub=function (){
           $(".form-datetime").datepicker({
                setDate: new Date(),
                autoclose: true,
                format: 'yyyy-mm-dd'
           });
            $("#start_time").datepicker("update", new Date());
            $("#end_time").datepicker("update", new Date());

            $('.selectpicker').selectpicker();

           var $btn = $(".run_algo_click").button("loading");
          //     console.log("click");
              var code = document.getElementById("markdown-editor").innerHTML;

               var start = $("#start_time").val();
               var end = $("#end_time").val();
               start="2015-01-01";
               end="2016-01-01";
          //     console.log("code:"+code);
          //     console.log("start:"+start);
          //     console.log("end:"+end);
            var username;
            if(localStorage.username=""){
                username="";
            }else {
                username=localStorage.username;
            }
            username="cx";
            var array=new Array();
            var count=-1;
            var length=0;
console.log("code:"+code);
          $http.post($scope.url, {
            "username": username,
           "startdate":start ,
           "enddate":end,
           "codestr":code,
           "method": "AService"
        }).success(function (data) {
              $scope.data=data;
              var temp  =$scope.data;
              console.log("data:"+data);
           $btn.button('reset');
              //  for (var item in  $scope.data) {
              //      length++;
              //  }
              //if(temp.length>2) {
              //                        array[count + 1] = new Array;
              //                        array[count + 1][0] = $scope.data[0].alpha;
              //                        array[count + 1][1] = $scope.data[0].sharpe;
              //                        array[count + 1][2] = $scope.data[0].annualized_returns;
              //                        array[count + 1][3] = $scope.data[0].benchmark_annualized_returns;
              //                        array[count + 1][4] = $scope.data[0].benchmark_daily_returns;
              //                        array[count + 1][5] = $scope.data[0].benchmark_total_returns;
              //                        array[count + 1][6] = $scope.data[0].daily_returns;
              //                        array[count + 1][7] = $scope.data[0].total_returns;
              //
              //                        //          count++;
              //                    } else{
              //                        array[count + 1] = new Array;
              //                        array[count + 1][0] = "-";
              //                        array[count + 1][1] = "-";
              //                        array[count + 1][2] =  "-";
              //                        array[count + 1][3] =  "-";
              //                        array[count + 1][4] =  "-";
              //                        array[count + 1][5] =  "-";
              //                        array[count + 1][6] =  "-";
              //                        array[count + 1][7] =  "-";
              //                        //     countOther++;
              //                    }
              //                    count++;
              //
              //                    if(count==length-2){
              //                        var dataSet = array;
              //
              //                        $(document).ready(function () {
              //                            var selected = [];
              //                            $('#table').DataTable({
              //                                "rowCallback": function (row, data) {
              //                                    if ($.inArray(data.DT_RowId, selected) !== -1) {
              //                                        $(row).addClass('selected');
              //                                    }
              //                                },
              //                                data: dataSet,
              //                                columns: [
              //                                {title: "阿尔法"},
              //                                {title: "夏普比率"},
              //                                {title: "年回报收益"},
              //                                {title: "基准年回报收益"},
              //                                {title: "基准日回报"},
              //                                {title: "基准总回报"},
              //                                {title: "日回报"},
              //                                {title: "总回报"},
              //                                ]
              //                       });
              //                    });
              //             }
              }).error(function (data) {
           $scope.data = data || "Request failed";
           $scope.status = status;
              $btn.button('reset');
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
    //                $scope.data = data;
    //
    //                //      console.log( $scope.data);
    //
    //                var temp  =Object.keys(  $scope.data[0] );
    //                //        console.log( $scope.data);
    //                //         console.log(temp.length);
    //
    //                if(temp.length>2) {
    //                    array[count + 1] = new Array;
    //                    array[count + 1][0] = stockName;
    //                    array[count + 1][1] = $scope.data[0].stock_name;
    //
    //                    array[count + 1][2] = $scope.data[0].open;
    //                    array[count + 1][3] = $scope.data[0].high;
    //                    array[count + 1][4] = $scope.data[0].low;
    //                    array[count + 1][5] = $scope.data[0].close;
    //                    array[count + 1][6] = $scope.data[0].volumn;
    //                    array[count + 1][7] = $scope.data[0].adj_price;
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
    //                            {title: "阿尔法"},
    //                            {title: "夏普比率"},
    //                            {title: "年回报收益"},
    //                            {title: "基准年回报收益"},
    //                            {title: "基准日回报"},
    //                            {title: "基准总回报"},
    //                            {title: "日回报"},
    //                            {title: "总回报"},
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
