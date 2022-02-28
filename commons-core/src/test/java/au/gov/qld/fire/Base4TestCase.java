package au.gov.qld.fire;

import org.apache.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;

import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.util.ThreadLocalUtils;

/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@ContextConfiguration(locations = {
    "classpath:applicationContext.test.xml",
    "classpath:au/gov/qld/fire/spring/spring-inc-tx.test.xml"
})
public abstract class Base4TestCase
    extends org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests
{
    /** logger. */
    protected final Logger LOG = Logger.getLogger(getClass());

    @BeforeTransaction
    public void beforeTransaction() throws Exception
    {
        ThreadLocalUtils.setDate(DateUtils.getCurrentDateTime());
        User user = null;
        ThreadLocalUtils.setUser(user);
    }

    @AfterTransaction
    public void afterTransaction() throws Exception
    {
        ThreadLocalUtils.clear();
    }

}