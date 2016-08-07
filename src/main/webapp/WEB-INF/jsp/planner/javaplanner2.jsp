<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
    <meta name="_csrf" content="${_csrf.token}" />
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}" />




    <!-- *************************************************** -->

    <link href="<c:url value="/resources/mytheme/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/mytheme/bootstrap/css/sb-admin.css" />"  rel="stylesheet">
    <!-- Bootstrap Core CSS -->
    <%--<link href="<c:url value="/resources/mytheme/bootstrap/css/bootstrap-table.css" />" rel="stylesheet">--%>


    <tiles:importAttribute name="extensibleStyles"/>
    <c:forEach var="x" items="${extensibleStyles}">
        <tiles:insertAttribute value="${x}" />
    </c:forEach>

    <!-- js -->
    <tiles:importAttribute name="scripts"/>
    <c:forEach var="script" items="${scripts}">
        <tiles:insertAttribute value="${script}" />
    </c:forEach>

    <tiles:importAttribute name="extensibleScripts"/>
    <c:forEach var="script" items="${extensibleScripts}">
        <tiles:insertAttribute value="${script}" />
    </c:forEach>
    <!-- *************************************************** -->
    <script
            src="<c:url value="/resources/codebase/ext/dhtmlxscheduler_editors.js" />"
            type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8">
        var tables = document.getElementsByTagName("meta");
        var orlik = ${ orlik };
    </script>
</head>
<body>
<div id="wrapper">
    <tiles:insertAttribute name="navbar" />

            <div class="planner" id="planner"><%=getPlanner(request)%></div>
            <%@ page
                    import="com.dhtmlx.planner.data.*,
				com.dhtmlx.planner.*,
				umk.zychu.inzynierka.controller.util.*,
				java.time.LocalDateTime,
				com.dhtmlx.planner.controls.DHXLocalization,
				java.util.Iterator,
				com.dhtmlx.planner.controls.*"%>
            <%!String getPlanner(HttpServletRequest request) throws Exception {
                DHXPlanner s = new DHXPlanner("./resources/codebase/", DHXSkin.TERRACE);
		/*s.setWidth(900);*/
		/*s.localizations.set(DHXLocalization.Polish);*/
                s.setInitialDate(LocalDateTime.now().getYear(), LocalDateTime.now()
                        .getMonthValue() - 1, LocalDateTime.now().getDayOfMonth());
                s.load(request.getRequestURL().toString()
                        .replace(request.getRequestURI(), request.getContextPath())
                        + "/api/rest/events", DHXDataFormat.JSON);
                s.data.dataprocessor.setURL(request.getRequestURL().toString()
                        .replace(request.getRequestURI(), request.getContextPath())
                        + "/api/rest/events");
                s.config.setDetailsOnCreate(true);
                DHXLightboxCheckbox check = new DHXLightboxCheckbox("allow", "REZERWACJE");
                check.setType("checkbox");
                s.lightbox.add(check);
                return s.render();
            }%>

</div> <!-- wrapper -->


</body>
</html>