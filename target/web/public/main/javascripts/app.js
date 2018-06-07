$(document).ready(function () {
    var wsUri = jsRoutes.controllers.BotController.socket().absoluteURL().replace(/^http/i, 'ws');

    websocket = new WebSocket(wsUri);

    websocket.onopen = function (ev) {
        $('#message_box').append("<div class=\"system_msg\">Conectado Com:</div>" + websocket.url);
    };

        $('#send-btn').click(function () {
        var mymessage = $('#message').val();
       // var myname = $('#name').val();
        //var toname = $('#toname').val();

       /* if (myname === "") {
            alert("Entre com algum nomde");
            return;
        }*/
        if (mymessage === "") {
            alert("Entre com alguma mensagem");
            return;
        } else if(mymessage != ""){
                $('#message_box').append("<div><span class=\"user_message\">" + mymessage + "</span></div>");
            }

            var msg = {
            message: mymessage,
            //name: myname,
            //toname: toname,
            //color: ''
        };

        websocket.send(JSON.stringify(msg));
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

    /*form.addEventListener('submit', ev => {
        ev.preventDefault();
        let message = Message.createText(input.value);
        sendMessage(message);
        buildMessage(message, 'local');
        input.value = '';
    });*/


    websocket.onmessage = function (ev) {
        var msg = JSON.parse(ev.data);
        var type = msg.type;
        var umsg = msg.message;
        var data = [];
    data.push({'message': umsg});
    var dados_user = JSON.stringify(data)
        var parser = JSON.parse(dados_user);


       console.log(type);
        if (type === 'usermsg') {
            $('#message_box').append("<div><span class=\"user_name\" style=\"color:" + ucolor + "\">" + uname + "</span> : <span class=\"user_message\">" + umsg + "</span></div>");
        } else if (type === 'system') {
            $('#message_box').append("<div class=\"system_msg\">" + umsg + "</div>");
        }

        $('#message').val('');
    };

    websocket.onerror = function (ev) {
        $('#message_box').append("<div class=\"system_error\">Um erro ocorreu - " + ev.data + "</div>");
    };
    /*websocket.onclose = function (ev) {
        $('#message_box').append("<div class=\"system_msg\">Conexão finalizada</div>");
        setTimeout(setupWebSocket, 1000);


    };*/



});