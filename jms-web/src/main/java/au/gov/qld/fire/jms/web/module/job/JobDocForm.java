package au.gov.qld.fire.jms.web.module.job;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.job.Job;
import au.gov.qld.fire.jms.domain.job.JobDoc;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobDocForm extends AbstractValidatorForm<JobDoc>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -2622379374394853727L;

    /** moveJobId */
    private Long moveJobId;
    
    /** moveFcaId */
    //private String moveFcaId;

    /* (non-Javadoc)
     * @see org.apache.struts.validator.ValidatorForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        super.reset(mapping, request);
        moveJobId = null;
        //moveFcaId = null;
        setEntity(new JobDoc());
        getEntity().setJob(new Job());
        getEntity().getJob().setFca(new Fca());
    }

    /**
     * @return the moveFileId
     */
    public Long getMoveJobId()
    {
        return moveJobId;
    }

    /**
     * @param moveFileId the moveFileId to set
     */
    public void setMoveJobId(Long moveFileId)
    {
        this.moveJobId = moveFileId;
    }

//    /**
//     * @return the moveFcaId
//     */
//    public String getMoveFcaId()
//    {
//        return moveFcaId;
//    }
//
//    /**
//     * @param moveFcaId the moveFcaId to set
//     */
//    public void setMoveFcaId(String moveFcaId)
//    {
//        this.moveFcaId = moveFcaId;
//    }
    
}