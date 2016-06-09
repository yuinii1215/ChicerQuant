var app = angular.module('eachIndustryApp', []);
app.controller('eachIndustryTableCtrl', function ($scope, $http) {

    $scope.url = 'http://115.159.97.98/php/serviceController.php'; // The url of our search

    var array=new Array();
    var count=-1;
    var length=0;
    var countOther=0;
    var allStocks=[];
    var sum=0;
    var industryLength=0;
    var index=0;
    $scope.allIndustry=[];
    $scope.singleNum=0;
    $scope.singleClose=0;
    $scope.singleOpen=0;
    $scope.singleMin=0;
    $scope.singleMax=0;
    $scope.singleTotal=0;
    $scope.singleUpDown=0;
    $scope.Up=0;
    $scope.Down=0;
    $scope.Keep=0;
//     localStorage.singleIndustryID
    console.log(localStorage.singleIndustryID);
    $http.post($scope.url, {"industry_name":localStorage.singleIndustryID,"method": "getStocksByIndustryService"}).
        success(function(data) {
            $scope.error = false;
            $scope.data = data;
            $scope.allStocks =data;
     //      console.log($scope.allStocks);

            for(var item in $scope.allStocks) {
                length++;
            }
            getEachIndustryNum(localStorage.singleIndustryID);

            for(var item in  $scope.allStocks) {
                if (item < length-1) {
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
        var day = dd.getDay();
        var show_day = new Array('星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日');
        var week = "";
        if (day == 0) {
            week = "星期日";
        } else {
            week = show_day[day - 1];
        }
        //  console.log(week);
        if(week=='星期日'){
            d=d-2;
            if(d<=0){
                m--;
                d=30+d;
            }
        }else if(week=='星期一'){
            d=d-3;
            if(d<=0){
                m--;
                d=30+d;
            }
        }else if(week=='星期六'){
            d=d-1;
            if(d<=0){
                m--;
                d=30+d;
            }
        }
           console.log(y+"-"+m+"-"+d);
        return y+"-"+m+"-"+d;
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
                var temp  =Object.keys($scope.singleStock[0]);
            //   console.log( $scope.singleStock);
          //     console.log(temp.length);

                if(temp.length>2) {
                    array[count + 1] = new Array;
                    array[count + 1][0] = stockName;
                    array[count + 1][1] = $scope.singleStock[0].stock_name;
                    array[count + 1][2] = $scope.singleStock[0].open;
                    array[count + 1][3] = $scope.singleStock[0].high;
                    array[count + 1][4] = $scope.singleStock[0].low;
                    array[count + 1][5] = $scope.singleStock[0].close;
                    array[count + 1][6] = $scope.singleStock[0].volumn;
                    array[count + 1][7] = $scope.singleStock[0].adj_price;
                    array[count + 1][8] = $scope.singleStock[0].pe_ttm;
                    array[count + 1][9] = $scope.singleStock[0].pb;
                    array[count + 1][10] = $scope.singleStock[0].industry;

                    //获得涨跌数
               var index =    $scope.singleStock[0].close-$scope.singleStock[0].open;
            //        console.log(stockName);
            //        console.log(index);
                    if(index>0){
                        $scope.Up++;
                    } else if(index<0){
                        $scope.Down++;
                    }else{
                        $scope.Keep++;
                    }
       //             console.log(  "UP:"+$scope.Up+ " "+"Down:"+ $scope.Down+ " "+ "Keep:"+$scope.Keep);
                   // count++;
                }else{
                    array[count + 1] = new Array;
                    array[count + 1][0] = stockName;
                    array[count + 1][1] = "-";
                    array[count + 1][2] = "-";
                    array[count + 1][3] = "-";
                    array[count + 1][4] = "-";
                    array[count + 1][5] = "-";
                    array[count + 1][6] = "-";
                    array[count + 1][7] = "-";
                    array[count + 1][8] = "-";
                    array[count + 1][9] = "-";
                    array[count + 1][10] ="-";
                //    countOther++;
                }
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
                            "createdRow": function ( row) {
                                $('td', row).eq(4).css( "color", "green");
                                $('td', row).eq(3).css( "color", "red");
                        //        $('td', row).eq(2).css("color", colorSet[i]);
                         //       i++;
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

    function getEachIndustryNum(industryName){
        $http.post($scope.url, {
            "industry_name":industryName,
            "date":  GetDateStr(-1),
            "method": "getIndustryService"
        }).success(function (data, status) {
            $scope.status = status;
            $scope.data = data;
            $scope.eachIndustry = data;
            $scope.singleNum=$scope.eachIndustry[0].companySum;

            var totals = $scope.eachIndustry[0].total / 100000000;
            var updowns = $scope.eachIndustry[0].updown * 100;
            var closes = $scope.eachIndustry[0].close*1;
            var opens = $scope.eachIndustry[0].open*1;
            var mins =$scope.eachIndustry[0].min*1;
            var maxs =$scope.eachIndustry[0].max*1;
            $scope.singleClose=closes.toFixed(4);
            $scope.singleOpen=opens.toFixed(4);
            $scope.singleMin=mins.toFixed(4);
            $scope.singleMax=maxs.toFixed(4);
            $scope.singleTotal=totals.toFixed(4);
            $scope.singleUpDown=updowns.toFixed(4);

            document.getElementById("elementTitle").innerHTML=industryName;
            document.getElementById("elements").innerHTML="开盘价:"+$scope.singleOpen+"&nbsp&nbsp&nbsp&nbsp"+"收盘价:"+$scope.singleClose+"<br/>"+
                "最高价:"+ $scope.singleMax+"&nbsp&nbsp&nbsp&nbsp"+"最低价:"+ $scope.singleMin+"<br/>"+"涨跌幅:"+$scope.singleUpDown+"%"+"<br/>"+"总资产（亿）:"+$scope.singleTotal;


   //         console.log( $scope.eachIndustry[0].companySum);
            })
            .error(function (data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });
    }


    //此方法获得了所有行业内股票数之和
    /*    function getEachIndustryNum(industryName){
     $http.post($scope.url, {
     "industry_name":industryName,
     "date":  GetDateStr(-1),
     "method": "getIndustryService"
     }).success(function (data, status) {
     $scope.status = status;
     $scope.data = data;
     $scope.eachIndustry=data;
     console.log( industryName+$scope.eachIndustry[0].companySum);
     sum=parseInt(sum)+parseInt($scope.eachIndustry[0].companySum);
     index++;
     if(index==industryLength-1) {
     console.log(sum);
     }
     //饼图



     })
     .error(function (data, status) {
     $scope.data = data || "Request failed";
     $scope.status = status;
     });
     }




     function getAllIndustryNum(){
     $http.post($scope.url, {
     "method": "getAllIndustriesService"
     }).success(function (data, status) {
     $scope.status = status;
     $scope.data = data;
     $scope.allIndustry=data;
     for(var item in  $scope.allIndustry) {
     industryLength++;
     }
     for(var item in $scope.allIndustry) {
     if(item<industryLength-1){
     getEachIndustryNum($scope.allIndustry[item].industry);
     }
     }
     })
     .error(function (data, status) {
     $scope.data = data || "Request failed";
     $scope.status = status;
     });
     }
     */

});