<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:logic="http://struts.apache.org/tags-logic"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:perm="/WEB-INF/tld/argus-perm.tld"
    version="2.0">

    <html:form action="/editUser">
        <html:hidden property="dispatch" value="save" />
        <html:hidden property="readOnly" value="false" />
        <html:hidden property="entity.userId" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <tr>
                <td class="jms-label"><bean:message key="label.user.login" /></td>
                <td><html:text property="entity.login" readonly="${not empty userForm.entity.userId}" styleClass="text" /></td>
                <perm:present role="Administrator">
                    <td class="jms-label"><bean:message key="label.user.status" /></td>
                    <td>
                        <html:hidden property="entity.active" styleId="active" />
                        <html:checkbox property="entity.active" onchange="this.form.active.value=this.checked;" />
                    </td>
                </perm:present>
                <perm:notPresent role="Administrator">
                    <td colspan="2">
                        <html:hidden property="entity.active" />
                    </td>
                </perm:notPresent>
            </tr>

            <perm:present role="Administrator">
                <tr>
                    <td class="jms-label"><bean:message key="label.user.userType" /></td>
                    <td>
                        <html:select property="entity.userType.userTypeId" styleId="userTypeId">
                            <option value=""><bean:message key="option.select" /></option>
                            <html:options collection="userTypes" property="id" labelProperty="name" />
                        </html:select>
                    </td>
                    <td class="jms-label"><bean:message key="label.user.securityGroup" /></td>
                    <td>
                        <html:select property="entity.securityGroup.securityGroupId">
                            <option value=""><bean:message key="option.select" /></option>
                            <html:options collection="securityGroups" property="id" labelProperty="description" />
                        </html:select>
                    </td>
                </tr>
            </perm:present>

            <perm:present role="Administrator">
                <tr>
                    <td class="jms-label"><bean:message key="label.supplier" /></td>
                    <td colspan="3">
                        <html:select property="supplierId">
                            <option value=""><bean:message key="option.select" /></option>
                            <html:options collection="suppliers" property="supplierId" labelProperty="name" />
                        </html:select>
                    </td>
                </tr>
            </perm:present>

            <perm:present role="Administrator">
                <tr>
                    <td colspan="4">
                        <table class="nonDataTable">
                            <html:errors property="userWorkGroups" />
                            <tr>
                                <th>Work Group Available</th>
                                <th>&#160;</th>
                                <th>Work Group Associated</th>
                            </tr>
                            <tr>
                                <td class="width50">
                                    <div id="availableWorkGroups" class="selectionPanel" style="height:200px;width:100%;">
                                        <c:forEach items="${availableWorkGroups}" var="item">
                                            <div id="availableWorkGroups${item.id}" onclick="YAHOO.jms.toggleClass(this,'selectionActive');">
                                                <input type="hidden" value="${item.id}" />${item.name}
                                            </div>
                                        </c:forEach>
                                    </div>
                                </td>
                                <td>
                                    <input id="add" type="button" value="&gt;&gt;" class="button" onclick="YAHOO.jms.moveSelected('userWorkGroups','availableWorkGroups','selectionActive');" />
                                    <br/><br/>
                                    <input id="remove" type="button" value="&lt;&lt;" class="button" onclick="YAHOO.jms.moveSelected('availableWorkGroups','userWorkGroups','selectionActive');" />
                                </td>
                                <td class="width50">
                                    <div id="userWorkGroups" class="selectionPanel" style="height:200px;overflow:auto;width:100%;">
                                        <c:forEach items="${userWorkGroups}" var="item">
                                            <div id="userWorkGroups${item.id}" onclick="YAHOO.jms.toggleClass(this,'selectionActive');">
                                                <input type="hidden" name="userWorkGroups" value="${item.id}" />${item.name}
                                            </div>
                                        </c:forEach>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </perm:present>

            <tr>
                <td colspan="4">
                    <jsp:include page="/WEB-INF/entity/contact/edit.jsp" />
                </td>
            </tr>

            <tr>
                <th><bean:message key="label.user.reissuePassword" /></th>
                <th><html:checkbox property="reissuePassword" onchange="YAHOO.jms.user.onchangeReissuePassword(this);" /></th>
                <th colspan="2">
                    <input type="password" name="entity.password" id="password" class="text hidden" />
                </th>
            </tr>
        </table>
    </html:form>

</jsp:root>