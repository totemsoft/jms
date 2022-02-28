package au.gov.qld.fire.dao.hibernate;

import java.util.List;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.ScheduledTaskHistoryDao;
import au.gov.qld.fire.domain.SearchCriteria;
import au.gov.qld.fire.domain.task.ScheduledTaskHistory;
import au.gov.qld.fire.domain.task.ScheduledTaskHistorySearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ScheduledTaskHistoryDaoImpl extends BaseDaoImpl<ScheduledTaskHistory> implements ScheduledTaskHistoryDao
{

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.ScheduledTaskHistoryDao#findByCriteria(au.gov.qld.fire.domain.task.ScheduledTaskHistorySearchCriteria)
	 */
	@SuppressWarnings("unchecked")
	public List<ScheduledTaskHistory> findByCriteria(ScheduledTaskHistorySearchCriteria criteria) throws DaoException
	{
		try
		{
			return getEntityManager()
				.createNamedQuery("scheduledTaskHistory.findByCriteria")
        		.setMaxResults(criteria.getMaxResults() > 0 ? criteria.getMaxResults() : SearchCriteria.MAX)
				.setParameter("name", criteria.getTask() == null ? null : criteria.getTask().getName())
				.setParameter("status", criteria.getStatus() == null ? null : criteria.getStatus().getCode())
				.getResultList();
		}
		catch (Exception e)
		{
			throw new DaoException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#saveOrUpdate(au.gov.qld.fire.domain.BaseModel)
	 */
	public void saveOrUpdate(ScheduledTaskHistory dto) throws DaoException
	{
		ScheduledTaskHistory entity = findById(dto.getId());
		if (entity == null)
		{
			super.save(dto);
		}
		else
		{
	        entity.setStatus(dto.getStatus());
	        entity.setResponse(dto.getResponse());
			super.saveOrUpdate(entity);
		}
	}

}