package au.gov.qld.fire.jms.domain.file;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "V_ACTIVE_ASE_FILE")
public class ActiveAseFile extends AbstractActiveFile
{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "FILE_ID", nullable = false)
    private Long fileId;

    @Transient
    private Date aseChangeDate;

    /**
     * @return the fileNo
     */
    public Long getFileId()
    {
        return fileId;
    }

	/**
     * @return the aseChangeDate
     */
    public Date getAseChangeDate()
    {
        return aseChangeDate;
    }

    public void setAseChangeDate(Date aseChangeDate)
    {
        this.aseChangeDate = aseChangeDate;
    }

}