package au.gov.qld.fire.domain.supplier;

import au.gov.qld.fire.domain.SearchCriteria;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SupplierSearchCriteria extends SearchCriteria
{

    /** serialVersionUID */
	private static final long serialVersionUID = -9181717802224628764L;

	private boolean ase;

	/** all, active, inactive */
    private Boolean active;

    private String supplierName;

    private String supplierContact;

    private String supplierPhone;

    /**
	 * @return the ase
	 */
	public boolean isAse()
	{
		return ase;
	}

	/**
	 * @param ase the ase to set
	 */
	public void setAse(boolean ase)
	{
		this.ase = ase;
	}

	/**
     * @return the active
     */
    public Boolean getActive()
    {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Boolean active)
    {
        this.active = active;
    }

    /**
     * @return the supplierName
     */
    public String getSupplierName()
    {
        return supplierName;
    }

    /**
     * @param supplierName the supplierName to set
     */
    public void setSupplierName(String supplierName)
    {
        this.supplierName = supplierName;
    }

    /**
     * @return the supplierContact
     */
    public String getSupplierContact()
    {
        return supplierContact;
    }

    /**
     * @param supplierContact the supplierContact to set
     */
    public void setSupplierContact(String supplierContact)
    {
        this.supplierContact = supplierContact;
    }

    /**
     * @return the supplierPhone
     */
    public String getSupplierPhone()
    {
        return supplierPhone;
    }

    /**
     * @param supplierPhone the supplierPhone to set
     */
    public void setSupplierPhone(String supplierPhone)
    {
        this.supplierPhone = supplierPhone;
    }

}