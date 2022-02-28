package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.mail.MailRegister;
import au.gov.qld.fire.jms.domain.mail.MailRegisterSearchCriteria;

/**
 * MailRegister DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface MailRegisterDao extends BaseDao<MailRegister>
{

    /**
     * 
     * @param criteria
     * @return
     * @throws DaoException
     */
	List<MailRegister> findByCriteria(MailRegisterSearchCriteria criteria) throws DaoException;

}