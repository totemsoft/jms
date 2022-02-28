package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.sap.SapHeader;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SapHeaderDaoTest extends BaseTestCase
{
    /** Entity id */
    /*private*/static Long ID;

    @Autowired private SapHeaderDao sapHeaderDao;

    /**
     *
     */
    public SapHeaderDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public SapHeaderDaoTest(String name)
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
            SapHeader entity = sapHeaderDao.findById(ID);
            assertNotNull(entity);
            //assertTrue(!entity.getSubPanels().isEmpty());
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
            List<SapHeader> entities = sapHeaderDao.findAll();
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
            File file = new File(FileDaoTest.ID);
            SapHeader entity = sapHeaderDao.findByFile(file);
            if (entity == null)
            {
                entity = new SapHeader();
                entity.setSapHeaderId(FileDaoTest.ID);
                entity.setSapCustNo(11111L);
            }
            //save
            sapHeaderDao.saveOrUpdate(entity);
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
            SapHeader entity = sapHeaderDao.findById(ID);
            assertNotNull(entity);
            sapHeaderDao.delete(entity);
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
        suite.addTest(FileDaoTest.suite());
        //tests
        suite.addTest(new SapHeaderDaoTest("testSave"));
        suite.addTest(new SapHeaderDaoTest("testFindById"));
        suite.addTest(new SapHeaderDaoTest("testFindAll"));
        suite.addTest(new SapHeaderDaoTest("testDelete"));
        return suite;
    }

}