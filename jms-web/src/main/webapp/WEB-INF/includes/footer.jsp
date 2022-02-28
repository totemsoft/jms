<?xml version="1.0" encoding="UTF-8"?>
<jsp:root
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:logic="http://struts.apache.org/tags-logic"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
    version="2.0">

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr class="bottom_menu">
            <td width="100%">
                <a href="javascript:;">New to QFRS ?</a> | 
                <a href="javascript:;">View Virtual Tours</a> | 
                <a href="javascript:;">Provide Feedback</a> | 
                <a href="javascript:;">FAQs</a> | <a href="javascript:;">Help</a>
            </td>
            <c:set var="mail"><bean:message key="icon.mail" /></c:set>
            <td><img src="${mail}" width="14" height="14" /> </td>
            <td nowrap="nowrap"><a href="javascript:;">Email Your Administrators</a>&#160;</td>
        </tr>
        <tr>
            <td colspan="3" height="15" align="center" valign="bottom">
                Copyright &#169; 2008 <a href="http://www.apollosoft.net" target="_blank">ApolloSoft Pty Ltd</a>
            </td>
        </tr>
    </table>

</jsp:root>