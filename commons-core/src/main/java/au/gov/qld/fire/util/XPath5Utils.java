package au.gov.qld.fire.util;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public final class XPath5Utils
{

    // hide ctor
    private XPath5Utils()
    {
        super();
    }

    /**
     * createXpath.
     * @param xpathExpression xpath.
     * @param context namespaceContext, can be null.
     * @return XPath.
     * @throws XPathException
     */
    public static XPath createXpath(String xpathExpression, NamespaceContext context)
        throws XPathException
    {
        XPath xpath = XPathFactory.newInstance().newXPath();
        if (context != null)
        {
            xpath.setNamespaceContext(context);
        }
        return xpath;
    }

    /**
     *
     * @param node
     * @param xpathExpression
     * @param context
     * @return
     */
    public static String getStringValue(Node node, String xpathExpression, NamespaceContext context)
        throws XPathException
    {
        XPath xpath = createXpath(xpathExpression, context);
        return (String) xpath.evaluate(xpathExpression, node, XPathConstants.STRING);
    }

    /**
     *
     * @param element
     * @param xpathExpression
     * @param context
     * @return
     * @throws XPathException
     */
    public static NodeList getNodesetValue(Node node, String xpathExpression,
        NamespaceContext context) throws XPathException
    {
        XPath xpath = createXpath(xpathExpression, context);
        return (NodeList) xpath.evaluate(xpathExpression, node, XPathConstants.NODESET);
    }

}