package au.gov.qld.fire.jms.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.DocumentDao;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.FileDao;
import au.gov.qld.fire.jms.dao.JobActionDao;
import au.gov.qld.fire.jms.domain.action.CallSearchCriteria;
import au.gov.qld.fire.jms.domain.action.JobAction;
import au.gov.qld.fire.jms.domain.action.JobActionTodo;
import au.gov.qld.fire.jms.domain.action.TodoSearchCriteria;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.job.Job;
import au.gov.qld.fire.jms.domain.job.JobSearchCriteria;
import au.gov.qld.fire.jms.domain.refdata.ActionType;
import au.gov.qld.fire.jms.domain.sap.SapHeader;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobActionDaoImpl extends BaseDaoImpl<JobAction> implements JobActionDao
{

	@Autowired private DocumentDao documentDao;

	@Autowired private FileDao fileDao;

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(JobAction entity) throws DaoException {
        try
        {
        	if (entity.getDocument() != null)
        	{
        		documentDao.saveOrUpdate(entity.getDocument());
        	}
        	super.saveOrUpdate(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.JobActionDao#findByJob(au.gov.qld.fire.jms.domain.job.Job)
     */
    @SuppressWarnings("unchecked")
    public List<JobAction> findByJob(Job job) throws DaoException
    {
        try
        {
        	return getEntityManager()
        		.createNamedQuery("jobAction.findByJob")
        		.setParameter("job", job)
        		.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.JobActionDao#findByFileActionType(au.gov.qld.fire.jms.domain.file.File, ActionType)
	 */
    @SuppressWarnings("unchecked")
	public List<JobAction> findByFileActionType(File file, ActionType actionType) throws DaoException
	{
        try
        {
        	return getEntityManager()
        		.createNamedQuery("jobAction.findByFileActionType")
        		.setParameter("file", file)
        		.setParameter("actionType", actionType == null || actionType.getId() == null ? null : actionType)
        		.getResultList();
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
    public void delete(JobAction entity) throws DaoException
    {
        if (entity == null)
        {
            return;
        }
        try
        {
            entity.setLogicallyDeleted(Boolean.TRUE);
            saveOrUpdate(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.JobActionDao#findJobActionTodoByDueDate(JobSearchCriteria, java.util.Date)
     */
    @SuppressWarnings("unchecked")
    public List<JobAction> findJobActionTodoByDueDate(JobSearchCriteria criteria, Date dueDate)
        throws DaoException
    {
        try
        {
            // join query (File, Fca, etc)
        	return getEntityManager()
        		.createNamedQuery("jobAction.findJobActionTodoByDueDate")
        		.setParameter("fileNo", getValueLike(criteria.getFileNo()))
        		.setParameter("fcaNo", getValueLike(criteria.getFcaNo()))
        		.setParameter("dueDate", dueDate)
        		.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.JobActionDao#findJobActionTodoByCriteria(au.gov.qld.fire.jms.domain.action.TodoSearchCriteria)
     */
    @SuppressWarnings("unchecked")
    public List<JobActionTodo> findJobActionToDoByCriteria(final TodoSearchCriteria criteria)
        throws DaoException
    {
        try
        {
            // join query (File, Fca, etc)
        	List<JobActionTodo> result = getEntityManager()
        	    .createNamedQuery("jobAction.findJobActionToDoByCriteria")
	            .setParameter("fileNo", getValueLike(criteria.getFileNo()))
	            .setParameter("fcaNo", getValueLike(criteria.getFcaNo()))
	            .setParameter("completed", criteria.getCompleted())
	            .setParameter("actionTypeId", criteria.getActionTypeId())
	            .setParameter("actionCodeId", criteria.getActionCodeId())
	            .setParameter("actionCode", getValueLike(criteria.getActionCode()))
	            .setParameter("workGroupId", criteria.getWorkGroupId())
	            .setParameter("workGroup", getValueLike(criteria.getWorkGroup()))
	            .setParameter("responsibleUserId", criteria.getResponsibleUserId())
	            .setMaxResults(criteria.getMaxResults())
	            .getResultList();
            // Calls UI require more details than ToDo UI
       		if (criteria instanceof CallSearchCriteria)
       		{
       			for (JobActionTodo item : result)
       			{
           			JobAction a = findById(item.getId());
           			SapHeader sapHeader = a.getFile().getSapHeader();
           			item.setSapCustNo(sapHeader == null ? null : sapHeader.getSapCustNo());
           			item.setCompletedBy(a.getCompletedBy() == null ? null : a.getCompletedBy().getContact().getName());
           			item.setCompletedDate(a.getCompletedDate());
           			item.setCreatedBy(a.getCreatedBy() == null ? null : a.getCreatedBy().getContact().getName());
           			item.setResponsibleUser(a.getResponsibleUser() == null ? null : a.getResponsibleUser().getContact().getName());
           			item.setDestination(a.getDestination());
           			item.setNotation(a.getNotation());
           			item.setContact(a.getContact());
       			}
       		}
            return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}