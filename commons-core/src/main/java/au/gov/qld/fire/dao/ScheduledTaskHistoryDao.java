package au.gov.qld.fire.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.domain.task.ScheduledTaskHistory;
import au.gov.qld.fire.domain.task.ScheduledTaskHistorySearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface ScheduledTaskHistoryDao extends BaseDao<ScheduledTaskHistory>
{

	/**
	 * 
	 * @param criteria
	 * @return
	 * @throws DaoException
	 */
	List<ScheduledTaskHistory> findByCriteria(ScheduledTaskHistorySearchCriteria criteria)
		throws DaoException;

}