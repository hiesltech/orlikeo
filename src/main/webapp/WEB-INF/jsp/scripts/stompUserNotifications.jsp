<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
 


<script type="text/javascript">
    // Create stomp client over sockJS protocol
    var userNotificationsSocket = new SockJS("/jbossews/hello");
    var stompClientUserNotifications;
    var obj = 0;

    // Render user dedicated data from server into HTML, registered as callback
    // when subscribing to dedicated topic
    function renderUserNotifications(frame) {
        counter = frame.body;
        if (counter != sessionStorage.getItem('actualNotificated')) {
            if (counter > sessionStorage.getItem('actualNotificated')) {
                ion.sound.play("door_bell");
            }
        }
        if (counter == 0) {
            $('#notifications').empty();
            $('#notifications').html("Animator");
        } else {
            $('#notifications').html(
                            "Animator  <span class=\"badge\" style=\"background-color:red\">"
                            + counter + "</span>");
        }
        console.log("SESSION: " + sessionStorage.getItem('actualNotificated'));
        sessionStorage.setItem('actualNotificated', counter);
    }

    // Callback function to be called when stomp client is connected to server
    var connectCallbackNotifications = function() {
        stompClient.subscribe('/user/notifications/read', renderNotifications);
    };

    // Callback function to be called when stomp client could not connect to server
    var errorCallbackNotifications = function(error) {
        console.log('STOMP: ' + error);
        setTimeout(connect1, 1000);
        console.log('STOMP: Reconecting in 10 seconds');
    };

    function connect1(){
        console.log('STOMP: Attempting connection');
        if(sessionStorage.getItem('actualNotificated') == null){
            sessionStorage.setItem('actualNotificated', 0);
        }
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