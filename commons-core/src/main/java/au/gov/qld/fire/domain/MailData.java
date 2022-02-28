package au.gov.qld.fire.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.util.Encoder;
import au.gov.qld.fire.util.XPathUtils;
import au.gov.qld.fire.util.XmlUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class MailData
{
	public static final String NO_SUBJECT = ".";

	/** message id, etc */
    private String id;
    private Date sentDate;
    private Date receivedDate;

    /** from */
    private String from;

    /** to */
    private String to;

    /** cc */
    private String cc;

    /** subject */
    private String subject;

    /** text */
    private String text;

    /** attachments */
    private File[] attachments;
    /** first and the only attachment document */
    private Document document;

	/**
	 * build unique identifier
	 * @param message
	 * @return
	 */
    @SuppressWarnings("unchecked")
	public static String generateId(javax.mail.Message message) throws Exception
    {
		int number = message.getMessageNumber();
		StringBuffer buf = new StringBuffer();
		Enumeration<javax.mail.Header> headers = message.getAllHeaders();
		while (headers.hasMoreElements()) {
			javax.mail.Header header = headers.nextElement();
			buf.append(header.getName() + "=" + header.getValue() + ";");
		}
		return number + "." + Encoder.digest(buf.toString().getBytes());
	}

    /**
     * 
     */
    public MailData()
    {
        super();
    }

    /**
     * @param content
     * @throws Exception 
     */
    public MailData(InputStream content) throws Exception
    {
        // load properties from template
    	org.w3c.dom.Document doc = XmlUtils.parse(content);
    	org.w3c.dom.Element root = doc.getDocumentElement();
        from = XPathUtils.getStringValue(root, "/email/from", null);
        subject = XPathUtils.getStringValue(root, "/email/subject", null);
        text = XPathUtils.getStringValue(root, "/email/body", null);
    }

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the sentDate
	 */
	public Date getSentDate()
	{
		return sentDate;
	}

	public void setSentDate(Date sentDate)
	{
		this.sentDate = sentDate;
	}

	/**
	 * @return the receivedDate
	 */
	public Date getReceivedDate()
	{
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate)
	{
		this.receivedDate = receivedDate;
	}

	/**
     * @return the from
     */
    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    /**
     * @return the to
     */
    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        this.to = to;
    }

    /**
     * @return the cc
     */
    public String getCc()
    {
        return cc;
    }

    public void setCc(String cc)
    {
        this.cc = cc;
    }

    /**
     * @return the subject
     */
    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    /**
     * @return the text
     */
    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * @return the attachments
     */
    public File[] getAttachments()
    {
        return attachments;
    }

    public void setAttachments(File[] attachments)
    {
        this.attachments = attachments;
    }

    public void setAttachmentsZip(File[] attachments, File zipFile) throws IOException
    {
        ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFile));
        try
        {
            for (int i = 0; attachments != null && i < attachments.length; i++) {
            	File attachment = attachments[i];
                InputStream input = new FileInputStream(attachment);
                // add ZIP entry to output stream
                zout.putNextEntry(new ZipEntry(attachment.getName()));
                // transfer bytes from the file to the ZIP file
                org.apache.commons.io.IOUtils.copy(input, zout);
                // complete the entry
                zout.closeEntry();
                IOUtils.closeQuietly(input);
            }
            setAttachments(new File[] {zipFile});
        }
        finally
        {
            // complete the ZIP file
            IOUtils.closeQuietly(zout);
        }
    }

	/**
	 * @return the document
	 */
	public Document getDocument()
	{
		return document;
	}

	public void setDocument(Document document)
	{
		this.document = document;
	}

}