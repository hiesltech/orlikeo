<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript">
    $(document).ready( function () {
        $('#friendFoundedTable').dataTable(
                {
                    "language": {
                        "url": "https://cdn.datatables.net/plug-ins/1.10.9/i18n/Polish.json"
                    },
                    "bFilter": false
                });
    } );
</script>