package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.dao.SalutationDao;
import au.gov.qld.fire.domain.refdata.Salutation;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SalutationDaoTest extends BaseTestCase
{
    /** Entity id */
    /*private*/static final String ID = "Mr";

    @Autowired private SalutationDao salutationDao;

    /**
     *
     */
    public SalutationDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public SalutationDaoTest(String name)
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
            Salutation entity = salutationDao.findById(ID);
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
            List<Salutation> entities = salutationDao.findAll();
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
            Salutation entity = new Salutation();
            entity.setId(ID);
            //save
            salutationDao.saveOrUpdate(entity);
            //mark for commit
            
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
            Salutation entity = salutationDao.findById(ID);
            assertNotNull(entity);
            salutationDao.delete(entity);
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
        suite.addTest(new SalutationDaoTest("testSave"));
        suite.addTest(new SalutationDaoTest("testFindById"));
        suite.addTest(new SalutationDaoTest("testFindAll"));
        suite.addTest(new SalutationDaoTest("testDelete"));
        return suite;
    }

}