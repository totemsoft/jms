package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.jms.domain.action.ActionOutcome;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ActionOutcomeDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static Long ID;

    /** Entity name */
    /*private*/static final String NAME = "TEST_ACTION_OUTCOME";

    @Autowired private ActionOutcomeDao actionOutcomeDao;

    /**
     *
     */
    public ActionOutcomeDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public ActionOutcomeDaoTest(String name)
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
            ActionOutcome entity = actionOutcomeDao.findById(ID);
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
            List<ActionOutcome> entities = actionOutcomeDao.findAll();
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
            ActionOutcome entity = actionOutcomeDao.findByName(NAME);
            if (entity == null)
            {
                entity = new ActionOutcome();
                entity.setName(NAME);
            }
            entity.setFixed(!entity.isFixed());
            //save
            actionOutcomeDao.saveOrUpdate(entity);
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
            ActionOutcome entity = actionOutcomeDao.findById(ID);
            assertNotNull(entity);
            actionOutcomeDao.delete(entity);
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
        //tests
        suite.addTest(new ActionOutcomeDaoTest("testSave"));
        suite.addTest(new ActionOutcomeDaoTest("testFindById"));
        suite.addTest(new ActionOutcomeDaoTest("testFindAll"));
        suite.addTest(new ActionOutcomeDaoTest("testDelete"));
        return suite;
    }

}