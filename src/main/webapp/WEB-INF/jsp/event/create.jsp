<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:url value="/graphic" var="terminarzUrl" />
<c:if test="${ not empty eventId } ">
	<c:url value="/editGraphic" var="terminarzUrl" />
</c:if>

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
			Wybierz orlik <small><i
				class="glyphicon glyphicon-picture"></i></small>
		</h1>
	</div>
</div>

	<div class="row">
		<div class="col-lg-12">
		<div class="well well-lg">
			<div class="row">
				<div class="col-lg-12">
					<form:form  modelAttribute="choosenOrlikBean" action="${terminarzUrl}" method="POST" >
						<div class="form-group">
							<form:errors path="Id" cssClass="error" />
							<spring:message code="web.register.email.placeholder" var="Placeholder"/>
							<form:select path="Id">
							    <form:options items="${orliks}" placeholder="${Placeholder}"/>
							</form:select>
						</div>
						<c:if test="${not empty eventId}">
							<form:hidden path="eventId" value="${ eventId }"/>
						</c:if>
						<form:button type="submit" class="btn btn-primary pull-right"
							id="btnContactUs">Dalej</form:button>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>