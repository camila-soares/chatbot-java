(function () {
    const body = document.getElementsByTagName('body')[0];
    const form = document.getElementById('form-main');
    const input = document.getElementById('form-msg');

    function sendMessage(message) { // Structured Message Object
        let ev = new CustomEvent('outgoing-message', {detail: message});
        body.dispatchEvent(ev);
    }


    const Message = {
        Action: {
            HELLO: "HELLO",
            TEXT: "TEXT",
            PONG: "PONG",
            POSTBACK: "POSTBACK"
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


    body.addEventListener('incoming-message', function (data) {
        if (!data.detail.action) return; // if the action isn't set, quit

        switch (data.detail.action) {
            case Message.Action.PONG:
                break;
            default:
                // add message to chat window
                buildMessage(data.detail, 'remote');
        }

    });

    form.addEventListener('submit', ev => {
        ev.preventDefault();
    let message = Message.createText(input.value);
    sendMessage(message);
    buildMessage(message, 'local');
    input.value = '';
});

    sendMessage(Message.createHello());

    function buildMessage(dataMessage, origin) {
        switch (dataMessage.action) {
            case Message.Action.TEXT:
                templateText(dataMessage.payload, origin);
                break;
            default:
            // Other types
        }
    }

    function templateText(payload, origin) {
        var messagesContainer = $('.messages-container');
        var msgContent        = '<p class="msg-content">' + payload.text + '</p>';
        var msgBloc           = '<div class="message msg-' + origin + ' msg-text" class="bounceIn animated">' + msgContent + '</div>';

        messagesContainer.append(msgBloc);

        setTimeout(function() {
            messagesContainer.find('.message.msg-remote:last-child').removeClass('animated');
        }, 600);

    }
})();