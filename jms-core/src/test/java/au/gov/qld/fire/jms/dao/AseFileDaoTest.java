package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.jms.domain.ase.AseFile;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.refdata.AseType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseFileDaoTest extends BaseTestCase
{
    /** Entity id */
    /*private*/static Long ID;

    @Autowired private AseFileDao aseFileDao;

    /**
     *
     */
    public AseFileDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public AseFileDaoTest(String name)
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
            AseFile entity = aseFileDao.findById(ID);
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
            List<AseFile> entities = aseFileDao.findAll();
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
            AseType aseType = new AseType(AseTypeDaoTest.ID);
            List<AseFile> entities = aseFileDao.findByAseType(aseType);
            AseFile entity;
            if (entities.isEmpty())
            {
                entity = new AseFile();
                entity.setAseType(aseType);
            }
            else
            {
                entity = entities.get(0);
            }
            entity.setFile(new File(FileDaoTest.ID));
            entity.setAseSerialNo("aseSerialNo");
            entity.setSupplier(new Supplier(SupplierDaoTest.ID));
            //save
            aseFileDao.saveOrUpdate(entity);
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
            AseFile entity = aseFileDao.findById(ID);
            assertNotNull(entity);
            aseFileDao.delete(entity);
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
        //tests
        suite.addTest(new AseFileDaoTest("testSave"));
        suite.addTest(new AseFileDaoTest("testFindById"));
        suite.addTest(new AseFileDaoTest("testFindAll"));
        suite.addTest(new AseFileDaoTest("testDelete"));
        return suite;
    }

}