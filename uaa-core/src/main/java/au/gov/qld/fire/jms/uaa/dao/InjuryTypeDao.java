package au.gov.qld.fire.jms.uaa.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.uaa.domain.refdata.InjuryType;
import au.gov.qld.fire.service.ServiceException;

/**
 * InjuryType DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface InjuryTypeDao extends BaseDao<InjuryType>
{

	/**
	 * 
	 * @param name
	 * @return
	 * @throws ServiceException
	 */
	InjuryType findByName(String name) throws ServiceException;

	/**
	 * 
	 * @param criteria
	 * @return
	 * @throws DaoException
	 */
    List<InjuryType> findAll(Boolean multiple) throws DaoException;

}