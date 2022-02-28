package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.ase.AseFile;
import au.gov.qld.fire.jms.domain.ase.SubPanel;

/**
 * SubPanel DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface SubPanelDao extends BaseDao<SubPanel>
{

    /**
     * Find entities by aseFile.
     * @param aseFile
     * @return
     * @throws DaoException
     */
    List<SubPanel> findByAseFile(AseFile aseFile) throws DaoException;

}