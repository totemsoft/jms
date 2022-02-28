package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.ase.AlarmPanel;
import au.gov.qld.fire.jms.domain.file.File;

/**
 * AlarmPanel DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface AlarmPanelDao extends BaseDao<AlarmPanel>
{

    /**
     * Find entities by file.
     * @param file
     * @return
     * @throws DaoException
     */
    List<AlarmPanel> findByFile(File file) throws DaoException;

}