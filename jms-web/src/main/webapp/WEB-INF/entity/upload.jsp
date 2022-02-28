<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <table class="nonDataTable">
        <caption><bean:message key="label.upload.file" /></caption>
        <tr>
            <td class="jms-label"><bean:message key="label.upload.file.name" /></td>
            <td>
                <input type="file" id="uploadFile" name="uploadFile" size="100" style="direction:ltr;" />
            </td>
        </tr>
    </table>

</jsp:root>