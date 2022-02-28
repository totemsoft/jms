package au.gov.qld.fire.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.domain.security.SecurityGroup;

/**
 * SecurityGroup DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface SecurityGroupDao extends BaseDao<SecurityGroup>
{

	/**
	 * 
	 * @param workGroupId
	 * @return
	 * @throws DaoException
	 */
	List<SecurityGroup> findByWorkGroupId(Long workGroupId) throws DaoException;

}