package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.dao.UserTypeDao;
import au.gov.qld.fire.domain.refdata.UserType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class UserTypeDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static final Long ID = 1000000L;

    /** Entity name */
    /*private*/static final String NAME = "TEST: UserTypeDaoTest";

    @Autowired private UserTypeDao userTypeDao;

    /**
     *
     */
    public UserTypeDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public UserTypeDaoTest(String name)
    {
        super(name);
    }

    /**
     * Test method for {@link au.gov.qld.fire.dao.BaseDao#findById(java.io.Serializable)}.
     */
    public final void testFindById()
    {
        try
        {
            UserType entity = userTypeDao.findById(ID);
            assertNotNull(entity);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link au.gov.qld.fire.dao.BaseDao#findAll()}.
     */
    public final void testFindAll()
    {
        try
        {
            List<UserType> entities = userTypeDao.findAll();
            assertTrue(!entities.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link au.gov.qld.fire.dao.BaseDao#saveOrUpdate(au.gov.qld.fire.jms.domain.BaseModel)}.
     */
    public final void testSave()
    {
        try
        {
            UserType entity = userTypeDao.findById(ID);
            if (entity == null)
            {
                entity = new UserType(ID);
                entity.setName(NAME);
            }

            userTypeDao.saveOrUpdate(entity);
            //mark for commit
            
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link au.gov.qld.fire.dao.BaseDao#delete(java.lang.Object)}.
     */
    public final void testDelete()
    {
        try
        {
            UserType entity = userTypeDao.findById(ID);
            assertNotNull(entity);
            userTypeDao.delete(entity);
            fail("Has to fail.");
        }
        catch (Exception e)
        {
            LOG.debug(e.getMessage());
        }
    }

    /**
     * Main test suite.
     * @return Test suite.
     */
    public static Test suite() throws Exception
    {
        TestSuite suite = new TestSuite();
        suite.addTest(new UserTypeDaoTest("testSave"));
        suite.addTest(new UserTypeDaoTest("testFindById"));
        suite.addTest(new UserTypeDaoTest("testFindAll"));
        //suite.addTest(new UserTypeDaoTest("testDelete"));
        return suite;
    }

}