package au.gov.qld.fire.jms.web.module.file;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;

import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class AbstractAttachmentForm<T> extends AbstractValidatorForm<T>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 6734468971503263811L;

    /** current attachmentFiles */
    private FormFile[] attachmentFiles;

    private Integer[] attachmentIndex;

    /** current attachment Documents */
    private List<Document> attachments;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(T entity)
    {
        super.setEntity(entity);
        attachmentFiles = null;
        attachmentIndex = null;
        attachments = null;
    }

    /**
     * @param index
     * @return
     */
    public FormFile getAttachmentFiles(int index)
    {
        if (attachmentFiles == null)
        {
            attachmentFiles = new FormFile[0];
        }
        while (attachmentFiles.length <= index)
        {
            attachmentFiles = (FormFile[]) ArrayUtils.add(attachmentFiles, null);
        }
        return attachmentFiles[index];
    }

    /**
     * @param value the attachmentFile to set
     */
    public void setAttachmentFiles(int index, FormFile value) throws Exception
    {
        //Initialise FormFile array
        getAttachmentFiles(index);
        attachmentFiles[index] = value;
        if (value != null && StringUtils.isNotBlank(value.getFileName()))
        {
            while (getAttachments().size() <= index)
            {
                getAttachments().add(new Document());
            }
            Document d = getAttachments().get(index);
            if (d == null) {
                d = new Document();
                getAttachments().set(index, d);
            }
            d.setName(value.getFileName());
            d.setContent(value.getFileData());
            d.setContentType(value.getContentType());
        }
    }

    /**
     * @return the attachmentIndexes
     */
    public Integer getAttachmentIndex(int index)
    {
        if (attachmentIndex == null)
        {
            attachmentIndex = new Integer[0];
        }
        while (attachmentIndex.length <= index)
        {
            attachmentIndex = (Integer[]) ArrayUtils.add(attachmentIndex, null);
        }
        return attachmentIndex[index];
    }

    /**
     * @param attachmentIndexes the attachmentIndexes to set
     */
    public void setAttachmentIndex(int index, Integer value)
    {
        //Initialise attachmentIndex array
        getAttachmentIndex(index);
        attachmentIndex[index] = value;
        while (getAttachments().size() <= index)
        {
            getAttachments().add(null);
        }
    }

    /**
     * @return the attachments
     */
    public List<Document> getAttachments()
    {
        if (attachments == null)
        {
            attachments = new ArrayList<Document>();
        }
        return attachments;
    }

}