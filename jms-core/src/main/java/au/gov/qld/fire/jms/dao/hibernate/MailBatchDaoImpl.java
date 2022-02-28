package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import javax.persistence.NoResultException;

import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.MailBatchDao;
import au.gov.qld.fire.jms.domain.action.MailSearchCriteria;
import au.gov.qld.fire.jms.domain.mail.MailBatch;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class MailBatchDaoImpl extends BaseDaoImpl<MailBatch> implements MailBatchDao
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.MailBatchDao#findByName(java.lang.String)
     */
    public MailBatch findByName(String name)
    {
        try
        {
            return (MailBatch) getEntityManager()
                .createNamedQuery("mailBatch.findByName")
                .setParameter("name", name)
                .getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.MailBatchDao#findByCriteria(au.gov.qld.fire.jms.domain.action.MailOutSearchCriteria)
     */
    @SuppressWarnings("unchecked")
    public List<MailBatch> findByCriteria(MailSearchCriteria criteria)
    {
        return getEntityManager()
            .createNamedQuery("mailBatch.findByCriteria")
            // TODO: use same criteria parameters
            .getResultList();
    }

}