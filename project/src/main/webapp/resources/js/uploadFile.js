/**
 * Created by gagarkin on 29.05.17.
 */

var file = ['pdf', 'zip']; // массив расширений
$('input[type=file]').change(function(){
    var parts = $(this).val().split('.');
    if(file.join().search(parts[parts.length - 1]) !== -1) {
        $("#upload").prop("disabled", false);
    }
    else {
        alert('Выберите файл в формате PDF или ZIP');
    }
});