package au.gov.qld.fire.service;

import java.util.List;

import org.apache.commons.collections.Predicate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.domain.MailData;
import au.gov.qld.fire.domain.security.ConnectionDetails;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public interface EmailService
{

    /**
     * Logic to send email, check email service provider.
     * @param data The MailData that you want to send.
     * @throws ServiceException
     */
    void sendMail(MailData data) throws ServiceException;

    /**
     * 
     * @param connectionDetails
     * @param filter
     * @return
     * @throws ServiceException
     */
    List<MailData> receiveMailByPop3(ConnectionDetails pop3, Predicate filter) throws ServiceException;
    List<MailData> receiveMailByIMAP(ConnectionDetails imap, Predicate filter) throws ServiceException;

}