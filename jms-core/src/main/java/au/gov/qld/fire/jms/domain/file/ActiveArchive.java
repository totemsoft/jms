package au.gov.qld.fire.jms.domain.file;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "V_ACTIVE_ARCHIVE")
public class ActiveArchive
{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ARCHIVE_ID")
	private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_FROM", nullable = true)
    private Date dateFrom;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_TO", nullable = true)
    private Date dateTo;

    @Column(name = "FILE_ID", nullable = false)
    private Long fileId;

    @Column(name = "FCA_NO", nullable = true)
    private String fcaId;

    @Column(name = "SAP_CUST_NO", nullable = true)
    private Long sapCustNo;

    /**
     * @return id
     */
    public Long getId()
    {
        return id;
    }

	/**
	 * @return the dateFrom
	 */
	public Date getDateFrom()
	{
		return dateFrom;
	}

	/**
	 * @return the dateTo
	 */
	public Date getDateTo()
	{
		return dateTo;
	}

	/**
	 * @return the fileId
	 */
	public Long getFileId()
	{
		return fileId;
	}

	/**
	 * @return the fcaId
	 */
	public String getFcaId()
	{
		return fcaId;
	}

	/**
	 * @return the sapCustNo
	 */
	public Long getSapCustNo()
	{
		return sapCustNo;
	}

}