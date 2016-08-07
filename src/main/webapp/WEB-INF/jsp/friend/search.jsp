<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<c:url value="/friends/search" var="searchFriends" />
<c:url value="/friends/userDetail" var="userDetails" />

<c:url value="/friends/friendRequest" var="inviteFriend" />

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header"> Znajomi </h1>
	</div>
</div>


<div class="container">
  <div class="row">
    <div class="col-sm-6 col-md-4 col-md-offset-4">
        <div class="account-wall">
        <h1 class="text-center login-title"><i class="glyphicon glyphicon-user"></i> Wyszukaj Znajomego</h1><hr>
          <form:form method="POST" data-toggle="validator" role="form" class="form-signin" action="${ searchFriends }">
            <div class="form-group" style="text-align: center">
              <input name="email" type="email" class="form-control" id="inputEmail" placeholder="Email" data-error="Nieprawidłowy adres email" required>
            </div>
              <c:if test="${notFound}" ><div class="form-group" style="text-align: center; text-align: center; color: red">Nie znaleziono użytkownika.</div></c:if>
            <div class="form-group" style="text-align: center">
              <button type="submit" class="btn-lg btn-primary">Szukaj</button>
            </div>
          </form:form>
      </div>
    </div>
  </div>
</div>



<c:if test="${ not empty userEmail }">
		<legend>Znaleziono</legend>
		<table id="friendFoundedTable" >
			<thead>
				<tr>
					<th>Użytkownik</th>
					<th>Akcja</th>
				</tr>
			</thead>
			<tbody>
               	<tr>
					<td>${ userEmail }</td>
					<td>
				        <a href="${ userDetails }/${userEmail}" class="btn btn-info" role="button">Zobacz profil</a>
					</td>
			    </tr>
			</tbody>
		</table>
</c:if>