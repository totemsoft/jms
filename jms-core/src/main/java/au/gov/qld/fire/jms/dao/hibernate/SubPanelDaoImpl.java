package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import org.hibernate.Query;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.SubPanelDao;
import au.gov.qld.fire.jms.domain.ase.AseFile;
import au.gov.qld.fire.jms.domain.ase.SubPanel;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SubPanelDaoImpl extends BaseDaoImpl<SubPanel> implements SubPanelDao
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.SubPanelDao#findByAseFile(au.gov.qld.fire.jms.domain.ase.AseFile)
     */
    @SuppressWarnings("unchecked")
    public List<SubPanel> findByAseFile(AseFile aseFile) throws DaoException
    {
        try
        {
            Query qry = getSession().getNamedQuery("subPanel.findByAseFile");
            qry.setParameter("aseFile", aseFile);
            return (List<SubPanel>) qry.list();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}