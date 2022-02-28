package au.gov.qld.fire.jms.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;

import au.gov.qld.fire.dao.LeaveTypeDao;
import au.gov.qld.fire.dao.MailTypeDao;
import au.gov.qld.fire.dao.SecurityGroupDao;
import au.gov.qld.fire.domain.StringPair;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.location.Region;
import au.gov.qld.fire.domain.refdata.LeaveType;
import au.gov.qld.fire.domain.refdata.MailType;
import au.gov.qld.fire.domain.refdata.WorkGroup;
import au.gov.qld.fire.domain.security.SecurityGroup;
import au.gov.qld.fire.domain.security.SystemFunction;
import au.gov.qld.fire.jms.dao.ActionCodeDao;
import au.gov.qld.fire.jms.dao.ActionOutcomeDao;
import au.gov.qld.fire.jms.dao.ActionTypeDao;
import au.gov.qld.fire.jms.dao.ActionWorkflowDao;
import au.gov.qld.fire.jms.dao.AseConnTypeDao;
import au.gov.qld.fire.jms.dao.AseKeyPriceDao;
import au.gov.qld.fire.jms.dao.AseTypeDao;
import au.gov.qld.fire.jms.dao.BuildingClassificationDao;
import au.gov.qld.fire.jms.dao.DocChkListDao;
import au.gov.qld.fire.jms.dao.FileStatusDao;
import au.gov.qld.fire.jms.dao.JobTypeDao;
import au.gov.qld.fire.jms.dao.SiteTypeDao;
import au.gov.qld.fire.jms.dao.StakeHolderDao;
import au.gov.qld.fire.jms.dao.StakeHolderTypeDao;
import au.gov.qld.fire.jms.domain.ConvertUtils;
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
import au.gov.qld.fire.jms.domain.refdata.JobTypeEnum;
import au.gov.qld.fire.jms.domain.refdata.SiteType;
import au.gov.qld.fire.jms.domain.refdata.StakeHolderType;
import au.gov.qld.fire.jms.domain.user.WorkflowRegister;
import au.gov.qld.fire.jms.service.DocumentService;
import au.gov.qld.fire.jms.service.EntityService;
import au.gov.qld.fire.service.ServiceException;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class EntityServiceImpl
    extends au.gov.qld.fire.service.impl.EntityServiceImpl implements EntityService
{

    /** logger. */
    private static final Logger LOG = Logger.getLogger(EntityServiceImpl.class);

    private static final String UPDATE_LIST = "/db/update/jms.properties";

    /** required database version */
    private String dbVersion;

    @Inject private BeanFactory beanFactory;

    @Inject private ActionCodeDao actionCodeDao;

    @Inject private ActionOutcomeDao actionOutcomeDao;

    @Inject private ActionTypeDao actionTypeDao;

    @Inject private ActionWorkflowDao actionWorkflowDao;

    @Inject private BuildingClassificationDao buildingClassificationDao;

    @Inject private DocChkListDao docChkListDao;

    @Inject private FileStatusDao fileStatusDao;

    @Inject private JobTypeDao jobTypeDao;

    @Inject private AseTypeDao aseTypeDao;

    @Inject private AseConnTypeDao aseConnTypeDao;

    @Inject private AseKeyPriceDao aseKeyPriceDao;

    @Inject private LeaveTypeDao leaveTypeDao;

    @Inject private MailTypeDao mailTypeDao;

    @Inject private SecurityGroupDao securityGroupDao;

    @Inject private SiteTypeDao siteTypeDao;

    @Inject private StakeHolderDao stakeHolderDao;

    @Inject private StakeHolderTypeDao stakeHolderTypeDao;

    @Inject private DocumentService documentService;

    /**
     * @param dbVersion the dbVersion to set
     */
    public void setDbVersion(String dbVersion)
    {
        this.dbVersion = dbVersion;
    }

    private EntityService getInstance()
    {
    	return  (EntityService) beanFactory.getBean("entityService");
    }

    /**
     * "JMS.01.00" < "JMS.01.01"
     * @param v1
     * @param v2
     * @return
     */
    private static int compareDbVersion(String v1, String v2)
    {
    	String[] parts1 = StringUtils.split(v1, '.');
    	String[] parts2 = StringUtils.split(v2, '.');
        if (!parts1[0].equals(parts2[0])) {
        	throw new ServiceException(v1 + " could not be compared to " + v2);
        }
        int major1 = NumberUtils.toInt(parts1[1]);
        int major2 = NumberUtils.toInt(parts2[1]);
        if (major1 != major2) {
        	return major1 - major2;
        }
        int minor1 = NumberUtils.toInt(parts1[2]);
        int minor2 = NumberUtils.toInt(parts2[2]);
       	return minor1 - minor2;
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#synchroniseDatabase()
     */
    //@Override
    public void synchroniseDatabase() throws ServiceException
    {
        try
        {
            // get current db version
            String currentDbVersion = (String) getJdbcDao().uniqueResult(
                "SELECT DBVERSION FROM DBVERSION WHERE DBVERSION LIKE 'JMS.%' ORDER BY CREATED_DATE DESC");
            if (compareDbVersion(currentDbVersion, dbVersion) < 0)
            {
                LOG.info("START synchroniseDatabase from [" + currentDbVersion + "] to [" + dbVersion + "]");
                BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(UPDATE_LIST)));
                try
                {
                    String line;
                    while ((line = reader.readLine()) != null)
                    {
                        int index = line.indexOf('=');
                        if (index <= 0)
                        {
                            continue; // comments
                        }
                        String key = line.substring(0, index);
                        String value = line.substring(index + 1);
                        if (compareDbVersion(dbVersion, key) >= 0 && compareDbVersion(key, currentDbVersion) > 0)
                        {
                        	getInstance().synchroniseDatabase(getClass().getResource(value));
                        }
                    }
                }
                finally
                {
                    IOUtils.closeQuietly(reader);
                }
                LOG.info(".. END synchroniseDatabase.");
            }
            // !!! FOR DEVELOPMENT ONLY !!!
        	//getInstance().synchroniseDatabase(getClass().getResource("/db/update/JMS.01.20.sql"));
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityService#findSyncProcesses()
	 */
	public List<StringPair> findSyncProcesses() throws ServiceException
	{
        List<StringPair> result = new ArrayList<StringPair>();
        // Location
        result.add(new StringPair("sps_region",  "Region"));
        result.add(new StringPair("sps_area",    "Area"));
        result.add(new StringPair("sps_station", "Station"));
        //
        result.add(new StringPair("sps_fca_station",  "FCA Station"));
        result.add(new StringPair("sps_fca_building", "FCA Building"));
        result.add(new StringPair("sps_fca_owner_1",  "FCA Owner"));
        result.add(new StringPair("sps_fca_owner_2",  "FCA Body Corporate"));
        result.add(new StringPair("sps_fca_owner_3",  "FCA Property Manager"));
        result.add(new StringPair("sps_fca_owner_4",  "FCA Property Tenant"));
        result.add(new StringPair("sps_fca_owner_5",  "FCA Alternate"));
        result.add(new StringPair("sps_fca_om89",     "FCA OM89 History"));
        //
		return result;
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityService#syncProcess(java.lang.String)
	 */
	public void syncProcess(String process) throws ServiceException
	{
        try
        {
    		getJdbcDao().executeUpdate(process);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityService#syncProcess()
	 */
	public void syncProcess() throws ServiceException
	{
		EntityService self = getInstance();
		for (StringPair p : self.findSyncProcesses()) {
			self.syncProcess(p.getKey());
		}
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.impl.EntityServiceImpl#deleteTemplate(au.gov.qld.fire.domain.document.Template)
	 */
	@Override
	public void deleteTemplate(Template template) throws ServiceException {
		super.deleteTemplate(template);
        // clear actionCode cache
        actionCodeDao.saveOrUpdate(null);
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findActionCode(java.lang.String)
     */
    public List<String> findActionCode(String actionCode) throws ServiceException
    {
        try {
            return actionCodeDao.findActionCode(actionCode);
        }
        catch (Exception e) {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityService#findAseKeyPrice()
	 */
	@Override
	public List<AseKeyPrice> findAseKeyPrice() throws ServiceException
	{
        try {
            return aseKeyPriceDao.findAll();
        }
        catch (Exception e) {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findActionCodes()
     */
    public List<ActionCode> findActionCodes() throws ServiceException
    {
        try
        {
            return actionCodeDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findActionCodesActive()
     */
    public List<ActionCode> findActionCodesActive() throws ServiceException
    {
        try
        {
            return actionCodeDao.findAllActive();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findActionCodes(au.gov.qld.fire.jms.domain.refdata.ActionCodeSearchCriteria)
     */
    public List<ActionCode> findActionCodes(ActionCodeSearchCriteria criteria)
        throws ServiceException
    {
        try
        {
            return actionCodeDao.findAllByCriteria(criteria);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findActionCodeByActionType(au.gov.qld.fire.jms.domain.refdata.ActionType)
     */
    public List<ActionCode> findActionCodeByActionType(ActionType actionType)
        throws ServiceException
    {
        try
        {
            return actionCodeDao.findAllByActionType(actionType);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findActionCodeByActionType(au.gov.qld.fire.jms.domain.refdata.ActionTypeEnum)
     */
    public List<ActionCode> findActionCodeByActionType(ActionTypeEnum actionType)
        throws ServiceException
    {
        try
        {
            return actionCodeDao.findAllByActionType(actionType);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityService#findActionCodeByActionTypeJobType(au.gov.qld.fire.jms.domain.refdata.ActionTypeEnum, au.gov.qld.fire.jms.domain.refdata.JobType)
	 */
	public List<ActionCode> findActionCodeByActionTypeJobType(ActionTypeEnum actionType, JobType jobType)
	    throws ServiceException
	{
        try
        {
        	//jobTypeDao.refresh(jobType);
        	Long jobTypeId = jobType == null ? null : jobType.getId();
            return actionCodeDao.findAllByActionTypeJobType(actionType, jobTypeId);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityService#findActionCodeByJobType(java.lang.Long)
	 */
	public List<ActionCode> findActionCodeByJobType(Long jobTypeId)
	    throws ServiceException
	{
        try
        {
            return actionCodeDao.findAllByJobType(jobTypeId);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findActionCodeById(java.lang.Long)
     */
    public ActionCode findActionCodeById(Long id) throws ServiceException
    {
        try
        {
            return actionCodeDao.findById(id);
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityService#findActionCodeByIdInitialise(java.lang.Long)
	 */
	public ActionCode findActionCodeByIdInitialise(Long id) throws ServiceException
	{
        try
        {
            return actionCodeDao.findByIdInitialise(id);
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findActionCodeByCode(java.lang.String)
     */
    public ActionCode findActionCodeByCode(String code) throws ServiceException
    {
        try
        {
            return actionCodeDao.findByCode(code);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#saveActionCode(au.gov.qld.fire.jms.domain.refdata.ActionCode)
     */
    public void saveActionCode(ActionCode actionCode) throws ServiceException
    {
    	// TODO: validate in AOP (check for duplicate code)
        try
        {
            // find by id
            Long id = actionCode.getId();
            ActionCode entity = null;
            if (id != null) {
                entity = actionCodeDao.findById(id);
            }
            // and copy
            boolean newEntity = entity == null;
            if (newEntity) {
                entity = actionCode;
            }
            else {
                ConvertUtils.copyProperties(actionCode, entity);
            }
            // optional
            if (entity.getJobType() != null && entity.getJobType().getId() == null) {
                entity.setJobType(null);
            }
            if (entity.getWorkGroup() != null && entity.getWorkGroup().getId() == null) {
                entity.setWorkGroup(null);
            }
            if (entity.getTemplate() != null && entity.getTemplate().getId() == null) {
                entity.setTemplate(null);
            }
            if (entity.getDocumentTemplate() != null && entity.getDocumentTemplate().getId() == null) {
                entity.setDocumentTemplate(null);
            }
            // save
            actionCodeDao.saveOrUpdate(entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findActionOutcomes()
     */
    public List<ActionOutcome> findActionOutcomes() throws ServiceException
    {
        try
        {
            return actionOutcomeDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findActionOutcomesByActionCode(au.gov.qld.fire.jms.domain.refdata.ActionCode)
     */
    public List<ActionOutcome> findActionOutcomesByActionCode(ActionCode actionCode)
        throws ServiceException
    {
        if (actionCode == null || actionCode.getId() == null)
        {
            return Collections.emptyList();
        }
        try
        {
            List<ActionOutcome> result = new ArrayList<ActionOutcome>();
            List<ActionWorkflow> actionWorkflows = actionWorkflowDao.findByActionCode(actionCode);
            for (ActionWorkflow actionWorkflow : actionWorkflows)
            {
                ActionOutcome aActionOutcome = actionWorkflow.getActionOutcome();
                //just in case filter configured outcome(s)
                if (actionWorkflow.getNextDueDays() != null && !result.contains(aActionOutcome))
                {
                    result.add(aActionOutcome);
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
     * @see au.gov.qld.fire.jms.service.EntityService#findActionOutcomeById(java.lang.Long)
     */
    public ActionOutcome findActionOutcomeById(Long id) throws ServiceException
    {
        try
        {
            return actionOutcomeDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#saveActionOutcome(au.gov.qld.fire.jms.domain.action.ActionOutcome)
     */
    public void saveActionOutcome(ActionOutcome actionOutcome) throws ServiceException
    {
        try
        {
            //find by id
            Long id = actionOutcome.getId();
            ActionOutcome entity = null;
            if (id != null)
            {
                entity = actionOutcomeDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = actionOutcome;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(actionOutcome, entity);
            }

            //optional

            //save
            actionOutcomeDao.saveOrUpdate(entity);
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
     * @see au.gov.qld.fire.jms.service.EntityService#findActionTypes()
     */
    public List<ActionType> findActionTypes() throws ServiceException
    {
        try
        {
            return actionTypeDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findActionWorkflowById(java.lang.Long)
     */
    public ActionWorkflow findActionWorkflowById(Long id) throws ServiceException
    {
        try
        {
            return actionWorkflowDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findActionWorkflowByActionCodeOutcome(au.gov.qld.fire.jms.domain.refdata.ActionCode, au.gov.qld.fire.jms.domain.action.ActionOutcome)
     */
    @SuppressWarnings("unchecked")
    public List<ActionWorkflow> findActionWorkflowByActionCodeOutcome(ActionCode actionCode,
        ActionOutcome actionOutcome) throws ServiceException
    {
        if (actionCode == null || actionCode.getId() == null || actionOutcome == null
            || actionOutcome.getId() == null)
        {
            return Collections.EMPTY_LIST;
        }
        try
        {
            return actionWorkflowDao.findByActionCodeOutcome(actionCode, actionOutcome);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findActionWorkflows()
     */
    public List<ActionWorkflow> findActionWorkflows() throws ServiceException
    {
        try
        {
            return actionWorkflowDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findActionWorkflows(au.gov.qld.fire.jms.domain.refdata.ActionCode)
     */
    public List<ActionWorkflow> findActionWorkflows(ActionCode actionCode) throws ServiceException
    {
        try
        {
            return actionWorkflowDao.findByActionCode(actionCode);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityService#findActionWorkflows(java.lang.Long)
	 */
	public List<ActionWorkflow> findActionWorkflows(Long actionCodeId) throws ServiceException
	{
        try
        {
            return actionWorkflowDao.findByActionCode(findActionCodeById(actionCodeId));
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityService#findActionWorkflows(java.lang.Long, java.lang.Long, boolean)
	 */
	public List<ActionWorkflow> findActionWorkflows(Long jobTypeId, Long actionCodeId, final boolean active)
	    throws ServiceException
	{
        try
        {
            // get all saved ActionWorkflow for this jobType/actionCode
            List<ActionCode> actionCodes;
            Map<ActionCode, List<ActionWorkflow>> entities = new LinkedHashMap<ActionCode, List<ActionWorkflow>>();
            if (jobTypeId != null) {
            	if (actionCodeId == null) {
                	actionCodes = findActionCodeByJobType(jobTypeId);
            	} else {
                    actionCodes = new ArrayList<ActionCode>();
                    ActionCode actionCode = findActionCodeById(actionCodeId);
                    if (actionCode.getJobType() != null && jobTypeId.equals(actionCode.getJobType().getJobTypeId())) {
                        actionCodes.add(actionCode);
                    }
            	}
            	for (ActionCode actionCode : actionCodes) {
            		entities.put(actionCode, findActionWorkflows(actionCode.getActionCodeId()));
            	}
            } else /*if (actionCodeId != null)*/ {
                ActionCode actionCode = findActionCodeById(actionCodeId);
                actionCodes = new ArrayList<ActionCode>();
                actionCodes.add(actionCode);
            	entities.put(actionCode, findActionWorkflows(actionCodeId));
            }
            // get all ActionOutcomes and wrap them as ActionWorkflow collection
            List<ActionWorkflow> result = new ArrayList<ActionWorkflow>();
            Iterator<Entry<ActionCode, List<ActionWorkflow>>> iter = entities.entrySet().iterator();
            while (iter.hasNext()) {
            	Entry<ActionCode, List<ActionWorkflow>> entry = iter.next();
            	final ActionCode actionCode = entry.getKey();
            	final List<ActionWorkflow> items = entry.getValue();
                CollectionUtils.collect(findActionOutcomes(), new Transformer() {
                    public Object transform(Object obj) {
                    	ActionOutcome actionOutcome = (ActionOutcome) obj;
                    	// create ActionWorkflow dto for this actionCode
                        ActionWorkflow actionWorkflow = new ActionWorkflow();
                        actionWorkflow.setActionCode(actionCode);
                        actionWorkflow.setActionOutcome(actionOutcome);
                        // replace ActionWorkflow dto with saved ActionWorkflow (if any)
                        for (ActionWorkflow item : items) {
                            if (actionOutcome.equals(item.getActionOutcome())) {
                                ConvertUtils.copyProperties(item, actionWorkflow);
                                break;
                            }
                        }
                        //
                        return actionWorkflow;
                    }
                }, result);
            }
            //
            if (active) {
            	for (Iterator<ActionWorkflow> i = result.iterator(); i.hasNext(); ) {
            		ActionWorkflow item = i.next();
                	if (!item.isActive() || item.getActionWorkflowId() == null) {
                		i.remove();
                	}
            	}
            }
            //
            return result;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#saveActionWorkflow(au.gov.qld.fire.jms.domain.action.ActionWorkflow)
     */
    public void saveActionWorkflow(ActionWorkflow actionWorkflow) throws ServiceException
    {
        try
        {
            //find by id
            Long id = actionWorkflow.getId();
            ActionWorkflow entity = null;
            if (id != null)
            {
                entity = actionWorkflowDao.findById(id);
            }

            //optional
            ActionCode actionCode = actionWorkflow.getActionCode();
            if (actionCode.getActionCodeId() == null)
            {
                actionCode = actionCodeDao.findByCode(actionCode.getCode());
                actionWorkflow.setActionCode(actionCode);
            }
            ActionOutcome actionOutcome = actionWorkflow.getActionOutcome();
            if (actionOutcome.getActionOutcomeId() == null)
            {
                actionOutcome = actionOutcomeDao.findByName(actionOutcome.getName());
                actionWorkflow.setActionOutcome(actionOutcome);
            }
            if (actionWorkflow.getNextDueDays() == null)
            {
                actionWorkflow.setNextDueDays(0L);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = actionWorkflow;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(actionWorkflow, entity);
            }

            //save
            actionWorkflowDao.saveOrUpdate(entity);
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
     * @see au.gov.qld.fire.jms.service.EntityService#deleteActionWorkflow(au.gov.qld.fire.jms.domain.action.ActionWorkflow)
     */
    public void deleteActionWorkflow(ActionWorkflow actionWorkflow) throws ServiceException
    {
        try
        {
            //find by id
            Long id = actionWorkflow.getId();
            ActionWorkflow entity = null;
            if (id != null)
            {
                entity = actionWorkflowDao.findById(id);
            }

            //save
            actionWorkflowDao.delete(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Deleted: " + entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#saveActionWorkflow(java.util.List)
     */
    public void saveActionWorkflow(List<ActionWorkflow> actionWorkflows) throws ServiceException
    {
        for (ActionWorkflow actionWorkflow : actionWorkflows)
        {
            saveActionWorkflow(actionWorkflow);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#deleteActionWorkflow(java.util.List)
     */
    public void deleteActionWorkflow(List<ActionWorkflow> actionWorkflows) throws ServiceException
    {
        for (ActionWorkflow actionWorkflow : actionWorkflows)
        {
            deleteActionWorkflow(actionWorkflow);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findDocChkLists()
     */
    public List<DocChkList> findDocChkLists() throws ServiceException
    {
        try
        {
            return docChkListDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findDocChkListsActive()
     */
    public List<DocChkList> findDocChkListsActive() throws ServiceException
    {
        try
        {
            return docChkListDao.findAllActive();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findDocChkListById(java.lang.Long)
     */
    public DocChkList findDocChkListById(Long id) throws ServiceException
    {
        try
        {
            return docChkListDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#saveDocChkList(au.gov.qld.fire.jms.domain.document.DocChkList)
     */
    public void saveDocChkList(DocChkList docChkList) throws ServiceException
    {
        try
        {
            //find by id
            Long id = docChkList.getId();
            DocChkList entity = null;
            if (id != null)
            {
                entity = docChkListDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = docChkList;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(docChkList, entity);
            }

            //optional

            //save
            docChkListDao.saveOrUpdate(entity);
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
     * @see au.gov.qld.fire.jms.service.EntityService#findFileStatuses()
     */
    public List<FileStatus> findFileStatuses() throws ServiceException
    {
        try
        {
            return fileStatusDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findFileStatusById(java.lang.Long)
     */
    public FileStatus findFileStatusById(Long id) throws ServiceException
    {
        try
        {
            return fileStatusDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#saveFileStatus(au.gov.qld.fire.jms.domain.refdata.FileStatus)
     */
    public void saveFileStatus(FileStatus fileStatus) throws ServiceException
    {
        try
        {
            //find by id
            Long id = fileStatus.getId();
            FileStatus entity = null;
            if (id != null)
            {
                entity = fileStatusDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = fileStatus;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(fileStatus, entity);
            }

            //optional

            //save
            fileStatusDao.saveOrUpdate(entity);
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
     * @see au.gov.qld.fire.jms.service.EntityService#findJobTypes()
     */
    public List<JobType> findJobTypes() throws ServiceException
    {
        try
        {
            return jobTypeDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findFileNewJobTypes()
     */
    public List<JobType> findFileNewJobTypes() throws ServiceException
    {
        try
        {
            List<JobType> result = new ArrayList<JobType>();
            result.add(jobTypeDao.findById(JobTypeEnum.ALARM_TRANSFER.getId()));
            result.add(jobTypeDao.findById(JobTypeEnum.ALARM_DISCONNECTION.getId()));
            return result;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findJobTypeById(java.lang.Long)
     */
    public JobType findJobTypeById(Long id) throws ServiceException
    {
        try
        {
            return jobTypeDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#saveJobType(au.gov.qld.fire.jms.domain.refdata.JobType)
     */
    public void saveJobType(JobType jobType) throws ServiceException
    {
        try
        {
            //find by id
            Long id = jobType.getId();
            JobType entity = null;
            if (id != null)
            {
                entity = jobTypeDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = jobType;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(jobType, entity);
            }

            //optional
            if (entity.getSecurityGroup().getId() == null)
            {
                entity.setSecurityGroup(null);
            }
            if (entity.getActionCode().getId() == null)
            {
                entity.setActionCode(null);
            }
            if (entity.getCloseJobSecurityGroup().getId() == null)
            {
                entity.setCloseJobSecurityGroup(null);
            }
            if (entity.getWorkGroup().getId() == null)
            {
                entity.setWorkGroup(null);
            }

            //save
            jobTypeDao.saveOrUpdate(entity);
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
     * @see au.gov.qld.fire.jms.service.EntityService#findAseTypes()
     */
    public List<AseType> findAseTypes() throws ServiceException
    {
        try
        {
            return aseTypeDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findAseTypesActive()
     */
    public List<AseType> findAseTypesActive() throws ServiceException
    {
        try
        {
            return aseTypeDao.findAllActive();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findAseTypeById(java.lang.Long)
     */
    public AseType findAseTypeById(Long id) throws ServiceException
    {
        try
        {
            return aseTypeDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#saveAseType(au.gov.qld.fire.jms.domain.refdata.AseType)
     */
    public void saveAseType(AseType aseType) throws ServiceException
    {
        try
        {
            //find by id
            Long id = aseType.getId();
            AseType entity = null;
            if (id != null)
            {
                entity = aseTypeDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = aseType;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(aseType, entity);
            }

            //optional

            //save
            aseTypeDao.saveOrUpdate(entity);
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
     * @see au.gov.qld.fire.jms.service.EntityService#findAseConnTypes()
     */
    public List<AseConnType> findAseConnTypes() throws ServiceException
    {
        try
        {
            return aseConnTypeDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findAseConnTypesActive()
     */
    public List<AseConnType> findAseConnTypesActive() throws ServiceException
    {
        try
        {
            return aseConnTypeDao.findAllActive();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findAseConnTypeById(java.lang.Long)
     */
    public AseConnType findAseConnTypeById(Long id) throws ServiceException
    {
        try
        {
            return aseConnTypeDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#saveAseConnType(au.gov.qld.fire.jms.domain.refdata.AseConnType)
     */
    public void saveAseConnType(AseConnType aseConnType) throws ServiceException
    {
        try
        {
            //find by id
            Long id = aseConnType.getId();
            AseConnType entity = null;
            if (id != null)
            {
                entity = aseConnTypeDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = aseConnType;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(aseConnType, entity);
            }

            //optional

            //save
            aseConnTypeDao.saveOrUpdate(entity);
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
	 * @see au.gov.qld.fire.jms.service.EntityService#findLeaveTypes()
	 */
	public List<LeaveType> findLeaveTypes() throws ServiceException
	{
        try
        {
            return leaveTypeDao.findAllActive();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityService#findMailTypeById(java.lang.Long)
	 */
	public MailType findMailTypeById(Long id)
	{
        try
        {
            return mailTypeDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityService#findMailTypeByName(java.lang.String)
	 */
	public MailType findMailTypeByName(String name) throws ServiceException
	{
        try
        {
            return mailTypeDao.findByName(name);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityService#findMailTypes()
	 */
	public List<MailType> findMailTypes() throws ServiceException
	{
        try
        {
            return mailTypeDao.findAllActive();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityService#saveMailType(au.gov.qld.fire.domain.refdata.MailType)
	 */
	public void saveMailType(MailType dto)
	{
        try
        {
            //find by id
            Long id = dto.getId();
            MailType entity = null;
            if (id != null)
            {
                entity = mailTypeDao.findById(id);
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

            //optional

            //save
            mailTypeDao.saveOrUpdate(entity);
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
     * @see au.gov.qld.fire.jms.service.EntityService#findStakeHolders()
     */
    public List<StakeHolder> findStakeHolders() throws ServiceException
    {
        try
        {
            return stakeHolderDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findStakeHoldersActive()
     */
    public List<StakeHolder> findStakeHoldersActive() throws ServiceException
    {
        try
        {
            return stakeHolderDao.findAllActive();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findStakeHoldersByRegion(au.gov.qld.fire.jms.domain.location.Region)
     */
    @SuppressWarnings("unchecked")
    public List<StakeHolder> findStakeHoldersByRegion(Region region) throws ServiceException
    {
        if (region == null || region.getRegionId() == null)
        {
            return Collections.EMPTY_LIST;
        }
        try
        {
            return stakeHolderDao.findByRegion(region);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findStakeHolderById(java.lang.Long)
     */
    public StakeHolder findStakeHolderById(Long id) throws ServiceException
    {
        try
        {
            return stakeHolderDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#saveStakeHolder(au.gov.qld.fire.jms.domain.entity.StakeHolder)
     */
    public void saveStakeHolder(StakeHolder stakeHolder) throws ServiceException
    {
        try
        {
            //find by id
            Long id = stakeHolder.getId();
            StakeHolder entity = null;
            if (id != null)
            {
                entity = stakeHolderDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = stakeHolder;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(stakeHolder, entity);
            }

            //optional

            //save
            stakeHolderDao.saveOrUpdate(entity);
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
     * @see au.gov.qld.fire.jms.service.EntityService#findStakeHolderTypes()
     */
    public List<StakeHolderType> findStakeHolderTypes() throws ServiceException
    {
        try
        {
            return stakeHolderTypeDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#findStakeHolderTypeById(java.lang.String)
     */
    public StakeHolderType findStakeHolderTypeById(Long id) throws ServiceException
    {
        try
        {
            return stakeHolderTypeDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EntityService#saveStakeHolderType(au.gov.qld.fire.jms.domain.refdata.StakeHolderType)
     */
    public void saveStakeHolderType(StakeHolderType stakeHolderType) throws ServiceException
    {
        try
        {
            //find by id
            Long id = stakeHolderType.getId();
            StakeHolderType entity = null;
            if (id != null)
            {
                entity = stakeHolderTypeDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = stakeHolderType;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(stakeHolderType, entity);
            }

            //optional

            //save
            stakeHolderTypeDao.saveOrUpdate(entity);
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
     * @see au.gov.qld.fire.jms.service.EntityService#saveTemplate(au.gov.qld.fire.jms.domain.document.Template)
     */
    public void saveTemplate(Template template) throws ServiceException
    {
        super.saveTemplate(template);
        //
        documentService.saveReport(template);
        //
        // validate parameters (if any) - can throw ServiceException - TODO: why did I put this here?
        //*Document document = */documentService.createActionDocument(template, null);
    }

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityService#findBuildingClassificationById(java.lang.Long)
	 */
	public BuildingClassification findBuildingClassificationById(Long id) throws ServiceException {
        try
        {
            return buildingClassificationDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityService#findBuildingClassifications()
	 */
	public List<BuildingClassification> findBuildingClassifications() throws ServiceException {
        try
        {
            return buildingClassificationDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityService#findBuildingClassificationsActive()
	 */
	public List<BuildingClassification> findBuildingClassificationsActive() throws ServiceException {
        try
        {
            return buildingClassificationDao.findAllActive();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityService#saveBuildingClassification(au.gov.qld.fire.jms.domain.refdata.BuildingClassification)
	 */
	public void saveBuildingClassification(BuildingClassification dto) throws ServiceException {
        try
        {
            //find by id
            Long id = dto.getId();
            BuildingClassification entity = null;
            if (id != null)
            {
                entity = buildingClassificationDao.findById(id);
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

            //optional

            //save
            buildingClassificationDao.saveOrUpdate(entity);
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
	 * @see au.gov.qld.fire.jms.service.EntityService#findSiteTypeById(java.lang.Long)
	 */
	public SiteType findSiteTypeById(Long id) throws ServiceException {
        try
        {
            return siteTypeDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityService#findSiteTypes()
	 */
	public List<SiteType> findSiteTypes() throws ServiceException {
        try
        {
            return siteTypeDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityService#findSiteTypesActive()
	 */
	public List<SiteType> findSiteTypesActive() throws ServiceException {
        try
        {
            return siteTypeDao.findAllActive();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityService#saveSiteType(au.gov.qld.fire.jms.domain.refdata.SiteType)
	 */
	public void saveSiteType(SiteType dto) throws ServiceException {
        try
        {
            //find by id
            Long id = dto.getId();
            SiteType entity = null;
            if (id != null)
            {
                entity = siteTypeDao.findById(id);
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

            //optional

            //save
            siteTypeDao.saveOrUpdate(entity);
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
	 * @see au.gov.qld.fire.jms.service.EntityService#findWorkflowRegistersByWorkGroup(boolean)
	 */
	public List<WorkflowRegister> findWorkflowRegistersByWorkGroup(boolean lazy) throws ServiceException
	{
        try
        {
        	List<WorkflowRegister> result = new ArrayList<WorkflowRegister>();
        	for (WorkGroup workGroup : findWorkGroups())
        	{
        		result.add(lazy ? new WorkflowRegister(workGroup) : findWorkflowRegisterByWorkGroup(workGroup.getId()));
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
	 * @see au.gov.qld.fire.jms.service.EntityService#findWorkflowRegistersByJobType(boolean)
	 */
	public List<WorkflowRegister> findWorkflowRegistersByJobType(boolean lazy) throws ServiceException
	{
        try
        {
        	List<WorkflowRegister> result = new ArrayList<WorkflowRegister>();
        	for (JobType jobType : findJobTypes())
        	{
        		result.add(lazy ? new WorkflowRegister(jobType) : findWorkflowRegisterByJobType(jobType.getId()));
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
	 * @see au.gov.qld.fire.jms.service.EntityService#findWorkflowRegisterByWorkGroup(java.lang.Long)
	 */
	public WorkflowRegister findWorkflowRegisterByWorkGroup(Long workGroupId) throws ServiceException
	{
        try
        {
        	WorkGroup workGroup = findWorkGroupById(workGroupId);
    		WorkflowRegister result = new WorkflowRegister(workGroup);
    		// function(s)
    		List<SecurityGroup> items = securityGroupDao.findByWorkGroupId(workGroupId);
    		for (SecurityGroup item : securityGroupDao.findAll())
    		{
    			if (items.contains(item))
    			{
    				for (SystemFunction f : item.getSystemFunctions())
    				{
    					result.add(f);
    				}
    			}
    		}
    		// workflow(s)
    		for (ActionWorkflow item : actionWorkflowDao.findAll())
    		{
    			ActionCode ac = item.getActionCode();
    			ActionCode nac = item.getNextActionCode();
    			if (ac != null && workGroup.equals(ac.getWorkGroup()))
    			{
    				result.add(item);
    			}
    			else if (nac != null && workGroup.equals(nac.getWorkGroup()))
    			{
    				result.add(item);
    			}
    		}
    		//
        	return result;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityService#findWorkflowRegisterByJobType(java.lang.Long)
	 */
	public WorkflowRegister findWorkflowRegisterByJobType(Long jobTypeId) throws ServiceException
	{
        try
        {
        	JobType jobType = findJobTypeById(jobTypeId);
    		WorkflowRegister result = new WorkflowRegister(jobType);
    		// TODO: function(s)
    		// workflow(s)
    		for (ActionWorkflow item : actionWorkflowDao.findAll())
    		{
    			ActionCode ac = item.getActionCode();
    			ActionCode nac = item.getNextActionCode();
    			if (ac != null && jobType.equals(ac.getJobType()))
    			{
    				result.add(item);
    			}
    			else if (nac != null && jobType.equals(nac.getJobType()))
    			{
    				result.add(item);
    			}
    		}
    		//
        	return result;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

}