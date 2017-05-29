$(function(){

//Живой поиск
    $('.who').delay(500).bind("change keyup input click", function(){
        if(this.value.length >= 2){
            console.log("Поиск " + this.value);
            var inputText = this.value;
            $.ajax({
                url : '/vkr/liveSearch.fst',
                type: 'GET',
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json',
                data : ({
                    text: inputText
                }),
                success: function (data) {
                    console.log("request");
                    console.log(data[1].username);
                }
            });
        }
    })

    $(".search_result").hover(function(){
        $(".who").blur(); //Убираем фокус с input
    })

//При выборе результата поиска, прячем список и заносим выбранный результат в input
    $(".search_result").on("click", "li", function(){
        s_user = $(this).text();
        //$(".who").val(s_user).attr('disabled', 'disabled'); //деактивируем input, если нужно
        $(".search_result").fadeOut();
    })

})