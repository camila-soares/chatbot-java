(function () {
    let _retry = 0, _maxRetries = 4;
    let websocket = null, outputScheduler = null;
    const body = document.getElementsByTagName('body')[0];
    body.addEventListener('outgoing-message', e => sendMessage(e.detail));
    let outputMessageQueue = [];

    if (sessionStorage) sessionStorage.setItem("access_token", accessToken);

    function connect() {
        if (_retry >= _maxRetries) return;
        if (websocket instanceof WebSocket && (websocket.readyState === WebSocket.OPEN || websocket.readyState === WebSocket.CONNECTING)) return;
        websocket = new WebSocket(jsRoutes.controllers.ChatbotController.socket(accessToken).webSocketURL(secured));
        setupWS();
    }

    function setupWS() {
        let pinger = setInterval(function () {
            sendMessage({action: "PING"});
        }, 30000);

        websocket.onopen = function (event) {
            _retry = 0;
            wsStatus(true);
        };
        websocket.onclose = function (event) {
            clearInterval(pinger);
            wsStatus(false);
            setTimeout(function () {
                _retry = _retry + 1;
                connect();
            }, 1000)
        };
        websocket.onerror = function (event) {
            wsStatus(false);
            clearInterval(pinger);
            setTimeout(function () {
                _retry = _retry + 1;
                connect();
            }, 1000)
        };
        websocket.onmessage = function (event) {
            const ev = new CustomEvent('incoming-message', {detail: JSON.parse(event.data)});
            body.dispatchEvent(ev);
        };
    }

    function sendMessage(message) {
        if (websocket.readyState === WebSocket.OPEN)
            websocket.send(JSON.stringify(message));
        else {
            outputMessageQueue.push(message);
            setupOutputScheduler();
        }
    }

    function setupOutputScheduler() {
        outputScheduler = setInterval(function () {
            if (outputMessageQueue.length !== 0 && websocket.readyState === WebSocket.OPEN) {
                sendMessage(outputMessageQueue.pop());
            }
            if (outputMessageQueue.length === 0)
                clearInterval(outputScheduler);
        }, 1000);

    }

    function wsStatus(status) {
        const ev = new CustomEvent('connection-status', {detail: {connected: status}});
        body.dispatchEvent(ev);
    }

    connect();

})();