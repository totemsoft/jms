package au.gov.qld.fire.jms.dao;

import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.springframework.beans.factory.annotation.Autowired;

import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.dao.UserDao;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.refdata.Salutation;
import au.gov.qld.fire.domain.refdata.UserType;
import au.gov.qld.fire.domain.security.SecurityGroup;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.domain.security.UserSearchCriteria;
import au.gov.qld.fire.util.ThreadLocalUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class UserDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static Long ID;

    /** Entity login */
    /*private*/static String LOGIN = "testuser";

    @Autowired private UserDao userDao;

    /**
     *
     */
    public UserDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public UserDaoTest(String name)
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
            User entity = userDao.findById(ID);
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
            List<User> entities = userDao.findAll();
            assertTrue(!entities.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link au.gov.qld.fire.dao.UserDao#findAllByCriteria()}.
     */
    public final void testFindAllByCriteria()
    {
        try
        {
            UserSearchCriteria criteria = new UserSearchCriteria();
            criteria.setUserTypes(UserTypeDaoTest.NAME);
            List<User> entities = userDao.findAllByCriteria(criteria);
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
            User entity = userDao.findByLogin(LOGIN);
            if (entity == null)
            {
                entity = new User();
                entity.setLogin(LOGIN);
            }
            entity.encodePassword(LOGIN);
            entity.setPasswordExpire(ThreadLocalUtils.getDate());
            entity.setSecurityGroup(new SecurityGroup(SecurityGroupDaoTest.ID));
            entity.setActive(false);
            entity.setUserType(new UserType(UserTypeDaoTest.ID));

            //Contact contact = new Contact(ContactDaoTest.ID);
            Contact contact = new Contact();
            contact.setSalutation(new Salutation(SalutationDaoTest.ID));
            contact.setFirstName("test");
            contact.setSurname("user");
            contact.setEmail(LOGIN + "@apollosoft.net.au");
            entity.setContact(contact);

            userDao.saveOrUpdate(entity);
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
            User entity = userDao.findById(ID);
            assertNotNull(entity);
            userDao.delete(entity);
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
        suite.addTest(new UserTypeDaoTest("testSave"));
        suite.addTest(new SystemFunctionDaoTest("testSave"));
        suite.addTest(new SecurityGroupDaoTest("testSave"));
        suite.addTest(new SalutationDaoTest("testSave"));
        suite.addTest(new ContactDaoTest("testSave"));
        //tests
        suite.addTest(new UserDaoTest("testSave"));
        suite.addTest(new UserDaoTest("testFindById"));
        suite.addTest(new UserDaoTest("testFindAll"));
        suite.addTest(new UserDaoTest("testFindAllByCriteria"));
        suite.addTest(new UserDaoTest("testDelete"));
        return suite;
    }

}