package au.gov.qld.fire.jms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.service.ServiceException;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface HelpService
{

    /**
     * Generic method.
     * @param module
     * @param path
     * @return
     * @throws ServiceException
     */
    String getHelp(String module, String path) throws ServiceException;
    
}