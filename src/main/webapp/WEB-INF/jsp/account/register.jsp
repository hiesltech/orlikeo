<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:url value="/" var="register" />
<c:url value="/help" var="helpUrl" />
<c:url value="/register" var="createAccountUrl" />
<c:url value="/register/new_user" var="registerUrl" />
<c:url value="/regulation" var="regulation" />


<div class="container">
<div class="row" style="padding: 50px 0px">
	<div class="col-sm-6 col-md-4 col-md-offset-4 vcenter"><h1 class="text-center login-title">Zarejestruj nowe konto</h1>
		<div class="account-wall">
			<form:form action="${registerUrl}" class="form-signin" modelAttribute="registerUserBean" method="POST" >
				
				<div class="form-group">
					<form:label path="email"><spring:message code="web.register.email.label"/></form:label>
					<br/>
					<form:errors path="email" class="text-danger" />
					<spring:message code="web.register.email.placeholder" var="emailPlaceholder"/>
					<form:input path="email" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}" required="required"  placeholder="${emailPlaceholder}"  class="form-control" />
				</div>
				
				<div class="form-group">
					<form:label path="password"><spring:message code="web.register.password.label"/></form:label>
					<br/>
					<form:errors path="password" class="text-danger"/>
					<spring:message code="web.register.password.placeholder" var="passwordPlaceholder"/>
					<form:password path="password" required="required" placeholder="${passwordPlaceholder}" class="form-control" />
				</div>
			
				<div class="form-group">
					<label><spring:message code="web.register.repeated_password.label"/></label>
					<br/>
					<form:errors path="repeatedPassword" class="text-danger" />
					<spring:message code="web.register.repeated_password.placeholder" var="repeatedPasswordPlaceholder" />
					<form:password path="repeatedPassword" required="required" placeholder="${repeatedPasswordPlaceholder}" class="form-control"  />
				</div>
			
				<div class="form-group">
					<form:checkbox path="acceptRegulation"/>
					<label><spring:message code="web.register.regulation.accept"/> <a href="${regulation}"><spring:message code="web.register.regulation.link_label"/></a></label>
					<br/>
					<form:errors path="acceptRegulation" class="text-danger"/>
				</div>
			
				<button class="btn btn-lg btn-success btn-block" type="submit">
					<spring:message code="web.register.submit_button.label"/> 
				</button>
			</form:form>
		</div>
	</div>
</div>
</div>