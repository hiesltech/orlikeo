<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
<c:set var="editingUser" value="${editFormUser}"/>
<c:url value="/events/edit" var="editEventUrl" />
<c:url value="/events/create" var="createEventUrl" />
<c:url value="/graphic/${orlikId}?event=${eventId}" var="graphicEventUrl" />
<c:set value="/events/remove" var="removeEventUrl" />

<c:choose>
	<c:when test="${stateId == 1 }"><c:set var="available" value="${false}"/></c:when>
	<c:otherwise><c:set var="available"  value="${true}"/></c:otherwise>
</c:choose>

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
		<c:if test="${ isOrganizer }" >
		<a href="${createEventUrl}?event=${eventId}" title="Zmień orlika"><button type="button" class="btn btn-info"><i class="glyphicon glyphicon-edit"></i></button></a>
		</c:if>
		<c:choose>
			<c:when test="${ available }">
			Orlik ${city} ul.${address}<small>
			<c:if test="${ isOrganizer }"><a href="${graphicEventUrl}" title="Zmień godzinę"><button type="button"  class="btn btn-info"><i class="glyphicon glyphicon-edit"></i></button></a>
			</c:if>
				<fmt:formatDate value="${startDate}" type="both" pattern="HH.mm" /> - <fmt:formatDate value="${endDate}" type="both" pattern="HH.mm" /> (<fmt:formatDate value="${startDate}" type="both" pattern="dd.MM.yyyy" />)</small>
			</c:when>
			<c:otherwise>
				Brak Orlika
			</c:otherwise>
		</c:choose>
				<c:if test="${ isOrganizer }" >
					<button type="button" title="Usuń" class="btn btn-danger"
							data-toggle="modal"
							data-target="#removeEventModal"
							data-ev="<c:out value='${eventId}' />"
							data-href="<c:url value='${ removeEventUrl }' />">
						<i class="glyphicon glyphicon-remove"></i></button>
		</c:if>
		</h1>
		<c:if test="${ available }">
		<ul>
			<li><i class="glyphicon glyphicon-info-sign"></i> Oświetlenie: <c:out value="${lights == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Bieżąca woda: <c:out value="${water == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Prysznic: <c:out value="${shower == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Obowiązujące obuwie: <c:out value="${shoes}"/></li>
			<li><i class="glyphicon glyphicon-user"></i> Animator: <c:if test="${ not empty animator }"> <c:out value="${animator.name}" /></c:if> </li>
		</ul>
		</c:if>
	</div>
</div>

<script type="text/javascript">
	window.saved = '${saved}';
</script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#example').dataTable(
				{
					"language": {
						"url": "//cdn.datatables.net/plug-ins/1.10.9/i18n/Polish.json"
					},
					"bFilter": false
				});
	} );
</script>

<form:form modelAttribute="editEventForm" class="form" action="${editEventUrl}" method="POST">
	<fieldset>
		<!-- Form Name -->
		<legend>Lista zaproszonych</legend>
		<table id="example"   style="background-color: white; " >

			<thead>
				<tr>
					<th>Zaproszenie</th>
					<c:if test="${ isOrganizer }" >
						<th>Prawo zapraszania</th>
					</c:if>
					<th>Zaprosił</th>
					<th>Data urodzenia</th>
					<th>Pozycja</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${ editEventForm.getEventFormMembers() }" var="friend" varStatus="i">                    
                    <tr>
                    <c:set var="friendEmail" value="${friend.getEmail()}" />
                    <c:set var="inviterEmail" value="${friend.getInviter()}" />
                    <c:choose>
                    	<c:when test="${ isOrganizer }">
                    		<c:choose>
                    			<c:when test="${ organizerEmail eq friendEmail }">
                    				<td>
	                    				<i style="color: gold;"class="glyphicon glyphicon-star"></i>
	                    				<span style="margin-left:20px">${friendEmail}</span>
	                    				<form:hidden path="eventFormMembers[${i.index}].invited"  class="invite" />
                    				</td>
                    				<td>
                    					<i style="color: gold; margin-right:20px" class="glyphicon glyphicon-star"></i>
                    					<form:hidden  path="eventFormMembers[${i.index}].allowed" class="allow" />
                    				</td>
                    			</c:when>
                    			<c:otherwise>
                    				<td><form:checkbox path="eventFormMembers[${i.index}].invited"  class="invite" /><span style="margin-left:20px">${friendEmail}</span></td>
                    				<td><form:checkbox  path="eventFormMembers[${i.index}].allowed" class="allow" /></td>
                    			</c:otherwise>		
                    		</c:choose>	
                    	</c:when>
                    	<c:otherwise>
                    		<c:choose>
                    			<c:when test="${ friendEmail eq organizerEmail }">
                    				<td><i style="color: gold; margin-right:20px" class="glyphicon glyphicon-star"></i>
                    					<form:hidden path="eventFormMembers[${i.index}].invited"  value="true"  />
                    					<form:hidden  path="eventFormMembers[${i.index}].allowed"  value="true" />${organizerEmail} 
                    				</td>
                    			</c:when>
                    			<c:when test="${ inviterEmail eq organizerEmail }">
		                    		<td><c:choose>
		                    				<c:when test="${ friend.invited }">
		                    					<form:hidden path="eventFormMembers[${i.index}].invited" value="true"/>
		                    					<i style="color: gold" class="glyphicon glyphicon-star"></i>
		                    				</c:when>
		                    				<c:otherwise>
		                    					<i style="color: gold" class="glyphicon glyphicon-random"></i>
		                    				</c:otherwise>
		                    			</c:choose>
		                    			<form:hidden  path="eventFormMembers[${i.index}].allowed"  /><span style="margin-left:20px">${friendEmail}</span> 
		                    		</td>
		                    	</c:when>
		                    	<c:when test="${ not empty inviterEmail and inviterEmail ne editingUser }">
		                    		<td>
		                    			<i style="color: silver; margin-right:20px" class="glyphicon glyphicon-check"></i>${friendEmail}
		                    			<form:hidden path="eventFormMembers[${i.index}].invited"  class="invite" />
                    					<form:hidden  path="eventFormMembers[${i.index}].allowed" class="allow" /></td>
		                    	</c:when>
		                    	<c:otherwise>
		                    		<td><form:checkbox path="eventFormMembers[${i.index}].invited" class="invite" />
	                    			<form:hidden  path="eventFormMembers[${i.index}].allowed" />
	                    			<span style="margin-left:20px">${friendEmail}</span></td>
		                    	</c:otherwise>
                    		</c:choose>
                    	</c:otherwise>
                    </c:choose>
					<td><form:hidden path="eventFormMembers[${i.index}].email" value="${ friendEmail }" /> 
					<c:choose >
						<c:when test="${ not empty inviterEmail }">
							<c:choose>
								<c:when test="${ organizerEmail eq inviterEmail }">
									<i style="color: gold;" class="glyphicon glyphicon-star"></i>
								</c:when>
								<c:otherwise>
									${ inviterEmail }
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
					</c:choose>
					</td>
					<td><fmt:formatDate value="${ friend.dateOfBirth }" type="both" pattern="dd.MM.yyyy" /></td>
					<td>${ friend.getPosition() }</td>
			    </tr>
			</c:forEach> 

			</tbody>
		</table>
		<div class="control-group">
		<form:hidden path="graphicId" value="${ editEventForm.getGraphicId()}" />
		<form:hidden path="eventId" value="${ eventId }" />
			<div>
				<input type="checkbox"  id="inviteAllStates">
				<label for="inviteAllStates">Zaproś wszystkich</label>
			</div>
			<c:if test="${ isOrganizer }" >
			<div>
				<input type="checkbox" id="allowAllStates">
				<label for="allowAllStates">Pozwól zapraszać wszystkim</label>
			</div>
			</c:if>
		</div>
		<c:choose>
			<c:when test="${ isOrganizer }" >
			<legend>Inne ustawienia</legend>
			<!-- Select Basic -->
			<div class="control-group">
			  <label class="control-label" for="maxPlayers">Limit graczy</label>
			  <div class="controls">
			    <form:select path="usersLimit" id="maxPlayers" name="maxPlayers" class="input-medium">
			    
			    <c:choose>
			    	<c:when test="${playersLimit == 12 }">
			    		<form:option value="12" selected="selected">12 (bez zmian)</form:option>
			    		<form:option value="14" >14 (po 1 na zmianę)</form:option>
			      		<form:option value="16" >16 (po 2 na zmianę)</form:option>
			    	</c:when>
			    		<c:when test="${playersLimit == 14 }">
			    		<form:option value="12">12 (bez zmian)</form:option>
			    		<form:option value="14" selected="selecet" >14 (po 1 na zmianę)</form:option>
			      		<form:option value="16" >16 (po 2 na zmianę)</form:option>
			    	</c:when>
			    		<c:when test="${playersLimit == 16 }">
			    		<form:option   value="12" >12 (bez zmian</form:option>
			    		<form:option value="14" >14 (po 1 na zmianę)</form:option>
			      		<form:option value="16" selected="selected">16 (po 2 na zmianę)</form:option>
			    	</c:when>
			    </c:choose>
			    </form:select>
			  </div>
			</div>
			</c:when>
			<c:otherwise>
				<form:hidden path="usersLimit" value="${playersLimit}"/>
			</c:otherwise>
		</c:choose>
		<!-- Buttons -->
		<div class="control-group">
			<div class="controls">
				<button id="singlebutton" name="singlebutton"
					class="btn btn-primary  pull-right">Zapisz zmiany</button>
			</div>
		</div>
	</fieldset>
</form:form>
<script>

$(document).ready(function() {
    $('#inviteAllStates').click(function(event) {  //on click
        if(this.checked) { // check select status
            $('.invite').each(function() { //loop through each checkbox
                this.checked = true; //select all checkboxes with class "controls"              
            });
        }else{
            $('.invite').each(function() { //loop through each checkbox
                this.checked = false; //deselect all checkboxes with class "controls"                      
            });        
        }
    });
    $('#allowAllStates').click(function(event) {  //on click
        if(this.checked) { // check select status
            $('.allow').each(function() { //loop through each checkbox
                this.checked = true;  //select all checkboxes with class "controls"              
            });
        }else{
            $('.allow').each(function() { //loop through each checkbox
                this.checked = false; //deselect all checkboxes with class "controls"                      
            });        
        }
    });
   
});
</script>

