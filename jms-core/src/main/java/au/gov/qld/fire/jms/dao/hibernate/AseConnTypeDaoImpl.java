package au.gov.qld.fire.jms.dao.hibernate;

import org.hibernate.Query;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.AseConnTypeDao;
import au.gov.qld.fire.jms.domain.refdata.AseConnType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseConnTypeDaoImpl extends BaseDaoImpl<AseConnType> implements AseConnTypeDao
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllOrderBy()
	 */
	@Override
	protected String findAllOrderBy() {
		return "ORDER BY name";
	}

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.AseConnTypeDao#findByName(java.lang.String)
     */
    public AseConnType findByName(String name) throws DaoException
    {
        try
        {
            Query qry = getSession().getNamedQuery("aseConnType.findByName");
            qry.setString("name", name);
            return (AseConnType) qry.uniqueResult();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}