package au.gov.qld.fire.jms.web.module.setup;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;

import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class AbstractUploadForm<T> extends AbstractValidatorForm<T>
{

	/** serialVersionUID */
	private static final long serialVersionUID = -713918532929168035L;

    private FormFile uploadFile;

    private transient String contentType;

    private transient String contentName;

    private transient byte[] content;

    private int contentLength;

    /**
     * @return the uploadFile
     */
    public FormFile getUploadFile()
    {
        return uploadFile;
    }

    public void setUploadFile(FormFile uploadFile) throws Exception
    {
        this.uploadFile = uploadFile;

        //we have two submit:
        //first - to save layer panel data to form (document data)
        //second - save form data (uploadFile is empty)
        if (StringUtils.isBlank(this.uploadFile.getFileName()))
        {
            return;
        }

        try
        {
            content = uploadFile.getFileData();
            contentLength = content == null ? 0 : content.length;
            contentType = uploadFile.getContentType();
            contentName = uploadFile.getFileName();
            if (LOG.isDebugEnabled())
                LOG.debug("content length=" + contentLength + ", mimeType=" + contentType + ", name=" + contentName);
        }
        catch (Exception e)
        {
            LOG.error("Could not load file for (" + uploadFile + "): " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * @return the content
     */
    public byte[] getContent()
    {
        return content;
    }

    /**
     * @return the content length
     */
    public int getContentLength()
    {
        return contentLength;
    }

    /**
     * @return the contentType
     */
    public String getContentType()
    {
        return contentType;
    }

    /**
     * @return the contentName
     */
    public String getContentName()
    {
        return contentName;
    }

}