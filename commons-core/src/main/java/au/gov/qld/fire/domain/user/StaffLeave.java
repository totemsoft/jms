package au.gov.qld.fire.domain.user;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.refdata.LeaveType;
import au.gov.qld.fire.domain.security.User;

/**
 * @hibernate.class table="STAFF_LEAVE" dynamic-update="true"
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "STAFF_LEAVE")
public class StaffLeave extends Auditable<Long>
{

    /** serialVersionUID */
	private static final long serialVersionUID = -8942740397932385862L;

	/** java enum for now, see LEAVE_STATUS table for values */
	public static enum StatusEnum {NONE, SUBMITTED, APPROVED, DECLINED};

    /**
     * USER_IGNORE
     */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"staffLeaveId", "user", "leaveType", "status", "statusBy", "statusDate"});

    private static final BigDecimal DEFAULT_LEAVE = new BigDecimal("7.25"); // 7h15m

    private User user;

    private Long leaveGroupId;

    private LeaveType leaveType;

    private Date date;

    @Transient private Date dateEnd;

    private String description;

    private StatusEnum status;

    private User statusBy;

    private Date statusDate;

    @Transient private int leaveTakenHours = -1;

    @Transient private int leaveTakenMinutes = -1;

    private BigDecimal leaveTaken = DEFAULT_LEAVE;

    private boolean formReceived;

    private Date formSentHrDate;

    /**
     * 
     */
    public StaffLeave()
    {
        super();
    }

    /**
     * @param id
     */
    public StaffLeave(Long id)
    {
        super(id);
    }

    /** 
     *            @hibernate.id
     *             generator-class="sequence"
     *             type="java.lang.Long"
     *             column="STAFF_LEAVE_ID"
     *         
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STAFF_LEAVE_ID")
    public Long getStaffLeaveId()
    {
        return super.getId();
    }

    public void setStaffLeaveId(Long staffLeaveId)
    {
        super.setId(staffLeaveId);
    }

	/**
	 * @return the user
	 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = true)
	public User getUser()
	{
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user)
	{
		this.user = user;
	}

	/**
	 * @return the leaveGroupId
	 */
    @Column(name = "LEAVE_GROUP_ID")
	public Long getLeaveGroupId()
	{
		return leaveGroupId;
	}

	/**
	 * @param leaveGroupId the leaveGroupId to set
	 */
	public void setLeaveGroupId(Long leaveGroupId)
	{
		this.leaveGroupId = leaveGroupId;
	}

	/**
	 * @return the leaveType
	 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEAVE_TYPE_ID", nullable = false)
	public LeaveType getLeaveType()
	{
		return leaveType;
	}

	/**
	 * @param leaveType the leaveType to set
	 */
	public void setLeaveType(LeaveType leaveType)
	{
		this.leaveType = leaveType;
	}

	/**
	 * @return the date
	 */
    @Temporal(TemporalType.DATE)
    @Column(name = "LEAVE_DATE", nullable = false)
	public Date getDate()
	{
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}

	/**
	 * Helper method for multiple entries - eg 2 weeks holiday
	 * @return the dateEnd
	 */
	@Transient
	public Date getDateEnd()
	{
		return dateEnd;
	}

	/**
	 * @param dateEnd the dateEnd to set
	 */
	public void setDateEnd(Date dateEnd)
	{
		this.dateEnd = dateEnd;
	}

	/**
	 * @return the description
	 */
    @Column(name = "DESCRIPTION", nullable = true)
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the status
	 */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "STATUS_ID", nullable = true)
	public StatusEnum getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(StatusEnum status)
    {
		this.status = status;
	}

	/**
	 * Calculated
	 * @return
	 */
	@Transient
	public boolean isSubmitted()
	{
		return StatusEnum.SUBMITTED.equals(status);
	}

	/**
	 * @return the statusBy
	 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS_BY", nullable = true)
	public User getStatusBy()
	{
		return statusBy;
	}

	/**
	 * @param statusBy the statusBy to set
	 */
	public void setStatusBy(User statusBy)
	{
		this.statusBy = statusBy;
	}

	/**
	 * @return the statusDate
	 */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STATUS_DATE", nullable = true)
	public Date getStatusDate()
	{
		return statusDate;
	}

	/**
	 * @param statusDate the statusDate to set
	 */
	public void setStatusDate(Date statusDate)
	{
		this.statusDate = statusDate;
	}

	/**
	 * @return the leaveTakenHours
	 */
	@Transient
	public int getLeaveTakenHours()
	{
		if (leaveTakenHours < 0)
		{
			leaveTakenHours = leaveTaken.setScale(0,  RoundingMode.FLOOR).intValue();
		}
		return leaveTakenHours;
	}

	/**
	 * @param leaveTakenHours the leaveTakenHours to set
	 */
	public void setLeaveTakenHours(int leaveTakenHours)
	{
		this.leaveTakenHours = leaveTakenHours;
	}

	/**
	 * @return the leaveTakenMinutes
	 */
	@Transient
	public int getLeaveTakenMinutes()
	{
		if (leaveTakenMinutes < 0)
		{
			leaveTakenMinutes = leaveTaken.subtract(leaveTaken.setScale(0,  RoundingMode.FLOOR)).movePointRight(2).intValue();
			if (leaveTakenMinutes != 0)
			{
				leaveTakenMinutes = leaveTakenMinutes * 3 / 5;
			}
		}
		return leaveTakenMinutes;
	}

	/**
	 * @param leaveTakenMinutes the leaveTakenMinutes to set
	 */
	public void setLeaveTakenMinutes(int leaveTakenMinutes)
	{
		this.leaveTakenMinutes = leaveTakenMinutes;
	}

	/**
	 * @return the leaveTaken
	 */
    @Column(name = "LEAVE_TAKEN", nullable = false)
	public BigDecimal getLeaveTaken()
	{
		return leaveTaken;
	}

	/**
	 * @param leaveTaken the leaveTaken to set
	 */
	public void setLeaveTaken(BigDecimal leaveTaken)
	{
		this.leaveTaken = leaveTaken;
	}

	/**
	 * @return the formReceived
	 */
    @Column(name = "FORM_RECEIVED", nullable = false)
    @Type(type = "yes_no")
	public boolean isFormReceived()
	{
		return formReceived;
	}

	/**
	 * @param formReceived the formReceived to set
	 */
	public void setFormReceived(boolean formReceived)
	{
		this.formReceived = formReceived;
	}

	/**
	 * @return the formSentHrDate
	 */
    @Temporal(TemporalType.DATE)
    @Column(name = "FORM_SENT_HR_DATE", nullable = true)
	public Date getFormSentHrDate()
	{
		return formSentHrDate;
	}

	/**
	 * @param formSentHrDate the formSentHrDate to set
	 */
	public void setFormSentHrDate(Date formSentHrDate)
	{
		this.formSentHrDate = formSentHrDate;
	}

}