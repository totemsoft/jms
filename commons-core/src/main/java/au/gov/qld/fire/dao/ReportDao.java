package au.gov.qld.fire.dao;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.domain.report.ReportSearchCriteria;

/*
 * Report DAO.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface ReportDao
{

    /**
     * 
     * @param query
     * @return
     * @throws DaoException
     */
    String[] getNamedParameters(String query) throws DaoException;

    /**
     * 
     * @param query Named query or SQL
     * @param criteria
     * @return
     * @throws DaoException
     */
    List<Map<String, Object>> getReportData(String query, ReportSearchCriteria criteria)
        throws DaoException;

    /**
     * 
     * @param reportCode Report identifier, eg "R001"
     * @param query Named query or SQL
     * @param criteria
     * @param xmlResult
     * @throws DaoException
     */
    void getReportData(String reportCode, String query, ReportSearchCriteria criteria, OutputStream xmlResult)
        throws DaoException;

}