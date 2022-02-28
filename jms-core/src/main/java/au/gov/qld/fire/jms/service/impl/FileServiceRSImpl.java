package au.gov.qld.fire.jms.service.impl;

import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.inject.Inject;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.log4j.Logger;

import au.gov.qld.fire.domain.LabelValue;
import au.gov.qld.fire.domain.LabelValues;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.jms.dao.AseKeyDao;
import au.gov.qld.fire.jms.dao.AseKeyOrderDao;
import au.gov.qld.fire.jms.dao.FileDao;
import au.gov.qld.fire.jms.dao.OwnerDao;
import au.gov.qld.fire.jms.domain.ase.AseKey;
import au.gov.qld.fire.jms.domain.ase.AseKeyOrder;
import au.gov.qld.fire.jms.domain.ase.AseKeySearchCriteria;
import au.gov.qld.fire.jms.domain.entity.Owner;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.mail.MailBatchFile;
import au.gov.qld.fire.jms.domain.mail.MailStatusEnum;
import au.gov.qld.fire.jms.domain.refdata.OwnerTypeEnum;
import au.gov.qld.fire.jms.service.DocumentService;
import au.gov.qld.fire.jms.service.FileServiceRS;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.util.ThreadLocalUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FileServiceRSImpl implements FileServiceRS
{

    /** logger. */
    private static final Logger LOG = Logger.getLogger(FileServiceRSImpl.class);

    @Inject private AseKeyDao aseKeyDao;
    @Inject private AseKeyOrderDao aseKeyOrderDao;
    //@Inject private FcaDao fcaDao;
    @Inject private FileDao fileDao;
    @Inject private OwnerDao ownerDao;

    @Inject private DocumentService documentService;

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileServiceRS#defaultOwnerType(java.lang.Long, java.lang.Long)
	 */
	public Long defaultOwnerType(Long fileId, OwnerTypeEnum ownerType) throws ServiceException
	{
        try {
    		File file = fileDao.findById(fileId);
    		for (Owner owner : file.getOwners()) {
    			owner.setDefaultContact(ownerType.getId().equals(owner.getOwnerType().getOwnerTypeId()));
    		}
            return ownerType.getId();
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileServiceRS#mailMethod(java.lang.Long, java.lang.Integer)
	 */
	public Integer mailMethod(Long fileId, Integer value) throws ServiceException
	{
        try {
    		File file = fileDao.findById(fileId);
    		file.setMailMethodId(value);
            return value;
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileServiceRS#noMailOut(java.lang.Long, boolean)
	 */
	public boolean noMailOut(Long fileId, boolean value) throws ServiceException
	{
        try {
    		File file = fileDao.findById(fileId);
    		file.setNoMailOut(value);
            return value;
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileServiceRS#mailSent(java.lang.Long)
	 */
	public Long mailSent(Long fileId) throws ServiceException
	{
        try {
    		File file = fileDao.findById(fileId);
    		MailBatchFile mbf = file.getLastMailBatchFile();
    		if (mbf == null) {
    			return null;
    		}
    		Date now = ThreadLocalUtils.getDate();
    		mbf.getMailStatus().setSentDate(now);
    		mbf.getMailStatus().setStatus(MailStatusEnum.SENT);
            return now.getTime();
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileServiceRS#mailReceived(java.lang.Long)
	 */
	public Long mailReceived(Long fileId) throws ServiceException
	{
        try {
    		File file = fileDao.findById(fileId);
    		MailBatchFile mbf = file.getLastMailBatchFile();
    		if (mbf == null) {
    			return null;
    		}
    		Date now = ThreadLocalUtils.getDate();
    		mbf.getMailStatus().setReceivedDate(now);
    		mbf.getMailStatus().setStatus(MailStatusEnum.RECEIVED);
            return now.getTime();
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileServiceRS#findOwnerLegalName(java.lang.Long, java.lang.String)
	 */
	public LabelValues findOwnerLegalName(OwnerTypeEnum ownerType, String legalName)
	    throws ServiceException
	{
        try {
        	LabelValues result = new LabelValues();
        	for (Owner owner : ownerDao.findByLegalNameLike(ownerType, legalName)) {
            	result.getRecords().add(new LabelValue(owner.getOwnerId(), owner.getLegalName()));
        	}
		    return result;
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileServiceRS#findOwnerContactName(java.lang.Long, java.lang.String)
	 */
	public LabelValues findOwnerContactName(OwnerTypeEnum ownerType, String contactName)
	    throws ServiceException
	{
        try {
        	LabelValues result = new LabelValues();
        	for (Owner owner : ownerDao.findByContactName(ownerType, contactName)) {
            	result.getRecords().add(new LabelValue(owner.getOwnerId(), owner.getContact().getName()));
        	}
		    return result;
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileServiceRS#aseKeyContactName(java.lang.String)
	 */
	public LabelValues aseKeyContactName(String contactName) throws ServiceException
	{
        try {
        	AseKeySearchCriteria criteria = new AseKeySearchCriteria();
        	criteria.setContactName(contactName);
        	LabelValues result = new LabelValues();
        	for (AseKey aseKey : aseKeyDao.findByCriteria(criteria)) {
        		Contact c = aseKey.getContact();
            	result.getRecords().add(new LabelValue(c.getContactId(), c.getName()));
        	}
		    return result;
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileServiceRS#aseKeyOrderContactName(java.lang.String)
	 */
	public LabelValues aseKeyOrderContactName(String contactName) throws ServiceException
	{
        try {
        	AseKeySearchCriteria criteria = new AseKeySearchCriteria();
        	criteria.setContactName(contactName);
        	LabelValues result = new LabelValues();
        	for (AseKeyOrder aseKeyOrder : aseKeyOrderDao.findByCriteria(criteria)) {
        		Contact c = aseKeyOrder.getContact();
            	result.getRecords().add(new LabelValue(c.getContactId(), c.getName()));
        	}
		    return result;
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileServiceRS#updateName(java.lang.Long, java.lang.String, java.lang.String)
	 */
	public String updateName(Long fileId, String oldName, String newName)
	    throws ServiceException
	{
        try {
        	File file = fileDao.findById(fileId);
        	return documentService.updateName(file.getFca(), oldName, newName);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileServiceRS#uploadFile(java.lang.Long, java.lang.String, java.util.List)
	 */
	public String uploadFile(Long fileId, String dir, List<Attachment> attachments)
	    throws ServiceException
	{
        try {
        	File file = fileDao.findById(fileId);
        	for (Attachment attachment : attachments) {
        		DataHandler dataHandler = attachment.getDataHandler();
        		String contentType = dataHandler.getContentType();
        		if (contentType != null) {
            		documentService.uploadFile(file.getFca(), dir, dataHandler);
        		}
        	};
        	return "";
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

}