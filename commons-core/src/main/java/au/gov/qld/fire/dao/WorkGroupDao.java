package au.gov.qld.fire.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.domain.refdata.WorkGroup;

/**
 * WorkGroup DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface WorkGroupDao extends BaseDao<WorkGroup>
{

    /**
     * 
     * @param workGroup
     * @return
     * @throws DaoException
     */
    List<String> findWorkGroup(String workGroup) throws DaoException;

}