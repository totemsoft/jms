package au.gov.qld.fire.jms.domain.file;

import java.util.Date;

import au.gov.qld.fire.domain.SearchCriteria;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FileArchiveSearchCriteria extends SearchCriteria
{

	/** serialVersionUID */
	private static final long serialVersionUID = -1861612990529381132L;

    private Date date;

    private String fcaId;

    private Long fileId;

    private Long sapCustNo;

    /**
     * Set Max results to N rows.
     */
    public FileArchiveSearchCriteria()
    {
        setMaxResults(DEFAULT_MAX);
    }

    /**
     * @return date
     */
    public Date getDate()
    {
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	/**
     * @return the fcaId
     */
    public String getFcaId()
    {
        return fcaId;
    }

    public void setFcaId(String fcaId)
    {
        this.fcaId = fcaId;
    }

    /**
     * @return the fileId
     */
    public Long getFileId()
    {
        return fileId;
    }

    public void setFileId(Long fileId)
    {
        this.fileId = fileId;
    }

	/**
	 * @return the sapCustNo
	 */
	public Long getSapCustNo()
	{
		return sapCustNo;
	}

	public void setSapCustNo(Long sapCustNo)
	{
		this.sapCustNo = sapCustNo;
	}

}