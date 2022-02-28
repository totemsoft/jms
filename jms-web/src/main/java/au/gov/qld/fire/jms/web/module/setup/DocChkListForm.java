package au.gov.qld.fire.jms.web.module.setup;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.jms.domain.document.DocChkList;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class DocChkListForm extends AbstractValidatorForm<DocChkList>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -9205161643519154749L;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        if (getEntity() != null)
        {
            getEntity().setActive(false);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(DocChkList entity)
    {
        super.setEntity(entity);
        //optional
    }

}