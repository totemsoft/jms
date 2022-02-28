package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.entity.Owner;
import au.gov.qld.fire.jms.domain.refdata.OwnerTypeEnum;

/**
 * Owner DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface OwnerDao extends BaseDao<Owner>
{

    /**
     * Find entity by legalName.
     * @param legalName
     * @return
     * @throws DaoException
     */
    Owner findByLegalName(String legalName) throws DaoException;
    /**
     * 
     * @param ownerType - optional
     * @param legalName - like 'legalName%'
     * @return
     * @throws DaoException
     */
    List<Owner> findByLegalNameLike(OwnerTypeEnum ownerType, String legalName) throws DaoException;
    /**
     * 
     * @param ownerType - optional
     * @param contactName - like 'firstName% surname%'
     * @return
     * @throws DaoException
     */
	List<Owner> findByContactName(OwnerTypeEnum ownerType, String contactName) throws DaoException;

    /**
     * 
     * @param legalName
     * @return
     * @throws DaoException
     */
    List<String> findLegalName(String legalName) throws DaoException;

    /**
     * 
     * @param contactSurname
     * @return
     * @throws DaoException
     */
    List<String> findContactSurname(String contactSurname) throws DaoException;

}