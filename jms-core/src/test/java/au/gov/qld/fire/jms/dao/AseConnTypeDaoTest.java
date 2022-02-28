package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.jms.domain.refdata.AseConnType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseConnTypeDaoTest extends BaseTestCase
{
    /** Entity id */
    /*private*/static Long ID;

    /** Entity name */
    private static String NAME = "TEST_ASE_CONN_TYPE";

    @Autowired private AseConnTypeDao aseConnTypeDao;

    /**
     *
     */
    public AseConnTypeDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public AseConnTypeDaoTest(String name)
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
            AseConnType entity = aseConnTypeDao.findById(ID);
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
            List<AseConnType> entities = aseConnTypeDao.findAll();
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
            AseConnType entity = aseConnTypeDao.findByName(NAME);
            if (entity == null)
            {
                entity = new AseConnType();
                entity.setName(NAME);
            }
            //save
            aseConnTypeDao.saveOrUpdate(entity);
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
            AseConnType entity = aseConnTypeDao.findById(ID);
            assertNotNull(entity);
            aseConnTypeDao.delete(entity);
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
        suite.addTest(new AseConnTypeDaoTest("testSave"));
        suite.addTest(new AseConnTypeDaoTest("testFindById"));
        suite.addTest(new AseConnTypeDaoTest("testFindAll"));
        suite.addTest(new AseConnTypeDaoTest("testDelete"));
        return suite;
    }

}