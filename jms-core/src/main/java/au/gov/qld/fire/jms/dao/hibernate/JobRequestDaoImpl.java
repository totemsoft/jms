package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.JobRequestDao;
import au.gov.qld.fire.jms.domain.job.ActiveJobRequest;
import au.gov.qld.fire.jms.domain.job.JobRequest;
import au.gov.qld.fire.jms.domain.job.JobRequestSearchCriteria;
import au.gov.qld.fire.jms.domain.refdata.JobType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobRequestDaoImpl extends BaseDaoImpl<JobRequest> implements JobRequestDao
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.JobRequestDao#findByJobType(au.gov.qld.fire.jms.domain.refdata.JobType)
     */
    @SuppressWarnings("unchecked")
    public List<JobRequest> findByJobType(JobType jobType) throws DaoException
    {
        try
        {
        	return getEntityManager()
        	    .createNamedQuery("jobRequest.findByJobType")
                .setParameter("jobType", jobType)
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.JobRequestDao#findJobRequestNo(java.lang.String)
     */
    public List<String> findJobRequestNo(String jobRequestNo) throws DaoException
    {
        return findValueLike("jobRequest.findJobRequestNo", jobRequestNo);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.JobRequestDao#findActiveJobRequestByCriteria(au.gov.qld.fire.jms.domain.job.JobRequestSearchCriteria)
     */
    @SuppressWarnings("unchecked")
    public List<ActiveJobRequest> findActiveJobRequestByCriteria(JobRequestSearchCriteria criteria)
        throws DaoException
    {
        try
        {
        	return getEntityManager()
        	    .createNamedQuery("jobRequest.findActiveJobRequestByCriteria")
                .setMaxResults(criteria.getMaxResults())
                .setParameter("jobRequestNo", getValueLike(criteria.getJobRequestNo()))
                .setParameter("jobType", getValueLike(criteria.getJobType()))
                .setParameter("jobTypeId", criteria.getJobTypeId())
                .setParameter("workGroup", getValueLike(criteria.getWorkGroup()))
                .setParameter("workGroupId", criteria.getWorkGroupId())
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}