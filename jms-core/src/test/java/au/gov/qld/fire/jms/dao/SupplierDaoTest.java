package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.dao.SupplierDao;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.location.Region;
import au.gov.qld.fire.domain.refdata.SupplierType;
import au.gov.qld.fire.domain.supplier.Supplier;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SupplierDaoTest extends BaseTestCase
{
    /** Entity id */
    /*private*/static Long ID;

    /** Entity name */
    private static String NAME = "TEST_SUPPLIER";

    @Autowired private SupplierDao supplierDao;

    /**
     *
     */
    public SupplierDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public SupplierDaoTest(String name)
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
            Supplier entity = supplierDao.findById(ID);
            assertNotNull(entity);
            assertTrue(!entity.getSupplierTypeMatch().isEmpty());
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
            List<Supplier> entities = supplierDao.findAll();
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
            Supplier entity = supplierDao.findByName(NAME);
            if (entity == null)
            {
                entity = new Supplier();
                entity.setName(NAME);
            }
            entity.setSupplierType(new SupplierType(SupplierTypeDaoTest.ID));
            entity.setAbn("123 456 789");
            entity.setLegalName(NAME);
            entity.setServiceDescription("TEST: ServiceDescription");
            entity.setAddress(new Address(AddressDaoTest.ID));
            entity.setPostAddress(new Address(AddressDaoTest.ID));
            entity.setContact(new Contact(ContactDaoTest.ID));
            entity.setRegion(new Region(RegionDaoTest.ID));
            entity.setMasterSupplier(null);
            //
            entity.add(new SupplierType(SupplierTypeDaoTest.ID2));
            //save
            supplierDao.saveOrUpdate(entity);
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
            Supplier entity = supplierDao.findById(ID);
            assertNotNull(entity);
            supplierDao.delete(entity);
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
        suite.addTest(new SupplierTypeDaoTest("testSave"));
        suite.addTest(new StateDaoTest("testSave"));
        suite.addTest(new AddressDaoTest("testSave"));
        suite.addTest(new SalutationDaoTest("testSave"));
        suite.addTest(new ContactDaoTest("testSave"));
        suite.addTest(new RegionDaoTest("testSave"));
        //tests
        suite.addTest(new SupplierDaoTest("testSave"));
        suite.addTest(new SupplierDaoTest("testFindById"));
        suite.addTest(new SupplierDaoTest("testFindAll"));
        suite.addTest(new SupplierDaoTest("testDelete"));
        return suite;
    }

}