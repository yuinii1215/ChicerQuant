"use strict";

$(function () {
    var editor = ace.edit("editor");
    $(".form-datetime").datepicker({
        setDate: new Date(),
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
    $("#start_time").datepicker("update", new Date());
    $("#end_time").datepicker("update", new Date());

    $('.selectpicker').selectpicker();

    $(".run_algo_click").click(function () {
        var $btn = $(".run_algo_click").button("loading");

        var code = editor.getValue();
        var start = $("#start_time").val();
        var end = $("#end_time").val();
        var freq = $(".selectpicker").val();
        var capital_base = $("#capital_base").val();
        var strategy_id = $("#strategy_id").val();
        var post_data = { code: code, start: start, end: end, capital_base: capital_base, freq: freq, strategy_id: strategy_id };
        $.ajax({
            type: "POST",
            url: "/algo/run/",
            data: post_data,
            dataType: "json",
            success: function success(data) {
                if (data['status'] == 'ok') {
                    //鎺ュ彛寰呭畾涔�
                    $("#annual_returns").html(data['data']['annualized_return']);
                    $("#bench_annual_returns").html(data['data']['benchmark_annualized_return']);
                    $("#alpha").html(data['data']['alpha']);
                    $("#beta").html(data['data']['beta']);
                    $("#sharpe").html(data['data']['sharpe']);
                    $("#volatility").html(data['data']['volatility']);
                    $("#information").html(data['data']['information']);
                    $("#max_drawdown").html(data['data']['max_drawdown']);
                    $("#result_chart_area").highcharts({
                        title: {
                            text: '绱鏀剁泭鐜�'
                        },
                        xAxis: {
                            type: 'datetime',
                            tickInterval: 7 * 24 * 60 * 60 * 1000,
                            labels: {
                                formatter: function formatter() {
                                    return Highcharts.dateFormat('%Y-%m-%d', this.value);
                                }
                            }
                        },
                        yAxis: {
                            title: null,
                            tickPositions: [-25, 0, 25, 50, 75, 100]
                        },
                        tooltip: {
                            shared: true,
                            crosshairs: true,
                            formatter: function formatter() {
                                return Highcharts.dateFormat('%Y-%m-%d', this.x) + '<br>绛栫暐: ' + this.points[0].y + '<br>鍩哄噯: ' + this.points[1].y;
                            }
                        },
                        series: [{
                            'name': '绛栫暐',
                            data: data['data']['cumulative_return']
                        }, {
                            'name': '鍩哄噯',
                            data: data['data']['benchmark_return']
                        }]
                    });
                } else {
                    $('#code_area').notify(data['data'], { position: 'top' });
                }
                $btn.button('reset');
            },
            error: function error(data) {
                $('#code_area').notify(data['data'], { position: 'top' });
                $btn.button('reset');
            }
        });
    });

    $("#share_btn").on("click", function () {
        var result_params = $("<table>").append($("#result_area table").clone()).html();
        var svg_content = $("<svg>").append($("#result_chart_area svg").clone()).html();
        var strategy_id = $("#strategy_id").val();

        var share_title = $("#share_title").val();
        var share_content = $("#share_content").val();

        $.ajax({
            url: '/social/share/',
            type: 'POST',
            data: { "strategy_id": strategy_id, "result_params": result_params,
                "svg_content": svg_content, "title": share_title, "comment_content": share_content },
            dataType: 'json',
            success: function success(data) {
                $("#share_modal").modal("hide");
                $.notify(data['data'], { position: 'right', className: 'info' });
            }
        });
    });

    $("#code_erase").on("click", function () {
        var editor = ace.edit("editor");
        editor.setValue("");
    });
});