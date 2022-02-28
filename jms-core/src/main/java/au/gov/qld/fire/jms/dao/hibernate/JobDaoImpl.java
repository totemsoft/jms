package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.JobDao;
import au.gov.qld.fire.jms.domain.job.ActiveJob;
import au.gov.qld.fire.jms.domain.job.Job;
import au.gov.qld.fire.jms.domain.job.JobSearchCriteria;
import au.gov.qld.fire.jms.domain.refdata.JobType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobDaoImpl extends BaseDaoImpl<Job> implements JobDao
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.JobDao#findByJobType(au.gov.qld.fire.jms.domain.refdata.JobType)
     */
    @SuppressWarnings("unchecked")
    public List<Job> findByJobType(JobType jobType) throws DaoException
    {
        try
        {
        	return getEntityManager()
        	    .createNamedQuery("job.findByJobType")
                .setParameter("jobType", jobType)
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.JobDao#findJobNo(java.lang.String)
     */
    public List<String> findJobNo(String jobNo) throws DaoException
    {
        return findValueLike("job.findJobNo", jobNo);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.JobDao#findActiveJobByCriteria(au.gov.qld.fire.jms.domain.file.FileSearchCriteria)
     */
    @SuppressWarnings("unchecked")
    public List<ActiveJob> findActiveJobByCriteria(JobSearchCriteria criteria) throws DaoException
    {
        try
        {
            // join query (File, Fca, Building)
        	return getEntityManager().createNamedQuery("job.findActiveJobByCriteria")
                .setMaxResults(criteria.getMaxResults())
                .setParameter("fileNo", getValueLike(criteria.getFileNo()))
                .setParameter("fcaNo", getValueLike(criteria.getFcaNo()))
                .setParameter("jobNo", getValueLike(criteria.getJobNo()))
                .setParameter("buildingId", criteria.getBuildingId())
                .setParameter("buildingName", getValueLike(criteria.getBuildingName()))
                .setParameter("actionCodeId", criteria.getActionCodeId())
                .setParameter("actionCode", getValueLike(criteria.getActionCode()))
                .setParameter("workGroup", getValueLike(criteria.getWorkGroup()))
                .setParameter("workGroupId", criteria.getWorkGroupId())
                .setParameter("jobType", getValueLike(criteria.getJobType()))
                .setParameter("jobTypeId", criteria.getJobTypeId())
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}