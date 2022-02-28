package au.gov.qld.fire.dao.hibernate;

import java.util.List;

import org.hibernate.Query;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.ReportDocDao;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.report.ReportDoc;
import au.gov.qld.fire.domain.security.User;

/**
 * ReportDoc DAO implementation.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ReportDocDaoImpl extends BaseDaoImpl<ReportDoc> implements ReportDocDao
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.ReportDocDao#findByTemplateUser(au.gov.qld.fire.jms.domain.document.Template, au.gov.qld.fire.jms.domain.security.User)
     */
    @SuppressWarnings("unchecked")
    public List<ReportDoc> findByTemplateUser(Template template, User user) throws DaoException
    {
        try
        {
            Query qry = getSession().getNamedQuery("reportDoc.findByTemplateUser");
            qry.setParameter("template", template);
            qry.setParameter("user", user);
            return (List<ReportDoc>) qry.list();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.hibernate.BaseDaoImpl#delete(java.lang.Object)
     */
    @Override
    public void delete(ReportDoc entity) throws DaoException
    {
        if (entity == null)
        {
            return;
        }
        try
        {
        	getSession().delete(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}