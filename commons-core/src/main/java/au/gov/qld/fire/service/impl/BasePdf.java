package au.gov.qld.fire.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class BasePdf
{
	protected final String XFA_DATA_NS = "http://www.xfa.org/schema/xfa-data/1.0/";

	protected final String XFA_DATA_ROOT_NODE = "xfa:datasets";
	protected final String XFA_DATA_NODE = "xfa:data";

    /**
     * Determine if the first element is the xfa-data root element expected.
     * 
     * @param dataStream
     *            an InputStream containing XML form data
     * @return a boolean indicating the presence of XFA header tags.
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
	protected boolean hasXFAheaders(InputStream dataStream, DocumentBuilder builder)
        throws ParserConfigurationException, SAXException, IOException
    {
        Document xmlDoc = builder.parse(dataStream);
        String xmlRootName = xmlDoc.getDocumentElement().getNodeName();
        return xmlRootName.equals(XFA_DATA_ROOT_NODE);
    }
    /**
     * adds back the XFA dataset wrapper that may be missing if, say, the form
     * data is exported from Acrobat.
     * 
     * The InputStream passed in will be closed after processing.
     * 
     * @param dataStream
     *            an InputStream containing XML form data
     * @return a replacement InputStream with the right XML tags
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws TransformerException
     * @throws Exception
     *             no exception-handling within this function.
     */
	protected InputStream addXFAheaders(InputStream dataStream, DocumentBuilder builder)
        throws ParserConfigurationException, SAXException, IOException,
        TransformerException
    {
        Document dataDoc = builder.parse(dataStream);
        Node dataRoot = dataDoc.getDocumentElement();
        //
        Document newDoc = builder.newDocument();
        Element newRoot = newDoc.createElementNS(XFA_DATA_NS, XFA_DATA_ROOT_NODE);
        newDoc.appendChild(newRoot);
        Node dataNode = newRoot.appendChild(newDoc.createElement(XFA_DATA_NODE));
        dataNode.appendChild(newDoc.importNode(dataRoot, true));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Source source = new DOMSource(newDoc);
        Writer writer = new OutputStreamWriter(out);
        StreamResult result = new StreamResult(writer);
        TransformerFactory.newInstance().newTransformer().transform(source, result);
        writer.flush();
        dataStream.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    /**
     * Looks for magic markers within the given file to identify the file's type.
     * @author pgallot
     * @param f a File to test
     * @return a FileID
     */
	protected enum FileID {NONE, DIR, PDF, FDF, XML, PKZIP}
	protected FileID identFile(File f)
    {
        FileID id;
        try {
            if (f.isDirectory())
                id = FileID.DIR;
            else if (!f.isFile())
                id = FileID.NONE;
            else {
                InputStream stream = new FileInputStream(f);
                id = identFile(stream);
            }
        } catch (IOException e) {
            id = FileID.NONE;
        }
        return id;
    }

	protected FileID identFile(InputStream input)
    {
        FileID id;
        try {
            byte[] hdr = new byte[1024];
            int readlen;

            readlen = input.read(hdr);
            input.close();

            // do a quick examination of the byte values to determine if they match up
            // with the expected magic markers for that particular file type.
            if (readlen < 5)
                id = FileID.NONE;
            else if (hdr[0] == (byte) 0x25 && hdr[1] == (byte) 0x50
                    && hdr[2] == (byte) 0x44 && hdr[3] == (byte) 0x46
                    && hdr[4] == (byte) 0x2D)
                id = FileID.PDF;
            else if (hdr[0] == (byte) 0x25 && hdr[1] == (byte) 0x46
                    && hdr[2] == (byte) 0x44 && hdr[3] == (byte) 0x46
                    && hdr[4] == (byte) 0x2D)
                id = FileID.FDF;
            else if (hdr[0] == (byte) 0x3C && hdr[1] == (byte) 0x3F
                    && hdr[2] == (byte) 0x78 && hdr[3] == (byte) 0x6D
                    && hdr[4] == (byte) 0x6C)
                id = FileID.XML;
            // TODO: examine hdr further to determine subtypes like XFDF.
            else if (hdr[0] == (byte) 0x50 && hdr[1] == (byte) 0x4B
                    && hdr[2] == (byte) 0x03 && hdr[3] == (byte) 0x04)
                id = FileID.PKZIP;
            else
                id = FileID.NONE;
        } catch (IOException e) {
            id = FileID.NONE;
        }
        return id;
    }

}