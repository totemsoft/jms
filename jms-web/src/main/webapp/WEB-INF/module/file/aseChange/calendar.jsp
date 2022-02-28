<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

{
"menuItems": [],

<jsp:include page="/WEB-INF/entity/calendar.jsp" />

"filters": [
{"name": "fileId", "label": "<bean:message key='label.file' />", "valuesUrl": "findFilter.do?dispatch=findFileNo"}
],

"actions": {
"cellSelection": true,
"findAction": "file/calendarAseChange.do?dispatch=find", "findScript" : "scripts/calendar.js",
"printAction": "file/calendarAseChange.do?dispatch=print"
}
}
</jsp:root>