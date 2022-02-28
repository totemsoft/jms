<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:logic="http://struts.apache.org/tags-logic"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:argus-html="/WEB-INF/tld/argus-html.tld"
    xmlns:perm="/WEB-INF/tld/argus-perm.tld"
    version="2.0">

    <html:xhtml/>

    <div id="pagetools" class="yuimenubar yuimenubarnav">
        <div class="bd">
            <ul class="first-of-type">
                <li class="yuimenubaritem first-of-type diary">
                    <a id="editDiary" class="yuimenubaritemlabel" href="#" title="New Diary"><span>${nbsp}</span>Diary</a>
                </li>
                <li class="yuimenubaritem call">
                    <a id="editCall" class="yuimenubaritemlabel" href="#" title="New Call"><span>${nbsp}</span>Call</a>
                </li>
                <li class="yuimenubaritem letter">
                    <a id="editLetter" class="yuimenubaritemlabel" href="#" title="New Letter"><span>${nbsp}</span>Letter</a>
                </li>
                <li class="yuimenubaritem mail">
                    <a id="editEmail" class="yuimenubaritemlabel" href="#" title="New Email"><span>${nbsp}</span>Email</a>
                </li>
                <li class="yuimenubaritem sms">
                    <a id="editSMS" class="yuimenubaritemlabel" href="#" title="New SMS"><span>${nbsp}</span>SMS</a>
                </li>
                <li class="yuimenubaritem rfi">
                    <a id="editRFI" class="yuimenubaritemlabel" href="#" title="New RFI"><span>${nbsp}</span>RFI</a>
                </li>
                <li class="yuimenubaritem file">
                    <a id="editFileStatus" class="yuimenubaritemlabel" href="#" title="Change File Status"><span>${nbsp}</span>Change Status</a>
                </li>
                <li class="yuimenubaritem job">
                    <a id="editJob" class="yuimenubaritemlabel" href="#" title="New Job"><span>${nbsp}</span>New Job</a>
                </li>
                <perm:present module="/file" path="/fileAudit">
                    <c:if test="${fileAudit.active}">
                        <li class="yuimenubaritem fileAudit">
                            <a id="fileAudit" class="yuimenubaritemlabel" href="#" title="Approve Changes"><span>${nbsp}</span>Approve</a>
                        </li>
                    </c:if>
                </perm:present>
            </ul>
        </div>
    </div>

</jsp:root>