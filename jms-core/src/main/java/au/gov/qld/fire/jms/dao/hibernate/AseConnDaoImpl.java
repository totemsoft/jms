package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import org.hibernate.Query;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.AseConnDao;
import au.gov.qld.fire.jms.domain.ase.AseConn;
import au.gov.qld.fire.jms.domain.refdata.AseConnType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseConnDaoImpl extends BaseDaoImpl<AseConn> implements AseConnDao
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.AseConnDao#findByAseConnType(au.gov.qld.fire.jms.domain.refdata.AseConnType)
     */
    @SuppressWarnings("unchecked")
    public List<AseConn> findByAseConnType(AseConnType aseConnType) throws DaoException
    {
        try
        {
            Query qry = getSession().getNamedQuery("aseConn.findByAseConnType");
            qry.setParameter("aseConnType", aseConnType);
            return (List<AseConn>) qry.list();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}