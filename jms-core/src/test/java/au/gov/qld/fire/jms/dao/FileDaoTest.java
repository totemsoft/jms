package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.refdata.FileStatus;
import au.gov.qld.fire.jms.domain.refdata.FileStatusEnum;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FileDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static Long ID;

    /** Entity email */
    /*private*/static final String EMAIL = "email@hotmail.com";

    @Autowired private FileDao fileDao;

    /**
     *
     */
    public FileDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public FileDaoTest(String name)
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
            File entity = fileDao.findById(ID);
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
            List<File> entities = fileDao.findAll();
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
            List<File> entities = fileDao.findByStatus(FileStatusDaoTest.ID);
            File entity;
            if (entities.isEmpty())
            {
                entity = new File();
                entity.setFileStatus(new FileStatus(FileStatusDaoTest.ID));
            }
            else
            {
                entity = entities.get(0);
                entity.setFileStatus(new FileStatus(FileStatusEnum.DISCONNECTED.getId()));
            }
            //entity.setRegion(new Region(RegionDaoTest.ID));
            //save
            fileDao.saveOrUpdate(entity);
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
            File entity = fileDao.findById(ID);
            assertNotNull(entity);
            fileDao.delete(entity);
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
        suite.addTest(new FileStatusDaoTest("testSave"));
        suite.addTest(new SalutationDaoTest("testSave"));
        suite.addTest(new ContactDaoTest("testSave"));
        suite.addTest(new StateDaoTest("testSave"));
        suite.addTest(new AddressDaoTest("testSave"));
        suite.addTest(new RegionDaoTest("testSave"));
        //tests
        suite.addTest(new FileDaoTest("testSave"));
        suite.addTest(new FileDaoTest("testFindById"));
        suite.addTest(new FileDaoTest("testFindAll"));
        suite.addTest(new FileDaoTest("testFindByCriteria"));
        suite.addTest(new FileDaoTest("testDelete"));
        return suite;
    }

}