package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import org.hibernate.Query;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.RfiDao;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.sap.Rfi;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class RfiDaoImpl extends BaseDaoImpl<Rfi> implements RfiDao
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.RfiDao#findByFile(au.gov.qld.fire.jms.domain.file.File)
     */
    @SuppressWarnings("unchecked")
    public List<Rfi> findByFile(File file) throws DaoException
    {
        try
        {
            Query qry = getSession().getNamedQuery("rfi.findByFile");
            qry.setParameter("file", file);
            return (List<Rfi>) qry.list();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.hibernate.BaseDaoImpl#delete(java.lang.Object)
     */
    @Override
    public void delete(Rfi entity) throws DaoException
    {
        if (entity == null)
        {
            return;
        }
        try
        {
        	getSession().delete(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}