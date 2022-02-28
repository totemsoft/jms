package au.gov.qld.fire.jms.service.impl;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.Transformer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;

import au.com.bytecode.opencsv.CSVWriter;
import au.gov.qld.fire.dao.StateDao;
import au.gov.qld.fire.dao.TemplateDao;
import au.gov.qld.fire.domain.BasePair;
import au.gov.qld.fire.domain.MailData;
import au.gov.qld.fire.domain.StringPair;
import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.document.TemplateEnum;
import au.gov.qld.fire.domain.entity.Company;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.location.State;
import au.gov.qld.fire.domain.refdata.AuditStatusEnum;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.exporter.IExporter;
import au.gov.qld.fire.jms.dao.AlarmPanelDao;
import au.gov.qld.fire.jms.dao.ArchiveDao;
import au.gov.qld.fire.jms.dao.AseChangeDao;
import au.gov.qld.fire.jms.dao.AseFileDao;
import au.gov.qld.fire.jms.dao.AseKeyDao;
import au.gov.qld.fire.jms.dao.AseKeyOrderDao;
import au.gov.qld.fire.jms.dao.BuildingContactDao;
import au.gov.qld.fire.jms.dao.BuildingDao;
import au.gov.qld.fire.jms.dao.FcaDao;
import au.gov.qld.fire.jms.dao.FileAuditDao;
import au.gov.qld.fire.jms.dao.FileDao;
import au.gov.qld.fire.jms.dao.FileDocChkListDao;
import au.gov.qld.fire.jms.dao.FileDocDao;
import au.gov.qld.fire.jms.dao.IsolationHistoryDao;
import au.gov.qld.fire.jms.dao.KeyReceiptDao;
import au.gov.qld.fire.jms.dao.KeyRegDao;
import au.gov.qld.fire.jms.dao.MailRegisterDao;
import au.gov.qld.fire.jms.dao.OwnerDao;
import au.gov.qld.fire.jms.dao.RfiDao;
import au.gov.qld.fire.jms.dao.SapHeaderDao;
import au.gov.qld.fire.jms.domain.ConvertUtils;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.KeyReceipt;
import au.gov.qld.fire.jms.domain.KeyReg;
import au.gov.qld.fire.jms.domain.ase.AlarmPanel;
import au.gov.qld.fire.jms.domain.ase.AlarmPanelPK;
import au.gov.qld.fire.jms.domain.ase.AseChange;
import au.gov.qld.fire.jms.domain.ase.AseChangeSearchCriteria;
import au.gov.qld.fire.jms.domain.ase.AseFile;
import au.gov.qld.fire.jms.domain.ase.AseKey;
import au.gov.qld.fire.jms.domain.ase.AseKeyInvoice;
import au.gov.qld.fire.jms.domain.ase.AseKeyOrder;
import au.gov.qld.fire.jms.domain.ase.AseKeySearchCriteria;
import au.gov.qld.fire.jms.domain.building.Building;
import au.gov.qld.fire.jms.domain.building.BuildingContact;
import au.gov.qld.fire.jms.domain.building.BuildingContact.BuildingContactComparator;
import au.gov.qld.fire.jms.domain.entity.Owner;
import au.gov.qld.fire.jms.domain.file.ActiveAseFile;
import au.gov.qld.fire.jms.domain.file.ActiveFile;
import au.gov.qld.fire.jms.domain.file.Archive;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.file.FileArchiveSearchCriteria;
import au.gov.qld.fire.jms.domain.file.FileAudit;
import au.gov.qld.fire.jms.domain.file.FileDoc;
import au.gov.qld.fire.jms.domain.file.FileDocChkList;
import au.gov.qld.fire.jms.domain.file.FileSearchCriteria;
import au.gov.qld.fire.jms.domain.isolation.IsolationHistory;
import au.gov.qld.fire.jms.domain.isolation.IsolationHistorySearchCriteria;
import au.gov.qld.fire.jms.domain.mail.MailRegister;
import au.gov.qld.fire.jms.domain.mail.MailRegisterSearchCriteria;
import au.gov.qld.fire.jms.domain.refdata.BuildingContactType;
import au.gov.qld.fire.jms.domain.refdata.SiteType;
import au.gov.qld.fire.jms.domain.sap.Rfi;
import au.gov.qld.fire.jms.domain.sap.SapHeader;
import au.gov.qld.fire.jms.service.DocumentService;
import au.gov.qld.fire.jms.service.FileService;
import au.gov.qld.fire.service.EmailService;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.service.SmsService;
import au.gov.qld.fire.service.ValidationException;
import au.gov.qld.fire.util.Formatter;
import au.gov.qld.fire.util.ThreadLocalUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FileServiceImpl implements FileService
{

    /** logger. */
    private static final Logger LOG = Logger.getLogger(FileServiceImpl.class);

    @Autowired private AlarmPanelDao alarmPanelDao;

    @Autowired private ArchiveDao archiveDao;

    @Autowired private BuildingDao buildingDao;

    @Autowired private BuildingContactDao buildingContactDao;

    @Autowired private FcaDao fcaDao;

    @Autowired private FileAuditDao fileAuditDao;

    @Autowired private FileDao fileDao;

    @Autowired private FileDocDao fileDocDao;

    @Autowired private FileDocChkListDao fileDocChkListDao;

    @Autowired private IsolationHistoryDao isolationHistoryDao;

    @Autowired private KeyReceiptDao keyReceiptDao;

    @Autowired private KeyRegDao keyRegDao;

    @Autowired private MailRegisterDao mailRegisterDao;

    @Autowired private OwnerDao ownerDao;

    @Autowired private AseChangeDao aseChangeDao;

    @Autowired private AseFileDao aseFileDao;

    @Autowired private AseKeyDao aseKeyDao;
    @Autowired private AseKeyOrderDao aseKeyOrderDao;

    @Autowired private RfiDao rfiDao;

    @Autowired private SapHeaderDao sapHeaderDao;

    @Autowired private StateDao stateDao;

    @Autowired private TemplateDao templateDao;

    @Autowired private IExporter exporter;

    @Autowired private DocumentService documentService;

    @Autowired private EmailService emailService;

    @Autowired private SmsService smsService;

    private HttpMessageConverter<File> fileConverter;

	public void setFileConverter(HttpMessageConverter<File> fileConverter) {
		this.fileConverter = fileConverter;
	}

    /**
     * 
     */
    private final static Transformer EXPORT_TRANSFORMER = new Transformer()
    {
        public Object transform(Object input)
        {
            if (input instanceof Date)
            {
                return Formatter.formatDate((Date) input);
            }
            return input;
        }
    };

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileService#findBuildings(java.lang.String)
	 */
	public List<BasePair> findBuildings(String buildingName) throws ServiceException
	{
        try
        {
        	List<Building> entities = buildingDao.findByNameLike(buildingName);
        	List<BasePair> result = new ArrayList<BasePair>();
        	for (Building entity : entities) {
        		result.add(new BasePair(entity.getId(), entity.getName()));
        	}
            return result;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findBuildingName(java.lang.String)
     */
    public List<String> findBuildingName(String buildingName) throws ServiceException
    {
        try
        {
            return buildingDao.findBuildingName(buildingName);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findBuildingAddress(java.lang.String)
     */
    public List<String> findBuildingAddress(String buildingAddress) throws ServiceException
    {
        try
        {
            return buildingDao.findBuildingSuburb(buildingAddress);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findFca(java.lang.String, boolean)
     */
    public List<BasePair> findFca(String fcaNo, boolean unassignedFca) throws ServiceException
    {
        try
        {
            return fcaDao.findFca(fcaNo, unassignedFca);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findFileNo(java.lang.String)
     */
    public List<BasePair> findFileNo(String fileNo) throws ServiceException
    {
        try
        {
            return fileDao.findFileNo(fileNo);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findOwnerContact(java.lang.String)
     */
    public List<String> findOwnerContact(String ownerContact) throws ServiceException
    {
        try
        {
            return ownerDao.findContactSurname(ownerContact);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findOwnerName(java.lang.String)
     */
    public List<String> findOwnerName(String ownerName) throws ServiceException
    {
        try
        {
            return ownerDao.findLegalName(ownerName);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findFiles()
     */
    public List<File> findFiles() throws ServiceException
    {
        try
        {
            return fileDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findFiles(au.gov.qld.fire.jms.domain.file.FileSearchCriteria)
     */
    public List<ActiveFile> findFiles(FileSearchCriteria criteria) throws ServiceException
    {
        try
        {
            //            //enforce at least some criteria.
            //            if (StringUtils.isBlank(criteria.getFileNo())
            //                && StringUtils.isBlank(criteria.getFcaNo())
            //                && StringUtils.isBlank(criteria.getBuildingName()))
            //            {
            //                return Collections.EMPTY_LIST;
            //            }
            return fileDao.findFilesByCriteria(criteria);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileService#findFileArchive(java.lang.Long)
	 */
	public Archive findArchive(Long archiveId) throws ServiceException
	{
        try
        {
            return archiveDao.findById(archiveId);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	public List<Archive> findArchives(FileArchiveSearchCriteria criteria) throws ServiceException
	{
        try
        {
            return archiveDao.findByCriteria(criteria);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileService#findArchives(java.lang.String)
	 */
	public List<StringPair> findArchives(String archiveCode) throws ServiceException
	{
        try
        {
        	List<StringPair> result = new ArrayList<StringPair>();
        	for (Archive entity : archiveDao.findByCodeLike(archiveCode))
        	{
        		result.add(new StringPair(entity.getName(), entity.getCode()));
        	}
            return result;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileService#saveFileArchive(au.gov.qld.fire.jms.domain.file.Archive)
	 */
	public void saveArchive(Archive dto) throws ServiceException
	{
        try
        {
            // find by id
            Long id = dto.getId();
            Archive entity = null;
            if (id != null)
            {
                entity = archiveDao.findById(id);
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

            // optional

            // save
            archiveDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findAseChangeFiles(AseChangeSearchCriteria)
     */
    public List<ActiveAseFile> findAseChangeFiles(AseChangeSearchCriteria criteria)
        throws ServiceException
    {
        try
        {
            List<ActiveAseFile> files = fileDao.findAseChangeFiles(criteria);
            //
            Iterator<ActiveAseFile> iter = files.iterator();
            while (iter.hasNext())
            {
            	ActiveAseFile activeFile = iter.next();
                File file = fileDao.findById(activeFile.getFileId());
                AseFile aseFile = file.getAseFile();
                AseChange aseChange = aseFile == null ? null : aseFile.getAseChange();
                if (aseChange == null)
                {
                    //no ASE change have been created
                    continue;
                }
                activeFile.setAseChangeDate(aseChange.getDateChange());
                if (aseChange.getAseChangeSuppliers().isEmpty())
                {
                    //no ASE change supplier(s) have been set
                    continue;
                }
                //ASE Installation or Carrier Installations or Both that are incomplete
                if (aseChange.isAseChangeCompleted())
                {
                    //Exclude completed ASE Changes
                    iter.remove();
                    continue;
                }
                //FIXME: could not work out hql for aseChangeSuppliers.supplier.name
                if (StringUtils.isNotBlank(criteria.getSupplierName())
                    && !aseChange.hasSupplier(criteria.getSupplierName()))
                {
                    iter.remove();
                    continue;
                }
            }
            return files;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findAseChangeByDateChange(FileSearchCriteria, java.util.Date)
     */
    public List<AseChange> findAseChangeByDateChange(FileSearchCriteria criteria, Date dateChange)
        throws ServiceException
    {
        try
        {
            List<AseChange> result = aseChangeDao.findByDateChange(criteria, dateChange);
            //FIXME: could not work out hql for aseChangeSuppliers.supplier.name
            if (StringUtils.isNotBlank(criteria.getSupplierName()) && !result.isEmpty())
            {
                Iterator<AseChange> iter = result.iterator();
                while (iter.hasNext())
                {
                    AseChange aseChange = iter.next();
                    if (aseChange.getAseChangeSuppliers().isEmpty())
                    {
                        //no ASE change supplier(s) have been set
                        continue;
                    }
                    if (!aseChange.hasSupplier(criteria.getSupplierName()))
                    {
                        iter.remove();
                        continue;
                    }
                }
            }
            return result;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findFileById(java.lang.Long)
     */
    public File findFileById(Long fileId) throws ServiceException
    {
        try
        {
            return fileDao.findById(fileId);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findFileAuditById(java.lang.Long)
     */
	public File findFileAuditById(Long fileId) throws ServiceException
	{
        try
        {
        	File file = fileDao.findById(fileId);
        	FileAudit fileAudit = file.getFileAudit();
        	if (fileAudit == null) {
        		return null;
        	}
            return File.convert(fileAudit.getContent(), fileConverter);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileService#saveFileAudit(au.gov.qld.fire.jms.domain.file.File)
	 */
	public void saveFileAudit(File auditedFile) throws ServiceException
	{
        try
        {
        	Long fileId = auditedFile.getFileId();
        	File file = fileDao.findById(fileId);
        	if (file == null) {
        		return; // throw ???
        	}
        	// save for audit purpose
        	FileAudit fileAudit = file.getFileAudit();
        	if (fileAudit != null) {
            	fileAudit.setContent(File.convert(file, fileConverter));
            	fileAudit.setStatus(AuditStatusEnum.APPROVED);
            	fileAuditDao.save(fileAudit);
        	} else {
        		// audit file UI used for file quick edit
        	}
            final State defaultState = findDefaultState();
        	// copy/save to original file
        	ConvertUtils.copyAuditedProperties(auditedFile, file);
        	// manual cascade of file audit properties
        	fileDao.save(file);
            sapHeaderDao.saveOrUpdate(file.getSapHeader());
            buildingDao.saveOrUpdate(file.getBuilding());
            for (Owner owner : file.getOwners()) {
            	if (owner.getAddress().getState().getId() == null) {
            		owner.getAddress().setState(defaultState);
            	}
                ownerDao.saveOrUpdate(owner);
            }
            for (BuildingContact bc : file.getBuildingContacts()) {
            	Company company = bc.getCompany();
            	if ((bc.isSecurity() || bc.isFire()) && company != null && StringUtils.isBlank(company.getName())) {
            		continue; // throw ???
            	}
            	Contact c = bc.getContact();
            	if (bc.isOwner() && StringUtils.isBlank(c.getFirstName()) && StringUtils.isBlank(c.getSurname())) {
            		continue; // skip empty contact
            	}
            	buildingContactDao.save(bc);
            }
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findFileBySapCustNo(java.lang.Long)
     */
    public File findFileBySapCustNo(Long sapCustNo) throws ServiceException
    {
        try
        {
            return fileDao.findBySapCustNo(sapCustNo);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#saveFile(au.gov.qld.fire.jms.domain.file.File)
     */
    public void saveFile(File file) throws ServiceException
    {
        try
        {
            // find by id
            Long id = file.getId();
            File entity = null;
            if (id != null)
            {
                entity = fileDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = file;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(file, entity);
            }

            // optional

            // save
            fileDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findBuildingById(java.lang.Long)
     */
    public Building findBuildingById(Long id) throws ServiceException
    {
        try
        {
            return buildingDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#saveBuilding(au.gov.qld.fire.jms.domain.building.Building)
     */
    public void saveBuilding(Building dto) throws ServiceException
    {
        try
        {
            // find by id
            Long id = dto.getId();
            Building entity = null;
            if (id != null)
            {
                entity = buildingDao.findById(id);
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

            // optional

            //save
            buildingDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findOwnerById(java.lang.Long)
     */
    public Owner findOwnerById(Long id) throws ServiceException
    {
        try
        {
            return ownerDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#saveOwner(au.gov.qld.fire.jms.domain.entity.Owner)
     */
    public void saveOwner(Owner dto) throws ServiceException
    {
        try
        {
            // find by id
            Long id = dto.getId();
            Owner entity = null;
            if (id != null)
            {
                entity = ownerDao.findById(id);
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

            // optional
            File file = fileDao.findById(dto.getFile().getId());
            for (Owner owner : file.getOwners())
            {
                if (owner.isDefaultContact() && entity.isDefaultContact() && !owner.getId().equals(entity.getId()))
                {
                    owner.setDefaultContact(false);
                }
            }

            // save
            ownerDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#saveMultiSite(au.gov.qld.fire.jms.domain.Fca, au.gov.qld.fire.domain.BasePair, java.util.List)
     */
    public void saveMultiSite(Fca fca, BasePair parent, List<BasePair> children)
    {
        try
        {
            // reload owner fca
            fca = fcaDao.findById(fca.getId());
            if (fca != null)
            {
                // parent
                Fca parentFca = fcaDao.findById(parent.getValue());
                if (parentFca != null)
                {
                    fca.setParent(parentFca);
                    fcaDao.save(parentFca);
                }
                // children
                for (BasePair child : children)
                {
                    Fca childFca = fcaDao.findById(child.getValue());
                    if (childFca != null)
                    {
                        childFca.setParent(fca);
                        fcaDao.save(childFca);
                    }
                }
            }
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#saveAseFile(au.gov.qld.fire.jms.domain.ase.AseFile, SiteType)
     */
    public void saveAseFile(AseFile dto, SiteType siteType) throws ServiceException
    {
        try
        {
            // find by id
            Long id = dto.getId();
            AseFile entity = null;
            if (id != null)
            {
                entity = aseFileDao.findById(id);
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

            // optional for AseFile
            if (entity.getSupplier() != null && entity.getSupplier().getId() == null)
            {
                entity.setSupplier(null);
            }
            // not used anymore
            AseChange aseChange = entity.getAseChange();
            if (aseChange != null && aseChange.getId() == null)
            {
                entity.setAseChange(null);
            }
            // optional for File
            if (siteType != null)
            {
                File file = fileDao.findById(entity.getFile().getId());
                file.setSiteType(siteType.getId() == null ? null : siteType);
                fileDao.saveOrUpdate(file);
            }

            // save
            aseFileDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findAseFile(java.lang.Long)
     */
    public AseFile findAseFile(Long aseFileId) throws ServiceException
    {
        try
        {
            return aseFileDao.findById(aseFileId);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findAseKeyNo(java.lang.String)
     */
    public List<String> findAseKeyNo(String aseKeyNo) throws ServiceException
    {
        try {
            return aseKeyDao.findAseKeyNo(aseKeyNo);
        }
        catch (Exception e) {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileService#findAseKeyOrderNo(java.lang.String)
	 */
	@Override
	public List<String> findAseKeyOrderNo(String orderNo) throws ServiceException
	{
        try {
            return aseKeyOrderDao.findOrderNo(orderNo);
        }
        catch (Exception e) {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findAseKey(java.lang.Long)
     */
    public AseKey findAseKey(Long aseKeyId) throws ServiceException
    {
        try {
            return aseKeyDao.findById(aseKeyId);
        }
        catch (Exception e) {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#saveAseKey(au.gov.qld.fire.jms.domain.ase.AseKey)
     */
    public void saveAseKey(AseKey dto) throws ServiceException
    {
        try {
            // find by id
            Long id = dto.getId();
            AseKey entity = id == null ? null : aseKeyDao.findById(id);
            // and copy
            boolean newEntity = entity == null;
            if (newEntity) {
                entity = dto;
            }
            else {
                ConvertUtils.copyProperties(dto, entity);
            }
            // optional for AseKey
            if (entity.getSupplier() != null && entity.getSupplier().getId() == null) {
                entity.setSupplier(null);
            }
            // save
            aseKeyDao.saveOrUpdate(entity);
        }
        catch (Exception e) {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findAseFiles(au.gov.qld.fire.jms.domain.ase.AseKeySearchCriteria)
     */
    public List<AseKey> findAseKey(AseKeySearchCriteria criteria) throws ServiceException
    {
        try {
            return aseKeyDao.findByCriteria(criteria);
        }
        catch (Exception e) {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#export(java.util.List, java.io.ByteArrayOutputStream, au.gov.qld.fire.domain.refdata.ContentTypeEnum)
     */
    public void exportAseKey(List<AseKey> entities, ByteArrayOutputStream contentStream, ContentTypeEnum contentType)
        throws ServiceException
    {
        if (entities.isEmpty()) {
            return;
        }
        try {
            if (ContentTypeEnum.isCsv(contentType)) {
                export2csv(entities.toArray(new AseKey[0]), contentStream);
            }
        }
        catch (Exception e) {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileService#findAseKeyOrder(java.lang.Long)
	 */
	public AseKeyOrder findAseKeyOrder(Long aseKeyOrderId) throws ServiceException
	{
        try {
            return aseKeyOrderDao.findById(aseKeyOrderId);
        }
        catch (Exception e) {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileService#saveAseKeyOrder(au.gov.qld.fire.jms.domain.ase.AseKeyOrder)
	 */
	public void saveAseKeyOrder(AseKeyOrder dto) throws ServiceException
	{
        try {
            // find by id
            Long id = dto.getId();
            AseKeyOrder entity = id == null ? null : aseKeyOrderDao.findById(id);
            // and copy
            boolean newEntity = entity == null;
            if (newEntity) {
                entity = dto;
            }
            else {
                ConvertUtils.copyProperties(dto, entity);
            }
            // optional for AseKeyOrder
            // save
            aseKeyOrderDao.saveOrUpdate(entity);
        }
        catch (Exception e) {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileService#findAseKeyOrder(au.gov.qld.fire.jms.domain.ase.AseKeySearchCriteria)
	 */
	public List<AseKeyOrder> findAseKeyOrder(AseKeySearchCriteria criteria) throws ServiceException
	{
        try {
            return aseKeyOrderDao.findByCriteria(criteria);
        }
        catch (Exception e) {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileService#exportAseKeyOrder(java.util.List, java.io.ByteArrayOutputStream, au.gov.qld.fire.domain.refdata.ContentTypeEnum)
	 */
	public void exportAseKeyOrder(List<AseKeyOrder> entities, ByteArrayOutputStream contentStream, ContentTypeEnum contentType) throws ServiceException
	{
        if (entities.isEmpty()) {
            return;
        }
        try {
            if (ContentTypeEnum.isCsv(contentType)) {
                //export2csv(entities.toArray(new AseKeyOrder[0]), contentStream);
            }
        }
        catch (Exception e) {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	private void export2csv(AseKey[] entities, ByteArrayOutputStream contentStream) throws Exception
    {
        Writer writer = new BufferedWriter(new OutputStreamWriter(contentStream));
        CSVWriter csvWriter = new CSVWriter(writer, ',');
        // header
        List<String> header = new ArrayList<String>();
        header.add("ASE Key ID");
        header.add("ASE Key No");
        header.add("Supplier Name");
        header.add("Supplier Contact");
        header.add("Date Sent to ADT");
        header.add("Date Sent to Customer");
        header.add("Method");
        header.add("Reference Number");
        header.add("Payment Method");
        header.add("Date of AseKeyInvoice");
        header.add("AseKeyInvoice Number");
        csvWriter.writeNext(header.toArray(new String[0]));
        // data
        for (AseKey entity : entities)
        {
            List<String> row = new ArrayList<String>();
            row.add("" + entity.getId());
            row.add(entity.getAseKeyNo());
            Supplier supplier = entity.getSupplier();
            row.add(supplier == null ? null : supplier.getName());
            Contact contact = supplier == null ? null : supplier.getContact();
            row.add(contact == null ? null : contact.getName());
            row.add(Formatter.formatDate(entity.getSentAdtDate()));
            row.add(Formatter.formatDate(entity.getSentCustomerDate()));
            row.add(entity.getSentMethod() == null ? null : entity.getSentMethod().getName());
            row.add(entity.getSentReferenceNo());
            row.add(entity.getPaymentMethod() == null ? null : entity.getPaymentMethod().getName());
            AseKeyInvoice invoice = entity.getInvoice();
            row.add(invoice == null ? null : Formatter.formatDate(invoice.getInvoiceDate()));
            row.add(invoice == null ? null : invoice.getInvoiceNo());
            csvWriter.writeNext(row.toArray(new String[0]));
        }
        csvWriter.close();
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findMailRegister(java.lang.Long)
     */
    public MailRegister findMailRegister(Long mailRegisterId) throws ServiceException
    {
        try
        {
            return mailRegisterDao.findById(mailRegisterId);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#saveMailRegister(au.gov.qld.fire.jms.domain.mail.MailRegister)
     */
    public MailRegister saveMailRegister(MailRegister dto, Owner owner) throws ServiceException
    {
        try
        {
            // find by id
            Long id = dto.getId();
            MailRegister entity = null;
            if (id != null)
            {
                entity = mailRegisterDao.findById(id);
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

            // optional for MailRegister
            if (owner != null && owner.getId() != null && owner.getId() > 0L)
            {
                // reload owner
                owner = ownerDao.findById(owner.getId());
                // copy address/contact details
                Address address = entity.getAddress() == null ? new Address() : entity.getAddress();
                ConvertUtils.copyProperties(owner.getAddress(), address);
                entity.setAddress(address);
                Contact contact = entity.getContact() == null ? new Contact() : entity.getContact();
                ConvertUtils.copyProperties(owner.getContact(), contact);
                entity.setContact(contact);
            }

            // save
            mailRegisterDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
            return entity;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findMailRegister(au.gov.qld.fire.jms.domain.mail.MailRegisterSearchCriteria)
     */
    public List<MailRegister> findMailRegister(MailRegisterSearchCriteria criteria)
        throws ServiceException
    {
        try
        {
            return mailRegisterDao.findByCriteria(criteria);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#exportMailRegister(java.util.List, java.io.ByteArrayOutputStream, au.gov.qld.fire.domain.refdata.ContentTypeEnum)
     */
    public void exportMailRegister(List<MailRegister> entities,
        ByteArrayOutputStream contentStream, ContentTypeEnum contentType)
        throws ServiceException
    {
        final String PROPERTIES = "/au/gov/qld/fire/jms/export/mailRegister.properties";
        final String[] GROUPS = {"header", "row"};
        InputStream is = getClass().getResourceAsStream(PROPERTIES);
        try
        {
            if (ContentTypeEnum.isCsv(contentType))
            {
                Writer writer = new BufferedWriter(new OutputStreamWriter(contentStream));
                CSVWriter csvWriter = new CSVWriter(writer, ',');
                //
                Map<String, List<String>> props = exporter.loadProperties(is, GROUPS);
                List<String> headerProps = props.get(GROUPS[0]);
                List<String> rowProps = props.get(GROUPS[1]);
                // header
                csvWriter.writeNext(headerProps.toArray(new String[0]));
                // data
                for (MailRegister entity : entities)
                {
                    csvWriter.writeNext(exporter.createLine(entity, rowProps, EXPORT_TRANSFORMER));
                }
                csvWriter.close();
            }
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
        finally
        {
            IOUtils.closeQuietly(is);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#saveKeyRegs(java.lang.Long, boolean, KeyReceipt, java.util.List)
     */
    public void saveKeyReceipt(Long fileId, boolean keyRequired, KeyReceipt keyReceipt, List<KeyReg> keyRegs)
        throws ServiceException
    {
        try
        {
            // find by id
            File file = fileDao.findById(fileId);

            // validate
            String keyReceiptNo = keyReceipt.getKeyReceiptNo();
            KeyReceipt duplicate = keyReceiptDao.findByKeyReceiptNo(keyReceiptNo);
            if (duplicate != null && !duplicate.getFile().equals(file))
            {
                throw new ValidationException("Not unique keyReceiptNo: " + keyReceiptNo);
            }

            // save keyRequired
            Building building = file.getBuilding();
            building.setKeyRequired(keyRequired);
            buildingDao.save(building);

            // get old KeyReceipt
            KeyReceipt oldKeyReceipt = file.getKeyReceipt();
            // save
            if (oldKeyReceipt != null)
            {
                ConvertUtils.copyProperties(keyReceipt, oldKeyReceipt);
                keyReceiptDao.saveOrUpdate(oldKeyReceipt);
            }
            else
            {
                keyReceiptDao.saveOrUpdate(keyReceipt);
            }

            // get old KeyReg(s)
            List<KeyReg> oldKeyRegs = file.getKeyRegs();
            // save
            for (KeyReg keyReg : keyRegs)
            {
                keyReg.setFile(file);
                int index = oldKeyRegs.indexOf(keyReg);
                if (index >= 0)
                {
                    // old updated
                    KeyReg oldKeyReg = oldKeyRegs.get(index);
                    oldKeyRegs.remove(index);
                    ConvertUtils.copyProperties(keyReg, oldKeyReg);
                    keyRegDao.saveOrUpdate(oldKeyReg);
                }
                else
                {
                    // new added
                    keyRegDao.saveOrUpdate(keyReg);
                }
            }
            // delete
            for (KeyReg keyReg : oldKeyRegs)
            {
                keyRegDao.delete(keyReg);
            }
        }
        catch (ValidationException e) {
        	throw e;
        }
        catch (Exception e) {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#saveBuildingContacts(java.lang.Long, java.util.List)
     */
    public void saveBuildingContacts(Long fileId, List<BuildingContact> buildingContacts)
        throws ServiceException
    {
        try
        {
            File file = fileDao.findById(fileId);
            // save Building changes (should exist by now)
            //saveBuilding(file.getBuilding());

            final State defaultState = findDefaultState();

            // sort new buildingContacts
        	Collections.sort(buildingContacts, BuildingContactComparator.getInstance());
        	// validate unique priority
        	BuildingContactType bct = null;
        	int p = -1;
            for (BuildingContact bc : buildingContacts) {
            	if ((bct != null && bct.equals(bc.getBuildingContactType())) && bc.getPriority() == p) {
            		throw new ServiceException("Two or more of the contact names you have entered have the same priority number [" + p + "0]. Please amend before saving.");
            	}
            	bct = bc.getBuildingContactType();
        		p = bc.getPriority();
            }

            // get old BuildingContact(s)
            List<BuildingContact> oldBuildingContacts = file.getBuildingContacts();
            // save
            int ownerIndex = 0, fireIndex = 0, securityIndex = 0;
            for (BuildingContact bc : buildingContacts) {
                bc.setFile(file);

                Company company = bc.getCompany();
                if (company != null && company.getId() == null) {
                    if (company.getAddress().getState().getState() == null) {
                        company.getAddress().setState(defaultState);
                    }
                    if (company.getPostAddress().getState().getState() == null) {
                        company.getPostAddress().setState(defaultState);
                    }
                }

                int priority = 0;
                if (bc.isActive()) {
                	priority = bc.isOwner() ? ++ownerIndex : (bc.isFire() ? ++fireIndex : (bc.isSecurity() ? ++securityIndex : 0));
                }
                if (oldBuildingContacts.contains(bc)) {
                    int index = oldBuildingContacts.indexOf(bc);
                    // old updated
                    BuildingContact oldBuildingContact = oldBuildingContacts.get(index);
                    //oldBuildingContact = buildingContactDao.findById(oldBuildingContact.getBuildingContactId()); // refresh
                    oldBuildingContacts.remove(index);
                    ConvertUtils.copyProperties(bc, oldBuildingContact);
                    oldBuildingContact.setPriority(priority);
                    buildingContactDao.saveOrUpdate(oldBuildingContact);
                }
                else {
                    // new added
                    Document d = bc.getDocument();
                    if (d != null && d.getContent() == null) {
                        bc.setDocument(null);
                    }
                    bc.setPriority(priority);
                    buildingContactDao.saveOrUpdate(bc);
                }
            }
            // delete removed
            for (BuildingContact oldBuildingContact : oldBuildingContacts) {
                buildingContactDao.delete(oldBuildingContact);
            }

        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#saveAlarmPanel(au.gov.qld.fire.jms.domain.ase.AlarmPanel)
     */
    public void saveAlarmPanel(AlarmPanel alarmPanel) throws ServiceException
    {
        try
        {
            //find by id
            AlarmPanelPK id = alarmPanel.getId();
            AlarmPanel entity = null;
            if (id != null)
            {
                entity = alarmPanelDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = alarmPanel;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(alarmPanel, entity);
            }

            //optional

            //save
            alarmPanelDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findSapCustNo(java.lang.String)
     */
    public List<String> findSapCustNo(String sapCustNo) throws ServiceException
    {
        try
        {
            return sapHeaderDao.findSapCustNo(sapCustNo);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#saveSapBilling(java.lang.Long, au.gov.qld.fire.jms.domain.sap.SapHeader, java.util.List)
     */
    public void saveSapBilling(Long fileId, SapHeader sapHeader, List<Rfi> rfis)
        throws ServiceException
    {
        try
        {
            // find by id
            File file = fileDao.findById(fileId);

            // get old SapHeader
            SapHeader oldSapHeader = file.getSapHeader();
            // save
            if (oldSapHeader != null)
            {
                ConvertUtils.copyProperties(sapHeader, oldSapHeader);
                sapHeaderDao.saveOrUpdate(oldSapHeader);
            }
            else
            {
                sapHeaderDao.saveOrUpdate(sapHeader);
            }

            //get old Rfi(s)
            List<Rfi> oldRfis = file.getRfis();
            //save
            for (Rfi rfi : rfis)
            {
                rfi.setFile(file);
                int index = oldRfis.indexOf(rfi);
                if (index >= 0)
                {
                    //old updated
                    Rfi oldRfi = oldRfis.get(index);
                    oldRfis.remove(index);
                    ConvertUtils.copyProperties(rfi, oldRfi);
                    rfiDao.saveOrUpdate(oldRfi);
                }
                else
                {
                    //new added
                    rfiDao.saveOrUpdate(rfi);
                }
            }
            //delete
            for (Rfi rfi : oldRfis)
            {
                rfiDao.delete(rfi);
            }

        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#saveFileDocChkList(java.lang.Long, java.util.List)
     */
    public void saveFileDocChkList(Long fileId, List<FileDocChkList> fileDocChkLists)
        throws ServiceException
    {
        try
        {
            //find by id
            File file = fileDao.findById(fileId);

            //get old FileDocChkList(s)
            List<FileDocChkList> oldFileDocChkLists = file.getFileDocChkLists();
            //save
            for (FileDocChkList fileDocChkList : fileDocChkLists)
            {
                fileDocChkList.setFile(file);
                int index = oldFileDocChkLists.indexOf(fileDocChkList);
                if (index >= 0)
                {
                    //old updated
                    FileDocChkList oldFileDocChkList = oldFileDocChkLists.get(index);
                    oldFileDocChkLists.remove(index);
                    ConvertUtils.copyProperties(fileDocChkList, oldFileDocChkList);
                    fileDocChkListDao.saveOrUpdate(oldFileDocChkList);
                }
                else
                {
                    //new added
                    fileDocChkListDao.saveOrUpdate(fileDocChkList);
                }
            }
            //delete
            for (FileDocChkList fileDocChkList : oldFileDocChkLists)
            {
                fileDocChkListDao.delete(fileDocChkList);
            }

        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#saveFileDoc(java.lang.Long, java.util.List)
     */
    public void saveFileDoc(Long fileId, List<FileDoc> fileDocs) throws ServiceException
    {
        try
        {
            //find by id
            File file = fileDao.findById(fileId);

            //get old FileDocChkList(s)
            List<FileDoc> oldFileDocs = file.getFileDocs();
            //save
            for (FileDoc fileDoc : fileDocs)
            {
                fileDoc.setFile(file);
                int index = oldFileDocs.indexOf(fileDoc);
                if (index >= 0)
                {
                    //old updated
                    FileDoc oldFileDoc = oldFileDocs.get(index);
                    oldFileDocs.remove(index);
                    ConvertUtils.copyProperties(fileDoc, oldFileDoc);
                    fileDocDao.saveOrUpdate(oldFileDoc);
                }
                else
                {
                    //new added
                    fileDocDao.saveOrUpdate(fileDoc);
                }
            }
            //delete
            for (FileDoc fileDoc : oldFileDocs)
            {
                fileDocDao.delete(fileDoc);
            }

        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#moveFileDoc(java.lang.Long, java.lang.Long, java.lang.String)
     */
    public void moveFileDoc(Long fileDocId, Long moveFileId, String moveFcaId)
        throws ServiceException
    {
        try
        {
            //find by id
            FileDoc fileDoc = fileDocDao.findById(fileDocId);

            //get file to move
            File moveFile = fileDao.findById(moveFileId);
            Fca moveFca = fcaDao.findById(moveFcaId);
            if (moveFile == null)
            {
                moveFile = moveFca.getFile();
            }
            //validation
            if (moveFile == null)
            {
                throw new ServiceException("No file to move found");
            }

            File file = fileDoc.getFile();
            if (moveFile.equals(file))
            {
                return;
            }

            //save
            fileDoc.setFile(moveFile);
            fileDocDao.saveOrUpdate(fileDoc);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findFileContacts(java.lang.Long)
     */
    public List<Contact> findFileContacts(Long fileId) throws ServiceException
    {
        try
        {
            // find by id
            File file = fileDao.findById(fileId);
            if (file == null) {
                return Collections.emptyList();
            }
            List<Contact> result = new ArrayList<Contact>();
            // owner
            Owner owner = file.getOwner();
            if (owner != null) {
            	Contact c = owner.getContact();
                c.setType("Owner");
                result.add(c);
            }
            // bodyCorporate
            Owner bodyCorporate = file.getBodyCorporate();
            if (bodyCorporate != null) {
            	Contact c = bodyCorporate.getContact();
            	c.setType("Body Corporate");
                result.add(c);
            }
            // propertyManager
            Owner propertyManager = file.getPropertyManager();
            if (propertyManager != null) {
            	Contact c = propertyManager.getContact();
            	c.setType("Property Manager");
                result.add(c);
            }
            // tenant
            Owner tenant = file.getTenant();
            if (tenant != null) {
            	Contact c = tenant.getContact();
            	c.setType("Tenant");
                result.add(c);
            }
            // building Contact(s)
            List<BuildingContact> buildingContacts = file.getBuildingContacts();
            for (BuildingContact bc : buildingContacts) {
                bc.setFile(file);
            	Contact c = bc.getContact();
                c.setType(bc.getBuildingContactType().getName());
                result.add(c);
            }
            return result;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findFileRfis(java.lang.Long)
     */
    public List<Rfi> findFileRfis(Long fileId) throws ServiceException
    {
        try
        {
            //find by id
            File file = fileDao.findById(fileId);
            return file.getRfis();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findFileDocChkLists(java.lang.Long)
     */
    public List<FileDocChkList> findFileDocChkLists(Long fileId) throws ServiceException
    {
        try
        {
            //find by id
            File file = fileDao.findById(fileId);
            return file.getFileDocChkLists();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findFileDocs(java.lang.Long)
     */
    public List<FileDoc> findFileDocs(Long fileId) throws ServiceException
    {
        try
        {
            //find by id
            File file = fileDao.findById(fileId);
            return file.getFileDocs();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#saveAseChange(au.gov.qld.fire.jms.domain.ase.AseChange)
     */
    public void saveAseChange(AseChange aseChange) throws ServiceException
    {
        try
        {
            //find by id
            Long id = aseChange.getId();
            AseChange entity = null;
            if (id != null)
            {
                entity = aseChangeDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = new AseChange();
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }

            ConvertUtils.copyProperties(aseChange, entity);

            //save
            aseChangeDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);

            ///////////////////////////////////////
            //Auto-email & SMS on save (fail-safe)
            ///////////////////////////////////////
            User user = ThreadLocalUtils.getUser();
            File file = entity.getAseFile().getFile();
            List<Contact> contacts = findFileContacts(file.getId());
            //////////
            // EMAIL
            MailData mailData = null;
            InputStream content = null;
            try
            {
                content = templateDao.getTemplateContent(TemplateEnum.EMAIL_ASE_CHANGE);
                mailData = createMailData(content, user, file);
                StringBuffer cc = new StringBuffer();
                for (Contact contact : contacts)
                {
                    if (StringUtils.isNotBlank(contact.getEmail()))
                    {
                        cc.append(contact.getEmail()).append(',');
                    }
                }
                mailData.setCc(cc.toString());
                //data.setAttachments(attachments);
                emailService.sendMail(mailData);
            }
            catch (Exception e)
            {
                LOG.warn("FAILED to send EMAIL: " + e.getMessage());
            }
            finally
            {
                IOUtils.closeQuietly(content);
            }
            ////////////////////////////
            // SMS (use email data text)
            for (Contact contact : contacts)
            {
                if (StringUtils.isNotBlank(contact.getMobile()))
                {
                    try
                    {
                        smsService.sendText(contact.getMobile(), mailData.getText());
                    }
                    catch (Exception e)
                    {
                        LOG.warn("FAILED to send SMS: " + e.getMessage());
                    }
                }
            }

        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileService#findIsolationHistory(java.lang.Long)
	 */
	public IsolationHistory findIsolationHistory(Long isolationHistoryId) throws ServiceException
	{
        try
        {
            return isolationHistoryDao.findById(isolationHistoryId);
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findIsolationHistory(au.gov.qld.fire.jms.domain.isolation.IsolationHistorySearchCriteria)
     */
    public List<IsolationHistory> findIsolationHistory(IsolationHistorySearchCriteria criteria)
        throws ServiceException
    {
        try
        {
            return isolationHistoryDao.findByCriteria(criteria);
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.FileService#saveIsolationHistory(au.gov.qld.fire.jms.domain.isolation.IsolationHistory)
	 */
	public void saveIsolationHistory(IsolationHistory isolationHistory) throws ServiceException
	{
        try
        {
            // find by id
            Long id = isolationHistory.getId();
            IsolationHistory entity = null;
            if (id != null)
            {
                entity = isolationHistoryDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = new IsolationHistory();
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }

            // TODO: preserve seconds in date(s)
            ConvertUtils.copyProperties(isolationHistory, entity);

            // save
            isolationHistoryDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);

            // TODO: ??? create isolation action todo (with document/form?)
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#exportIsolationHistory(java.util.List, java.io.ByteArrayOutputStream, au.gov.qld.fire.domain.refdata.ContentTypeEnum)
     */
    public void exportIsolationHistory(List<IsolationHistory> entities,
        OutputStream contentStream, ContentTypeEnum contentType)
        throws ServiceException
    {
        if (entities.isEmpty())
        {
            return;
        }

        try
        {
            if (ContentTypeEnum.isCsv(contentType))
            {
                Writer writer = new BufferedWriter(new OutputStreamWriter(contentStream));
                CSVWriter csvWriter = new CSVWriter(writer, ',');
                // header
                List<String> header = new ArrayList<String>();
                header.add("Isolation ID");
                header.add("FCA No");
                header.add("Input");
                header.add("Isolation Date");
                header.add("De-Isolation Date");
                header.add("Time (seconds)");
                header.add("Details");
                header.add("Key Details");
                csvWriter.writeNext(header.toArray(new String[0]));
                // data
                for (IsolationHistory entity : entities)
                {
                    List<String> row = new ArrayList<String>();
                    row.add("" + entity.getId());
                    row.add(entity.getFca().getFcaId());
                    row.add("" + entity.getInput());
                    row.add(Formatter.formatDateTime(entity.getIsolationDate()));
                    row.add(Formatter.formatDateTime(entity.getDeIsolationDate()));
                    row.add(entity.getIsolationTimeText());
                    row.add(entity.getDetails());
                    row.add(entity.getKeyDetails());
                    csvWriter.writeNext(row.toArray(new String[0]));
                }
                csvWriter.close();
            }
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EmailService#createMailData(java.io.InputStream, au.gov.qld.fire.jms.domain.security.User, au.gov.qld.fire.jms.domain.file.File)
     */
    public MailData createMailData(InputStream content, User user, File file) throws ServiceException
    {
        try
        {
            MailData data = new MailData(content);
            data.setTo(user.getContact().getEmail());
            String text = documentService.updateParameters(data.getText(), user, file);
            data.setText(text);
            return data;
        }
        catch (Exception e)
        {
            throw new ServiceException("Failed to create MailData: " + e.getMessage(), e);
        }
    }

    protected State findDefaultState()
    {
        List<State> states = stateDao.findDefault();
        return states.isEmpty() ? null : states.get(0);
    }

}