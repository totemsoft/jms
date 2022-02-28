package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.jms.domain.building.BuildingContact;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.refdata.BuildingContactType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class BuildingContactDaoTest extends BaseTestCase
{
    /** Entity id */
    /*private*/static Long ID;

    @Autowired private BuildingContactDao buildingContactDao;

    /**
     *
     */
    public BuildingContactDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public BuildingContactDaoTest(String name)
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
            BuildingContact entity = buildingContactDao.findById(ID);
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
            List<BuildingContact> entities = buildingContactDao.findAll();
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
            BuildingContactType buildingContactType = new BuildingContactType(BuildingContactTypeDaoTest.ID);
            List<BuildingContact> entities = buildingContactDao.findByBuildingContactType(buildingContactType);
            BuildingContact entity;
            if (entities.isEmpty())
            {
                entity = new BuildingContact();
                entity.setBuildingContactType(buildingContactType);
            }
            else
            {
                entity = entities.get(0);
            }
            entity.setFile(new File(FileDaoTest.ID));
            entity.setContact(new Contact(ContactDaoTest.ID));
            //save
            buildingContactDao.saveOrUpdate(entity);
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
            BuildingContact entity = buildingContactDao.findById(ID);
            assertNotNull(entity);
            buildingContactDao.delete(entity);
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
        suite.addTest(new BuildingContactTypeDaoTest("testSave"));
        suite.addTest(new FileStatusDaoTest("testSave"));
        suite.addTest(new SalutationDaoTest("testSave"));
        suite.addTest(new ContactDaoTest("testSave"));
        suite.addTest(new StateDaoTest("testSave"));
        suite.addTest(new AddressDaoTest("testSave"));
        suite.addTest(new RegionDaoTest("testSave"));
        suite.addTest(new FileDaoTest("testSave"));
        //tests
        suite.addTest(new BuildingContactDaoTest("testSave"));
        suite.addTest(new BuildingContactDaoTest("testFindById"));
        suite.addTest(new BuildingContactDaoTest("testFindAll"));
        suite.addTest(new BuildingContactDaoTest("testDelete"));
        return suite;
    }

}