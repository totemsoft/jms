package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.jms.domain.ase.AseChange;
import au.gov.qld.fire.jms.domain.ase.AseFile;
import au.gov.qld.fire.util.ThreadLocalUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseChangeDaoTest extends BaseTestCase
{
    /** Entity id */
    /*private*/static Long ID;

    @Autowired private AseChangeDao aseChangeDao;

    /**
     *
     */
    public AseChangeDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public AseChangeDaoTest(String name)
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
            AseChange entity = aseChangeDao.findById(ID);
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
            List<AseChange> entities = aseChangeDao.findAll();
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
            List<AseChange> entities = aseChangeDao.findByAseFile(aseFile);
            AseChange entity;
            if (entities.isEmpty())
            {
                entity = new AseChange();
                entity.setAseFile(aseFile);
            }
            else
            {
                entity = entities.get(0);
            }
            entity.setDateChange(ThreadLocalUtils.getDate());
            //entity.add(new Supplier(SupplierDaoTest.ID));
            //save
            aseChangeDao.saveOrUpdate(entity);
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
            AseChange entity = aseChangeDao.findById(ID);
            assertNotNull(entity);
            aseChangeDao.delete(entity);
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
        suite.addTest(new SupplierTypeDaoTest("testSave"));
        suite.addTest(new SupplierDaoTest("testSave"));
        suite.addTest(new AseFileDaoTest("testSave"));
        //tests
        suite.addTest(new AseChangeDaoTest("testSave"));
        suite.addTest(new AseChangeDaoTest("testFindById"));
        suite.addTest(new AseChangeDaoTest("testFindAll"));
        suite.addTest(new AseChangeDaoTest("testDelete"));
        return suite;
    }

}