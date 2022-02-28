package au.gov.qld.fire.jms;

import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.sql.DataSource;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JndiTest extends TestCase
{

    public JndiTest()
    {
        super();
    }

    public JndiTest(String name)
    {
        super(name);
    }

    //http://www.informit.com/articles/article.aspx?p=384904
    //[ConnectionFactoryBindingService] Bound ConnectionManager 'jboss.jca:service=DataSourceBinding,name=jdbc/DataSource_jms' to JNDI name 'java:jdbc/DataSource_jms'
    public void testJBossJndi() throws Exception
    {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        env.put(Context.PROVIDER_URL, "localhost");
        //env.put(Context.PROVIDER_URL, "jnp://localhost:1099");
        env.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        //env.put(Context.SECURITY_AUTHENTICATION, "simple");
        //env.put(Context.SECURITY_PRINCIPAL, "cn=Directory Manager");
        //env.put(Context.SECURITY_CREDENTIALS, "password");
        Context initCtx = new InitialContext(env);
        try
        {
            Context envCtx = (Context) initCtx.lookup("java:"); //:comp/env
            
            NamingEnumeration<Binding> bindings = envCtx.listBindings("");
            while (bindings.hasMore())
            {
                Binding binding = bindings.next();
                assertNotNull(binding);
            }
            Object bean = (Object) envCtx.lookup("jdbc/DataSource_jms");
            assertNotNull(bean);
        }
        finally
        {
            initCtx.close();
        }
    }

    public void testWeblogicJndi() throws Exception
    {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://localhost:7001");
        //env.put(Context.SECURITY_AUTHENTICATION, "simple");
        //env.put(Context.SECURITY_PRINCIPAL, "cn=Directory Manager");
        //env.put(Context.SECURITY_CREDENTIALS, "password");
        Context initCtx = new InitialContext(env);
        try
        {
            DataSource bean = (DataSource) initCtx.lookup("jndi/jms");
            assertNotNull(bean);
        }
        finally
        {
            initCtx.close();
        }
    }

    /**
     * Main test suite.
     * @return Test suite.
     */
    public static Test suite() throws Exception
    {
        TestSuite suite = new TestSuite();
        suite.addTest(new JndiTest("testJBossJndi"));
        //suite.addTest(new JndiTest("testWeblogicJndi"));
        return suite;
    }

}