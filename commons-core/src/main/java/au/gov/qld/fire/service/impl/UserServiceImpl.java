package au.gov.qld.fire.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialException;
import javax.security.auth.login.LoginException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import au.gov.qld.fire.dao.StaffLeaveDao;
import au.gov.qld.fire.dao.TemplateDao;
import au.gov.qld.fire.dao.UserDao;
import au.gov.qld.fire.domain.ConvertUtils;
import au.gov.qld.fire.domain.MailData;
import au.gov.qld.fire.domain.document.TemplateEnum;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.refdata.LeaveType;
import au.gov.qld.fire.domain.refdata.WorkGroup;
import au.gov.qld.fire.domain.security.SecurityGroup;
import au.gov.qld.fire.domain.security.SystemFunction;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.domain.security.UserSearchCriteria;
import au.gov.qld.fire.domain.user.StaffLeave;
import au.gov.qld.fire.domain.user.StaffLeave.StatusEnum;
import au.gov.qld.fire.domain.user.StaffLeaveSearchCriteria;
import au.gov.qld.fire.security.GroupPrincipal;
import au.gov.qld.fire.service.DocumentService;
import au.gov.qld.fire.service.EmailService;
import au.gov.qld.fire.service.EntityService;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.service.UserService;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.util.Encoder;
import au.gov.qld.fire.util.ThreadLocalUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class UserServiceImpl implements UserService
{

    /** logger. */
    //private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

    @Autowired private TemplateDao templateDao;

    @Autowired private StaffLeaveDao staffLeaveDao;

    @Autowired private UserDao userDao;

    @Autowired private EntityService entityService;

    @Autowired private DocumentService documentService;

    @Autowired private EmailService emailService;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.UserService#checkLogin(java.lang.String, char[])
     */
    public void checkLogin(String login, char[] password) throws LoginException
    {
        try
        {
            //get user
            User user = userDao.findByLogin(login);
            if (user == null)
            {
                throw new AccountNotFoundException("User not found: [" + login + "]");
            }
            if (!user.isActive())
            {
                throw new AccountLockedException("User is locked: [" + login + "]");
            }
            Date now = DateUtils.getCurrentDateTime();
            String digestPassword = Encoder.digest(new String(password).getBytes());
            if (!digestPassword.equals(user.getPassword()))
            {
                throw new CredentialException("Password does not match");
            }
            if (user.getPasswordExpire().before(now))
            {
                //LOG.error("Password Expired: [" + user.getPasswordExpire() + "]");
                //return false;
                throw new AccountExpiredException("Password Expired: [" + user.getPasswordExpire()
                    + "]");
            }
        }
        catch (LoginException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new LoginException(e.getMessage());
        }
    }

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.UserService#changePassword(java.lang.String, java.lang.String, java.lang.String)
     */
	public User changePassword(String login, char[] oldPassword, char[] newPassword)
		throws ServiceException
	{
		return changePassword(login, new String(oldPassword), new String(newPassword));
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.UserService#changePassword(java.lang.String, java.lang.String, java.lang.String)
     */
    public User changePassword(String login, String oldPassword, String newPassword)
        throws ServiceException
    {
        try
        {
            // get user
            User user = userDao.findByLogin(login);
            if (user == null)
            {
                throw new ServiceException("User not found: [" + login + "]");
            }
            // non-system user
            if (!user.getPassword().equals(Encoder.digest(oldPassword.getBytes())))
            {
                throw new ServiceException("Password does not match");
            }

            // set new password for active user
            user.encodePassword(newPassword);

            // set new expire password date for active user
            Date now = DateUtils.getCurrentDate();
            //if (user.getPasswordExpire().before(now))
            //{
            user.setPasswordExpire(DateUtils.addDays(now, 90));
            //}

            userDao.saveOrUpdate(user);

            return user;
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.UserService#findUserGroup(java.lang.String)
     */
    public GroupPrincipal findUserGroup(String login) throws ServiceException
    {
        try
        {
            //get user
            User user = userDao.findByLogin(login);
            if (user == null)
            {
                throw new ServiceException("User not found: [" + login + "]");
            }
            //get user securityGroup
            SecurityGroup securityGroup = user.getSecurityGroup();
            if (securityGroup == null)
            {
                throw new ServiceException("User securityGroup not found: [" + login + "]");
            }
            //
            SystemFunction[] systemFunctions = securityGroup.getSystemFunctions().toArray(
                new SystemFunction[0]);
            return new GroupPrincipal(securityGroup.getName(), systemFunctions);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.UserService#findUserById(java.lang.Long)
     */
    public User findUserById(Long id) throws ServiceException
    {
        try
        {
            return userDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.UserService#findByLogin(java.lang.String)
     */
    public User findByLogin(String name) throws ServiceException
    {
        try
        {
            return userDao.findByLogin(name, true);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.service.UserService#findSystemUser()
	 */
	public User findSystemUser() throws ServiceException
	{
        try
        {
            return null;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.UserService#findUsers()
     */
    public List<User> findUsers() throws ServiceException
    {
        try
        {
            return userDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.UserService#findUsers(au.gov.qld.fire.jms.domain.security.UserSearchCriteria)
     */
    public List<User> findUsers(UserSearchCriteria criteria) throws ServiceException
    {
        try
        {
            return userDao.findAllByCriteria(criteria);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.UserService#saveUser(au.gov.qld.fire.jms.domain.security.User, au.gov.qld.fire.jms.domain.entity.Contact, boolean)
     */
    public void saveUser(User user, Contact contact, Long[] workGroupIds, boolean reissuePassword)
        throws ServiceException
    {
        try
        {
            String login = user.getLogin();
            // find by id or login (both unique)
            User entity = null;
            if (user.getUserId() != null) {
                entity = findUserById(user.getUserId());
            }
            else if (StringUtils.isNotBlank(login)) {
                entity = findByLogin(login);
                user.setId(entity == null ? null : entity.getId());
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity) {
                entity = user;
                if (contact != null) {
                    entity.setContact(contact);
                }
            }
            else {
                if (contact != null) {
                    user.setContact(contact);
                }
                ConvertUtils.copyProperties(user, entity);
                //ConvertUtils.copyProperties(contact, entity.getContact());
            }
            // optional supplier
            if (user.getSupplier() != null && (user.getSupplier().getId() == null || user.getSupplier().getId() == 0L)) {
                user.setSupplier(null);
            }
            // update the list of workGroups to be set into the user
            List<WorkGroup> workGroups = new ArrayList<WorkGroup>();
            if (workGroupIds != null) {
                for (int i = 0; i < workGroupIds.length; i++) {
                	WorkGroup workGroup = entityService.findWorkGroupById(workGroupIds[i]);
                    workGroups.add(workGroup);
                }
            }
            entity.setWorkGroups(workGroups);

            // set new password for user
            if (newEntity || reissuePassword) {
            	if (entity.isSystem()) {
            		// user selected password
                    entity.encodePassword(user.getPassword());
                    entity.setPasswordExpire(null);
            	} else {
                    // same as login and expired immediately
                    entity.encodePassword(login);
                    entity.setPasswordExpire(DateUtils.getCurrentDate());
            	}
            }

            // notify by email
            if (!entity.isSystem()) {
                if (newEntity) {
                    InputStream content = null;
                    try {
                        content = templateDao.getTemplateContent(TemplateEnum.EMAIL_NEW_USER);
                        MailData mailData = createMailData(content, entity);
                        emailService.sendMail(mailData);
                    }
                    finally {
                        IOUtils.closeQuietly(content);
                    }
                }
                else if (reissuePassword) {
                    InputStream content = null;
                    try {
                        content = templateDao.getTemplateContent(TemplateEnum.EMAIL_CHANGE_PASSWORD);
                        MailData mailData = createMailData(content, entity);
                        emailService.sendMail(mailData);
                    }
                    finally {
                        IOUtils.closeQuietly(content);
                    }
                }
            }

            // save
            userDao.saveOrUpdate(entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.service.UserService#findStaffLeaveByCriteria(au.gov.qld.fire.domain.user.StaffLeaveSearchCriteria)
	 */
	public List<StaffLeave> findStaffLeaveByCriteria(StaffLeaveSearchCriteria criteria) throws ServiceException
	{
        try
        {
        	User user;
        	if (criteria.getUser() == null)
        	{
            	user = ThreadLocalUtils.getUser();
        	}
        	else
        	{
        		user = userDao.findById(criteria.getUser().getId());
        		user.getContact().getName(); // init lazy property (will be used in edit ui)
        	}
    		criteria.setUser(user);
            return staffLeaveDao.findByCriteria(criteria);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.UserService#saveStaffLeaves(java.lang.Long, java.util.List)
	 */
	public void saveStaffLeaves(Long userId, List<StaffLeave> staffLeaves) throws ServiceException
	{
        try
        {
       		User user = userId == null ? ThreadLocalUtils.getUser() : new User(userId);
        	Long leaveGroupId = staffLeaveDao.findLastLeaveGroupId(user);
        	leaveGroupId = (leaveGroupId == null ? 0L : leaveGroupId) + 1L;
        	Date dateEnd = null;
        	LeaveType leaveType = null;
        	String description = null;
        	for (StaffLeave dto : staffLeaves)
        	{
        		Date date = dto.getDate();
        		// weekend
        		if (date == null)
        		{
        			continue;
        		}

        		// handle date range
        		if (dto.getDateEnd() != null)
        		{
        			dateEnd = dto.getDateEnd();
                	leaveType = dto.getLeaveType();
                	description = dto.getDescription();
        		}
        		if (dateEnd != null && date.after(dateEnd))
        		{
        			dateEnd = null;
                	leaveType = null;
                	description = null;
        		}
        		if (dateEnd != null)
        		{
        			dto.setDate(date);
        			dto.setLeaveType(leaveType);
        			dto.setDescription(description);
        		}

        		//
        		if (dto.getLeaveType().getId() == null)
        		{
        			continue;
        		}

        		// handle leaveTaken Hours/Minutes
        		BigDecimal leaveTaken = new BigDecimal(dto.getLeaveTakenHours() + "." + (dto.getLeaveTakenMinutes() * 5 / 3));
        		dto.setLeaveTaken(leaveTaken);
                // find by id
        		StaffLeave entity = staffLeaveDao.findById(dto.getId());

                // and copy
                boolean newEntity = entity == null;
        		if (newEntity)
        		{
        			// enrich dto
        			dto.setUser(user);
        			dto.setLeaveGroupId(leaveGroupId);
        			dto.setStatus(StatusEnum.SUBMITTED);
            		//
        			entity = dto;
        		}
        		else
        		{
                    ConvertUtils.copyProperties(dto, entity);
        		}

                // save
        		staffLeaveDao.saveOrUpdate(entity);
        	}
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.UserService#approveStaffLeave(java.lang.Long)
	 */
	public void approveStaffLeave(Long staffLeaveId) throws ServiceException
	{
        try
        {
    		StaffLeave entity = staffLeaveDao.findById(staffLeaveId);
    		entity.setStatus(StatusEnum.APPROVED);
    		entity.setStatusBy(ThreadLocalUtils.getUser());
    		entity.setStatusDate(ThreadLocalUtils.getDate());
    		staffLeaveDao.saveOrUpdate(entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.UserService#declineStaffLeave(java.lang.Long)
	 */
	public void declineStaffLeave(Long staffLeaveId) throws ServiceException
	{
        try
        {
    		StaffLeave entity = staffLeaveDao.findById(staffLeaveId);
    		entity.setStatus(StatusEnum.DECLINED);
    		entity.setStatusBy(ThreadLocalUtils.getUser());
    		entity.setStatusDate(ThreadLocalUtils.getDate());
    		staffLeaveDao.saveOrUpdate(entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EmailService#createMailData(java.io.InputStream, au.gov.qld.fire.jms.domain.security.User)
     */
    private MailData createMailData(InputStream content, User user) throws ServiceException
    {
        try
        {
            MailData data = new MailData(content);
            data.setTo(user.getContact().getEmail());
            String text = data.getText();
            text = documentService.updateParameters(text, user);
            data.setText(text);
            return data;
        }
        catch (Exception e)
        {
            throw new ServiceException("Failed to create MailData: " + e.getMessage(), e);
        }
    }

}