package au.gov.qld.fire.jms.domain.job;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.ArrayUtils;

import au.gov.qld.fire.domain.document.BaseDoc;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "JOB_DOC")
public class JobDoc extends BaseDoc
{

    /** serialVersionUID */
    private static final long serialVersionUID = -1384237265687553409L;

    /**
     * JOB_DOC_IGNORE
     */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(BaseDoc.IGNORE,
        new String[] {"jobDocId", "job"});

    /** persistent field */
    private Job job;

    /**
     * 
     */
    public JobDoc()
    {
        super();
    }

    /**
     * @param id
     */
    public JobDoc(Long id)
    {
        super(id);
    }

    /** 
     *            @hibernate.id
     *             generator-class="sequence"
     *             type="java.lang.Long"
     *             column="JOB_DOC_ID"
     *         
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JOB_DOC_ID")
    public Long getJobDocId()
    {
        return super.getId();
    }

    public void setJobDocId(Long jobDocId)
    {
        super.setId(jobDocId);
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="JOB_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOB_ID", nullable = false)
    public Job getJob()
    {
        return this.job;
    }

    public void setJob(Job job)
    {
        this.job = job;
    }

}