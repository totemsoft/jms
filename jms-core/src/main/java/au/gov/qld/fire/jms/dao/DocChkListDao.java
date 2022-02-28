package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.jms.domain.document.DocChkList;

/**
 * DocChkList DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface DocChkListDao extends BaseDao<DocChkList>
{

    /**
     * Finder:
     * @return
     * @throws DaoException
     */
    public List<DocChkList> findAllActive() throws DaoException;

    /**
     * Find entities by template.
     * @param template
     * @return
     * @throws DaoException
     */
    List<DocChkList> findByTemplate(Template template) throws DaoException;

}