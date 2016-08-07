<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<c:url value="/events/register" var="registerEventUrl" />
<c:url value="/events/create" var="createEventUrl" />
<c:url value="/graphic" var="graphicEventUrl" />

<c:set var="eventId" value="${eventDetails.eventId}"/>
<c:set var="orlikId" value="${eventDetails.orlikId }" />
<c:set var="stateId" value="${eventDetails.stateId}"/> 
<c:set var="address" value="${eventDetails.address}"/> 
<c:set var="city" value="${eventDetails.city}"/>
<c:set var="organizerEmail" value="${eventDetails.organizerEmail}"/>
<c:set var="startDate" value="${eventDetails.startDate}"/>
<c:set var="endDate" value="${eventDetails.endDate}"/>
<c:set var="decisionId" value="${eventDetails.decisionId}"/>
<c:set var="roleId" value="${eventDetails.roleId}"/>
<c:set var="permission" value="${eventDetails.permission}"/>
<c:set var="playersLimit" value="${eventDetails.playersLimit}"/>
<c:set var="willCome" value="${eventDetails.willCome}"/>
<c:set var="invited" value="${eventDetails.invited}"/>
<c:set var="lights" value="${eventDetails.lights}"/>
<c:set var="water" value="${eventDetails.water}"/>
<c:set var="shower" value="${eventDetails.shower}"/>
<c:set var="shoes" value="${eventDetails.shoes}"/>



<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Utworzyłeś wydarzenie
			<a href="${createEventUrl}?event=${eventId}"><i class="glyphicon glyphicon-edit"></i></a>
			${city} ul.${address} <small><a
				href="${graphicEventUrl}/${orlikId}?event=${eventId}"><i class="glyphicon glyphicon-edit"></i></a>
				<fmt:formatDate value="${startDate}" type="both" pattern="HH.mm" /> - <fmt:formatDate value="${endDate}" type="both" pattern="HH.mm" /> (<fmt:formatDate value="${startDate}" type="both" pattern="dd.MM.yyyy" />)</small>
		</h1>
		<ul>
			<li><i class="glyphicon glyphicon-info-sign"></i> Oświetlenie: <c:out value="${lights == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Bieżąca woda: <c:out value="${water == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Prysznic: <c:out value="${shower == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Obowiązujące obuwie: <c:out value="${shoes}"/></li>
			<li><i class="glyphicon glyphicon-user"></i> Animatorzy: <c:forEach items="${managers}" var="manager" varStatus="i">
																		<c:out value="${manager.email}"/><c:if test="${ i.index < managers.size()-1 }">, </c:if>
																	</c:forEach></li>
		</ul>
	</div>
</div>

		<legend>Lista biorących udział w wydarzeniu</legend>
		<table data-toggle="table" style="background-color: white" >
			<thead>
				<tr>
					<th>Prawo zapraszania</th>
					<th>Status uzytkwnika</th>
					<th>Użytkownik</th>
					<th>Pozycja</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${ usersEvents }" var="userEvent" varStatus="i">   
			<c:set var="decision"  value="${ userEvent.decision.id }"/>  
			<c:set var="permission"  value="${ userEvent.userPermission }"/> 
			<c:set var="email"  value="${ userEvent.user.email}"/>
			<c:set var="userPrincipal"  value="${ pageContext.request.userPrincipal }"/>               
                    <tr>
					<td>${ permission ? "TAK" : "NIE" }</td>
					<td><c:choose>
						    <c:when test="${decision == 1}">
						    	<c:if test="${not empty userPrincipal}">
    								Zaproszony
								</c:if>
						    </c:when>
						    <c:when test="${decision == 2}">
						        <c:if test="${not empty userPrincipal}">
    								${userPrincipal.name == email ? "Organizator" : "Zaakceptował" }
								</c:if>
						    </c:when>
						    <c:when test="${decision == 3}">
						        Odrzucił
						    </c:when>
						    <c:when test="${decision == 4}">
						    	Nie zaproszony
						    </c:when>
						    <c:otherwise>
						        Nie wiadomo co zrobił
						    </c:otherwise>
						</c:choose></td>
					<td>${ email }</td>
					<td></td>
			    </tr>
			</c:forEach> 

			</tbody>
		</table>
		



<script src="<c:url value="/resources/mytheme/bootstrap/js/bootstrap-table.js" />" ></script>