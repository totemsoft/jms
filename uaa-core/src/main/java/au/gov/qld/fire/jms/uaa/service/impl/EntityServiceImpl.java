package au.gov.qld.fire.jms.uaa.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.uaa.dao.InjuryTypeDao;
import au.gov.qld.fire.jms.uaa.domain.refdata.InjuryType;
import au.gov.qld.fire.jms.uaa.service.EntityService;
import au.gov.qld.fire.service.ServiceException;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class EntityServiceImpl
    extends au.gov.qld.fire.service.impl.EntityServiceImpl implements EntityService
{

    /** logger. */
    private static final Logger LOG = Logger.getLogger(EntityServiceImpl.class);

    private static final String UPDATE_LIST = "/db/update/uaa.properties";

    /** required database version */
    private String dbVersion;

    @Autowired private BeanFactory beanFactory;

    @Autowired private InjuryTypeDao injuryTypeDao;

    /**
     * @param dbVersion the dbVersion to set
     */
    public void setDbVersion(String dbVersion)
    {
        this.dbVersion = dbVersion;
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#synchroniseDatabase()
     */
    public void synchroniseDatabase() throws ServiceException
    {
        try
        {
            // get current db version
            String currentDbVersion = (String) getJdbcDao().uniqueResult(
                "SELECT DBVERSION FROM DBVERSION WHERE DBVERSION LIKE 'UAA.%' ORDER BY DBVERSION DESC");
            // eg "JMS.01.00" < "JMS.01.01"
            if (currentDbVersion == null || currentDbVersion.compareToIgnoreCase(dbVersion) < 0)
            {
                LOG.info("START synchroniseDatabase from [" + currentDbVersion + "] to [" + dbVersion + "]");
                BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(UPDATE_LIST)));
                try
                {
                	EntityService entityService = (EntityService) beanFactory.getBean("entityService");
                    String line;
                    while ((line = reader.readLine()) != null)
                    {
                        int index = line.indexOf('=');
                        if (index <= 0)
                        {
                            continue; // comments
                        }
                        String key = line.substring(0, index);
                        String value = line.substring(index + 1);
                        if (dbVersion.compareToIgnoreCase(key) >= 0
                            && (currentDbVersion == null || key.compareToIgnoreCase(currentDbVersion) > 0))
                        {
                        	entityService.synchroniseDatabase(getClass().getResource(value));
                        }
                    }
                }
                finally
                {
                    IOUtils.closeQuietly(reader);
                }
                LOG.info(".. END synchroniseDatabase.");
            }
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

	public InjuryType findInjuryTypeById(Long id) throws ServiceException {
		try {
			return injuryTypeDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	public InjuryType findInjuryTypeByName(String name) throws ServiceException {
		try {
			return injuryTypeDao.findById(name);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	public List<InjuryType> findInjuryTypes(Boolean multiple) throws ServiceException {
		try {
			return injuryTypeDao.findAll(multiple);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

}