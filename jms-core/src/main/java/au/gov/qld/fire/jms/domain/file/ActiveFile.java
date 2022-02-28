package au.gov.qld.fire.jms.domain.file;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "V_ACTIVE_FILE")
public class ActiveFile extends AbstractActiveFile
{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "FILE_ID", nullable = false)
    private Long fileId;

    /**
     * @return the fileNo
     */
    public Long getFileId()
    {
        return fileId;
    }

}