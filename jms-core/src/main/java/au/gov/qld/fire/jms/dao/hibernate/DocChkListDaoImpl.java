package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import org.hibernate.Query;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.jms.dao.DocChkListDao;
import au.gov.qld.fire.jms.domain.document.DocChkList;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class DocChkListDaoImpl extends BaseDaoImpl<DocChkList> implements DocChkListDao
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.DocChkListDao#findByTemplate(au.gov.qld.fire.jms.domain.document.Template)
     */
    @SuppressWarnings("unchecked")
    public List<DocChkList> findByTemplate(Template template) throws DaoException
    {
        try
        {
            Query qry = getSession().getNamedQuery("docChkList.findByTemplate");
            qry.setParameter("template", template);
            return (List<DocChkList>) qry.list();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}