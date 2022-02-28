package au.gov.qld.fire.jms.web.module.file;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.file.FileDoc;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FileDocAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        //set references
        request.setAttribute(SessionConstants.DOC_TYPES, getEntityService().findDocTypes());
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
        FileForm myform = (FileForm) form;
        File file = myform.getEntity();
        // refresh lazy collections
        List<FileDoc> fileDocs = getFileService().findFileDocs(file.getId());
        file.setFileDocs(fileDocs);
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
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
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
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addFileDoc(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE addFileDoc()..");
        try
        {
            //set form data
            FileForm myform = (FileForm) form;
            File file = myform.getEntity();
            file.add(new FileDoc());
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
    public ActionForward moveFileDoc(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE moveFileDoc()..");
        try
        {
            //get index to remove
            int index = WebUtils.getIndex(request);
            //set form data
            FileForm fileForm = FileForm.getInstance(request);
            File file = fileForm.getEntity();
            FileDoc fileDoc = file.getFileDocs().get(index);
            FileDocForm myform = (FileDocForm) form;
            myform.setEntity(fileDoc);

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
    public ActionForward removeFileDoc(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE removeFileDoc()..");
        try
        {
            //get index to remove
            int index = WebUtils.getIndex(request);
            //set form data
            FileForm myform = (FileForm) form;
            File file = myform.getEntity();
            FileDoc fileDoc = file.getFileDocs().get(index);
            file.getFileDocs().remove(fileDoc);
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
     * Save changes (if any).
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
            File file = myform.getEntity();
            //save FileDoc(s) changes
            List<FileDoc> fileDocs = file.getFileDocs();
            List<Document> attachments = myform.getAttachments();
            int i = 0;
            for (FileDoc fileDoc : fileDocs)
            {
                Document document = attachments.get(i++);
                if (document != null)
                {
                    fileDoc.setDocument(document);
                }
            }
            getFileService().saveFileDoc(file.getFileId(), fileDocs);

            return findForwardSuccess(mapping);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return findForwardError(mapping);
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
    public ActionForward moveFileDocSave(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE moveFileDocSave()..");
        try
        {
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty())
            {
                saveErrors(request, response, errors);
                populateRequest(form, request);
                return findForwardError(mapping);
            }

            FileDocForm myform = (FileDocForm) form;
            FileDoc fileDoc = myform.getEntity();
            //save FileDoc move changes
            getFileService().moveFileDoc(fileDoc.getId(), myform.getMoveFileId(),
                myform.getMoveFcaId());

            return findForwardSuccess(mapping);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return findForwardError(mapping);
        }
    }

    public ActionForward downloadFileDoc(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        return downloadDocument(mapping, form, request, response);
    }

    public ActionForward downloadFcaDoc(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	String pathname = WebUtils.getStringId(request);
        WebUtils.downloadContent(response, pathname);
        return null;
    }

}