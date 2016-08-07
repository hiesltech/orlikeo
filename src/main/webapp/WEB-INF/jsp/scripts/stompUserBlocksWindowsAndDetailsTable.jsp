<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript">
    $(document).ready( function () {
        $('#userEventsTable').dataTable(
                {
                    "language": {
                        "url": "https://cdn.datatables.net/plug-ins/1.10.9/i18n/Polish.json"
                    },
                    "bFilter": false
                });
    } );
    window.linking= '${ pageContext.request.contextPath }';
    window.incomingRendered = false;
    window.state = '${ stateId}';
    window.page = '${ page }';
    function renderBlock(block) {
        var elementId = '';
        window.zmienna++;
        switch (block.stateId) {
            case 5 :
                if (!incomingRendered) {
                    window.incomingRendered = true;
                    elementId = 'primaryBlock';
                    block.stateId = 6;
                } else {
                    elementId = 'greenBlock';
                }
                ;
                break;
            case 4 :
                elementId = 'redBlock';
                break;
            case 3 :
                elementId = 'yellowBlock';
                break;
            case 2 :
                elementId = 'silverBlock';
                break;
            case 1 :
                elementId = 'darkBlock';
            default :
                break;
        }

        console.log("element ID = " + elementId);

        var href = window.linking + '/events/show';
        if (window.page) {
            href += '?state=' + block.stateId + '&page=' + window.page;
        }
        if (block.countedInSameState > 0) {

            var day = "--";
            var month = "--";
            var year = "----";
            var hourStart = "--";
            var minutesStart = "--";
            var hourEnd = "--";
            var minutesEnd = "--";
            if(block.startTime) {
                var startTime = new Date(block.startTime);
                var endTime = new Date(block.endTime);
                year = startTime.getFullYear();
                month = startTime.getMonth();
                if (++month < 10) month = "0" + month;
                day = startTime.getDate();
                if (day < 10) day = "0" + day;
                hourStart = startTime.getHours();
                if (hourStart < 10) hourStart = "0" + hourStart;
                minutesStart = startTime.getMinutes();
                if (minutesStart < 10) minutesStart = "0" + minutesStart;
                hourEnd = endTime.getHours();
                if (hourEnd < 10) hourEnd = "0" + hourEnd;
                minutesEnd = endTime.getMinutes();
                if (minutesEnd < 10) minutesEnd = "0" + minutesEnd;
            }


            /*
            var startTime = new Date(block.startTime);
            var endTime = new Date(block.endTime);
            var year = startTime.getFullYear();
            var month = startTime.getMonth();
            month++;
            if (month < 10) month = "0" + month;
            var day = startTime.getDate();
            if (day < 10) day = "0" + day;
            var hourStart = startTime.getHours();
            if (hourStart < 10) hourStart = "0" + hourStart;
            var minutesStart = startTime.getMinutes();
            if (minutesStart < 10) minutesStart = "0" + minutesStart;
            var hourEnd = endTime.getHours();
            if (hourEnd < 10) hourEnd = "0" + hourEnd;
            var minutesEnd = endTime.getMinutes();
            if (minutesEnd < 10) minutesEnd = "0" + minutesEnd;*/
            document.getElementById(elementId)
                    .getElementsByTagName("div")[5]
                    .innerHTML = 'Adres: ' + block.city + ', ' + block.address;
            document.getElementById(elementId)
                    .getElementsByTagName("div")[6]
                    .innerHTML = 'Data rozpoczęcia: ' + day + '.' + month + '.' + year;
            document.getElementById(elementId)
                    .getElementsByTagName("div")[7]
                    .innerHTML = 'Czas: ' + hourStart + ':' + minutesStart + ' - ' + hourEnd + ':' + minutesEnd;
            document.getElementById(elementId).querySelector('span').innerHTML = block.goingToCome + '/'
                    + block.playersLimit;
            document.getElementById(elementId)
                    .querySelector('a')
                    .setAttribute('href', href);
        } else {
            document.getElementById(elementId)
                    .getElementsByTagName("div")[5]
                    .innerHTML = 'Adres: ';
            document.getElementById(elementId)
                    .getElementsByTagName("div")[6]
                    .innerHTML = 'Data rozpoczęcia: --.--.----';
            document.getElementById(elementId)
                    .getElementsByTagName("div")[7]
                    .innerHTML = 'Czas: --' + ':' + '--' + ' - ' + '--' + ':' + '--';
            document.getElementById(elementId).querySelector('span').innerHTML = 0 + '/'
                    + 0;
            document.getElementById(elementId)
                    .querySelector('a')
                    .setAttribute('href', '#');
        }
        document.getElementById(elementId)
                .querySelector('a')
                .innerHTML = '<div class="panel-footer">'
                + '<span class="pull-left">WSZYSTKIE (' + block.countedInSameState + ')</span> <span class="pull-right">'
                + '<i class="glyphicon glyphicon-eye-open"></i>'
                + '</span>'
                + '<div class="clearfix"></div>'
                + '</div>';
/*        var elementId = '';

        switch(block.stateId){
            case 5 :
                if(!incomingRendered){
                    elementId = 'primaryBlock';
                }else{
                    elementId = 'greenBlock';
                };
                break;
            case 4 :
                elementId = 'redBlock';
                break;
            case 3 :
                elementId = 'yellowBlock';
                break;
            case 2 :
                elementId = 'silverBlock';
                break;
            case 1 :
                elementId = 'darkBlock';
            default :
                    break;
        }
        $('#siemanko').append(elementId);
        var href = window.linking + '/events/show';
            if(window.page || window.state ){
                href +=  '?'
            }
            if(!window.incomingRendered){
                block.stateId = 6;
                incomingRendered = true;
            }
            if(window.page && block.stateId){
                href += 'state=' + block.stateId + '&page=' + window.page;
            }else if(window.page){
                href += 'page=' + window.page;
            }else if(window.state){
                href += 'state=' + block.stateId;
            }
            if(block.countedInSameState > 0){
                var startTime = new Date(block.startTime);
                var endTime = new Date(block.endTime);
                var year = startTime.getFullYear();
                var month = startTime.getMonth();
                month++;
                if(month < 10) month = "0" + month;
                var day = startTime.getDate();
                if(day < 10) day = "0" + day;
                var hourStart = startTime.getHours();
                if(hourStart < 10) hourStart = "0" + hourStart;
                var minutesStart = startTime.getMinutes();
                if(minutesStart < 10) minutesStart = "0" + minutesStart;
                var hourEnd = endTime.getHours();
                if(hourEnd < 10) hourEnd = "0" + hourEnd;
                var minutesEnd = endTime.getMinutes();
                if(minutesEnd < 10) minutesEnd = "0" + minutesEnd;
                console.log("elementId = " + elementId);
                document.getElementById(elementId)
                        .getElementsByTagName("div")[5]
                        .innerHTML = 'Adres: ' + block.city + ', ' + block.address;
                document.getElementById(elementId)
                        .getElementsByTagName("div")[6]
                        .innerHTML = 'Data rozpoczęcia: ' + day + '.' + month + '.' + year;
                document.getElementById(elementId)
                        .getElementsByTagName("div")[7]
                        .innerHTML = 'Czas: ' + hourStart + ':' + minutesStart + ' - ' + hourEnd + ':' + minutesEnd;
                document.getElementById(elementId).querySelector('span').innerHTML = block.goingToCome + '/'
                        +  block.playersLimit;
                document.getElementById(elementId)
                        .querySelector('a')
                        .setAttribute('href',href);
            }else{
                document.getElementById(elementId)
                        .getElementsByTagName("div")[5]
                        .innerHTML = 'Adres: ';
                document.getElementById(elementId)
                        .getElementsByTagName("div")[6]
                        .innerHTML = 'Data rozpoczęcia: --.--.----';
                document.getElementById(elementId)
                        .getElementsByTagName("div")[7]
                        .innerHTML = 'Czas: --' + ':' + '--' + ' - ' + '--' + ':' + '--';
                document.getElementById(elementId).querySelector('span').innerHTML = 0 + '/'
                        +  0;
                document.getElementById(elementId)
                        .querySelector('a')
                        .setAttribute('href','#');
            }
        document.getElementById(elementId)
                .querySelector('a')
                .innerHTML = '<div class="panel-footer">'
                + '<span class="pull-left">WSZYSTKIE (' + block.countedInSameState +')</span> <span class="pull-right">'
                +       '<i class="glyphicon glyphicon-eye-open"></i>'
                + '</span>'
                + '<div class="clearfix"></div>'
                + '</div>';*/
    }


    function renderBlocks(blocks){
/*        window.incomingRendered = false;
        for(var i in blocks) {
            var block = blocks[i];
            renderBlock(block);
        }*/
        window.incomingRendered = false;
        for(var i in blocks) {
            var block = blocks[i];
            renderBlock(block);
            console.log(block.stateId + "BLOCK RENDERED")
            }
    }

    function renderTable(eventsDetailsTableRows){
        for(var i in eventsDetailsTableRows){
            var eventRow = eventsDetailsTableRows[i];
            renderTableRow(eventRow);
        }

    function renderTableRow(row){
        var day = "--";
        var month = "--";
        var year = "----";
        var hourStart = "--";
        var minutesStart = "--";
        var hourEnd = "--";
        var minutesEnd = "--";
        if(row.startDate) {
            var startTime = new Date(row.startDate);
            var endTime = new Date(row.endDate);
            year = startTime.getFullYear();
            month = startTime.getMonth();
            if (++month < 10) month = "0" + month;
            day = startTime.getDate();
            if (day < 10) day = "0" + day;
            hourStart = startTime.getHours();
            if (hourStart < 10) hourStart = "0" + hourStart;
            minutesStart = startTime.getMinutes();
            if (minutesStart < 10) minutesStart = "0" + minutesStart;
            hourEnd = endTime.getHours();
            if (hourEnd < 10) hourEnd = "0" + hourEnd;
            minutesEnd = endTime.getMinutes();
            if (minutesEnd < 10) minutesEnd = "0" + minutesEnd;
        }
            var state = "";
            var targetTable = "#userEventsTableRows";
            var style= "background-color : ";
            var token = '<c:out value="${ _csrf.token }" />';
                switch (row.stateId) {
                    case 1:
                        state = "W koszu";
                        style += "#7D7D7D";
                        break;
                    case 2:
                        state = "W budowie";
                        style += "#A9A9A9";
                        break;
                    case 3:
                        state = "Do akceptacji";
                        style += "#F0AD4E; ";
                        break;
                    case 4:
                        state = "Zagrożony";
                        style += "#D9534F";
                        break;
                    case 5:
                        state = "Przyjęty";
                        style += "#5CB85C";
                        break;
                    case 6:
                        state = "Nadchodzący";
                        style += "#428BCA";
                        break;
                }
            var decisionHTML = '';
            if(row.roleId == 1){
                decisionHTML += '<i style="color: gold" class="glyphicon glyphicon-star"></i>';
            }else if(row.roleId == 2 && row.stateId != 1){
                var accept = 'true';
                var reject = 'false';
                var urlSuffix = '&page=' + window.page;
                if(window.state){
                    urlSuffix += '&state=' + window.state;
                }
                var acceptDecisionUrl = window.linking + '/events/decision/' + row.eventId + '?decision=' + accept + urlSuffix;
                var rejectDecisionUrl = window.linking + '/events/decision/' + row.eventId + '?decision=' + reject + urlSuffix;
                switch(row.decisionId){
                    case 1 :
                        decisionHTML += '<i style="color: blue" class="glyphicon glyphicon glyphicon-star-empty"></i>'
                                + '<a href="' + acceptDecisionUrl + '" title="Dołącz"> <i style="color: grey" class="glyphicon glyphicon-plus"></i></a>'
                                + '<a href="' + rejectDecisionUrl + '" title="Odrzuć"> <i style="color: grey" class="glyphicon glyphicon-minus"></i></a>';
                        break;
                    case 2 :
                        decisionHTML += '<i style="color: green" class="glyphicon glyphicon-plus"></i>'
                                + '<a href="' + rejectDecisionUrl + '" title="Odrzuć"> <i style="color: grey" class="glyphicon glyphicon-minus"></i></a>';
                        break;
                    case 3 :
                        decisionHTML += '<a href="' + acceptDecisionUrl + '" title="Dołącz"> <i style="color: grey" class="glyphicon glyphicon-plus"></i></a>'
                                 + ' <i style="color: red" class="glyphicon glyphicon-minus"></i>';
                        break;
                    case 4 :
                        if(row.permission == false){
                            decisionHTML += '<i class=" glyphicon glyphicon-question-sign">';
                        }else{
                            decisionHTML += '<i class="glyphicon glyphicon-random"></i>';
                        }
                }
            }
            var detailsURL = window.linking + '/events/details/' + row.eventId;
            var actionHTML = '<a href="' + detailsURL + '" title="Szczegóły"><button type="button" title="Szczegóły" class="btn btn-ok" style="margin-left:2px">'
                + '<i class="glyphicon glyphicon-eye-open"></i></button></a>';
            if(row.permission){
                var editHREF = window.linking + '/events/edit/' + row.eventId;
                actionHTML += '<a href="' + editHREF + '" title="Edytuj"> <button type="button" title="Szczegóły" class="btn btn-info">'
                        + '<i class="glyphicon glyphicon-edit"></i></button></a>';
            }
            if(row.roleId == 1){
                var removeURL = window.linking + '/events/remove'
                var suffixURL = '?page=' + window.page;
                if(window.state){
                    suffixURL += '&state=' + window.state;
                }
                actionHTML += '<button type="button" title="Usuń" class="btn btn-danger" style="margin-left:2px"'
                        + 'data-toggle="modal"'
                        + 'data-target="#removeEventModal"'
                        + 'data-ev="' + row.eventId + '"'
                        + 'data-href="' + removeURL + suffixURL + '">'
                        + '<i class="glyphicon glyphicon-remove"></i></button>';
            }


            $(targetTable).append(
                    $('<tr style="' + style + '">').append(
                            $('<td>').html(row.address),
                            $('<td>').html(day + "." + month + "." + year),
                            $('<td>').html(hourStart + ":" + minutesStart + " - " + hourEnd + ":" + minutesEnd),
                            $('<td>').html(state),
                            $('<td>').html(decisionHTML),
                            $('<td title="przyjdzie/limit/zaproszonych">').html(row.willCome + "/" + row.playersLimit + "/" + row.invited),
                            $('<td>').html(actionHTML)
                    )
            );
        }
    }
</script>

<script type="text/javascript">
    //Create stomp client over sockJS protocol
    var socketShow = new SockJS("/jbossews/hello");
    var stompShowClient;

    // Render user dedicated data from server into HTML, registered as callback
    // when subscribing to dedicated topic
    function renderShow(frame) {
        console.log("Connected: " + frame);
        var map = JSON.parse(frame.body);
        $('#siemanko').empty();
        var blocks = {};
        var eventsDetailsTableRows = {};
        $.each(map, function(key, value) {
            console.log(key+ ':**********:' + value);
            if(key == "blocks")
                blocks = value;
            if(key == "detailsTable")
                eventsDetailsTableRows = value;
        });
        renderBlocks(blocks);
        $("#userEventsTableRows").empty();
        if(eventsDetailsTableRows.length >0){
            renderTable(eventsDetailsTableRows);
        }

        console.log("Kuniec");
    }

    // Callback function to be called when stomp client is connected to server
    var connectShowCallback = function() {
        console.log('connectShowCallback');
        stompShowClient.subscribe('/user/show/details', renderShow, {oko : "kupa"});
    };

    // Callback function to be called when stomp client could not connect to server
    var errorShowCallback = function(errorX) {
        console.log('STOMP: ' + errorX);
        setTimeout(stompShowConnect, 1000);
        console.log('STOMP: Reconecting /show in 1 second');
    };

    function stompShowConnect() {
        console.log('STOMP: Attempting connection to /show');
        // recreate the stompClient to use a new WebSocket
        stompShowClient= Stomp.over(socketShow);
        // Connect to server via websocket
        var token = '${ _csrf.token}';
        var headerName = '${ _csrf.headerName}';
        var headers = {};
        headers[headerName] = token;
        var page = window.page;
        var state = window.state;
        if(page){
            headers['page'] = page;
        }
        if(state){
            headers['state'] = state;
        }
        stompShowClient.connect(headers, connectShowCallback, errorShowCallback);
    }
    stompShowConnect();
</script>