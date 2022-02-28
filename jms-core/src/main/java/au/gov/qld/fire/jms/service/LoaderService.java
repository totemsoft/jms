package au.gov.qld.fire.jms.service;

import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.jms.domain.file.FileAudit;
import au.gov.qld.fire.service.ServiceException;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Service
public interface LoaderService
{

    /**
     * Load domain objects from xml source.
     * @param source
     * @return
     * @throws ServiceException
     */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	FileAudit loadOM89Form(String name, InputStream source) throws ServiceException;

}