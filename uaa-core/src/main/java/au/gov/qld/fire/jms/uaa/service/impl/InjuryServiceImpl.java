package au.gov.qld.fire.jms.uaa.service.impl;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import au.com.bytecode.opencsv.CSVWriter;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.jms.uaa.dao.InjuryDao;
import au.gov.qld.fire.jms.uaa.domain.IncidentSearchCriteria;
import au.gov.qld.fire.jms.uaa.domain.Injury;
import au.gov.qld.fire.jms.uaa.service.InjuryService;
import au.gov.qld.fire.service.ServiceException;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class InjuryServiceImpl implements InjuryService {

	@Inject private InjuryDao injuryDao;

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.uaa.service.InjuryService#findInjuryById(java.lang.Long)
	 */
	public Injury findInjuryById(Long id) throws ServiceException
	{
		try
		{
			return injuryDao.findById(id);
		}
		catch (DaoException e)
		{
			throw new ServiceException(e);
		}
	}

	public List<Injury> findInjuryByCriteria(IncidentSearchCriteria criteria)
	    throws ServiceException
	{
		try
		{
			List<Injury> result = injuryDao.findByCriteria(criteria);
			return result;
		}
		catch (DaoException e)
		{
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.uaa.service.InjuryService#exportInjury(java.util.List, java.io.OutputStream, au.gov.qld.fire.domain.refdata.ContentTypeEnum)
	 */
	public void exportInjury(List<Injury> entities,
	    OutputStream contentStream, ContentTypeEnum contentType)
	    throws ServiceException
	{
        if (entities.isEmpty())
        {
            return;
        }

        try
        {
            if (ContentTypeEnum.isCsv(contentType))
            {
                Writer writer = new BufferedWriter(new OutputStreamWriter(contentStream));
                CSVWriter csvWriter = new CSVWriter(writer, ',');
                // header
                List<String> header = new ArrayList<String>();
                header.add("Injury ID");
                
                csvWriter.writeNext(header.toArray(new String[0]));
                // data
                for (Injury entity : entities)
                {
                    List<String> row = new ArrayList<String>();
                    row.add("" + entity.getId());
                    
                    csvWriter.writeNext(row.toArray(new String[0]));
                }
                csvWriter.close();
            }
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

}