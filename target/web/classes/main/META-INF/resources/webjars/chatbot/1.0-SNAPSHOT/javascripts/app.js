
window.onload = function () {

    var message_mmm = document.getElementById('message');
    var listaMessagem = document.getElementById('message_box');


    var wsUri = jsRoutes.controllers.BotController.socket().absoluteURL().replace(/^http/i, 'ws');
    websocket = new WebSocket(wsUri);

    function ping() {
        //websocket.send('__ping__');
        tm = setTimeout(function () {

        }, 5000);
    }

    function pong(){
        clearTimeout(tm);
    }

    websocket.onopen = function (ev) {
        $('#message_box').append("<div class=\"system_msg\">Conectado!</div>");
        setTimeout(ping(), 3000);
    };

        $('#send-btn').click(function () {
        var mymessage = $('#message').val();


        if(mymessage != ""){
                $('#message_box').append("<div><span class=\"user_message\">" + mymessage + "</span></div>");
            }


            var msg = {
            message: mymessage
        };
            websocket.send(JSON.stringify(msg));
            websocket.onmessage = function (ev) {
                var msg = JSON.stringify(ev.data);
                message_mmm = ev.data;
                listaMessagem.innerHTML += $('#message_box').append("<div class=\"system_msg\">" + msg.value + "</div>");;
                var type = msg.type;
                var umsg = msg.message;
                var data = [];
                data.push({'message': umsg});
                var dados_user = JSON.stringify(data)
                var parser = JSON.parse(dados_user);
                pong();


                console.log(type);
                if (type === 'usermsg') {
                    $('#message_box').append("<div><span class=\"user_name\" style=\"color:" + ucolor + "\">" + uname + "</span> : <span class=\"user_message\">" + umsg + "</span></div>");
                } else if (type === 'system') {
                    $('#message_box').append("<div class=\"system_msg\">" + parser + "</div>");
                }
                $('#message').val('');
            };


    });

    const Message = {
        Action: {
            TEXT: "TEXT"
        },
        createText: function (text) {
            //return $('#message_box').append("<div class=\"system_msg\">" + text + "</div>");
        },
    };



    websocket.onerror = function (ev) {
        $('#message_box').append("<div class=\"system_error\">Um erro ocorreu - " + ev.data + "</div>");
    };

    websocket.onclose = function (ev) {
        $('#message_box').append("<div class=\"system_msg\">Conexão finalizada</div>");
        setTimeout(setupWebSocket, 1000);


    };





};