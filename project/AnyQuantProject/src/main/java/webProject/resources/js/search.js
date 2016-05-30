/**
 * Created by QiHan on 2016/5/14.
 */
function toSingleStockPage() {
    var parm1=document.getElementById("search").value;
    var id1= parm1.split('(');
    var id= id1[1].split(')');
//    alert(id[0]);
    localStorage.singleStockID=id[0];
    window.location.href="../singleStockPage/singleStockPage.html";
}

function allStock2SingleStockPage(stockID) {
    localStorage.singleStockID=stockID;
    window.location.href="../singleStockPage/singleStockPage.html";
}


var app = angular.module('mySearchApp', []);
app.controller('searchCtrl', function ($scope, $http) {

    $scope.url = 'http://115.159.97.98/php/serviceController.php'; // The url of our search

    var array=new Array();
    var count=0;

    $http.post($scope.url, {"method": "getAllStocksService"}).
        success(function(data) {
            $scope.error = false;
            $scope.data = data;
            $scope.allStocks =data;

            //   console.log($scope.allStocks);
            for(var item in $scope.allStocks) {
                count++;
            }

            for(var item in  $scope.allStocks) {
                if (item < count-1) {
                    array[item] = $scope.allStocks[item].stock_name+"("+$scope.allStocks[item].stock_id+")";
                    console.log(array[item] );
                }
            }


            //   console.log($scope.table);

            //initStockPreviewData();//调用linechart的初始化方法
        })
        .
        error(function(data) {
            $scope.error = true;
            $scope.data = data || "Request failed";

        });


    (function() {
        var displayResults, findAll, maxResults, names, resultsOutput, searchInput;

        names =  array;

        findAll = (function(_this) {
            return function(wordList, collection) {
                return collection.filter(function(word) {
                    word = word.toLowerCase();
                    return wordList.some(function(w) {
                        return ~word.indexOf(w);
                    });
                });
            };
        })(this);
        displayResults = function(resultsEl, wordList) {
            return resultsEl.innerHTML = (wordList.map(function(w) {
                return '<li onclick=javascript:a(this.innerText);>' + w + '</li>';
            })).join('');
        };
        searchInput = document.getElementById('search');
        resultsOutput = document.getElementById('results');
        maxResults = 7;

        searchInput.addEventListener('keyup', (function(_this) {
            return function(e) {
                var suggested, value;
                value = searchInput.value.toLowerCase().split(' ');
                suggested = (value[0].length ? findAll(value, names) : []);
                return displayResults(resultsOutput, suggested);
            };
        })(this));


    }).call(this);

});

function a(w){
    console.log(w);
    document.getElementById('search').value=w;
    /*    var id1= w.split('(');
    var id= id1[1].split(')');
    alert(id[0]);
      var value = w.replace(/[^\a-\z\A-\Z0-9]/ig,"");
     alert(value);
     */
}

