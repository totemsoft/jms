package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.domain.refdata.DocType;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.file.FileDoc;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FileDocDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static Long ID;

    @Autowired private FileDocDao fileDocDao;

    /**
     *
     */
    public FileDocDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public FileDocDaoTest(String name)
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
            FileDoc entity = fileDocDao.findById(ID);
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
            List<FileDoc> entities = fileDocDao.findAll();
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
            List<FileDoc> entities = fileDocDao.findByFile(file);
            FileDoc entity;
            if (entities.isEmpty())
            {
                entity = new FileDoc();
                entity.setFile(file);
            }
            else
            {
                entity = entities.get(0);
            }
            entity.setDocType(new DocType(DocTypeDaoTest.ID));
            entity.setDescription("TEST: FileDocDaoTest - Description");
            entity.setLocation("TEST: FileDocDaoTest - Location");
            //entity.setUploadDate(ThreadLocalUtils.getDate());

            fileDocDao.saveOrUpdate(entity);
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
            FileDoc entity = fileDocDao.findById(ID);
            assertNotNull(entity);
            fileDocDao.delete(entity);
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
        suite.addTest(new DocTypeDaoTest("testSave"));
        suite.addTest(new FileStatusDaoTest("testSave"));
        suite.addTest(new SalutationDaoTest("testSave"));
        suite.addTest(new ContactDaoTest("testSave"));
        suite.addTest(new StateDaoTest("testSave"));
        suite.addTest(new AddressDaoTest("testSave"));
        suite.addTest(new RegionDaoTest("testSave"));
        suite.addTest(new FileDaoTest("testSave"));
        //tests
        suite.addTest(new FileDocDaoTest("testSave"));
        suite.addTest(new FileDocDaoTest("testFindById"));
        suite.addTest(new FileDocDaoTest("testFindAll"));
        suite.addTest(new FileDocDaoTest("testDelete"));
        return suite;
    }

}