<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript">
    $(document).ready( function () {
        $('#forAcceptationTable').dataTable(
                {
                    "language": {
                        "url": "https://cdn.datatables.net/plug-ins/1.10.9/i18n/Polish.json"
                    },
                    "bFilter": false
                });
    } );
    $(document).ready( function () {
        $('#treathenedTable').dataTable(
                {
                    "language": {
                        "url": "https://cdn.datatables.net/plug-ins/1.10.9/i18n/Polish.json"
                    },
                    "bFilter": false
                });
    } );
    $(document).ready( function () {
        $('#acceptedTable').dataTable(
                {
                    "language": {
                        "url": "https://cdn.datatables.net/plug-ins/1.10.9/i18n/Polish.json"
                    },
                    "bFilter": false
                });
    } );
    $(document).ready( function () {
        $('#underConstructionTable').dataTable(
                {
                    "language": {
                        "url": "https://cdn.datatables.net/plug-ins/1.10.9/i18n/Polish.json"
                    },
                    "bFilter": false
                });
    } );
</script>

<script type="text/javascript">
    //Create stomp client over sockJS protocol
    var socket2 = new SockJS("/jbossews/hello");
    var stompClient2;

    // Render user dedicated data from server into HTML, registered as callback
    // when subscribing to dedicated topic
    function renderEvents(frame2) {
        console.log("Connected: " + frame2);
        var events = JSON.parse(frame2.body);
        $('#forAcceptation').empty();
        $('#inDangerous').empty();
        $('#accepted').empty();
        $('#underConstruction').empty();
        for(var i in events) {
            var event = events[i];
            var year = (new Date(event.startDate)).getFullYear();
            var month = (new Date(event.startDate)).getMonth();
            if(month < 10) month = "0" + month;
            var day = (new Date(event.startDate)).getDate();
            if(day < 10) day = "0" + day;
            var hourStart = (new Date(event.startDate)).getHours();
            if(hourStart < 10) hourStart = "0" + hourStart;
            var minutesStart = (new Date(event.startDate)).getMinutes();
            if(minutesStart < 10) minutesStart = "0" + minutesStart;
            var hourEnd = (new Date(event.endDate)).getHours();
            if(hourEnd < 10) hourEnd = "0" + hourEnd;
            var minutesEnd = (new Date(event.endDate)).getMinutes();
            if(minutesEnd < 10) minutesEnd = "0" + minutesEnd;
            var tag = "";
            var state = "";
            var targetTable = "";
            var addButton = '';
            var form = '';
            var style= "background-color : ";
            var token = '<c:out value="${ _csrf.token }" />';
            switch (event.stateId) {
                case 2:
                    tag = ' class="active"';
                    state = "W budowie";
                    targetTable = '#underConstruction';
                    style += "#F2F2F2";
                    break;
                case 3:
                    tag = ' class="warning"';
                    state = "Do akceptacji";
                    targetTable = '#forAcceptation';
                    addButton = '<button id="' + event.eventId +'" type="button" class="btn btn-success">Zatwierdź</button>';
                    var url = '<c:out value="${pageContext.request.contextPath}/pane/accept" />';
                    form ='<form action="' + url + '" method="POST">'
                            + '<input type="hidden" value="' + event.eventId + '" name="ev" />'
                            + '<button id="singlebutton" name="singlebutton" class="btn btn-success pull-right">Zatwierdź</button>'
                            + '<input type="hidden" name="_csrf" value="' + token + '" />'
                            + '</form>';
                    style += "#FFCC00";
                    break;
                case 4:
                    tag = ' class="danger"';
                    state = "Zagrożony";
                    targetTable = '#inDangerous';
                    style += "#FF7F7F";
                    break;
                case 5:
                    tag = ' class="success"';
                    state = "Przyjęty";
                    targetTable = '#accepted';
                    style += "#66CCFF";
                    break;
            }

            $(targetTable).append(
                    $('<tr style="' + style + '" ' + tag +'>').append(
                            $('<td>').html(event.organizerEmail),
                            $('<td>').html(year + "." + month + "." + day),
                            $('<td>').html(hourStart + ":" + minutesStart),
                            $('<td>').html(hourEnd + ":" + minutesEnd),
                            $('<td>').html(event.willCome + "/" + event.playersLimit),
                            $('<td>').html(form)
                    )
            );
        }
    }

    // Callback function to be called when stomp client is connected to server
    var connectCallback = function() {
        stompClient2.subscribe('/user/events/read', renderEvents);
    };

    // Callback function to be called when stomp client could not connect to server
    var errorCallback = function(error2) {
        console.log('STOMP: ' + error2);
        setTimeout(stompConnect, 1000);
        console.log('STOMP: Reconecting in 10 seconds');
    };

    function stompConnect() {
        console.log('STOMP: Attempting connection');
        // recreate the stompClient to use a new WebSocket
        stompClient2 = Stomp.over(socket2);
        // Connect to server via websocket
        var token = '${ _csrf.token}';
        var headerName = '${ _csrf.headerName}';
        var headers = {};
        headers[headerName] = token;
        stompClient2.connect(headers, connectCallback, errorCallback);
    }
    stompConnect();
</script>