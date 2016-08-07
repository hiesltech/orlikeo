<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html lang="en">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Narzędzie ułatwiające piłkarzykowanie">
    <meta name="author" content="Mariusz Zych">
    <title>Orlikeo <tiles:getAsString name="title" /></title>

    <!-- css -->
    <link  href="<c:url value="https://cdn.datatables.net/1.10.9/css/jquery.dataTables.min.css" />" rel="stylesheet">
    <tiles:importAttribute name="styles"/>
    <c:forEach var="style" items="${styles}">
        <tiles:insertAttribute value="${style}" />
    </c:forEach>

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
</head>
<body>
    <div id="wrapper">
		<tiles:insertAttribute name="navbar" />
		<div id="page-wrapper" >
			 <div class="container-fluid" >
			 	<tiles:insertAttribute name="eventsMainWindow" />
				<tiles:insertAttribute name="content" />
				<tiles:insertAttribute name="modal" />
	    	 </div>
        </div> <!-- page-wrapper -->
	</div> <!-- wrapper -->
</body>
</html>



