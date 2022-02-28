package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.dao.SupplierTypeDao;
import au.gov.qld.fire.domain.refdata.SupplierType;
import au.gov.qld.fire.domain.refdata.SupplierTypeEnum;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SupplierTypeDaoTest extends BaseTestCase
{
    /** Entity id */
    /*private*/static Long ID;

    /** Entity id */
    /*private*/static Long ID2;

    /** Entity name */
    private static String NAME = SupplierTypeEnum.TELCO.getName();

    /** Entity name */
    private static String NAME2 = SupplierTypeEnum.FIRE_ALARM_PANEL.getName();

    @Autowired private SupplierTypeDao supplierTypeDao;

    /**
     *
     */
    public SupplierTypeDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public SupplierTypeDaoTest(String name)
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
            SupplierType entity = supplierTypeDao.findById(ID);
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
            List<SupplierType> entities = supplierTypeDao.findAll();
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
            SupplierType entity = supplierTypeDao.findByName(NAME);
            if (entity == null)
            {
                entity = new SupplierType();
                entity.setName(NAME);
            }
            //save
            supplierTypeDao.saveOrUpdate(entity);

            SupplierType entity2 = supplierTypeDao.findByName(NAME2);
            if (entity2 == null)
            {
                entity2 = new SupplierType();
                entity2.setName(NAME2);
            }
            //save
            supplierTypeDao.saveOrUpdate(entity2);
            //mark for commit
            
            ID = entity.getId();
            ID2 = entity2.getId();
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
            SupplierType entity = supplierTypeDao.findById(ID);
            assertNotNull(entity);
            supplierTypeDao.delete(entity);
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
        suite.addTest(new SupplierTypeDaoTest("testSave"));
        suite.addTest(new SupplierTypeDaoTest("testFindById"));
        suite.addTest(new SupplierTypeDaoTest("testFindAll"));
        suite.addTest(new SupplierTypeDaoTest("testDelete"));
        return suite;
    }

}