package au.gov.qld.fire.dao.hibernate;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import au.gov.qld.fire.dao.AddressDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.domain.location.Address;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AddressDaoImpl extends BaseDaoImpl<Address> implements AddressDao
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.AddressDao#findByAddrLinePostcodeSuburb(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<Address> findByAddrLinePostcodeSuburb(String addrLine1, String addrLine2,
        String postcode, String suburb, String state) throws DaoException
    {
        try
        {
            Query qry = getSession().getNamedQuery("address.findByAddrLinePostcodeSuburb");
            qry.setString("addrLine1", StringUtils.isBlank(addrLine1) ? null : addrLine1);
            qry.setString("addrLine2", StringUtils.isBlank(addrLine2) ? null : addrLine2);
            qry.setString("postcode", StringUtils.isBlank(postcode) ? null : postcode);
            qry.setString("suburb", StringUtils.isBlank(suburb) ? null : suburb);
            qry.setString("state", StringUtils.isBlank(state) ? null : state);
            return (List<Address>) qry.list();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}