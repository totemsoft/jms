package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import org.hibernate.Query;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.AlarmPanelDao;
import au.gov.qld.fire.jms.domain.ase.AlarmPanel;
import au.gov.qld.fire.jms.domain.file.File;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AlarmPanelDaoImpl extends BaseDaoImpl<AlarmPanel> implements AlarmPanelDao
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.AlarmPanelDao#findByFile(au.gov.qld.fire.jms.domain.file.File)
     */
    @SuppressWarnings("unchecked")
    public List<AlarmPanel> findByFile(File file) throws DaoException
    {
        try
        {
            Query qry = getSession().getNamedQuery("alarmPanel.findByFile");
            qry.setParameter("file", file);
            return (List<AlarmPanel>) qry.list();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}