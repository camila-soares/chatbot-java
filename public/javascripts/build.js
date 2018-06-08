
$("#mensagem").submit(function(){
    event.preventDefault();
    if($("#mensagem #texto").val() === ""){

        $("#chat").append("<div class=\"texto usuario\">...</div>");

        var data = $("#mensagem").serialize();
        data.__RequestVerificationToken = $('input[name=__RequestVerificationToken]').val();

        $(".mensagens").animate({scrollTop: $("#chat").height()});
        setTimeout(function(){

            $("#chat").append("<div class=\"texto chatbot\">Você precisa digitar alguma coisa para prosseguir.</div>");

            $(".mensagens").animate({scrollTop: $("#chat").height()});
        },1000);
        return false;
    }


    $.ajax({
        header:{"Content-Type": "application/json"},
        url: "https://gateway.watsonplatform.net/conversation/api/v1/workspaces/42893f0a-6c0e-4b66-8e30-525058a4322e",
        type: 'GET',
        data: data,
        dataType: 'jsonp',
        beforeSend: function(){

            $("#chat").append("<div class=\"texto usuario\">" + $("#mensagem #texto").val() + "</div>");
        },
        success: function(resposta){

            $("#mensagem #texto").val("");
            $("#mensagem #texto").focus();


            if(resposta.error){
                $("#chat").append("<div class=\"texto chatbot\">" + resposta.error + "</div>");
                return false;
            }


            var mensagemChatbot  = "<div class=\"texto chatbot\">";
            mensagemChatbot		+= resposta.output.text[0];
            mensagemChatbot		+= "</div>";
            setTimeout(function(){
                $("#chat").append(mensagemChatbot);
                $(".mensagens").animate({scrollTop: $("#chat").height()});
            },1000);
        }
    });

    return false;
});