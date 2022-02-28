package au.gov.qld.fire.dao.hibernate;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.DocumentDao;
import au.gov.qld.fire.domain.document.Document;

/**
 * Document DAO implementation.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class DocumentDaoImpl extends BaseDaoImpl<Document> implements DocumentDao
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#save(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(Document entity) throws DaoException {
		// mandatory content
		if (entity != null && entity.getContent() != null)
		{
			super.saveOrUpdate(entity);
		}
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.hibernate.BaseDaoImpl#delete(java.lang.Object)
     */
    @Override
    public void delete(Document entity) throws DaoException
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