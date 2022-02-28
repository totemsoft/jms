package au.gov.qld.fire.jms.web.module.supplier;

import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.location.Region;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SupplierForm extends AbstractValidatorForm<Supplier>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -5387253339839090385L;

    public static final String BEAN_NAME = "supplierForm";

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(Supplier entity)
    {
        super.setEntity(entity);
        if (getEntity() != null)
        {
            if (getEntity().getRegion() == null)
            {
                getEntity().setRegion(new Region());
            }
        }
    }

    /**
     * Required in JSP.
     * @return
     */
    public Contact getContact()
    {
        return getEntity().getContact();
    }

    /**
     * Required in JSP.
     * @return
     */
    public Address getAddress()
    {
        return getEntity().getAddress();
    }

    /**
     * Required in JSP.
     * @return
     */
    public Address getPostAddress()
    {
        return getEntity().getPostAddress();
    }
    
}