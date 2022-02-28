package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import org.hibernate.Query;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.FileDocChkListDao;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.file.FileDocChkList;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FileDocChkListDaoImpl extends BaseDaoImpl<FileDocChkList> implements FileDocChkListDao
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.FileDocChkListDao#findByFile(au.gov.qld.fire.jms.domain.file.File)
     */
    @SuppressWarnings("unchecked")
    public List<FileDocChkList> findByFile(File file) throws DaoException
    {
        try
        {
            Query qry = getSession().getNamedQuery("fileDocChkList.findByFile");
            qry.setParameter("file", file);
            return (List<FileDocChkList>) qry.list();
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
    public void delete(FileDocChkList entity) throws DaoException
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