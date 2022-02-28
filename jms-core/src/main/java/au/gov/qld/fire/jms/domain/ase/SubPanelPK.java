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
public class SubPanelPK implements Serializable
{

    /** serialVersionUID */
    private static final long serialVersionUID = 7929552543103782867L;

    /** identifier field */
    private Long aseFileId;

    /** identifier field */
    private Long subPanelOrderId;

    public SubPanelPK()
    {
    	super();
    }

    public SubPanelPK(Long aseFileId, Long subPanelOrderId)
    {
        this.aseFileId = aseFileId;
        this.subPanelOrderId = subPanelOrderId;
    }

	@Column(name = "ASE_FILE_ID", nullable = false)
    public Long getAseFileId()
    {
        return this.aseFileId;
    }

    public void setAseFileId(Long aseFileId)
    {
        this.aseFileId = aseFileId;
    }

	@Column(name = "SUB_PANEL_ORDER_ID", nullable = false)
    public Long getSubPanelOrderId()
    {
        return this.subPanelOrderId;
    }

    public void setSubPanelOrderId(Long subPanelOrderId)
    {
        this.subPanelOrderId = subPanelOrderId;
    }

    public String toString()
    {
        return new ToStringBuilder(this).append("aseFileId", getAseFileId()).append(
            "subPanelOrderId", getSubPanelOrderId()).toString();
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof SubPanelPK))
            return false;
        SubPanelPK castOther = (SubPanelPK) other;
        return new EqualsBuilder().append(this.getAseFileId(), castOther.getAseFileId()).append(
            this.getSubPanelOrderId(), castOther.getSubPanelOrderId()).isEquals();
    }

    public int hashCode()
    {
        return new HashCodeBuilder().append(getAseFileId()).append(getSubPanelOrderId())
            .toHashCode();
    }

}
