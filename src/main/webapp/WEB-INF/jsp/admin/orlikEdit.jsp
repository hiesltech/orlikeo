<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"  %>




<c:url value="/admin/edit" var="editOrlikUrl" />
<script type="text/javascript">
	window.saved = '${saved}';
</script>
<!-- TODO: potwierdzenie dokonanych zmian -->
<div class="container">
<div class="row" style="padding: 50px 0px">
	<div class="col-sm-6 col-md-4 col-md-offset-4 vcenter"><h1 class="text-center login-title">
		<c:choose>
			<c:when test="${creation}">Dodawanie</c:when>
			<c:otherwise>Aktualizacja</c:otherwise>
		</c:choose> orlika</h1>
		<div class="account-wall">
                <form:form modelAttribute="orlikForm" method="POST" class="form-signin" action="${editOrlikUrl}">
					<form:hidden path="id" ></form:hidden>
				<!-- city -->
                <div class="form-group">
					<form:label path="city">Miasto</form:label>
					<br/>	
					<form:errors path="city" cssClass="error" />
					<%--<spring:message code="web.account.edit.name.placeholder" var="namePlaceholder"/>--%>
					<form:input  path="city" pattern="[a-zęóąśłżźń A-ZŚŁĆŹŻ]{2,100}" required="required" type="text" class="form-control"  />
				</div>

				<!-- address -->
                <div class="form-group">
					<form:label path="address">Adres</form:label>
					<br/>	
					<%--<spring:message code="web.account.edit.surname.placeholder" var="surnamePlaceholder"/>--%>
					<form:input  path="address" pattern="[a-zęóąśłżźń A-ZŚŁĆŹŻ 0-9]{2,100}" required="required" type="text" class="form-control" />
				</div>
        		
        		<!-- lights -->
                <div class="form-group">
					<form:label path="lights">Światło</form:label>
					<br/>
					<form:select path="lights">
						<form:options items="${orlikForm.chooser}"/>
					</form:select>
				</div>

					<!-- water-->
					<div class="form-group">
						<form:label path="water">Woda</form:label>
						<br/>
						<form:errors path="water" cssClass="error" />
							<%--<spring:message code="web.account.edit.position.placeholder" var="positionPlaceholder"/>--%>
						<form:select path="water">
							<form:options items="${orlikForm.chooser}"/>
						</form:select>
					</div>

					<!-- shower-->
					<div class="form-group">
						<form:label path="shower">Prysznic</form:label>
						<br/>
						<form:errors path="shower" cssClass="error" />
							<%--<spring:message code="web.account.edit.position.placeholder" var="positionPlaceholder"/>--%>
						<form:select path="shower">
							<form:options items="${orlikForm.chooser}"/>
						</form:select>
					</div>
					<!-- shoes -->
					<div class="form-group">
						<form:label path="shoes">Obuwie</form:label>
						<br/>
							<%--<spring:message code="web.account.edit.surname.placeholder" var="surnamePlaceholder"/>--%>
						<form:input  path="shoes" pattern="[a-zęóąśłżźń A-ZŚŁĆŹŻ ,]{2,20}" type="text" class="form-control" />
					</div>
					<div class="form-group">
						<form:errors path="animatorEmail" class="text-danger" />
						<form:label path="animatorEmail">Email animatora</form:label>
						<br/>
						<form:input path="animatorEmail" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}" class="form-control" />
					</div>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Zapisz zmiany</button>
                </form:form>
            </div>
        </div>
    </div>
</div>



