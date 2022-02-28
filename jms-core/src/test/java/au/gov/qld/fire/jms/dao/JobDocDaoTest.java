package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.domain.refdata.DocType;
import au.gov.qld.fire.jms.domain.job.Job;
import au.gov.qld.fire.jms.domain.job.JobDoc;
import au.gov.qld.fire.util.ThreadLocalUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobDocDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static Long ID;

    @Autowired private JobDocDao jobDocDao;

    /**
     *
     */
    public JobDocDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public JobDocDaoTest(String name)
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
            JobDoc entity = jobDocDao.findById(ID);
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
            List<JobDoc> entities = jobDocDao.findAll();
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
            List<JobDoc> entities = jobDocDao.findByJob(job);
            JobDoc entity;
            if (entities.isEmpty())
            {
                entity = new JobDoc();
                entity.setJob(job);
            }
            else
            {
                entity = entities.get(0);
            }
            entity.setDocType(new DocType(DocTypeDaoTest.ID));
            entity.setDescription("TEST: JobDocDaoTest - Description");
            entity.setLocation("TEST: JobDocDaoTest - Location");
            entity.setUploadDate(ThreadLocalUtils.getDate());

            jobDocDao.saveOrUpdate(entity);
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
            JobDoc entity = jobDocDao.findById(ID);
            assertNotNull(entity);
            jobDocDao.delete(entity);
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
        suite.addTest(new JobRequestDaoTest("testSave"));
        suite.addTest(new FileDaoTest("testSave"));
        suite.addTest(new JobDaoTest("testSave"));
        suite.addTest(new DocTypeDaoTest("testSave"));
        //tests
        suite.addTest(new JobDocDaoTest("testSave"));
        suite.addTest(new JobDocDaoTest("testFindById"));
        suite.addTest(new JobDocDaoTest("testFindAll"));
        suite.addTest(new JobDocDaoTest("testDelete"));
        return suite;
    }

}