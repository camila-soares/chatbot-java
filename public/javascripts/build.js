var sock = new WebSocket(jsRoutes.controllers.BotController.socket().absoluteURL().replace(/^http/i, 'ws'));

sock.onopen = function (event) {
    alert('Socket Connected');
    setTimeout(function () {
        sock.send("Hey there");
    }, 1000);

};

sock.onmessage = function (event) {
    console.log(event) message_box.value += msg.ev + "\n";
}

function sendMessage(){
    websocket.send()
}