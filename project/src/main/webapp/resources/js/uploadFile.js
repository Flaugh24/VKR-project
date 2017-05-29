/**
 * Created by gagarkin on 29.05.17.
 */

var filePdf = ['pdf']; // массив расширений
var fileZip = ['zip'];
$('input[type=file]').change(function(){
    var parts = $(this).val().split('.');
    if(filePdf.join().search(parts[parts.length - 1]) !== -1) {
        $("#filePdf").prop("disabled", false);
        $("#fileZip").prop("disabled", true);
    }
    else if(fileZip.join().search(parts[parts.length - 1]) !== -1){
        $("#fileZip").prop("disabled", false);
        $("#filePdf").prop("disabled", true);
    }
    else {
        alert('Выберите файл в формате PDF или ZIP');
    }
});