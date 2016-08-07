<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<c:set var="username"   scope="session"  value="${username}"/>

<c:url value="/home" var="homeUrl" />
<c:url value="/account/profile" var="profileUrl" />
<c:url value="/events/create" var="createEventUrl" />


<c:url value="/friends" var="friendsUrl" />
<c:url value="/friends/search" var="searchFriends" />
<c:url value="/planner" var="plannerUrl" />
<c:url value="/pane" var="paneUrl" />
<c:url value="/websocket" var="socket" />


<c:url value="/events/show" var="showEvents" />

<c:url value="/notifications/" var="userNotificationsUrl" />

<c:url value="/admin/animators" var="adminAnimatorsUrl" />
<c:url value="/admin/orliks" var="adminOrliksUrl" />



<!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
                      
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${homeUrl}"><i class="glyphicon glyphicon-globe"></i>rlikeo!</a>
            </div>
            
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <sec:authorize access="hasRole('ROLE_ANIMATOR')">
    			<li class="nav nav-pills">
        			<li><a href="${paneUrl}" id="notifications" >Animator</a></li>
    			</li>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_USER')">
                <li class="dropdown">
                    <a aria-expanded="false" href="#" class="dropdown-toggle"
                       data-toggle="dropdown" id="userNotificationsCounter">Powiadomienia</a>
                    <ul class="dropdown-menu message-dropdown" id="userNotificationsList">
                    </ul>
                </li>
                </sec:authorize>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-user"></i> <sec:authentication property="principal.username"/> <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="${profileUrl}" ><i class="glyphicon glyphicon-cog"></i> Profil</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="<c:url value="/j_spring_security_logout" />"><i class="glyphicon glyphicon-off"></i> Wyloguj</a>
                        </li>
                    </ul>
                </li>
            </ul>


            
            
<!--  LEFT MENU  -->
            <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
<%--                 <li class="active">
                        <a href="${homeUrl}" data-toggle="collapse" data-target="#main"><i class="glyphicon glyphicon-eye-open"></i> SZYBKI PRZEGLĄD </a>
                    </li> --%>
                    <sec:authorize access="hasRole('ROLE_USER')">
                    <li class="active" >
                        <a href="javascript:;" data-toggle="collapse" data-target="#events"><i class="glyphicon glyphicon-list"></i> WYDARZENIA </a>
                        <ul id="events" class="collapse" >
                            <li>
                                <a href="${createEventUrl}"><i class="glyphicon glyphicon-plus"></i> Utwórz nowe </a>
                            </li>
                            <li>
                                <a href="${ showEvents }"><i class="glyphicon glyphicon-pushpin"></i> Wszystkie </a> 
                            </li>
                            <li>
                                <a href="${showEvents}?page=organized"><i class="glyphicon glyphicon-star"></i> Organizowane </a>
                            </li>
                            <li>
                                <a href="${showEvents}?page=invitations"><i class="glyphicon glyphicon-list-alt"></i> Zaproszenia </a>
                            </li>
                        </ul>
                    </li>
                    <li class="active">
                        <a href="javascript:;" data-toggle="collapse" data-target="#friends"><i class="glyphicon glyphicon-user"></i><i class="glyphicon glyphicon-user"></i> ZNAJOMI </a>
                        <ul id="friends" class="collapse" >
                            <li>
                                <a href="${friendsUrl}"><i class="glyphicon glyphicon-link"></i> Moi znajomi </a>
                            </li>
                            <li>
                                <a href="${searchFriends}"><i class="glyphicon glyphicon-search"></i> Szukaj znajomych </a>
                            </li>
                        </ul>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_ANIMATOR')">
                    <li class="active">
                        <a href="javascript:;" data-toggle="collapse" data-target="#animator"><i class="glyphicon glyphicon-home"></i> ANIMATOR </a>
                        <ul id="animator" class="collapse" >
                            <li>
                                <a href="${plannerUrl}"><i class="glyphicon glyphicon-calendar"></i> Grafik </a>
                            </li>
                            <li>
                                <a href="${paneUrl}"><i class="glyphicon glyphicon-list-alt"></i> Panel </a>
                            </li>
                        </ul>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_USER')" >
                    <li class="active">
                        <a href="${userNotificationsUrl}" data-toggle="collapse" data-target="#userNotifications"><i class="glyphicon glyphicon-comment"></i> POWIADOMIENIA </a>
                    </li>
                    </sec:authorize>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li class="active">
                        <a href="javascript:;" data-toggle="collapse" data-target="#admin"><i class="glyphicon glyphicon-headphones"></i> ADMINISTRATOR </a>
                        <ul id="admin" class="collapse" >
                            <li>
                                <a href="${adminOrliksUrl}"><i class="glyphicon glyphicon-picture"></i> Orliki </a>
                            </li>
                        </ul>
                     </li>
                </sec:authorize>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </nav>
        <!--  
        
        
        -->




<script type="text/javascript">
    // Create stomp client over sockJS protocol
    var userNotificationsSocket = new SockJS("/jbossews/hello");
    var stompClientUserNotifications;
    window.basePath = '${pageContext.request.contextPath}';

    // Render user dedicated data from server into HTML, registered as callback
    // when subscribing to dedicated topic
    function renderUserNotifications(frame) {
        var notifications = JSON.parse(frame.body);
        console.log("Masz " + notifications.length + " powiadomienia");
        $('#userNotificationsCounter').empty();
        $('#userNotificationsList').empty();
        if(notifications.length == 0){
            $('#userNotificationsCounter').html('Powiadomienia');
        }else{
            $('#userNotificationsCounter').html('Powiadomienia <span class="badge" style="background-color:#66CCFF">' + notifications.length + '</span>');
            for(var i in notifications) {
                var notification = notifications[i];
                var notifyTime = new Date(notification.date);
                var year = notifyTime.getFullYear();
                var month = notifyTime.getMonth();
                if (++month < 10) month = "0" + month;
                var day = notifyTime.getDate();
                if (day < 10) day = "0" + day;
                var hour = notifyTime.getHours();
                if (hour < 10) hour = "0" + hour;
                var minutes = notifyTime.getMinutes();
                if (minutes < 10) minutes = "0" + minutes;
                var dateString = day + "." + month + "." + year + ", " + hour + ":" + minutes;
                var glyphicon;
                if(notification.link.indexOf("event") > -1){
                    glyphicon = 'glyphicon glyphicon-picture';
                }else if(notification.link.indexOf("userDetail") > -1){
                    glyphicon = 'glyphicon glyphicon-user';
                }else{
                    glyphicon = 'glyphicon glyphicon-globe';
                }
                var notifyLink = "#";
                var removalFn = '';
                if(notification.link != '#'){
                    notifyLink = window.basePath + notification.link;
                }else{
                    removalFn = 'onclick="resetOne(' + notification.id + ')"';
                }
                $('#userNotificationsList').append('<li class="message-preview">'
                        + '<a href="' + notifyLink +'" ' + removalFn + ' >'
                        + '<div class="media">'
                        + '<span class="pull-left">'
                        + '<i class="' + glyphicon + '"></i>'
                        + '</span>'
                        + '<div class="media-body">'
                        + '<h5 class="media-heading"><strong>' + notification.subject + '</strong>'
                + '</h5>'
                + '<p class="small text-muted"><i class="fa fa-clock-o"></i>' + dateString + '</p>'
                + '<p>' + notification.description + '</p>'
                + '</div>'
                + '</div>'
                + '</a>'
                + '</li>'
                );
                if(i === 3)
                    break;
            }
            $('#userNotificationsList').append('<li class="message-footer">'
                    + '<a href="${userNotificationsUrl}">Zobacz wszystkie</a>'
                    + '<a href="#" id="allRead" onclick="myFunction()">Oznacz wszystkie jako przeczytane</a>'
                    + '</li>'
            );

        }
    }

    // Callback function to be called when stomp client is connected to server
    var connectCallbackUserNotifications = function() {
        stompClientUserNotifications.subscribe('/user/notifications/dedicated', renderUserNotifications);
    };

    var myFunction =  function() {
        stompClientUserNotifications.send('/user/notifications/dedicated', {checked: 0});
    };

    var resetOne = function(id){
        stompClientUserNotifications.send('/user/notifications/dedicated', {checked: id});
    };


    // Callback function to be called when stomp client could not connect to server
    var errorCallbackUserNotifications = function(error) {
        console.log('STOMP: ' + error);
        setTimeout(connectX, 1000);
        console.log('STOMP: Reconecting in 10 seconds');
    };

    function connectX(){
        console.log('STOMP: Attempting connection');

        // recreate the stompClient to use a new WebSocket
        stompClientUserNotifications = Stomp.over(userNotificationsSocket);
        // Connect to server via websocket
        var headerName = '${ _csrf.headerName }';
        var csrf = '${ _csrf.token }';
        var headers = {};
        headers[headerName] = csrf;
        stompClientUserNotifications.connect(headers, connectCallbackUserNotifications, errorCallbackUserNotifications);
    }
    connectX();
</script>