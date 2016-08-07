<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:url value="/events/register" var="registerEventUrl" />
<c:url value="/events/create" var="createEventUrl" />
<c:url value="/events/graphic" var="graphicEventUrl" />
<c:url value="/events/decision/" var="eventDecisionUrl" />
<c:url value="/events/remove" var="removeEventUrl" />
<c:url value="/events/edit" var="editEventUrl" />



<c:set var="eventId" value="${ details.getEventId() }" />
<c:set var="stateId" value="${ details.getStateId() }" />
<c:set var="decisionId" value="${ details.getDecisionId() }" />
<c:set var="organizerEmail" value="${ details.getOrganizerEmail() }" />
<c:set var="roleId" value="${ details.getRoleId() }" />
<c:set var="userPermission" value="${ details.getPermission() }" />
<c:set var="city" value="${details.getCity() } " />
<c:set var="address" value="${ details.getAddress() }" />
<c:set var="startDate" value="${ details.getStartDate() }" />
<c:set var="endDate" value="${ details.getEndDate() }" />
<c:set var="players" value="${ details.getWillCome() }" />
<c:set var="limit" value="${ details.getPlayersLimit()}" />
<c:set var="invited" value="${ details.getInvited()}" />

<c:set var="lights" value="${ details.getLights() }" />
<c:set var="water" value="${ details.getWater() }" />
<c:set var="shower" value="${ details.getShower()}" />
<c:set var="shoes" value="${ details.getShoes()}" />



<c:set var="available" value="${true}" />
<c:if test="${ stateId == 1 }">
	<c:set var="available" value="${false}" />
</c:if>



<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
		<c:choose>
			<c:when test="${ available }">
			${city} ul.${address}  <small>
				<fmt:formatDate value="${startDate}" type="both" pattern="HH.mm" /> - <fmt:formatDate value="${endDate}" type="both" pattern="HH.mm" /> (<fmt:formatDate value="${startDate}" type="both" pattern="dd.MM.yyyy" />)</small>
				<c:choose >
					<c:when test="${isOrganizer}" >
						<a href="${editEventUrl}/${eventId}" title="Edytuj"> <i class="glyphicon glyphicon-edit" style="margin-left:0.5em"></i></a>
						<a href="${removeEventUrl}/${eventId}" title="Usuń"> <i class="glyphicon glyphicon-remove" style="margin-left:0.5em"></i></a>
					</c:when>
					<c:otherwise>
						<c:if test="${allowed}" >
						<a href="${editEventUrl}/${eventId}" title="Edytuj"> <i class="glyphicon glyphicon-edit" style="margin-left:0.5em"></i></a>
						</c:if>
						
						<c:set var="accept" value="?decision=true" />
		    			<c:set var="reject" value="?decision=false" />
		    			<c:set var="urlSuffix" value="" />
		    			<c:if test="${ not empty page }">
		    				<c:set var="urlSuffix" value="${urlSuffix}&page=${ page }" />
		    			</c:if>
		    			<c:set var="acceptDecisionUrl" value="${eventDecisionUrl}${eventId}${accept}${urlSuffix}" />
		    			<c:set var="rejectDecisionUrl" value="${eventDecisionUrl}${eventId}${reject}${urlSuffix}" />
						
						<c:choose>
							<c:when test="${ decisionId == 1 }">
								<a href="${acceptDecisionUrl}" title="Wezmę udział"> <i class="glyphicon glyphicon-plus" style="margin-left:0.5em; color:green;"></i></a> /
	    						<a href="${rejectDecisionUrl}" title="Nie wezmę udziału"> <i class="glyphicon glyphicon-minus" style="margin-left:0.5em; color:red;"></i></a>	
							</c:when>
							<c:when test="${ decisionId == 2}">
								<i class="glyphicon glyphicon-plus" style="margin-left:0.5em; color:green;"></i> /
	    						<a href="${rejectDecisionUrl}" title="Nie wezmę udziału"> <i class="glyphicon glyphicon-minus" style="margin-left:0.5em; color:grey;"></i></a>
							</c:when>
							<c:when test="${ decisionId == 3}">
								<a href="${acceptDecisionUrl}" title="Wezmę udział"> <i class="glyphicon glyphicon-plus" style="margin-left:0.5em; color: grey;"></i></a> /
	    						<i class="glyphicon glyphicon-minus" style="margin-left:0.5em; color:red;"></i>
							</c:when>
						</c:choose>
					</c:otherwise>
	    		</c:choose>	
	    	</c:when>
	    	<c:otherwise>
	    		Brak orlika <c:if test="${ allowed }">
	    						<a href="${editEventUrl}/${eventId}" title="Edytuj"> <i class="glyphicon glyphicon-edit" style="margin-left:0.5em"></i></a>
	    					</c:if> 
	    	</c:otherwise>
		</c:choose>
		</h1>
		<c:if test="${ available }">
		<ul>
			<li><i class="glyphicon glyphicon-info-sign"></i> Oświetlenie: <c:out value="${ lights == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Bieżąca woda: <c:out value="${ water == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Prysznic: <c:out value="${ shower == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Obowiązujące obuwie: <c:out value="${ shoes }"/></li>
		</ul>
		</c:if>
		<ul>
			<li> Aktualny status:<c:choose>
									<c:when test="${ stateId == 1}"> <i class="glyphicon glyphicon-trash"></i> W koszu</c:when>
									<c:when test="${ stateId == 2}"> <i class="glyphicon glyphicon-thumbs-down"></i> W budowie</c:when>
									<c:when test="${ stateId == 3}"> <i class="glyphicon glyphicon-send"></i> Do akceptacji</c:when>
									<c:when test="${ stateId == 4}"> <i class="glyphicon glyphicon-warning-sign"></i> Zagrożony</c:when>
									<c:when test="${ stateId == 5}"> <i class="glyphicon glyphicon-thumbs-up"></i> Przyjęty</c:when>
								</c:choose></li>
			<li> Wezmą udział : ${players}</li>
			<li> Miejsc : ${limit}</li>
			<li> Zaproszonych : ${invited}</li>
		</ul>
	</div>
</div>


</br>
</br>
</br>
<div class="row">
		<!-- Form Name -->
<legend>Wezmą udział (${usersJoinedDecision.size()})</legend>
<table id="goingToComeTable" style="background-color:white">
    <thead style="background-color:#999999">
        <tr>
            <th data-field="orlikId" data-align="center" data-sortable="true"><i class="glyphicon glyphicon-user"></i> Użytkownik</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${usersJoinedDecision }" var="user">
    	<tr>
    		<td>${user.email }</td>		
    	</tr> 
    </c:forEach>      	
    </tbody>
</table>
</div>






</br>
</br>
</br>
<div class="row">
		<!-- Form Name -->
<legend>Niezdecydowani (${usersWithoutDecision.size()})</legend>
<table id="withoutDecisionTable" style="background-color:white">
    <thead style="background-color:#999999">
        <tr>
            <th data-field="orlikId" data-align="center" data-sortable="true"><i class="glyphicon glyphicon-user"></i> Użytkownik</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${usersWithoutDecision }" var="user">
    	<tr>
    		<td>${user.email }</td>
    	</tr> 
    </c:forEach>       	
    </tbody>
</table>
</div>

</br>
</br>
</br>
<div class="row">
		<!-- Form Name -->
<legend>Odrzucili (${usersRejectedDecision.size()})</legend>
<table id="rejectedTable" style="background-color:white">
    <thead style="background-color:#999999">
        <tr>
            <th data-field="orlikId" data-align="center" data-sortable="true"><i class="glyphicon glyphicon-user"></i> Użytkownik</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${usersRejectedDecision }" var="user">
    	<tr>
    		<td>${user.email }</td>
    	</tr> 
    </c:forEach>  	
    </tbody>
</table>
</div>

</br>
</br>
</br>
<div class="row">
		<!-- Form Name -->
<legend>Mogących zapraszać (${usersPermittedOnly.size()})</legend>
<table id="canInviteTable" style="background-color:white">
    <thead style="background-color:#999999">
        <tr>
            <th data-field="orlikId" data-align="center" data-sortable="true"><i class="glyphicon glyphicon-user"></i> Użytkownik</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${usersPermittedOnly }" var="user">
    	<tr>
    		<td>${user.email }</td>
    	</tr> 
    </c:forEach>  	
    </tbody>
</table>
</div>
</br>
</br>
</br>
<script src="<c:url value="/resources/mytheme/bootstrap/js/bootstrap-table.js" />"></script>