package au.gov.qld.fire.dao.hibernate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.hibernate.Query;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.TemplateDao;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.document.TemplateEnum;
import au.gov.qld.fire.domain.refdata.TemplateType;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class TemplateDaoImpl extends BaseDaoImpl<Template> implements TemplateDao
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllOrderBy()
	 */
	@Override
	protected String findAllOrderBy() {
		return "ORDER BY templateType.name, name";
	}

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.TemplateDao#findByTemplateType(au.gov.qld.fire.jms.domain.refdata.TemplateType)
     */
    @SuppressWarnings("unchecked")
    public List<Template> findByTemplateType(TemplateType templateType) throws DaoException
    {
        try
        {
            Query qry = getSession().getNamedQuery("template.findByTemplateType");
            qry.setParameter("templateType", templateType);
            return (List<Template>) qry.list();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.TemplateDao#findByTemplateTypeCode(au.gov.qld.fire.jms.domain.refdata.TemplateType, java.lang.String)
     */
    public Template findByTemplateTypeCode(TemplateType templateType, String templateCode)
        throws DaoException
    {
        try
        {
            Query qry = getSession().getNamedQuery("template.findByTemplateTypeCode");
            qry.setParameter("templateType", templateType);
            qry.setParameter("templateCode", templateCode);
            return (Template) qry.uniqueResult();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.TemplateDao#getTemplate(au.gov.qld.fire.jms.domain.document.TemplateEnum)
     */
    public Template getTemplate(TemplateEnum templateEnum) throws DaoException
    {
        try
        {
            String templateCode = templateEnum.getCode();
            return findByTemplateTypeCode(new TemplateType(templateEnum.getTemplateType().getId()),
                templateCode);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.TemplateDao#getTemplateContent(au.gov.qld.fire.jms.domain.document.TemplateEnum)
     */
    public InputStream getTemplateContent(TemplateEnum templateEnum) throws DaoException
    {
        try
        {
            String templateCode = templateEnum.getCode();
            Template template = findByTemplateTypeCode(new TemplateType(templateEnum
                .getTemplateType().getId()), templateCode);
            if (template != null)
            {
                return new ByteArrayInputStream(template.getContent());
            }
            if (templateEnum.getPath() == null)
            {
            	throw new DaoException("No default template with code '" + templateCode + "' found.");
            }

            LOG.warn("No template with code '" + templateCode + "', using default one.");
            URL templateUrl = getClass().getResource(templateEnum.getPath());
            return templateUrl.openStream();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}