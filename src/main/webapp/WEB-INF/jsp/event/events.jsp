<%@ page contentType="application/json" %>
<%-- <%= getEvents(request) %>
<%@ page import="com.dhtmlx.planner.*,umk.zychu.inzynierka.util.*" %>
<%!
    String getEvents(HttpServletRequest request) throws Exception {
    EventsManager evs = new EventsManager(request);
    return evs.run();
  } --%>
%>