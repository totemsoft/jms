package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.jms.domain.refdata.StakeHolderType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class StakeHolderTypeDaoTest extends BaseTestCase
{
    /** Entity id */
    /*private*/static Long ID = 1L;

    @Autowired private StakeHolderTypeDao stakeHolderTypeDao;

    /**
     *
     */
    public StakeHolderTypeDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public StakeHolderTypeDaoTest(String name)
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
            StakeHolderType entity = stakeHolderTypeDao.findById(ID);
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
            List<StakeHolderType> entities = stakeHolderTypeDao.findAll();
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
            StakeHolderType entity = stakeHolderTypeDao.findById(ID);
            if (entity == null)
            {
                entity = new StakeHolderType(ID);
            }
            entity.setName("Community Safety");
            //save
            stakeHolderTypeDao.saveOrUpdate(entity);
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
            StakeHolderType entity = stakeHolderTypeDao.findById(ID);
            assertNotNull(entity);
            stakeHolderTypeDao.delete(entity);
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
        suite.addTest(new StakeHolderTypeDaoTest("testSave"));
        suite.addTest(new StakeHolderTypeDaoTest("testFindById"));
        suite.addTest(new StakeHolderTypeDaoTest("testFindAll"));
        suite.addTest(new StakeHolderTypeDaoTest("testDelete"));
        return suite;
    }

}