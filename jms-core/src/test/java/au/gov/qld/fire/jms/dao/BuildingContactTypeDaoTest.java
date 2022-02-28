package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.jms.domain.refdata.BuildingContactType;
import au.gov.qld.fire.jms.domain.refdata.BuildingContactTypeEnum;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class BuildingContactTypeDaoTest extends BaseTestCase
{
    /** Entity id */
    /*private*/static Long ID;

    /** Entity name */
    private static String NAME = BuildingContactTypeEnum.AFTER_HOURS.getName();

    @Autowired private BuildingContactTypeDao buildingContactTypeDao;

    /**
     *
     */
    public BuildingContactTypeDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public BuildingContactTypeDaoTest(String name)
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
            BuildingContactType entity = buildingContactTypeDao.findById(ID);
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
            List<BuildingContactType> entities = buildingContactTypeDao.findAll();
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
            BuildingContactType entity = buildingContactTypeDao.findByName(NAME);
            if (entity == null)
            {
                entity = new BuildingContactType();
                entity.setName(NAME);
            }
            //save
            buildingContactTypeDao.saveOrUpdate(entity);
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
            BuildingContactType entity = buildingContactTypeDao.findById(ID);
            assertNotNull(entity);
            buildingContactTypeDao.delete(entity);
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
        suite.addTest(new BuildingContactTypeDaoTest("testSave"));
        suite.addTest(new BuildingContactTypeDaoTest("testFindById"));
        suite.addTest(new BuildingContactTypeDaoTest("testFindAll"));
        suite.addTest(new BuildingContactTypeDaoTest("testDelete"));
        return suite;
    }

}