package au.gov.qld.fire.util;

import java.util.List;

import org.jaxen.JaxenException;
import org.jaxen.NamespaceContext;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Node;

/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public final class XPathUtils
{

    // hide ctor
    private XPathUtils()
    {
        super();
    }

    /**
     * createXpath.
     * @param xpathExpression xpath.
     * @param context namespaceContext, can be null.
     * @return DOMXPath.
     * @throws JaxenException
     */
    public static DOMXPath createXpath(String xpathExpression, NamespaceContext context)
        throws JaxenException
    {
        DOMXPath xpath = new DOMXPath(xpathExpression);
        if (context != null)
        {
            xpath.setNamespaceContext(context);
        }
        return xpath;
    }

    /**
     *
     * @param contextNode
     * @param xpathExpression
     * @param context
     * @return
     */
    public static String getStringValue(Node contextNode, String xpathExpression, NamespaceContext context)
        throws JaxenException
    {
        DOMXPath xpath = createXpath(xpathExpression, context);
        Node node = (Node) xpath.selectSingleNode(contextNode);
        return node == null ? "" : node.getTextContent();
    }

    /**
     *
     * @param element
     * @param xpathExpression
     * @param context
     * @return
     * @throws JaxenException
     */
    @SuppressWarnings("unchecked")
    public static List getNodesetValue(Node contextNode, String xpathExpression,
        NamespaceContext context) throws JaxenException
    {
        DOMXPath xpath = createXpath(xpathExpression, context);
        List nodes = xpath.selectNodes(contextNode);
        return nodes;
    }

}