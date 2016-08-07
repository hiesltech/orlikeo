<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<c:url value="/account/password" var="passwordUrl" />
<c:url value="/account/edit" var="editAccountUrl" />




<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
			Profil zawodnika <small><i class="glyphicon glyphicon-info-sign"></i> Informacje</small>
		</h1>
	</div>
</div>


<div class="container" >
        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad" >
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> <c:if test="${ not empty user.name }" >${user.name}</c:if> <c:if test="${ not empty user.surname }" >${user.surname} </c:if></h3>
            </div>
            <div class="panel-body">
              <div class="row">
                <div class="col-md-3 col-lg-3 " align="center"> 
                <img alt="User Pic" src="http://www.line-stickers.com/wp-content/uploads/2015/04/sticker4153.main_-120x120.png" class="img-circle"></div>
                <div class=" col-md-9 col-lg-9 "> 
                  <table class="table table-user-information">
                    <tbody>
                      <tr>
						<td>Email:</td>
                        <td><a href="mailto:${user.email}">${user.email}</a></td>
                      </tr>
                      <tr>
						<td>Data urodzenia:</td>
                        <td><c:if test="${ not empty user.dateOfBirth }" ><fmt:formatDate value="${user.dateOfBirth}" type="both" pattern="dd.MM.yyyy" /></c:if></td>
                      </tr>
                      <tr>
						<td>Pozycja:</td>
                        <td><c:if test="${ not empty user.position }" >${user.position}</c:if></td>
                      </tr>
                      <tr>
						<td>Waga:</td>
                        <td><c:if test="${ not empty user.weight }" >${user.weight}</c:if></td>
                      </tr>
                      <tr>
						<td>Wzrost:</td>
                        <td><c:if test="${ not empty user.height }" >${user.height}</c:if></td>
                      </tr>
                      <tr>
						<td>Noga:</td>
                        <td><c:if test="${ not empty user.foot }" >${user.foot}</c:if></td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
			<div class="panel-footer text-right">
				<c:if test="${ self }">
					<a href="${passwordUrl}" class="btn btn-primary">Zmien hasło</a>
					<a href="${editAccountUrl}" class="btn btn-primary">Edytuj profil</a>
                    <sec:authorize access="hasRole('ROLE_USER')">
                    <button type="button" title="Usuń konto" class="btn btn-warning" data-toggle="modal" data-target="#removeAccountModal" ><i class="glyphicon glyphicon-remove"></i></button>
                    </sec:authorize>
				</c:if>
		<c:choose>
					<c:when test="${ contact == 'pendingRequester'}">Wysłano zaproszenie do użytkownika
                        <button type="button" title="Cofnij" class="btn btn-warning" data-toggle="modal" data-target="#cancelInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-stop"></i></button>
                        <button type="button" title="Zablokuj" class="btn btn-danger" data-toggle="modal" data-target="#blockInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ban-circle"></i></button>
					</c:when>
					<c:when test="${ contact == 'pendingReceiver'}">Otrzymałeś zaproszenie od użytkownika
                        <button type="button" title="Akceptuj" class="btn btn-success" data-toggle="modal" data-target="#acceptInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ok-sign"></i></button>
                        <button type="button" title="Odrzuć" class="btn btn-warning" data-toggle="modal" data-target="#declineInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-remove-sign"></i></button>
                        <button type="button" title="Zablokuj" class="btn btn-danger" data-toggle="modal" data-target="#blockInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ban-circle"></i></button>
					</c:when>
					<c:when test="${ contact == 'friends'}">Jesteście znajomymi
                        <button type="button" title="Usuń" class="btn btn-danger" data-toggle="modal" data-target="#removeFriendshipModal" data-whatever="${ email }"><i class="glyphicon glyphicon-minus-sign"></i></button>
                        <button type="button" title="Zablokuj" class="btn btn-danger" data-toggle="modal" data-target="#blockInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ban-circle"></i></button>
					</c:when>
					<c:when test="${ contact == 'decliner'}">Odrzuciłeś zaproszenie użytkownika
                        <button type="button" title="Zaproś" class="btn btn-success" data-toggle="modal" data-target="#inviteUserModal" data-whatever="${ email }"><i class="glyphicon glyphicon-plus-sign"></i></button>
                        <button type="button" title="Zablokuj" class="btn btn-danger" data-toggle="modal" data-target="#blockInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ban-circle"></i></button>
					</c:when>
					<c:when test="${ contact == 'declined'}">Użytkownik odrzucił Twoje zaproszenie
                        <button type="button" title="Zaproś" class="btn btn-success" data-toggle="modal" data-target="#inviteUserModal" data-whatever="${ email }"><i class="glyphicon glyphicon-plus-sign"></i></button>
                        <button type="button" title="Zablokuj" class="btn btn-danger" data-toggle="modal" data-target="#blockInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ban-circle"></i></button>
					</c:when>
					<c:when test="${ contact == 'blocker'}">Zablokowałeś użytkownika
                        <button type="button" title="Odblokuj" class="btn btn-success" data-toggle="modal" data-target="#unblockUser" data-whatever="${ email }"><i class="glyphicon glyphicon-refresh"></i></button>
					</c:when>
					<c:when test="${ contact == 'blocked'}">
						Zostałeś zablokowany przez użytkownika
					</c:when>
					<c:when test="${ contact == 'without'}">
                        <button type="button" title="Zaproś" class="btn btn-success" data-toggle="modal" data-target="#inviteUserModal" data-whatever="${ email }"><i class="glyphicon glyphicon-plus-sign"></i></button>
                        <button type="button" title="Zablokuj" class="btn btn-danger" data-toggle="modal" data-target="#blockInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ban-circle"></i></button>
					</c:when> 
		</c:choose>
			</div>
          </div>
        </div>
</div>
<!-- TODO: usuwanie konta wraz z wywalaniem zewsząd + potwierdzenie -->
<%--
<button type="button" title="Zaproś" class="btn btn-success" data-toggle="modal" data-target="#inviteUserModal" data-whatever="${ user.email }"><i class="glyphicon glyphicon-plus-sign"></i></button>
<button type="button" title="Akceptuj" class="btn btn-success" data-toggle="modal" data-target="#acceptInvitationModal" data-whatever="${ user.email }"><i class="glyphicon glyphicon-ok-sign"></i></button>
<button type="button" title="Odrzuć" class="btn btn-warning" data-toggle="modal" data-target="#declineInvitationModal" data-whatever="${ user.email }"><i class="glyphicon glyphicon-remove-sign"></i></button>
<button type="button" title="Cofnij" class="btn btn-warning" data-toggle="modal" data-target="#cancelInvitationModal" data-whatever="${ user.email }"><i class="glyphicon glyphicon-stop"></i></button>
<button type="button" title="Zablokuj" class="btn btn-danger" data-toggle="modal" data-target="#blockInvitationModal" data-whatever="${ user.email }"><i class="glyphicon glyphicon-ban-circle"></i></button>
<button type="button" title="Odblokuj" class="btn btn-success" data-toggle="modal" data-target="#unblockUser" data-whatever="${ user.email }"><i class="glyphicon glyphicon-refresh"></i></button>
<button type="button" title="Usuń" class="btn btn-danger" data-toggle="modal" data-target="#removeFriendshipModal" data-whatever="${ user.email }"><i class="glyphicon glyphicon-minus-sign"></i></button>
--%>
