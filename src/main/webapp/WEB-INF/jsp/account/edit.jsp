<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>



<c:url value="/account/edit" var="editAccountUrl" />

<!-- TODO: potwierdzenie dokonanych zmian -->
<div class="container">
<div class="row" style="padding: 50px 0px">
	<div class="col-sm-6 col-md-4 col-md-offset-4 vcenter"><h1 class="text-center login-title">Aktualizacja profilu</h1>
		<div class="account-wall">
                <!-- <img class="profile-img" src="http://cdn.content.easports.com/fifa/fltOnlineAssets/2013/fut/items/images/players/web/158023.png"
                    alt=""> -->
                <form:form modelAttribute="editAccountForm" method="POST" class="form-signin" action="${editAccountUrl}">    
	
				<!-- name -->
                <div class="form-group">
					<form:label path="name"><spring:message code="web.account.edit.name.label"/></form:label>
					<br/>	
					<form:errors path="name" cssClass="error" />
					<spring:message code="web.account.edit.name.placeholder" var="namePlaceholder"/>
					<form:input  path="name" pattern="[a-zęóąśłżźń A-ZŚŁĆŹŻ]{2,45}" type="text" class="form-control"  placeholder="${ namePlaceholder }"  />
				</div>

				<!-- surname -->
                <div class="form-group">
					<form:label path="surname"><spring:message code="web.account.edit.surname.label"/></form:label>
					<br/>	
					<spring:message code="web.account.edit.surname.placeholder" var="surnamePlaceholder"/>
					<form:input  path="surname" pattern="[a-zęóąśłżźń A-ZŚŁĆŹŻ]{2,45}" type="text" class="form-control"  placeholder="${ surnamePlaceholder }" />
				</div>
        		
        		<!-- dateOfBirth -->
                <div class="form-group">
					<form:label path="dateOfBirth"><spring:message code="web.account.edit.birthdate.label"/></form:label>
					<br/>	
					<form:errors path="dateOfBirth" cssClass="error" />
					<spring:message code="web.account.edit.birthdate.placeholder" var="birthdatePlaceholder"/>
					<form:input  path="dateOfBirth"  pattern="((0[1-9])|(1[0-9])|(2[0-9])|(3[0-1]))[.]((0[1-9])|(1[0-2]))[.](\d{4})" required="required" type="text" class="form-control"  placeholder="${ birthdatePlaceholder }"/>
				</div>
        		
        		<!-- position -->
                <div class="form-group">
					<form:label path="position"><spring:message code="web.account.edit.position.label"/></form:label>
					<br/>	
					<form:errors path="position" cssClass="error" />
					<spring:message code="web.account.edit.position.placeholder" var="positionPlaceholder"/>
					<form:input  path="position" pattern="[a-zęóąśłżźń A-ZĘÓŚŁĆŹŻ0-9]{2,30}" type="text" class="form-control"  placeholder="${ positionPlaceholder }"/>
				</div>
        		
        		<!-- weight -->
                <div class="form-group">
					<form:label path="weight"><spring:message code="web.account.edit.weight.label"/></form:label>
					<br/>	
					<form:errors path="weight" cssClass="error" />
					<spring:message code="web.account.edit.weight.placeholder" var="weightPlaceholder"/>
					<form:input  path="weight" pattern="[0-9]{1,3}" type="text" class="form-control"  placeholder="${ weightPlaceholder }"/>
				</div>
				
				
				<!-- height -->
                <div class="form-group">
					<form:label path="height"><spring:message code="web.account.edit.height.label"/></form:label>
					<br/>	
					<form:errors path="height" cssClass="error" />
					<spring:message code="web.account.edit.height.placeholder" var="heightPlaceholder"/>
					<form:input  path="height" pattern="[0-9]{1,3}" type="text" class="form-control"  placeholder="${ heightPlaceholder }"/>
				</div>
				
        		<!--  foot -->
                <div class="form-group">
					<form:label path="foot"><spring:message code="web.account.edit.foot.label"/></form:label>
					<br/>	
					<form:errors path="foot" cssClass="error" />
					<spring:message code="web.account.edit.foot.placeholder" var="footPlaceholder"/>
					<form:input  path="foot" pattern="[a-zęóąśłżźń A-ZĘÓŚŁĆŹŻ0-9]{1,20}" type="text" class="form-control" placeholder="${ footPlaceholder }" />
				</div>
        
                <button class="btn btn-lg btn-primary btn-block" type="submit">Zapisz zmiany</button>
                </form:form>
            </div>
        </div>
    </div>
</div>


