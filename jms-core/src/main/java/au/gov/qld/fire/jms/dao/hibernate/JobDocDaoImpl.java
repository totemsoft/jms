package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.DocumentDao;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.JobDocDao;
import au.gov.qld.fire.jms.domain.job.Job;
import au.gov.qld.fire.jms.domain.job.JobDoc;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobDocDaoImpl extends BaseDaoImpl<JobDoc> implements JobDocDao
{

	@Autowired private DocumentDao documentDao;

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(JobDoc entity) throws DaoException {
        try
        {
        	documentDao.saveOrUpdate(entity.getDocument());
        	super.saveOrUpdate(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.JobDocDao#findByJob(au.gov.qld.fire.jms.domain.job.Job)
     */
    @SuppressWarnings("unchecked")
    public List<JobDoc> findByJob(Job job) throws DaoException
    {
        try
        {
            Query qry = getSession().getNamedQuery("jobDoc.findByJob");
            qry.setParameter("job", job);
            return (List<JobDoc>) qry.list();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}