package au.gov.qld.fire.jms.web.module.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.BasePair;
import au.gov.qld.fire.domain.refdata.LeaveType;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.domain.user.StaffLeave;
import au.gov.qld.fire.domain.user.StaffLeaveCalendar;
import au.gov.qld.fire.domain.user.StaffLeaveCalendarItem;
import au.gov.qld.fire.domain.user.StaffLeaveSearchCriteria;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.service.UserService;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class StaffCalendarAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        // set references
        request.setAttribute(SessionConstants.LEAVE_TYPES, getEntityService().findLeaveTypes());
        //
        List<BasePair> leaveTakenHours = new ArrayList<BasePair>();
        for (long i = 0; i < 8; i++)
        {
        	leaveTakenHours.add(new BasePair(i));
        }
        request.setAttribute("leaveTakenHours", leaveTakenHours);
        //
        List<BasePair> leaveTakenMinutes = new ArrayList<BasePair>();
    	leaveTakenMinutes.add(new BasePair(0L, "00"));
        for (long i = 1; i < 4; i++)
        {
        	leaveTakenMinutes.add(new BasePair(i * 15));
        }
        request.setAttribute("leaveTakenMinutes", leaveTakenMinutes);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
        StaffCalendarForm myform = (StaffCalendarForm) form;
        // Search by userId
    	StaffLeaveSearchCriteria criteria = new StaffLeaveSearchCriteria();
    	Long userId = myform.getUserId();
        criteria.setUser(userId == null ? null : new User(userId));
        criteria.setStatus(myform.getStatus());

        final UserService userService = getUserService();
        StaffLeaveCalendar entity = new StaffLeaveCalendar(WebUtils.getYear(request), WebUtils.getMonth(request));
        for (List<StaffLeaveCalendarItem> items : entity.getItems())
        {
            for (StaffLeaveCalendarItem item : items)
            {
                if (item == null)
                {
                	continue;
                }
                Date date = item.getDate();
            	criteria.setDate(date);
            	List<StaffLeave> staffLeaves = userService.findStaffLeaveByCriteria(criteria);
                item.setItems(staffLeaves);
                StaffLeave staffLeave;
                if (staffLeaves.isEmpty())
                {
                	staffLeave = new StaffLeave();
                	staffLeave.setDate(date);
                }
                else if (staffLeaves.size() == 1)
                {
                	staffLeave = staffLeaves.get(0);
                }
                else
                {
                	throw new ServiceException("Multiple leaves found for one day: " + date);
                }
                if (staffLeave.getLeaveType() == null && !DateUtils.isWeekend(date))
                {
                	staffLeave.setLeaveType(new LeaveType());
                }
                myform.getStaffLeaves().add(staffLeave);
            }
        }
        request.setAttribute(SessionConstants.ENTITIES, entity.getItems());
        request.setAttribute("userName", criteria.getUser().getContact().getName());
    }

    /**
     * 
     */
    public ActionForward find(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE find().. ");
        StaffCalendarForm myform = (StaffCalendarForm) form;
        myform.setStatus(null); // all leaves
        try
        {
            populateRequest(form, request);
            populateForm(mapping, form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    /**
     * 
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE edit()..");
        StaffCalendarForm myform = (StaffCalendarForm) form;
        try
        {
        	//Long staffLeaveId = WebUtils.getLongId(request);
        	myform.setStatus(null);
            populateRequest(form, request);
            populateForm(mapping, form, request);
            return findForward(mapping, EDIT);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return null;
        }
    }

    /**
     * 
     */
    public ActionForward approve(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE approve()..");
        try
        {
        	Long staffLeaveId = WebUtils.getLongId(request);
            getUserService().approveStaffLeave(staffLeaveId);
            return edit(mapping, form, request, response);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return null;
        }
    }

    /**
     * 
     */
    public ActionForward decline(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE decline()..");
        try
        {
        	Long staffLeaveId = WebUtils.getLongId(request);
            getUserService().declineStaffLeave(staffLeaveId);
            return edit(mapping, form, request, response);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return null;
        }
    }


    /**
     * Save changes (if any).
     */
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE save()..");
        try
        {
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty())
            {
                saveErrors(request, response, errors);
                populateRequest(form, request);
                return findForwardError(mapping);
            }

            StaffCalendarForm myform = (StaffCalendarForm) form;
            //String userId = request.getParameter(SessionConstants.USER_ID);
            Long userId = myform.getUserId();//StringUtils.isBlank(userId) ? null : new Long(userId);
            getUserService().saveStaffLeaves(userId, myform.getStaffLeaves());

            return findForwardSuccess(mapping);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return findForwardError(mapping);
        }
    }

}