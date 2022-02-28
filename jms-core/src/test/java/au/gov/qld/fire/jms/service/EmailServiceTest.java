package au.gov.qld.fire.jms.service;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.domain.MailData;
import au.gov.qld.fire.domain.security.ConnectionDetails;
import au.gov.qld.fire.service.EmailService;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class EmailServiceTest extends BaseTestCase
{

	@Inject private EmailService emailService;

    //@Ignore
    @Test
    public final void testSendMail()
    {
        try
        {
            MailData mailData = new MailData();
            //mailData.setHost("smtp.gmail.com");
            //mailData.setPort("465");
            //mailData.setFrom("qfrsjms@emergency.qld.gov.au,");
            mailData.setSubject("EmailServiceTest");
            mailData.setText("Hi, this is a test mail from EmailServiceTest.");
            mailData.setTo("jms.uat@gmail.com,");
            //mailData.setCc("shibaevv@hotmail.com,");
            emailService.sendMail(mailData);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Ignore
    //@Test
    public final void testReceiveMail()
    {
    	// support.godaddy.com/help/article/4714/setting-up-your-email-address-with-imap
    	// http://support.godaddy.com/help/article/4738/changing-from-pop-to-imap
    	// A3 NO [ALERT] full IMAP support is NOT enabled for this account
        try
        {
        	ConnectionDetails connectionDetails = new ConnectionDetails();
        	connectionDetails.setHost("imap.gmail.com");
        	connectionDetails.setPort("993");
        	connectionDetails.setUsername("jms.uat@gmail.com");
        	connectionDetails.setPassword("???");
            List<MailData> result = emailService.receiveMailByIMAP(connectionDetails, null);
            Assert.assertTrue(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

}