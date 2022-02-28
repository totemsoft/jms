package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.dao.AreaDao;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.location.Area;
import au.gov.qld.fire.domain.location.Region;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AreaDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static String ID = "TEST_AREA";

    @Autowired private AreaDao areaDao;

    /**
     *
     */
    public AreaDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public AreaDaoTest(String name)
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
            Area entity = areaDao.findById(ID);
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
            List<Area> entities = areaDao.findAll();
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
            Area entity = areaDao.findById(ID);
            if (entity == null)
            {
                entity = new Area(ID);
                entity.setName("TEST: AreaDaoTest");
            }
            entity.setContact(new Contact(ContactDaoTest.ID));
            entity.setAddress(new Address(AddressDaoTest.ID));
            entity.setRegion(new Region(RegionDaoTest.ID));
            //save
            areaDao.saveOrUpdate(entity);
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
            Area entity = areaDao.findById(ID);
            assertNotNull(entity);
            areaDao.delete(entity);
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
        suite.addTest(new SalutationDaoTest("testSave"));
        suite.addTest(new ContactDaoTest("testSave"));
        suite.addTest(new StateDaoTest("testSave"));
        suite.addTest(new AddressDaoTest("testSave"));
        suite.addTest(new RegionDaoTest("testSave"));
        //tests
        suite.addTest(new AreaDaoTest("testSave"));
        suite.addTest(new AreaDaoTest("testFindById"));
        suite.addTest(new AreaDaoTest("testFindAll"));
        suite.addTest(new AreaDaoTest("testDelete"));
        return suite;
    }

}