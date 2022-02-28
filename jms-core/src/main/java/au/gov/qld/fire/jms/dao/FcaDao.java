package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.domain.BasePair;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.fca.FcaSearchCriteria;

/**
 * Fca DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface FcaDao extends BaseDao<Fca>
{

	/**
	 * 
	 * @param fcaNo
	 * @return
	 * @throws DaoException
	 */
    Fca findById(String fcaNo) throws DaoException;

    /**
     * 
     * @param fcaNoLike
     * @param unassignedFca
     * @return
     * @throws DaoException
     */
    List<BasePair> findFca(String fcaNoLike, boolean unassignedFca) throws DaoException;

    /**
     * 
     * @param criteria
     * @return
     * @throws DaoException
     */
    List<Fca> findByCriteria(FcaSearchCriteria criteria) throws DaoException;

    /**
     * 
     * @param fileId
     * @return
     * @throws DaoException
     */
    List<Fca> findByFileId(Long fileId) throws DaoException;

    /**
     * 
     * @param fileId
     * @return
     * @throws DaoException
     */
    int clearFileId(Long fileId) throws DaoException;

}