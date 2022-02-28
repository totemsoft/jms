package au.gov.qld.fire.service.impl;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.marre.SmsSender;

import au.gov.qld.fire.domain.MailData;
import au.gov.qld.fire.service.EmailService;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.service.SmsService;

/**
 * Logic to send sms, check sms service provider.
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SmsServiceImpl implements SmsService
{

    //private static final Logger LOG = Logger.getLogger(SmsServiceImpl.class);

    /** username */
    private String username;
    /** password */
    private String password;

    /** default sent apiid */
    private boolean viaSmtp;

    /** http apiid */
    private String httpApiid;
    /** http sender (mobile) */
    private String httpSender;

    /** smtp apiid */
    private String smtpApiid;
    /** smtp sender (email) */
    private String smtpSender;

    @Inject private EmailService emailService;

    /**
     * @param username
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * @param password
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
	 * @param viaSmtp the viaSmtp to set
	 */
	public void setViaSmtp(boolean viaSmtp)
	{
		this.viaSmtp = viaSmtp;
	}

	/**
     * @param httpApiid
     */
    public void setHttpApiid(String httpApiid)
    {
        this.httpApiid = httpApiid;
    }

    /**
     * @param httpSender
     */
    public void setHttpSender(String httpSender)
    {
        this.httpSender = httpSender;
    }

    /**
	 * @param smtpApiid the smtpApiid to set
	 */
	public void setSmtpApiid(String smtpApiid)
	{
		this.smtpApiid = smtpApiid;
	}

	/**
	 * @param smtpSender the smtpSender to set
	 */
	public void setSmtpSender(String smtpSender)
	{
		this.smtpSender = smtpSender;
	}

	/**
     * Logic to send sms, check sms service provider.
     * @param receiver
     * @param msg The message that you want to send.
     * @return
     * @throws ServiceException
     */
    public String sendText(String receiver, String msg) throws ServiceException
    {
    	receiver = StringUtils.trimToNull(receiver);
        if (receiver == null) {
            throw new ServiceException("No receiver found.");
        }
        msg = StringUtils.trimToNull(msg);
        if (msg == null) {
            throw new ServiceException("No message found.");
        }

        // International number to receiver without leading "+"
        // Add country code 61 at front of mobile, remove first 0 and spaces e.g. 0409 228 568 = 61409228568
        receiver = receiver.replaceAll("\\s+", "");
        if (receiver.startsWith("0")) {
            receiver = receiver.substring(1);
        }
        if (!receiver.startsWith("61")) {
            receiver = "61" + receiver;
        }

        //
        if (msg.length() > 160) {
            msg = msg.substring(0, 160);
        }

        // Send SMS with clickatell
        SmsSender smsSender = null;
        try
        {
        	if (viaSmtp) {
        		MailData mailData = new MailData();
                mailData.setTo(smtpSender);
                mailData.setSubject(MailData.NO_SUBJECT);
                mailData.setText("User: " + username + "\nPassword: " + password + "\nApi_ID: " + smtpApiid + "\nTo: " + receiver + "\nText: " + msg);
        		emailService.sendMail(mailData);
        		return null;
        	}
        	// by http apiid
            smsSender = SmsSender.getClickatellSender(username, password, httpApiid);
            smsSender.connect();
            return smsSender.sendTextSms(msg, receiver, httpSender);
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
        finally
        {
            try
            {
                if (smsSender != null) {
                    smsSender.disconnect();
                }
            }
            catch (Exception e)
            {
                throw new ServiceException(e);
            }
        }
    }
}