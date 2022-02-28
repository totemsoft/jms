package au.gov.qld.fire.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.domain.task.ScheduledTask;
import au.gov.qld.fire.domain.task.ScheduledTaskSearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface ScheduledTaskDao extends BaseDao<ScheduledTask>
{

	/**
	 * 
	 * @param criteria
	 * @return
	 * @throws DaoException
	 */
	List<ScheduledTask> findByCriteria(ScheduledTaskSearchCriteria criteria)
		throws DaoException;

}