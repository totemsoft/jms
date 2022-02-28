package au.gov.qld.fire.dao.hibernate;

import javax.persistence.NoResultException;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.ScheduledTaskHistoryItemDao;
import au.gov.qld.fire.domain.task.ScheduledTaskHistoryItem;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ScheduledTaskHistoryItemDaoImpl extends BaseDaoImpl<ScheduledTaskHistoryItem> implements ScheduledTaskHistoryItemDao
{

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.ScheduledTaskHistoryItemDao#findByItemId(java.lang.String)
	 */
	public ScheduledTaskHistoryItem findByItemId(String itemId) throws DaoException
	{
		try
		{
			return (ScheduledTaskHistoryItem) getEntityManager()
				.createNamedQuery("scheduledTaskHistoryItem.findByItemId")
				.setParameter("itemId", itemId)
				.getSingleResult();
		}
		catch (NoResultException ignore)
		{
			return null;
		}
		catch (Exception e)
		{
			throw new DaoException(e.getMessage(), e);
		}
	}

}