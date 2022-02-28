package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.jms.domain.document.DocChkList;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.util.ThreadLocalUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class DocChkListDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static Long ID = 0L;

    @Autowired private DocChkListDao docChkListDao;

    /**
     *
     */
    public DocChkListDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public DocChkListDaoTest(String name)
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
            DocChkList entity = docChkListDao.findById(ID);
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
            List<DocChkList> entities = docChkListDao.findAll();
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
            Template template = new Template(TemplateDaoTest.ID);
            List<DocChkList> entities = docChkListDao.findByTemplate(template);
            DocChkList entity;
            if (entities.isEmpty())
            {
                entity = new DocChkList();
                entity.setTemplate(template);
            }
            else
            {
                entity = entities.get(0);
            }
            entity.setActive(true);
            entity.setStartDate(ThreadLocalUtils.getDate());
            entity.setEndDate(DateUtils.addWeeks(entity.getStartDate(), 4));
            //save
            docChkListDao.saveOrUpdate(entity);
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
            DocChkList entity = docChkListDao.findById(ID);
            assertNotNull(entity);
            docChkListDao.delete(entity);
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
        suite.addTest(new TemplateTypeDaoTest("testSave"));
        suite.addTest(new TemplateDaoTest("testSave"));
        //tests
        suite.addTest(new DocChkListDaoTest("testSave"));
        suite.addTest(new DocChkListDaoTest("testFindById"));
        suite.addTest(new DocChkListDaoTest("testFindAll"));
        suite.addTest(new DocChkListDaoTest("testDelete"));
        return suite;
    }

}