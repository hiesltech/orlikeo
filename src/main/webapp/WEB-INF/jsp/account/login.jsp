<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<c:url value="/help" var="helpUrl" />
<c:url value="/register" var="createAccountUrl" />

 

<div class="container">	
     <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <h1 class="text-center login-title">Zaloguj i korzystaj z możliwości jakie daje Orlikeo!</h1>
         <c:if test="${not empty error}">
			<div class="error text-center" class="text-danger">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg text-center" style="color:green">${msg}</div>
		</c:if>
            <div class="account-wall" style="padding:10px">
                <img class="profile-img" src="http://www.line-stickers.com/wp-content/uploads/2015/04/sticker4153.main_-120x120.png"
                    alt="">
                <form name='loginForm'
		  action="<c:url value='/j_spring_security_check' />" method='POST'>
                <div class="form-group">
					<div class="input-group" data-validate="email">
						<input type="text" class="form-control" name="email" id="email"  placeholder="E-mail" required autofocus>
						<span class="input-group-addon danger"><span class="glyphicon glyphicon-remove"></span></span>
					</div>
				</div>					
				<div class="form-group">
					<div class="input-group">
						<input type="password" class="form-control" name="password" id="password"  placeholder="Hasło" required>
						<span class="input-group-addon danger"><span class="glyphicon glyphicon-remove"></span></span>
					</div>
				</div>
                <button class="btn btn-lg btn-primary btn-block" type="submit" name="submit">Zaloguj</button>
                <div class="form-group">
					<div class="input-group">
                <input type="checkbox" value="remember-me" >  Zapamiętaj mnie
                </div>
                </div>
                <div class="form-group">
					<div class="input-group">
                <a href="${helpUrl}" class="pull-right need-help">Potrzebujesz pomocy? </a>
                </div>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                </form>
            </div>
            <a href="${createAccountUrl}" class="text-center new-account">Utwórz nowe konto</a>
        </div>
	</div>
</div>

<!-- TODO: REGULAMIN -->
