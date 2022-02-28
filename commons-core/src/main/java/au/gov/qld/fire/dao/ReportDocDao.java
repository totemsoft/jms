package au.gov.qld.fire.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.report.ReportDoc;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;


/**
 * ReportDoc DAO.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface ReportDocDao extends BaseDao<ReportDoc>
{

    /**
     * Find entities by template, user.
     * @param template
     * @param user
     * @return
     * @throws DaoException
     */
    List<ReportDoc> findByTemplateUser(Template template, User user) throws DaoException;

}