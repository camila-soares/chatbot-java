var mmChat = {
  _retry: 0,
  _maxRetries: 4,
  _websocket: null,
  init: function () {
    this.connect();
    $('body').on('outgoing-message', function (e) {
      mmChat._sendMessage(e.message);
    });
  },
  connect: function () {
    if (this._retry < this._maxRetries &&
      (!this._websocket ||
        (this._websocket instanceof WebSocket &&
          this._websocket.readyState !== WebSocket.OPEN && this._websocket.readyState !== WebSocket.CONNECTING))) {
      if (this._retry !== 0)
        console.log("Reconnecting to socket");
      var url = jsRoutes.controllers.BotController.socket().absoluteURL(secured).replace(/^http/i, 'ws/localhost:9000/chat');
      console.log("connecting to " + url);
      this._websocket = new WebSocket(url);
      this._pinger = setInterval(function () {
        mmChat._sendMessage({action: "PING"});
      }, 30000);
      this._websocket.onopen = function (event) {
        mmChat._retry = 0;
        console.log("new connection opened");
        mmChat._wsStatus(true);
        mmChatW._sendHello();
      };
      this._websocket.onclose = function (event) {
        console.log("connection closed, retrying...");
        mmChat._wsStatus(false);
        clearInterval(mmChat._pinger);
        setTimeout(function () {
          mmChat._retry = mmChat._retry + 1;
          mmChat.connect();
        }, 1000)
      };
      this._websocket.onerror = function (event) {
        console.log("connection error, retrying...");
        mmChat._wsStatus(false);
        clearInterval(mmChat._pinger);
        setTimeout(function () {
          mmChat._retry = mmChat._retry + 1;
          mmChat.connect();
        }, 1000)
      };
      this._websocket.onmessage = function (event) {
        var ev = jQuery.Event('incoming-message');
        ev.message = JSON.parse(event.data);
        // console.log('message', ev.message);
        $('body').trigger(ev);
      };
    } else {
      console.log("already connected, dropping attempt");
    }
  },
  _sendMessage: function (message) {
    this._websocket.send(JSON.stringify(message));
  },
  _wsStatus: function (status) {
    var ev = jQuery.Event('connection-status');
    ev.message = status;
    $('body').trigger(ev);
  }
};
mmChat.init();