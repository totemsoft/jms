package au.gov.qld.fire.jms.web.module;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class AbstractValidatorForm<T> extends ValidatorForm
{

    /** serialVersionUID */
    private static final long serialVersionUID = -848251306599529464L;

    /** logger */
    protected final Logger LOG = Logger.getLogger(getClass());

    /** Override default validation key (form name) */
    private static final String VALIDATION_KEY = "validationKey";

    /** backend entity */
    private T entity;

    /* (non-Javadoc)
     * @see org.apache.struts.validator.ValidatorForm#getValidationKey(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public String getValidationKey(ActionMapping mapping, HttpServletRequest request)
    {
        if (mapping.getProperty(VALIDATION_KEY) != null)
        {
            return mapping.getProperty(VALIDATION_KEY);
        }
        return super.getValidationKey(mapping, request);
    }

    /* (non-Javadoc)
     * @see org.apache.struts.validator.ValidatorForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        super.reset(mapping, request);
        //DO NOT RESET TO null!
        //entity = null;
    }

    /**
     * @return the user
     */
    public T getEntity()
    {
        return entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(T entity)
    {
        this.entity = entity;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("entity", getEntity()).toString();
    }

}