package au.gov.qld.fire.dao.hibernate;

import java.util.List;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.StaffLeaveDao;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.domain.user.StaffLeave;
import au.gov.qld.fire.domain.user.StaffLeaveSearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class StaffLeaveDaoImpl extends BaseDaoImpl<StaffLeave> implements StaffLeaveDao
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.StaffLeaveDao#findByCriteria(au.gov.qld.fire.domain.user.StaffLeaveSearchCriteria)
	 */
    @SuppressWarnings("unchecked")
	public List<StaffLeave> findByCriteria(StaffLeaveSearchCriteria criteria) throws DaoException
	{
        try
        {
        	return getEntityManager().createQuery(
            	"FROM StaffLeave WHERE user = :user AND date = :date AND (:status IS NULL OR status = :status)")
            	.setParameter("user", criteria.getUser())
            	.setParameter("date", criteria.getDate())
            	.setParameter("status", criteria.getStatus() == null ? null : criteria.getStatus().ordinal())
            	.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.StaffLeaveDao#findLastLeaveGroupId(au.gov.qld.fire.domain.security.User)
	 */
	public Long findLastLeaveGroupId(User user) throws DaoException
	{
        try
        {
        	Number result = (Number) getEntityManager().createQuery(
            	"SELECT MAX(leaveGroupId) FROM StaffLeave WHERE user.id = :userId")
            	.setParameter("userId", user.getId())
            	.getSingleResult();
        	return result == null ? null : result.longValue();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

}