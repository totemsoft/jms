<?xml version="1.0" encoding="UTF-8"?>
<jsp:root
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <head>
        <!-- to change the content type or response encoding change the following line -->
        <jsp:directive.page contentType="text/html;charset=utf-8"/>

        <title><fmt:message key="title.changePassword.step2" /></title>

        <script>
            YAHOO.namespace("jms");
            function init() {
                // Instantiate a Panel from markup
                YAHOO.jms.changeContact = new YAHOO.widget.Panel("changeContact",
                    { fixedcenter:true, width:"350px", visible:true, draggable:false, close:false } );
                YAHOO.jms.changeContact.render();
                //YAHOO.util.Dom.get('firstName').focus();
                document.f.firstName.focus();
            }
            YAHOO.util.Event.addListener(window, "load", init);
        </script>
    </head>

    <body class="yui-skin-sam">
        <div id="changeContact">
            <form name="f" action="changeContact.do" method="post">
                <input type="hidden" name="readOnly" value="false" />
                <input type="hidden" name="userId" value="${entity.userId}" />
                <input type="hidden" name="active" value="${entity.active}" />
                <input type="hidden" name="contact.contactId" value="${entity.contact.contactId}" />

                <div class="hd, login">
                    <span class="security">&#160;</span>
                    <fmt:message key="title.changePassword.step2" />
                </div>

                <div class="bd">
                    <p><fmt:message key="title.changePassword.step2" /></p>

                    <div id="errors" class="error">
                        ${exception.message}
                    </div>

                    <table>
                        <tr>
                            <td class="bold"><fmt:message key="label.user.login" /></td>
                            <td><input type="text" name="login" value="${entity.login}" readonly="readonly" class="text" /></td>
                        </tr>

                        <!-- FIXME: does not decorate on WebLogic -->
                        <!--jsp:include page="/WEB-INF/entity/contact/edit.jsp" /-->
                        <tr>
                            <td class="bold"><fmt:message key="label.contact.salutation" /></td>
                            <td>
                                <select name="contact.salutation.salutation">
                                    <option value=""><fmt:message key="option.select" /></option>
                                    <c:forEach items="${salutations}" var="item">
                                        <c:if test="${item.id eq entity.contact.salutation.id}">
                                            <option value="${item.id}" selected="selected">${item.id}</option>
                                        </c:if>
                                        <c:if test="${item.id ne entity.contact.salutation.id}">
                                            <option value="${item.id}">${item.id}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="bold"><fmt:message key="label.contact.firstName" /></td>
                            <td><input type="text" name="contact.firstName" value="${entity.contact.firstName}" class="text" /></td>
                        </tr>
                        <tr>
                            <td class="bold"><fmt:message key="label.contact.surname" /></td>
                            <td><input type="text" name="contact.surname" value="${entity.contact.surname}" class="text" /></td>
                        </tr>
                        <tr>
                            <td class="bold"><fmt:message key="label.contact.email" /></td>
                            <td><input type="text" name="contact.email" value="${entity.contact.email}" class="text" /></td>
                        </tr>
                        <tr>
                            <td><fmt:message key="label.contact.emailConfirm" /></td>
                            <td><input type="text" name="contact.emailConfirm" value="${entity.contact.email}" class="text" /></td>
                        </tr>
                        <tr>
                            <td class="bold"><fmt:message key="label.contact.phone" /></td>
                            <td><input type="text" name="contact.phone" value="${entity.contact.phone}" class="text" /></td>
                        </tr>
                        <tr>
                            <td class="bold"><fmt:message key="label.contact.fax" /></td>
                            <td><input type="text" name="contact.fax" value="${entity.contact.fax}" class="text" /></td>
                        </tr>
                        <tr>
                            <td class="bold"><fmt:message key="label.contact.mobile" /></td>
                            <td><input type="text" name="contact.mobile" value="${entity.contact.mobile}" class="text" /></td>
                        </tr>
                        <tr>
                            <td class="bold"><fmt:message key="label.contact.skype" /></td>
                            <td><input type="text" name="contact.skype" value="${entity.contact.skype}" class="text" /></td>
                        </tr>
                        <!-- FIXME: does not decorate on WebLogic -->

                        <tr>
                            <td></td>
                            <td>
                                <fmt:message var="changeContact" key="button.change" />
                                <input name="submit" type="submit" class="button-submit" value="${changeContact}" />
                            </td>
                        </tr>
                    </table>
                </div>
            </form>
        </div>
    </body>

</jsp:root>