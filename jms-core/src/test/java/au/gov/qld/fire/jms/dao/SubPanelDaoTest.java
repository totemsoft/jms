package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.jms.domain.ase.AseFile;
import au.gov.qld.fire.jms.domain.ase.SubPanel;
import au.gov.qld.fire.jms.domain.ase.SubPanelPK;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SubPanelDaoTest extends BaseTestCase
{
    /** Entity id */
    /*private*/static SubPanelPK ID;

    @Autowired private SubPanelDao subPanelDao;

    /**
     *
     */
    public SubPanelDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public SubPanelDaoTest(String name)
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
            SubPanel entity = subPanelDao.findById(ID);
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
            List<SubPanel> entities = subPanelDao.findAll();
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
            AseFile aseFile = new AseFile(AseFileDaoTest.ID);
            List<SubPanel> entities = subPanelDao.findByAseFile(aseFile);
            SubPanel entity;
            if (entities.isEmpty())
            {
                entity = new SubPanel();
                entity.getId().setAseFileId(aseFile.getId());
                entity.getId().setSubPanelOrderId(1L);
            }
            else
            {
                entity = entities.get(0);
            }
            entity.setAddress(new Address(AddressDaoTest.ID));
            entity.setBuildingName("TEST buildingName");
            //save
            subPanelDao.saveOrUpdate(entity);
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
            SubPanel entity = subPanelDao.findById(ID);
            assertNotNull(entity);
            subPanelDao.delete(entity);
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
        suite.addTest(new AseTypeDaoTest("testSave"));
        suite.addTest(new AseFileDaoTest("testSave"));
        //dependency
        suite.addTest(new AddressDaoTest("testSave"));
        //tests
        suite.addTest(new SubPanelDaoTest("testSave"));
        suite.addTest(new SubPanelDaoTest("testFindById"));
        suite.addTest(new SubPanelDaoTest("testFindAll"));
        suite.addTest(new SubPanelDaoTest("testDelete"));
        return suite;
    }

}