package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.dao.StationDao;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.location.Area;
import au.gov.qld.fire.domain.location.Station;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class StationDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static String ID = "TEST_STATN";

    @Autowired private StationDao stationDao;

    /**
     *
     */
    public StationDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public StationDaoTest(String name)
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
            Station entity = stationDao.findById(ID);
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
            List<Station> entities = stationDao.findAll();
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
            Station entity = stationDao.findById(ID);
            if (entity == null)
            {
                entity = new Station(ID);
                entity.setName("TEST: StationDaoTest");
            }
            entity.setContact(new Contact(ContactDaoTest.ID));
            entity.setAddress(new Address(AddressDaoTest.ID));
            entity.setArea(new Area(AreaDaoTest.ID));
            //save
            stationDao.saveOrUpdate(entity);
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
            Station entity = stationDao.findById(ID);
            assertNotNull(entity);
            stationDao.delete(entity);
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
        suite.addTest(new AreaDaoTest("testSave"));
        //tests
        suite.addTest(new StationDaoTest("testSave"));
        suite.addTest(new StationDaoTest("testFindById"));
        suite.addTest(new StationDaoTest("testFindAll"));
        suite.addTest(new StationDaoTest("testDelete"));
        return suite;
    }

}