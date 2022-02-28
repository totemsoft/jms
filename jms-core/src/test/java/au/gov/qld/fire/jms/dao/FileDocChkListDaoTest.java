package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.file.FileDocChkList;
import au.gov.qld.fire.jms.domain.file.FileDocChkListPK;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FileDocChkListDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static FileDocChkListPK ID;

    @Autowired private FileDocChkListDao fileDocChkListDao;

    /**
     *
     */
    public FileDocChkListDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public FileDocChkListDaoTest(String name)
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
            FileDocChkList entity = fileDocChkListDao.findById(ID);
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
            List<FileDocChkList> entities = fileDocChkListDao.findAll();
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
            List<FileDocChkList> entities = fileDocChkListDao.findByFile(file);
            FileDocChkList entity;
            if (entities.isEmpty())
            {
                entity = new FileDocChkList();
                entity.getId().setFileId(file.getId());
                entity.getId().setDocChkListId(DocChkListDaoTest.ID);
            }
            else
            {
                entity = entities.get(0);
            }
            entity.setDocOnFile(true);
            //save
            fileDocChkListDao.saveOrUpdate(entity);
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
            FileDocChkList entity = fileDocChkListDao.findById(ID);
            assertNotNull(entity);
            fileDocChkListDao.delete(entity);
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
        suite.addTest(new FileDaoTest("testSave"));
        //dependency
        suite.addTest(new TemplateTypeDaoTest("testSave"));
        suite.addTest(new TemplateDaoTest("testSave"));
        suite.addTest(new DocChkListDaoTest("testSave"));
        //tests
        suite.addTest(new FileDocChkListDaoTest("testSave"));
        suite.addTest(new FileDocChkListDaoTest("testFindById"));
        suite.addTest(new FileDocChkListDaoTest("testFindAll"));
        suite.addTest(new FileDocChkListDaoTest("testDelete"));
        return suite;
    }

}