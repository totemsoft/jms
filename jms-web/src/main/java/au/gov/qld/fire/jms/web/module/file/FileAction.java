package au.gov.qld.fire.jms.web.module.file;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.location.Region;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.document.DocChkList;
import au.gov.qld.fire.jms.domain.file.ActiveFile;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.file.FileDocChkList;
import au.gov.qld.fire.jms.domain.file.FileSearchCriteria;
import au.gov.qld.fire.jms.domain.mail.MailMethodEnum;
import au.gov.qld.fire.jms.domain.refdata.OwnerTypeEnum;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FileAction extends AbstractDispatchAction
{

	private static final MailMethodEnum[] MAIL_METHODS = {MailMethodEnum.EMAIL, MailMethodEnum.POST};
	private static final OwnerTypeEnum[] OWNER_TYPES = OwnerTypeEnum.values();

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void populateRequest(ActionForm form, HttpServletRequest request)
	    throws Exception
	{
        request.setAttribute(SessionConstants.MAIL_METHODS, MAIL_METHODS);
        request.setAttribute(SessionConstants.OWNER_TYPES, OWNER_TYPES);
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
        // form is session bound (id can be null, eg just created)
        FileForm myform = (FileForm) form;
        setEntity(myform, request);
        File entity = myform.getEntity();

        // The link between the File and Stakeholder is Region.
        // When a user is looking at a File it is linked to a region, the stakeholders for each are to be displayed.
        Fca fca = entity.getFca();
        Region region = fca == null ? null : fca.getRegion();
        request.setAttribute(SessionConstants.STAKEHOLDERS, getEntityService().findStakeHoldersByRegion(region));
        request.setAttribute(SessionConstants.FCA_DOCS, getDocumentService().findFcaDocuments(fca));

        // FIXME: refresh lazy collections,
        // will be refreshed in jsp ${not empty ..},
        // but sometimes failing - LazyInitializationException
        //entity.getRfis().size();
        //entity.getFileDocs().size();
        //entity.getFileDocChkLists().size();

        // populate FileDocChkList(s) if empty
        List<FileDocChkList> fileDocChkLists = entity.getFileDocChkLists();
        if (fileDocChkLists.isEmpty()) {
            List<DocChkList> docChkLists = getEntityService().findDocChkListsActive();
            for (DocChkList docChkList : docChkLists) {
                FileDocChkList fileDocChkList = new FileDocChkList();
                fileDocChkList.setDocChkList(docChkList);
                entity.add(fileDocChkList);
            }
        }

        // CRM Actions To Do
        // CRM Completed Actions
        populateFileActions(entity, request);

        // Job Actions To Do
        // Job Completed Actions
        populateJobActions(entity.getJobs(), request);
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
            String fileId = request.getParameter(SessionConstants.FILE_ID);
            String fcaId = request.getParameter(SessionConstants.FCA_ID);
            String buildingId = request.getParameter(SessionConstants.BUILDING_ID);
            String buildingName = request.getParameter(SessionConstants.BUILDING_NAME);
            String buildingAddress = request.getParameter(SessionConstants.BUILDING_ADDRESS);
            String ownerName = request.getParameter(SessionConstants.OWNER_NAME);
            String ownerContact = request.getParameter(SessionConstants.OWNER_CONTACT);
            String supplierName = request.getParameter(SessionConstants.SUPPLIER_NAME);
            String disconnectedFile = request.getParameter(SessionConstants.DISCONNECTED_FILE);

            FileSearchCriteria criteria = new FileSearchCriteria();
            //criteria.setFileTypeId(new Long(FileTypeEnum.MAIL_REGISTER.ordinal()));
            criteria.setFileNo(fileId);
            criteria.setFcaNo(fcaId);
            criteria.setBuildingId(NumberUtils.toLong(buildingId, 0L));
            criteria.setBuildingName(buildingName);
            criteria.setBuildingAddress(buildingAddress);
            criteria.setOwnerName(ownerName);
            criteria.setOwnerContact(ownerContact);
            criteria.setSupplierName(supplierName);
            criteria.setDisconnectedFile(Boolean.parseBoolean(disconnectedFile));
            List<ActiveFile> entities = getFileService().findFiles(criteria);

            request.setAttribute(SessionConstants.ENTITIES, entities);

            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Present new entity form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
            File entity = new File();
            //save new file (empty)
            getFileService().saveFile(entity);
            //
            FileForm myform = (FileForm) form;
            myform.setEntity(entity);
            //
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
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
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
            populateForm(mapping, form, request);
            populateRequest(form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward viewFcaDocs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
            // form is session bound (id can be null, eg just created)
        	FileForm myform = (FileForm) form;
            File entity = myform.getEntity();
            Fca fca = entity.getFca();
            request.setAttribute(SessionConstants.FCA_DOCS, getDocumentService().findFcaDocuments(fca));
            return findForward(mapping, "viewFcaDocs");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward uploadFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
            // form is session bound (id can be null, eg just created)
        	FileForm myform = (FileForm) form;
            File entity = myform.getEntity();
        	request.setAttribute(SessionConstants.FILE_ID, entity.getFileId());
        	request.setAttribute("dir", request.getParameter("dir"));
            return findForward(mapping, "uploadFile");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

}