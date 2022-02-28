package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.jms.domain.KeyReceipt;
import au.gov.qld.fire.jms.domain.file.File;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class KeyReceiptDaoTest extends BaseTestCase
{
    /** Entity id */
    /*private*/static Long ID;

    @Autowired private KeyReceiptDao keyReceiptDao;

    /**
     *
     */
    public KeyReceiptDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public KeyReceiptDaoTest(String name)
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
            KeyReceipt entity = keyReceiptDao.findById(ID);
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
            List<KeyReceipt> entities = keyReceiptDao.findAll();
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
            List<KeyReceipt> entities = keyReceiptDao.findByFile(file);
            KeyReceipt entity;
            if (entities.isEmpty())
            {
                entity = new KeyReceipt();
                entity.setFile(file);
            }
            else
            {
                entity = entities.get(0);
            }
            entity.setKeyReceiptNo("keyReceiptNo");
            //save
            keyReceiptDao.saveOrUpdate(entity);
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
            KeyReceipt entity = keyReceiptDao.findById(ID);
            assertNotNull(entity);
            keyReceiptDao.delete(entity);
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
        suite.addTest(new KeyReceiptDaoTest("testSave"));
        suite.addTest(new KeyReceiptDaoTest("testFindById"));
        suite.addTest(new KeyReceiptDaoTest("testFindAll"));
        suite.addTest(new KeyReceiptDaoTest("testDelete"));
        return suite;
    }

}