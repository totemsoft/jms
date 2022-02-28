package au.gov.qld.fire.dao.hibernate;

import java.util.List;

import org.hibernate.Query;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.StateDao;
import au.gov.qld.fire.domain.location.State;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class StateDaoImpl extends BaseDaoImpl<State> implements StateDao
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.StateDao#findDefault()
     */
    @SuppressWarnings("unchecked")
    public List<State> findDefault() throws DaoException
    {
        try
        {
            Query qry = getSession().getNamedQuery("state.findDefault");
            return qry.list();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}