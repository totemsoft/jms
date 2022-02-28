package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.jms.domain.action.ActionOutcome;
import au.gov.qld.fire.jms.domain.action.ActionWorkflow;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ActionWorkflowDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static Long ID;

    @Autowired private ActionWorkflowDao actionWorkflowDao;

    /**
     *
     */
    public ActionWorkflowDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public ActionWorkflowDaoTest(String name)
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
            ActionWorkflow entity = actionWorkflowDao.findById(ID);
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
            List<ActionWorkflow> entities = actionWorkflowDao.findAll();
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
            List<ActionWorkflow> entities = actionWorkflowDao.findByActionCode(new ActionCode(ActionCodeDaoTest.ID));
            ActionWorkflow entity;
            if (entities.isEmpty())
            {
                entity = new ActionWorkflow();
                entity.setActionCode(new ActionCode(ActionCodeDaoTest.ID));
            }
            else
            {
                entity = entities.get(0);
            }
            entity.setActionOutcome(new ActionOutcome(ActionOutcomeDaoTest.ID));
            entity.setNextDueDays(10L);
            //save
            actionWorkflowDao.saveOrUpdate(entity);
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
            ActionWorkflow entity = actionWorkflowDao.findById(ID);
            assertNotNull(entity);
            actionWorkflowDao.delete(entity);
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
        //dependency
        suite.addTest(new ActionTypeDaoTest("testSave"));
        suite.addTest(new SalutationDaoTest("testSave"));
        suite.addTest(new ContactDaoTest("testSave"));
        suite.addTest(new StateDaoTest("testSave"));
        suite.addTest(new AddressDaoTest("testSave"));
        suite.addTest(new RegionDaoTest("testSave"));
        suite.addTest(new AreaDaoTest("testSave"));
        suite.addTest(new ActionCodeDaoTest("testSave"));
        suite.addTest(new ActionOutcomeDaoTest("testSave"));
        //tests
        suite.addTest(new ActionWorkflowDaoTest("testSave"));
        suite.addTest(new ActionWorkflowDaoTest("testFindById"));
        suite.addTest(new ActionWorkflowDaoTest("testFindAll"));
        suite.addTest(new ActionWorkflowDaoTest("testDelete"));
        return suite;
    }

}