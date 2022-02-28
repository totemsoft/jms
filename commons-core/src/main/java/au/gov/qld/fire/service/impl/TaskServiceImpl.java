package au.gov.qld.fire.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.inject.Inject;

import jxl.Cell;
import jxl.CellType;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.scheduling.support.CronSequenceGenerator;

import au.com.bytecode.opencsv.CSVReader;
import au.gov.qld.fire.aspect.SystemAccess;
import au.gov.qld.fire.dao.ScheduledTaskDao;
import au.gov.qld.fire.dao.ScheduledTaskHistoryDao;
import au.gov.qld.fire.dao.ScheduledTaskHistoryItemDao;
import au.gov.qld.fire.domain.ConvertUtils;
import au.gov.qld.fire.domain.MailData;
import au.gov.qld.fire.domain.Request;
import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.domain.refdata.TaskStatusEnum;
import au.gov.qld.fire.domain.security.ConnectionDetails;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.domain.task.ScheduledTask;
import au.gov.qld.fire.domain.task.ScheduledTaskEnum;
import au.gov.qld.fire.domain.task.ScheduledTaskHistory;
import au.gov.qld.fire.domain.task.ScheduledTaskHistoryItem;
import au.gov.qld.fire.domain.task.ScheduledTaskSearchCriteria;
import au.gov.qld.fire.domain.task.TaskImportRequest;
import au.gov.qld.fire.domain.task.TaskRequest;
import au.gov.qld.fire.service.EmailService;
import au.gov.qld.fire.service.Importer;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.service.TaskService;
import au.gov.qld.fire.service.UserService;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.util.IOUtils;
import au.gov.qld.fire.util.ThreadLocalUtils;
import au.gov.qld.fire.validation.ValidationException.ValidationMessage;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class TaskServiceImpl implements TaskService
{

    private static final Logger LOG = Logger.getLogger(TaskServiceImpl.class);

    @Inject private BeanFactory beanFactory;

    @Inject private EmailService emailService;

    @Inject private ScheduledTaskDao scheduledTaskDao;

    @Inject private ScheduledTaskHistoryDao scheduledTaskHistoryDao;

    @Inject private ScheduledTaskHistoryItemDao scheduledTaskHistoryItemDao;

    @Inject private UserService userService;

    private String om89Host;
    private String om89Port;
    private String om89User;
    private String om89Subject;

	public void setOm89Host(String om89Host)
	{
		this.om89Host = om89Host;
	}

	public void setOm89Port(String om89Port)
	{
		this.om89Port = om89Port;
	}

	public void setOm89User(String om89User)
	{
		this.om89User = om89User;
	}

	public void setOm89Subject(String om89Subject)
	{
		this.om89Subject = om89Subject;
	}

	private TaskService getInstance()
    {
        return beanFactory.getBean("taskService", TaskService.class);
    }

    @SuppressWarnings("unchecked")
	private Importer<Object> getImporter(ScheduledTaskEnum task)
    {
        return beanFactory.getBean(task.getName(), Importer.class);
    }

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.TaskService#findScheduledTaskById(java.lang.Long)
	 */
	public ScheduledTask findScheduledTaskById(Long scheduledTaskId)
		throws ServiceException
	{
        try
        {
        	ScheduledTask entity = scheduledTaskDao.findById(scheduledTaskId);
            // convert from json string
        	if (StringUtils.isNotBlank(entity.getRequest()))
        	{
            	entity.setTaskRequest(TaskRequest.fromByteArray(entity.getRequest().getBytes()));
        	}
            return entity;
        }
        catch (Exception e)
        {
        	throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.TaskService#findByCriteria(au.gov.qld.fire.domain.task.ScheduledTaskSearchCriteria)
	 */
	public List<ScheduledTask> findByCriteria(ScheduledTaskSearchCriteria criteria)
		throws ServiceException
	{
        try
        {
            return scheduledTaskDao.findByCriteria(criteria);
        }
        catch (Exception e)
        {
        	throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.TaskService#saveScheduledTask(au.gov.qld.fire.domain.task.ScheduledTask)
	 */
	public void saveScheduledTask(ScheduledTask dto) throws ServiceException
	{
        try
        {
            // find by id
            Long id = dto.getId();
            ScheduledTask entity = null;
            if (id != null)
            {
                entity = scheduledTaskDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = dto;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(dto, entity);
            }
            // convert to json string
            String request = new String(dto.getTaskRequest().toByteArray());
            entity.setRequest(StringUtils.isBlank(request) ? null : request);

            // optional

            // save
            scheduledTaskDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (Exception e)
        {
        	throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.TaskService#importData(au.gov.qld.fire.domain.task.TaskImportRequest)
	 */
	public Map<? extends Request, List<ValidationMessage>> importData(TaskImportRequest request) throws ServiceException
	{
        try
        {
        	final Importer<Object> importer = getImporter(request.getTask());
			if (importer == null)
			{
				LOG.warn("Unknown Importer: " + request.getTask());
				return null;
			}
			final String name = request.getContentName();
			final String contentType = request.getContentType();
			final byte[] content = request.getContent();
			if (ContentTypeEnum.isZip(contentType))
			{
				File file = File.createTempFile(name, ".tmp");
				IOUtils.copy(new ByteArrayInputStream(content), file);
				ZipFile zipFile = null;
		        try {
		            zipFile = new ZipFile(file);
		            Enumeration<? extends ZipEntry> e = zipFile.entries();
		            Map<? extends Request, List<ValidationMessage>> result = null;
		            while (e.hasMoreElements()) {
		                ZipEntry entry = e.nextElement();
		                if (entry.isDirectory()) {
		                    continue;
		                }
		                // read contents
		                final String entryName = entry.getName();
		                final String ext = entryName.substring(entryName.lastIndexOf('.') + 1);
		                final ContentTypeEnum entryContentType = ContentTypeEnum.findByExt(ext);
	                    InputStream source = new BufferedInputStream(zipFile.getInputStream(entry));
		                try {
		                	ByteArrayOutputStream output = new ByteArrayOutputStream();
		                	IOUtils.copy(source, output);
		                	byte[] entryContent = output.toByteArray();
		                	Map<? extends Request, List<ValidationMessage>> r = importData(importer, entryName, entryContentType.getContentType(), entryContent);
		                	if (result == null && r != null) {
		                		result = r;
		                	} else if (r != null) {
		                		// TODO: fix generics
//		                		result.putAll((Map<?, ? extends List<ValidationMessage>>) r);
//		                		for (Entry<? extends Request, List<ValidationMessage>> item : r.entrySet()) {
//		                			result.put(item.getKey(), item.getValue());
//		                		}
		                	}
		                } finally {
		                	IOUtils.closeQuietly(source);
		                }
		            }
		            return result;
		        } finally {
		        	IOUtils.closeQuietly(zipFile);
		        	IOUtils.deleteFile(file);
		        }
			}
			return importData(importer, name, contentType, content);
        }
        catch (Exception e)
        {
        	throw new ServiceException(e);
        }
	}

    private Map<? extends Request, List<ValidationMessage>> importData(Importer<Object> importer,
	    String name, String contentType, byte[] content) throws Exception
	{
		if (ContentTypeEnum.isExcel(contentType))
		{
			return importer.importData(name, tableDataExcel(content));
		}
		if (ContentTypeEnum.isCsv(contentType))
		{
			return importer.importData(name, tableDataCsv(content));
		}
		if (ContentTypeEnum.isXml(contentType))
		{
			return importer.importData(name, new ByteArrayInputStream(content));
		}
    	throw new ServiceException("Unhandled ContentType: " + contentType);
	}

	private List<List<String>> tableDataExcel(byte[] content) throws Exception
	{
    	Workbook wb = Workbook.getWorkbook(new ByteArrayInputStream(content));
    	// process first visible sheet only
		Sheet sheet = null;
		for (Sheet s : wb.getSheets())
		{
			//if (s.getSettings() != null && !s.getSettings().isHidden()) // settings == null ???
			if (!s.isHidden())
			{
				sheet = s;
				break;
			}
		}
		List<List<String>> result = new ArrayList<List<String>>();
		for (int r = 0; r < sheet.getRows(); r++)
		{
			Cell[] row = sheet.getRow(r);
			if (row == null)
			{
				continue;
			}
			List<String> rowData = new ArrayList<String>();
			for (int c = 0; c < row.length; c++)
			{
				Cell cell = row[c];
				if (cell == null)
				{
					rowData.add(null);
					continue;
				}
				CellType cellType = cell.getType();
				String data;	
				if (CellType.NUMBER.equals(cellType))
				{
					data = "" + ((NumberCell) cell).getValue();
				}
//				else if (CellType.LABEL.equals(cellType))
//				{
//					data = ((LabelCell) cell).getString();
//				}
				else
				{
					data = cell.getContents();	
				}
				rowData.add(data);
			}
			// do not add empty rows
			int emptyCells = 0;
			for (String s : rowData)
			{
				if (StringUtils.isBlank(s))
				{
					emptyCells++;
				}
			}
			if (emptyCells != rowData.size())
			{
				result.add(rowData);
			}
		}
		return result;
	}

	private List<List<String>> tableDataCsv(byte[] content) throws Exception
	{
        CSVReader csvReader = new CSVReader(new StringReader(new String(content)));
        //csvReader.setBlockDelimiter((char) Character.LINE_SEPARATOR);
        try
        {
    		List<List<String>> result = new ArrayList<List<String>>();
            String[] data;
            // read all rows
            while ((data = csvReader.readNext()) != null)
            {
                if (data.length == 0 || data[0].trim().startsWith("#"))
                {
                    // ignore header or comment
                    continue;
                }
                // do not add empty data row
                for (String s : data)
                {
                    if (StringUtils.isNotBlank(s))
                    {
                        result.add(Arrays.asList(data));
                        break;
                    }
                }
            }
    		return result;
        }
        finally
        {
            csvReader.close();
        }
	}

	/**
	 * 
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getExcelColumn(int c)
	{
		final String ALFA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int g = c / ALFA.length() - 1;
		if (g >= ALFA.length())
			return "" + c;
			
		int i = c % ALFA.length();
		if (i >= ALFA.length())
			return "" + c;
		
		return g < 0 ? "" + ALFA.charAt(i) : "" + ALFA.charAt(g) + ALFA.charAt(i);
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.TaskService#run()
	 */
    @SystemAccess
	public void run()
	{
		// run every minute
		final long delta = 1 * DateUtils.MILLIS_PER_MINUTE;
		final Date now = ThreadLocalUtils.getDate();
        try
        {
        	ScheduledTaskSearchCriteria criteria = new ScheduledTaskSearchCriteria();
            criteria.setTask(null);
            criteria.setActive(true); // active task(s) only
            List<ScheduledTask> scheduledTasks = findByCriteria(criteria);
            final TaskService self = getInstance();
            Map<ScheduledTask, String> result = new LinkedHashMap<ScheduledTask, String>();
            for (ScheduledTask scheduledTask : scheduledTasks)
            {
                try
                {
        			TaskRequest request = scheduledTask.getTaskRequest();
        			CronSequenceGenerator generator = new CronSequenceGenerator(request.getCronExpression(), TimeZone.getDefault());
        			Date date = generator.next(now);
        			if (date.getTime() - now.getTime() > delta) {
        				continue;
        			}
        			//
        			ScheduledTaskHistory task = new ScheduledTaskHistory(scheduledTask);
        			task.setCreatedDate(date);
                    try
                    {
            			//self.updateStatus(task, TaskStatusEnum.P, null);
                    	String message = self.runTask(task);
                    	if (StringUtils.isNotBlank(message)) {
                            result.put(scheduledTask, message);
                    	}
                    }
                    catch (ServiceException e)
                    {
                    	String error = ExceptionUtils.getRootCauseMessage(e);
                        result.put(scheduledTask, error);
                        self.updateStatus(task, TaskStatusEnum.F, error);
                        throw e;
                    }
                }
                catch (Exception e)
                {
                    // DO NOT re-throw - this is scheduled task
                    LOG.error(e.getMessage(), e);
                }
            }
            if (!result.isEmpty())
            {
                // TODO: send update summary (if any update happened)
                //runTaskNotify(result);
            }
        }
        catch (Exception e)
        {
            // DO NOT re-throw - this is scheduled task
            LOG.error(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.TaskService#updateStatus(au.gov.qld.fire.domain.task.ScheduledTaskHistory, au.gov.qld.fire.domain.refdata.TaskStatusEnum, java.lang.String)
	 */
	public void updateStatus(ScheduledTaskHistory task, TaskStatusEnum status, String response) throws ServiceException
	{
		task.setStatus(status);
		task.setResponse(response);
        scheduledTaskHistoryDao.saveOrUpdate(task);
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.TaskService#runTaskWithStatus(au.gov.qld.fire.domain.task.ScheduledTaskHistory)
	 */
	public String runTaskWithStatus(ScheduledTaskHistory task) throws ServiceException
	{
        final TaskService self = getInstance();
        try
        {
			//self.updateStatus(task, TaskStatusEnum.P, null);
        	String message = self.runTask(task);
        	if (StringUtils.isNotBlank(message)) {
                return message;
        	}
        	return null;
        }
        catch (ServiceException e)
        {
        	String error = ExceptionUtils.getRootCauseMessage(e);
        	//return error;
            self.updateStatus(task, TaskStatusEnum.F, error);
            throw e;
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.TaskService#runTask(au.gov.qld.fire.domain.task.ScheduledTask)
	 */
	public String runTask(ScheduledTaskHistory task) throws ServiceException
	{
        try
        {
    		String result = null;
        	String name = task.getName();
			// isolation task
			if (ScheduledTaskEnum.isolationImporter.getName().equals(name))
			{
				result = runIsolationTask(task);
			}
			else if (ScheduledTaskEnum.uaaInvoiceImporter.getName().equals(name))
			{
				result = runUaaInvoiceTask(task);
			}
			else if (ScheduledTaskEnum.uaaIncidentImporter.getName().equals(name))
			{
				result = runUaaIncidentTask(task);
			}
			else if (ScheduledTaskEnum.om89FormImporter.getName().equals(name))
			{
				result = runOM89Task(task);
			}
			// TODO: more task(s).. else if () {}

			// check if any items processed
			if (!task.getItems().isEmpty()) {
	            updateStatus(task, TaskStatusEnum.S, null);
			}
			return result;
		}
        catch (Exception e)
		{
			throw new ServiceException(e);
		}
	}

	private String runIsolationTask(ScheduledTaskHistory task)
	{
		// TODO: implement
		return null;
	}

	private String runUaaInvoiceTask(ScheduledTaskHistory task)
	{
		// TODO: implement
		return null;
	}

	private String runUaaIncidentTask(ScheduledTaskHistory task)
	{
		// TODO: implement
		return null;
	}

	private String runOM89Task(ScheduledTaskHistory task)
	{
		User user = userService.findByLogin(om89User);
		if (user == null) {
			throw new ServiceException("No om89User [" + om89User + "] found.");
		}
		if (!user.isSystem()) {
			throw new ServiceException("om89User [" + om89User + "] has to be System user.");
		}
		if (!user.isActive()) {
			LOG.info("om89User [" + om89User + "] user is not active.");
			return null;
		}
		// put connectionDetails as system user
    	ConnectionDetails connectionDetails = new ConnectionDetails();
    	connectionDetails.setHost(om89Host);
    	connectionDetails.setPort(om89Port);
    	connectionDetails.setUsername(user.getContact().getEmail());
    	connectionDetails.setPassword(user.decodePassword());
    	// read email(s)
    	final Predicate filter = new Predicate() {
			public boolean evaluate(Object o) {
                try {
                	if (o instanceof javax.mail.Message) {
                		javax.mail.Message message = (javax.mail.Message) o;
    					String subject = message.getSubject();
    					if (subject.matches(om89Subject)) {
                        	String id = MailData.generateId(message);
                        	// test if this id was already processed
                        	if (scheduledTaskHistoryItemDao.findByItemId(id) != null) {
                        		return false;
                        	}
    						return true;
    					}
                	}
                	else if (o instanceof javax.mail.Part) {
                		javax.mail.Part part = ((javax.mail.Part) o);
                        String contentType = part.getContentType();
                        if (contentType.contains(ContentTypeEnum.APPLICATION_XML.getContentType())) {
                        	//((javax.mail.Multipart) part.getContent()).getParent();
                        	return true;
                        }
                	}
					return false;
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
					throw new ServiceException(e);
				}
			}
		};
        List<MailData> result = emailService.receiveMailByIMAP(connectionDetails, filter);
        for (MailData data : result) {
            TaskImportRequest taskImportRequest = new TaskImportRequest();
            taskImportRequest.setTask(ScheduledTaskEnum.om89FormImporter);
            Document document = data.getDocument();
            taskImportRequest.setContentName(document.getName());
            taskImportRequest.setContent(document.getContent());
            taskImportRequest.setContentType(document.getContentType());
            // check for validation errors (if any)
            /*Map<? extends Request, List<ValidationMessage>> errors = */importData(taskImportRequest);
            // store history item
            ScheduledTaskHistoryItem item = new ScheduledTaskHistoryItem();
            item.setItemId(data.getId());
            item.setSentDate(data.getSentDate());
            item.setReceivedDate(data.getReceivedDate());
            item.setFrom(data.getFrom());
            task.add(item);
        }
        if (!result.isEmpty()) {
            String response = result.size() + " email(s) with subject [" + om89Subject + "] processed";
            task.setResponse(response);
            return response;
        }
        return null;
	}

}
