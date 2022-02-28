package au.gov.qld.fire.dao.hibernate;

import java.util.List;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.ScheduledTaskDao;
import au.gov.qld.fire.domain.SearchCriteria;
import au.gov.qld.fire.domain.task.ScheduledTask;
import au.gov.qld.fire.domain.task.ScheduledTaskSearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ScheduledTaskDaoImpl extends BaseDaoImpl<ScheduledTask> implements ScheduledTaskDao
{

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.ScheduledTaskDao#findByCriteria(au.gov.qld.fire.domain.task.ScheduledTaskSearchCriteria)
	 */
	@SuppressWarnings("unchecked")
	public List<ScheduledTask> findByCriteria(ScheduledTaskSearchCriteria criteria) throws DaoException
	{
		try
		{
			return getEntityManager()
				.createNamedQuery("scheduledTask.findByCriteria")
        		.setMaxResults(criteria.getMaxResults() > 0 ? criteria.getMaxResults() : SearchCriteria.MAX)
				.setParameter("name", criteria.getTask() == null ? null : criteria.getTask().getName())
				.setParameter("active", criteria.getActive() == null ? null : criteria.getActive() ? 'Y' : 'N')
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
	@Override
	public void saveOrUpdate(ScheduledTask dto) throws DaoException
	{
		ScheduledTask entity = findById(dto.getId());
		if (entity == null)
		{
			super.save(dto);
		}
		else
		{
	        entity.setActive(dto.isActive());
	        entity.setRequest(dto.getRequest());
			super.saveOrUpdate(entity);
		}
	}

}