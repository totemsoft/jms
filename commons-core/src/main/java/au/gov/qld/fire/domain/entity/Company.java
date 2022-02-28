package au.gov.qld.fire.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.ArrayUtils;
import org.codehaus.jackson.annotate.JsonProperty;

import au.gov.qld.fire.domain.AbstractBusiness;
import au.gov.qld.fire.domain.refdata.CompanyTypeEnum;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "COMPANY")
public class Company extends AbstractBusiness
{

	/** serialVersionUID */
	private static final long serialVersionUID = 2678614610184613820L;

    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(AbstractBusiness.IGNORE,
        new String[] {"companyId"});

    public static final Company SECURITY = new Company(CompanyTypeEnum.SECURITY);
    public static final Company FIRE = new Company(CompanyTypeEnum.FIRE);

    private CompanyTypeEnum companyType;

    public Company()
    {
        super();
    }

    public Company(Long id)
    {
        super(id);
    }

    public Company(CompanyTypeEnum companyType)
    {
        setCompanyType(companyType);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPANY_ID")
    public Long getCompanyId()
    {
        return super.getId();
    }

    public void setCompanyId(Long companyId)
    {
        super.setId(companyId);
    }

    @Enumerated
    @Column(name = "COMPANY_TYPE_ID", nullable = false)
    @JsonProperty
    public CompanyTypeEnum getCompanyType()
	{
		return companyType;
	}

	public void setCompanyType(CompanyTypeEnum companyType)
	{
		this.companyType = companyType;
	}

    /**
     * Check for company type
     * @return
     */
    @Transient
    public boolean isFire()
    {
        return CompanyTypeEnum.FIRE.equals(getCompanyType());
    }

    @Transient
    public boolean isSecurity()
    {
        return CompanyTypeEnum.SECURITY.equals(getCompanyType());
    }

}