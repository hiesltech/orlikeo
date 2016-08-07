<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Orlikeo - Rejestracja</title>

    <!-- Bootstrap core CSS -->

   	<link href="<c:url value="/resources/mytheme/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
   	<link  href="<c:url value="/resources/mytheme/css/style.css" />" rel="stylesheet">


   	<script src="<c:url value="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js" /> "></script>
   	<script type="text/javascript" src="<c:url value="/resources/mytheme/js/register.js" />"></script>
  	<script src="<c:url value="/resources/mytheme/bootstrap/js/bootstrap-table.js" />" ></script>
	<script src="<c:url value="/resources/mytheme/bootstrap/js/bootstrap.js" />" ></script>


    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

  </head>
  <body>

    <div class="container">
		<tiles:insertAttribute name="content" />
    </div> <!-- /container -->
  </body>
</html>








