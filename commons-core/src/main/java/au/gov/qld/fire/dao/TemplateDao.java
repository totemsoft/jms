package au.gov.qld.fire.dao;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.refdata.TemplateType;
import au.gov.qld.fire.domain.document.TemplateEnum;

/*
 * Template DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface TemplateDao extends BaseDao<Template>
{

    /**
     * Find entities by templateType.
     * @param templateType
     * @return
     * @throws DaoException
     */
    List<Template> findByTemplateType(TemplateType templateType) throws DaoException;

    /**
     * 
     * @param templateType
     * @param templateCode
     * @return
     * @throws DaoException
     */
    Template findByTemplateTypeCode(TemplateType templateType, String templateCode)
        throws DaoException;

    /**
     * 
     * @param templateEnum
     * @return
     * @throws DaoException
     */
    Template getTemplate(TemplateEnum templateEnum) throws DaoException;

    /**
     * 
     * @param templateEnum
     * @return
     * @throws DaoException
     */
    InputStream getTemplateContent(TemplateEnum templateEnum) throws DaoException;

}