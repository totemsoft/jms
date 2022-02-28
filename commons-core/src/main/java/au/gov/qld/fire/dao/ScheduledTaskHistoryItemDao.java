package au.gov.qld.fire.dao;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.domain.task.ScheduledTaskHistoryItem;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface ScheduledTaskHistoryItemDao extends BaseDao<ScheduledTaskHistoryItem>
{

	/**
	 * 
	 * @param criteria
	 * @return
	 * @throws DaoException
	 */
	ScheduledTaskHistoryItem findByItemId(String itemId)
		throws DaoException;

}