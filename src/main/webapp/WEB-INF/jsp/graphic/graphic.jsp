<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<c:choose>
	<c:when test="${not empty evId }"><c:url value="/events/reserve/${evId}" var="reserveUrl" /></c:when>
	<c:otherwise><c:url value="/events/reserve" var="reserveUrl" /></c:otherwise>
</c:choose>


<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
			Grafik <small><i class="glyphicon glyphicon-picture"></i> <fmt:formatDate value="${data}" type="both"
      pattern="dd-MM-yyyy-HH-mm" /> Orlik <c:out value="${orlikInfo.city}" /> ul. <c:out value="${orlikInfo.address}" /></small>
		</h1>
		<ul>
			<li><i class="glyphicon glyphicon-info-sign"></i> Oświetlenie: <c:out value="${orlikInfo.getLights() == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Bieżąca woda: <c:out value="${orlikInfo.getWater() == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Prysznic: <c:out value="${orlikInfo.getShower() == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Obowiązujące obuwie: <c:out value="${orlikInfo.getShoes()}"/></li>
			<li><i class="glyphicon glyphicon-user"></i> Animatorzy: <c:if test="${not empty animator}" ><c:out value="${animator.email}" /></c:if></li>
		</ul>
	</div>
</div>


<script type="text/javascript">
	$.getScript("<c:url value="/resources/mytheme/js/calendar.js" />",
			function() {

				var json = ${list};

				$.each(json, function(index, value) {
					value.start = new Date(value.start);
					value.end = new Date(value.end);
					if (value.available) {
						value.url = '${reserveUrl}/' + value.id;
					} else {
						value.url = '';
					}
				});

				// debug //
				// alert(JSON.stringify(json));  

				$('#calendar').fullCalendar({
					header : {
						left : 'prev,next today',
						center : 'title',
						right : 'month,agendaWeek,agendaDay'
					},
					editable : false,
					events : json
				});
			})
</script>



<div class="container">
    <hr>
	<div id="calendar"></div>
</div>









<!-- <script type="text/javascript">
$.getScript("<c:url value="/resources/mytheme/js/calendar.js" />",function(){
  
  var date = new Date();
  var d = date.getDate();
  var m = date.getMonth();
  var y = date.getFullYear();
  
  $('#calendar').fullCalendar({
    header: {
      left: 'prev,next today',
      center: 'title',
      right: 'month,agendaWeek,agendaDay'
    },
    editable: true,
    events: [
      {
        title: 'All Day Event',
        start: new Date(y, m, 1)
      },
      {
        title: 'Long Event',
        start: new Date(y, m, d-5),
        end: new Date(y, m, d-2)
      },
      {
        id: 999,
        title: 'Repeating Event',
        start: new Date(y, m, d-3, 16, 0),
        allDay: false
      },
      {
        id: 999,
        title: 'Repeating Event',
        start: new Date(y, m, d+4, 16, 0),
        allDay: false
      },
      {
          title: 'Zarezerwowane (1.5 h)',
          start: new Date("12, 4, 2014, 16:00"),
          end: new Date("12, 4, 2014, 17:30"),
          allDay: false
        },
      {
        title: 'Wolny termin (1.5 h)',
        start: new Date(y, m, d, 17, 30),
        end: new Date(y, m, d,  19, 0),
        allDay: false,
        url: '${reserveUrl}/33/${data.getTime()}/${data.getTime()+1}'
      },
      {
        title: 'Lunch',
        start: new Date(y, m, d, 12, 0),
        end: new Date(y, m, d, 14, 0),
        allDay: false
      },
      {
        title: 'Birthday Party',
        start: new Date(y, m, d+1, 19, 0),
        end: new Date(y, m, d+1, 22, 30),
        allDay: false
      },
      {
        title: 'Click for Google',
        start: new Date(y, m, 28),
        end: new Date(y, m, 29),
        url: 'http://google.com/'
      }
    ]
  });
})

</script>  -->