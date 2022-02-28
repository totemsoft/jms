package au.gov.qld.fire.domain.report;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

import au.gov.qld.fire.domain.SearchCriteria;
import au.gov.qld.fire.domain.refdata.WorkGroup;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.util.Formatter;

/**
 * Used in UI to set user selected criteria.
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ReportSearchCriteria extends SearchCriteria
{
    /** serialVersionUID */
    private static final long serialVersionUID = 3635826120577200309L;

    /** Date range start */
    private Date dateStart;

    /** Date range end */
    private Date dateEnd;

    /** workGroupId */
    private Long workGroupId;
    private transient WorkGroup workGroup;

    /** supplierId */
    private Long supplierId;
    private transient Supplier supplier;

    /** three possible values: 'Y', 'N' or ' ' (any) */
    private Character active = ' ';

    /** custom properties */
    private Properties customParameters = new Properties();

    /**
     * @return the dateEnd
     */
    public Date getDateEnd()
    {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd)
    {
        this.dateEnd = dateEnd;
    }

    /**
     * @return the dateStart
     */
    public Date getDateStart()
    {
        return dateStart;
    }

    public void setDateStart(Date dateStart)
    {
        this.dateStart = dateStart;
    }

    /**
     * @return the fileWorkGroupId
     */
    public Long getWorkGroupId()
    {
        return workGroupId;
    }

    public void setWorkGroupId(Long workGroupId)
    {
        this.workGroupId = workGroupId;
    }

    /**
     * @return the workGroup
     */
    public WorkGroup getWorkGroup()
    {
        return workGroup;
    }

    /**
     * @param workGroup the workGroup to set
     */
    public void setWorkGroup(WorkGroup workGroup)
    {
        this.workGroup = workGroup;
    }

    /**
     * @return the supplierId
     */
    public Long getSupplierId()
    {
        return supplierId;
    }

    /**
     * @param supplierId the supplierId to set
     */
    public void setSupplierId(Long supplierId)
    {
        this.supplierId = supplierId;
    }

    /**
     * @return the supplier
     */
    public Supplier getSupplier()
    {
        return supplier;
    }

    /**
     * @param supplier the supplier to set
     */
    public void setSupplier(Supplier supplier)
    {
        this.supplier = supplier;
    }

    /**
     * @return the active
     */
    public Character getActive()
    {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Character active)
    {
        this.active = active;
    }

    /**
     * @return the customParameters
     */
    public Properties getCustomParameters()
    {
        return customParameters;
    }

    /**
     * @param customParameters the customParameters to set
     */
    public void setCustomParameters(Properties customParameters)
    {
        this.customParameters = customParameters;
    }

    /**
     * 
     */
    public void loadCustomParameters()
    {
        //dateStart
        if (dateStart != null)
        {
            customParameters.setProperty("dateStart", DateUtils.YYYY_MM_DD.format(dateStart));
        }
        //dateEnd
        if (dateEnd != null)
        {
            customParameters.setProperty("dateStart", DateUtils.YYYY_MM_DD.format(dateEnd));
        }
        //workGroup
        if (workGroupId != null)
        {
            customParameters.setProperty("workGroupId", workGroupId.toString());
        }
        //supplier
        if (supplierId != null)
        {
            customParameters.setProperty("supplierId", supplierId.toString());
        }
        //active
        customParameters.setProperty("active", active != null ? active.toString() : " ");
    }

    /**
     * 
     * @param xmlResult
     * @throws IOException 
     */
    public void write(OutputStream xmlResult) throws IOException
    {
        //dateStart
        if (dateStart != null)
        {
            xmlResult.write(("dateStart=\"" + Formatter.formatDate(dateStart) + "\" ").getBytes());
        }
        //dateEnd
        if (dateEnd != null)
        {
            xmlResult.write(("dateEnd=\"" + Formatter.formatDate(dateEnd) + "\" ").getBytes());
        }
        //workGroup
        if (workGroup != null)
        {
            xmlResult.write(("workGroup=\"" + workGroup.getName() + "\" ").getBytes());
        }
        else
        {
            xmlResult.write(("workGroup=\"All\" ").getBytes());
        }
        //supplier
        if (supplier != null)
        {
            xmlResult.write(("supplier=\"" + supplier.getName() + "\" ").getBytes());
        }
        //active
        if (active == null || active.equals(' '))
        {
            //do nothing
            //xmlResult.write(("active=\"Both\" ").getBytes());
        }
        else if (active.equals('Y'))
        {
            xmlResult.write(("active=\"Active\" ").getBytes());
        }
        else if (active.equals('N'))
        {
            xmlResult.write(("active=\"Inactive\" ").getBytes());
        }
    }

}