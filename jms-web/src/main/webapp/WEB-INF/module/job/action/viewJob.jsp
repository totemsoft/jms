<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:logic="http://struts.apache.org/tags-logic"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <html:xhtml/>

    <tr>
        <td class="jms-label"><bean:message key="label.job" /></td>
        <td><html:text property="jobAction.job.jobId" readonly="true" styleClass="text" /></td>
        <td class="jms-label"><bean:message key="label.jobType" /></td>
        <td><html:text property="jobAction.job.jobType.name" readonly="true" styleClass="text" /></td>
    </tr>

</jsp:root>