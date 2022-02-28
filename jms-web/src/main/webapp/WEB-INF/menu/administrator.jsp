<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:perm="/WEB-INF/tld/argus-perm.tld"
    version="2.0">

    <html:xhtml/>

    <div id="leftNav">
        <div id="folder_list">
            <div id="mainMenu" class="wrap yuimenu">
                <ul class="first-of-type">
                    <li id="menu.fca" class="yuimenuitem first-of-type fca"><a class="yuimenuitemlabel" href="${findFca}" onclick="return false;" title="FCA"><span>${nbsp}</span>FCA</a></li>
                    <li id="menu.supplier" class="yuimenuitem supplier"><a class="yuimenuitemlabel" href="${findSupplier}" onclick="return false;" title="Suppliers"><span>${nbsp}</span>Suppliers</a></li>
                    <li id="menu.setup" class="yuimenuitem setup"><a class="yuimenuitemlabel" href="${getSetup}" onclick="return false;" title="Setup"><span>${nbsp}</span>Setup</a></li>
                    <li id="menu.user-admin" class="yuimenuitem user-admin"><a class="yuimenuitemlabel" href="${findUser}" onclick="return false;" title="User Admin"><span>${nbsp}</span>User Admin</a></li>
                    <perm:present module="/user" path="/workflowRegister">
                        <li id="menu.workflowRegister" class="yuimenuitem workflowRegister"><a class="yuimenuitemlabel" href="${workflowRegister}" onclick="return false;" title="Workflow Register"><span>${nbsp}</span>Workflow Register</a></li>
                    </perm:present>
                </ul>
                
            </div>
            <div class="wrap">
                <div id="calContainer"><div id="cal">${nbsp}</div></div>
            </div>
        </div>
    </div>

</jsp:root>