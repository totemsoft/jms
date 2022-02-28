package au.gov.qld.fire.jms.domain.report;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.lang.ArrayUtils;

import au.gov.qld.fire.jms.domain.refdata.ActionCode;


/**
 * Used in UI to set user selected criteria.
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public final class ReportSearchCriteria extends au.gov.qld.fire.domain.report.ReportSearchCriteria
{

    /** serialVersionUID */
    private static final long serialVersionUID = -8976080026041935345L;

    /** File No */
    private Long fileId;

    /** actionCodeIds */
    private Long[] actionCodeIds;
    private transient ActionCode[] actionCodes;

    /** nextCompletedActionCodeId */
    private Long nextCompletedActionCodeId;
    private transient ActionCode nextCompletedActionCode;

    /**
     * @return the fileId
     */
    public Long getFileId()
    {
        if (fileId != null && fileId == 0)
        {
            fileId = null;
        }
        return fileId;
    }

    public void setFileId(Long fileId)
    {
        this.fileId = fileId;
    }

    /**
     * @return the actionCodeIds
     */
    public Long[] getActionCodeIds()
    {
        if (actionCodeIds == null)
        {
            actionCodeIds = new Long[0];
        }
        return actionCodeIds;
    }

    /**
     * @param actionCodeIds the actionCodeIds to set
     */
    public void setActionCodeIds(Long[] actionCodeIds)
    {
        this.actionCodeIds = actionCodeIds;
    }

    /**
     * @return the actionCodes
     */
    public ActionCode[] getActionCodes()
    {
        return actionCodes;
    }

    /**
     * @param actionCodes the actionCodes to set
     */
    public void setActionCodes(ActionCode[] actionCodes)
    {
        this.actionCodes = actionCodes;
    }

    /**
     * @return the nextCompletedActionCodeId
     */
    public Long getNextCompletedActionCodeId()
    {
        return nextCompletedActionCodeId;
    }

    /**
     * @param nextCompletedActionCodeId the nextCompletedActionCodeId to set
     */
    public void setNextCompletedActionCodeId(Long nextCompletedActionCodeId)
    {
        this.nextCompletedActionCodeId = nextCompletedActionCodeId;
    }

    /**
     * @return the nextCompletedActionCode
     */
    public ActionCode getNextCompletedActionCode()
    {
        return nextCompletedActionCode;
    }

    /**
     * @param nextCompletedActionCode the nextCompletedActionCode to set
     */
    public void setNextCompletedActionCode(ActionCode nextCompletedActionCode)
    {
        this.nextCompletedActionCode = nextCompletedActionCode;
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.domain.report.ReportSearchCriteria#loadCustomParameters()
     */
    @Override
    public void loadCustomParameters()
    {
        super.loadCustomParameters();
        // TODO: add properties
    }

    /**
     * 
     * @param xmlResult
     * @throws IOException 
     */
    public void write(OutputStream xmlResult) throws IOException
    {
        super.write(xmlResult);
        //fileId
        if (getFileId() != null)
        {
            xmlResult.write(("fileId=\"" + getFileId() + "\" ").getBytes());
        }
        //actionCodes
        if (!ArrayUtils.isEmpty(actionCodes))
        {
            StringBuffer sb = new StringBuffer();
            for (ActionCode actionCode : actionCodes)
            {
                if (sb.length() > 0)
                {
                    sb.append(", ");
                }
                sb.append(actionCode.getCode());
            }
            xmlResult.write(("actionCodes=\"" + sb.toString() + "\" ").getBytes());
        }
        //nextCompletedActionCode
        if (nextCompletedActionCode != null)
        {
            xmlResult.write(("nextCompletedActionCode=\"" + nextCompletedActionCode.getCode() + "\" ").getBytes());
        }            
    }

}