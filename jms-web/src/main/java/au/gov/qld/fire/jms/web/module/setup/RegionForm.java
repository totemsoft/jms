package au.gov.qld.fire.jms.web.module.setup;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.location.Region;
import au.gov.qld.fire.domain.location.State;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class RegionForm extends AbstractValidatorForm<Region>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -2544003610920300535L;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        super.reset(mapping, request);
        setEntity(new Region());
        //set default state (if empty)
        if (getEntity().getAddress().getState().getId() == null)
        {
            String stateDefault = WebUtils.getStateDefault(request.getSession());
            getEntity().getAddress().setState(new State(stateDefault));
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(Region entity)
    {
        super.setEntity(entity);
        //optional
        if (entity.getContact() == null)
        {
            entity.setContact(new Contact());
        }
        if (entity.getAddress() == null)
        {
            entity.setAddress(new Address());
        }
    }

    /**
     * Required for JSP.
     * @return
     */
    public Contact getContact()
    {
        return getEntity().getContact();
    }

    /**
     * Required for JSP.
     * @return
     */
    public Address getAddress()
    {
        return getEntity().getAddress();
    }

}