package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.action.FileAction;
import au.gov.qld.fire.jms.domain.action.FileActionTodo;
import au.gov.qld.fire.jms.domain.action.MailSearchCriteria;
import au.gov.qld.fire.jms.domain.action.TodoSearchCriteria;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.refdata.ActionType;

/**
 * FileAction DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface FileActionDao extends BaseDao<FileAction>
{

    /**
     * Find entities by file.
     * @param file
     * @param actionType
     * @return
     * @throws DaoException
     */
    List<FileAction> findByFileActionType(File file, ActionType actionType) throws DaoException;

    /**
     * 
     * @param criteria
     * @return
     * @throws DaoException
     */
    List<FileActionTodo> findFileActionToDoByCriteria(TodoSearchCriteria criteria) throws DaoException;

	List<FileActionTodo> findMailOutActionByCriteria(MailSearchCriteria criteria) throws DaoException;

}