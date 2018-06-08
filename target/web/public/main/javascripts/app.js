$(document).ready(function () {
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
        $('#message_box').append("<div class=\"system_msg\">Conectado Com:</div>");
        setTimeout(ping(), 3000);
    };

        $('#send-btn').click(function () {
        var mymessage = $('#message').val();

        /*if (mymessage === "") {
            alert("Entre com alguma mensagem");
            return;
        } */

        if(mymessage != ""){
                $('#message_box').append("<div><span class=\"user_message\">" + mymessage + "</span></div>");
            }

            var msg = {
            message: mymessage,
            //name: myname,
            //toname: toname,
            //color: ''
        };


        websocket.send(JSON.stringify(msg));
        body.dispatchEvent(msg);
    });

    const Message = {
        Action: {
            HELLO: "HELLO",
            TEXT: "TEXT",
            PONG: "PONG",
            IMAGE: "IMAGE",
            BUBBLE: "BUBBLE",
            CAROUSEL: "CAROUSEL",
            POSTBACK: "POSTBACK",
            QUICKREPLY: "QUICKREPLY",
            MENU: "MENU"
        },
        createText: function (text) {
            return {"action": Message.Action.TEXT, "payload": {"text": text}};
        },
        createHello: function () {
            return {"action": Message.Action.HELLO};
        },
        createPostback: function (label, payload) {
            return {
                "action": Message.Action.POSTBACK,
                "payload": {"text": label, "postback": payload}
            };
        }
    };



    websocket.onmessage = function (ev) {
        var msg = JSON.parse(ev.data);
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
            $('#message_box').append("<div class=\"system_msg\">" + data + "</div>");
        }

        $('#message').val('');
    };

    function addMessage() {
        var message = message.value;
        message.value = "";
        websocket.send(message);
    }

    websocket.onerror = function (ev) {
        $('#message_box').append("<div class=\"system_error\">Um erro ocorreu - " + ev.data + "</div>");
    };

    websocket.onclose = function (ev) {
        $('#message_box').append("<div class=\"system_msg\">Conexão finalizada</div>");
        setTimeout(setupWebSocket, 1000);


    };



});