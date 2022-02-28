package au.gov.qld.fire.jms.domain.ase;

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
public class AlarmPanelPK implements Serializable
{

    /** serialVersionUID */
    private static final long serialVersionUID = 6897579804383393735L;

    private Long fileId;

    private Long supplierId;

    public AlarmPanelPK()
    {
        super();
    }

    public AlarmPanelPK(Long fileId, Long supplierId)
    {
        this.fileId = fileId;
        this.supplierId = supplierId;
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

	@Column(name = "SUPPLIER_ID", nullable = false)
    public Long getSupplierId()
    {
        return this.supplierId;
    }

    public void setSupplierId(Long supplierId)
    {
        this.supplierId = supplierId;
    }

    public String toString()
    {
        return new ToStringBuilder(this).append("fileId", getFileId()).append("supplierId",
            getSupplierId()).toString();
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof AlarmPanelPK))
            return false;
        AlarmPanelPK castOther = (AlarmPanelPK) other;
        return new EqualsBuilder().append(this.getFileId(), castOther.getFileId()).append(
            this.getSupplierId(), castOther.getSupplierId()).isEquals();
    }

    public int hashCode()
    {
        return new HashCodeBuilder().append(getFileId()).append(getSupplierId()).toHashCode();
    }

}