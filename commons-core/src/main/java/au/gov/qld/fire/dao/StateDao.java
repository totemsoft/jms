package au.gov.qld.fire.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.domain.location.State;

/**
 * State DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface StateDao extends BaseDao<State>
{

    /**
     * 
     * @return
     * @throws DaoException
     */
    List<State> findDefault() throws DaoException;

}