package au.gov.qld.fire.jms.web.module.setup;

import au.gov.qld.fire.domain.task.ScheduledTask;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ScheduledTaskForm extends AbstractUploadForm<ScheduledTask>
{

	/** serialVersionUID */
	private static final long serialVersionUID = 2340882284120130526L;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(ScheduledTask entity)
    {
        super.setEntity(entity);
    }

}