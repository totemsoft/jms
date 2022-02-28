package au.gov.qld.fire.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.domain.Request;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.domain.task.ScheduledTaskEnum;
import au.gov.qld.fire.domain.task.TaskImportRequest;
import au.gov.qld.fire.validation.ValidationException.ValidationMessage;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class TaskServiceTest extends BaseTestCase
{

    @Inject private TaskService taskService;

    /**
	 * Test method for {@link au.gov.qld.fire.service.TaskService#importData(au.gov.qld.fire.domain.task.TaskImportRequest)}.
	 */
	@Ignore
	@Test
	public final void testImportIsolationData()
	{
        try
        {
        	InputStream is = getClass().getResourceAsStream("/drop/isolation/CMSIsolationHistory.xls");
            byte[] content = IOUtils.toByteArray(is);
            is.close();
            //
            TaskImportRequest taskImportRequest = new TaskImportRequest();
            taskImportRequest.setTask(ScheduledTaskEnum.isolationImporter);
            taskImportRequest.setContentName("invoice-template.xls");
            taskImportRequest.setContent(content);
            taskImportRequest.setContentType(ContentTypeEnum.APPLICATION_MSEXCEL.getContentType());
            // check for validation errors (if any)
            Map<? extends Request, List<ValidationMessage>> errors = taskService.importData(taskImportRequest);
        	Assert.assertTrue(MapUtils.isEmpty(errors));
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
	}
	@Ignore
	@Test
	public final void testImportInvoiceData()
	{
        try
        {
        	InputStream is = getClass().getResourceAsStream("/drop/uaa/invoice-template.xls");
            byte[] content = IOUtils.toByteArray(is);
            is.close();
            //
            TaskImportRequest taskImportRequest = new TaskImportRequest();
            taskImportRequest.setTask(ScheduledTaskEnum.uaaInvoiceImporter);
            taskImportRequest.setContentName("invoice-template.xls");
            taskImportRequest.setContent(content);
            taskImportRequest.setContentType(ContentTypeEnum.APPLICATION_MSEXCEL.getContentType());
            // check for validation errors (if any)
            Map<? extends Request, List<ValidationMessage>> errors = taskService.importData(taskImportRequest);
        	Assert.assertTrue(MapUtils.isEmpty(errors));
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
	}
	@Ignore
	@Test
	public final void testImportIncidentData()
	{
        try
        {
        	InputStream is = getClass().getResourceAsStream("/drop/uaa/incident.csv");
            byte[] content = IOUtils.toByteArray(is);
            is.close();
            //
            TaskImportRequest taskImportRequest = new TaskImportRequest();
            taskImportRequest.setTask(ScheduledTaskEnum.uaaIncidentImporter);
            taskImportRequest.setContentName("incident.csv");
            taskImportRequest.setContent(content);
            taskImportRequest.setContentType(ContentTypeEnum.APPLICATION_CSV.getContentType());
            // check for validation errors (if any)
            Map<? extends Request, List<ValidationMessage>> errors = taskService.importData(taskImportRequest);
        	Assert.assertTrue(MapUtils.isEmpty(errors));
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
	}
	@Test
	public final void testImportOM89Data()
	{
        try
        {
        	//InputStream is = getClass().getResourceAsStream("/drop/mailOut/OM89_data.xml");
        	InputStream is = getClass().getResourceAsStream("/drop/mailOut/OM89_data.zip");
            byte[] content = IOUtils.toByteArray(is);
            is.close();
            //
            TaskImportRequest taskImportRequest = new TaskImportRequest();
            taskImportRequest.setTask(ScheduledTaskEnum.om89FormImporter);
            taskImportRequest.setContentName("OM89_data");
            taskImportRequest.setContent(content);
            //taskImportRequest.setContentType(ContentTypeEnum.APPLICATION_XML.getContentType());
            taskImportRequest.setContentType(ContentTypeEnum.APPLICATION_ZIP.getContentType());
            // check for validation errors (if any)
            Map<? extends Request, List<ValidationMessage>> errors = taskService.importData(taskImportRequest);
        	Assert.assertTrue(MapUtils.isEmpty(errors));
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
	}

}