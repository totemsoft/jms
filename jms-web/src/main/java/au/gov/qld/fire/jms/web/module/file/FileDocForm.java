package au.gov.qld.fire.jms.web.module.file;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.file.FileDoc;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FileDocForm extends AbstractValidatorForm<FileDoc>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 1250479119621474021L;

    /** moveFileId */
    private Long moveFileId;
    
    /** moveFcaId */
    private String moveFcaId;

    /* (non-Javadoc)
     * @see org.apache.struts.validator.ValidatorForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        super.reset(mapping, request);
        moveFileId = null;
        moveFcaId = null;
        setEntity(new FileDoc());
        getEntity().setFile(new File());
        getEntity().getFile().setFca(new Fca());
    }

    /**
     * @return the moveFileId
     */
    public Long getMoveFileId()
    {
        return moveFileId;
    }

    /**
     * @param moveFileId the moveFileId to set
     */
    public void setMoveFileId(Long moveFileId)
    {
        this.moveFileId = moveFileId;
    }

    /**
     * @return the moveFcaId
     */
    public String getMoveFcaId()
    {
        return moveFcaId;
    }

    /**
     * @param moveFcaId the moveFcaId to set
     */
    public void setMoveFcaId(String moveFcaId)
    {
        this.moveFcaId = moveFcaId;
    }
    
}