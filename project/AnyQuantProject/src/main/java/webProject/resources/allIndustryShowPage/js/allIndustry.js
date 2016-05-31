/**
 * Created by QiHan on 2016/5/18.
 */

var app = angular.module('allIndustryApp',[]);
app.controller('allIndustryCtrl', function ($scope, $http) {

    $scope.url = 'http://115.159.97.98/php/serviceController.php'; // The url of our search
    var index=0;
    var count=-1;
    var industryDetail=[];
    var length=0;
    var array=new Array();
    $scope.industryPure=[]; /*存所有行业净额*/
    $http.post($scope.url, {"method": "getAllIndustriesService"}).
        success(function (data) {
            $scope.error = false;
            $scope.data = data;
            $scope.industrys =data;
            $scope.allIndustryName=[];

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
       //     console.log( $scope.table);
         //   $('#allIndustryTable').html(($('#allIndustryTable').html()+ $scope.table));

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
     //   console.log(y+"-"+m+"-"+d);
        return y+"-"+m+"-"+d;
    }

    function  getIndustryPure(industryName,item) {
        $http.post($scope.url, {
            "industry_name":industryName,
            "date":  GetDateStr(-1),
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
            "date":  GetDateStr(-1),
            "method": "getIndustryService"
        }).success(function (data) {
            $scope.error = false;
            $scope.data = data;
            $scope.industryDetail = data;
     //       console.log($scope.industryDetail[0]);
            var pures = $scope.industryDetail[0].pure / 100000000;

            var totals = $scope.industryDetail[0].total / 100000000;
            var updowns = $scope.industryDetail[0].updown * 100;
            var leaderUpdowns = $scope.industryDetail[0].leaderUpdown * 100;


                array[count+1]=new Array;
                array[count+1][0]=$scope.industryDetail[0].industry_name;
                array[count+1][1]=totals.toFixed(4);
                array[count+1][2]=updowns.toFixed(4);
                array[count+1][3]=pures.toFixed(4);
                array[count+1][4]=$scope.industryDetail[0].companySum;
                array[count+1][5]=$scope.industryDetail[0].leaderName;
                array[count+1][6]=$scope.industryDetail[0].leader;
                array[count+1][7]=leaderUpdowns.toFixed(4);
                array[count+1][8]=$scope.industryDetail[0].leaderPrice;

            /*    $scope.t="<tr><td>"+$scope.industryDetail[0].industry_name+"</td><td>"+totals.toFixed(4)+"</td><td>"+updowns.toFixed(4)+"%"+"</td>" +
             "<td>"+pures.toFixed(4)+"</td><td>"+$scope.industryDetail[0].companySum+"</td><td>"+$scope.industryDetail[0].leaderName+"</td>" +
             "<td>"+$scope.industryDetail[0].leader+"</td><td>"+leaderUpdowns.toFixed(4)+"%"+"</td>" +
             "<td>"+$scope.industryDetail[0].leaderPrice+"</td></tr>";

              $scope.t = "[\"" + $scope.industryDetail[0].industry_name + "\",\"" + totals.toFixed(4) + "\",\"" + updowns.toFixed(4) + "%" + "\",\""
             + pures.toFixed(4) + "\",\"" + $scope.industryDetail[0].companySum + "\",\"" + $scope.industryDetail[0].leaderName + "\",\""
             + $scope.industryDetail[0].leader + "\",\"" + leaderUpdowns.toFixed(4) + "%" + "\",\"" + $scope.industryDetail[0].leaderPrice + "\"]" + "]";
             */
            count++;
            if (count == length-2) {
                 count = 0;
                var dataSet =array;

                $(document).ready(function() {
                    var selected = [];
                    $('#table').DataTable( {
                        "rowCallback": function( row, data ) {
                            if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                                $(row).addClass('selected');
                            }
                        },
                        data:dataSet,
                        columns: [
                            { title: "行业名称"},
                            { title: "总资产（亿）"},
                            { title: "涨跌幅"},
                            { title: "净额（亿）"},
                            { title: "公司家数"},
                            { title: "领涨股名称"},
                            { title: "领涨股代码"},
                            { title: "涨跌幅"},
                            { title: "当前价（元）"},
                        ]
                    } );
                    $('#table tbody').on('click', 'tr', function () {
                        var id = this.id;
                        var index = $.inArray(id, selected);

                        if ( index === -1 ) {
                            selected.push( id );

                            var rowIndex=$(this).index();
                            console.log( rowIndex);
                            localStorage.singleIndustryID=$(this).eq(0)[0].firstChild.textContent;
                            console.log( localStorage.singleIndustryID);
                            window.location.href="../singleIndustryPage/singleIndustryPage.html";
                        } else {
                            selected.splice( index, 1 );
                        }

                        $(this).toggleClass('selected');
                    } );
                } );
             //   console.log($scope.table);
            }

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