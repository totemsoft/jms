package au.gov.qld.fire.jms.web.module.setup;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.domain.refdata.TemplateSearchCriteria;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class TemplateAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        //set references
        request.setAttribute(SessionConstants.TEMPLATE_TYPES, getEntityService().findTemplateTypes());
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
        //get entity id
        Long id = WebUtils.getLongId(request);
        //
        TemplateForm myform = (TemplateForm) form;
        Template entity;
        if (id == null)
        {
            entity = new Template();
        }
        else
        {
            entity = getEntityService().findTemplateById(id);
        }
        myform.setEntity(entity);
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
    public ActionForward updateTemplateType(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            //set form data
            //TemplateForm myform = (TemplateForm) form;

            //
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
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward find(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
            String templateTypes = request.getParameter(SessionConstants.TEMPLATE_TYPES);

            List<Template> entities;
            if (StringUtils.isBlank(templateTypes))
            {
                entities = getEntityService().findTemplates();
            }
            else
            {
                TemplateSearchCriteria criteria = new TemplateSearchCriteria();
                criteria.setTemplateTypes(templateTypes);
                entities = getEntityService().findTemplates(criteria);
            }

            request.setAttribute(SessionConstants.ENTITIES, entities);

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
     * Present view ActionOutcome form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
            populateForm(mapping, form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Present edit user form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
            populateRequest(form, request);
            populateForm(mapping, form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    public ActionForward download(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            //get id
            Long templateId = WebUtils.getLongId(request);
            Template template = getEntityService().findTemplateById(templateId);

            //ContentTypeEnum contentType = ContentTypeEnum.findByContentType(template.getContentType());
            String contentName = template.getLocation();
            if (StringUtils.isBlank(contentName))
            {
                contentName = template.getName();
            }
            byte[] content = template.getContent();
            WebUtils.downloadContent(response, content, template.getContentType(), contentName);

            return null;
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForwardError(mapping);
        }
    }

    public ActionForward delete(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            // get id
            Long templateId = WebUtils.getLongId(request);
            getEntityService().deleteTemplate(new Template(templateId));

            return null;
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForwardError(mapping);
        }
    }

    /**
     * Update/Insert new user.
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
        try
        {
            TemplateForm myform = (TemplateForm) form;
            Template entity = myform.getEntity();
            // user entry
            if (StringUtils.isNotBlank(myform.getHtmlInput())) {
                entity.setContent(myform.getHtmlInput().getBytes());
                if (ContentTypeEnum.isHtml(entity.getContentType())) {
                    //entity.setLocation(null);
                    entity.setUploadDate(null);
                } else {
                    entity.setContentType(ContentTypeEnum.APPLICATION_HTML.getContentType());
                }
            }

            // check for errors
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty())
            {
                saveValidateErrors(request, response, errors);
                populateRequest(form, request);
                return findForwardError(mapping);
            }

            // save changes (if any)
            entity.setConfig(myform.getConfig());
            getEntityService().saveTemplate(entity);

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