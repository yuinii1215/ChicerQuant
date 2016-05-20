var dataSet =localStorage.allIndustryTableData;

$(document).ready(function() {
    $('#table').DataTable( {
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
} );

/*<th>行业名称</th>
<th>总资产（亿）</th>
<th>涨跌幅</th>
<th>净额（亿）</th>
<th>公司家数</th>
<th>领涨股名称</th>
<th>领涨股代码</th>
<th>涨跌幅</th>
<th>当前价（元）</th>

 <thead>
 <tr>
 <th>行业名称</th>
 <th>总资产（亿）</th>
 <th>涨跌幅</th>
 <th>净额（亿）</th>
 <th>公司家数</th>
 <th>领涨股名称</th>
 <th>领涨股代码</th>
 <th>涨跌幅</th>
 <th>当前价（元）</th>
 </tr>
 </thead>
 <tfoot id="allIndustryTable">
 <tr>
 <th>industry_name</th>
 <th>total</th>
 <th>updown</th>
 <th>pure</th>
 <th>companySum</th>
 <th>leaderName</th>
 <th>leader</th>
 <th>leaderUpdown</th>
 <th>leaderPrice</th>
 </tr>
 </tfoot>*/