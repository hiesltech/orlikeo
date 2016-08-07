<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:url var="url" value="/pane/accept" />

<h1>Wydarzenia</h1>
<hr>
<h3>Do akceptacji</h3>
<table id="forAcceptationTable" >
    <thead style="background-color:#999999">
		<tr>
			<th>Organizator</th>
			<th>Data</th>
			<th>Start</th>
			<th>Koniec</th>
			<th>Zapełnienie</th>
			<th></th>
		</tr>
	</thead>
	<tbody id="forAcceptation" style="background-color: red"></tbody>
</table>

<h3>Zagrożone</h3>
<table id="treathenedTable" style="background-color:white">
	<thead>
		<tr>
			<th>Organizator</th>
			<th>Data</th>
			<th>Start</th>
			<th>Koniec</th>
			<th>Zapełnienie</th>
			<th></th>
		</tr>
	</thead>
	<tbody id="inDangerous"></tbody>
</table>

<h3>Przyjęte</h3>
<table id="acceptedTable" style="background-color:white">
	<thead>
		<tr>
			<th>Organizator</th>
			<th>Data</th>
			<th>Start</th>
			<th>Koniec</th>
			<th>Zapełnienie</th>
			<th></th>
		</tr>
	</thead>
	<tbody id="accepted"></tbody>
</table>

<h3>W budowie</h3>
<table id="underConstructionTable" style="background-color:white">
	<thead>
		<tr>
			<th>Organizator</th>
			<th>Data</th>
			<th>Start</th>
			<th>Koniec</th>
			<th>Zapełnienie</th>
			<th></th>
		</tr>
	</thead>
	<tbody id="underConstruction"></tbody>
</table>


