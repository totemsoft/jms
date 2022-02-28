package au.gov.qld.fire.jms.domain.sap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonProperty;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.jms.domain.file.File;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "SAP_HEADER")
@org.hibernate.annotations.GenericGenerator(name = "assigned-strategy", strategy = "assigned")
public class SapHeader extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -3697886854181135284L;

    public static final String FILE = "file";

    @JsonProperty
    private Long sapCustNo;

    private File file;

    @Id
    @GeneratedValue(generator = "assigned-strategy")
    @Column(name = "FILE_ID")
    public Long getSapHeaderId()
    {
        return super.getId();
    }

    public void setSapHeaderId(Long id)
    {
        super.setId(id);
    }

    /** 
     *                @hibernate.property
     *                 column="SAP_CUST_NO"
     *             
     */
    @Column(name = "SAP_CUST_NO", nullable = true, scale = 11)
    public Long getSapCustNo()
    {
        return this.sapCustNo;
    }

    public void setSapCustNo(Long sapCustNo)
    {
        this.sapCustNo = sapCustNo;
    }

    /** 
     *            @hibernate.many-to-one
     *             update="false"
     *             insert="false"
     *         
     *            @hibernate.column
     *             name="FILE_ID"
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID", nullable = false, insertable = false, updatable = false)
    public File getFile()
    {
        return this.file;
    }

    public void setFile(File file)
    {
        if (getSapHeaderId() == null && file != null)
        {
            setSapHeaderId(file.getFileId());
        }
        this.file = file;
    }

}