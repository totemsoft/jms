<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
{
"tabs": ["tab1"],

"columnDefs": [
{"key":"id","className":"width0","hidden":true},
{"key":"fireCallNo","label":"Fire Call No"},
{"key":"injuryNo","label":"Injury No"},
{"key":"region","label":"Region"},
{"key":"incidentDate","label":"Incident Date"},
{"key":"name","label":"Name"},
{"key":"dateOfBirth","label":"Date Of Birth"},
{"key":"address","label":"Address"}
],

"rows":[<c:forEach items="${entities}" var="e" varStatus="s">{
"id":"${e.id}",
"fireCallNo":"${e.fireCallNo}",
"injuryNo":"${e.injuryNo}",
"region":"${e.region.name}",
"incidentDate":"<fmt:formatDate value='${e.incidentDate}' pattern='${datePattern}' />",
"name":"${e.contact.firstName} ${e.contact.surname}",
"dateOfBirth":"<fmt:formatDate value='${e.contact.dateOfBirth}' pattern='${datePattern}' />",
"address":"${e.address.fullAddressJson}"
}<c:if test="${not s.last}">,</c:if></c:forEach>],

"filters": [
{"id": "f_date", "name": "date", "label": "<bean:message key="label.date" />"},
{"name": "fcaId", "label": "<bean:message key='label.fca' />", "valuesUrl": "findFilter.do?dispatch=findFcaNo", "forceSelection": true},
{"name": "fileId", "label": "<bean:message key='label.file' />", "valuesUrl": "findFilter.do?dispatch=findFileNo", "forceSelection": true}
],

"actions": {
"findAction": "file/uaaIncident.do?dispatch=find",
"viewAction": "file/uaaIncident.do?dispatch=view",
"exportAction": "file/uaaIncident.do?dispatch=export",
"importAction": "file/uaaIncident.do?dispatch=importData", "importWidth": 450, "importHeight": 150
}
}
</jsp:root>