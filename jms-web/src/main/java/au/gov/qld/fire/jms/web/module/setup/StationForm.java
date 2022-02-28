package au.gov.qld.fire.jms.web.module.setup;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.location.Area;
import au.gov.qld.fire.domain.location.State;
import au.gov.qld.fire.domain.location.Station;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class StationForm extends AbstractValidatorForm<Station>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 4221964748674328622L;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        super.reset(mapping, request);
        setEntity(new Station());
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
    public void setEntity(Station entity)
    {
        super.setEntity(entity);
        //optional
        if (entity.getContact().getId() == null)
        {
            entity.setContact(new Contact());
        }
        if (entity.getAddress() == null)
        {
            entity.setAddress(new Address());
        }
        if (entity.getArea() == null)
        {
            entity.setArea(new Area());
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

//    /**
//     * Required for JSP.
//     * @return
//     */
//    public Area getArea()
//    {
//        return getEntity().getArea();
//    }

}