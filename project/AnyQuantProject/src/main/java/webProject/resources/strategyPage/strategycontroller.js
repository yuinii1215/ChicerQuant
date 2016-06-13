var app=angular.module("app",[]);




app.controller("strategycontroller",function($scope,$http){

  $scope.change=function(){
    var stockid=$scope.stockid;
    var nowDate=new Date();
    var year=nowDate.getFullYear();
    var month=nowDate.getMonth()+1;
    var day=nowDate.getDate();

    if (stockid==undefined) {
      stockid='sh600315';
      $scope.stockid='sh600315'
    }
    var startdate='startdate='+year+'-'+(month-2)+'-'+day;
    var enddate='enddate='+year+'-'+month+'-'+day;
    var name='name='+$scope.stockid;
    ER(name,startdate,enddate);
    NormalTest(name,startdate,enddate);
    Risk(name,startdate,enddate);
  };

  $scope.timechange=function(){
    var stockid=$scope.stockid;
    var nowDate=new Date();
    var year=nowDate.getFullYear();
    var month=nowDate.getMonth()+1;
    var day=nowDate.getDate();

    if (stockid==undefined) {
      stockid='sh600315';
      $scope.stockid='sh600315'
    }
    var startdate='startdate='+year+'-'+(month-2)+'-'+day;
    var enddate='enddate='+year+'-'+month+'-'+day;
    var name='name='+$scope.stockid;
    ER_time(name,startdate,enddate);
    acfpacf(name,startdate,enddate);
    balanceTest(name,startdate,enddate);
    finalPredict(name,startdate,enddate);
  }

  $scope.change();

  function finalPredict(name,startdate,enddate) {
    var url='http://localhost:8020/strategy/getARMA?';
    url=url+name+'&'+startdate+'&'+enddate;
    $http.get(url).success(function(response,status){
      var e=response['e'];
      var predict=response['predict'];
      var ljungbox=response['ljungbox'];
      //predict
      var predictx=[];
      var predicty=[];
      for (var obj in predict) {
        predictx.push(predict[obj]['date']);
        var ttt=Number(predict[obj]['er'])*100;
        predicty.push(Number(ttt.toFixed(2)));
      }
      var predictopt={
        title: {
            left: 'center',
            text: '时序预测图',
            subtext: '后三天为预测值'
        },
        legend: {
            top: 'bottom',
            data:['Earned Value']
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            animation: true
          }
        },
        toolbox: {
            show: true,
            feature: {
                dataView: {show: true, readOnly: true},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: predictx
        },
        yAxis: {
            type: 'value',
            boundaryGap: false,
            axisLabel: {
                 formatter: function (val) {
                     return val + '%';
                 }
             }
        },

        series: [
            {
                name:'Earned Value',
                type:'line',
                smooth:true,
                data: predicty,
                markPoint: {
                  data: [
                      {type: 'max', name: '最大值'},
                      {type: 'min', name: '最小值'}
                  ]
              },
              markLine: {
                  data: [
                      {type: 'average', name: '平均值'}
                  ]
              }
            }
        ]
      };
      var predictchart=echarts.init(document.getElementById('time_predict'));
      predictchart.setOption(predictopt);
      //Q-Q
      var qqsrc=e['evalue'];
      var epoint=[];
      for (var obj in qqsrc) {
        var etemp=[];
        var t1=Number(qqsrc[obj]['x']);

        etemp.push(t1);
        var t2=Number(qqsrc[obj]['y']);

        etemp.push(t2);
        epoint.push(etemp);
      }
      var eopt={
        title : {
          text: "残差正态分布检验Q-Q图",
          subtext: 'powered by chicer'
        },
        legend: {
          data: ['Q-Q点'],
          left: 'right'
        },
        tooltip : {
        trigger: 'axis',
        showDelay : 5,

        axisPointer:{
            show: true,
            type : 'cross',
            lineStyle: {
                type : 'dashed',
                width : 1
            }
        }
      },
        xAxis : [
        {
            type : 'value',
            scale:true,
            splitLine: {
                lineStyle: {
                    type: 'dashed'
                }
            }
        }
        ],
        yAxis : [
        {
            type : 'value',
            scale:true,
            splitLine: {
                lineStyle: {
                    type: 'dashed'
                }
            }
        }
        ],
        series:[
          {
            name:'Q-Q point',
            type:'scatter',
            data:epoint
          }
        ]
      };
      var eechart=echarts.init(document.getElementById('time_qq'));
      eechart.setOption(eopt);
      //ljungbox
      var lbqx=[];
      var lbqy=[];
      for (var obj in ljungbox) {
        lbqx.push(Number(obj)+1);
        if (obj == '0') {
          lbqy.push(Number(ljungbox[obj])/100);
        }else {
          lbqy.push(Number(ljungbox[obj]));
        }

      }
      var lbqopt={
        title : {
          text: 'LBQ检验结果图',
          subtext: 'powered by chicer'
        },
        tooltip : {
          trigger: 'axis'
        },
        legend: {
          data:['LBQ']
        },
        toolbox: {
          show : true,
        feature : {
            dataView : {show: true, readOnly: true},
            magicType : {show: true, type: ['line', 'bar']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
      },
    xAxis : [
        {
            data : lbqx
        }
    ],
    yAxis : [
        {
            type : 'value',
            boundaryGap: false
        }
    ],
    series:[
      {
           name:'LBQ',
           type:'bar',
           data:lbqy,

           markLine : {
               data : [
                   {type : 'average', name: '平均值'}
               ]
           }
       }
    ]
      };
      var lbqchart=echarts.init(document.getElementById('time_lbq'));
      lbqchart.setOption(lbqopt);
      $scope.time_normal=(Number(e['normal'])*100).toFixed(2);
      $scope.time_dw=Number(e['watson']);
    });
  }

  function balanceTest(name,startdate,enddate) {
    var url='http://localhost:8020/strategy/getBalanceTest?';
    url=url+name+'&'+startdate+'&'+enddate;
    $http.get(url).success(function(response,status){
      var p=Number(response['p']);
      var ten=Number(response['10%']);
      var five=Number(response['5%']);
      var one=Number(response['1%']);
      var max,split,value;
      if (p<=one) {
        max=10;
        split=10;
        value=1-(one-p)/100;

      }else if (p<=five) {
        max=10;
        split=10;
        value=1+4*(p-one)/(five-one);
      }else if (p<=ten) {
        max=10;
        split=10;
        value=5+5*(p-five)/(ten-five);
      }else{
        max=100;
        split=10;
        value=10+(p-ten)/10;
      }
      value/=100;
      value=value.toFixed(4);
      var balanceopt={
        title:{
          name:'拒绝域'
        },
        tooltip : {
        formatter: "{a} <br/>{b} : {c}%"
        },
        toolbox: {
        feature: {
            restore: {},
            saveAsImage: {}
        }
        },
    series: [
        {
            name: '检验结果',
            type: 'gauge',
            detail: {formatter:'{value}%'},
            min:0,
            max:max,
            startAngle: 225,
            endAngle: -45,
            splitNumber: split,
            data: [{value: value}]
        }
    ]
      };
      var balancechart=echarts.init(document.getElementById('balance'));
      balancechart.setOption(balanceopt);
    });

  }

  function acfpacf(name,startdate,enddate) {
    var url='http://localhost:8020/strategy/getRelate?';
    url=url+name+'&'+startdate+'&'+enddate;
    $http.get(url).success(function(response,status){
      var p_value=response['p_value'];
      var acf=response['acf'];
      var pacf=response['pacf'];
      var x=[];
      var acfy=[];
      var pacfy=[];
      for (var variable in acf) {
        x.push(Number(variable));
        acfy.push(Number(acf[variable]));
        pacfy.push(Number(pacf[variable]));
      }
      var acfopt={
        title : {
          text: $scope.stockid+' 自相关系数表',
          subtext: '取置信区间95%'
        },
        tooltip : {
          trigger: 'axis'
        },
        legend: {
          data:['自相关系数']
        },
        toolbox: {
          show : true,
        feature : {
            dataView : {show: true, readOnly: true},
            magicType : {show: true, type: ['line', 'bar']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
      },
    xAxis : [
        {
            data : x
        }
    ],
    yAxis : [
        {
            type : 'value',
            boundaryGap: false
        }
    ],
    series:[
      {
           name:'自相关系数',
           type:'bar',
           data:acfy,

           markLine : {
               data : [
                   {type : 'average', name: '平均值'},
                   {yAxis: p_value, name :'p value'},
                   {yAxis: -p_value, name :'- p value'}
               ]
           }
       }
    ]
      };
      var acfchart=echarts.init(document.getElementById('acf_chart'));
      acfchart.setOption(acfopt);
      var pacfopt={
        title : {
          text: $scope.stockid+' 偏自相关系数表',
          subtext: '取置信区间95%'
        },
        tooltip : {
          trigger: 'axis'
        },
        legend: {
          data:['偏自相关系数']
        },
        toolbox: {
          show : true,
        feature : {
            dataView : {show: true, readOnly: true},
            magicType : {show: true, type: ['line', 'bar']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
      },
    xAxis : [
        {
            data : x
        }
    ],
    yAxis : [
        {
            type : 'value',
            boundaryGap: false
        }
    ],
    series:[
      {
           name:'偏自相关系数',
           type:'bar',
           data:pacfy,

           markLine : {
               data : [
                   {type : 'average', name: '平均值'},
                   {yAxis: p_value, name :'p value'},
                   {yAxis: -p_value, name :'- p value'}
               ]
           }
       }
    ]
      };
      var pacfchart=echarts.init(document.getElementById('pacf_chart'));
      pacfchart.setOption(pacfopt);
    });
  }
  function Risk(name,startdate,enddate) {
    var url='http://115.159.106.212/strategy/getRisk?';
    url=url+name+'&'+startdate+'&'+enddate;
    $http.get(url).success(function(response,status){
      var down=Number(response['downside']);
      down*=100;
      down=down.toFixed(2);
      var VaR=Number(response['VaR']);
      VaR*=100;
      VaR=VaR.toFixed(2);
      $scope.downside=down;
      $scope.VaR=VaR;
    });
  }

  function NormalTest(name,startdate,enddate) {
    var url='http://115.159.106.212/strategy/getNormalTest?';
      url=url+name+'&'+startdate+'&'+enddate;
    $http.get(url).success(function(response,status){
      var average=Number(response['average']);
      average=average*100;
      average=average.toFixed(2);
      var varvalue=Number(response['var']);
      varvalue=varvalue*10000;
      varvalue=varvalue.toFixed(2);
      var p_value=Number(response['p']);
      p_value=p_value*100;
      p_value=p_value.toFixed(2);
      $scope.normal_p=p_value;
      $scope.average=average;
      $scope.varvalue=varvalue;
    }
    ).error(function() {
      alert('error');

    });
    var securl='http://115.159.106.212/strategy/getQQ?';
    securl=securl+name+'&'+startdate+'&'+enddate;
    $http.get(securl).success(function(response,status){
      var point=[];
      for (obj in response) {
        var te=[];
        te.push(Number(response[obj]['x']));
        te.push(Number(response[obj]['y']));
        point.push(te);
      }
      qqopt={
        title : {
          text: $scope.stockid+" 正态分布检验Q-Q图",
          subtext: 'powered by chicer'
        },
        legend: {
          data: ['Q-Q点'],
          left: 'right'
        },
        tooltip : {
        trigger: 'axis',
        showDelay : 5,

        axisPointer:{
            show: true,
            type : 'cross',
            lineStyle: {
                type : 'dashed',
                width : 1
            }
        }
      },
        xAxis : [
        {
            type : 'value',
            scale:true,
            splitLine: {
                lineStyle: {
                    type: 'dashed'
                }
            }
        }
        ],
        yAxis : [
        {
            type : 'value',
            scale:true,
            splitLine: {
                lineStyle: {
                    type: 'dashed'
                }
            }
        }
        ],
        series:[
          {
            name:'Q-Q point',
            type:'scatter',
            data:point
          }
        ]
      };
      var chart=echarts.init(document.getElementById('Q_Q'));
      chart.setOption(qqopt);
    });
  }

  function ER(name,startdate,enddate) {

    var url='http://115.159.106.212/strategy/getER?';

    url=url+name+'&'+startdate+'&'+enddate;
    $http.get(url).success(function(response,status){
      var date=[];
      var value=[];
      for (obj in response) {
        date.push(response[obj]['date']);
        var te=Number(response[obj]['er']);
        value.push(te.toFixed(2));
      }
    var option={
      title: {
          left: 'center',
          text: 'Earned Value Chart',
      },
      legend: {
          top: 'bottom',
          data:['Earned Value']
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          animation: true
        }
      },
      toolbox: {
          show: true,
          feature: {
              dataView: {show: true, readOnly: true},
              magicType: {show: true, type: ['line', 'bar']},
              restore: {show: true},
              saveAsImage: {show: true}
          }
      },
      xAxis: {
          type: 'category',
          boundaryGap: false,
          data: date
      },
      yAxis: {
          type: 'value',
          boundaryGap: false,
          axisLabel: {
               formatter: function (val) {
                   return val + '%';
               }
           }
      },

      series: [
          {
              name:'Earned Value',
              type:'line',
              smooth:true,
              data: value,
              markPoint: {
                data: [
                    {type: 'max', name: '最大值'},
                    {type: 'min', name: '最小值'}
                ]
            },
            markLine: {
                data: [
                    {type: 'average', name: '平均值'}
                ]
            }
          }
      ]
    };
    var chart=echarts.init(document.getElementById('ER'));
    chart.setOption(option);

    }).error(function(){
      alert("error");
    });
  }


  function ER_time(name,startdate,enddate) {

    var url='http://115.159.106.212/strategy/getER?';

    url=url+name+'&'+startdate+'&'+enddate;
    $http.get(url).success(function(response,status){
      var date=[];
      var value=[];
      for (obj in response) {
        date.push(response[obj]['date']);
        var te=Number(response[obj]['er']);
        value.push(te.toFixed(2));
      }
    var option={
      title: {
          left: 'center',
          text: 'Earned Value Chart',
      },
      legend: {
          top: 'bottom',
          data:['Earned Value']
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          animation: true
        }
      },
      toolbox: {
          show: true,
          feature: {
              dataView: {show: true, readOnly: true},
              magicType: {show: true, type: ['line', 'bar']},
              restore: {show: true},
              saveAsImage: {show: true}
          }
      },
      xAxis: {
          type: 'category',
          boundaryGap: false,
          data: date
      },
      yAxis: {
          type: 'value',
          boundaryGap: false,
          axisLabel: {
               formatter: function (val) {
                   return val + '%';
               }
           }
      },

      series: [
          {
              name:'Earned Value',
              type:'line',
              smooth:true,
              data: value,
              markPoint: {
                data: [
                    {type: 'max', name: '最大值'},
                    {type: 'min', name: '最小值'}
                ]
            },
            markLine: {
                data: [
                    {type: 'average', name: '平均值'}
                ]
            }
          }
      ]
    };
    var chart=echarts.init(document.getElementById('ER_time'));
    chart.setOption(option);

    }).error(function(){
      alert("error");
    });
  }
});
