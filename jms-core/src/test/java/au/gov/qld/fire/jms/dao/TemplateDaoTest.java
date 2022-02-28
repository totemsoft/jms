package au.gov.qld.fire.jms.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestSuite;
import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.dao.TemplateDao;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.domain.refdata.TemplateType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class TemplateDaoTest extends BaseTestCase
{

    /** Entity id */
    /*private*/static Long ID;

    /** Entity content */
    private static byte[] CONTENT = "This is content for TemplateDaoTest (Apollosoft P/L).".getBytes();

    @Autowired private TemplateDao templateDao;

    /**
     *
     */
    public TemplateDaoTest()
    {
        super();
    }

    /**
     * @param name
     */
    public TemplateDaoTest(String name)
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
            Template entity = templateDao.findById(ID);
            assertNotNull(entity);
            assertTrue(Arrays.equals(CONTENT, entity.getContent()));
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
            List<Template> entities = templateDao.findAll();
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
            TemplateType templateType = new TemplateType(TemplateTypeDaoTest.ID);
            List<Template> entities = templateDao.findByTemplateType(templateType);
            Template entity = new Template();
            if (entities.isEmpty())
            {
                entity = new Template();
                entity.setTemplateType(templateType);
            }
            else
            {
                entity = entities.get(0);
            }
            entity.setName("TEST: TemplateDaoTest.");
            entity.setContentType(ContentTypeEnum.APPLICATION_TXT.getContentType());
            entity.setContent(CONTENT);
            //save
            templateDao.saveOrUpdate(entity);
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
            Template entity = templateDao.findById(ID);
            assertNotNull(entity);
            templateDao.delete(entity);
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
        //tests
        suite.addTest(new TemplateDaoTest("testSave"));
        suite.addTest(new TemplateDaoTest("testFindById"));
        suite.addTest(new TemplateDaoTest("testFindAll"));
        suite.addTest(new TemplateDaoTest("testDelete"));
        return suite;
    }

}