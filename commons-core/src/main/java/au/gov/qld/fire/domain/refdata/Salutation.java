package au.gov.qld.fire.domain.refdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import au.gov.qld.fire.domain.Auditable;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "SALUTATION")
@org.hibernate.annotations.Entity(mutable = false)
@Cache(region = "au.gov.qld.fire.domain.refdata.Salutation", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Salutation extends Auditable<String>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -1716345612641208891L;

    /** IGNORE */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"salutation"});

    public static final Salutation UNKNOWN = new Salutation("-");

    public Salutation()
    {
        super();
    }

    public Salutation(String id)
    {
        super(id);
    }

    @Id
    @Column(name = "SALUTATION")
    public String getSalutation()
    {
        return super.getId();
    }

    public void setSalutation(String salutation)
    {
        super.setId(salutation);
    }

}