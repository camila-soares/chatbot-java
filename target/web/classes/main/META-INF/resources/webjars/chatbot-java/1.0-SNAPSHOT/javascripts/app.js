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
        if (!(this._retry < this._maxRetries &&
            (!this._websocket ||
                (this._websocket instanceof WebSocket &&
                    this._websocket.readyState !== WebSocket.OPEN && this._websocket.readyState !== WebSocket.CONNECTING)))) {
            console.log("already connected, dropping attempt");
        } else {
            if (this._retry !== 0)
                console.log("Reconnecting to socket");
            var url = "ws://localhost:9000/ws";

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
'use strict';
// (function ($) {
var doPostback, doNavigate, doText;
var firstLoad = true;
var actual_filter = "A - B";

var mmChatW = {
    _token: '',
    _chatWindow: $('#chat-window'),
    _closeButton: $('.chat-window-close'),
    _chatMessages: document.getElementById('chat-messages'),
    init: function () {
        if (window.sessionStorage) {
            if (window.sessionStorage.getItem('mmc_token') !== null)
                this._setToken(window.sessionStorage.getItem('mmc_token'));
            var history = JSON.parse(window.sessionStorage.getItem('mmc_history')) || [];
            history.forEach(function (message) {
                mmChatW.addMessage(message.message, message.origin, true);
            });
        }
        this._closeButton.on('click', function () {
            if (!mmChatW._chatWindow.hasClass('on')) {
                window.parent.postMessage(JSON.stringify({size: {width: 320, height: 480}}), '*');
                $(".chat-window-footer").show(100);
            } else {
                window.parent.postMessage(JSON.stringify({size: {width: 170, height: 48}}), '*');
                $(".chat-window-footer").hide();
            }
            mmChatW._chatWindow.toggleClass('on');
        });
        abrirMenu = function () {
            if ($('.menu-button').hasClass('ativo')) {
                $('.menu-button').removeClass('ativo');
                $('#chat-menu').fadeOut();
                $('.menu-button i').removeClass('fa-times').addClass('fa-bars');
            } else {
                $('.menu-button').addClass('ativo');
                $('#chat-menu').fadeIn();
                $('.menu-button i').removeClass('fa-bars').addClass('fa-times');
            }
            if (firstLoad) {
                $('.chat-window-footer').hide();
                firstLoad = false;
            } else {
                $('.chat-window-footer').show();
            }
            closeSubmenuSobre()
            hideStores();
        };
        fecharMenu = function () {
            $('.menu-button').removeClass('ativo');
            $('#chat-menu').fadeOut();
            $('.menu-button i').removeClass('fa-times').addClass('fa-bars');
        };
        openFilterStores = function () {
            $('.carousel-stores').hide();
            $('.filtro-avancado').show();
        };
        closeFilterStores = function () {
            $('.filtro-avancado').hide();
            $('.carousel-stores').show();
        };
        openSubmenuSobre = function () {
            $(".chat-submenu-sobre").show();
        };
        closeSubmenuSobre = function () {
            $(".chat-submenu-sobre").hide();
        };
        viewStores = function (html) {
            if ($('.last-chat').length > 0) {
                $('.filtro-avancado').each(function (i, carousel) {
                    $(this).slick('unslick');
                });
                $('.carousel-stores').each(function (i, carousel) {
                    $(this).slick('unslick');
                });
                $('.last-chat').remove();
            }
            var div = document.createElement('div');
            div.classList.add('sequence');
            div.classList.add('sequence-local');
            div.classList.add('last-chat');
            div.appendChild(html);
            var chat = document.getElementById("chat-messages");
            chat.appendChild(div);
            $('.carousel-stores').slick({
                arrows: true,
                infinite: false
            });
            $('.filtro-avancado').slick({
                arrows: true,
                infinite: false
            });
            $('.chat-menu-stores').show();
            mmChatW._autoScroll();
        };
        hideStores = function () {
            $('#chat-menu-stores').fadeOut();
        };
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
    _sendHello: function () {
        if (mmChat._websocket.readyState !== WebSocket.OPEN) {
            setTimeout(function () {
                mmChatW._sendHello();
            }, 500);
        } else {
            var ev = jQuery.Event('outgoing-message');
            ev.message = Message.createHello();
            $('body').trigger(ev);
        }
    },
    _getWrapper: function (source) {
        var wrap = document.createElement('div');
        wrap.classList.add('sequence', 'sequence-' + source);
        return wrap;
    },
    _parseBubbleStoreTemplate: function (bubbles) {

        var div = document.createElement('div');

        $.each(bubbles, function (i, bubble) {
            var li = document.createElement('li');
            li.classList.add('menu-item');
            var firuge = document.createElement('figure');

            var p = document.createElement('p');
            p.innerText = bubble.title;

            var img = document.createElement('img');

            if (bubble.image_url !== undefined) {
                img.src = bubble.image_url;
            } else {
                img.src = "http://via.placeholder.com/256x256?text=" + bubble.title;
            }

            li.addEventListener("click", function () {
                doPostback(bubble.title, "about_store_" + bubble.item_url);
            }, false);

            firuge.appendChild(img);
            li.appendChild(firuge);
            li.appendChild(p);
            div.appendChild(li);
        });

        return div;
    },
    _parseBubbleTemplate: function (bubble, single) {
        var structure = '';
        if (bubble.image_url) {
            if (bubble.item_url)
                structure += `<a href="${bubble.item_url}" target="_blank"><div class="carousel-picture" style="background-image: url('${bubble.image_url}');"></div></a>`;
            else
                structure += `<div class="carousel-picture" style="background-image: url('${bubble.image_url}');"></div>`;
        }
        structure += '<div class="carousel-body">';
        if (bubble.title) {
            structure += `<h3 class="carousel-title">${bubble.title}</h3>`;
        }
        if (bubble.subtitle) {
            const subtitleLines = bubble.subtitle.split("\n");
            if (subtitleLines.length > 1) {
                structure += `<div class="carousel-text"><p>${subtitleLines[0]}<br>${subtitleLines[1]}</p></div>`;
            } else {
                structure += `<div class="carousel-text"><p>${subtitleLines[0]}</p></div>`;
            }
        }
        structure += '</div>'; // .carousel-body
        if (Array.isArray(bubble.buttons)) {
            structure += '<div class="carousel-footer">';
            var buttons = bubble.buttons.map(function (button) {
                if (button.type === Button.Type.POSTBACK) {
                    return `<a class="bubble-action" type="button" onclick="doPostback('${button.label}', '${button.payload}')">${button.label}</a>`;
                } else if (button.type === Button.Type.URL) {
                    return `<a class="bubble-action" href="${button.payload}" target="_blank">${button.label}</a>`;
                } else if (button.type === Button.Type.NAVIGATE) {
                    if (button.payload.indexOf(Payload.MOVIES) > -1) {
                        var path = button.payload.substring(Payload.MOVIES.length).toLowerCase();

                        const from = "ãàáäâẽèéëêìíïîõòóöôùúüûñç·/_,:;";
                        const to = "aaaaaeeeeeiiiiooooouuuunc------";

                        for (var i = 0; i < path.length; i++) {
                            const char = path.charAt(i);
                            const indexOfChar = from.indexOf(char);
                            if (indexOfChar > -1) {
                                path = path.replace(new RegExp(from.charAt(indexOfChar), 'g'), to.charAt(indexOfChar));
                            }
                        }

                        path = path
                            .toString()                     // Cast to string
                            .toLowerCase()                  // Convert the string to lowercase letters
                            .trim()                         // Remove whitespace from both sides of a string
                            .replace(/\s+/g, '-')           // Replace spaces with -
                            .replace(/&/g, '-y-')           // Replace & with 'and'
                            .replace(/[^\w\-]+/g, '')       // Remove all non-word chars
                            .replace(/\-\-+/g, '-');        // Replace multiple - with single -

                        button.payload = Payload.MOVIES + path;
                    }
                    return `<button class="bubble-action" type="button" onclick="doNavigate('${button.label}', '${button.payload}')">${button.label}</button>`;
                }
            });
            structure += `<div class="bubble-actions bubble-actions-multiple">${buttons.join('')}</div>`;
            structure += '</div>' // .carousel-footer
        }

        var element = document.createElement('div');
        element.classList.add('carousel-item');

        if (single) {
            element.classList.add('slick-current');
        }

        element.innerHTML = structure;

        return element;
    },
    _getStoresCarousel: function (bubbles) {

        var i, j, chunk = 6;
        var bubblesPages = [];

        for (i = 0, j = bubbles.length; i < j; i += chunk) {
            bubblesPages.push(bubbles.slice(i, i + chunk));
        }

        var render = bubblesPages.map(this._parseBubbleStoreTemplate);

        if (bubbles.length > 0) {
            var div_stores = document.createElement('div');
            div_stores.classList.add('chat-menu-stores');

            var div_content = document.createElement('div');
            div_content.classList.add('menu-content');

            var div_row = document.createElement('div');
            div_row.classList.add('row');

            var h2 = document.createElement('h2');
            h2.innerText = "Lojas";

            var div_filtro = document.createElement('div');
            div_filtro.classList.add('filtro');
            div_filtro.innerHTML = 'De ' + actual_filter + '<i class="fa fa-bars"></i>';

            var div_back = document.createElement('div');
            div_back.classList.add('back');
            div_back.innerHTML = '<i class="fa fa-chevron-left"></i> Voltar';

            div_row.appendChild(h2);
            div_row.appendChild(div_filtro);
            div_row.appendChild(div_back);

            div_content.appendChild(div_row);

            var ul = document.createElement('ul');
            ul.classList.add('menu-items');
            ul.classList.add('carousel-stores');
            render.forEach(function (item) {
                ul.appendChild(item);
            });
            div_content.appendChild(ul);


            var filtros = [
                ['A - B', 'C - D', 'E - F', 'G - H', 'I - J', 'K - L', 'M - N', 'O - P', 'Q - R'], // slide 1
                ['S - T', 'U - V', 'W - X', 'Y - Z'] // slide 2
            ];

            var ul_filtro = document.createElement('ul');
            ul_filtro.classList.add('menu-items');
            ul_filtro.classList.add('filtro-avancado');

            $.each(filtros, function (i, slide) {

                var div_filtro = document.createElement('div');

                $.each(slide, function (i, filtro) {

                    var li_filtro = document.createElement('li');
                    li_filtro.classList.add('do-filter-stores');
                    var p_filtro = document.createElement('p');

                    p_filtro.innerText = filtro;
                    li_filtro.appendChild(p_filtro);

                    div_filtro.appendChild(li_filtro);
                });

                ul_filtro.appendChild(div_filtro);
            });


            div_content.appendChild(ul_filtro);

            div_stores.appendChild(div_content);

            return div_stores;
        }

        return render[0];
    },
    _getCarousel: function (bubbles) {
        var render = bubbles.map(this._parseBubbleTemplate);
        if (bubbles.length > 1) {
            var carousel = document.createElement('div');
            carousel.classList.add('carousel');
            render.forEach(function (item) {
                carousel.appendChild(item);
            });
            return carousel;
        }
        return render[0];
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

var Button = {
    Type: {
        POSTBACK: "POSTBACK",
        URL: "URL",
        NAVIGATE: "NAVIGATE"
    }
};

var Payload = {
    MOVIES: '/cinema/'
};

var Message = {
    Action: {
        CONFIG: "CONFIG",
        HELLO: "HELLO",
        TEXT: "TEXT",
        PING: "PING",
        PONG: "PONG",
        IMAGE: "IMAGE",
        BUBBLE: "BUBBLE",
        CAROUSEL: "CAROUSEL",
        POSTBACK: "POSTBACK",
        QUICKREPLY: "QUICKREPLY",
        STORES: "STORES"
    },
    createText: function (text) {
        return {"token": mmChatW.getToken(), "action": Message.Action.TEXT, "payload": {"text": text}};
    },
    createHello: function () {
        if (mmChatW.getToken() && mmChatW.getToken() !== '')
            return {"token": mmChatW.getToken(), "action": Message.Action.HELLO};
        return {"action": Message.Action.HELLO};
    },
    createPostback: function (label, payload) {
        return {
            "token": mmChatW.getToken(),
            "action": Message.Action.POSTBACK,
            "payload": {"text": label, "postback": payload}
        };
    }
};

$(document).ready(function () {
    mmChatW.init();

    $("body").on("click", ".do-filter-stores", function () {
        var filter = $(this).text();
        actual_filter = filter;
        filter = filter.toLocaleLowerCase();
        doPostback('Lojas', 'view_stores_' + filter.replace(/\s+/g, ''));
    });

    $("body").on("click", ".filtro", function () {
        $('.filtro-avancado').each(function () {
            $(this).slick('refresh');
        });
        openFilterStores();
        $(this).hide();
        $(".back").show();
    });

    $("body").on("click", ".back", function () {
        $(this).hide();
        $(".filtro").show();
        $(".filtro-avancado").hide();
        $('.carousel-stores').show();
    });

});