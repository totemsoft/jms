package au.gov.qld.fire.jms.web.module.setup;

import au.gov.qld.fire.jms.domain.refdata.ActionCode;
import au.gov.qld.fire.jms.domain.refdata.JobType;
import au.gov.qld.fire.domain.refdata.WorkGroup;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;
import au.gov.qld.fire.domain.security.SecurityGroup;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobTypeForm extends AbstractValidatorForm<JobType>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -1352840278897480133L;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(JobType entity)
    {
        super.setEntity(entity);
        //optional
        if (entity.getActionCode() == null)
        {
            entity.setActionCode(new ActionCode());
        }
        if (entity.getCloseJobSecurityGroup() == null)
        {
            entity.setCloseJobSecurityGroup(new SecurityGroup());
        }
        if (entity.getSecurityGroup() == null)
        {
            entity.setSecurityGroup(new SecurityGroup());
        }
        if (entity.getWorkGroup() == null)
        {
            entity.setWorkGroup(new WorkGroup());
        }
    }

}