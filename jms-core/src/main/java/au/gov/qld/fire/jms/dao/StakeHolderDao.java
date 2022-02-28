package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.domain.location.Region;
import au.gov.qld.fire.jms.domain.entity.StakeHolder;
import au.gov.qld.fire.jms.domain.refdata.StakeHolderType;

/**
 * StakeHolder DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface StakeHolderDao extends BaseDao<StakeHolder>
{

    /**
     * Find entities by stakeHolderType.
     * @param stakeHolderType
     * @return
     * @throws DaoException
     */
    List<StakeHolder> findByStakeHolderType(StakeHolderType stakeHolderType) throws DaoException;

    /**
     * 
     * @param region
     * @return
     * @throws DaoException
     */
    List<StakeHolder> findByRegion(Region region) throws DaoException;

}