package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.dao.ContactDao;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.refdata.Salutation;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ContactDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static Long ID;

    /** Entity email */
    /*private*/static final String EMAIL = "email@hotmail.com";

    @Autowired private ContactDao contactDao;

    /**
     *
     */
    public ContactDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public ContactDaoTest(String name)
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
            Contact entity = contactDao.findById(ID);
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
            List<Contact> entities = contactDao.findAll();
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
            List<Contact> entities = contactDao.findByEmail(EMAIL);
            Contact entity;
            if (entities.isEmpty())
            {
                entity = new Contact();
                entity.setEmail(EMAIL);
            }
            else
            {
                entity = entities.get(0);
            }
            entity.setSalutation(new Salutation(SalutationDaoTest.ID));
            entity.setFirstName("Firstname");
            entity.setSurname("Surname");
            entity.setFax("6129876543210");
            entity.setMobile("0412555555");
            entity.setPhone("9876543210");
            entity.setSkype("skype");
            //save
            contactDao.saveOrUpdate(entity);
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
            Contact entity = contactDao.findById(ID);
            assertNotNull(entity);
            contactDao.delete(entity);
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
        suite.addTest(new SalutationDaoTest("testSave"));
        //tests
        suite.addTest(new ContactDaoTest("testSave"));
        suite.addTest(new ContactDaoTest("testFindById"));
        suite.addTest(new ContactDaoTest("testFindAll"));
        suite.addTest(new ContactDaoTest("testDelete"));
        return suite;
    }

}