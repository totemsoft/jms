<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

"columnDefs": [
{"key": "c0", "label": "<bean:message key='label.calendar.sunday' />",    "resizeable": true, "width": 120},{"key": "c0day", "hidden": true},
{"key": "c1", "label": "<bean:message key='label.calendar.monday' />",    "resizeable": true, "width": 120},{"key": "c1day", "hidden": true},
{"key": "c2", "label": "<bean:message key='label.calendar.tuesday' />",   "resizeable": true, "width": 120},{"key": "c2day", "hidden": true},
{"key": "c3", "label": "<bean:message key='label.calendar.wednesday' />", "resizeable": true, "width": 120},{"key": "c3day", "hidden": true},
{"key": "c4", "label": "<bean:message key='label.calendar.thursday' />",  "resizeable": true, "width": 120},{"key": "c4day", "hidden": true},
{"key": "c5", "label": "<bean:message key='label.calendar.friday' />",    "resizeable": true, "width": 120},{"key": "c5day", "hidden": true},
{"key": "c6", "label": "<bean:message key='label.calendar.saturday' />",  "resizeable": true, "width": 120},{"key": "c6day", "hidden": true}
],

"fields": [
{"key": "c0"},{"key": "c0day"},
{"key": "c1"},{"key": "c1day"},
{"key": "c2"},{"key": "c2day"},
{"key": "c3"},{"key": "c3day"},
{"key": "c4"},{"key": "c4day"},
{"key": "c5"},{"key": "c5day"},
{"key": "c6"},{"key": "c6day"}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"c0": "&lt;div class=&apos;calDate&apos;&gt;${entity[0].day}&lt;/div&gt;&lt;div class=&apos;calItem&apos;&gt;${entity[0].innerHtml}&lt;/div&gt;", "c0day": "${entity[0].day}",
 "c1": "&lt;div class=&apos;calDate&apos;&gt;${entity[1].day}&lt;/div&gt;&lt;div class=&apos;calItem&apos;&gt;${entity[1].innerHtml}&lt;/div&gt;", "c1day": "${entity[1].day}",
 "c2": "&lt;div class=&apos;calDate&apos;&gt;${entity[2].day}&lt;/div&gt;&lt;div class=&apos;calItem&apos;&gt;${entity[2].innerHtml}&lt;/div&gt;", "c2day": "${entity[2].day}",
 "c3": "&lt;div class=&apos;calDate&apos;&gt;${entity[3].day}&lt;/div&gt;&lt;div class=&apos;calItem&apos;&gt;${entity[3].innerHtml}&lt;/div&gt;", "c3day": "${entity[3].day}",
 "c4": "&lt;div class=&apos;calDate&apos;&gt;${entity[4].day}&lt;/div&gt;&lt;div class=&apos;calItem&apos;&gt;${entity[4].innerHtml}&lt;/div&gt;", "c4day": "${entity[4].day}",
 "c5": "&lt;div class=&apos;calDate&apos;&gt;${entity[5].day}&lt;/div&gt;&lt;div class=&apos;calItem&apos;&gt;${entity[5].innerHtml}&lt;/div&gt;", "c5day": "${entity[5].day}",
 "c6": "&lt;div class=&apos;calDate&apos;&gt;${entity[6].day}&lt;/div&gt;&lt;div class=&apos;calItem&apos;&gt;${entity[6].innerHtml}&lt;/div&gt;", "c6day": "${entity[6].day}"}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

</jsp:root>