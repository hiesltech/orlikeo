<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<c:url value="/events/register" var="registerEventUrl" />
<c:url value="/events/create" var="createEventUrl" />
<c:url value="/events/graphic" var="graphicEventUrl" />

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
			Orlik ${orlik.getCity()} ul.${orlik.getAddress()} <small>
			<fmt:formatDate value="${ event.startTime }" type="both" pattern="HH.mm" /> - <fmt:formatDate value="${event.getEndTime()}" type="both" pattern="HH.mm" /> (<fmt:formatDate value="${event.getStartTime()}" type="both" pattern="dd.MM.yyyy" />)</small>
		</h1>
		
		<ul>
			<li><i class="glyphicon glyphicon-info-sign"></i> Oświetlenie: <c:out value="${orlik.getLights() == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Bieżąca woda: <c:out value="${orlik.getWater() == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Prysznic: <c:out value="${orlik.getShower() == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Obowiązujące obuwie: <c:out value="${orlik.getShoes()}"/></li>
			<li><i class="glyphicon glyphicon-user"></i> Animatorzy:<c:if test="${not empty animator}" > <c:out value="${animator.email}" /></c:if> </li>
		</ul>
	</div>
</div>

 
<form:form modelAttribute="registerEventForm" class="form" action="${registerEventUrl}" method="POST">
	<fieldset>
		<!-- Form Name -->
		<legend>Zaproś znajomych</legend>
		<table id="eventReserveTable" style="background-color: white" >
			<thead>
				<tr>
					<th>Zaproszenie</th>
					<th>Prawo zapraszania</th>
					<th>Użytkownik</th>
					<th>Wiek</th>
					<th>Pozycja</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${ registerEventForm.getEventFormMembers() }" var="friend" varStatus="i">                    
                    <tr>
                    <td><form:checkbox path="eventFormMembers[${i.index}].invited" class="invite" value="true" /></td>
					<td><form:checkbox path="eventFormMembers[${i.index}].allowed" class="allow" value="true" /></td>
					<td><form:hidden path="eventFormMembers[${i.index}].email" value="${ friend.getEmail() }" /> ${ friend.getEmail() }</td>
					<td><%-- ${ friend.getAge() } --%></td>
					<td>${ friend.getPosition() }</td>
			    </tr>
			</c:forEach> 

			</tbody>
		</table>
		
		<div class="control-group .">
		<form:hidden path="graphicId" value="${ registerEventForm.getGraphicId() }" />
			<div>
				<input type="checkbox"  id="inviteAllStates">
				<label for="inviteAllStates">Zaproś wszystkich</label>
			</div>
			<div>
				<input type="checkbox" id="allowAllStates">
				<label for="allowAllStates">Pozwól zapraszać wszystkim</label>
			</div>
		</div>
		<legend>Inne ustawienia</legend>
		<!-- Select Basic -->
		<div class="control-group">
		  <label class="control-label" for="maxPlayers">Limit graczy</label>
		  <div class="controls">
		    <form:select path="usersLimit" id="maxPlayers" name="maxPlayers" class="input-medium">
		      <form:option value="12">12 (bez zmian)</form:option>
		      <form:option selected="selected" value="14" >14 (po 1 na zmianę)</form:option>
		      <form:option value="16">16 (po 2 na zmianę)</form:option>
		    </form:select>
		  </div>
		</div>
		<!-- Buttons -->
		<div class="control-group .">
			<div class="controls">
<!-- 				Confirm changes
				<button id="singlebutton" name="singlebutton"
					class="btn btn-primary  pull-right">Zapisz zmiany / Zaproś</button> -->
				<!-- New event -->
				<button id="singlebutton" name="singlebutton"
					class="btn btn-primary  pull-right">Utwórz wydarzenie</button>
			</div>
		</div>
	</fieldset>
</form:form>



<script>

$(document).ready(function() {
    $('#inviteAllStates').click(function(event) {  //on click
        if(this.checked) { // check select status
            $('.invite').each(function() { //loop through each checkbox
                this.checked = true;  //select all checkboxes with class "controls"              
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


<script src="<c:url value="/resources/mytheme/bootstrap/js/bootstrap-table.js" />" ></script>