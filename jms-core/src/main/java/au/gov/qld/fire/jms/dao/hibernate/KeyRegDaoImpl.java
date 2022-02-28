package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import org.hibernate.Query;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.KeyRegDao;
import au.gov.qld.fire.jms.domain.KeyReg;
import au.gov.qld.fire.jms.domain.file.File;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class KeyRegDaoImpl extends BaseDaoImpl<KeyReg> implements KeyRegDao
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.KeyRegDao#findByFile(au.gov.qld.fire.jms.domain.file.File)
     */
    @SuppressWarnings("unchecked")
    public List<KeyReg> findByFile(File file) throws DaoException
    {
        try
        {
            Query qry = getSession().getNamedQuery("keyReg.findByFile");
            qry.setParameter("file", file);
            return (List<KeyReg>) qry.list();
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
    public void delete(KeyReg entity) throws DaoException
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