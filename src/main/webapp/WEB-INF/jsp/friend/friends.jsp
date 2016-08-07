<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>




<c:url value="/friends/userDetail" var="detailsUrl" />
<c:url value="/friends/acceptUser" var="acceptUserUrl" />

<!-- INVITE USER-->
<c:url value="/friends/friendRequest" var="inviteUserUrl" />
<!-- ACCEPT INVITATION-->
<c:url value="/friends/acceptUser" var="acceptInvitationUrl" />
<!-- REJECT INVITATION-->
<c:url value="/friends/reject" var="rejectInvitationUrl" />
<!-- REMOVE FROM FRIENDS-->
<c:url value="/friends/remove" var="removeFriendshipUrl" />
<!-- CANCEL INVITATION-->
<c:url value="/friends/cancel" var="cancelInvitationUrl" />
<!-- BLOCK USER-->
<c:url value="/friends/block" var="blockUserUrl" />
<!-- UNBLOCK USER-->
<c:url value="/friends/unblock" var="unblockUserUrl" />



<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
			Znajomi <small><i class="glyphicon glyphicon-user"></i> Moi znajomi</small>
		</h1>
	</div>
</div>

<table id="friendsTable" style="background-color:white">
    <thead style="background-color:#999999">
        <tr>
            <th data-field="uId" data-align="left" data-sortable="true">Użytkownik</th>
            <th data-field="email" data-align="center" data-sortable="true">Email</th>
            <th data-align="center">Szczegóły </th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${friends}" var="friend" varStatus="i">
		<c:set var="name"  value="${ friend.name }"/>
		<c:set var="surname"  value="${ friend.surname }"/>
		<c:set var="email"  value="${ friend.email }"/>
		<c:set var="userId"  value="${ friend.id }"/>
    	<tr>
    		<td>${name} ${surname}</td>
    		<td>${email}</td>		
    		<td><div style="display: inline-block; margin: auto">
				<a href="${detailsUrl}/${email}" ><button title="Szczegóły" class="btn btn-info"> <i class="glyphicon glyphicon-eye-open"></i> </button> </a>
				<button type="button" title="Usuń" class="btn btn-danger" data-toggle="modal" data-target="#removeFriendshipModal" data-whatever="${ email }"><i class="glyphicon glyphicon-minus-sign"></i></button>
				<button type="button" title="Zablokuj" class="btn btn-danger" data-toggle="modal" data-target="#blockInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ban-circle"></i></button>
				</div>
    		</td>    		
    	</tr>  
   </c:forEach>     	
    </tbody>
</table>

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header"><small><i class="glyphicon glyphicon-user"></i> Otrzymane zaproszenia</small>
		</h1>
	</div>
</div>

<table id="invitationsReceiveTable" style="background-color: white">
	<thead style="background-color: #999999">
		<tr>
			<th data-field="uId" data-align="left" data-sortable="true">Użytkownik</th>
			<th data-field="email" data-align="center" data-sortable="true">Email</th>
			<th data-align="center">Akcja</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${friendsReceivedRequests}" var="requester"
			varStatus="i">
			<c:set var="name" value="${ requester.name }" />
			<c:set var="surname" value="${ requester.surname }" />
			<c:set var="email" value="${ requester.email }" />
			<c:set var="userId" value="${ requester.id }" />
			<tr>
				<td>${name}${surname}</td>
				<td>${ email }</td>
				<td>
					<div style="display: inline-block; margin: auto">
						<a href="${detailsUrl}/${email}" ><button title="Szczegóły" class="btn btn-info"> <i class="glyphicon glyphicon-eye-open"></i> </button> </a>
						<button type="button" title="Akceptuj" class="btn btn-success" data-toggle="modal" data-target="#acceptInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ok-sign"></i></button>
						<button type="button" title="Odrzuć" class="btn btn-warning" data-toggle="modal" data-target="#declineInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-remove-sign"></i></button>
						<button type="button" title="Zablokuj" class="btn btn-danger" data-toggle="modal" data-target="#blockInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ban-circle"></i></button>
					</div>		
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>



<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header"><small><i class="glyphicon glyphicon-user"></i> Wysłane zaproszenia</small></h1>

	</div>
</div>

<table id="invitationsRequestsTable" style="background-color:white">
    <thead style="background-color:#999999">
        <tr>
            <th data-field="uId" data-align="left" data-sortable="true">Użytkownik</th>
            <th data-field="email" data-align="center" data-sortable="true">Email</th>
            <th data-align="center">Szczegóły </th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${friendsPendedRequests}" var="friend" varStatus="i">
		<c:set var="name"  value="${ friend.name }"/>
		<c:set var="surname"  value="${ friend.surname }"/>
		<c:set var="email"  value="${ friend.email }"/>
		<c:set var="userId"  value="${ friend.id }"/>
    	<tr>
    		<td>${name} ${surname}</td>
    		<td>${email}</td>		
    		<td>
				<div style="display: inline-block; margin: auto">
					<a href="${detailsUrl}/${email}" ><button title="Szczegóły" class="btn btn-info"> <i class="glyphicon glyphicon-eye-open"></i> </button> </a>
					<button type="button" title="Cofnij" class="btn btn-warning" data-toggle="modal" data-target="#cancelInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-stop"></i></button>
					<button type="button" title="Zablokuj" class="btn btn-danger" data-toggle="modal" data-target="#blockInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ban-circle"></i></button>
				</div>	
    		</td>    		
    	</tr>  
   </c:forEach>     	
    </tbody>
</table>






<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header"><small><i class="glyphicon glyphicon-user"></i> Zablokowani użytkownicy</small></h1>

	</div>
</div>

<table id="blockedTable" style="background-color:white">
    <thead style="background-color:#999999">
        <tr>
            <th data-field="uId" data-align="left" data-sortable="true">Użytkownik</th>
            <th data-field="email" data-align="center" data-sortable="true">Email</th>
            <th data-align="center">Szczegóły </th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${blockedUsers}" var="friend" varStatus="i">
		<c:set var="name"  value="${ friend.name }"/>
		<c:set var="surname"  value="${ friend.surname }"/>
		<c:set var="email"  value="${ friend.email }"/>
		<c:set var="userId"  value="${ friend.id }"/>
    	<tr>
    		<td>${name} ${surname}</td>
    		<td>${email}</td>		
    		<td>
				<div style="display: inline-block; margin: auto">
					<a href="${detailsUrl}/${email}" ><button title="Szczegóły" class="btn btn-info"> <i class="glyphicon glyphicon-eye-open"></i> </button> </a>
					<button type="button" title="Odblokuj" class="btn btn-success" data-toggle="modal" data-target="#unblockUser" data-whatever="${ email }"><i class="glyphicon glyphicon-refresh"></i></button>
				</div>	
    		</td>    		
    	</tr>  
   </c:forEach>     	
    </tbody>
</table>