<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

{
"menuItems": [],

"columnDefs": [
{"key": "id", "label": "", "formatter": "YAHOO.widget.DataTable.formatCheckbox", "hidden": true},
{"key": "login", "label": "Login", "sortable":true, "resizeable":true},
{"key": "fullName", "label": "Name", "sortable":true, "resizeable":true},
{"key": "email", "label": "EMail", "sortable":true, "resizeable":true},
{"key": "phone", "label": "Phone", "sortable":true, "resizeable":true},
{"key": "userType", "label": "User Type", "sortable":true, "resizeable":true},
{"key": "securityGroup", "label": "Security Group", "sortable":true, "resizeable":true}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": "${entity.id}",
"login": "${entity.login}",
"fullName": "${entity.contact.fullName}",
"email": "${entity.contact.email}",
"phone": "${entity.contact.phone}",
"userType": "${entity.userType.name}",
"securityGroup": "${entity.securityGroup.description}"}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [
{"name": "userTypes", "label": "User Type", "values": [<c:forEach items="${userTypes}" var="userType" varStatus="status">
"${userType.name}"<c:if test="${not status.last}">,</c:if>
</c:forEach>]},
{"name": "securityGroups", "label": "Security Group", "values": [<c:forEach items="${securityGroups}" var="securityGroup" varStatus="status">
"${securityGroup.name}"<c:if test="${not status.last}">,</c:if>
</c:forEach>]}
],

"actions": {
"findAction": "user/findUser.do?dispatch=find",
"viewAction": "user/viewUser.do?dispatch=view",
"newAction":  "user/editUser.do?dispatch=edit", "newWidth" : 670,
"editAction": "user/editUser.do?dispatch=edit", "editWidth": 670}
}
</jsp:root>