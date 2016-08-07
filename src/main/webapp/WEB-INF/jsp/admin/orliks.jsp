<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<c:url var="orlikEditUrl" value="/admin/edit" />
<c:set var="orlikRemoveUrl" value="/admin/remove" />


<c:set var="plus" value="<i class=\"glyphicon glyphicon-plus\"></i>" />
<c:set var="minus" value="<i class=\"glyphicon glyphicon-minus\"></i>" />

<h1>Orliki <a href="${orlikEditUrl}" style="float:right" ><button title="Nowy orlik" class="btn btn-info">Nowy <i class="glyphicon glyphicon-plus-sign"></i> </button> </a></h1>
<hr>

<table id="orliksTable" >
    <thead style="background-color:#999999">
		<tr>
			<th>Miasto</th>
			<th>Adres</th>
			<th>Światło</th>
			<th>Woda</th>
			<th>Prysznic</th>
			<th>Obuwie</th>
			<th>Animator</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${orliks}" var="orlik" varStatus="i">
		<c:set var="id" value="${ orlik.id}" />
		<c:set var="city"  value="${ orlik.city}"/>
		<c:set var="address"  value="${ orlik.address }"/>
		<c:set var="light"  value="${ orlik.lights }"/>
		<c:set var="water"  value="${ orlik.water }"/>
		<c:set var="shower"  value="${ orlik.shower }"/>
		<c:set var="shoes"  value="${ orlik.shoes }"/>
		<c:set var="animator"  value="${ orlik.animator.email }"/>
		<c:if test="${empty animator}">
			<c:set var="animator" value="brak" />
		</c:if>
		<tr>
			<td>${city}</td>
			<td>${address}</td>
			<td><c:choose>
				<c:when test="${light}"> ${plus} </c:when>
				<c:otherwise> ${minus} </c:otherwise>
			</c:choose></td>
			<td><c:choose>
				<c:when test="${water}"> ${plus} </c:when>
				<c:otherwise> ${minus} </c:otherwise>
			</c:choose></td>
			<td><c:choose>
				<c:when test="${shower}"> ${plus} </c:when>
				<c:otherwise> ${minus} </c:otherwise>
			</c:choose></td>
			<td>${ shoes }</td>
			<td>${ animator }</td>
			<td><div style="display: inline-block; margin: auto">
				<a href="${orlikEditUrl}?orlikId=${id}" ><button title="Edytuj" class="btn btn-info"> <i class="glyphicon glyphicon-pencil"></i> </button> </a>
				<button type="button" title="Usuń" class="btn btn-danger"
						data-toggle="modal"
						data-target="#removeOrlikModal"
						data-ev="<c:out value='${id}' />"
						data-href="<c:url value='${ orlikRemoveUrl }' />">
					<i class="glyphicon glyphicon-remove"></i></button>
			</div></td>
		</tr>
	</c:forEach>
	</tbody>
</table>



