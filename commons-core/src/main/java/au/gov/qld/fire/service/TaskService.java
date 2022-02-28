package au.gov.qld.fire.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.domain.Request;
import au.gov.qld.fire.domain.refdata.TaskStatusEnum;
import au.gov.qld.fire.domain.task.ScheduledTask;
import au.gov.qld.fire.domain.task.ScheduledTaskHistory;
import au.gov.qld.fire.domain.task.ScheduledTaskSearchCriteria;
import au.gov.qld.fire.domain.task.TaskImportRequest;
import au.gov.qld.fire.validation.ValidationException.ValidationMessage;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface TaskService //extends Runnable
{

	/**
	 * 
	 * @param scheduledTaskId
	 * @return
	 * @throws ServiceException
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	ScheduledTask findScheduledTaskById(Long scheduledTaskId)
	    throws ServiceException;

    /**
     * 
     * @param criteria
     * @return
     * @throws ServiceException
     */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    List<ScheduledTask> findByCriteria(ScheduledTaskSearchCriteria criteria)
    	throws ServiceException;

    /**
     * 
     * @param entity
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	void saveScheduledTask(ScheduledTask dto)
	    throws ServiceException;

    /**
     * 
     * @param request
     * @return
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Map<? extends Request, List<ValidationMessage>> importData(TaskImportRequest request)
		throws ServiceException;

    /**
     * Scheduled task (run every N minutes)
     * must have void returns and must not expect any arguments
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    void run();

    /**
     * 
     * @param task
     * @return - response (can be null on success)
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    String runTaskWithStatus(ScheduledTaskHistory task)
    	throws ServiceException;
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    String runTask(ScheduledTaskHistory task)
    	throws ServiceException;

    /**
     * 
     * @param task
     * @param status
     * @param response
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    void updateStatus(ScheduledTaskHistory task, TaskStatusEnum status, String response)
        throws ServiceException;

}