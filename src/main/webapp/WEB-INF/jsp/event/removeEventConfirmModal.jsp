<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!-- Remove friendship pop up confirm window-->
<div class="modal fade" id="removeEventModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" title="Zamknij" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="exampleModalLabel" style="text-align:center" ></h4>
      </div>
      <div class="modal-body">
        <form:form id="command" action="" method="POST">
        <input type="hidden" class="form-control" name="eventToRemoveId" id="eventToRemoveId" />
        <div class="modal-footer">
     	<button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
        <button type="submit" class="btn btn-danger">Usuń</button>
      	</div>
        </form:form>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
$('#removeEventModal').on('show.bs.modal', function (event) {
	  var button = $(event.relatedTarget); // Button that triggered the modal
	  var eventId = button.data('ev'); // Extract info from data-* attributes
	  var href = button.data('href'); // Extract info from data-* attributes
	  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
	  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
	  var modal = $(this);
	  modal.find('.modal-title').text('Usunąć wydarzenie ?');
	  modal.find('.modal-body #eventToRemoveId').val(eventId);
	  document.getElementById('command').action = href;
}); 
</script>