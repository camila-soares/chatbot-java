$(document).ready(function () {
    var wsUri = jsRoutes.controllers.BotController.socket().absoluteURL().replace(/^http/i, 'ws');
    websocket = new WebSocket(wsUri);

    websocket.onopen = function (ev) {
        $('#message_box').append("<div class=\"system_msg\">Conectado!</div>");
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
            alert("Entre com aguma mensagem");
            return;
        }

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
    });


    websocket.onmessage = function (ev) {
        var msg = JSON.parse(ev.data);
        var type = msg.type;
        var umsg = msg.message;
      //  var uname = msg.name;
      //  var ucolor = msg.color;

        console.log(type);
        if (type === 'usermsg') {
            $('#message_box').append("<div><span class=\"user_name\" style=\"color:#" + ucolor + "\">" + uname + "</span> : <span class=\"user_message\">" + umsg + "</span></div>");
        } else if (type === 'system') {
            $('#message_box').append("<div class=\"system_msg\">" + umsg + "</div>");
        }

        $('#message').val('');
    };

    websocket.onerror = function (ev) {
        $('#message_box').append("<div class=\"system_error\">Um erro ocorreu - " + ev.data + "</div>");
    };
    websocket.onclose = function (ev) {
        $('#message_box').append("<div class=\"system_msg\">Conexão finalizada</div>");
    };
});