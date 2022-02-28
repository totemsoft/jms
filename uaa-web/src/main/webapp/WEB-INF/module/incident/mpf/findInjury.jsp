<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
{"records":[<c:forEach items="${entities}" var="e" varStatus="s">{
"id":"${e.id}",
"fireCallNo":"${e.fireCallNo}",
"injuryNo":"${e.injuryNo}",
"region":"${e.region}",
"incidentDate":"<fmt:formatDate value='${e.incidentDate}' pattern='${datePattern}' />",
"name":"${e.firstName} ${e.surname}",
"dateOfBirth":"<fmt:formatDate value='${e.dateOfBirth}' pattern='${datePattern}' />",
"address":"${e.addrLine1} ${e.addrLine2} ${e.suburb} ${e.postcode}",
"injury":"${e.injuryType} ${e.injuryTo}",
"multipleInjury":"${e.multipleInjuryType} ${e.multipleInjuryTo}",
"alcoholLevel":"${e.alcoholLevel}",
"drugs":"${e.drugs}",
"treatedAt":"${e.treatedAt}",
"selected":{"id":"selected.${e.fireCallNo}.${e.injuryNo}","value":"${e.fireCallNo}.${e.injuryNo}","disabled":${not empty e.injuryId}}
}<c:if test="${not s.last}">,</c:if></c:forEach>]
}
</jsp:root>