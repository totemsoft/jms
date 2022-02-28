package au.gov.qld.fire.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.domain.Request;
import au.gov.qld.fire.validation.ValidationException.ValidationMessage;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Service
public interface Importer<T>
{

    /**
     * 
     * @param name
     * @param data
     * @return errors (if any)
     * @throws ServiceException
     */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	Map<? extends Request, List<ValidationMessage>> importData(String name, T data)
	    throws ServiceException;

}