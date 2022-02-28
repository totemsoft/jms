package au.gov.qld.fire.dao.hibernate;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.util.ThreadLocalUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class HibernateInterceptor extends EmptyInterceptor
{

    /** serialVersionUID */
    private static final long serialVersionUID = 6119507066934549278L;

    /** logger */
    //private static final Logger LOG = Logger.getLogger(HibernateInterceptor.class);

    /* (non-Javadoc)
     * @see org.hibernate.EmptyInterceptor#onFlushDirty(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
     */
    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState,
        Object[] previousState, String[] propertyNames, Type[] types)
    {
        //if (LOG.isDebugEnabled())
        //    LOG.debug(Arrays.asList(propertyNames));
        //if (LOG.isDebugEnabled())
        //    LOG.debug(Arrays.asList(currentState));
        //if (LOG.isDebugEnabled())
        //    LOG.debug(Arrays.asList(types));
        if (entity instanceof Auditable)
        {
            return updateAuditable((Auditable) entity, propertyNames, currentState);
        }
        return false;//super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }

    /* (non-Javadoc)
     * @see org.hibernate.EmptyInterceptor#onSave(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
     */
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames,
        Type[] types)
    {
        if (entity instanceof Auditable)
        {
            return updateAuditable((Auditable) entity, propertyNames, state);
        }
        return false;//super.onSave(entity, id, state, propertyNames, types);
    }

    /**
     * Sets the Auditable object.
     * @param obj The object on which to ensure the audit is set.
     * @param propertyNames The Property names of the entity.
     * @param currentState The current state of the entity.
     * @return true if modified.
     */
    @SuppressWarnings("unchecked")
    private boolean updateAuditable(Auditable obj, String[] propertyNames, Object[] currentState)
    {
        final User user = ThreadLocalUtils.getUser();
        final Date date = ThreadLocalUtils.getDate();
        if (user == null && date == null)
        {
            return false; // some automatic process/task
        }
        for (int i = 0; i < propertyNames.length; i++)
        {
            if (propertyNames[i].equals(Auditable.MODIFIED_BY))
            {
                currentState[i] = user;
            }
            else if (propertyNames[i].equals(Auditable.MODIFIED_DATE))
            {
                currentState[i] = date;
            }
            else if (propertyNames[i].equals(Auditable.CREATED_BY) && currentState[i] == null)
            {
                currentState[i] = user;
            }
            else if (propertyNames[i].equals(Auditable.CREATED_DATE) && currentState[i] == null)
            {
                currentState[i] = date;
            }
        }
        return true;
    }

}