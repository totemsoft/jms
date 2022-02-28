package au.gov.qld.fire.jms.web.module.job;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.job.JobRequest;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;
import au.gov.qld.fire.util.ThreadLocalUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobRequestForm extends AbstractValidatorForm<JobRequest>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 804453220159891876L;

    public static final String BEAN_NAME = "jobRequest";

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        super.reset(mapping, request);
        setEntity(new JobRequest());
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(JobRequest entity)
    {
        super.setEntity(entity);
        if (entity.getFca().getFile() == null)
        {
        	entity.getFca().setFile(new File());
        }
        if (entity.getRequestedDate() == null)
        {
            entity.setRequestedDate(ThreadLocalUtils.getDate());
        }
        if (entity.getRequestedBy() == null)
        {
            entity.setRequestedBy(ThreadLocalUtils.getUser());
            //entity.setRequestedEmail(ThreadLocalUtils.getUser().getContact().getEmail());
        }
    }

}