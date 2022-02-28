package au.gov.qld.fire.jms.service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.domain.BasePair;
import au.gov.qld.fire.domain.StringPair;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.KeyReceipt;
import au.gov.qld.fire.jms.domain.KeyReg;
import au.gov.qld.fire.jms.domain.ase.AlarmPanel;
import au.gov.qld.fire.jms.domain.ase.AseChange;
import au.gov.qld.fire.jms.domain.ase.AseChangeSearchCriteria;
import au.gov.qld.fire.jms.domain.ase.AseFile;
import au.gov.qld.fire.jms.domain.ase.AseKey;
import au.gov.qld.fire.jms.domain.ase.AseKeyOrder;
import au.gov.qld.fire.jms.domain.ase.AseKeySearchCriteria;
import au.gov.qld.fire.jms.domain.building.Building;
import au.gov.qld.fire.jms.domain.building.BuildingContact;
import au.gov.qld.fire.jms.domain.entity.Owner;
import au.gov.qld.fire.jms.domain.file.ActiveAseFile;
import au.gov.qld.fire.jms.domain.file.ActiveFile;
import au.gov.qld.fire.jms.domain.file.Archive;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.file.FileArchiveSearchCriteria;
import au.gov.qld.fire.jms.domain.file.FileDoc;
import au.gov.qld.fire.jms.domain.file.FileDocChkList;
import au.gov.qld.fire.jms.domain.file.FileSearchCriteria;
import au.gov.qld.fire.jms.domain.isolation.IsolationHistory;
import au.gov.qld.fire.jms.domain.isolation.IsolationHistorySearchCriteria;
import au.gov.qld.fire.jms.domain.mail.MailRegister;
import au.gov.qld.fire.jms.domain.mail.MailRegisterSearchCriteria;
import au.gov.qld.fire.jms.domain.refdata.SiteType;
import au.gov.qld.fire.jms.domain.sap.Rfi;
import au.gov.qld.fire.jms.domain.sap.SapHeader;
import au.gov.qld.fire.service.ServiceException;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface FileService
{

	/**
	 * Search entity id/name by name like 'name%'
	 * @param buildingName
	 * @return
	 * @throws ServiceException
	 */
	List<BasePair> findBuildings(String buildingName) throws ServiceException;

    /**
     * Search entity name by name like 'name%'
     * @param buildingName
     * @return
     * @throws ServiceException
     */
    List<String> findBuildingName(String buildingName) throws ServiceException;

    /**
     * Search entity code/name by name like 'name%'
     * @param buildingAddress
     * @return
     * @throws ServiceException
     */
    List<String> findBuildingAddress(String buildingAddress) throws ServiceException;

    /**
     * Search entity code/name by name like 'name%'
     * @param fcaNo
     * @param unassignedFca
     * @return
     * @throws ServiceException
     */
    List<BasePair> findFca(String fcaNo, boolean unassignedFca) throws ServiceException;

    /**
     * Search entity code/name by name like 'name%'
     * @param fileNo
     * @return
     * @throws ServiceException
     */
    List<BasePair> findFileNo(String fileNo) throws ServiceException;

    /**
     * Search entity code/name by name like 'name%'
     * @param ownerName
     * @return
     * @throws ServiceException
     */
    List<String> findOwnerName(String ownerName) throws ServiceException;

    /**
     * Search entity code/name by name like 'name%'
     * @param ownerContact
     * @return
     * @throws ServiceException
     */
    List<String> findOwnerContact(String ownerContact) throws ServiceException;

    /**
     * Find all active files.
     * @return
     * @throws ServiceException
     */
    List<File> findFiles() throws ServiceException;

    /**
     * 
     * @param fileId
     * @return
     * @throws ServiceException
     */
    File findFileById(Long fileId) throws ServiceException;

	File findFileAuditById(Long fileId) throws ServiceException;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	void saveFileAudit(File auditedFile) throws ServiceException;

    /**
     * 
     * @param sapCustNo
     * @return
     * @throws ServiceException
     */
    File findFileBySapCustNo(Long sapCustNo) throws ServiceException;

    /**
     * 
     * @param criteria
     * @return
     * @throws ServiceException
     */
    List<ActiveFile> findFiles(FileSearchCriteria criteria) throws ServiceException;

    /**
     * 
     * @param archiveId
     * @return
     * @throws ServiceException
     */
	Archive findArchive(Long archiveId) throws ServiceException;

	/**
     * 
     * @param criteria
     * @return
     * @throws ServiceException
     */
	List<Archive> findArchives(FileArchiveSearchCriteria criteria) throws ServiceException;

    /**
     * 
     * @param archiveCode (%)
     * @return
     * @throws ServiceException
     */
	List<StringPair> findArchives(String archiveCode) throws ServiceException;

	/**
	 * 
	 * @param dto
	 * @throws ServiceException
	 */
	void saveArchive(Archive dto) throws ServiceException;

    /**
     * Search by FCA, FileNo or Building Name (%)
     * Exclude completed ASE Changes
     * Show only ASE Installation or Carrier Installations or Both that are incomplete
     * Show Only FCA/Files/Buildings that no ASE change job times have been created
     * Limit Search by Supplier Name
     * 
     * @param criteria
     * @return
     * @throws ServiceException
     */
    List<ActiveAseFile> findAseChangeFiles(AseChangeSearchCriteria criteria) throws ServiceException;

    /**
     * 
     * @param criteria
     * @param dateChange
     * @return
     * @throws ServiceException
     */
    List<AseChange> findAseChangeByDateChange(FileSearchCriteria criteria, Date dateChange) throws ServiceException;

    /**
     * 
     * @param entity
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    void saveFile(File file) throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    Building findBuildingById(Long id) throws ServiceException;

    /**
     * 
     * @param building
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveBuilding(Building building) throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    Owner findOwnerById(Long id) throws ServiceException;

    /**
     * 
     * @param owner
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveOwner(Owner owner) throws ServiceException;

    /**
     * 
     * @param fca
     * @param parent
     * @param children
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveMultiSite(Fca fca, BasePair parent, List<BasePair> children);

    /**
     * 
     * @param aseFile
     * @param siteType
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveAseFile(AseFile aseFile, SiteType siteType) throws ServiceException;

    /**
     * 
     * @param aseFileId
     * @return
     * @throws ServiceException
     */
    AseFile findAseFile(Long aseFileId) throws ServiceException;

    /**
     * 
     * @param aseKeyNo
     * @return
     * @throws ServiceException
     */
    List<String> findAseKeyNo(String aseKeyNo) throws ServiceException;
    List<String> findAseKeyOrderNo(String orderNo) throws ServiceException;

    /**
     * 
     * @param aseKeyId
     * @return
     * @throws ServiceException
     */
    AseKey findAseKey(Long aseKeyId)
        throws ServiceException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveAseKey(AseKey dto)
        throws ServiceException;

    List<AseKey> findAseKey(AseKeySearchCriteria criteria)
        throws ServiceException;

    void exportAseKey(List<AseKey> entities,
        ByteArrayOutputStream contentStream, ContentTypeEnum contentType)
        throws ServiceException;

    /**
     * 
     * @param aseKeyOrderId
     * @return
     * @throws ServiceException
     */
    AseKeyOrder findAseKeyOrder(Long aseKeyOrderId)
        throws ServiceException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveAseKeyOrder(AseKeyOrder dto)
        throws ServiceException;

    List<AseKeyOrder> findAseKeyOrder(AseKeySearchCriteria criteria)
        throws ServiceException;

    void exportAseKeyOrder(List<AseKeyOrder> entities,
        ByteArrayOutputStream contentStream, ContentTypeEnum contentType)
        throws ServiceException;

    /**
     * 
     * @param mailRegisterId
     * @return
     * @throws ServiceException
     */
    MailRegister findMailRegister(Long mailRegisterId)
        throws ServiceException;

    /**
     * Requires new txn, as can create following job action (if rts)
     * @param dto
     * @param owner
     * @return
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    MailRegister saveMailRegister(MailRegister dto, Owner owner)
        throws ServiceException;

    List<MailRegister> findMailRegister(MailRegisterSearchCriteria criteria)
        throws ServiceException;

    void exportMailRegister(List<MailRegister> entities,
        ByteArrayOutputStream contentStream, ContentTypeEnum contentType)
        throws ServiceException;

    /**
     * 
     * @param fileId
     * @param keyRequired
     * @param keyReceipt
     * @param keyRegs
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveKeyReceipt(Long fileId, boolean keyRequired, KeyReceipt keyReceipt, List<KeyReg> keyRegs)
        throws ServiceException;

    /**
     * 
     * @param fileId
     * @param buildingContacts
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveBuildingContacts(Long fileId, List<BuildingContact> buildingContacts)
        throws ServiceException;

    /**
     * 
     * @param alarmPanel
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveAlarmPanel(AlarmPanel alarmPanel) throws ServiceException;

    /**
     * 
     * @param sapCustNo
     * @return
     * @throws ServiceException
     */
    List<String> findSapCustNo(String sapCustNo) throws ServiceException;

    /**
     * 
     * @param fileId
     * @param sapHeader
     * @param rfis
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveSapBilling(Long fileId, SapHeader sapHeader, List<Rfi> rfis) throws ServiceException;

    /**
     * 
     * @param fileId
     * @param fileDocChkLists
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveFileDocChkList(Long fileId, List<FileDocChkList> fileDocChkLists)
        throws ServiceException;

    /**
     * 
     * @param fileId
     * @param fileDocs
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveFileDoc(Long fileId, List<FileDoc> fileDocs) throws ServiceException;

    /**
     * 
     * @param fileDocId
     * @param moveFileId
     * @param moveFcaId
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void moveFileDoc(Long fileDocId, Long moveFileId, String moveFcaId) throws ServiceException;

    /**
     * 
     * @param fileId
     * @return
     * @throws ServiceException
     */
    List<Contact> findFileContacts(Long fileId) throws ServiceException;

    /**
     * 
     * @param fileId
     * @return
     * @throws ServiceException
     */
    List<FileDoc> findFileDocs(Long fileId) throws ServiceException;

    /**
     * 
     * @param fileId
     * @return
     * @throws ServiceException
     */
    List<FileDocChkList> findFileDocChkLists(Long fileId) throws ServiceException;

    /**
     * 
     * @param fileId
     * @return
     * @throws ServiceException
     */
    List<Rfi> findFileRfis(Long fileId) throws ServiceException;

    /**
     * 
     * @param aseChange
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveAseChange(AseChange aseChange) throws ServiceException;

    /**
     * 
     * @param isolationHistoryId
     * @return
     */
	IsolationHistory findIsolationHistory(Long isolationHistoryId) throws ServiceException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	void saveIsolationHistory(IsolationHistory isolationHistory) throws ServiceException;

	List<IsolationHistory> findIsolationHistory(IsolationHistorySearchCriteria criteria)
        throws ServiceException;

    void exportIsolationHistory(List<IsolationHistory> entities,
        OutputStream contentStream, ContentTypeEnum contentType)
        throws ServiceException;

}