package au.gov.qld.fire.jms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.jms.domain.file.File;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "KEY_RECEIPT")
public class KeyReceipt extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 6539050257794742459L;

    /** FILE property name */
    public static final String FILE = "file";

    private String keyReceiptNo;

    private File file;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KEY_RECEIPT_ID")
    public Long getKeyReceiptId()
    {
        return super.getId();
    }

    public void setKeyReceiptId(Long keyReceiptId)
    {
        super.setId(keyReceiptId);
    }

    /** 
     *            @hibernate.property
     *             column="KEY_RECEIPT_NO"
     *             length="50"
     *             not-null="true"
     *         
     */
    @Column(name = "KEY_RECEIPT_NO", nullable = false)
    public String getKeyReceiptNo()
    {
        return this.keyReceiptNo;
    }

    public void setKeyReceiptNo(String keyReceiptNo)
    {
        this.keyReceiptNo = keyReceiptNo;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="FILE_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID", nullable = false)
    public File getFile()
    {
        return this.file;
    }

    public void setFile(File file)
    {
        this.file = file;
    }

}