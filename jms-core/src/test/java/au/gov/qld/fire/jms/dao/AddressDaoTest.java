package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.dao.AddressDao;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.location.State;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AddressDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static Long ID;

    /** Entity email */
    private static final String ADDR_LINE_1 = "addrLine1";

    private static final String ADDR_LINE_2 = "addrLine2";

    private static final String POSTCODE = "0000";

    private static final String SUBURB = "suburb";

    @Autowired private AddressDao addressDao;

    /**
     *
     */
    public AddressDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public AddressDaoTest(String name)
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
            Address entity = addressDao.findById(ID);
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
            List<Address> entities = addressDao.findAll();
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
            List<Address> entities = addressDao.findByAddrLinePostcodeSuburb(ADDR_LINE_1, ADDR_LINE_2,
                POSTCODE, SUBURB, StateDaoTest.ID);
            Address entity;
            if (entities.isEmpty())
            {
                entity = new Address();
                entity.setAddrLine1(ADDR_LINE_1);
                entity.setAddrLine2(ADDR_LINE_2);
                entity.setPostcode(POSTCODE);
                entity.setSuburb(SUBURB);
                entity.setState(new State(StateDaoTest.ID));
            }
            else
            {
                entity = entities.get(0);
            }
            //save
            addressDao.saveOrUpdate(entity);
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
            Address entity = addressDao.findById(ID);
            assertNotNull(entity);
            addressDao.delete(entity);
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
        suite.addTest(new StateDaoTest("testSave"));
        //tests
        suite.addTest(new AddressDaoTest("testSave"));
        suite.addTest(new AddressDaoTest("testFindById"));
        suite.addTest(new AddressDaoTest("testFindAll"));
        suite.addTest(new AddressDaoTest("testDelete"));
        return suite;
    }

}