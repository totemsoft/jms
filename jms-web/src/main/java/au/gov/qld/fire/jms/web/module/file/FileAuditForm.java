package au.gov.qld.fire.jms.web.module.file;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import au.gov.qld.fire.jms.domain.building.Building;
import au.gov.qld.fire.jms.domain.building.BuildingContact;
import au.gov.qld.fire.jms.domain.entity.Owner;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.refdata.BuildingContactType;
import au.gov.qld.fire.jms.domain.refdata.OwnerTypeEnum;
import au.gov.qld.fire.jms.domain.sap.SapHeader;

public class FileAuditForm extends ValidatorForm
{

	/** serialVersionUID */
	private static final long serialVersionUID = -349150730639479240L;

    private File entity;

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        //file = null;
    }

    public File getEntity()
    {
        return entity;
    }

    public void setEntity(File file)
    {
        this.entity = file;
        if (file.getSapHeader() == null) {
        	file.setSapHeader(new SapHeader());
        }
    }

    /**
     * Required for JSP.
     * @return
     */
    public Building getBuilding()
    {
        return getEntity().getBuilding();
    }
    public Owner getOwner()
    {
    	Owner result = getEntity().getOwner();
    	if (result == null) {
    		result = new Owner(OwnerTypeEnum.OWNER);
    		getEntity().add(result);
    	}
        return result;
    }
    public Owner getBodyCorporate()
    {
    	Owner result = getEntity().getBodyCorporate();
    	if (result == null) {
    		result = new Owner(OwnerTypeEnum.BODY_CORPORATE);
    		getEntity().add(result);
    	}
        return result;
    }
    public Owner getPropertyManager()
    {
    	Owner result = getEntity().getPropertyManager();
    	if (result == null) {
    		result = new Owner(OwnerTypeEnum.PROPERTY_MANAGER);
    		getEntity().add(result);
    	}
        return result;
    }
    public Owner getTenant()
    {
    	Owner result = getEntity().getTenant();
    	if (result == null) {
    		result = new Owner(OwnerTypeEnum.TENANT);
    		getEntity().add(result);
    	}
        return result;
    }
    public Owner getAlternate()
    {
    	Owner result = getEntity().getAlternate();
    	if (result == null) {
    		result = new Owner(OwnerTypeEnum.ALTERNATE);
    		getEntity().add(result);
    	}
        return result;
    }
    public List<BuildingContact> getOwnerBuildingContacts()
    {
    	List<BuildingContact> result = getEntity().getOwnerBuildingContacts();
    	// to allow edit
    	while (result.size() < 3) {
    		BuildingContact bc = new BuildingContact();
    		bc.setBuildingContactType(BuildingContactType.OWNER);
    		result.add(bc);
    		getEntity().add(bc);
    		bc.setPriority(result.size());
    	}
        return result;
    }
	public List<BuildingContact> getSecurityBuildingContacts()
	{
		return getEntity().getSecurityBuildingContacts();
	}

}