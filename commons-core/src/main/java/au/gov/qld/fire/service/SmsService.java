package au.gov.qld.fire.service;

import org.springframework.stereotype.Service;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Service
public interface SmsService
{

    /**
     * Logic to send sms, check sms service provider.
     * @param receiver
     * @param msg The message that you want to send.
     * @return Some Core API text (see marre java doc for more details) 
     * @throws ServiceException
     */
    String sendText(String receiver, String msg) throws ServiceException;

}