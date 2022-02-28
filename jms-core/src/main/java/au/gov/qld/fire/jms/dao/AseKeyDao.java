package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.ase.AseKey;
import au.gov.qld.fire.jms.domain.ase.AseKeySearchCriteria;

/**
 * AseKey DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface AseKeyDao extends BaseDao<AseKey>
{
	/**
	 * 
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	AseKey findById(Long id) throws DaoException;

    /**
     * 
     * @param criteria
     * @return
     * @throws DaoException
     */
	List<AseKey> findByCriteria(AseKeySearchCriteria criteria) throws DaoException;

	/**
	 * 
	 * @param aseKeyNo
	 * @return
	 * @throws DaoException
	 */
	List<String> findAseKeyNo(String aseKeyNo) throws DaoException;

}