package au.gov.qld.fire.dao;

import java.io.Serializable;
import java.util.List;

/**
 * DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface BaseDao<T>
{

    /**
     * Finder:
     * @param id The search criteria (primary key of the object).
     * @return
     * @throws DaoException
     */
    T findById(Serializable id) throws DaoException;

    /**
     * Finder:
     * @return
     * @throws DaoException
     */
    List<T> findAll() throws DaoException;

    /**
     * Finder:
     * @return
     * @throws DaoException
     */
    List<T> findAllActive() throws DaoException;

    /**
     * Insert or Update
     * @param entity The object to save.
     * @throws DaoException
     */
    void saveOrUpdate(T entity) throws DaoException;

    /**
     * Insert
     * @param entity The object to save.
     * @throws DaoException
     */
    void save(T entity) throws DaoException;

    /**
     * Update
     * @param entity The object to save.
     * @throws DaoException
     */
    void merge(T entity) throws DaoException;

    /**
     * Delete (could be logically) the object from the database.
     * @param entity
     * @throws DaoException
     */
    void delete(T entity) throws DaoException;

    /**
     * 
     * @param entity
     * @throws DaoException
     */
    void detach(T entity) throws DaoException;

    /**
     * Refresh the state of the instance from the database,
     * overwriting changes made to the entity, if any.
     * @param entity
     * @throws DaoException
     */
    void refresh(T entity) throws DaoException;

}