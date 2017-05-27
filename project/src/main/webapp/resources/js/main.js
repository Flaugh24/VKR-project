/**
 * Created by gagar on 22.05.2017.
 */

jQuery(document).ready(
    function($) {
        console.log("button");
        $('button').prop('disabled', false);


        $("#btn-save").click(function(event) {
            console.log("val of button" + $(this).val);

            var data = {}
            data["id"] = $("#id").val();
            data["button"] = $(this).val();

            $("#btn-save").prop('disabled', true)

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "path-to",
                data: JSON.stringify(data),
                dataType: 'json',
                timeout: 600000,
                success: function (data) {
                    $("#btn-update").prop("disabled", false);
                    //...
                },
                error: function (e) {
                    $("#btn-save").prop("disabled", false);
                    //...
                }
            });


        });

    });