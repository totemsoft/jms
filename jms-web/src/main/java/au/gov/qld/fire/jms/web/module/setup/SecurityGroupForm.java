package au.gov.qld.fire.jms.web.module.setup;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;
import au.gov.qld.fire.domain.security.SecurityGroup;
import au.gov.qld.fire.domain.security.SystemFunction;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SecurityGroupForm extends AbstractValidatorForm<SecurityGroup>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -6506066002955615873L;

    /** systemFunctionIds */
    private Long[] systemFunctionIds;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(SecurityGroup entity)
    {
        super.setEntity(entity);
        //optional initialisation
        List<Long> ids = new ArrayList<Long>();
        CollectionUtils.collect(entity.getSystemFunctions(), new Transformer()
        {
            /* (non-Javadoc)
             * @see org.apache.commons.collections.Transformer#transform(java.lang.Object)
             */
            public Object transform(Object obj)
            {
                SystemFunction systemFunction = (SystemFunction) obj;
                return systemFunction.getSystemFunctionId();
            }
        }, ids);
        setSystemFunctionIds(ids.toArray(new Long[0]));
    }

    /**
     * @return the systemFunctionIds
     */
    public Long[] getSystemFunctionIds()
    {
        if (systemFunctionIds == null)
        {
            systemFunctionIds = new Long[0];
        }
        return systemFunctionIds;
    }

    /**
     * @param systemFunctionIds the systemFunctionIds to set
     */
    public void setSystemFunctionIds(Long[] systemFunctionIds)
    {
        this.systemFunctionIds = systemFunctionIds;
    }

}