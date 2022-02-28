package au.gov.qld.fire.jms.web.module.file;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.refdata.MailType;
import au.gov.qld.fire.domain.refdata.WorkGroup;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.action.JobAction;
import au.gov.qld.fire.jms.domain.entity.Owner;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.mail.MailRegister;
import au.gov.qld.fire.jms.domain.sap.SapHeader;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class MailRegisterForm extends AbstractValidatorForm<MailRegister>
{

	/** serialVersionUID */
	private static final long serialVersionUID = -1274129127424365071L;

	public static final String BEAN_NAME = "mailRegisterForm";

    private File file;

    private Owner owner;

    /* (non-Javadoc)
     * @see org.apache.struts.validator.ValidatorForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        super.reset(mapping, request);
        //file = null;
        //owner = null;
    }

    /* (non-Javadoc)
     * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        ActionErrors errors = super.validate(mapping, request);
        //
        return errors;
    }

	/**
     * @return the file (parent)
     */
    public File getFile()
    {
    	if (file == null)
    	{
    		file = new File();
    	}
        return file;
    }

	public void setFile(File file)
	{
		this.file = file;
        if (file.getFca() == null)
        {
        	file.setFca(new Fca());
        }
	}

	/**
     * @return the owner
     */
	public Owner getOwner()
	{
		return owner;
	}

	public void setOwner(Owner owner)
	{
		this.owner = owner;
	}


	public boolean isFileOwner()
    {
    	return owner != null && owner.getId() != null  && owner.getId() > 0;
    }

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(MailRegister entity)
    {
        super.setEntity(entity);
        //
        file = entity.getFile();
        if (entity.isMailIn())
        {
            owner = null;
        }
        else
        {
        	owner = file.getDefaultOwner();
            owner = owner == null ? new Owner() : owner;
        }
        if (file.getFca() == null)
        {
        	file.setFca(new Fca());
        }
        if (file.getSapHeader() == null)
        {
        	file.setSapHeader(new SapHeader());
        }
        //
        if (entity.getMailType() == null)
        {
        	entity.setMailType(new MailType());
        }
        if (entity.getContact() == null)
        {
        	entity.setContact(new Contact());
        }
        if (entity.getAddress() == null)
        {
        	entity.setAddress(new Address());
        }
        if (entity.getWorkGroup() == null)
        {
        	entity.setWorkGroup(new WorkGroup());
        }
        if (entity.getUser() == null)
        {
        	entity.setUser(new User());
        }
        if (entity.getFileAction() == null)
        {
        	entity.setFileAction(new au.gov.qld.fire.jms.domain.action.FileAction());
        }
        if (entity.getJobAction() == null)
        {
        	entity.setJobAction(new JobAction());
        }
    }

	public Contact getContact()
    {
        return getEntity().getContact();
    }

    public Address getAddress()
    {
        return getEntity().getAddress();
    }

    public User getUser()
    {
        return getEntity().getUser();
    }

    public WorkGroup getWorkGroup()
    {
        return getEntity().getWorkGroup();
    }

}