<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

{
"tabs": ["tab1"],

"menuItems": [],

<jsp:include page="/WEB-INF/entity/calendar.jsp" />

"filters": [
{"name": "fileId", "label": "<bean:message key='label.file' />", "valuesUrl": "findFilter.do?dispatch=findFileNo"},
{"name": "fcaId", "label": "<bean:message key='label.fca' />", "valuesUrl": "findFilter.do?dispatch=findFcaNo"}
],

"actions": {
"cellSelection": true,
"findAction": "job/calendarJobAction.do?dispatch=find", "findScript" : "scripts/calendar.js"
}
}
</jsp:root>