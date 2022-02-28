package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.domain.action.FileAction;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.sap.Rfi;
import au.gov.qld.fire.util.ThreadLocalUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class RfiDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static Long ID;

    @Autowired private RfiDao rfiDao;

    /**
     *
     */
    public RfiDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public RfiDaoTest(String name)
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
            Rfi entity = rfiDao.findById(ID);
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
            List<Rfi> entities = rfiDao.findAll();
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
            File file = new File(FileDaoTest.ID);
            List<Rfi> entities = rfiDao.findByFile(file);
            Rfi entity;
            if (entities.isEmpty())
            {
                entity = new Rfi();
                entity.setFile(file);
            }
            else
            {
                entity = entities.get(0);
            }
            entity.setDescription("TEST: RfiDaoTest - Description");
            entity.setFileAction(new FileAction(FileActionDaoTest.ID));
            entity.setSapInvNo("sapInvNo");
            entity.setSapInvDate(ThreadLocalUtils.getDate());
            entity.setSapInvCompBy(new User(UserDaoTest.ID));
            entity.setRfiDelBy(new User(UserDaoTest.ID));
            entity.setRfiDelDate(ThreadLocalUtils.getDate());

            rfiDao.saveOrUpdate(entity);
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
            Rfi entity = rfiDao.findById(ID);
            assertNotNull(entity);
            rfiDao.delete(entity);
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
        suite.addTest(new ActionOutcomeDaoTest("testSave"));
        suite.addTest(new UserTypeDaoTest("testSave"));
        suite.addTest(new SystemFunctionDaoTest("testSave"));
        suite.addTest(new SecurityGroupDaoTest("testSave"));
        suite.addTest(new SalutationDaoTest("testSave"));
        suite.addTest(new ContactDaoTest("testSave"));
        suite.addTest(new UserDaoTest("testSave"));
        suite.addTest(new StateDaoTest("testSave"));
        suite.addTest(new AddressDaoTest("testSave"));
        suite.addTest(new FileStatusDaoTest("testSave"));
        suite.addTest(new RegionDaoTest("testSave"));
        suite.addTest(new AreaDaoTest("testSave"));
        suite.addTest(new StationDaoTest("testSave"));
        suite.addTest(new FileDaoTest("testSave"));
        suite.addTest(new FcaDaoTest("testSave"));
        suite.addTest(new ActionTypeDaoTest("testSave"));
        suite.addTest(new ActionCodeDaoTest("testSave"));
        suite.addTest(new WorkGroupDaoTest("testSave"));
        suite.addTest(new FileActionDaoTest("testSave"));
        //tests
        suite.addTest(new RfiDaoTest("testSave"));
        suite.addTest(new RfiDaoTest("testFindById"));
        suite.addTest(new RfiDaoTest("testFindAll"));
        suite.addTest(new RfiDaoTest("testDelete"));
        return suite;
    }

}