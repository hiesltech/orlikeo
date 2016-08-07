<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- INVITE USER-->
<c:url value="/friends/friendRequest" var="inviteUserUrl" />
<!-- ACCEPT INVITATION-->
<c:url value="/friends/acceptUser" var="acceptInvitationUrl" />
<!-- REJECT INVITATION-->
<c:url value="/friends/reject" var="rejectInvitationUrl" />
<!-- REMOVE FROM FRIENDS-->
<c:url value="/friends/remove" var="removeFriendshipUrl" />
<!-- CANCEL INVITATION-->
<c:url value="/friends/cancel" var="cancelInvitationUrl" />
<!-- BLOCK USER-->
<c:url value="/friends/block" var="blockUserUrl" />
<!-- UNBLOCK USER-->
<c:url value="/friends/unblock" var="unblockUserUrl" />



<!-- Invite -->
<div class="modal fade" id="inviteUserModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" title="Zamknij" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel" style="text-align:center" ></h4>
            </div>
            <div class="modal-body">
                <form:form action="${inviteUserUrl}" method="POST">
                    <input type="hidden" class="form-control" name="email" id="email" ></input>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
                        <button type="submit" class="btn btn-success">Wyślij</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

<!-- Accept invitation -->
<div class="modal fade" id="acceptInvitationModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" title="Zamknij" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel" style="text-align:center" ></h4>
            </div>
            <div class="modal-body">
                <form:form action="${acceptInvitationUrl}" method="POST">
                    <input type="hidden" class="form-control" name="email" id="email" ></input>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
                        <button type="submit" class="btn btn-success">Akceptuj</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

<!-- Decline invitation -->
<div class="modal fade" id="declineInvitationModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" title="Zamknij" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel" style="text-align:center" ></h4>
            </div>
            <div class="modal-body">
                <form:form action="${rejectInvitationUrl}" method="POST">
                    <input type="hidden" class="form-control" name="email" id="email" ></input>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
                        <button type="submit" class="btn btn-warning">Odrzuć</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

<!-- Block invitation -->
<div class="modal fade" id="blockInvitationModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" title="Zamknij" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel" style="text-align:center" ></h4>
            </div>
            <div class="modal-body">
                <form:form action="${blockUserUrl}" method="POST">
                    <input type="hidden" class="form-control" name="email" id="email" ></input>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
                        <button type="submit" class="btn btn-danger">Zablokuj</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

<!-- Remove friendship -->
<div class="modal fade" id="removeFriendshipModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" title="Zamknij" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel" style="text-align:center" ></h4>
            </div>
            <div class="modal-body">
                <form:form action="${removeFriendshipUrl}" method="POST">
                    <input type="hidden" class="form-control" name="email" id="email" ></input>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
                        <button type="submit" class="btn btn-danger">Usuń</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

<!-- Cancel friendship invitation -->
<div class="modal fade" id="cancelInvitationModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" title="Zamknij" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel" style="text-align:center" ></h4>
            </div>
            <div class="modal-body">
                <form:form action="${cancelInvitationUrl}" method="POST">
                    <input type="hidden" class="form-control" name="email" id="email" ></input>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
                        <button type="submit" class="btn btn-warning">Cofnij</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

<!-- Unblock user -->
<div class="modal fade" id="unblockUser" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" title="Zamknij" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel" style="text-align:center" ></h4>
            </div>
            <div class="modal-body">
                <form:form action="${unblockUserUrl}" method="POST">
                    <input type="hidden" class="form-control" name="email" id="email" ></input>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
                        <button type="submit" class="btn btn-success">Odblokuj</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
    $('#inviteUserModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('whatever') // Extract info from data-* attributes
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
        var modal = $(this)
        modal.find('.modal-title').text('Wysłać zaproszenie do ' + recipient + ' ?')
        modal.find('.modal-body #email').val(recipient)
    });

    $('#declineInvitationModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('whatever') // Extract info from data-* attributes
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
        var modal = $(this)
        modal.find('.modal-title').text('Odrzucić zaproszenie od ' + recipient + ' ?')
        modal.find('.modal-body #email').val(recipient)
    });

    $('#acceptInvitationModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('whatever') // Extract info from data-* attributes
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
        var modal = $(this)
        modal.find('.modal-title').text('Zaakceptować zaproszenie od ' + recipient + ' ?')
        modal.find('.modal-body #email').val(recipient)
    });

    $('#declineInvitationModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('whatever') // Extract info from data-* attributes
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
        var modal = $(this)
        modal.find('.modal-title').text('Odrzucić zaproszenie od ' + recipient + ' ?')
        modal.find('.modal-body #email').val(recipient)
    });


    $('#blockInvitationModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('whatever') // Extract info from data-* attributes
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
        var modal = $(this)
        modal.find('.modal-title').text('Zablokować użytkownika ' + recipient + ' ?')
        modal.find('.modal-body #email').val(recipient)
    });



    $('#removeFriendshipModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('whatever') // Extract info from data-* attributes
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
        var modal = $(this)
        modal.find('.modal-title').text('Usunąć użytkownika ' + recipient + ' ze znajomych ?')
        modal.find('.modal-body #email').val(recipient)
    });


    $('#cancelInvitationModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('whatever') // Extract info from data-* attributes
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
        var modal = $(this)
        modal.find('.modal-title').text('Cofnąć zaproszenie dla użytkownika ' + recipient + ' ?')
        modal.find('.modal-body #email').val(recipient)
    });


    $('#unblockUser').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('whatever') // Extract info from data-* attributes
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
        var modal = $(this)
        modal.find('.modal-title').text('Odblokować użytkownika ' + recipient + ' ?')
        modal.find('.modal-body #email').val(recipient)
    });
</script>
