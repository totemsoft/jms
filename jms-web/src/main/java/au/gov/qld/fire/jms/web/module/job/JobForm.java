package au.gov.qld.fire.jms.web.module.job;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.job.Job;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobForm extends AbstractValidatorForm<Job>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 7807428388228926095L;

    public static final String BEAN_NAME = "jobForm";

    /** responsibleUser */
    private User responsibleUser;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        //do nothing (this form is session bound)
        //super.reset(mapping, request);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(Job entity)
    {
        super.setEntity(entity);
        if (getEntity().getFca() == null)
        {
            getEntity().setFca(new Fca());
        }
        if (getEntity().getFile() == null)
        {
            getEntity().setFile(new File());
        }
        responsibleUser = getEntity().getResponsibleUser() != null ? getEntity().getResponsibleUser() : new User();
        // to refresh lazy hibernate collections
        getEntity().getJobDocs().size();
        getEntity().getJobActions().size();
    }

    /**
     * @return
     */
    public File getFile()
    {
        return getEntity().getFile();
    }

    /**
     * @return the responsibleUser
     */
    public User getResponsibleUser()
    {
    	if (responsibleUser == null)
    	{
    		responsibleUser = new User();
    	}
        return responsibleUser;
    }

    /**
     * @param responsibleUser the responsibleUser to set
     */
    public void setResponsibleUser(User responsibleUser)
    {
        this.responsibleUser = responsibleUser;
    }

    /**
     * Calculated property (either Active or Inactive).
     * @return
     */
    public String getJobStatus()
    {
        return getEntity().isStatus() ? "Active" : "Inactive";
    }

}