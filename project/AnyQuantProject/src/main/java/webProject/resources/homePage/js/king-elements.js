$(document).ready(function(){
    //*******************************************
    /*	FLASH MESSAGE/GRITTER
     /********************************************/

    // if flash alert/message page
    if( $('body').hasClass('flash-alert') ) {

        // global setting override
        $.extend( $.gritter.options, {
            // you can use these params to set global variable that affect all the notications behaviour
            //class_name: 'gritter-light',
            //fade_in_speed: 100,
            //fade_out_speed: 100,
            //position: 'bottom-right' // possibilities: bottom-left, bottom-right, top-left, top-right
            time: 1500,
        });

        jQuery(window).load(function() {
            // clean the wrapper position class
            $('#gritter-notice-wrapper').attr('class', '');
            // global setting override
            $.extend( $.gritter.options, {
                position: '' + $(this).attr('id') + '' // possibilities: bottom-left, bottom-right, top-left, top-right
            });
            $.gritter.options.position="bottom-right";
            $.gritter.add({
                title: $(this).find('span.title').text(), // could be simpler, just for demo purposes
                text:"hello"+"</br>"+'Hi, I\'m on the  ' + $.gritter.options.position + ''
            });
        });

    } // end if flash alert page

}); // end ready function
