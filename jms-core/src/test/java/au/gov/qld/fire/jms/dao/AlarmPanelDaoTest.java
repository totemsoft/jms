package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.jms.domain.ase.AlarmPanel;
import au.gov.qld.fire.jms.domain.ase.AlarmPanelPK;
import au.gov.qld.fire.jms.domain.file.File;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AlarmPanelDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static AlarmPanelPK ID;

    @Autowired private AlarmPanelDao alarmPanelDao;

    /**
     *
     */
    public AlarmPanelDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public AlarmPanelDaoTest(String name)
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
            AlarmPanel entity = alarmPanelDao.findById(ID);
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
            List<AlarmPanel> entities = alarmPanelDao.findAll();
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
            List<AlarmPanel> entities = alarmPanelDao.findByFile(file);
            AlarmPanel entity;
            if (entities.isEmpty())
            {
                entity = new AlarmPanel();
                entity.getId().setFileId(file.getId());
                entity.getId().setSupplierId(SupplierDaoTest.ID);
            }
            else
            {
                entity = entities.get(0);
            }
            entity.setAlrmManuf("alrmManuf");
            
            //save
            alarmPanelDao.saveOrUpdate(entity);
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
            AlarmPanel entity = alarmPanelDao.findById(ID);
            assertNotNull(entity);
            alarmPanelDao.delete(entity);
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
        suite.addTest(SupplierDaoTest.suite());
        //tests
        suite.addTest(new AlarmPanelDaoTest("testSave"));
        suite.addTest(new AlarmPanelDaoTest("testFindById"));
        suite.addTest(new AlarmPanelDaoTest("testFindAll"));
        suite.addTest(new AlarmPanelDaoTest("testDelete"));
        return suite;
    }

}