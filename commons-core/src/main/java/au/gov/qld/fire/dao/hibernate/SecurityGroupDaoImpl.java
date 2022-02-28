package au.gov.qld.fire.dao.hibernate;

import java.util.List;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.SecurityGroupDao;
import au.gov.qld.fire.domain.security.SecurityGroup;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SecurityGroupDaoImpl extends BaseDaoImpl<SecurityGroup> implements SecurityGroupDao
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllOrderBy()
	 */
	@Override
	protected String findAllOrderBy() {
		return "ORDER BY name";
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.SecurityGroupDao#findByWorkGroupId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<SecurityGroup> findByWorkGroupId(Long workGroupId) throws DaoException
	{
        try
        {
            return getEntityManager()
            	.createNamedQuery("securityGroup.findByWorkGroupId")
	            .setParameter("workGroupId", workGroupId)
	            .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

}