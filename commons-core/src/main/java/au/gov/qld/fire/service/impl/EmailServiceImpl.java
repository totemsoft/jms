package au.gov.qld.fire.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import au.gov.qld.fire.domain.MailData;
import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.refdata.UserType;
import au.gov.qld.fire.domain.refdata.UserTypeEnum;
import au.gov.qld.fire.domain.security.ConnectionDetails;
import au.gov.qld.fire.domain.security.SecurityGroup;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.service.EmailService;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.service.UserService;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.util.IOUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class EmailServiceImpl implements EmailService
{

    /** logger. */
    private static final Logger LOG = Logger.getLogger(EmailServiceImpl.class);

    @Inject private UserService userService;

    /** smtp */
    private ConnectionDetails smtp = new ConnectionDetails();

    /** loaded from smtpUsername, eg JMS */
    private String from;

    public EmailServiceImpl(String host, String port, String username, String password, String from)
    {
        if (StringUtils.isBlank(username)) {
            throw new ServiceException("No username configured.");
        }
        smtp.setHost(host);
        smtp.setPort(port);
        smtp.setUsername(username);
        smtp.setPassword(password);
        this.from = from;
    }

	private User getSmtpUser()
	{
		String username = smtp.getUsername();
        User result = userService.findByLogin(username);
        if (result == null) {
            LOG.warn("No smtpUser found: " + username + ". Creating default one..");
            Date now = DateUtils.getCurrentDateTime();
            String password = smtp.getPassword();
            User user = new User();
            user.setUserType(new UserType(UserTypeEnum.SYSTEM.getId()));
            user.setSecurityGroup(new SecurityGroup(1L)); // Administrator
            user.setLogin(username);
            user.encodePassword(password);
            user.setPasswordExpire(null);
            user.setCreatedDate(now);
            Contact contact = user.getContact();
            contact.setFirstName("JMS");
            contact.setSurname("System");
            contact.setEmail(from);
            contact.setCreatedDate(now);
            userService.saveUser(user, null, null, false);
            result = user;
        } else {
        	from = result.getContact().getEmail(); // reload, can be updated in database
        }
        return result;
	}

	private Authenticator getAuthenticator(ConnectionDetails cd)
    {
		String l = cd.getUsername(), p = cd.getPassword();
        if (StringUtils.isBlank(l) && StringUtils.isBlank(p)) {
            return null;
        }
        // password already decrypted
        return new Authenticator(l, p);
    }

	private Authenticator getAuthenticator(User u)
    {
		String l = u.getLogin(), p = u.decodePassword();
        if (StringUtils.isBlank(l) && StringUtils.isBlank(p)) {
            return null;
        }
        // password already decrypted
        return new Authenticator(l, p);
    }

    private static class Authenticator extends javax.mail.Authenticator
    {
        private PasswordAuthentication authentication;
        public Authenticator(String userName, String password)
        {
            authentication = new PasswordAuthentication(userName, password);
        }
        @Override
        protected PasswordAuthentication getPasswordAuthentication()
        {
            return authentication;
        }
    }

    /** logger. */
    //private final static Logger LOG = Logger.getLogger(EmailServiceImpl.class);
    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EmailService#sendMail(au.gov.qld.fire.jms.domain.MailData)
     */
    public void sendMail(MailData data) throws ServiceException
    {
    	User smtpUser = null;
    	LOG.info("sendMail to: " + data.getTo());
        try
        {
            if (StringUtils.isBlank(smtp.getHost())) {
                LOG.warn("No [mailSmtpHost] set");
                LOG.debug(data.getText());
                return;
            }

            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", smtp.getHost());
            if (smtp.getPort() != null) props.setProperty("mail.smtp.port", smtp.getPort());

            smtpUser = getSmtpUser();

            //Authenticator authenticator = getAuthenticator(smtp);
            Authenticator authenticator = getAuthenticator(smtpUser);
            if (authenticator != null) {
                props.setProperty("mail.smtp.auth", "true");
            }

            Session session = Session.getInstance(props, authenticator);
            //Session session = Session.getDefaultInstance(props, authenticator);
            Message msg = new MimeMessage(session);
            MimeMultipart mimeMultipart = null;
            java.io.File[] attachments = data.getAttachments();
            if (attachments != null && attachments.length > 0)
            {
                mimeMultipart = new MimeMultipart("mixed");
                try
                {
                    for (java.io.File file : attachments)
                    {
                        MimeBodyPart part = new MimeBodyPart();
                        part.attachFile(file);
                        mimeMultipart.addBodyPart(part);
                    }
                }
                catch (Exception e)
                {
                    throw new ServiceException("Exception occured while attaching file", e);
                }
            }

            Multipart body = null;
            String text = data.getText();
            //eg <element>text</element>
            boolean isHtml = text.indexOf("<") >= 0 && text.indexOf("</") > 0;
            if (StringUtils.isNotBlank(text))
            {
                body = new MimeMultipart("alternative");
                if (mimeMultipart != null)
                {
                    MimeBodyPart aux = new MimeBodyPart();
                    aux.setContent(body);
                    mimeMultipart.addBodyPart(aux);
                }
            }
            else
            {
                //throw new ServiceException("Email Body is blank.");
            }

            addTextPart(msg, mimeMultipart, body, text, (isHtml ? "text/html" : "text/plain"));

            if (mimeMultipart != null) {
                //text and attachments
                msg.setContent(mimeMultipart);
            }
            else if (body != null) {
                //text only
                msg.setContent(body);
            }
            else {
                //throw new ServiceException("Email Content is blank.");
            }

            String subject = data.getSubject();
            if (StringUtils.isNotBlank(subject)) {
            	// to allow empty subject (used to send sms via smtp)
            	if (!MailData.NO_SUBJECT.equals(subject)) {
                    msg.setSubject(subject);
            	}
            }
            else {
                throw new ServiceException("Email Subject is blank.");
            }

            if (StringUtils.isBlank(data.getFrom())) {
                data.setFrom(smtpUser.getContact().getEmail());
            }
            else {
                //throw new ServiceException("Email From is blank.");
            }

            msg.addFrom(InternetAddress.parse(data.getFrom()));
            if (StringUtils.isNotBlank(data.getTo()))
            {
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(data.getTo()));
            }
            else
            {
                throw new ServiceException("Email Recipients is blank.");
            }

            if (StringUtils.isNotBlank(data.getCc()))
            {
                msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(data.getCc()));
            }
            //if (StringUtils.isNotBlank(data.getBcc()))
            //{
            //    msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(data.getBcc()));
            //}

            Transport.send(msg);
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
        	if (e instanceof AuthenticationFailedException && smtpUser != null) {
        		LOG.error(e.getMessage() + ": [" + smtpUser.getLogin() + "]::[" + smtpUser.decodePassword() + "], " + smtp.getHost() + ":" + smtp.getPort());
        	}
            throw new ServiceException("Failed to send email: " + e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EmailService#receiveMailByPop3(au.gov.qld.fire.domain.security.ConnectionDetails)
     */
    public List<MailData> receiveMailByPop3(ConnectionDetails pop3, Predicate filter) throws ServiceException
    {
        final String storeName = "pop3";
        LOG.info("receiveMail from: " + pop3.getUsername());
        if (StringUtils.isBlank(pop3.getHost()))
        {
            LOG.warn("No [pop3Host] set");
            LOG.debug(pop3);
            return Collections.emptyList();
        }

        Store store = null;
        Folder inbox = null;
        try
        {
            Properties props = System.getProperties();
            props.setProperty("mail." + storeName + ".host", pop3.getHost());
            if (pop3.getPort() != null) props.setProperty("mail." + storeName + ".port", pop3.getPort());
            //props.setProperty("mail." + storeName + ".socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            //props.setProperty("mail." + storeName + ".socketFactory.fallback", "false");
            //props.setProperty("mail." + storeName + ".socketFactory.port", "995");
            //props.setProperty("mail." + storeName + ".timeout", "120000");
            Authenticator authenticator = getAuthenticator(pop3);
            if (authenticator != null) {
                props.setProperty("mail." + storeName + ".auth", "true");
            }
        
            Session session = Session.getInstance(props, authenticator);
            //session.setDebug(true);
            store = session.getStore(storeName);
            store.connect();
            inbox = store.getDefaultFolder().getFolder("INBOX");
            // POP3 messages are read-only
            inbox.open(Folder.READ_ONLY); // READ_WRITE
//            if (!inbox.hasNewMessages()) {
//            	return Collections.emptyList();
//            }
            //
            List<MailData> result = new ArrayList<MailData>();
            // Retrieve all messages.
            Message[] messages = inbox.getMessages();
            // Get attributes & flags for all messages
//            FetchProfile fp = new FetchProfile();
//            fp.add(FetchProfile.Item.ENVELOPE);
//            fp.add(FetchProfile.Item.FLAGS);
//            fp.add("X-Mailer");
//            inbox.fetch(messages, fp); // Hint to the store to prefetch information on the supplied messaged
            // Search the supplied messages for those that match the supplied criteria; messages must belong to this folder.
//            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false); // SEEN, RECENT
//            messages = inbox.search(ft, messages);
            for (Message message : messages) {
//                if (message.isSet(Flags.Flag.SEEN)) {
//                    continue;
//                }
//                // require [imaps]
//                message.setFlag(Flags.Flag.SEEN, true); 
//                // setting a flag has nothing to do with modifying the message / message headers, that's why saveChanges is not needed
//                message.saveChanges();
                //
                if (filter != null && !filter.evaluate(message)) {
                    continue;
                }
                Object content = message.getContent();
                if (content instanceof Multipart) {
                    Multipart multipartContent = (Multipart) content;
                    for (int i = 0; i < multipartContent.getCount(); i++) {
                        Part messagePart = multipartContent.getBodyPart(i);
                        String contentType = messagePart.getContentType().toLowerCase();
                        if (contentType.contains("text/plain") || contentType.contains("text/html")) {
                            // plain text or HTML only email
                        } else if (contentType.contains("application/xml")) {
                            // xml
                        } else if (contentType.contains("multipart") || (filter != null && filter.evaluate(messagePart))) {
                            // email contains attachments
                            MimeMultipart multiPart = (MimeMultipart) message.getContent();
                            BodyPart part = multiPart.getBodyPart(Part.ATTACHMENT);
                            if (part != null) {
                            	result.add(saveDocument(message, part));
                            } else {
                                for (int p = 0; p < multiPart.getCount(); p++) {
                                    part = multiPart.getBodyPart(p);
                                    if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                                    	result.add(saveDocument(message, part));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException("Failed to receive email: " + e.getMessage(), e);
        }
        finally
        {
            if (inbox != null && inbox.isOpen()) {
                try {
                    inbox.close(false);
                } catch (MessagingException ignore) {
                    LOG.error(ignore.getMessage(), ignore);
                }
            }
            if (store != null && store.isConnected()) {
                try {
                    store.close();
                } catch (MessagingException ignore) {
                    LOG.error(ignore.getMessage(), ignore);
                }
            }
        }
    }

    @Override
	public List<MailData> receiveMailByIMAP(ConnectionDetails imap, Predicate filter) throws ServiceException
	{
        final String storeName = "imaps";
        LOG.info("receiveMail from: " + imap.getUsername());
        if (StringUtils.isBlank(imap.getHost()))
        {
            LOG.warn("No [imapHost] set");
            LOG.debug(imap);
            return Collections.emptyList();
        }

        Store store = null;
        Folder inbox = null;
        try
        {
            Properties props = System.getProperties();
            props.setProperty("mail." + storeName + ".host", imap.getHost());
            if (imap.getPort() != null) props.setProperty("mail." + storeName + ".port", imap.getPort());
            //props.setProperty("mail." + storeName + ".socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            //props.setProperty("mail." + storeName + ".socketFactory.fallback", "false");
            //props.setProperty("mail." + storeName + ".socketFactory.port", "995");
            //props.setProperty("mail." + storeName + ".timeout", "120000");
            Authenticator authenticator = getAuthenticator(imap);
            if (authenticator != null) {
                props.setProperty("mail." + storeName + ".auth", "true");
            }
        
            Session session = Session.getInstance(props, authenticator);
            //session.setDebug(true);
            store = session.getStore(storeName);
            store.connect();
            inbox = store.getDefaultFolder().getFolder("INBOX");
            // POP3 messages are read-only // READ_ONLY
            inbox.open(Folder.READ_WRITE);
//            if (!inbox.hasNewMessages()) {
//            	return Collections.emptyList();
//            }
            //
            List<MailData> result = new ArrayList<MailData>();
            // Retrieve all messages.
            Message[] messages = inbox.getMessages();
            // Get attributes & flags for all messages
            FetchProfile fp = new FetchProfile();
            fp.add(FetchProfile.Item.ENVELOPE);
            fp.add(FetchProfile.Item.FLAGS);
            fp.add("X-Mailer");
            inbox.fetch(messages, fp); // Hint to the store to prefetch information on the supplied messaged
            // Search the supplied messages for those that match the supplied criteria; messages must belong to this folder.
            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false); // SEEN, RECENT
            messages = inbox.search(ft, messages);
            LOG.info(messages.length + " emails found.");
            int index = 0;
            for (Message message : messages) {
            	index++;
                if (message.isSet(Flags.Flag.SEEN)) {
                    continue;
                }
                // require [imaps]
                message.setFlag(Flags.Flag.SEEN, true);
                // setting a flag has nothing to do with modifying the message / message headers, that's why saveChanges is not needed
                //message.saveChanges(); // IMAPMessage is read-only
                //
                if (filter != null && !filter.evaluate(message)) {
                    continue;
                }
                LOG.info(index + "). processing email from " + message.getFrom()[0]);
                Object content = message.getContent();
                if (content instanceof Multipart) {
                    Multipart multipartContent = (Multipart) content;
                    for (int i = 0; i < multipartContent.getCount(); i++) {
                        Part messagePart = multipartContent.getBodyPart(i);
                        String contentType = messagePart.getContentType().toLowerCase();
                        if (contentType.contains("text/plain") || contentType.contains("text/html")) {
                            // plain text or HTML only email
                        } else if (contentType.contains("application/xml")) {
                            // xml
                        } else if (contentType.contains("multipart") || (filter != null && filter.evaluate(messagePart))) {
                            // email contains attachments
                            MimeMultipart multiPart = (MimeMultipart) message.getContent();
                            BodyPart part = multiPart.getBodyPart(Part.ATTACHMENT);
                            if (part != null) {
                            	MailData mailData = saveDocument(message, part);
                            	result.add(mailData);
                                LOG.info(index + "). saved document: " + mailData.getDocument().getName());
                            } else {
                                for (int p = 0; p < multiPart.getCount(); p++) {
                                    part = multiPart.getBodyPart(p);
                                    if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                                    	MailData mailData = saveDocument(message, part);
                                    	result.add(mailData);
                                        LOG.info(index + "). saved document: " + mailData.getDocument().getName());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            LOG.info(result.size() + " emails processed.");
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException("Failed to receive email: " + e.getMessage(), e);
        }
        finally
        {
            if (inbox != null && inbox.isOpen()) {
                try {
                    inbox.close(false);
                } catch (MessagingException ignore) {
                    LOG.error(ignore.getMessage(), ignore);
                }
            }
            if (store != null && store.isConnected()) {
                try {
                    store.close();
                } catch (MessagingException ignore) {
                    LOG.error(ignore.getMessage(), ignore);
                }
            }
        }
	}

	private MailData saveDocument(Message message, BodyPart part) throws Exception
    {
    	MailData data = new MailData();
    	data.setId(MailData.generateId(message));
    	data.setSentDate(message.getSentDate());
    	data.setReceivedDate(message.getReceivedDate());
        Address[] addresses = message.getFrom();
    	data.setFrom(addresses[0].toString());
        //
    	Document document = new Document();
    	document.setName(part.getFileName());
    	InputStream input = part.getInputStream();
    	ByteArrayOutputStream output = new ByteArrayOutputStream();
        IOUtils.copy(input, output);
        input.close();
        document.setContent(output.toByteArray());
        String contentType = part.getContentType();
        document.setContentType(contentType.substring(0, contentType.indexOf(';'))); // eg application/xml;\n\rfileName
        data.setDocument(document);
        return data;
    }

    /**
     * 
     * @param msg
     * @param multipart
     * @param body
     * @param text
     * @param contentType
     * @throws MessagingException
     */
    private void addTextPart(Message msg, Multipart multipart, Multipart body, String text,
        String contentType) throws MessagingException
    {
        if (body != null)
        {
            body.addBodyPart(createBodyPart(text, contentType));
        }
        else if (multipart != null)
        {
            multipart.addBodyPart(createBodyPart(text, contentType));
        }
        else
        {
            msg.setContent(text, contentType);
        }
    }

    private MimeBodyPart createBodyPart(Object content, String contentType)
        throws MessagingException
    {
        MimeBodyPart part = new MimeBodyPart();
        part.setContent(content, contentType);
        return part;
    }

}
