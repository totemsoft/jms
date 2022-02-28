package au.gov.qld.fire.jms.uaa.domain.refdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;

/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "UAA_INJURY_TYPE")
public class InjuryType extends Auditable<Long>
{

	/** serialVersionUID */
	private static final long serialVersionUID = 2035074046498605206L;

    private String name;

    private String description;

    private boolean multiple;

    /**
     * 
     */
    public InjuryType()
    {
        super();
    }

    /**
     * @param id
     */
    public InjuryType(Long id)
    {
        super(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INJURY_TYPE_ID")
    public Long getId()
    {
        return super.getId();
    }

    public void setId(Long id)
    {
        super.setId(id);
    }

    @Column(name = "NAME")
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    @Column(name = "MULTIPLE")
    @Type(type = "yes_no")
	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

}