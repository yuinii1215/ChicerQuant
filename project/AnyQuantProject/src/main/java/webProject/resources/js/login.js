$(function(){
    $('#btn').on('click', function(){
        $('#dialog').show();
    });

    $('#dialog').on('click', '.weui_btn_dialog', function(){
        $('#dialog').hide();
    });
});