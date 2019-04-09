var ws;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#events_one_table").show();
        $("#events_two_table").show();
    } else {
        $("#events_one_table").hide();
        $("#events_two_table").hide();
    }
    $("#events_one").html("");
    $("#events_two").html("");
}

function connect() {
    //connect to stomp where stomp endpoint is exposed
    var baseUrl = $("meta[name='_base_url']").attr("content");
    var ws_protocol;

    if (window.location.protocol === "https:") {
        ws_protocol = "wss:";
    } else {
        ws_protocol = "ws:";
    }

    var socket = new WebSocket(ws_protocol + "//" + window.location.host + baseUrl + "listener/websocket");
    //var socket = new WebSocket("ws://localhost:8080/listener/websocket");
    ws = Stomp.over(socket);

    ws.connect({}, function (frame) {

            //subscribe to principal name topic
            ws.subscribe("/user/topic/eventsOne/", function (msg) {
                var data = msg.body;
                data = data.replace("@type", "type");
                var dataEvent = JSON.parse(data);
                showEventsOne(dataEvent);
            });

            ws.subscribe("/user/topic/eventsTwo/", function (msg) {
                var data = msg.body;
                data = data.replace("@type", "type");
                var dataEvent = JSON.parse(data);
                showEventsTwo(dataEvent);
            });
           //subcribe to common topic
            ws.subscribe("/topic/common", function (msg) {
                showCommonEvents(msg.body);
            });
            setConnected(true);
        }
    )
}

function sendEventOne() {
    var data = JSON.stringify({type: "one"});
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", make_url("event/send"), false);
    xmlHttp.setRequestHeader(header, token);
    xmlHttp.setRequestHeader('Content-type', 'application/json');
    xmlHttp.onload = function () {
        console.log(this.responseText);
    };
    xmlHttp.send(data);
}
function sendEventTwo() {
    var data = JSON.stringify({type: "two"});
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", make_url("event/send"), false);
    xmlHttp.setRequestHeader(header, token);
    xmlHttp.setRequestHeader('Content-type', 'application/json');
    xmlHttp.onload = function () {
        console.log(this.responseText);
    };
    xmlHttp.send(data);
}



function disconnect() {
    if (ws != null) {
        ws.disconnect();
    }
    setConnected(false);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", make_url("event/clean"), false);
    xmlHttp.setRequestHeader(header, token);
    xmlHttp.setRequestHeader('Content-type', 'application/json');
    xmlHttp.onload = function () {
        console.log(this.responseText);
    };
    xmlHttp.send(null);
    console.log("Disconnected");
}

function make_url(path) {
    var base = $("meta[name='_base_url']").attr("content");
    return window.location.protocol + '//' + window.location.host + base + path;
}

function showEventsOne(dataEvent) {
    console.log(dataEvent);
    var stringDataEvent = JSON.stringify(dataEvent);
    $("#events_one").append("<tr><td> " + stringDataEvent + "</td></tr>");
}

function showEventsTwo(dataEvent) {
    console.log(dataEvent);
    var stringDataEvent = JSON.stringify(dataEvent);
    $("#events_two").append("<tr><td> " + stringDataEvent + "</td></tr>");
}

function showCommonEvents(dataEvent) {
    console.log(dataEvent);
}

$(function () {

    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#eventOne").click(function () {
        sendEventOne();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#eventTwo").click(function () {
        sendEventTwo();
    });

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});
