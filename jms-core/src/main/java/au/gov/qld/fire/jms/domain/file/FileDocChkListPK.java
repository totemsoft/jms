package au.gov.qld.fire.jms.domain.file;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Embeddable
public class FileDocChkListPK implements Serializable
{

    /** serialVersionUID */
    private static final long serialVersionUID = -1612487257082501679L;

    /** identifier field */
    private Long fileId;

    /** identifier field */
    private Long docChkListId;

    public FileDocChkListPK()
    {
    	super();
    }

    public FileDocChkListPK(Long fileId, Long docChkListId)
    {
        this.fileId = fileId;
        this.docChkListId = docChkListId;
    }

	@Column(name = "FILE_ID", nullable = false)
    public Long getFileId()
    {
        return this.fileId;
    }

    public void setFileId(Long fileId)
    {
        this.fileId = fileId;
    }

	@Column(name = "DOC_CHK_LIST_ID", nullable = false)
    public Long getDocChkListId()
    {
        return this.docChkListId;
    }

    public void setDocChkListId(Long docChkListId)
    {
        this.docChkListId = docChkListId;
    }

    public String toString()
    {
        return new ToStringBuilder(this).append("fileId", getFileId()).append("docChkListId",
            getDocChkListId()).toString();
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof FileDocChkListPK))
            return false;
        FileDocChkListPK castOther = (FileDocChkListPK) other;
        return new EqualsBuilder().append(this.getFileId(), castOther.getFileId()).append(
            this.getDocChkListId(), castOther.getDocChkListId()).isEquals();
    }

    public int hashCode()
    {
        return new HashCodeBuilder().append(getFileId()).append(getDocChkListId()).toHashCode();
    }

}