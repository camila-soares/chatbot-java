$("#mensagem").submit(function(){

    if($("#mensagem #texto").val() === ""){

        $("#chat").append("<div class=\"texto usuario\">...</div>");


        $(".mensagens").animate({scrollTop: $("#chat").height()});
        setTimeout(function(){

            $("#chat").append("<div class=\"texto chatbot\">Você precisa digitar alguma coisa para prosseguir.</div>");

            $(".mensagens").animate({scrollTop: $("#chat").height()});
        },1000);
        return false;
    }


    $.ajax({
        header:{"Accept": "application/json"},
        type: 'GET',

        url: jsRoutes.controllers.BotController.socket().absoluteURL().replace(/^http/i, 'ws'),
        crossDomain: true,
        dataType: 'jsonp', // Determina o tipo de retorno
        beforeSend: function(){
            //Adiciona a mensagem de usuário à lista de mensagens.
            $("#chat").append("<div class=\"texto usuario\">" + $("#mensagem #texto").val() + "</div>");
        },
        success: function(resposta){
            //Limpa o que o usuário digitou e foca no input para próxima interação.
            $("#mensagem #texto").val("");
            $("#mensagem #texto").focus();

            //Caso haja um erro, o Watson retornará a mensagem de erro ao usuário
            //Basta ler o objeto error para o usuário.
            if(resposta.error){
                $("#chat").append("<div class=\"texto chatbot\">" + resposta.error + "</div>");
                return false;
            }

            //Colocar a resposta do Watson para o usuário ler
            //A mensagem de texto pode ser lida a partir da lógica
            //do json de exemplo da imagem no post
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