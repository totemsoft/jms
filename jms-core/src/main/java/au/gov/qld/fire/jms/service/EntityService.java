package au.gov.qld.fire.jms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.domain.StringPair;
import au.gov.qld.fire.domain.location.Region;
import au.gov.qld.fire.domain.refdata.LeaveType;
import au.gov.qld.fire.domain.refdata.MailType;
import au.gov.qld.fire.jms.domain.action.ActionOutcome;
import au.gov.qld.fire.jms.domain.action.ActionWorkflow;
import au.gov.qld.fire.jms.domain.ase.AseKeyPrice;
import au.gov.qld.fire.jms.domain.document.DocChkList;
import au.gov.qld.fire.jms.domain.entity.StakeHolder;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;
import au.gov.qld.fire.jms.domain.refdata.ActionCodeSearchCriteria;
import au.gov.qld.fire.jms.domain.refdata.ActionType;
import au.gov.qld.fire.jms.domain.refdata.ActionTypeEnum;
import au.gov.qld.fire.jms.domain.refdata.AseConnType;
import au.gov.qld.fire.jms.domain.refdata.AseType;
import au.gov.qld.fire.jms.domain.refdata.BuildingClassification;
import au.gov.qld.fire.jms.domain.refdata.FileStatus;
import au.gov.qld.fire.jms.domain.refdata.JobType;
import au.gov.qld.fire.jms.domain.refdata.SiteType;
import au.gov.qld.fire.jms.domain.refdata.StakeHolderType;
import au.gov.qld.fire.jms.domain.user.WorkflowRegister;
import au.gov.qld.fire.service.ServiceException;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface EntityService extends au.gov.qld.fire.service.EntityService
{

    /**
     * Update database with sql scripts from db/update folder.
     * (no transaction, will use synchroniseDatabase(URL source) from super)
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    void synchroniseDatabase() throws ServiceException;

    /**
     *
     */
	List<StringPair> findSyncProcesses() throws ServiceException;
    /**
     *
     * @param process
     * @throws ServiceException
     */
	//@Transactional(propagation = Propagation.NEVER, readOnly = true)
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	void syncProcess(String process) throws ServiceException;
    /**
     *
     * @throws ServiceException
     */
	@Transactional(propagation = Propagation.NEVER, readOnly = true)
	void syncProcess() throws ServiceException;

    /**
     * Search entity code/name by name like 'name%'
     * @param actionCode
     * @return
     * @throws ServiceException
     */
    List<String> findActionCode(String actionCode) throws ServiceException;

    /**
     * 
     * @return
     * @throws ServiceException
     */
    List<AseKeyPrice> findAseKeyPrice() throws ServiceException;

    /**
     * Search entity code/name by name like 'name%'
     * @param workGroup
     * @return
     * @throws ServiceException
     */
    List<String> findWorkGroup(String workGroup) throws ServiceException;

    /* ===================================================================== */

    /**
     * Find all ActionCode.
     * @return
     * @throws ServiceException
     */
    List<ActionCode> findActionCodes() throws ServiceException;

    /**
     * Find all active ActionCode.
     * @return
     * @throws ServiceException
     */
    List<ActionCode> findActionCodesActive() throws ServiceException;

    /**
     *
     * @param criteria
     * @return
     * @throws ServiceException
     */
    List<ActionCode> findActionCodes(ActionCodeSearchCriteria criteria) throws ServiceException;

    /**
     *
     * @param actionType
     * @return
     * @throws ServiceException
     */
    List<ActionCode> findActionCodeByActionType(ActionType actionType) throws ServiceException;

    /**
     *
     * @param actionType
     * @return
     * @throws ServiceException
     */
    List<ActionCode> findActionCodeByActionType(ActionTypeEnum actionType) throws ServiceException;
    List<ActionCode> findActionCodeByActionTypeJobType(ActionTypeEnum actionType, JobType jobType) throws ServiceException;
    List<ActionCode> findActionCodeByJobType(Long jobTypeId) throws ServiceException;

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    ActionCode findActionCodeById(Long id) throws ServiceException;
    ActionCode findActionCodeByIdInitialise(Long id) throws ServiceException;

    /**
     *
     * @param code
     * @return
     * @throws ServiceException
     */
    ActionCode findActionCodeByCode(String code) throws ServiceException;

    /**
     *
     * @param actionCode
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveActionCode(ActionCode actionCode) throws ServiceException;

    /**
     *
     * @return
     * @throws ServiceException
     */
    List<ActionOutcome> findActionOutcomes() throws ServiceException;

    /**
     *
     * @param actionCode
     * @return
     * @throws ServiceException
     */
    List<ActionOutcome> findActionOutcomesByActionCode(ActionCode actionCode) throws ServiceException;

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    ActionOutcome findActionOutcomeById(Long id) throws ServiceException;

    /**
     *
     * @param actionOutcome
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveActionOutcome(ActionOutcome actionOutcome) throws ServiceException;

    /**
     * Find all active ActionType.
     * @return
     * @throws ServiceException
     */
    List<ActionType> findActionTypes() throws ServiceException;

    /**
     *
     * @return
     * @throws ServiceException
     */
    List<ActionWorkflow> findActionWorkflows() throws ServiceException;

    /**
     *
     * @param actionCode
     * @return
     * @throws ServiceException
     */
    List<ActionWorkflow> findActionWorkflows(ActionCode actionCode) throws ServiceException;
    List<ActionWorkflow> findActionWorkflows(Long actionCodeId) throws ServiceException;
    List<ActionWorkflow> findActionWorkflows(Long jobTypeId, Long actionCodeId, boolean active) throws ServiceException;

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    ActionWorkflow findActionWorkflowById(Long id) throws ServiceException;

    /**
     *
     * @param actionCode
     * @param actionOutcome
     * @return
     * @throws ServiceException
     */
    List<ActionWorkflow> findActionWorkflowByActionCodeOutcome(ActionCode actionCode,
        ActionOutcome actionOutcome) throws ServiceException;

    /**
     *
     * @param actionWorkflow
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveActionWorkflow(ActionWorkflow actionWorkflow) throws ServiceException;

    /**
     *
     * @param actionWorkflows
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveActionWorkflow(List<ActionWorkflow> actionWorkflows) throws ServiceException;

    /**
     *
     * @param actionWorkflow
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void deleteActionWorkflow(ActionWorkflow actionWorkflow) throws ServiceException;

    /**
     *
     * @param actionWorkflows
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void deleteActionWorkflow(List<ActionWorkflow> actionWorkflows) throws ServiceException;

    /**
     * All.
     * @return
     * @throws ServiceException
     */
    List<DocChkList> findDocChkLists() throws ServiceException;

    /**
     * Active only.
     * @return
     * @throws ServiceException
     */
    List<DocChkList> findDocChkListsActive() throws ServiceException;

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    DocChkList findDocChkListById(Long id) throws ServiceException;

    /**
     *
     * @param docChkList
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveDocChkList(DocChkList docChkList) throws ServiceException;

    /**
     * Find all active FileStatus.
     * @return
     * @throws ServiceException
     */
    List<FileStatus> findFileStatuses() throws ServiceException;

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    FileStatus findFileStatusById(Long id) throws ServiceException;

    /**
     *
     * @param fileStatus
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveFileStatus(FileStatus fileStatus) throws ServiceException;

    /**
     * Find all active JobType.
     * @return
     * @throws ServiceException
     */
    List<JobType> findJobTypes() throws ServiceException;

    /**
     * Find all File New JobType (transfer/disconnected alarms).
     * @return
     * @throws ServiceException
     */
    List<JobType> findFileNewJobTypes() throws ServiceException;

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    JobType findJobTypeById(Long id) throws ServiceException;

    /**
     *
     * @param jobType
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveJobType(JobType jobType) throws ServiceException;

    /**
     * Find all AseType.
     * @return
     * @throws ServiceException
     */
    List<AseType> findAseTypes() throws ServiceException;

    /**
     * Find all active AseType.
     * @return
     * @throws ServiceException
     */
    List<AseType> findAseTypesActive() throws ServiceException;

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    AseType findAseTypeById(Long id) throws ServiceException;

    /**
     *
     * @param aseType
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveAseType(AseType aseType) throws ServiceException;

    /**
     * Find all AseConnType.
     * @return
     * @throws ServiceException
     */
    List<AseConnType> findAseConnTypes() throws ServiceException;

    /**
     * Find all active AseConnType.
     * @return
     * @throws ServiceException
     */
    List<AseConnType> findAseConnTypesActive() throws ServiceException;

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    AseConnType findAseConnTypeById(Long id) throws ServiceException;

    /**
     *
     * @param aseConnType
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveAseConnType(AseConnType aseConnType) throws ServiceException;

	/**
	 *
	 * @return
	 * @throws ServiceException
	 */
	List<LeaveType> findLeaveTypes() throws ServiceException;

	/**
	 *
	 * @param id
	 * @return
	 */
	MailType findMailTypeById(Long id);

	MailType findMailTypeByName(String name) throws ServiceException;

	List<MailType> findMailTypes() throws ServiceException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	void saveMailType(MailType dto);

    /**
     * Find all StakeHolder.
     * @return
     * @throws ServiceException
     */
    List<StakeHolder> findStakeHolders() throws ServiceException;

    /**
     * Find all active StakeHolder.
     * @return
     * @throws ServiceException
     */
    List<StakeHolder> findStakeHoldersActive() throws ServiceException;

    /**
     *
     * @param region
     * @return
     * @throws ServiceException
     */
    List<StakeHolder> findStakeHoldersByRegion(Region region) throws ServiceException;

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    StakeHolder findStakeHolderById(Long id) throws ServiceException;

    /**
     *
     * @param stakeHolder
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveStakeHolder(StakeHolder stakeHolder) throws ServiceException;

    /**
     *
     * @return
     * @throws ServiceException
     */
    List<StakeHolderType> findStakeHolderTypes() throws ServiceException;

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    StakeHolderType findStakeHolderTypeById(Long id) throws ServiceException;

    /**
     *
     * @param stakeHolderType
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveStakeHolderType(StakeHolderType stakeHolderType) throws ServiceException;

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
	BuildingClassification findBuildingClassificationById(Long id) throws ServiceException;

	/**
	 * Find all BuildingClassification(s).
	 * @return
	 * @throws ServiceException
	 */
	List<BuildingClassification> findBuildingClassifications() throws ServiceException;

	/**
	 * Find all active BuildingClassification(s).
	 * @return
	 * @throws ServiceException
	 */
	List<BuildingClassification> findBuildingClassificationsActive() throws ServiceException;

	/**
	 *
	 * @param dto
	 * @throws ServiceException
	 */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	void saveBuildingClassification(BuildingClassification dto) throws ServiceException;

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
	SiteType findSiteTypeById(Long id) throws ServiceException;

	/**
	 * Find all SiteType(s).
	 * @return
	 * @throws ServiceException
	 */
	List<SiteType> findSiteTypes() throws ServiceException;

	/**
	 * Find all active SiteType(s).
	 * @return
	 * @throws ServiceException
	 */
	List<SiteType> findSiteTypesActive() throws ServiceException;

	/**
	 *
	 * @param dto
	 * @throws ServiceException
	 */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	void saveSiteType(SiteType dto) throws ServiceException;

    /**
     * @param lazy
     * @return
     * @throws ServiceException
     */
    List<WorkflowRegister> findWorkflowRegistersByWorkGroup(boolean lazy) throws ServiceException;
    List<WorkflowRegister> findWorkflowRegistersByJobType(boolean lazy) throws ServiceException;

    /**
     *
     * @param workGroupId
     * @return
     * @throws ServiceException
     */
    WorkflowRegister findWorkflowRegisterByWorkGroup(Long workGroupId) throws ServiceException;
    WorkflowRegister findWorkflowRegisterByJobType(Long jobTypeId) throws ServiceException;

}