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


            var startDate=document.getElementById("start_time").value;
            var endDate=document.getElementById("end_time").value;
            var date  = startDate.split("/");
            var date1  = endDate.split("/");
            console.log(date);
            startDate = date[2]+"-"+date[1]+"-"+date[1];
            endDate = date1[2]+"-"+date1[1]+"-"+date1[1];
            console.log("startDate:"+startDate+"endDate:"+endDate);

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
            if(localStorage.userName==""){
                username="";

                $.extend($.gritter.options, {
                    time: 4000,
                });
                // clean the wrapper position class
                $('#gritter-notice-wrapper').attr('class', '');
                // global setting override
                $.extend($.gritter.options, {
                    position: '' + $(this).attr('id') + '' // possibilities: bottom-left, bottom-right, top-left, top-right
                });
                $.gritter.options.position = "bottom-right";
                $.gritter.add({
                    title: $(this).find('span.title').text(), // could be simpler, just for demo purposes
                    text: "您好！" + "</br>" + "请先返回主页登录",
                });


            }else {
                username=localStorage.userName;
                var last_total=0;
                var array=new Array();
                var count=-1;
                var length=0;
                console.log("code:"+code);
                $http.post($scope.url, {
                    "username": username,
                    "startdate":startDate ,
                    "enddate":endDate,
                    "codestr":code,
                    "method": "AService"
                }).success(function (data) {
                    $scope.data=data;
                    console.log("data:"+$scope.data);

                    $btn.button('reset');
                    for (var item in  $scope.data) {
                        length++;

                    }
                    var temp  =Object.keys(  $scope.data );


                    if(temp.length>1){
                        for( var i=0;i< temp.length;i++){
                            //$scope.data["2016-07-01T00:00:00.000Z"];
                            array[count + 1] = new Array;
                            array[count + 1][1] = $scope.data[temp[i]].alpha;
                            array[count + 1][2] = $scope.data[temp[i]].sharpe;
                            array[count + 1][3] = $scope.data[temp[i]].annualized_returns;
                            array[count + 1][4] = $scope.data[temp[i]].benchmark_annualized_returns;
                            array[count + 1][5] = $scope.data[temp[i]].benchmark_daily_returns;
                            array[count + 1][6] = $scope.data[temp[i]].benchmark_total_returns;
                            array[count + 1][7] = $scope.data[temp[i]].daily_returns;
                            array[count + 1][8] = $scope.data[temp[i]].total_returns;
                            array[count + 1][0] = temp[i].substring(0,10);
                            last_total=$scope.data[temp[i]].total_returns;
                            count++;
                        }
                    } else{
                        array[count + 1] = new Array;
                        array[count + 1][0] = "-";
                        array[count + 1][1] = "-";
                        array[count + 1][2] =  "-";
                        array[count + 1][3] =  "-";
                        array[count + 1][4] =  "-";
                        array[count + 1][5] =  "-";
                        array[count + 1][6] =  "-";
                        array[count + 1][7] =  "-";
                        array[count + 1][8] =  "-";
                        //     countOther++;
                        count++;
                    }

                    if(count==length-1){
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
                                    {title: "日期"},
                                    {title: "阿尔法"},
                                    {title: "夏普比率"},
                                    {title: "年回报收益"},
                                    {title: "基准年回报收益"},
                                    {title: "基准日回报"},
                                    {title: "基准总回报"},
                                    {title: "日回报"},
                                    {title: "总回报"},
                                ]
                            });
                        });
                        $http.post($scope.url, {
                            "username": username,
                            "ratio": last_total,
                            "method": "saveRecordService"
                        }).success(function(data,status){
                            // alert('save record');
                        }).error(function(data){
                            // alert('save failed');
                        });

                    }
                }).error(function (data) {
                    $scope.data = data || "Request failed";
                    $scope.status = status;
                    $btn.button('reset');
                });



            }

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
