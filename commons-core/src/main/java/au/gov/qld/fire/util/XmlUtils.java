package au.gov.qld.fire.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.jaxen.NamespaceContext;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public final class XmlUtils
{

    // hide ctor
    private XmlUtils()
    {
        super();
    }

    /**
     * create DOM document
     * @return		XML document (org.w3c.dom.Document)
     */
    public static Document newDocument() throws ParserConfigurationException
    {
        return newDocument(null, null);
    }

    public static Document newDocument(DocumentBuilderFactory factory, Properties jaxpAttributes)
        throws ParserConfigurationException
    {
        if (factory == null)
            factory = DocumentBuilderFactory.newInstance();

        if (jaxpAttributes != null)
        {
            for (Map.Entry<Object, Object> entry : jaxpAttributes.entrySet())
            {
                factory.setAttribute((String) entry.getKey(), entry.getValue());
            }
        }

        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        return docBuilder.newDocument();
    }

    /**
     *
     * @param	source		[in]	Stream of XML document (java.io.InputStream)
     * @return	destination	[out]	XML document (org.w3c.dom.Document)
     */
    public static Document parse(InputStream source) throws ParserConfigurationException,
        IOException, SAXException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        return docBuilder.parse(source);
    }

    /**
    *
    * @param   source      [in]    Stream of XML document (java.io.InputStream)
    * @return  destination [out]   XML document (org.w3c.dom.Document)
    */
    public static Document parse(URL source) throws ParserConfigurationException, IOException,
        SAXException
    {
        InputStream is = source.openStream();
        try
        {
            return parse(is);
        }
        finally
        {
            IOUtils.closeQuietly(is);
        }
    }

    /**
     *
     * @param  source       [in]    Stream of XML document (java.io.InputStream)
     * @param  context      [in]    Can be null (for non NamespaceAware xml document).
     * @return destination  [out]   XML document (org.w3c.dom.Document)
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static Document parse(InputStream source, NamespaceContext context)
        throws ParserConfigurationException, IOException, SAXException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(context != null);
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        return docBuilder.parse(source);
    }

    /**
     * @param	source		[in]	Stream of XML document (java.io.InputStream)
     * @param	destination	[out]	XML document (org.w3c.dom.Document)
     */
    public static void transform(InputStream source, OutputStream result)
        throws TransformerException
    {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new StreamSource(source), new StreamResult(result));
    }

    /**
     * @param   source      [in]    Stream of XML document (java.io.Reader)
     * @param   destination [out]   XML document (org.w3c.dom.Document)
     */
    public static void transform(Reader source, OutputStream result) throws TransformerException
    {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new StreamSource(source), new StreamResult(result));
    }

    public static void transform(Source source, OutputStream result) throws TransformerException
    {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, new StreamResult(result));
    }

    public static void transform(Source source, Result result) throws TransformerException
    {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, result);
    }

    /**
     * @param   source          [in]    XML document
     * @param   result          [in]    XML document
     * @param   transformation  [in]    XSLT transformation
     * @return  List of messages generated in xml
     */
    public static void transform(Source source, Result result, Source transformation)
        throws TransformerException
    {
        transform(source, result, transformation, null, null, null);
    }

    /**
     * @param   source          [in]    XML document
     * @param   result          [in]    XML document
     * @param   transformation  [in]    XSLT transformation
     * @return  List of messages generated in xml
     */
    public static void transform(Source source, Result result, Source transformation,
        Properties parameters) throws TransformerException
    {
        transform(source, result, transformation, parameters, null, null);
    }

    /**
     * @param	source			[in]	XML document
     * @param	result			[in]	XML document
     * @param	transformation	[in]	XSLT transformation
     * @param	errorListener	[in]	ErrorListener
     * @param	uriResolver		[in]	URIResolver
     */
    public static void transform(Source source, Result result, Source transformation,
        Properties parameters, ErrorListener errorListener, URIResolver uriResolver)
        throws TransformerException
    {
        // template can be used for optimization
        TransformerFactory tf = TransformerFactory.newInstance();

        if (errorListener != null)
        {
            tf.setErrorListener(errorListener);
        }
        if (uriResolver != null)
        {
            tf.setURIResolver(uriResolver);
        }

        Transformer transformer = tf.newTransformer(transformation);

        if (parameters != null)
        {
            for (Map.Entry<Object, Object> entry : parameters.entrySet())
            {
                transformer.setParameter((String) entry.getKey(), (String) entry.getValue());
            }
        }

        transformer.transform(source, result);
    }

    /**
     * format XML
     * @param data
     * @return
     */
    public static String formatXML(String data)
    {
        return data.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;")
            .replaceAll("\'", "&apos;").replaceAll("\"", "&quot;");
    }

}