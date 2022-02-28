package au.gov.qld.fire.jms.web.module.file;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.BasePair;
import au.gov.qld.fire.domain.location.Area;
import au.gov.qld.fire.domain.location.Region;
import au.gov.qld.fire.domain.location.Station;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.KeyReceipt;
import au.gov.qld.fire.jms.domain.ase.AlarmPanel;
import au.gov.qld.fire.jms.domain.ase.AseChangeSupplier;
import au.gov.qld.fire.jms.domain.ase.AseConn;
import au.gov.qld.fire.jms.domain.ase.AseFile;
import au.gov.qld.fire.jms.domain.building.Building;
import au.gov.qld.fire.jms.domain.building.BuildingContact;
import au.gov.qld.fire.jms.domain.entity.Owner;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.refdata.AseType;
import au.gov.qld.fire.jms.domain.refdata.OwnerTypeEnum;
import au.gov.qld.fire.jms.domain.refdata.SiteType;
import au.gov.qld.fire.jms.domain.sap.SapHeader;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FileForm extends AbstractAttachmentForm<File>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 6555083615131494333L;

    public static final String BEAN_NAME = "fileForm";

    /** date portion of aseChange.dateChange */
    private String aseDateChange;

    /** time portion of aseChange.dateChange */
    private String aseTimeChange;

    private BasePair parent;

    private List<BasePair> children;

    private Owner editOwner;

    private Building building;

    private List<AseChangeSupplier> aseInstallationAseChangeSuppliers;

    private List<AseChangeSupplier> telcoAseChangeSuppliers;

    private List<BuildingContact> ownerBuildingContacts;

    @Deprecated
    private List<BuildingContact> daytimeBuildingContacts;

    @Deprecated
    private List<BuildingContact> afterHoursBuildingContacts;

    private List<BuildingContact> securityBuildingContacts;

    private List<BuildingContact> fireBuildingContacts;

    /**
     * 
     * @param request
     * @return
     */
    public static FileForm getInstance(HttpServletRequest request)
    {
        return (FileForm) request.getSession().getAttribute(BEAN_NAME);
    }

    /* (non-Javadoc)
     * @see org.apache.struts.validator.ValidatorForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        //do nothing (this form is session bound)
        //super.reset(mapping, request);
    }

    /* (non-Javadoc)
     * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        ActionErrors errors = super.validate(mapping, request);
//        String validationKey = getValidationKey(mapping, request);
//        //aseChange validation
//        if ("fileForm.aseChange".equals(validationKey))
//        {
//            // check for ASE Installation duplicates
//            List<AseChangeSupplier> aseInstallationAseChangeSuppliers = getAseInstallationAseChangeSuppliers();
//            List<Supplier> duplicates = new ArrayList<Supplier>();
//            for (int i = 0; i < aseInstallationAseChangeSuppliers.size(); i++)
//            {
//                AseChangeSupplier aseChangeSupplier = aseInstallationAseChangeSuppliers.get(i);
//                Supplier supplier = aseChangeSupplier.getId().getSupplier();
//                if (duplicates.contains(supplier))
//                {
//                    String key = "aseInstallationAseChangeSuppliers[" + i
//                        + "].id.supplier.supplierId";
//                    errors.add(key, new ActionMessage("errors.aseChangeSupplier.duplicate"));
//                }
//                else
//                {
//                    duplicates.add(supplier);
//                }
//            }
//            // check for Telco duplicates
//            List<AseChangeSupplier> telcoAseChangeSuppliers = getTelcoAseChangeSuppliers();
//            duplicates.clear();
//            for (int i = 0; i < telcoAseChangeSuppliers.size(); i++)
//            {
//                AseChangeSupplier aseChangeSupplier = telcoAseChangeSuppliers.get(i);
//                Supplier supplier = aseChangeSupplier.getId().getSupplier();
//                if (duplicates.contains(supplier))
//                {
//                    String key = "telcoAseChangeSuppliers[" + i + "].id.supplier.supplierId";
//                    errors.add(key, new ActionMessage("errors.aseChangeSupplier.duplicate"));
//                }
//                else
//                {
//                    duplicates.add(supplier);
//                }
//            }
//        }
        return errors;
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(File entity)
    {
        super.setEntity(entity);
        // FCA
        if (getEntity().getFca() == null)
        {
            getEntity().setFca(new Fca());
        }
        // Owners
        getEntity().getOwners().size();
        // I've had discussions with the Data Management Supervisor and we can expand on the copy functions.
        // As much as I liked your idea for the "If Null" display Parent Information; the Data people didn't. I think they'd prefer to have null if not known and keep it in their stats. More work for them, but oh well.
//        Fca parent = getEntity().getFca().getParent();
//        if (parent != null && parent.getFile() != null)
//        {
//            if (getEntity().getOwner() == null)
//            {
//                getEntity().add(parent.getFile().getOwner());
//            }
//            if (getEntity().getBodyCorporate() == null)
//            {
//                getEntity().add(parent.getFile().getBodyCorporate());
//            }
//            if (getEntity().getPropertyManager() == null)
//            {
//                getEntity().add(parent.getFile().getPropertyManager());
//            }
//            if (getEntity().getTenant() == null)
//            {
//                getEntity().add(parent.getFile().getTenant());
//            }
//        }
        // Building view
        this.building = getEntity().getBuilding();
        if (this.building == null)
        {
        	setBuilding(new Building());
        }
        // MultiSite view
        this.parent = Fca.toBasePair(getEntity().getFca().getParent());
        this.children = new ArrayList<BasePair>();
        for (Fca child : getEntity().getFca().getChildren())
        {
            getChildren().add(Fca.toBasePair(child));
        }
        // FCA/ASE view
        if (entity.getSiteType() == null)
        {
            entity.setSiteType(new SiteType());
        }
        if (getEntity().getFca().getRegion() == null)
        {
            getEntity().getFca().setRegion(new Region());
        }
        if (getEntity().getFca().getArea() == null)
        {
            getEntity().getFca().setArea(new Area());
        }
        if (getEntity().getFca().getStation() == null)
        {
            getEntity().getFca().setStation(new Station());
        }
        if (getEntity().getAseFile() == null)
        {
            getEntity().setAseFile(new AseFile());
        }
        if (getEntity().getAseFile().getAseType() == null)
        {
            getEntity().getAseFile().setAseType(new AseType());
        }
        if (getEntity().getAseFile().getAseConn() == null)
        {
            getEntity().getAseFile().setAseConn(new AseConn());
        }
//        // ASE Change Over - Supplier Scheduling view
//        if (getEntity().getAseFile().getAseChange() == null)
//        {
//            getEntity().getAseFile().setAseChange(new AseChange());
//        }
        this.aseInstallationAseChangeSuppliers = new ArrayList<AseChangeSupplier>();
        this.telcoAseChangeSuppliers = new ArrayList<AseChangeSupplier>();
        // StationKey view
        if (getEntity().getKeyReceipt() == null)
        {
            getEntity().setKeyReceipt(new KeyReceipt());
        }
        // EmergencyContacts
        this.ownerBuildingContacts = new ArrayList<BuildingContact>();
        this.daytimeBuildingContacts = new ArrayList<BuildingContact>();
        this.afterHoursBuildingContacts = new ArrayList<BuildingContact>();
        this.securityBuildingContacts = new ArrayList<BuildingContact>();
        this.fireBuildingContacts = new ArrayList<BuildingContact>();
        // FirePanel view
        if (getEntity().getAlarmPanel() == null)
        {
            getEntity().setAlarmPanel(new AlarmPanel());
        }
        if (getEntity().getAlarmPanel().getSupplier() == null)
        {
            getEntity().getAlarmPanel().setSupplier(new Supplier());
        }
        // SAP Billing
        if (getEntity().getSapHeader() == null)
        {
            getEntity().setSapHeader(new SapHeader());
        }
        // to refresh lazy hibernate collections
        getEntity().getFileActions().size();
        getEntity().getFileDocs().size();
        getEntity().getKeyRegs().size();
    }

    /**
     * @return the editOwner
     */
    public Owner getEditOwner()
    {
        return editOwner;
    }

    public void setEditOwner(Owner editOwner)
    {
        this.editOwner = editOwner;
    }

    /**
     * Required for JSP.
     * @return
     */
    public Owner getDefaultOwner()
    {
    	Owner result = getEntity().getDefaultOwner();
    	if (result == null) {
    		result = new Owner(OwnerTypeEnum.OWNER);
    	}
        return result;
    }
    public Owner getOwner()
    {
        return getEntity().getOwner();
    }
    public Owner getBodyCorporate()
    {
        return getEntity().getBodyCorporate();
    }
    public Owner getPropertyManager()
    {
        return getEntity().getPropertyManager();
    }
    public Owner getTenant()
    {
        return getEntity().getTenant();
    }
    public Owner getAlternate()
    {
    	Owner result = getEntity().getAlternate();
    	if (result == null) {
    		result = new Owner(OwnerTypeEnum.ALTERNATE);
    	}
        return result;
    }

    public Station getStation()
    {
        return getEntity().getFca().getStation();
    }

    /**
     * Required for JSP.
     * @return
     */
    public AseFile getAseFile()
    {
        return getEntity().getAseFile();
    }

    /**
     * Required for multiSite JSP.
     * @return
     */
    public BasePair getParent()
    {
        if (parent == null)
        {
            parent = new BasePair();
        }
        return parent;
    }
    public void setParent(BasePair parent)
    {
        this.parent = parent;
    }

    /**
     * 
     * @return
     */
    public List<BasePair> getChildren()
    {
        return children;
    }

    /**
     * Required for JSP.
     * @return
     */
    public Building getBuilding()
    {
    	return building;
        //return getEntity().getBuilding();
    }

	public void setBuilding(Building building)
	{
		this.building = building;
        getEntity().setBuilding(this.building);
	}

	/**
     * @return the aseDateChange
     */
    public String getAseDateChange()
    {
        return aseDateChange;
    }

    public void setAseDateChange(String aseDateChange)
    {
        this.aseDateChange = aseDateChange;
    }

    /**
     * @return the aseTimeChange
     */
    public String getAseTimeChange()
    {
        return aseTimeChange;
    }

    public void setAseTimeChange(String aseTimeChange)
    {
        this.aseTimeChange = aseTimeChange;
    }

    /**
     * @return the aseInstallationAseChangeSuppliers
     */
    public List<AseChangeSupplier> getAseInstallationAseChangeSuppliers()
    {
        return aseInstallationAseChangeSuppliers;
    }

    /**
     * @return the telcoAseChangeSuppliers
     */
    public List<AseChangeSupplier> getTelcoAseChangeSuppliers()
    {
        return telcoAseChangeSuppliers;
    }

    /**
	 * @return the ownerBuildingContacts
	 */
	public List<BuildingContact> getOwnerBuildingContacts()
	{
		return ownerBuildingContacts;
	}

	/**
     * @return the daytimeBuildingContacts
     */
    @Deprecated
    public List<BuildingContact> getDaytimeBuildingContacts()
    {
        return daytimeBuildingContacts;
    }

    /**
     * @return the afterHoursBuildingContacts
     */
    @Deprecated
    public List<BuildingContact> getAfterHoursBuildingContacts()
    {
        return afterHoursBuildingContacts;
    }

	/**
	 * @return the securityBuildingContacts
	 */
	public List<BuildingContact> getSecurityBuildingContacts()
	{
		return securityBuildingContacts;
	}

	/**
	 * @return the fireBuildingContacts
	 */
	public List<BuildingContact> getFireBuildingContacts()
	{
		return fireBuildingContacts;
	}

}