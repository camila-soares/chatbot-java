var SOCKET_CONNECTING = 0;
var SOCKET_OPEN = 1;
var SOCKET_CLOSING = 2;
var SOCKET_CLOSED = 3;
var require;

var WebSocketServer = new require.constructor('ws').Server
wss = new WebSocketServer({ port: 9000 });

//Broadcast method to send message to all the users
wss.broadcast = function broadcast(data,sentBy)
{
    for (var i in this.clients)
    {
        if(this.clients[i] != sentBy)
        {
            this.clients[i].send(data);
        }
    }
};

//Send message to all the users
wss.broadcast = function broadcast(data,sentBy)
{
    for (var i in this.clients)
    {
        this.clients[i].send(data);
    }
};

var userList = [];
var keepAlive = null;
var keepAliveInterval = 5000; //5 seconds

//JSON string parser
function isJson(str)
{
    try {
        JSON.parse(str);
    }
    catch (e) {
        return false;
    }
    return true;
}

//WebSocket connection open handler
wss.on('connection', function connection(ws) {

    function ping(client) {
        if (ws.readyState === SOCKET_OPEN) {
            ws.send('__ping__');
        } else {
            console.log('Server - connection has been closed for client ' + client);
            removeUser(client);
        }
    }

    function removeUser(client) {

        console.log('Server - removing user: ' + client)

        var found = false;
        for (var i = 0; i < userList.length; i++) {
            if (userList[i].name === client) {
                userList.splice(i, 1);
                found = true;
            }
        }

        //send out the updated users list
        if (found) {
            wss.broadcast(JSON.stringify({userList: userList}));
        };

        return found;
    }

    function pong(client) {
        console.log('Server - ' + client + ' is still active');
        clearTimeout(keepAlive);
        setTimeout(function () {
            ping(client);
        }, keepAliveInterval);
    }

    //WebSocket message receive handler
    ws.on('message', function incoming(message) {
        if (isJson(message)) {
            var obj = JSON.parse(message);

            //client is responding to keepAlive
            if (obj.keepAlive !== undefined) {
                pong(obj.keepAlive.toLowerCase());
            }

            if (obj.action === 'join') {
                console.log('Server - joining', obj);

                //start pinging to keep alive
                ping(obj.name.toLocaleLowerCase());

                if (userList.filter(function(e) { return e.name == obj.name.toLowerCase(); }).length <= 0) {
                    userList.push({name: obj.name.toLowerCase()});
                }

                wss.broadcast(JSON.stringify({userList: userList}));
                console.log('Server - broadcasting user list', userList);
            }
        }

        console.log('Server - received: %s', message.toString());
        return false;
    });
});