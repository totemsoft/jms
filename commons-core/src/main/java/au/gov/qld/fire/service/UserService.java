package au.gov.qld.fire.service;

import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.domain.security.UserSearchCriteria;
import au.gov.qld.fire.domain.user.StaffLeave;
import au.gov.qld.fire.domain.user.StaffLeaveSearchCriteria;
import au.gov.qld.fire.security.GroupPrincipal;

/**
 * @author Valeri CHIBAEV (mailto:mail@apollosoft.net.au)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface UserService
{
    /**
     * 
     * @param login
     * @param password
     */
    void checkLogin(String login, char[] password) throws LoginException;

    /**
     * 
     * @param login
     * @param oldPassword
     * @param newPassword
     * @return User.
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    User changePassword(String login, char[] oldPassword, char[] newPassword) throws ServiceException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    User changePassword(String login, String oldPassword, String newPassword) throws ServiceException;

    /**
     * 
     * @param login
     * @return
     * @throws ServiceException
     */
    GroupPrincipal findUserGroup(String login) throws ServiceException;

    /**
     * Find user by id.
     * @param id
     * @return
     * @throws ServiceException
     */
    User findUserById(Long id) throws ServiceException;

    /**
     * 
     * @param login
     * @return
     * @throws ServiceException
     */
    User findByLogin(String login) throws ServiceException;

    /**
     * 
     * @return
     * @throws ServiceException
     */
	User findSystemUser() throws ServiceException;

	/**
     * Find all active users.
     * @return
     * @throws ServiceException
     */
    List<User> findUsers() throws ServiceException;

    /**
     * 
     * @param criteria
     * @return
     * @throws ServiceException
     */
    List<User> findUsers(UserSearchCriteria criteria) throws ServiceException;

    /**
     * Save user.
     * @param user
     * @param contact
     * @param workGroupIds
     * @param reissuePassword
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    void saveUser(User user, Contact contact, Long[] workGroupIds, boolean reissuePassword) throws ServiceException;

    /**
     * 
     * @param criteria
     * @return
     * @throws ServiceException
     */
	List<StaffLeave> findStaffLeaveByCriteria(StaffLeaveSearchCriteria criteria) throws ServiceException;

	/**
	 * 
	 * @param userId
	 * @param staffLeaves
	 * @throws ServiceException
	 */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	void saveStaffLeaves(Long userId, List<StaffLeave> staffLeaves) throws ServiceException;

    /**
     * 
     * @param staffLeaveId
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	void approveStaffLeave(Long staffLeaveId) throws ServiceException;

	/**
	 * 
	 * @param staffLeaveId
	 * @throws ServiceException
	 */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	void declineStaffLeave(Long staffLeaveId) throws ServiceException;

}