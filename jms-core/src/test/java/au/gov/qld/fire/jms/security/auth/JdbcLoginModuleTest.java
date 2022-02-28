package au.gov.qld.fire.jms.security.auth;

import java.io.File;
import java.security.Policy;
import java.security.PrivilegedAction;
import java.util.Arrays;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.BeforeTransaction;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.security.CompositePolicy;
import au.gov.qld.fire.security.ILoginModuleNames;
import au.gov.qld.fire.security.auth.callback.LoginCallbackHandler;

/*
 * @author VChibaev
 */
public class JdbcLoginModuleTest extends BaseTestCase
{

    @Autowired private Policy securityPolicy;

    public JdbcLoginModuleTest()
    {
        super();
    }

    public JdbcLoginModuleTest(String name)
    {
        super(name);
    }

    @BeforeTransaction
    public void beforeTransaction() throws Exception
    {
        super.beforeTransaction();
        //System.setSecurityManager(new SecurityManager());
        if (System.getProperty("java.security.auth.login.config") == null)
        {
            File file = new File("src/main/config/jaas.config");
            System.setProperty("java.security.auth.login.config", file.getCanonicalPath());
            LOG.info("java.security.auth.login.config=" + file.getCanonicalPath());
        }

        Policy defaultPolicy = Policy.getPolicy();
        if (!defaultPolicy.getClass().isAssignableFrom(CompositePolicy.class))
        {
            Policy.setPolicy(new CompositePolicy(Arrays.asList(new Policy[] {defaultPolicy,
                    securityPolicy})));
        }
    }

    /**
     * Test method for {@link au.gov.qld.fire.security.auth.JdbcLoginModule#login()}.
     */
    //@Test
    public final void testLoginAdmin()
    {
        try
        {
            String name = "admin";
            char[] password = "password".toCharArray();
            testLogin(name, password);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link au.gov.qld.fire.security.auth.JdbcLoginModule#login()}.
     */
    //@Test
    public final void testLoginAdminFail()
    {
        try
        {
            String name = "admin";
            char[] password = "".toCharArray();
            testLogin(name, password);
            fail("Should Fail Here - Login Failure");
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage());
        }
    }

    /**
     * Test method for {@link au.gov.qld.fire.security.auth.JdbcLoginModule#login()}.
     */
    //@Test
    public final void testLoginUser()
    {
        try
        {
            String name = "user";
            char[] password = "password".toCharArray();
            testLogin(name, password);
            fail("Should Fail Here - access denied (java.io.FilePermission src\test\resources\test-access.txt write)");
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage());
        }
    }

    /**
     * 
     * @param name
     * @param password
     * @throws Exception
     */
    private final void testLogin(final String name, char[] password) throws Exception
    {
        final File file = new File("src/test/resources/test-access.txt");
        //if (LOG.isDebugEnabled())
        //    LOG.debug(file.getCanonicalFile());

        CallbackHandler callbackHandler = new LoginCallbackHandler(name, password);
        LoginContext ctx = new LoginContext(ILoginModuleNames.LOGIN_CONFIG, callbackHandler);
        ctx.login();
        try
        {
            Subject subject = ctx.getSubject();
            if (LOG.isDebugEnabled())
                LOG.debug("Logged in: " + subject);
            // Create privileged action block which limits permissions
            // to only the Subject's permissions.
            Subject.doAsPrivileged(subject, new PrivilegedAction<Object>()
            {
                public Object run()
                {
                    if (file.canRead() && LOG.isDebugEnabled())
                        LOG.debug("'" + name + "' can read: " + file);
                    if (file.canWrite() && LOG.isDebugEnabled())
                        LOG.debug("'" + name + "' can write: " + file);
                    return null;
                }
            }, null);
        }
        finally
        {
            ctx.logout();
        }
    }

    /**
     * Main test suite.
     * @return Test suite.
     */
    public static Test suite() throws Exception
    {
        TestSuite suite = new TestSuite();
        //suite.addTest(new JdbcLoginModuleTest("testLoginAdmin"));
        //suite.addTest(new JdbcLoginModuleTest("testLoginAdminFail"));
        //suite.addTest(new JdbcLoginModuleTest("testLoginUser"));
        //suite.addTest(new JdbcLoginModuleTest("testLoginUserFail"));
        return suite;
    }

}