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

    <table class="nonDataTable">
        <tr>
            <td>
                <table class="dataTable" id="uploadFileTable">
                    <caption>Add Attachment</caption>
                    <tr>
                        <th><bean:message key="label.attachment.name" /></th>
                        <th></th>
                    </tr>
                    <tr>
                        <th colspan="2">
                            <html:link href="javascript:addAttachment('uploadFileTable');" titleKey="link.add">
                                <html:img src="images/new.png" /><bean:message key="link.attachment.add" />
                            </html:link>
                        </th>
                    </tr>
                </table>
            </td>
        </tr>
    </table>

</jsp:root>