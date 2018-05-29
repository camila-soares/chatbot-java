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
            var url = jsRoutes.controllers.BotController.socket().absoluteURL().replace(/^http/i, 'ws');
            console.log("connecting to " + url);
            this._websocket = new WebSocket(url);
            this._websocket.onopen = function (event) {
                mmChat._retry = 0;
                console.log("new connection opened");
                mmChat._wsStatus(true);
            };
            this._pinger = setInterval(function () {
                mmChat._sendMessage({action: "Teste"});
            }, 30000);
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
    },


};
mmChat.init();
'use strict';
// (function ($) {
var doPostback, doNavigate, doText;
var firstLoad = true;
var actual_filter = "A - B";

var mmChatW = {
    _token: '',
    _chatWindow: $('#chat-window'),
    _closeButton: $('.chat-window-close'),
    _chatMessages: document.getElementById('mensagem'),
    init: function () {
        if (window.sessionStorage) {
            if (window.sessionStorage.getItem('mmc_token') !== null)
                this._setToken(window.sessionStorage.getItem('mmc_token'));
            var history = JSON.parse(window.sessionStorage.getItem('mmc_history')) || [];
            history.forEach(function (message) {
                mmChatW.addMessage(message.message, message.origin, true);
            });
        }

        doText = function (message) {
            var ev = jQuery.Event('outgoing-message');
            ev.message = Message.createText(message);
            $('body').trigger(ev);
            mmChatW.addMessage(ev.message, 'local');
            fecharMenu();
            closeSubmenuSobre();
        };
        doPostback = function (label, payload) {
            var ev = jQuery.Event('outgoing-message');
            ev.message = Message.createPostback(label, payload);
            $('body').trigger(ev);
            if (payload.indexOf("view_stores_") === -1 || payload === "view_stores_") {
                mmChatW.addMessage(ev.message, 'local');
            }
            fecharMenu();
        };
        doNavigate = function (label, url) {
            mmChatW.addMessage(Message.createText(label), 'local');
            mmChatW.addMessage(Message.createText("Ok. Redirecionando..."), 'remote');
            parent.postMessage({navigate: true, url: url}, "*");
        };
        $('body')
            .on('incoming-message', function (e) {
                if (e.message.action == Message.Action.STORES) {
                    var html = mmChatW._getStoresCarousel(e.message.bubbles);
                    viewStores(html);
                } else {
                    mmChatW.addMessage(e.message, 'remote');
                }
            });
    },
    addMessage: function (message, origin, isHistory) {
        if (message && message.action !== Message.Action.PING) {
            var wrapper = this._getWrapper(origin);
            var msg = this._getMessage(message, origin);
            if (msg !== null) {

                wrapper.appendChild(msg);
                this._chatMessages.appendChild(wrapper);

                if (message.action === Message.Action.CAROUSEL) {
                    var crs = jQuery('.carousel');
                    crs.slick({
                        arrows: false,
                        centerMode: true,
                        centerPadding: '30px',
                        dots: true,
                        focusOnSelect: true,
                        infinite: false
                    });
                    crs.removeClass('carousel');
                } else if (message.action === Message.Action.STORES) {
                    if (!isHistory) {
                        $('.chat-menu-stores').show();
                    }
                }

                this._autoScroll();
                if (!isHistory) {
                    if (firstLoad) {
                        abrirMenu();
                    } else {
                        $('.chat-window-footer').show();
                    }
                    if (window.sessionStorage) {
                        var history = JSON.parse(window.sessionStorage.getItem('mmc_history')) || [];
                        history.push({
                            origin: origin,
                            message: message
                        });
                        window.sessionStorage.setItem('mmc_history', JSON.stringify(history));
                    }
                }
            }
        }
    },

    _getMessage: function (message, origin) {
        var domobj = null;
        switch (message.action) {
            case Message.Action.POSTBACK:
            case Message.Action.TEXT:
                domobj = this._getBubble(message.payload.text, origin);
                break;
            case Message.Action.PONG:
                break;
            case Message.Action.CONFIG:
                this._storeToken(message.token);
                break;
            case Message.Action.IMAGE:
                domobj = this._getBubble(this._getImage(message), origin, 'media');
                break;
            case Message.Action.BUBBLE:
                domobj = this._parseBubbleTemplate(message.bubbles[0], true);
                break;
            case Message.Action.CAROUSEL:
                domobj = this._getCarousel(message.bubbles);
                break;
            case Message.Action.STORES:
                domobj = this._getStoresCarousel(message.bubbles);
                break;
            default:
                console.log('not implemented:', message);
                break;
        }
        return domobj;
    },
    _getImage: function (message) {
        //TODO
        var src = message.bubbles[0].image_url;
        return `<img src="${src}" />`;
    },
    _getBubble: function (message, origin, type) {
        var bubble = document.createElement('div'), bubbleContent = document.createElement('div');
        bubbleContent.innerHTML = message;
        bubbleContent.classList.add('bubble-content');
        bubble.classList.add('bubble');
        switch (origin) {
            default:
            case 'local':
                bubble.classList.add('bubble-local');
                bubble.classList.add('bubble-has-padding');
                break;
            case 'remote':
                bubble.classList.add('bubble-remote');
                if (type === 'media')
                    bubble.classList.add('bubble-has-media');
                else
                    bubble.classList.add('bubble-has-padding');
                break;
        }
        bubble.appendChild(bubbleContent);
        return bubble;
    },
    _autoScroll: function () {
        this._chatMessages.scrollTop = this._chatMessages.scrollHeight;
    },
    getToken: function () {
        return this._token;
    },
    _setToken: function (token) {
        this._token = token;
    },
    _storeToken: function (token) {
        if (window.sessionStorage)
            window.sessionStorage.setItem('mmc_token', token);
        this._setToken(token);
    }
};
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
        url: jsRoutes.controllers.BotController.socket().absoluteURL().replace(/^http/i, 'ws'),
        crossDomain: true,
        dataType: 'jsonp', // Determina o tipo de retorno
        beforeSend: function(){
            $("#chat").append("<div class=\"texto usuario\">" + $("#mensagem #texto").val() + "</div>");
        },
        success: function(resposta){
            $("#mensagem #texto").val("");
            $("#mensagem #texto").focus();
            if(resposta.error){
                $("#chat").append("<div class=\"texto chatbot\">" + resposta.error + "</div>");
                return false;
            }
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
