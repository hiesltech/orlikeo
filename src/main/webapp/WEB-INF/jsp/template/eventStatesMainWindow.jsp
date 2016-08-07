<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
			<c:set var="cathegory" value="" />
			<c:choose>
				<c:when test="${page == 'all'}"><c:set var="cathegory" value="Wszystkie" /></c:when>
				<c:when test="${page == 'organized'}"><c:set var="cathegory" value="Organizowane" /></c:when>
				<c:when test="${page == 'invitations'}"><c:set var="cathegory" value="Zaproszenia" /></c:when>
			</c:choose> Wydarzenia <small> <i class="glyphicon glyphicon-picture"></i> ${cathegory}</small>
		</h1>
	</div>
</div>

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
				<c:set var="headerSuffix" value="" />
				<c:choose>
					<c:when test="${page == 'all'}"><c:set var="headerSuffix" value=" w których uczestniczysz" /></c:when>
					<c:when test="${page == 'organized'}"><c:set var="headerSuffix" value=", które organizujesz" /></c:when>
					<c:when test="${page == 'invitations'}"><c:set var="headerSuffix" value=" na które Cię zaproszono" /></c:when>
			</c:choose>
					<i class="glyphicon glyphicon-info-sign"></i>Informacje o wydarzeniach <c:out value="${headerSuffix}" />
				</h3>
			</div>
			<div class="panel-body">
			<c:forEach items="${eventWindowsList}" var="windowBlock" varStatus="i">
					    <c:set var="willCome"  value="${ windowBlock.goingToCome }"/>
						<c:set var="limit"  value="${ windowBlock.playersLimit}"/>
						<c:set var="address"  value="${ windowBlock.address }"/>
						<c:set var="city"  value="${ windowBlock.city }"/>
						<c:set var="start"  value="${ windowBlock.startTime }"/>
						<c:set var="end"  value="${ windowBlock.endTime }"/>
						<c:set var="state"  value="${ windowBlock.stateId }"/>
						<c:set var="inSameState"  value="${ windowBlock.countedInSameState }"/>
						<c:set var="displayOrder"  value="${ windowBlock.displayOrder }"/>
						<c:set var="label"  value="${ windowBlock.label }"/>
						<c:set var="incoming"  value="${ windowBlock.incoming }"/>


				<div class="col-lg-2">
						<c:choose>
							<c:when test="${ displayOrder  == 5 }">
								<div class="panel panel-primary" id="primaryBlock">
							</c:when>
							<c:when test="${ displayOrder  == 4 }">
								<div class="panel panel-green" id="greenBlock">
							</c:when>
							<c:when test="${ displayOrder  == 3 }">
								<div class="panel panel-red" id="redBlock">
							</c:when>
							<c:when test="${ displayOrder  == 2 }">
								<div class="panel panel-yellow" id="yellowBlock">
							</c:when>
							<c:when test="${ displayOrder  == 1 }">
								<div class="panel panel-silver" id="silverBlock">
							</c:when>
							<c:when test="${ displayOrder  == 0 }">
								<div class="panel panel-silver-dark" id="darkBlock">
							</c:when>
						</c:choose>
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<c:choose>
										<c:when test="${ displayOrder  == 5 }">
											<i style="font-size: 2.5em;" class="glyphicon glyphicon-plane"></i>
										</c:when>
										<c:when test="${ displayOrder  == 4 }">
											<i style="font-size: 2.5em;" class="glyphicon glyphicon-thumbs-up"></i>
										</c:when>
										<c:when test="${ displayOrder == 3 }">
											<i style="font-size: 2.5em;" class="glyphicon glyphicon-warning-sign"></i>
										</c:when>
										<c:when test="${ displayOrder  == 2 }">
											<i style="font-size: 2.5em;" class="glyphicon glyphicon-send"></i>
										</c:when>
										<c:when test="${ displayOrder  == 1 }">
											<i style="font-size: 2.5em;" class="glyphicon glyphicon-thumbs-down"></i>
										</c:when>
										<c:when test="${ displayOrder  == 0 }">
											<i style="font-size: 2.5em;" class="glyphicon glyphicon-trash"></i>
										</c:when>
									</c:choose><span class="dupa">${willCome}/${limit}</span>
								</div>
								<div class="col-xs-12 text-right">
									<div class="large">${ label }</div>
									<div class="address">Adres:
                                    <c:if test="${not empty city}">
                                        ${ city}, ${ address }
                                    </c:if>
                                    </div>
									<c:choose >
                                        <c:when test="${ start != null }" >
										<div>Data rozpoczęcia: <fmt:formatDate value="${start}" type="both" pattern="dd.MM.yyyy" /></div>
										<div>Czas: <fmt:formatDate value="${start}" type="both" pattern="HH:mm" /> - <fmt:formatDate value="${end}" type="both" pattern="HH:mm" /></div>
                                        </c:when>
                                        <c:otherwise>
                                          <div>Data rozpoczęcia: --.--.----</div>
                                          <div>Czas: --:-- - --:--</div>
                                        </c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
					<c:choose>
							<c:when test="${inSameState != 0 }">
							<c:set var="link" value="/events/show" />
									 	<c:set var="link" value="${link}?state=${ displayOrder + 1 }" />
									<c:if test="${ not empty page }">
										<c:set var="link" value="${link}&page=${ page }" />
									</c:if>
								<a href="<c:url value='${ link }' />">
							</c:when>
							<c:otherwise>
							<a href="#">
						</c:otherwise>
					</c:choose>
							<div class="panel-footer">
								<span class="pull-left">WSZYSTKIE (${ inSameState })</span>
                                <span class="pull-right"><i class="glyphicon glyphicon-eye-open"></i></span>
								<div class="clearfix"></div>
							</div>
							</a>
						</div>
					</div>
			</c:forEach>

			</div>
		</div>
	</div>
</div>