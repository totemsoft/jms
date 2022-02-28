package au.gov.qld.fire.jms.web.module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.springframework.transaction.TransactionStatus;

import au.gov.qld.fire.IBeanNames;
import au.gov.qld.fire.domain.BasePair;
import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.jms.domain.ConvertUtils;
import au.gov.qld.fire.jms.domain.action.FileAction;
import au.gov.qld.fire.jms.domain.action.JobAction;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.job.Job;
import au.gov.qld.fire.jms.service.ActionService;
import au.gov.qld.fire.jms.service.DocumentService;
import au.gov.qld.fire.jms.service.EntityService;
import au.gov.qld.fire.jms.service.FcaService;
import au.gov.qld.fire.jms.service.FileService;
import au.gov.qld.fire.jms.service.HelpService;
import au.gov.qld.fire.jms.service.InvoiceService;
import au.gov.qld.fire.jms.service.JobService;
import au.gov.qld.fire.jms.uaa.service.InjuryService;
import au.gov.qld.fire.jms.web.module.file.FileForm;
import au.gov.qld.fire.security.GroupPrincipal;
import au.gov.qld.fire.service.CacheService;
import au.gov.qld.fire.service.SupplierService;
import au.gov.qld.fire.service.TaskService;
import au.gov.qld.fire.service.UserService;
import au.gov.qld.fire.service.ValidationException;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.util.ThreadLocalUtils;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class AbstractDispatchAction extends DispatchAction
{

    /** Logger */
    protected final Logger LOG = Logger.getLogger(getClass());

    protected final static String CANCEL = "cancel";

    protected final static String SUCCESS = "success";

    protected final static String ERROR = "error";

    protected final static String EDIT = "edit";

    protected final static String VIEW = "view";

    protected final static String FIND = "find";

    protected final static String EXPORT_DATA = "exportData";

    protected final static String IMPORT_DATA = "importData";

    protected boolean hasSystemFunction(String module, String path, String query) throws Exception
    {
        GroupPrincipal principal = getUserService().findUserGroup(ThreadLocalUtils.getUser().getLogin());
        return principal.hasSystemFunction(module, path, query);
    }

    /* (non-Javadoc)
     * @see org.apache.struts.actions.DispatchAction#unspecified(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected ActionForward unspecified(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        populateRequest(form, request);
        if (form != null)
        {
            populateForm(mapping, form, request);
        }
        return mapping.getInputForward();
    }

    /* (non-Javadoc)
     * @see org.apache.struts.actions.DispatchAction#getMethodName(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)
     */
    @Override
    protected String getMethodName(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response, String parameter)
        throws Exception
    {
        String methodName = super.getMethodName(mapping, form, request, response, parameter);
        if (methodName == null)
        {
            methodName = mapping.getProperty(parameter);
        }
        return methodName;
    }

    /**
     * Populate request data (do nothing by default).
     * @param request
     * @throws Exception
     */
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        //do nothing by default
    }

    /**
     * Populate form data (do nothing by default).
     * @param mapping
     * @param form
     * @param request
     * @throws Exception
     */
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
        //do nothing by default
    }

    /**
     * 
     * @param request
     * @return
     * @throws IOException
     */
    protected String getPayload(HttpServletRequest request)
        throws IOException
    {
    	StringBuffer payload = new StringBuffer();
	    BufferedReader reader = request.getReader();
	    String line;
	    while ((line = reader.readLine()) != null) {
	    	payload.append(line);
	    }
	    return payload.toString();
    }

    /**
     * Refresh file entity.
     * @param form
     * @param request
     * @throws Exception
     */
    protected void setEntity(FileForm form, HttpServletRequest request)
        throws Exception
    {
        File entity = form.getEntity();
        // get entity id
        Long id = WebUtils.getLongId(request);
        if (id == null && entity != null)
        {
            id = entity.getId();
        }
        // refresh
        if (id != null)
        {
            entity = getFileService().findFileById(id);
            form.setEntity(entity);
        }
    }

    protected void populatePriorities(HttpServletRequest request, String priority, List<?> list)
    {
        // why priority multiple 10, not 1? - that is how users are used to see them!!!
        // System Default of MAStermind..  it's made by GE, so it would cost more then JMS to change that one thing
        List<BasePair> priorities = new ArrayList<BasePair>();
    	priorities.add(new BasePair(0L, "00"));
        for (int i = 0; i < list.size(); i++)
        {
        	long key = i + 1;
        	priorities.add(new BasePair(key, key + "0"));
        }
        request.setAttribute(priority, priorities);
    }

    /**
     * 
     * @param file
     * @param request
     * @throws Exception
     */
    protected void populateFileActions(File file, HttpServletRequest request) throws Exception
    {
        List<FileAction> fileActionsToDo = new ArrayList<FileAction>();
        List<FileAction> fileActionsCompleted = new ArrayList<FileAction>();
        List<FileAction> fileActions = ConvertUtils.splitFileActions(file, fileActionsToDo, fileActionsCompleted);
        request.setAttribute(SessionConstants.FILE_ACTIONS_TODO, fileActionsToDo);
        request.setAttribute(SessionConstants.FILE_ACTIONS_COMPLETED, fileActionsCompleted);
        //
        Iterator<FileAction> iter = fileActions.iterator();
    	while (iter.hasNext()) {
    		FileAction a = iter.next();
    		if (a.getParentFileAction() != null) {
    			iter.remove();
    		}
    	}
        request.setAttribute(SessionConstants.FILE_ACTIONS, fileActions);
    }

    /**
     * 
     * @param jobs
     * @param request
     * @throws Exception
     */
    protected void populateJobActions(List<Job> jobs, HttpServletRequest request) throws Exception
    {
        List<JobAction> jobActionsToDo = new ArrayList<JobAction>();
        List<JobAction> jobActionsCompleted = new ArrayList<JobAction>();
        List<JobAction> jobActions = ConvertUtils.splitJobActions(jobs, jobActionsToDo, jobActionsCompleted);
        request.setAttribute(SessionConstants.JOB_ACTIONS_TODO, jobActionsToDo);
        request.setAttribute(SessionConstants.JOB_ACTIONS_COMPLETED, jobActionsCompleted);
        //
        Iterator<JobAction> iter = jobActions.iterator();
    	while (iter.hasNext()) {
    		JobAction a = iter.next();
    		if (a.getParentJobAction() != null) {
    			iter.remove();
    		}
    	}
        request.setAttribute(SessionConstants.JOB_ACTIONS, jobActions);
    }

    /**
     * 
     * @param mapping
     * @return
     * @throws Exception
     */
    protected ActionForward findForward(ActionMapping mapping, String name) throws Exception
    {
        return mapping.findForwardConfig(name) == null ? null : mapping.findForward(name);
    }

    /**
     * 
     * @param mapping
     * @return
     * @throws Exception
     */
    protected ActionForward findForwardSuccess(ActionMapping mapping) throws Exception
    {
        return findForward(mapping, SUCCESS);
    }

    /**
     * 
     * @param mapping
     * @return
     * @throws Exception
     */
    protected ActionForward findForwardError(ActionMapping mapping) throws Exception
    {
        return mapping.findForwardConfig(ERROR) == null ? mapping.getInputForward() : mapping.findForward(ERROR);
    }

    /**
     * Close edit/view form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward close(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE close()..");
        return findForwardSuccess(mapping);
    }

    /**
     * Help view.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward help(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE help().. ");
        try
        {
            String module = mapping.getModuleConfig().getPrefix();
            String path = mapping.getPath();
            request.setAttribute(SessionConstants.HELP, getHelpService().getHelp(module, path));
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    protected ActionService getActionService() throws Exception
    {
        return WebUtils.getBean(getServlet().getServletContext(),
            IBeanNames.ACTION_SERVICE, ActionService.class);
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    protected CacheService getCacheService() throws Exception
    {
        return WebUtils.getBean(getServlet().getServletContext(),
            IBeanNames.CACHE_SERVICE, CacheService.class);
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    protected DocumentService getDocumentService() throws Exception
    {
        return WebUtils.getBean(getServlet().getServletContext(),
            IBeanNames.DOCUMENT_SERVICE, DocumentService.class);
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    protected EntityService getEntityService() throws Exception
    {
        return WebUtils.getBean(getServlet().getServletContext(),
            IBeanNames.ENTITY_SERVICE, EntityService.class);
    }

    /**
     * @return
     * @throws Exception
     */
    protected FcaService getFcaService() throws Exception
    {
        return WebUtils.getBean(getServlet().getServletContext(),
            IBeanNames.FCA_SERVICE, FcaService.class);
    }

    /**
     * @return
     * @throws Exception
     */
    protected FileService getFileService() throws Exception
    {
        return WebUtils.getBean(getServlet().getServletContext(),
            IBeanNames.FILE_SERVICE, FileService.class);
    }

    /**
     * @return
     * @throws Exception
     */
    protected JobService getJobService() throws Exception
    {
        return WebUtils.getBean(getServlet().getServletContext(),
            IBeanNames.JOB_SERVICE, JobService.class);
    }

    /**
     * @return
     * @throws Exception
     */
    protected HelpService getHelpService() throws Exception
    {
        return WebUtils.getBean(getServlet().getServletContext(),
            IBeanNames.HELP_SERVICE, HelpService.class);
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    protected SupplierService getSupplierService() throws Exception
    {
        return WebUtils.getBean(getServlet().getServletContext(),
            IBeanNames.SUPPLIER_SERVICE, SupplierService.class);
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    protected InvoiceService getInvoiceService() throws Exception
    {
        return WebUtils.getBean(getServlet().getServletContext(),
            IBeanNames.INVOICE_SERVICE, InvoiceService.class);
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    protected InjuryService getInjuryService() throws Exception
    {
        return WebUtils.getBean(getServlet().getServletContext(),
            IBeanNames.INJURY_SERVICE, InjuryService.class);
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    protected TaskService getTaskService() throws Exception
    {
        return WebUtils.getBean(getServlet().getServletContext(),
            IBeanNames.TASK_SERVICE, TaskService.class);
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    protected UserService getUserService() throws Exception
    {
        return WebUtils.getBean(getServlet().getServletContext(),
            IBeanNames.USER_SERVICE, UserService.class);
    }

    /**
     * Will mark current transaction for rollback.
     * @param session
     */
    protected void setRollbackOnly(HttpSession session)
    {
        TransactionStatus txnStatus = (TransactionStatus) session
            .getAttribute(TransactionStatus.class.getSimpleName());
        if (txnStatus != null)
        {
            txnStatus.setRollbackOnly();
        }
    }

    protected void saveExceptionJson(HttpServletResponse response, Throwable t) throws Exception
    {
        LOG.error(t.getMessage(), t);
        PrintWriter out = response.getWriter();
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        String error = ExceptionUtils.getRootCauseMessage(t);
        out.append('{').append("\"name\":\"error\",\"message\":\"" + StringUtils.trimToEmpty(error) + "\"").append('}');
    }

    /**
     *
     * @param request
     * @param response
     * @param messages
     */
    protected void saveErrors(HttpServletRequest request, HttpServletResponse response,
        ActionMessages messages) throws IOException
    {
        saveErrors(request, response, messages, HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     *
     * @param request
     * @param response
     * @param messages
     */
    protected void saveValidateErrors(HttpServletRequest request, HttpServletResponse response,
        ActionMessages messages) throws IOException
    {
        saveErrors(request, response, messages, HttpServletResponse.SC_NOT_ACCEPTABLE);
    }

    /**
     * 
     * @param request
     * @param response
     * @param messages
     * @param status
     * @throws IOException 
     */
    private void saveErrors(HttpServletRequest request, HttpServletResponse response,
        ActionMessages messages, int status) throws IOException
    {
        LOG.error(messages);

        setRollbackOnly(request.getSession(false));

        super.saveErrors(request, messages);

        //used to set response failure
        response.setStatus(status);
    }

    /**
     *
     * @param e
     */
    protected ActionErrors toActionErrors(Exception e)
    {
    	// log error
    	if (e instanceof ValidationException)
    	{
            LOG.error(e.getMessage());
    	}
    	else
    	{
            LOG.error(e.getMessage(), e);
    	}
    	//
        ActionErrors actionErrors = new ActionErrors();
        String exMsg = e.getMessage();
        Throwable cause = e.getCause();
        String[] errors = exMsg == null ? new String[] {null} : exMsg.split("\n");
        for (String error : errors)
        {
            ActionMessage actionError = new ActionMessage(error == null ? (cause != null ? cause : e).getClass().getName() : error, false);
            actionErrors.add(ActionMessages.GLOBAL_MESSAGE, actionError);
        }
        // get root (if any)
        String exMsg2 = ExceptionUtils.getRootCauseMessage(e);
        if (StringUtils.isNotBlank(exMsg2) && !exMsg2.contains(exMsg))
        {
            ActionMessage actionError = new ActionMessage(exMsg2, false);
            actionErrors.add(ActionMessages.GLOBAL_MESSAGE, actionError);
        }
        return actionErrors;
    }

    public ActionForward downloadDocument(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            Long documentId = WebUtils.getDocumentId(request);
            downloadDocument(getDocumentService().findDocumentById(documentId), response);
            return null;
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForwardError(mapping);
        }
    }

    protected void downloadDocument(Document document, HttpServletResponse response) throws Exception
    {
        ContentTypeEnum contentType = document.getContentTypeEnum();
        String contentName = document.getName() + "." + document.getId() + "."
            + (contentType == null ? document.getContentType() : contentType.getDefaultExt());
        WebUtils.downloadContent(response, document.getContent(), document.getContentType(), contentName);
    }

    protected String getContentName(String prefix, ContentTypeEnum contentType)
    {
    	return prefix + '.' + ThreadLocalUtils.getUser().getLogin()
    		+ '.' + DateUtils.YYYYMMDD_HHMMSS.format(DateUtils.getCurrentDateTime())
    		+ '.' + contentType.getDefaultExt();
    }

}