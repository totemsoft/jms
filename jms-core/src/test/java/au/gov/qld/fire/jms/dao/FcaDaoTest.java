package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.domain.location.Region;
import au.gov.qld.fire.domain.location.Station;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.fca.FcaSearchCriteria;
import au.gov.qld.fire.jms.domain.file.File;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FcaDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static String ID = "00001";

    @Autowired private FcaDao fcaDao;

    /**
     *
     */
    public FcaDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public FcaDaoTest(String name)
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
            Fca entity = fcaDao.findById(ID);
            assertNotNull(entity);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link au.gov.qld.fire.jms.dao.FcaDao#findByCriteria(FcaSearchCriteria)}.
     */
    public final void testFindByCriteria()
    {
        try
        {
            FcaSearchCriteria criteria = new FcaSearchCriteria();
            criteria.setMaxResults(20);
            criteria.setUnassignedFca(false);
            criteria.setRegion("Region 1");
            //criteria.setArea("Area 1");
            //criteria.setStation("Station 1");

            List<Fca> entities = fcaDao.findByCriteria(criteria);
            assertTrue(!entities.isEmpty());
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
            List<Fca> entities = fcaDao.findAll();
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
            Fca entity = fcaDao.findById(ID);
            if (entity == null)
            {
                entity = new Fca(ID);
            }
            entity.setRegion(new Region(RegionDaoTest.ID));
            entity.setFile(new File(FileDaoTest.ID));
            entity.setStation(new Station(StationDaoTest.ID));
            //save
            fcaDao.saveOrUpdate(entity);
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
            Fca entity = fcaDao.findById(ID);
            assertNotNull(entity);
            fcaDao.delete(entity);
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
        suite.addTest(new FileStatusDaoTest("testSave"));
        suite.addTest(new RegionDaoTest("testSave"));
        suite.addTest(new AreaDaoTest("testSave"));
        suite.addTest(new StationDaoTest("testSave"));
        suite.addTest(new FileDaoTest("testSave"));
        //tests
        suite.addTest(new FcaDaoTest("testSave"));
        suite.addTest(new FcaDaoTest("testFindById"));
        suite.addTest(new FcaDaoTest("testFindByCriteria"));
        suite.addTest(new FcaDaoTest("testFindAll"));
        suite.addTest(new FcaDaoTest("testDelete"));
        return suite;
    }

}