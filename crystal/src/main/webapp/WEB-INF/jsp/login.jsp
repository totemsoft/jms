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

        <link href="yui/assets/skins/sam/skin.css" rel="stylesheet" type="text/css" />

        <script type="text/javascript" src="yui/yahoo-dom-event/yahoo-dom-event.js">&#160;</script>
        <script type="text/javascript" src="yui/container/container-min.js">&#160;</script>
        
        <style>
            body {visibility: hidden;}
        </style>

        <script type="text/javascript">
            YAHOO.util.Event.addListener(window, 'load', function() {
                // Instantiate a Panel from markup
                var winLogin = new YAHOO.widget.Panel('winLogin',
                    { fixedcenter:true, width:'380px', visible:true, draggable:false, close:false } );
                winLogin.render();
                YAHOO.util.Dom.get('j_username').focus();
            });
        </script>
    </head>

    <body class="yui-skin-sam">
        <div id="winLogin">
            <form action="security.do" method="post" id="userForm">
                <input type="hidden" name="dispatch" value="login" />

                <div class="hd, login">
                    <span class="security">&#160;</span>
                    <fmt:message key="title.login" />
                </div>

                <div class="bd">
                    <h3><fmt:message key="text.login" /></h3>

                    <c:if test="${error}">
                        <div class="error">
                            <div class="error">Your login attempt was not successful, try again.</div>
                        </div>
                    </c:if>

                    <table class="nonDataTable">
                        <tr>
                            <td class="nowrap"><fmt:message key="label.user.login" /></td>
                            <td class="width100">
                                <input type="text" class="text" id="j_username" value="" name="j_username" gtbfieldid="1" />
                            </td>
                        </tr>
                        <tr>
                            <td class="nowrap"><fmt:message key="label.user.password" /></td>
                            <td class="width100">
                                <input type="password" class="text" value="" name="password" />
                            </td>
                        </tr>
                    </table>
                </div>

                <div class="ft">
                    <table class="width100">
                        <tr>
                            <td class="center">
                                <fmt:message var="login" key="button.login" />
                                <input type="submit" class="button-submit" value="${login}" name="login" />
                            </td>
                        </tr>
                    </table>
                </div>
            </form>
        </div>
    </body>

</jsp:root>