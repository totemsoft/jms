package au.gov.qld.fire.jms.web.module.setup;

import au.gov.qld.fire.jms.domain.refdata.BuildingClassification;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class BuildingClassificationForm extends AbstractValidatorForm<BuildingClassification>
{

	/** serialVersionUID */
	private static final long serialVersionUID = 8696123648000973206L;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(BuildingClassification entity)
    {
        super.setEntity(entity);
        //optional
    }

}