package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.jms.domain.ase.AseConn;
import au.gov.qld.fire.jms.domain.ase.AseFile;
import au.gov.qld.fire.jms.domain.refdata.AseConnType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseConnDaoTest extends BaseTestCase
{
    /** Entity id */
    /*private*/static Long ID;

    @Autowired private AseConnDao aseConnDao;

    /**
     *
     */
    public AseConnDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public AseConnDaoTest(String name)
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
            AseConn entity = aseConnDao.findById(ID);
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
            List<AseConn> entities = aseConnDao.findAll();
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
            AseConnType aseConnType = new AseConnType(AseConnTypeDaoTest.ID);
            List<AseConn> entities = aseConnDao.findByAseConnType(aseConnType);
            AseConn entity;
            if (entities.isEmpty())
            {
                entity = new AseConn();
                entity.setAseFile(new AseFile(AseFileDaoTest.ID));
                entity.setAseConnType(aseConnType);
            }
            else
            {
                entity = entities.get(0);
            }
            entity.setPriRef("priRef");
            entity.setSecRef("secRef");
            entity.setSecAseConnType(aseConnType);
            //save
            aseConnDao.saveOrUpdate(entity);
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
            AseConn entity = aseConnDao.findById(ID);
            assertNotNull(entity);
            aseConnDao.delete(entity);
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
        //dependency
        suite.addTest(new SupplierTypeDaoTest("testSave"));
        suite.addTest(new SupplierDaoTest("testSave"));
        suite.addTest(new AseFileDaoTest("testSave"));
        //dependency
        suite.addTest(new AseConnTypeDaoTest("testSave"));
        //tests
        suite.addTest(new AseConnDaoTest("testSave"));
        suite.addTest(new AseConnDaoTest("testFindById"));
        suite.addTest(new AseConnDaoTest("testFindAll"));
        suite.addTest(new AseConnDaoTest("testDelete"));
        return suite;
    }

}