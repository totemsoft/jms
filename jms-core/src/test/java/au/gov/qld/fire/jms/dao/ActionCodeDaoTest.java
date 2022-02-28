package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.domain.location.Area;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;
import au.gov.qld.fire.jms.domain.refdata.ActionType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ActionCodeDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static Long ID = 0L;

    /** Action code */
    /*private*/static final String CODE = "ACT_CD_DAO";

    @Autowired private ActionCodeDao actionCodeDao;

    /**
     *
     */
    public ActionCodeDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public ActionCodeDaoTest(String name)
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
            ActionCode entity = actionCodeDao.findById(ID);
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
            List<ActionCode> entities = actionCodeDao.findAll();
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
            ActionCode entity = actionCodeDao.findByCode(CODE);
            if (entity == null)
            {
                entity = new ActionCode();
                entity.setCode(CODE);
            }
            entity.setNotation("TEST: ActionCodeDaoTest.");
            entity.setActionType(new ActionType(ActionTypeDaoTest.ID));
            entity.setArea(new Area(AreaDaoTest.ID));
            //entity.setWorkGroup(workGroup);
            //entity.setTemplate(template);
            //save
            actionCodeDao.saveOrUpdate(entity);
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
            ActionCode entity = actionCodeDao.findById(ID);
            assertNotNull(entity);
            actionCodeDao.delete(entity);
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
        //tests
        suite.addTest(new ActionCodeDaoTest("testSave"));
        suite.addTest(new ActionCodeDaoTest("testFindById"));
        suite.addTest(new ActionCodeDaoTest("testFindAll"));
        suite.addTest(new ActionCodeDaoTest("testDelete"));
        return suite;
    }

}