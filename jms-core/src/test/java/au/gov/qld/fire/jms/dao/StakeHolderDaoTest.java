package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.location.Region;
import au.gov.qld.fire.jms.domain.entity.StakeHolder;
import au.gov.qld.fire.jms.domain.refdata.StakeHolderType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class StakeHolderDaoTest extends BaseTestCase
{
    /** Entity id */
    /*private*/static Long ID;

    @Autowired private StakeHolderDao stakeHolderDao;

    /**
     *
     */
    public StakeHolderDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public StakeHolderDaoTest(String name)
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
            StakeHolder entity = stakeHolderDao.findById(ID);
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
            List<StakeHolder> entities = stakeHolderDao.findAll();
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
            StakeHolderType stakeHolderType = new StakeHolderType(StakeHolderTypeDaoTest.ID);
            List<StakeHolder> entities = stakeHolderDao.findByStakeHolderType(stakeHolderType);
            StakeHolder entity;
            if (entities.isEmpty())
            {
                entity = new StakeHolder();
                entity.setStakeHolderType(stakeHolderType);
            }
            else
            {
                entity = entities.get(0);
            }
            entity.setAddress(new Address(AddressDaoTest.ID));
            entity.setContact(new Contact(ContactDaoTest.ID));
            entity.setRegion(new Region(RegionDaoTest.ID));
            //save
            stakeHolderDao.saveOrUpdate(entity);
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
            StakeHolder entity = stakeHolderDao.findById(ID);
            assertNotNull(entity);
            stakeHolderDao.delete(entity);
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
        suite.addTest(new StakeHolderTypeDaoTest("testSave"));
        suite.addTest(new SalutationDaoTest("testSave"));
        suite.addTest(new ContactDaoTest("testSave"));
        suite.addTest(new StateDaoTest("testSave"));
        suite.addTest(new AddressDaoTest("testSave"));
        suite.addTest(new RegionDaoTest("testSave"));
        //tests
        suite.addTest(new StakeHolderDaoTest("testSave"));
        suite.addTest(new StakeHolderDaoTest("testFindById"));
        suite.addTest(new StakeHolderDaoTest("testFindAll"));
        suite.addTest(new StakeHolderDaoTest("testDelete"));
        return suite;
    }

}