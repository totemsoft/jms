<?xml version="1.0" encoding="utf-8"?>
<jsp:root
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:logic="http://struts.apache.org/tags-logic"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <html:xhtml/>

    <c:set var="action" value="j_security_check" />

    <head>
        <!-- to change the content type or response encoding change the following line -->
        <jsp:directive.page contentType="text/html;charset=utf-8"/>
        <!--script type="text/javascript" src="scripts/googleapis/ajax/libs/chrome-frame/1/CFInstall.js">${nbsp}</script-->
        <title><bean:message key="title.login" /></title>

        <script type="text/javascript">
            YAHOO.util.Event.addListener(window, 'load', function() {
                // Instantiate a Panel from markup
                var winLogin = new YAHOO.widget.Panel('winLogin',
                    { fixedcenter:true, width:'380px', visible:true, draggable:false, close:false } );
                winLogin.render();
                YAHOO.util.Dom.get('j_username').focus();
//                if (YAHOO.env.ua.ie >= 6) {
//                    if (!CFInstall.isAvailable()) {
//                        document.getElementById('message').innerHTML = '<a href="http://www.google.com/chromeframe?quickenable=true" title="For the best user experience - install Google Chrome Frame plug-in!">Install Google Chrome Frame</a>';
//                    }
//                }
            });
        </script>
    </head>

    <body class="yui-skin-sam">
        <div id="winLogin">
            <html:form action="${action}">
                <html:hidden property="dispatch" value="login" />

                <div class="hd, login">
                    <span class="security">${nbsp}</span>
                    <bean:message key="title.login" />
                </div>

                <div class="bd">
                    <h3><bean:message key="text.login" /></h3>

                    <table align="center">
                        <html:errors />
                    </table>

                    <table class="nonDataTable">
                        <tr>
                            <td class="nowrap"><bean:message key="label.user.login" /></td>
                            <td class="width100"><html:text styleId="j_username" property="j_username" styleClass="text" /></td>
                        </tr>
                        <tr>
                            <td class="nowrap"><bean:message key="label.user.password" /></td>
                            <td class="width100"><html:password property="password" styleClass="text" /></td>
                        </tr>
                    </table>
                </div>

                <div class="ft">
                    <table class="width100">
                        <tr>
                            <td class="center">
                                <html:submit property="login" styleClass="button-submit">
                                    <bean:message key="button.login" />
                                </html:submit>
                            </td>
                            <td id="message" class="center"></td>
                        </tr>
                        <!--tr>
                            <td class="center">
                                <html:link module="/user" action="/forgotPassword.do">
                                    <bean:message key="link.forgotPassword" />
                                </html:link>
                                |
                                <html:link module="/user" action="/registerUser.do">
                                    <bean:message key="link.registerUser" />
                                </html:link>
                            </td>
                        </tr-->
                    </table>
                </div>
            </html:form>
        </div>
    </body>
</jsp:root>