package au.gov.qld.fire.jms.web.module.file;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.BasePair;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.fca.FcaSearchCriteria;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.WebUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class MultiSiteAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        //set references
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
        //FileForm myform = (FileForm) form;
        //BasePair parent = myform.getParent();
    }

    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward removeParent(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE removeParent()..");
        try
        {
            // set form data
            FileForm myform = (FileForm) form;
            myform.setParent(null);
            //
            populateRequest(form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            //populateForm(mapping, form, request);
            populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addParent(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE addParent()..");
        try
        {
            // set form data
            FileForm myform = (FileForm) form;
            Fca fca = myform.getEntity().getFca();
            if (fca != null && fca.isSubPanel())
            {
            	FcaSearchCriteria criteria = new FcaSearchCriteria();
            	criteria.setFcaNo(fca.getId());
            	criteria.setSubPanel(false);
            	List<Fca> items = getFcaService().findFca(criteria);
            	if (items.size() == 1)
            	{
            		Fca item = items.get(0);
            		BasePair parent = new BasePair(item.getFile().getId(), item.getId());
            		myform.setParent(parent);
            	}
            }
            //
            populateRequest(form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            //populateForm(mapping, form, request);
            populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addSubPanel(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE addSubPanel()..");
        try
        {
            // set form data
            FileForm myform = (FileForm) form;
            Fca fca = myform.getEntity().getFca();
            if (fca != null && !fca.isSubPanel())
            {
                List<BasePair> children = myform.getChildren();
            	FcaSearchCriteria criteria = new FcaSearchCriteria();
            	criteria.setFcaNo(fca.getId());
            	criteria.setSubPanel(true);
            	List<Fca> items = getFcaService().findFca(criteria);
            	for (Fca item : items)
            	{
            		BasePair subPanel = new BasePair(item.getFile().getId(), item.getId());
            		if (!children.contains(subPanel))
            		{
            			children.add(subPanel);
            		}
            	}
            }
            //
            populateRequest(form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            //populateForm(mapping, form, request);
            populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addChild(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE addChild()..");
        try
        {
            // set form data
            FileForm myform = (FileForm) form;
            myform.getChildren().add(new BasePair());
            //
            populateRequest(form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            //populateForm(mapping, form, request);
            populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward removeChild(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE removeChild()..");
        try
        {
            // get index to remove
            int index = WebUtils.getIndex(request);
            // set form data
            FileForm myform = (FileForm) form;
            BasePair child = myform.getChildren().get(index);
            myform.getChildren().remove(child);
            //
            populateRequest(form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            //populateForm(mapping, form, request);
            populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    /**
     * Present edit entity form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE edit()..");
        try
        {
            populateForm(mapping, form, request);
            populateRequest(form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    /**
     * Update/Insert.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE save()..");
        try
        {
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty())
            {
                saveErrors(request, response, errors);
                populateRequest(form, request);
                return findForwardError(mapping);
            }

            FileForm myform = (FileForm) form;
            Fca fca = myform.getEntity().getFca();
            BasePair parent = myform.getParent();
            List<BasePair> children = myform.getChildren();
            // save changes (if any)
            getFileService().saveMultiSite(fca, parent, children);

            return findForwardSuccess(mapping);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return findForwardError(mapping);
        }
    }

}