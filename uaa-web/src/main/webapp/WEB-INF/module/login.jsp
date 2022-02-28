<?xml version="1.0" encoding="utf-8"?>
<jsp:root
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <head>
        <!-- to change the content type or response encoding change the following line -->
        <jsp:directive.page contentType="text/html;charset=utf-8"/>

        <title><fmt:message key="title.login" /></title>

        <script type="text/javascript">
            YAHOO.util.Event.addListener(window, 'load', function() {
                // Instantiate a Panel from markup
                var winLogin = new YAHOO.widget.Panel('winLogin',
                    {fixedcenter:true, width:'380px', visible:true, draggable:false, close:false});
                winLogin.render();
                //YAHOO.util.Dom.get('j_username').focus();
                document.f.j_username.focus();
            });
        </script>
    </head>

    <body class="yui-skin-sam">
        <div id="winLogin">
            <form name="f" action="j_security_check.do" method="post">
                <input type="hidden" name="dispatch" value="login" />

                <div class="hd, login">
                    <span class="security">&#160;</span>
                    <fmt:message key="title.login" />
                </div>

                <div class="bd">
                    <h3><fmt:message key="text.login" /></h3>

                    <table class="width100">
                        <tr class="error">
                            ${exception.message}
                        </tr>
                    </table>

                    <table class="nonDataTable">
                        <tr>
                            <td class="nowrap"><fmt:message key="label.user.login" /></td>
                            <td class="width100"><input type="text" class="text" name="j_username" value="" /></td>
                        </tr>
                        <tr>
                            <td class="nowrap"><fmt:message key="label.user.password" /></td>
                            <td class="width100"><input type="password" class="text" name="password" /></td>
                        </tr>
                    </table>
                </div>

                <div class="ft">
                    <table class="width100">
                        <tr>
                            <td class="center">
                                <input name="submit" type="submit" class="button-submit" value="Login" />
                            </td>
                        </tr>
                    </table>
                </div>
            </form>
        </div>
    </body>

</jsp:root>