package au.gov.qld.fire.jms.domain.file;

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
@Table(name = "FILE_DOC")
public class FileDoc extends BaseDoc
{

    /** serialVersionUID */
    private static final long serialVersionUID = 2145745629205459313L;

    /** IGNORE */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(BaseDoc.IGNORE,
        new String[] {"fileDocId", "file"});

    private File file;

    public FileDoc()
    {
        super();
    }

    public FileDoc(Long id)
    {
        super(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_DOC_ID")
    public Long getFileDocId()
    {
        return super.getId();
    }

    public void setFileDocId(Long fileDocId)
    {
        super.setId(fileDocId);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID", nullable = false)
    public File getFile()
    {
        return this.file;
    }

    public void setFile(File file)
    {
        this.file = file;
    }

}