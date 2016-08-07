<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript">
    $(document).ready( function () {
        $('#userEventsTable').DataTable();

    } );
    window.linking= '${ pageContext.request.contextPath }';
    window.incomingRendered = false;
    window.page = '${ page }';

    function renderWindowBlock(block){
        var elementId = '';
        window.zmienna++;
        switch(block.stateId){
            case 5 :
                if(!incomingRendered){
                    window.incomingRendered = true;
                    elementId = 'primaryBlock';
                    block.stateId = 6;
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

        console.log("element ID = " + elementId);

        var href = window.linking + '/events/show';
            if(window.page){
                href += '?state=' + block.stateId + '&page=' + window.page;
            }
            if(block.countedInSameState > 0){
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
                + '</div>';
    }

    function renderWindowBlocks(blocks){
        window.incomingRendered = false;
        for(var i in blocks) {
            var block = blocks[i];
            renderWindowBlock(block);
            console.log(block.stateId + "BLOCK RENDERED")
        }
    }

</script>

<script type="text/javascript">
    //Create stomp client over sockJS protocol
    var socketBlocks = new SockJS("/jbossews/hello");
    var stompBlocksClient;

    // Render user dedicated data from server into HTML, registered as callback
    // when subscribing to dedicated topic
    function renderShowBlocks(frame) {
        console.log("Connected: " + frame);
        var map = JSON.parse(frame.body);
        var blocks = {};
        $.each(map, function(key, value) {
            console.log(key+ ':**********:' + value);
            if(key == "blocks")
                blocks = value;
        });
        renderWindowBlocks(blocks);
        console.log("PAGE: " + page);
    }

    // Callback function to be called when stomp client is connected to server
    var connectBlocksCallback = function() {
        stompBlocksClient.subscribe('/user/blocks/get', renderShowBlocks);
    };

    // Callback function to be called when stomp client could not connect to server
    var errorBlocksCallback = function(errorX) {
        console.log('STOMP: ' + errorX);
        setTimeout(stompBlocksConnect, 1000);
        console.log('STOMP: Reconecting /show in 1 second');
    };

    function stompBlocksConnect() {
        console.log('STOMP: Attempting connection to /show');
        // recreate the stompClient to use a new WebSocket
        stompBlocksClient= Stomp.over(socketBlocks);
        // Connect to server via websocket
        var token = '${ _csrf.token}';
        var headerName = '${ _csrf.headerName}';
        var headers = {};
        headers[headerName] = token;
        var page = window.page;
        if(page){
            headers['page'] = page;
        }
        stompBlocksClient.connect(headers, connectBlocksCallback, errorBlocksCallback);
    }
    stompBlocksConnect();
</script>