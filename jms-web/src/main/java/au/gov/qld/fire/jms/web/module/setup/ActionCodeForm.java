package au.gov.qld.fire.jms.web.module.setup;

import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;
import au.gov.qld.fire.jms.domain.refdata.JobType;
import au.gov.qld.fire.domain.refdata.WorkGroup;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ActionCodeForm extends AbstractValidatorForm<ActionCode>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 5352461513022179110L;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(ActionCode entity)
    {
        super.setEntity(entity);
        // optional
        if (entity.getJobType() == null) {
        	entity.setJobType(new JobType());
        }
        if (entity.getWorkGroup() == null) {
            entity.setWorkGroup(new WorkGroup());
        }
        if (entity.getTemplate() == null) {
            entity.setTemplate(new Template());
        }
        if (entity.getDocumentTemplate() == null) {
            entity.setDocumentTemplate(new Template());
        }
    }

}