package au.gov.qld.fire.jms.web.module.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.refdata.LeaveType;
import au.gov.qld.fire.domain.user.StaffLeave;
import au.gov.qld.fire.domain.user.StaffLeave.StatusEnum;
import au.gov.qld.fire.domain.user.StaffLeaveCalendar;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class StaffCalendarForm extends AbstractValidatorForm<StaffLeaveCalendar>
{

	/** serialVersionUID */
	private static final long serialVersionUID = -5705621437870064331L;

	private Long userId;

	private int day;

	private StatusEnum status;

    private List<StaffLeave> staffLeaves;

    /* (non-Javadoc)
     * @see org.apache.struts.validator.ValidatorForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        super.reset(mapping, request);
        //this.userId = null;
        this.staffLeaves = null;
    }

    /* (non-Javadoc)
     * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        return super.validate(mapping, request);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(StaffLeaveCalendar entity)
    {
        super.setEntity(entity);
    }

	/**
	 * @return the userId
	 */
	public Long getUserId()
	{
		if (userId != null && userId == 0L)
		{
			userId = null;
		}
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(int day)
	{
		this.day = day;
	}

	/**
	 * @return the status
	 */
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
	 * @return the staffLeaves
	 */
	public List<StaffLeave> getStaffLeaves()
	{
		if (staffLeaves == null)
		{
	        staffLeaves = new ArrayList<StaffLeave>();
		}
		return staffLeaves;
	}

	/**
	 * @param staffLeaves the staffLeaves to set
	 */
	public void setStaffLeaves(List<StaffLeave> staffLeaves)
	{
		this.staffLeaves = staffLeaves;
	}

	public StaffLeave getStaffLeave(int index)
	{
		while (getStaffLeaves().size() <= index)
		{
			StaffLeave staffLeave = new StaffLeave();
			staffLeave.setLeaveType(new LeaveType());
			getStaffLeaves().add(staffLeave);
		}
		return getStaffLeaves().get(index);
	}

}