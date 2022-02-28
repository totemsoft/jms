package au.gov.qld.fire.jms.domain.refdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import au.gov.qld.fire.domain.Auditable;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "BRIGADE")
public class Brigade extends Auditable<Long>
{

	/** serialVersionUID */
	private static final long serialVersionUID = -6503031866557732761L;

	private String name;

    private Long sapCustNo;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "BRIGADE_ID")
    public Long getBrigadeId()
    {
        return super.getId();
    }

    public void setBrigadeId(Long brigadeId)
    {
        super.setId(brigadeId);
    }

    @Column(name = "NAME", nullable = false)
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "SAP_CUST_NO", nullable = false)
    public Long getSapCustNo()
    {
        return this.sapCustNo;
    }

    public void setSapCustNo(Long sapCustNo)
    {
        this.sapCustNo = sapCustNo;
    }

}