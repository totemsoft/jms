package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.dao.SecurityGroupDao;
import au.gov.qld.fire.domain.security.SecurityGroup;
import au.gov.qld.fire.domain.security.SystemFunction;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SecurityGroupDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static final Long ID = 1000000L;

    @Autowired private SecurityGroupDao securityGroupDao;

    /**
     *
     */
    public SecurityGroupDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public SecurityGroupDaoTest(String name)
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
            SecurityGroup entity = securityGroupDao.findById(ID);
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
            List<SecurityGroup> entities = securityGroupDao.findAll();
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
            SecurityGroup entity = securityGroupDao.findById(ID);
            if (entity == null)
            {
                entity = new SecurityGroup(ID);
                entity.setName("test");
                entity.setDescription("TEST: SecurityGroupDaoTest");
                entity.add(new SystemFunction(SystemFunctionDaoTest.ID));
            }

            securityGroupDao.saveOrUpdate(entity);
            //mark for commit
            
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
            SecurityGroup entity = securityGroupDao.findById(ID);
            assertNotNull(entity);
            securityGroupDao.delete(entity);
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
        suite.addTest(new SystemFunctionDaoTest("testSave"));
        //tests
        suite.addTest(new SecurityGroupDaoTest("testSave"));
        suite.addTest(new SecurityGroupDaoTest("testFindById"));
        suite.addTest(new SecurityGroupDaoTest("testFindAll"));
        //suite.addTest(new SecurityGroupDaoTest("testDelete"));
        return suite;
    }

}