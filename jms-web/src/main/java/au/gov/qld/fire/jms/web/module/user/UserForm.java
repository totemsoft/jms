package au.gov.qld.fire.jms.web.module.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.Resources;

import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;
import au.gov.qld.fire.web.UrlConstants;

/**
 * The Servlet spec requires that the action for the login form be j_security_check. Also, the form
 * input field for the username must have the name j_username, and the password input field must
 * have the name j_password. Requiring these names makes implementing Servlet security a little
 * easier for web container vendors, and isn't too much of an inconvenience for developers.
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class UserForm extends AbstractValidatorForm<User>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -867344817457855988L;

    /** backend entity */
    private Contact contact;

    /** reissuePassword flag */
    private transient boolean reissuePassword = false;

    /** password */
    private transient String password;

    /** passwordNew */
    private transient String passwordNew;

    /** passwordConfirm */
    private transient String passwordConfirm;

    /** userWorkGroup ids */
    private Long[] userWorkGroups;

    /* (non-Javadoc)
     * @see org.apache.struts.validator.ValidatorForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        super.reset(mapping, request);
        setEntity(new User());
        contact = null;
        reissuePassword = false;
        password = null;
        passwordNew = null;
        passwordConfirm = null;
        userWorkGroups = null;
    }

    /* (non-Javadoc)
     * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        String path = mapping.getPath(); // eg /action
        String module = mapping.getModuleConfig().getPrefix();
        //TODO: use validation (module aware)
        if (StringUtils.isBlank(module) && UrlConstants.CHANGE_PASSWORD_PATH.equals(path))
        {
            ActionErrors errors = new ActionErrors();
            if (StringUtils.isBlank(passwordNew))
            {
                errors.add("passwordNew", new ActionMessage("errors.required", Resources
                    .getMessage(request, "label.user.password.new")));
            }
            if (!passwordNew.equals(passwordConfirm))
            {
                //New and confirm passwords do not match
                errors.add("passwordConfirm", new ActionMessage("errors.required", Resources
                    .getMessage(request, "label.user.password.confirm")));
            }
            return errors;
        }
        //module /user
        return super.validate(mapping, request);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(User entity)
    {
        super.setEntity(entity);
        if (entity == null)
        {
            setContact(null);
        }
        else
        {
            setContact(entity.getContact());
        }
    }

    /**
     * @return the contact
     */
    public Contact getContact()
    {
        if (contact == null)
        {
            contact = new Contact();
        }
        return contact;
    }

    /**
     * @param contact the contact to set
     */
    public void setContact(Contact contact)
    {
        this.contact = contact;
    }

    /**
     * @return the supplierId
     */
    public Long getSupplierId()
    {
        if (getEntity().getSupplier() == null)
        {
            getEntity().setSupplier(new Supplier());
        }
        return getEntity().getSupplier().getId();
    }

    /**
     * @param supplierId the supplierId to set
     */
    public void setSupplierId(Long supplierId)
    {
        if (getEntity().getSupplier() == null)
        {
            getEntity().setSupplier(new Supplier());
        }
        getEntity().getSupplier().setId(supplierId);
    }

    /**
     * @return the reissuePassword
     */
    public boolean isReissuePassword()
    {
        return reissuePassword;
    }

    /**
     * @param reissuePassword the reissuePassword to set
     */
    public void setReissuePassword(boolean reissuePassword)
    {
        this.reissuePassword = reissuePassword;
    }

    /**
     * @return the j_username
     */
    public String getJ_username()
    {
        return getEntity().getLogin();
    }

    /**
     * @param j_username the j_username to set
     */
    public void setJ_username(String j_username)
    {
        getEntity().setLogin(j_username);
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * @return the passwordNew
     */
    public String getPasswordNew()
    {
        return passwordNew;
    }

    /**
     * @param passwordNew the passwordNew to set
     */
    public void setPasswordNew(String passwordNew)
    {
        this.passwordNew = passwordNew;
    }

    /**
     * @return the passwordConfirm
     */
    public String getPasswordConfirm()
    {
        return passwordConfirm;
    }

    /**
     * @param passwordConfirm the passwordConfirm to set
     */
    public void setPasswordConfirm(String passwordConfirm)
    {
        this.passwordConfirm = passwordConfirm;
    }

	/**
	 * @return the userWorkGroups
	 */
	public Long[] getUserWorkGroups()
	{
		return userWorkGroups;
	}

	/**
	 * @param userWorkGroups the userWorkGroups to set
	 */
	public void setUserWorkGroups(Long[] userWorkGroups)
	{
		this.userWorkGroups = userWorkGroups;
	}

}