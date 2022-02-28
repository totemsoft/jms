package au.gov.qld.fire.jms.service.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import au.gov.qld.fire.domain.Request;
import au.gov.qld.fire.jms.service.LoaderService;
import au.gov.qld.fire.service.Importer;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.validation.ValidationException.ValidationMessage;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class OM89FormImporterImpl implements Importer<InputStream>
{

    @Autowired private LoaderService loaderService;

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.Importer#importData(java.lang.String, java.io.InputStream)
	 */
	@Override
	public Map<? extends Request, List<ValidationMessage>> importData(String name, InputStream data) throws ServiceException
	{
		try
		{
//        	Map<OM89FormRequest, List<ValidationMessage>> errors = new LinkedHashMap<OM89FormRequest, List<ValidationMessage>>();
//    		// return requests for validation
//    		if (!errors.isEmpty())
//    		{
//    			return errors;
//    		}
            /*FileAudit fileAudit = */loaderService.loadOM89Form(name, data);
			return null;
        }
        catch (Exception e)
        {
        	throw new ServiceException(e);
        }
	}

}