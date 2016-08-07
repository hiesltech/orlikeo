<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html>
<html>
<head>
    <title>Hello WebSocket</title>
    <script src="<c:url value="/resources/websocket/sockjs-0.3.4.js" />" type="text/javascript" ></script>
    <script src="<c:url value="/resources/websocket/stomp.js" />" type="text/javascript" > </script>
    <script src="<c:url value="/resources/mytheme/bootstrap/js/jquery.min.js" />" type="text/javascript" ></script>

    <script type="text/javascript">
        // Create stomp client over sockJS protocol
        var socket = new SockJS("/jbossews/hello");
        var stompClient;
        var obj = 0;

        // Render user dedicated data from server into HTML, registered as callback
        // when subscribing to dedicated topic
        function renderNotifications(frame) {
            var counter = frame.body;
            console.log(frame.body);
            if (counter == 0) {
                $('#notifications').append(frame.body);
            } else {
                $('#notifications').append(
                        "<span>"
                        + counter + "</span><br>");
            }
        }

        // Callback function to be called when stomp client is connected to server
        var connectCallbackNotifications = function() {
            stompClient.subscribe('/topic/price', renderNotifications);
        };

        // Callback function to be called when stomp client could not connect to server
        var errorCallbackNotifications = function(error) {
            console.log('STOMP: ' + error);
            setTimeout(connect1, 1000);
            console.log('STOMP: Reconecting in 10 seconds');
        };

        function connect1(){
            console.log('STOMP: Attempting connection');
            // recreate the stompClient to use a new WebSocket
            stompClient = Stomp.over(socket);
            // Connect to server via websocket
            var headerName = '${ _csrf.headerName }';
            var csrf = '${ _csrf.token }';
            var headers = {};
            headers[headerName] = csrf;
            stompClient.connect(headers, connectCallbackNotifications, errorCallbackNotifications);
        }
        connect1();
    </script>
</head>
<body>
<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being enabled. Please enable
    Javascript and reload this page!</h2></noscript>
<div id="notifications">
    Animator
    </div>
</div>
</body>
</html>