package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.jms.domain.refdata.ActionType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ActionTypeDaoTest extends BaseTestCase
{
    /** Entity id */
    /*private*/static Long ID;

    /** Entity name */
    private static String NAME = "TEST_ACTION_TYPE";

    @Autowired private ActionTypeDao actionTypeDao;

    /**
     *
     */
    public ActionTypeDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public ActionTypeDaoTest(String name)
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
            ActionType entity = actionTypeDao.findById(ID);
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
            List<ActionType> entities = actionTypeDao.findAll();
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
            ActionType entity = actionTypeDao.findByName(NAME);
            if (entity == null)
            {
                entity = new ActionType();
                entity.setName(NAME);
            }
            //save
            actionTypeDao.saveOrUpdate(entity);
            //mark for commit
            
            ID = entity.getId();
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
            ActionType entity = actionTypeDao.findById(ID);
            assertNotNull(entity);
            actionTypeDao.delete(entity);
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
        suite.addTest(new ActionTypeDaoTest("testSave"));
        suite.addTest(new ActionTypeDaoTest("testFindById"));
        suite.addTest(new ActionTypeDaoTest("testFindAll"));
        suite.addTest(new ActionTypeDaoTest("testDelete"));
        return suite;
    }

}