package au.gov.qld.fire.jms.web.module.file;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import au.gov.qld.fire.domain.refdata.DateOptionEnum;
import au.gov.qld.fire.jms.domain.action.MailSearchCriteria;
import au.gov.qld.fire.jms.domain.refdata.OwnerTypeEnum;
import au.gov.qld.fire.util.BeanUtils;

/**
 * form == criteria
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class MailForm extends ValidatorForm // extends AbstractValidatorForm<MailOut>
{

	/** serialVersionUID */
	private static final long serialVersionUID = 3375672889711513486L;

	private Long batchId;

	private Long actionCodeId;
	private String actionCode;

	private Long regionId;
	private String region;

    /** search files where there are no items found on or after this date */
    private Integer sentDateOptionId;
    private String sentDateOption;
    private Date sentDateFrom;
    private Date sentDateTo;

    private Integer receivedDateOptionId;
    private String receivedDateOption;
    private Date receivedDateFrom;
    private Date receivedDateTo;

	private String fileId;
	private String fcaId;

    private boolean rejected;

    private boolean doNotMail;

    private String ownerTypeId;
    private String ownerLegalName;
    private Long ownerLegalNameId;
    private String ownerContactName; // firstName and/or surname
    private Long ownerContactNameId;

	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		this.batchId = null;
	}

	public MailSearchCriteria getCriteria()
	{
        MailSearchCriteria criteria = new MailSearchCriteria();
        BeanUtils.copyProperties(this, criteria, null);
        criteria.setSentDateOption(DateOptionEnum.findById(getSentDateOptionId()));
        criteria.setReceivedDateOption(DateOptionEnum.findById(getReceivedDateOptionId()));
        criteria.setOwnerType(getOwnerType());
        criteria.setOwnerId(StringUtils.isBlank(getOwnerLegalName()) ? null : getOwnerLegalNameId());
        criteria.setOwnerIdContact(StringUtils.isBlank(getOwnerContactName()) ? null : getOwnerContactNameId());
        criteria.setFileNo(getFileId());
        criteria.setFcaNo(getFcaId());
        return criteria;
	}

	/**
	 * @return the batchId
	 */
	public Long getBatchId()
	{
		return batchId;
	}

	public void setBatchId(Long batchId)
	{
		this.batchId = batchId;
	}

	/**
	 * @return actionCodeId
	 */
	public Long getActionCodeId()
	{
		if (actionCodeId != null && actionCodeId == 0L) {
			actionCodeId = null;
		}
		return actionCodeId;
	}

	public void setActionCodeId(Long actionCodeId)
	{
		this.actionCodeId = actionCodeId;
	}

	/**
	 * @return the actionCode
	 */
	public String getActionCode()
	{
		return actionCode;
	}

	public void setActionCode(String actionCode)
	{
		this.actionCode = actionCode;
	}

	/**
	 * @return regionId
	 */
	public Long getRegionId()
	{
		if (regionId != null && regionId == 0L) {
			regionId = null;
		}
		return regionId;
	}

	public void setRegionId(Long regionId)
	{
		this.regionId = regionId;
	}

	/**
	 * @return the region
	 */
	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
	}

	/**
	 * @return the sentDateOptionId
	 */
	public Integer getSentDateOptionId()
	{
		return sentDateOptionId;
	}

	public void setSentDateOptionId(Integer sentDateOptionId)
	{
		this.sentDateOptionId = sentDateOptionId;
	}

	/**
	 * @return the sentDateOption
	 */
	public String getSentDateOption()
	{
		if (sentDateOption == null) {
			sentDateOption = DateOptionEnum.NONE.getName();
		}
		return sentDateOption;
	}

	public void setSentDateOption(String sentDateOption)
	{
		this.sentDateOption = sentDateOption;
	}

	/**
	 * @return sentDateFrom
	 */
	public Date getSentDateFrom()
	{
		return sentDateFrom;
	}

	public void setSentDateFrom(Date sentDateFrom)
	{
		this.sentDateFrom = sentDateFrom;
	}

	/**
	 * @return the sentDateTo
	 */
	public Date getSentDateTo()
	{
		return sentDateTo;
	}

	public void setSentDateTo(Date sentDateTo)
	{
		this.sentDateTo = sentDateTo;
	}

	/**
	 * @return the receivedDateOptionId
	 */
	public Integer getReceivedDateOptionId()
	{
		return receivedDateOptionId;
	}

	public void setReceivedDateOptionId(Integer receivedDateOptionId)
	{
		this.receivedDateOptionId = receivedDateOptionId;
	}

	/**
	 * @return the receivedDateOption
	 */
	public String getReceivedDateOption()
	{
		if (receivedDateOption == null) {
			receivedDateOption = DateOptionEnum.NONE.getName();
		}
		return receivedDateOption;
	}

	public void setReceivedDateOption(String receivedDateOption)
	{
		this.receivedDateOption = receivedDateOption;
	}

	/**
	 * @return the receivedDateFrom
	 */
	public Date getReceivedDateFrom()
	{
		return receivedDateFrom;
	}

	public void setReceivedDateFrom(Date receivedDateFrom)
	{
		this.receivedDateFrom = receivedDateFrom;
	}

	/**
	 * @return the receivedDateTo
	 */
	public Date getReceivedDateTo()
	{
		return receivedDateTo;
	}

	public void setReceivedDateTo(Date receivedDateTo)
	{
		this.receivedDateTo = receivedDateTo;
	}

	/**
	 * @return fileId
	 */
	public String getFileId()
	{
		return fileId;
	}

	public void setFileId(String fileId)
	{
		this.fileId = fileId;
	}

	/**
	 * @return fcaId
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
	 * rejected will be mapped to status in action
	 * @return the rejected
	 */
	public boolean isRejected()
	{
		return rejected;
	}

	public void setRejected(boolean rejected)
	{
		this.rejected = rejected;
	}

	/**
	 * @return the doNotMail
	 */
	public boolean isDoNotMail()
	{
		return doNotMail;
	}

	public void setDoNotMail(boolean doNotMail)
	{
		this.doNotMail = doNotMail;
	}

	/**
	 * @return the ownerTypeId
	 */
	public String getOwnerTypeId()
	{
		if (StringUtils.isBlank(ownerTypeId)) {
			ownerTypeId = OwnerTypeEnum.NONE.getCode();
		}
		return ownerTypeId;
	}

	public void setOwnerTypeId(String ownerTypeId)
	{
		this.ownerTypeId = ownerTypeId;
	}

	/**
	 * @return the ownerType
	 */
	public OwnerTypeEnum getOwnerType()
	{
		return OwnerTypeEnum.valueOf(getOwnerTypeId());
	}

	/**
	 * @return the ownerLegalName
	 */
	public String getOwnerLegalName()
	{
		return ownerLegalName;
	}

	public void setOwnerLegalName(String ownerLegalName)
	{
		this.ownerLegalName = ownerLegalName;
	}

	/**
	 * @return the ownerLegalNameId
	 */
	public Long getOwnerLegalNameId()
	{
		return ownerLegalNameId;
	}

	public void setOwnerLegalNameId(Long ownerLegalNameId)
	{
		this.ownerLegalNameId = ownerLegalNameId;
	}

	/**
	 * @return the ownerContactName
	 */
	public String getOwnerContactName()
	{
		return ownerContactName;
	}

	public void setOwnerContactName(String ownerContactName)
	{
		this.ownerContactName = ownerContactName;
	}

	/**
	 * @return the ownerContactNameId
	 */
	public Long getOwnerContactNameId()
	{
		return ownerContactNameId;
	}

	public void setOwnerContactNameId(Long ownerContactNameId)
	{
		this.ownerContactNameId = ownerContactNameId;
	}

}