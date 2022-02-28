package au.gov.qld.fire.jms.dao;

import java.util.Date;
import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.action.JobAction;
import au.gov.qld.fire.jms.domain.action.JobActionTodo;
import au.gov.qld.fire.jms.domain.action.TodoSearchCriteria;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.job.Job;
import au.gov.qld.fire.jms.domain.job.JobSearchCriteria;
import au.gov.qld.fire.jms.domain.refdata.ActionType;

/*
 * JobAction DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface JobActionDao extends BaseDao<JobAction>
{

    /**
     * Find entities by job.
     * @param job
     * @return
     * @throws DaoException
     */
    List<JobAction> findByJob(Job job) throws DaoException;

    /**
     * 
     * @param file
     * @param actionType
     * @return
     * @throws DaoException
     */
	List<JobAction> findByFileActionType(File file, ActionType actionType)throws DaoException;

    /**
     * 
     * @param criteria
     * @param dueDate
     * @return
     * @throws DaoException
     */
    List<JobAction> findJobActionTodoByDueDate(JobSearchCriteria criteria, Date dueDate) throws DaoException;

    /**
     * 
     * @param criteria
     * @return
     * @throws DaoException
     */
    List<JobActionTodo> findJobActionToDoByCriteria(TodoSearchCriteria criteria) throws DaoException;

}