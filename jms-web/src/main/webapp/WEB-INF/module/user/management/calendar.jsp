<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:perm="/WEB-INF/tld/argus-perm.tld"
    version="2.0">
<c:set var="bundle" value="user" />
{
"tabs": ["tab1"],

"menuItems": [],

<jsp:include page="/WEB-INF/entity/calendar.jsp" />

<perm:present module="/user" path="/calendarStaffSupervisor">
"filters": [
{"name": "user", "label": "<bean:message key='label.user.login' />", "valuesUrl": "findFilter.do?dispatch=userName", "hiddenValue": "${loggedUser.id}", "value": "${userName}", "forceSelection": true}
],
</perm:present>

"actions": {
"cellSelection": true,
"findAction": "user/calendarStaff.do?dispatch=find", "findScript": "scripts/calendar.js",
"newAction": "user/calendarStaff.do?dispatch=edit&amp;userId=${loggedUser.id}", "newScript": "", "newTitle": "<bean:message key='label.user.calendar.newLeave' bundle='${bundle}' />", "newWidth": 0.8
<perm:present module="/user" path="/calendarStaffSupervisor">
,"editAction": "user/calendarStaff.do?dispatch=edit&amp;day=", "editScript": "", "editTitle": "<bean:message key='label.user.calendar.editLeave' bundle='${bundle}' />", "editWidth": 0.8
</perm:present>
}
}
</jsp:root>