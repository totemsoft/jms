package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.domain.building.Building;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.util.ThreadLocalUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class BuildingDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static Long ID;

    /** Entity name */
    private static String NAME = "TEST_BUILDING";

    @Autowired private BuildingDao buildingDao;

    /**
     *
     */
    public BuildingDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public BuildingDaoTest(String name)
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
            Building entity = buildingDao.findById(ID);
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
            List<Building> entities = buildingDao.findAll();
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
            Building entity = buildingDao.findByName(NAME);
            if (entity == null)
            {
                entity = new Building();
                entity.setName(NAME);
            }
            entity.setDescription("TEST: BuildingDaoTest - Description");
            entity.setUsage("TEST: BuildingDaoTest - Usage");
            entity.setFile(new File(FileDaoTest.ID));
            entity.setAddress(new Address(AddressDaoTest.ID));
            entity.setArchDate(ThreadLocalUtils.getDate());
            entity.setArchBy(new User(UserDaoTest.ID));
            entity.setNextBuilding(null);

            buildingDao.saveOrUpdate(entity);
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
            Building entity = buildingDao.findById(ID);
            assertNotNull(entity);
            buildingDao.delete(entity);
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
        suite.addTest(new FileStatusDaoTest("testSave"));
        suite.addTest(new SalutationDaoTest("testSave"));
        suite.addTest(new ContactDaoTest("testSave"));
        suite.addTest(new StateDaoTest("testSave"));
        suite.addTest(new AddressDaoTest("testSave"));
        suite.addTest(new RegionDaoTest("testSave"));
        suite.addTest(new FileDaoTest("testSave"));
        //dependency
        suite.addTest(new UserTypeDaoTest("testSave"));
        suite.addTest(new SystemFunctionDaoTest("testSave"));
        suite.addTest(new SecurityGroupDaoTest("testSave"));
        suite.addTest(new UserDaoTest("testSave"));
        //tests
        suite.addTest(new BuildingDaoTest("testSave"));
        suite.addTest(new BuildingDaoTest("testFindById"));
        suite.addTest(new BuildingDaoTest("testFindAll"));
        suite.addTest(new BuildingDaoTest("testDelete"));
        return suite;
    }

}