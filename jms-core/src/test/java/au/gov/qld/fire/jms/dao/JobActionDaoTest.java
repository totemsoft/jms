package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.domain.action.ActionOutcome;
import au.gov.qld.fire.jms.domain.action.JobAction;
import au.gov.qld.fire.jms.domain.job.Job;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.util.ThreadLocalUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobActionDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static Long ID;

    @Autowired private JobActionDao jobActionDao;

    /**
     *
     */
    public JobActionDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public JobActionDaoTest(String name)
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
            JobAction entity = jobActionDao.findById(ID);
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
            List<JobAction> entities = jobActionDao.findAll();
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
            Job job = new Job(JobDaoTest.ID);
            List<JobAction> entities = jobActionDao.findByJob(job);
            JobAction entity;
            if (entities.isEmpty())
            {
                entity = new JobAction();
                entity.setJob(job);
            }
            else
            {
                entity = entities.get(0);
            }
            entity.setActionCode(new ActionCode(ActionCodeDaoTest.ID));
            entity.setActionOutcome(new ActionOutcome(ActionOutcomeDaoTest.ID));
            entity.setCompletedBy(new User(UserDaoTest.ID));
            entity.setCompletedDate(ThreadLocalUtils.getDate());
            entity.setNotation("TEST: JobActionDaoTest - Notation");
            entity.setDestination("TEST: JobActionDaoTest - Destination");
            entity.setDueDate(DateUtils.addWeeks(ThreadLocalUtils.getDate(), 1));
            entity.setSubject("TEST: JobActionDaoTest - Subject");

            jobActionDao.saveOrUpdate(entity);
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
            JobAction entity = jobActionDao.findById(ID);
            assertNotNull(entity);
            jobActionDao.delete(entity);
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
        suite.addTest(new JobTypeDaoTest("testSave"));
        suite.addTest(new JobRequestDaoTest("testSave"));
        suite.addTest(new JobDaoTest("testSave"));
        //tests
        suite.addTest(new JobActionDaoTest("testSave"));
        suite.addTest(new JobActionDaoTest("testFindById"));
        suite.addTest(new JobActionDaoTest("testFindAll"));
        suite.addTest(new JobActionDaoTest("testDelete"));
        return suite;
    }

}