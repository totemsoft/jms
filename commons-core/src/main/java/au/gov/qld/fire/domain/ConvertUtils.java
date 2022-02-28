package au.gov.qld.fire.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.entity.Company;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.location.Area;
import au.gov.qld.fire.domain.location.Region;
import au.gov.qld.fire.domain.location.State;
import au.gov.qld.fire.domain.location.Station;
import au.gov.qld.fire.domain.refdata.DocType;
import au.gov.qld.fire.domain.refdata.Salutation;
import au.gov.qld.fire.domain.refdata.SupplierType;
import au.gov.qld.fire.domain.refdata.UserType;
import au.gov.qld.fire.domain.refdata.WorkGroup;
import au.gov.qld.fire.domain.report.ReportDoc;
import au.gov.qld.fire.domain.security.SecurityGroup;
import au.gov.qld.fire.domain.security.SystemFunction;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.domain.task.ScheduledTask;
import au.gov.qld.fire.domain.user.StaffLeave;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class ConvertUtils
{

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(Address source, Address target)
    {
        BeanUtils.copyProperties(source, target, Address.IGNORE);
        target.setState(source.getState());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(Area source, Area target)
    {
        BeanUtils.copyProperties(source, target, Area.IGNORE);
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
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(Company source, Company target)
    {
		// mandatory content
        if (source != null && source.getName() != null)
        {
            BeanUtils.copyProperties(source, target, Company.IGNORE);
        }
	}

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(Contact source, Contact target)
    {
        BeanUtils.copyProperties(source, target, Contact.IGNORE);
        target.setSalutation(source.getSalutation());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(DocType source, DocType target)
    {
        BeanUtils.copyProperties(source, target, DocType.IGNORE);
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(Document source, Document target)
    {
		// mandatory content
        if (source != null && source.getContent() != null)
        {
            BeanUtils.copyProperties(source, target, Document.IGNORE);
        }
	}

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(Region source, Region target)
    {
        BeanUtils.copyProperties(source, target, Region.IGNORE);
        if (target.getContact() == null)
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
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(ReportDoc source, ReportDoc target)
    {
        BeanUtils.copyProperties(source, target, ReportDoc.IGNORE);
        target.setDocType(source.getDocType());
        target.setDocument(source.getDocument());
        target.setTemplate(source.getTemplate());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(Salutation source, Salutation target)
    {
        BeanUtils.copyProperties(source, target, Salutation.IGNORE);
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(ScheduledTask source, ScheduledTask target)
	{
        BeanUtils.copyProperties(source, target, ScheduledTask.IGNORE);
	}

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(SecurityGroup source, SecurityGroup target)
    {
        BeanUtils.copyProperties(source, target, SecurityGroup.IGNORE);
        target.setSystemFunctions(source.getSystemFunctions());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(StaffLeave source, StaffLeave target)
    {
        BeanUtils.copyProperties(source, target, StaffLeave.IGNORE);
	}
   
    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(State source, State target)
    {
        BeanUtils.copyProperties(source, target);
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(Station source, Station target)
    {
        BeanUtils.copyProperties(source, target, Station.IGNORE);
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
        target.setArea(source.getArea());
    }

    public static void copyProperties(Supplier source, Supplier target)
    {
        BeanUtils.copyProperties(source, target, Supplier.IGNORE);
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
        if (target.getPostAddress().getId() == null) {
            target.setPostAddress(source.getPostAddress());
        }
        else {
            copyProperties(source.getPostAddress(), target.getPostAddress());
        }
        target.setMasterSupplier(source.getMasterSupplier());
        target.setRegion(source.getRegion());
        target.setSupplierType(source.getSupplierType());
        target.setSupplierTypeMatch(source.getSupplierTypeMatch());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(SupplierType source, SupplierType target)
    {
        BeanUtils.copyProperties(source, target, SupplierType.IGNORE);
    }

    /**
     * 
     * @param source
     * @param target
     */
	public static void copyProperties(SystemFunction source, SystemFunction target)
	{
        BeanUtils.copyProperties(source, target, SystemFunction.IGNORE);
	}
    
    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(Template source, Template target)
    {
        BeanUtils.copyProperties(source, target, Template.IGNORE);
        target.setTemplateType(source.getTemplateType());
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(User source, User target)
    {
        BeanUtils.copyProperties(source, target, User.IGNORE);
        if (target.getContact().getId() == null)
        {
            target.setContact(source.getContact());
        }
        else
        {
            copyProperties(source.getContact(), target.getContact());
        }
        if (source.getSecurityGroup().getId() != null)
        {
            target.setSecurityGroup(source.getSecurityGroup());
        }
        if (source.getUserType().getId() != null)
        {
            target.setUserType(source.getUserType());
        }
        if (source.getSupplier() != null && source.getSupplier().getId() != null)
        {
            target.setSupplier(source.getSupplier());
        }
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(UserType source, UserType target)
    {
        BeanUtils.copyProperties(source, target, UserType.IGNORE);
    }

    /**
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(WorkGroup source, WorkGroup target)
    {
        BeanUtils.copyProperties(source, target, WorkGroup.IGNORE);
    }

    /**
     * Utility method
     * @param systemFunctions
     * @return
     */
    public static Map<String, List<SystemFunction>> toMap(List<SystemFunction> systemFunctions)
    {
        Map<String, List<SystemFunction>> systemFunctionsMap = new TreeMap<String, List<SystemFunction>>();
        String key = null;
        List<SystemFunction> value = null;
        for (SystemFunction systemFunction : systemFunctions)
        {
            if (!StringUtils.equals(key, systemFunction.getModule()))
            {
                key = systemFunction.getModule();
                value = new ArrayList<SystemFunction>();
                systemFunctionsMap.put(key, value);
            }
            value.add(systemFunction);
        }
        return systemFunctionsMap;
    }

}