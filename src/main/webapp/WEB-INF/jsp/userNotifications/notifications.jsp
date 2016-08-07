<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script type="text/javascript">
	$(document).ready( function () {
		$('#userNotificationsTable').dataTable(
				{
					"language": {
						"url": "//cdn.datatables.net/plug-ins/1.10.9/i18n/Polish.json"
					},
					"aaSorting": []
				});
	} );
</script>


<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
			Powiadomienia <small><i class="glyphicon glyphicon-th-list"></i> Wszystkie</small>
		</h1>
	</div>
</div>

<table id="userNotificationsTable" style="background-color:white">
    <thead style="background-color:#999999">
        <tr>
            <th data-field="title" data-align="left" data-sortable="true">Tytuł</th>
            <th data-field="description" data-align="center" data-sortable="true">Opis</th>
            <th data-align="center">Data</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${notifications}" var="notify" varStatus="i">
		<c:set var="title"  value="${ notify.subject }"/>
		<c:set var="description"  value="${notify.description }"/>
		<c:set var="notifydate"  value="${notify.notificationDate}"/>
    	<tr <c:if test="${(i.index mod 2) == 1}">
			style="background-color:#A9A9A9;"
			</c:if>>
    		<td>${title}</td>
    		<td>${description}</td>
    		<td><fmt:formatDate value="${notifydate}" type="both" pattern="HH:mm dd.MM.yyy" /></td>
    	</tr>  
   </c:forEach>     	
    </tbody>
</table>
