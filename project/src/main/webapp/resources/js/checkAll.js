jQuery.noConflict()

jQuery(function($){
    var table = $('#tableReady');
    table
        .on('change', '> tbody input:checkbox',function() {
            $(this).closest('span').toggleClass('checked', $(this).is(':checked'));
        })
        .on('change', '#all', function(){
            $('> tbody input:checkbox', table).prop('checked', $(this).is(':checked')).trigger('change');
        });
});

