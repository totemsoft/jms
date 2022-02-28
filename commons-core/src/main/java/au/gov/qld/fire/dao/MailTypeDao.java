package au.gov.qld.fire.dao;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.domain.refdata.MailType;

/**
 * MailType DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface MailTypeDao extends BaseDao<MailType>
{

	MailType findByName(String name) throws DaoException;

}