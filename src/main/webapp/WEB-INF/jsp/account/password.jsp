<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>



<c:url value="/account/password" var="changePswdUrl" />


<div class="container">
<div class="row" style="padding: 50px 0px">
	<div class="col-sm-6 col-md-4 col-md-offset-4 vcenter"><h1 class="text-center login-title">Zmień hasło</h1>
		<div class="account-wall">
                <!-- <img class="profile-img" src="http://cdn.content.easports.com/fifa/fltOnlineAssets/2013/fut/items/images/players/web/158023.png"
                    alt=""> -->
                <form:form modelAttribute="changePasswordForm" method="POST" class="form-signin" action="${changePswdUrl}">    
        
                <div class="form-group">
					<form:label path="oldPassword"><spring:message code="web.change.password.label"/></form:label>
					<br/>	
					<form:errors path="oldPassword" cssClass="error" />
					<spring:message code="web.change.password.placeholder" var="oldPasswordPlaceholder"/>
					<form:input  path="oldPassword" type="password" class="form-control"  placeholder="${oldPasswordPlaceholder}" required="required" />
				</div>
        
                <div class="form-group">
					<form:label path="newPassword"><spring:message code="web.change.newPassword.label"/></form:label>
					<br/>
					<form:errors path="newPassword" cssClass="error" />
					<spring:message code="web.change.newPassword.placeholder" var="newPasswordPlaceholder"/>
					<form:input path="newPassword" type="password" class="form-control"  placeholder="${newPasswordPlaceholder}" required="required" />
				</div>				
        
                <div class="form-group">
					<form:label path="newPasswordConfirm"><spring:message code="web.change.newPasswordConfirm.label"/></form:label>
					<br/>
					<form:errors path="newPasswordConfirm" cssClass="error" />
					<spring:message code="web.change.newPasswordConfirm.placeholder" var="newPasswordConfirmPlaceholder"/>
					<form:input path="newPasswordConfirm" type="password" class="form-control"  placeholder="${newPasswordConfirmPlaceholder}" required="required" />
				</div>
        
                <button class="btn btn-lg btn-primary btn-block" type="submit">Zmień hasło</button>
                </form:form>
            </div>
        </div>
    </div>
</div>


