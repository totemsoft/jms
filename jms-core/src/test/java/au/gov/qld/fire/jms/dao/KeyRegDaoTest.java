package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.jms.domain.KeyReg;
import au.gov.qld.fire.jms.domain.file.File;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class KeyRegDaoTest extends BaseTestCase
{
    /** Entity id */
    /*private*/static Long ID;

    @Autowired private KeyRegDao keyRegDao;

    /**
     *
     */
    public KeyRegDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public KeyRegDaoTest(String name)
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
            KeyReg entity = keyRegDao.findById(ID);
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
            List<KeyReg> entities = keyRegDao.findAll();
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
            List<KeyReg> entities = keyRegDao.findByFile(file);
            KeyReg entity;
            if (entities.isEmpty())
            {
                entity = new KeyReg();
                entity.setFile(file);
            }
            else
            {
                entity = entities.get(0);
            }
            entity.setKeyNo("keyNo");
            entity.setLockLocation("lockLocation");
            //save
            keyRegDao.saveOrUpdate(entity);
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
            KeyReg entity = keyRegDao.findById(ID);
            assertNotNull(entity);
            keyRegDao.delete(entity);
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
        suite.addTest(new KeyRegDaoTest("testSave"));
        suite.addTest(new KeyRegDaoTest("testFindById"));
        suite.addTest(new KeyRegDaoTest("testFindAll"));
        suite.addTest(new KeyRegDaoTest("testDelete"));
        return suite;
    }

}