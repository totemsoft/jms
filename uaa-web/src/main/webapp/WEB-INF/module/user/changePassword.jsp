<?xml version="1.0" encoding="utf-8"?>
<jsp:root
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <head>
        <!-- to change the content type or response encoding change the following line -->
        <jsp:directive.page contentType="text/html;charset=utf-8"/>

        <title><fmt:message key="title.changePassword.step1" /></title>

        <script>
            YAHOO.namespace("jms");
            function init() {
                // Instantiate a Panel from markup
                YAHOO.jms.changePassword = new YAHOO.widget.Panel("changePassword",
                    { fixedcenter:true, width:"350px", visible:true, draggable:false, close:false } );
                YAHOO.jms.changePassword.render();
                //YAHOO.util.Dom.get('password').focus();
                document.f.password.focus();
            }
            YAHOO.util.Event.addListener(window, "load", init);
        </script>
    </head>

    <body class="yui-skin-sam">
        <div id="changePassword">
            <form name="f" action="changePassword.do" method="post">
                <input type="hidden" name="readOnly" value="false" />
                <input type="hidden" name="userId" value="${entity.userId}" />
                
                <div class="hd, login">
                    <span class="security">&#160;</span>
                    <fmt:message key="title.changePassword.step1" />
                </div>
                
                <div class="bd">
                    <p><fmt:message key="title.changePassword.step1" /></p>
    
                    <table class="width100">
                        <tr class="error">
                            ${exception.message}
                        </tr>
                    </table>
    
                    <table>
                        <tr>
                            <td class="bold"><fmt:message key="label.user.login" /></td>
                            <td><input type="text" name="login" value="${entity.login}" readonly="readonly" class="text" /></td>
                        </tr>
                        <tr>
                            <td class="bold"><fmt:message key="label.user.password.current" /></td>
                            <td><input type="password" name="password" id="password" class="text" /></td>
                        </tr>
                        <tr>
                            <td class="bold"><fmt:message key="label.user.password.new" /></td>
                            <td><input type="password" name="passwordNew" class="text" /></td>
                        </tr>
                        <tr>
                            <td><fmt:message key="label.user.password.confirm" /></td>
                            <td><input type="password" name="passwordConfirm" class="text" /></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <fmt:message var="changePassword" key="button.change" />
                                <input name="submit" type="submit" class="button-submit" value="${changePassword}" />
                            </td>
                        </tr>
                    </table>
                </div>
            </form>
        </div>
    </body>

</jsp:root>