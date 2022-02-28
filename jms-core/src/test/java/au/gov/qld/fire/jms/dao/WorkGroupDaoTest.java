package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.dao.WorkGroupDao;
import au.gov.qld.fire.domain.refdata.WorkGroup;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class WorkGroupDaoTest extends BaseTestCase
{
    /** Entity id */
    /*private*/static Long ID = 1L;

    @Autowired private WorkGroupDao workGroupDao;

    /**
     *
     */
    public WorkGroupDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public WorkGroupDaoTest(String name)
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
            WorkGroup entity = workGroupDao.findById(ID);
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
            List<WorkGroup> entities = workGroupDao.findAll();
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
            WorkGroup entity = new WorkGroup(ID);
            entity.setName("Alarm Admin");
            //save
            workGroupDao.saveOrUpdate(entity);
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
            WorkGroup entity = workGroupDao.findById(ID);
            assertNotNull(entity);
            workGroupDao.delete(entity);
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
        suite.addTest(new WorkGroupDaoTest("testSave"));
        suite.addTest(new WorkGroupDaoTest("testFindById"));
        suite.addTest(new WorkGroupDaoTest("testFindAll"));
        suite.addTest(new WorkGroupDaoTest("testDelete"));
        return suite;
    }

}