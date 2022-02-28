package au.gov.qld.fire.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.domain.user.StaffLeave;
import au.gov.qld.fire.domain.user.StaffLeaveSearchCriteria;

/**
 * User DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface StaffLeaveDao extends BaseDao<StaffLeave>
{

    /**
     * Finder:
     * @return
     * @throws DaoException
     */
    List<StaffLeave> findByCriteria(StaffLeaveSearchCriteria criteria) throws DaoException;

    /**
     * 
     * @param user
     * @return
     * @throws DaoException
     */
	Long findLastLeaveGroupId(User user) throws DaoException;

}