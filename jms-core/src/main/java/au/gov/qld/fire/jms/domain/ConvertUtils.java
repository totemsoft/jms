package au.gov.qld.fire.jms.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Variables;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.entity.Company;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.location.State;
import au.gov.qld.fire.domain.refdata.CompanyTypeEnum;
import au.gov.qld.fire.domain.refdata.MailType;
import au.gov.qld.fire.jms.domain.action.ActionOutcome;
import au.gov.qld.fire.jms.domain.action.ActionWorkflow;
import au.gov.qld.fire.jms.domain.action.BaseActionComparatorCompletedDate;
import au.gov.qld.fire.jms.domain.action.BaseActionComparatorDueDate;
import au.gov.qld.fire.jms.domain.action.FileAction;
import au.gov.qld.fire.jms.domain.action.JobAction;
import au.gov.qld.fire.jms.domain.ase.AlarmPanel;
import au.gov.qld.fire.jms.domain.ase.AseChange;
import au.gov.qld.fire.jms.domain.ase.AseConn;
import au.gov.qld.fire.jms.domain.ase.AseFile;
import au.gov.qld.fire.jms.domain.ase.AseKey;
import au.gov.qld.fire.jms.domain.ase.AseKeyInvoice;
import au.gov.qld.fire.jms.domain.ase.AseKeyOrder;
import au.gov.qld.fire.jms.domain.building.Building;
import au.gov.qld.fire.jms.domain.building.BuildingContact;
import au.gov.qld.fire.jms.domain.document.DocChkList;
import au.gov.qld.fire.jms.domain.entity.Owner;
import au.gov.qld.fire.jms.domain.entity.StakeHolder;
import au.gov.qld.fire.jms.domain.file.Archive;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.file.FileDoc;
import au.gov.qld.fire.jms.domain.file.FileDocChkList;
import au.gov.qld.fire.jms.domain.isolation.IsolationHistory;
import au.gov.qld.fire.jms.domain.job.Job;
import au.gov.qld.fire.jms.domain.job.JobDoc;
import au.gov.qld.fire.jms.domain.job.JobRequest;
import au.gov.qld.fire.jms.domain.mail.MailRegister;
import au.gov.qld.fire.jms.domain.mailOut.OM89Form;
import au.gov.qld.fire.jms.domain.mailOut.OM89Form.AlarmDetails;
import au.gov.qld.fire.jms.domain.mailOut.OM89Form.BodyCorporate;
import au.gov.qld.fire.jms.domain.mailOut.OM89Form.EmergencyContactInfo;
import au.gov.qld.fire.jms.domain.mailOut.OM89Form.FutureEmergencyContacts;
import au.gov.qld.fire.jms.domain.mailOut.OM89Form.PremiseOwnerDetails;
import au.gov.qld.fire.jms.domain.mailOut.OM89Form.PropertyManager;
import au.gov.qld.fire.jms.domain.mailOut.OM89Form.SecurityContactInfo;
import au.gov.qld.fire.jms.domain.mailOut.OM89Form.TenantDetails;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;
import au.gov.qld.fire.jms.domain.refdata.AseConnType;
import au.gov.qld.fire.jms.domain.refdata.AseType;
import au.gov.qld.fire.jms.domain.refdata.BuildingClassification;
import au.gov.qld.fire.jms.domain.refdata.BuildingContactType;
import au.gov.qld.fire.jms.domain.refdata.BuildingContactTypeEnum;
import au.gov.qld.fire.jms.domain.refdata.FileStatus;
import au.gov.qld.fire.jms.domain.refdata.JobType;
import au.gov.qld.fire.jms.domain.refdata.OwnerTypeEnum;
import au.gov.qld.fire.jms.domain.refdata.SiteType;
import au.gov.qld.fire.jms.domain.refdata.StakeHolderType;
import au.gov.qld.fire.jms.domain.sap.Rfi;
import au.gov.qld.fire.jms.domain.sap.SapHeader;
import au.gov.qld.fire.util.Formatter;
import au.gov.qld.fire.util.ThreadLocalUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class ConvertUtils extends au.gov.qld.fire.domain.ConvertUtils
{

    /**
     * ACTION_CODE_IGNORE
     */
    public static final String[] ACTION_CODE_IGNORE = (String[]) ArrayUtils.addAll(
        Auditable.IGNORE, new String[]
        {
            ActionCode.ACTION_TYPE, ActionCode.TEMPLATE, ActionCode.WORK_GROUP
        });

    /**
     * ACTION_OUTCOME_IGNORE
     */
    public static final String[] ACTION_OUTCOME_IGNORE = (String[]) ArrayUtils.addAll(
        Auditable.IGNORE, new String[]
        {});

    /**
     * ALARM_PANEL_IGNORE
     */
    public static final String[] ALARM_PANEL_IGNORE = (String[]) ArrayUtils.addAll(
        Auditable.IGNORE, new String[]
        {
            AlarmPanel.FILE, AlarmPanel.SUPPLIER
        });

    /**
     * DOC_CHK_LIST_IGNORE
     */
    public static final String[] DOC_CHK_LIST_IGNORE = (String[]) ArrayUtils.addAll(
        Auditable.IGNORE, new String[]
        {
            DocChkList.TEMPLATE
        });

    /**
     * FILE_DOC_CHK_LIST_IGNORE
     */
    public static final String[] FILE_DOC_CHK_LIST_IGNORE = (String[]) ArrayUtils.addAll(
        Auditable.IGNORE, new String[]
        {
            FileDocChkList.FILE, FileDocChkList.DOC_CHK_LIST
        });

    /**
     * FILE_STATUS_IGNORE
     */
    public static final String[] FILE_STATUS_IGNORE = (String[]) ArrayUtils.addAll(
        Auditable.IGNORE, new String[]
        {});

    /**
     * JOB_REQUEST_IGNORE
     */
    public static final String[] JOB_REQUEST_IGNORE = (String[]) ArrayUtils.addAll(
        Auditable.IGNORE, new String[]
        {
            JobRequest.ACCEPTED_BY, JobRequest.FCA, JobRequest.JOB_TYPE, JobRequest.REQUESTED_BY
        });

    /**
     * JOB_TYPE_IGNORE
     */
    public static final String[] JOB_TYPE_IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[]
        {
            JobType.SECURITY_GROUP, JobType.ACTION_CODE, JobType.CLOSE_JOB_SECURITY_GROUP,
            JobType.WORK_GROUP
        });

    /**
     * KEY_RECEIPT_IGNORE
     */
    public static final String[] KEY_RECEIPT_IGNORE = (String[]) ArrayUtils.addAll(
        Auditable.IGNORE, new String[]
        {
            KeyReceipt.FILE
        });

    /**
     * KEY_REG_IGNORE
     */
    public static final String[] KEY_REG_IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[]
        {
            KeyReg.FILE
        });

    /**
     * ASE_CHANGE_IGNORE
     */
    public static final String[] ASE_CHANGE_IGNORE = (String[]) ArrayUtils.addAll(
        Auditable.IGNORE, new String[]
        {
            AseChange.ASE_FILE, AseChange.ASE_CHANGE_SUPPLIERS
        });

    /**
     * ASE_CONN_IGNORE
     */
    public static final String[] ASE_CONN_IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[]
        {
            AseConn.ASE_CONN_TYPE, AseConn.ASE_FILE, AseConn.SEC_ASE_CONN_TYPE
        });

    /**
     * ASE_TYPE_IGNORE
     */
    public static final String[] ASE_TYPE_IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[]
        {});

    /**
     * ASE_CONN_TYPE_IGNORE
     */
    public static final String[] ASE_CONN_TYPE_IGNORE = (String[]) ArrayUtils.addAll(
        Auditable.IGNORE, new String[]
        {});

    /**
     * RFI_IGNORE
     */
    public static final String[] RFI_IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[]
        {
            Rfi.FILE, Rfi.FILE_ACTION, Rfi.RFI_DEL_BY, Rfi.SAP_INV_COMP_BY
        });

    /**
     * SAP_HEADER_IGNORE
     */
    public static final String[] SAP_HEADER_IGNORE = (String[]) ArrayUtils.addAll(
        Auditable.IGNORE, new String[]
        {
            SapHeader.FILE
        });

    /**
     * STAKE_HOLDER_IGNORE
     */
    public static final String[] STAKE_HOLDER_IGNORE = (String[]) ArrayUtils.addAll(
        Auditable.IGNORE, new String[]
        {
            StakeHolder.ADDRESS, StakeHolder.CONTACT, StakeHolder.REGION,
            StakeHolder.STAKE_HOLDER_TYPE
        });

    /**
     * STAKE_HOLDER_TYPE_IGNORE
     */
    public static final String[] STAKE_HOLDER_TYPE_IGNORE = (String[]) ArrayUtils.addAll(
        Auditable.IGNORE, new String[]
        {});

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(ActionCode source, ActionCode target)
    {
        BeanUtils.copyProperties(source, target, ACTION_CODE_IGNORE);
        target.setActionType(source.getActionType());
        //optional
        target.setWorkGroup(source.getWorkGroup().getId() != null ? source.getWorkGroup() : null);
        target.setTemplate(source.getTemplate() != null && source.getTemplate().getId() != null ? source.getTemplate() : null);
        target.setDocumentTemplate(source.getDocumentTemplate() != null && source.getDocumentTemplate().getId() != null ? source.getDocumentTemplate() : null);
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(ActionOutcome source, ActionOutcome target)
    {
        BeanUtils.copyProperties(source, target, ACTION_OUTCOME_IGNORE);
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(ActionWorkflow source, ActionWorkflow target)
    {
        BeanUtils.copyProperties(source, target, ActionWorkflow.IGNORE);
        target.setActionCode(source.getActionCode());
        target.setActionOutcome(source.getActionOutcome());
        target.setNextActionCode(source.getNextActionCode());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(AlarmPanel source, AlarmPanel target)
    {
        BeanUtils.copyProperties(source, target, ALARM_PANEL_IGNORE);
        //target.setFile(source.getFile()); //never changed (set in UI)
        target.setSupplier(source.getSupplier());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(Building source, Building target)
    {
        BeanUtils.copyProperties(source, target, Building.IGNORE);
        copyProperties(source.getAddress(), target.getAddress());
        target.setArchBy(source.getArchBy());
        //target.setFile(source.getFile()); // never changed (set in UI)
        target.getClassifications().clear();
        for (BuildingClassification bc : source.getClassifications())
        {
            if (bc.getId() != null && !target.getClassifications().contains(bc))
            {
                target.getClassifications().add(bc);
            }
        }
        target.setNextBuilding(source.getNextBuilding());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(BuildingClassification source, BuildingClassification target)
    {
        BeanUtils.copyProperties(source, target, BuildingClassification.IGNORE);
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(SiteType source, SiteType target)
    {
        BeanUtils.copyProperties(source, target, SiteType.IGNORE);
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(BuildingContact source, BuildingContact target)
    {
        BeanUtils.copyProperties(source, target, BuildingContact.IGNORE);
        if (target.getContact().getId() == null)
        {
            target.setContact(source.getContact());
        }
        else
        {
            copyProperties(source.getContact(), target.getContact());
        }
        target.setBuildingContactType(source.getBuildingContactType());
        // FIXME: too complicated
        Company sourceCompany = source.getCompany();
        if (sourceCompany == null || sourceCompany.getName() == null)
        {
            source.setCompany(null);
        }
        if (target.getCompany() == null)
        {
            target.setCompany(source.getCompany());
        }
        else if (source.getCompany() != null)
        {
            copyProperties(source.getCompany(), target.getCompany());
        }
        // FIXME: too complicated
        Document sourceDocument = source.getDocument();
        if (sourceDocument == null || sourceDocument.getContent() == null)
        {
            source.setDocument(null);
        }
        if (target.getDocument() == null)
        {
            target.setDocument(source.getDocument());
        }
        else if (source.getDocument() != null)
        {
            copyProperties(source.getDocument(), target.getDocument());
        }
        //target.setFile(source.getFile()); //never changed (set in UI)
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(DocChkList source, DocChkList target)
    {
        BeanUtils.copyProperties(source, target, DOC_CHK_LIST_IGNORE);
        target.setTemplate(source.getTemplate());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(Fca source, Fca target)
    {
        BeanUtils.copyProperties(source, target, Fca.IGNORE);
        //Region
        target.setRegion(source.getRegion());
        if (target.getRegion().getId() == null)
        {
            target.setRegion(null);
        }
        //File
        target.setFile(source.getFile());
        if (target.getFile() != null && target.getFile().getId() == null)
        {
            target.setFile(null);
        }
        //Station
        target.setStation(source.getStation());
        if (target.getStation() != null && target.getStation().getId() == null)
        {
            target.setStation(null);
        }
        //Area
        target.setArea(source.getArea());
        if (target.getArea() != null && target.getArea().getId() == null)
        {
            target.setArea(null);
        }
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(File source, File target)
    {
        BeanUtils.copyProperties(source, target, File.IGNORE);
        //TODO: have to do this (otherwise do not save in file change status)
        target.setFileStatus(new FileStatus(source.getFileStatus().getId()));
        if (target.getSiteType() != null && target.getSiteType().getId() == null)
        {
            target.setSiteType(null);
        }
        if (target.getSapHeader() != null && target.getSapHeader().getId() == null)
        {
            target.setSapHeader(null);
        }
    }

    /**
     * OM89Form specific
     * @param auditedFile
     * @param target
     */
    public static void copyAuditedProperties(File auditedFile, File target)
    {
    	// sapHeader
        SapHeader sapHeader = target.getSapHeader();
        if (sapHeader == null) {
            target.setSapHeader(auditedFile.getSapHeader());
        } else {
        	SapHeader source = auditedFile.getSapHeader();
            if (source.getSapCustNo() != null) {
            	sapHeader.setSapCustNo(source.getSapCustNo());
            }
        }
        // building
        Building building = target.getBuilding();
        if (building == null) {
            target.setBuilding(auditedFile.getBuilding());
        } else {
            Building source = auditedFile.getBuilding();
            if (StringUtils.isNotBlank(source.getName())) {
            	building.setName(source.getName());
            }
            if (StringUtils.isNotBlank(source.getLotPlanNumber())) {
            	building.setLotPlanNumber(source.getLotPlanNumber());
            }
            copyProperties(source.getAddress(), building.getAddress());
        }
        // owner::owner
        Owner owner = target.getOwner();
        if (owner == null) {
            target.add(auditedFile.getOwner());
        } else {
        	copyProperties(auditedFile.getOwner(), owner); // owner.getContact().getSalutation().getSalutation()
        }
        // owner::bodyCorporate
        owner = target.getBodyCorporate();
        if (owner == null) {
            target.add(auditedFile.getBodyCorporate());
        } else {
        	copyProperties(auditedFile.getBodyCorporate(), owner);
        }
        // owner::propertyManager
        owner = target.getPropertyManager();
        if (owner == null) {
            target.add(auditedFile.getPropertyManager());
        } else {
        	copyProperties(auditedFile.getPropertyManager(), owner);
        }
        // owner::tenant
        owner = target.getTenant();
        if (owner == null) {
            target.add(auditedFile.getTenant());
        } else {
        	copyProperties(auditedFile.getTenant(), owner);
        }
        // owner::alternate
        owner = target.getAlternate();
        if (owner == null) {
            target.add(auditedFile.getAlternate());
        } else {
        	copyProperties(auditedFile.getAlternate(), owner);
        }
        // buildingContact(s)
        List<BuildingContact> buildingContacts = target.getBuildingContacts();
        for (BuildingContact bc : auditedFile.getBuildingContacts()) {
        	if (buildingContacts.contains(bc)) {
                copyProperties(bc, buildingContacts.get(buildingContacts.indexOf(bc)));
        	} else {
        		target.add(bc);
        	}
        }
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(Archive source, Archive target)
    {
        BeanUtils.copyProperties(source, target, Archive.IGNORE);
        //target.setFiles(source.getFiles());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(FileDoc source, FileDoc target)
    {
        BeanUtils.copyProperties(source, target, FileDoc.IGNORE);
        //target.setFile(source.getFile()); //never changed (set in UI)
        target.setDocType(source.getDocType());
        if (target.getDocument() == null && source.getDocument() != null)
        {
            target.setDocument(source.getDocument());
            target.setUploadDate(ThreadLocalUtils.getDate());
        }
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(JobDoc source, JobDoc target)
    {
        BeanUtils.copyProperties(source, target, JobDoc.IGNORE);
        //target.setJob(source.getJob()); //never changed (set in UI)
        target.setDocType(source.getDocType());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(FileDocChkList source, FileDocChkList target)
    {
        BeanUtils.copyProperties(source, target, FILE_DOC_CHK_LIST_IGNORE);
        //target.setFile(source.getFile()); //never changed (set in UI)
        target.setDocChkList(source.getDocChkList());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(FileStatus source, FileStatus target)
    {
        BeanUtils.copyProperties(source, target, FILE_STATUS_IGNORE);
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(Job source, Job target)
    {
        BeanUtils.copyProperties(source, target, Job.IGNORE);
        target.setCompletedBy(source.getCompletedBy());
        target.setFca(source.getFca());
        target.setFile(source.getFile());
        target.setJobActions(source.getJobActions());
        target.setJobDocs(source.getJobDocs());
        target.setJobRequest(source.getJobRequest());
        target.setJobType(source.getJobType());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(JobRequest source, JobRequest target)
    {
        BeanUtils.copyProperties(source, target, JOB_REQUEST_IGNORE);
        target.setAcceptedBy(source.getAcceptedBy());
        target.setFca(source.getFca());
        target.setJobType(source.getJobType());
        target.setRequestedBy(source.getRequestedBy());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(JobType source, JobType target)
    {
        BeanUtils.copyProperties(source, target, JOB_TYPE_IGNORE);
        target.setSecurityGroup(source.getSecurityGroup());
        target.setActionCode(source.getActionCode());
        target.setCloseJobSecurityGroup(source.getCloseJobSecurityGroup());
        target.setWorkGroup(source.getWorkGroup());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(IsolationHistory source, IsolationHistory target)
    {
        BeanUtils.copyProperties(source, target, IsolationHistory.IGNORE);
        target.setFca(source.getFca());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(KeyReceipt source, KeyReceipt target)
    {
        BeanUtils.copyProperties(source, target, KEY_RECEIPT_IGNORE);
        //target.setFile(source.getFile()); //never changed (set in UI)
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(KeyReg source, KeyReg target)
    {
        BeanUtils.copyProperties(source, target, KEY_REG_IGNORE);
        //target.setFile(source.getFile()); //never changed (set in UI)
    }


    public static void copyProperties(MailType source, MailType target)
    {
        BeanUtils.copyProperties(source, target, MailType.IGNORE);
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(Owner source, Owner target)
    {
        BeanUtils.copyProperties(source, target, Owner.IGNORE);
        if (target.getOwnerType().getId() == null) {
            target.setOwnerType(source.getOwnerType()); // never change once assigned
        }
        copyProperties(source.getContact(), target.getContact());
        copyProperties(source.getAddress(), target.getAddress());
        target.setArchBy(source.getArchBy());
        //target.setFile(source.getFile()); //never changed (set in UI)
        target.setNextOwner(source.getNextOwner());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(AseChange source, AseChange target)
    {
        BeanUtils.copyProperties(source, target, ASE_CHANGE_IGNORE);
        target.setAseChangeSuppliers(source.getAseChangeSuppliers());
        target.setAseFile(source.getAseFile());
    }

    /**
     * 
     * @param source
     * @param target
     */
    private static void copyProperties(AseConn source, AseConn target)
    {
        BeanUtils.copyProperties(source, target, ASE_CONN_IGNORE);
        target.setAseConnType(source.getAseConnType());
        target.setSecAseConnType(source.getSecAseConnType());
        target.setAseFile(source.getAseFile());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(AseFile source, AseFile target)
    {
        BeanUtils.copyProperties(source, target, AseFile.IGNORE);
        //target.setFile(source.getFile()); //never changed (set in UI)
        //target.setAseChange(source.getAseChange()); // @deprecated
        copyProperties(source.getAseConn(), target.getAseConn());
        target.setAseType(source.getAseType());
        target.setSubPanels(source.getSubPanels());
        target.setSupplier(source.getSupplier());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(AseKey source, AseKey target)
    {
        BeanUtils.copyProperties(source, target, AseKey.IGNORE);
        //target.setFile(source.getFile()); //never changed (set in UI)
        target.setSupplier(source.getSupplier());
        if (target.getInvoice() == null) {
            target.setInvoice(source.getInvoice());
        }
        else {
            copyProperties(source.getInvoice(), target.getInvoice());
        }
        if (target.getContact() == null || target.getContact().getId() == null) {
            target.setContact(source.getContact());
        }
        else {
            copyProperties(source.getContact(), target.getContact());
        }
        if (target.getOrder() == null) {
            target.setOrder(source.getOrder());
        }
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(AseKeyOrder source, AseKeyOrder target)
    {
        BeanUtils.copyProperties(source, target, AseKeyOrder.IGNORE);
        target.setSupplier(source.getSupplier());
        if (target.getContact().getId() == null) {
            target.setContact(source.getContact());
        }
        else {
            copyProperties(source.getContact(), target.getContact());
        }
        if (target.getAddress().getId() == null) {
            target.setAddress(source.getAddress());
        }
        else {
            copyProperties(source.getAddress(), target.getAddress());
        }
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(MailRegister source, MailRegister target)
    {
        BeanUtils.copyProperties(source, target, MailRegister.IGNORE);
        //target.setFile(source.getFile()); //never changed (set in UI)
        if (target.getContact() == null) {
            target.setContact(source.getContact());
        }
        else {
            copyProperties(source.getContact(), target.getContact());
        }
        if (target.getAddress() == null) {
            target.setAddress(source.getAddress());
        }
        else {
            copyProperties(source.getAddress(), target.getAddress());
        }
        target.setWorkGroup(source.getWorkGroup() == null || source.getWorkGroup().getId() == null ? null : source.getWorkGroup());
        target.setUser(source.getUser() == null || source.getUser().getId() == null ? null : source.getUser());
        target.setFileAction(source.getFileAction() == null || source.getFileAction().getId() == null ? null : source.getFileAction());
        target.setJobAction(source.getJobAction() == null || source.getJobAction().getId() == null ? null : source.getJobAction());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(AseType source, AseType target)
    {
        BeanUtils.copyProperties(source, target, ASE_TYPE_IGNORE);
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(AseConnType source, AseConnType target)
    {
        BeanUtils.copyProperties(source, target, ASE_CONN_TYPE_IGNORE);
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(AseKeyInvoice source, AseKeyInvoice target)
    {
        BeanUtils.copyProperties(source, target, AseKeyInvoice.IGNORE);
        target.setAuthorisedBy(source.getAuthorisedBy());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(Rfi source, Rfi target)
    {
        BeanUtils.copyProperties(source, target, RFI_IGNORE);
        //target.setFile(source.getFile()); //never changed (set in UI)
        target.setFileAction(source.getFileAction()); //just update action reference (no edit)
        target.setRfiDelBy(source.getRfiDelBy());
        target.setSapInvCompBy(source.getSapInvCompBy());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(SapHeader source, SapHeader target)
    {
        BeanUtils.copyProperties(source, target, SAP_HEADER_IGNORE);
        //target.setFile(source.getFile()); //never changed (set in UI)
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(StakeHolder source, StakeHolder target)
    {
        BeanUtils.copyProperties(source, target, STAKE_HOLDER_IGNORE);
        if (target.getContact().getId() == null)
        {
            target.setContact(source.getContact());
        }
        else
        {
            copyProperties(source.getContact(), target.getContact());
        }
        if (target.getAddress() == null)
        {
            target.setAddress(source.getAddress());
        }
        else
        {
            copyProperties(source.getAddress(), target.getAddress());
        }
        target.setRegion(source.getRegion());
        target.setStakeHolderType(source.getStakeHolderType());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(StakeHolderType source, StakeHolderType target)
    {
        BeanUtils.copyProperties(source, target, STAKE_HOLDER_TYPE_IGNORE);
    }

    /**
     * 
     * @param file
     * @param fileActionsToDo CRM Actions To Do
     * @param fileActionsCompleted CRM Completed Actions
     */
    public static List<FileAction> splitFileActions(File file, List<FileAction> fileActionsToDo, List<FileAction> fileActionsCompleted)
    {
        List<FileAction> fileActions = new ArrayList<FileAction>();
        for (FileAction fileAction : file.getFileActions())
        {
            if (fileAction.getCompletedDate() == null)
            {
                fileActionsToDo.add(fileAction);
            }
            else
            {
                fileActionsCompleted.add(fileAction);
            }
            fileActions.add(fileAction);
        }
        Collections.sort(fileActions, BaseActionComparatorDueDate.INSTANCE);
        Collections.sort(fileActionsToDo, BaseActionComparatorDueDate.INSTANCE);
        Collections.sort(fileActionsCompleted, BaseActionComparatorCompletedDate.INSTANCE);
        return fileActions;
    }

    /**
     * 
     * @param job
     * @param jobActionsToDo
     * @param jobActionsCompleted
     */
    public static List<JobAction> splitJobActions(Job job, List<JobAction> jobActionsToDo, List<JobAction> jobActionsCompleted)
    {
        List<JobAction> jobActions = new ArrayList<JobAction>();
        for (JobAction jobAction : job.getJobActions())
        {
            if (jobAction.getCompletedDate() == null)
            {
                jobActionsToDo.add(jobAction);
            }
            else
            {
                jobActionsCompleted.add(jobAction);
            }
            jobActions.add(jobAction);
        }
        Collections.sort(jobActions, BaseActionComparatorDueDate.INSTANCE);
        Collections.sort(jobActionsToDo, BaseActionComparatorDueDate.INSTANCE);
        Collections.sort(jobActionsCompleted, BaseActionComparatorCompletedDate.INSTANCE);
        return jobActions;
    }

    /**
     * 
     * @param jobs
     * @param jobActionsToDo
     * @param jobActionsCompleted
     */
    public static List<JobAction> splitJobActions(List<Job> jobs, List<JobAction> jobActionsToDo, List<JobAction> jobActionsCompleted)
    {
        List<JobAction> jobActions = new ArrayList<JobAction>();
        for (Job job : jobs)
        {
            for (JobAction jobAction : job.getJobActions())
            {
                if (jobAction.getCompletedDate() == null)
                {
                    jobActionsToDo.add(jobAction);
                }
                else
                {
                    jobActionsCompleted.add(jobAction);
                }
                jobActions.add(jobAction);
            }
        }
        Collections.sort(jobActions, BaseActionComparatorDueDate.INSTANCE);
        Collections.sort(jobActionsToDo, BaseActionComparatorDueDate.INSTANCE);
        Collections.sort(jobActionsCompleted, BaseActionComparatorCompletedDate.INSTANCE);
        return jobActions;
    }

    /**
     * 
     * @param source - source
     * @param target - target
     */
    public static File convert(OM89Form source, File target)
    {
    	//
        // OM89Form::alarmDetails
        final AlarmDetails alarmDetails = source.getAlarmDetails();
        SapHeader sapHeader = target.getSapHeader();
        if (sapHeader == null) {
            sapHeader = new SapHeader();
            target.setSapHeader(sapHeader);
        }
        convert(alarmDetails, sapHeader);
        Building building = target.getBuilding();
        if (building == null) {
            building = new Building();
            target.setBuilding(building);
        }
        convert(alarmDetails, building);
        //
        // OM89Form::premiseOwnerDetails
        Owner owner = target.getOwner();
        if (owner == null) {
        	owner = new Owner(OwnerTypeEnum.OWNER);
            target.add(owner);
        }
        convert(source.getPremiseOwnerDetails(), owner);
        //
        // OM89Form::bodyCorporate
        owner = target.getBodyCorporate();
        if (owner == null) {
        	owner = new Owner(OwnerTypeEnum.BODY_CORPORATE);
            target.add(owner);
        }
        BodyCorporate bodyCorporate = source.getBodyCorporate();
        convert(bodyCorporate, owner);
        //
        // OM89Form::propertyManager
        owner = target.getPropertyManager();
        if (owner == null) {
        	owner = new Owner(OwnerTypeEnum.PROPERTY_MANAGER);
            target.add(owner);
        }
        PropertyManager propertyManager = source.getPropertyManager();
        if (propertyManager.isSameAsBodyCorporate()) {
            convert(bodyCorporate, owner);
        } else {
            convert(propertyManager, owner);
        }
        //
        // OM89Form::tenantDetails
        owner = target.getTenant();
        if (owner == null) {
        	owner = new Owner(OwnerTypeEnum.TENANT);
            target.add(owner);
        }
        convert(source.getTenantDetails(), owner);
        //
        // OM89Form::futureEmergencyContacts
        owner = target.getAlternate();
        if (owner == null) {
        	owner = new Owner(OwnerTypeEnum.ALTERNATE);
            target.add(owner);
        }
        convert(source.getFutureEmergencyContacts(), owner);
        //
        // OM89Form::emergencyContactInfo/securityContactInfo/futureEmergencyContacts
        convert(source.getEmergencyContactInfo(), source.getSecurityContactInfo(), target);
        //
        // OM89Form::additionalInfo
        //
        // OM89Form::customerFeedback
        //
        return target;
    }
    public static OM89Form convert(File source, OM89Form target)
    {
    	if (target == null) {
    		target = new OM89Form();
    	}
    	//
        // OM89Form::alarmDetails
    	AlarmDetails alarmDetails = target.getAlarmDetails();
    	if (alarmDetails == null) {
    		alarmDetails = new AlarmDetails();
    		target.setAlarmDetails(alarmDetails);
    	}
    	final SapHeader sapHeader = source.getSapHeader();
        convert((SapHeader) sapHeader, alarmDetails);
    	final Building building = source.getBuilding();
        convert(building, alarmDetails);
        //
        // OM89Form::premiseOwnerDetails
        PremiseOwnerDetails premiseOwnerDetails = target.getPremiseOwnerDetails();
        if (premiseOwnerDetails == null) {
        	premiseOwnerDetails = new PremiseOwnerDetails();
        	target.setPremiseOwnerDetails(premiseOwnerDetails);
        }
        convert(source.getOwner(), premiseOwnerDetails);
        //
        // OM89Form::bodyCorporate
        BodyCorporate bodyCorporate = target.getBodyCorporate();
        if (bodyCorporate == null) {
        	bodyCorporate = new BodyCorporate();
            target.setBodyCorporate(bodyCorporate);
        }
        convert(source.getBodyCorporate(), bodyCorporate);
        //
        // OM89Form::propertyManager
        PropertyManager propertyManager = target.getPropertyManager();
        if (propertyManager == null) {
        	propertyManager = new PropertyManager();
            target.setPropertyManager(propertyManager);
        }
        convert(source.getPropertyManager(), propertyManager);
        //
        // OM89Form::tenantDetails
        TenantDetails tenantDetails = target.getTenantDetails();
        if (tenantDetails == null) {
        	tenantDetails = new TenantDetails();
            target.setTenantDetails(tenantDetails);
        }
        convert(source.getTenant(), tenantDetails);
        //
        // OM89Form::futureEmergencyContacts
        FutureEmergencyContacts futureEmergencyContacts = target.getFutureEmergencyContacts();
        if (futureEmergencyContacts == null) {
        	futureEmergencyContacts = new FutureEmergencyContacts();
            target.setFutureEmergencyContacts(futureEmergencyContacts);
        }
        convert(source.getAlternate(), futureEmergencyContacts);
        //
        // OM89Form::emergencyContactInfo
        EmergencyContactInfo emergencyContactInfo = target.getEmergencyContactInfo();
        if (emergencyContactInfo == null) {
        	emergencyContactInfo = new EmergencyContactInfo();
            target.setEmergencyContactInfo(emergencyContactInfo);
        }
        convert(source.getOwnerBuildingContacts(), emergencyContactInfo);
        // OM89Form::securityContactInfo
        SecurityContactInfo securityContactInfo = target.getSecurityContactInfo();
        if (securityContactInfo == null) {
        	securityContactInfo = new SecurityContactInfo();
            target.setSecurityContactInfo(securityContactInfo);
        }
        convert(source.getSecurityBuildingContacts(), securityContactInfo);
        //
        // OM89Form::futureEmergencyContacts
        //
        // OM89Form::additionalInfo
        //
        // OM89Form::customerFeedback
        //
        return target;
    }

	private static void convert(AlarmDetails source, SapHeader target)
    {
        if (StringUtils.isNotBlank(source.getCustomernumber())) {
        	target.setSapCustNo(Formatter.parseLong(source.getCustomernumber()));
        }
	}
	private static void convert(SapHeader source, AlarmDetails target)
    {
		if (source == null) {
			return;
		}
       	target.setCustomernumber(source.getSapCustNo() != null ? source.getSapCustNo().toString() : null);
	}

    private static void convert(AlarmDetails source, Building target)
    {
        if (StringUtils.isNotBlank(source.getPremisename())) {
        	target.setName(source.getPremisename());
        }
        if (StringUtils.isNotBlank(source.getChangepremisenameto())) {
        	target.setName(source.getChangepremisenameto());
        }
        if (StringUtils.isNotBlank(source.getLotplannumber())) {
        	target.setLotPlanNumber(source.getLotplannumber());
        }
        convert(source, target.getAddress());
	}
    private static void convert(Building source, AlarmDetails target)
    {
		if (source == null) {
			return;
		}
       	target.setPremisename(source.getName());
       	target.setLotplannumber(source.getLotPlanNumber());
        convert(target, source.getAddress());
	}

    private static void convert(PremiseOwnerDetails source, Owner target)
    {
    	if (StringUtils.isNotBlank(source.getOwnername())) {
            target.setLegalName(source.getOwnername());
        }
    	if (StringUtils.isNotBlank(source.getAbnacn())) {
            target.setAbn(source.getAbnacn());
        }
    	convert(source, target.getContact());
    	convert(source, target.getAddress());
	}
    private static void convert(Owner source, PremiseOwnerDetails target)
    {
    	if (source == null) {
    		return;
    	}
        target.setOwnername(source.getLegalName());
        target.setAbnacn(source.getAbn());
    	convert(source.getContact(), (OM89Form.Contact) target);
    	convert(source.getAddress(), (OM89Form.Address) target);
	}

    private static void convert(BodyCorporate source, Owner target)
    {
    	if (StringUtils.isNotBlank(source.getCompanyname())) {
            target.setLegalName(source.getCompanyname());
        }
    	if (StringUtils.isNotBlank(source.getAbnacn())) {
            target.setAbn(source.getAbnacn());
        }
    	target.setReference(source.getCtsnumber());
    	target.setManagement(source.getBodycorpmanagement());
    	convert(source, target.getContact());
    	convert(source, target.getAddress());
	}
    private static void convert(Owner source, BodyCorporate target)
    {
    	if (source == null) {
    		return;
    	}
        target.setCompanyname(source.getLegalName());
        target.setAbnacn(source.getAbn());
    	target.setCtsnumber(source.getReference());
    	target.setBodycorpmanagement(source.getManagement());
    	convert(source.getContact(), (OM89Form.Contact) target);
    	convert(source.getAddress(), (OM89Form.Address) target);
	}

    private static void convert(PropertyManager source, Owner target)
    {
    	if (StringUtils.isNotBlank(source.getCompanyname())) {
            target.setLegalName(source.getCompanyname());
        }
    	convert(source, target.getContact());
    	convert(source, target.getAddress());
	}
    private static void convert(Owner source, PropertyManager target)
    {
    	if (source == null) {
    		return;
    	}
        target.setCompanyname(source.getLegalName());
    	convert(source.getContact(), (OM89Form.Contact) target);
    	convert(source.getAddress(), (OM89Form.Address) target);
	}

    private static void convert(TenantDetails source, Owner target)
    {
    	if (StringUtils.isNotBlank(source.getCompanyname())) {
            target.setLegalName(source.getCompanyname());
        }
    	target.setOwnerOccupied(source.isOwnerOccupied());
    	convert(source, target.getContact());
    	convert(source, target.getAddress());
	}
    private static void convert(Owner source, TenantDetails target)
    {
    	if (source == null) {
    		return;
    	}
        target.setCompanyname(source.getLegalName());
    	target.setOwnerOccupied(source.isOwnerOccupied());
    	convert(source.getContact(), (OM89Form.Contact) target);
    	convert(source.getAddress(), (OM89Form.Address) target);
	}

    private static void convert(FutureEmergencyContacts source, Owner target)
    {
//    	if (StringUtils.isNotBlank(source.getRelationship())) {
//            target.setRelationship(source.getRelationship());
//        }
//    	target.setEmailpost(source.getEmailpost());
    	convert(source, target.getContact());
    	convert(source, target.getAddress());
	}
    private static void convert(Owner source, FutureEmergencyContacts target)
    {
    	if (source == null) {
    		return;
    	}
        //target.setRelationship(source.getRelationship());
    	//target.setEmailpost(source.isEmailpost());
    	convert(source.getContact(), (OM89Form.Contact) target);
    	convert(source.getAddress(), (OM89Form.Address) target);
	}

    private static void convert(EmergencyContactInfo emergencyContacts, SecurityContactInfo securityContacts, File file)
	{
		JXPathContext context = JXPathContext.newContext(file.getBuildingContacts());
		context.setLenient(true);
		Variables variables = context.getVariables();
		BuildingContact bc;
		//
		final String emergencyContactsXPath = ".[buildingContactType/@id = $typeId and trim(contact/@firstName) = $firstName and trim(contact/@surname) = $surname]";
		// 1
		variables.declareVariable("firstName", emergencyContacts.getContact1givenname());
		variables.declareVariable("surname", emergencyContacts.getContact1lastname());
		final boolean owner = true;
		if (owner) {
			// owner
			variables.declareVariable("typeId", BuildingContactTypeEnum.OWNER.getId());
			bc = (BuildingContact) context.getValue(emergencyContactsXPath);
			if (bc == null) {
				bc = new BuildingContact();
				bc.setBuildingContactType(BuildingContactType.OWNER);
				bc.getContact().setFirstName(emergencyContacts.getContact1givenname());
				bc.getContact().setSurname(emergencyContacts.getContact1lastname());
				file.add(bc);
			}
			bc.getContact().setPhone(emergencyContacts.getContact1daytimenumber());
			bc.getContact().setMobile(emergencyContacts.getContact1afterhoursnumber());
		} else {
			// daytime
			variables.declareVariable("typeId", BuildingContactTypeEnum.DAYTIME.getId());
			bc = (BuildingContact) context.getValue(emergencyContactsXPath);
			if (bc == null) {
				bc = new BuildingContact();
				bc.setBuildingContactType(BuildingContactType.DAYTIME);
				bc.getContact().setFirstName(emergencyContacts.getContact1givenname());
				bc.getContact().setSurname(emergencyContacts.getContact1lastname());
				file.add(bc);
			}
			bc.getContact().setPhone(emergencyContacts.getContact1daytimenumber());
			// afterhours
			variables.declareVariable("typeId", BuildingContactTypeEnum.AFTER_HOURS.getId());
			bc = (BuildingContact) context.getValue(emergencyContactsXPath);
			if (bc == null) {
				bc = new BuildingContact();
				bc.setBuildingContactType(BuildingContactType.AFTER_HOURS);
				bc.getContact().setFirstName(emergencyContacts.getContact1givenname());
				bc.getContact().setSurname(emergencyContacts.getContact1lastname());
				file.add(bc);
			}
			bc.getContact().setPhone(emergencyContacts.getContact1afterhoursnumber());
		}
		//
		// 2
		variables.declareVariable("firstName", emergencyContacts.getContact2givenname());
		variables.declareVariable("surname", emergencyContacts.getContact2lastname());
		if (owner) {
			// owner
			variables.declareVariable("typeId", BuildingContactTypeEnum.OWNER.getId());
			bc = (BuildingContact) context.getValue(emergencyContactsXPath);
			if (bc == null) {
				bc = new BuildingContact();
				bc.setBuildingContactType(BuildingContactType.OWNER);
				bc.getContact().setFirstName(emergencyContacts.getContact2givenname());
				bc.getContact().setSurname(emergencyContacts.getContact2lastname());
				file.add(bc);
			}
			bc.getContact().setPhone(emergencyContacts.getContact2daytimenumber());
			bc.getContact().setMobile(emergencyContacts.getContact2afterhoursnumber());
		} else {
			// daytime
			variables.declareVariable("typeId", BuildingContactTypeEnum.DAYTIME.getId());
			bc = (BuildingContact) context.getValue(emergencyContactsXPath);
			if (bc == null) {
				bc = new BuildingContact();
				bc.setBuildingContactType(BuildingContactType.DAYTIME);
				bc.getContact().setFirstName(emergencyContacts.getContact2givenname());
				bc.getContact().setSurname(emergencyContacts.getContact2lastname());
				file.add(bc);
			}
			bc.getContact().setPhone(emergencyContacts.getContact2daytimenumber());
			// afterhours
			variables.declareVariable("typeId", BuildingContactTypeEnum.AFTER_HOURS.getId());
			bc = (BuildingContact) context.getValue(emergencyContactsXPath);
			if (bc == null) {
				bc = new BuildingContact();
				bc.setBuildingContactType(BuildingContactType.AFTER_HOURS);
				bc.getContact().setFirstName(emergencyContacts.getContact2givenname());
				bc.getContact().setSurname(emergencyContacts.getContact2lastname());
				file.add(bc);
			}
			bc.getContact().setPhone(emergencyContacts.getContact2afterhoursnumber());
		}
		//
		// 3 - owner
		variables.declareVariable("firstName", emergencyContacts.getContact3givenname());
		variables.declareVariable("surname", emergencyContacts.getContact3lastname());
		if (owner) {
			// owner
			variables.declareVariable("typeId", BuildingContactTypeEnum.OWNER.getId());
			bc = (BuildingContact) context.getValue(emergencyContactsXPath);
			if (bc == null) {
				bc = new BuildingContact();
				bc.setBuildingContactType(BuildingContactType.OWNER);
				bc.getContact().setFirstName(emergencyContacts.getContact3givenname());
				bc.getContact().setSurname(emergencyContacts.getContact3lastname());
				file.add(bc);
			}
			bc.getContact().setPhone(emergencyContacts.getContact3daytimenumber());
			bc.getContact().setMobile(emergencyContacts.getContact3afterhoursnumber());
		} else {
			// daytime
			variables.declareVariable("typeId", BuildingContactTypeEnum.DAYTIME.getId());
			bc = (BuildingContact) context.getValue(emergencyContactsXPath);
			if (bc == null) {
				bc = new BuildingContact();
				bc.setBuildingContactType(BuildingContactType.DAYTIME);
				bc.setContact(new Contact());
				bc.getContact().setFirstName(emergencyContacts.getContact3givenname());
				bc.getContact().setSurname(emergencyContacts.getContact3lastname());
				file.add(bc);
			}
			bc.getContact().setPhone(emergencyContacts.getContact3daytimenumber());
			// afterhours
			variables.declareVariable("typeId", BuildingContactTypeEnum.AFTER_HOURS.getId());
			bc = (BuildingContact) context.getValue(emergencyContactsXPath);
			if (bc == null) {
				bc = new BuildingContact();
				bc.setBuildingContactType(BuildingContactType.AFTER_HOURS);
				bc.getContact().setFirstName(emergencyContacts.getContact3givenname());
				bc.getContact().setSurname(emergencyContacts.getContact3lastname());
				file.add(bc);
			}
			bc.getContact().setPhone(emergencyContacts.getContact3afterhoursnumber());
		}
		//
		// monitoredsecurity
		final String securityContactsXPath = ".[buildingContactType/@id = $typeId and trim(company/@name) = $companyName]";
		variables.undeclareVariable("firstName");
		variables.undeclareVariable("surname");
		variables.declareVariable("typeId", BuildingContactTypeEnum.SECURITY.getId());
		variables.declareVariable("companyName", securityContacts.getMonitoredsecuritycompanyname());
		bc = (BuildingContact) context.getValue(securityContactsXPath);
		if (bc == null) {
			bc = new BuildingContact();
			bc.setBuildingContactType(BuildingContactType.SECURITY);
			file.add(bc);
		}
    	if (StringUtils.isNotBlank(securityContacts.getMonitoredsecuritycompanyname())) {
    		if (bc.getCompany() == null) {
    			bc.setCompany(new Company());
    		}
    		bc.getCompany().setCompanyType(CompanyTypeEnum.SECURITY);
			bc.getCompany().setName(securityContacts.getMonitoredsecuritycompanyname());
        }
    	if (StringUtils.isNotBlank(securityContacts.getMonitoredsecurityphonenumber())) {
    		bc.getContact().setPhone(securityContacts.getMonitoredsecurityphonenumber());
    	}
	}
	private static void convert(List<BuildingContact> source, EmergencyContactInfo target)
	{
		int size = source.size();
		if (size > 0) {
			BuildingContact bc = source.get(0);
			Contact c = bc.getContact();
			target.setContact1givenname(c.getFirstName());
			target.setContact1lastname(c.getSurname());
			target.setContact1daytimenumber(c.getPhone());
			target.setContact1afterhoursnumber(c.getMobile());
		}
		if (size > 1) {
			BuildingContact bc = source.get(1);
			Contact c = bc.getContact();
			target.setContact2givenname(c.getFirstName());
			target.setContact2lastname(c.getSurname());
			target.setContact2daytimenumber(c.getPhone());
			target.setContact2afterhoursnumber(c.getMobile());
		}
		if (size > 2) {
			BuildingContact bc = source.get(2);
			Contact c = bc.getContact();
			target.setContact3givenname(c.getFirstName());
			target.setContact3lastname(c.getSurname());
			target.setContact3daytimenumber(c.getPhone());
			target.setContact3afterhoursnumber(c.getMobile());
		}
	}
	private static void convert(List<BuildingContact> source, SecurityContactInfo target)
	{
		int size = source.size();
		if (size > 0) {
			BuildingContact bc = source.get(0);
			target.setMonitoredsecuritycompanyname(bc.getCompany().getName());
			target.setMonitoredsecurityphonenumber(bc.getContact().getPhone());
		}
	}

	private static void convert(OM89Form.Address source, Address target)
    {
    	if (StringUtils.isNotBlank(source.getStreetnumber())) {
            target.setAddrLine1(source.getStreetnumber());
        }
        if (StringUtils.isNotBlank(source.getStreetname())) {
            target.setAddrLine2(source.getStreetname());
        }
        if (StringUtils.isNotBlank(source.getSuburb())) {
            target.setSuburb(source.getSuburb());
        }
        if (StringUtils.isNotBlank(source.getPostcode())) {
            target.setPostcode(source.getPostcode());
        }
        if (StringUtils.isNotBlank(source.getState())) {
            target.setState(new State(source.getState()));
        }
    }
	private static void convert(Address source, OM89Form.Address target)
    {
        target.setStreetnumber(source.getAddrLine1());
        target.setStreetname(source.getAddrLine2());
        target.setSuburb(source.getSuburb());
        target.setPostcode(source.getPostcode());
        target.setState(source.getState().getState());
    }

    private static void convert(OM89Form.Contact source, Contact target)
    {
    	if (StringUtils.isNotBlank(source.getContactname())) {
            target.setSurname(source.getContactname());
        }
    	if (StringUtils.isNotBlank(source.getEmail())) {
            target.setEmail(source.getEmail());
        }
    	if (StringUtils.isNotBlank(source.getFax())) {
            target.setFax(source.getFax());
        }
    	if (StringUtils.isNotBlank(source.getMobile())) {
            target.setMobile(source.getMobile());
        }
    	if (StringUtils.isNotBlank(source.getOffice())) {
            target.setPhone(source.getOffice());
        }
    }
    private static void convert(Contact source, OM89Form.Contact target)
    {
        target.setContactname(source.getSurname());
        target.setEmail(source.getEmail());
        target.setFax(source.getFax());
        target.setMobile(source.getMobile());
        target.setOffice(source.getPhone());
    }

}