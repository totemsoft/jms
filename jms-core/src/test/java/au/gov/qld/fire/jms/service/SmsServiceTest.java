package au.gov.qld.fire.jms.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.service.SmsService;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SmsServiceTest extends BaseTestCase
{

    /** date formatter. */
    private final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Autowired private SmsService smsService;

    //@Autowired private ProxyService proxyService;

    /**
     * Test method for {@link au.gov.qld.fire.service.xcelerate.elixir.service.SmsService#sendText(java.lang.String, java.lang.String, java.lang.String)}.
     */
    public final void testSendText()
    {
        //test, no proxy
        //proxyService.disableProxy();

        String reciever = "0404 087478";
        //reciever = "0423767017";
        String msg = FORMAT.format(new Date()) + "\nHello JMS development team (from SmsServiceTest)!";
        //msg = "";
        try
        {
            smsService.sendText(reciever, msg);
        }
        catch (ServiceException e)
        {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

}