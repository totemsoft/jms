package au.gov.qld.fire.dao.hibernate;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.ReportDao;
import au.gov.qld.fire.domain.report.ReportSearchCriteria;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.util.Formatter;
import au.gov.qld.fire.util.XmlUtils;

/*
 * Report DAO implementation.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ReportDaoImpl extends BaseDaoImpl implements ReportDao
{

    /** xmlRoot */
    private String xmlRoot;

    /**
     * @param xmlRoot the xmlRoot to set
     */
    public void setXmlRoot(String xmlRoot)
    {
        this.xmlRoot = xmlRoot;
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.ReportDao#getNamedParameters(java.lang.String)
     */
    public String[] getNamedParameters(String query) throws DaoException
    {
        try
        {
            Query qry = getQuery(query);
            return qry == null ? null : qry.getNamedParameters();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.ReportDao#getReportData(java.lang.String, au.gov.qld.fire.jms.domain.report.ReportSearchCriteria)
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getReportData(String query, ReportSearchCriteria criteria)
        throws DaoException
    {
        try
        {
            Query qry = getQuery(query);
            String[] parameters = qry.getNamedParameters();

            //ReportSearchCriteria property
            BeanMap criteriaProperties = new BeanMap(criteria);

            //get the value
            for (String name : parameters)
            {
                Object value = null;
                if (criteriaProperties.containsKey(name))
                {
                    value = criteriaProperties.get(name);
                }
                else
                {
                    value = criteria.getCustomParameters().get(name);
                }

                //check for null parameter value.
                if (value == null)
                {
                    qry.setParameter(name, null);
                    //throw new DaoException("Query '" + query + "' - <null> value for parameter '"
                    //    + name + "' found in ReportSearchCriteria.");
                }
                //
                else if (value instanceof String)
                {
                    if (StringUtils.isBlank((String) value))
                    {
                        value = null;
                    }
                    qry.setString(name, (String) value);
                }
                //active
                else if (value instanceof Character)
                {
                    if (value.equals(' '))
                    {
                        value = null;
                    }
                    qry.setParameter(name, value);
                }
                //fileId, workGroupId
                else if (value instanceof Number)
                {
                    if (((Number) value).doubleValue() == 0.)
                    {
                        value = null;
                    }
                    qry.setParameter(name, value);
                }
                //dateStart, dateEnd, YMnn
                else if (value instanceof Date)
                {
                    qry.setDate(name, (Date) value);
                }
                //id(s)
                else if (value.getClass().isArray())
                {
                    Long[] values = (Long[]) value;
                    if (values.length == 0)
                    {
                        values = new Long[]
                        {
                            0L
                        };
                    }
                    //Type type = org.hibernate.type.LongType.INSTANCE;
                    //Type type = org.hibernate.Hibernate.LONG;
                    final Type type = new LongType();
                    qry.setParameterList(name, Arrays.asList(values), type);
                }
                else
                {
                    //never get here
                    throw new DaoException("Query '" + query + "' - No parameter '" + name
                        + "' match found in ReportSearchCriteria. Value='" + value + "'.");
                }
            }
            //define a strategy for transforming criteria query results into the actual application-visible query result list.
            qry.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            return qry.list();
        }
        catch (DaoException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.ReportDao#getReportData(java.lang.String, java.lang.String, au.gov.qld.fire.jms.domain.report.ReportSearchCriteria, java.io.OutputStream)
     */
    public void getReportData(String reportCode, String query, ReportSearchCriteria criteria,
        OutputStream xmlResult) throws DaoException
    {
        try
        {
            xmlResult.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes());
            xmlResult.write(("<" + xmlRoot + " date=\""
                + Formatter.formatDate(DateUtils.getCurrentDate()) + "\">").getBytes());

            //add criteria
            xmlResult.write(("<Criteria ").getBytes());
            criteria.write(xmlResult);
            //Criteria ends
            xmlResult.write(("/>").getBytes());

            //add data
            List<Map<String, Object>> resultSet = getReportData(query, criteria);
            for (Map<String, Object> result : resultSet)
            {
                xmlResult.write(("<" + reportCode + " ").getBytes());
                Set<Entry<String, Object>> entrySet = result.entrySet();
                for (Entry<String, Object> entry : entrySet)
                {
                    Object value = entry.getValue();
                    String sValue;
                    if (value == null)
                    {
                        continue;
                    }
                    else if (value instanceof String)
                    {
                        sValue = XmlUtils.formatXML((String) value);
                    }
                    else if (value instanceof Date)
                    {
                        Date date = (Date) value;
                        if (DateUtils.isStartOfDay(date))
                        {
                            sValue = Formatter.formatDate(date);
                        }
                        else
                        {
                            sValue = Formatter.formatDateTime(date);
                        }
                    }
                    else
                    {
                        //use default toString() implementation
                        sValue = "" + value;
                    }
                    xmlResult.write((entry.getKey() + "=\"" + sValue + "\" ").getBytes());
                }
                xmlResult.write(("/>").getBytes());
            }

            xmlResult.write(("</" + xmlRoot + ">").getBytes());
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}