package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.job.JobRequest;
import au.gov.qld.fire.jms.domain.refdata.JobType;
import au.gov.qld.fire.util.ThreadLocalUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobRequestDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static Long ID;

    @Autowired private JobRequestDao jobRequestDao;

    /**
     *
     */
    public JobRequestDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public JobRequestDaoTest(String name)
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
            JobRequest entity = jobRequestDao.findById(ID);
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
            List<JobRequest> entities = jobRequestDao.findAll();
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
            JobType jobType = new JobType(JobTypeDaoTest.ID);
            List<JobRequest> entities = jobRequestDao.findByJobType(jobType);
            JobRequest entity;
            if (entities.isEmpty())
            {
                entity = new JobRequest();
                entity.setJobType(jobType);
            }
            else
            {
                entity = entities.get(0);
            }
            entity.setAccepted(true);
            entity.setAcceptedBy(new User(UserDaoTest.ID));
            entity.setAcceptedDate(ThreadLocalUtils.getDate());
            entity.setDescription("TEST: JobRequestDaoTest - Description");
            entity.setFca(new Fca(FcaDaoTest.ID));
            entity.setLink("http://by111w.bay111.mail.live.com/mail/InboxLight.aspx?n=465736275");
            entity.setRejectReason("TEST: JobRequestDaoTest - reject Reason");
            entity.setRequestedBy(new User(UserDaoTest.ID));
            entity.setRequestedDate(ThreadLocalUtils.getDate());
            entity.setRequestedEmail("mail@apollosoft.net");

            jobRequestDao.saveOrUpdate(entity);
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
            JobRequest entity = jobRequestDao.findById(ID);
            assertNotNull(entity);
            jobRequestDao.delete(entity);
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
        //tests
        suite.addTest(new JobRequestDaoTest("testSave"));
        suite.addTest(new JobRequestDaoTest("testFindById"));
        suite.addTest(new JobRequestDaoTest("testFindAll"));
        suite.addTest(new JobRequestDaoTest("testDelete"));
        return suite;
    }

}