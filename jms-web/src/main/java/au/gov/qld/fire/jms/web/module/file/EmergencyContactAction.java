package au.gov.qld.fire.jms.web.module.file;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.entity.Company;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.refdata.CompanyTypeEnum;
import au.gov.qld.fire.domain.refdata.Salutation;
import au.gov.qld.fire.jms.domain.ConvertUtils;
import au.gov.qld.fire.jms.domain.building.BuildingContact;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.refdata.BuildingContactType;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class EmergencyContactAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        // set references
        request.setAttribute(SessionConstants.SALUTATIONS, getEntityService().findSalutations());

        FileForm myform = (FileForm) form;
        populatePriorities(request, "ownerBuildingContactsPriorities", myform.getOwnerBuildingContacts());
        populatePriorities(request, "securityBuildingContactsPriorities", myform.getSecurityBuildingContacts());
        populatePriorities(request, "fireBuildingContactsPriorities", myform.getFireBuildingContacts());
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
        FileForm myform = (FileForm) form;
        setEntity(myform, request);
        File entity = myform.getEntity();
        myform.getOwnerBuildingContacts().clear();
        myform.getSecurityBuildingContacts().clear();
        myform.getFireBuildingContacts().clear();
        // get parent (if copy requested)
    	Long copyId = WebUtils.getLongId(request, "copyId");
    	File copyFile = copyId != null ? getFileService().findFileById(copyId) : null;
    	File source = copyFile != null ? copyFile : entity;
    	myform.getOwnerBuildingContacts().addAll(source.getOwnerBuildingContacts());
        myform.getSecurityBuildingContacts().addAll(source.getSecurityBuildingContacts());
        myform.getFireBuildingContacts().addAll(source.getFireBuildingContacts());
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

    public ActionForward addBuildingContact(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE addBuildingContact()..");
        try
        {
            //set form data
            FileForm myform = (FileForm) form;
            //
            BuildingContact bc = new BuildingContact();
            if (Boolean.parseBoolean(request.getParameter("owner")))
            {
                bc.setBuildingContactType(BuildingContactType.OWNER);
                myform.getOwnerBuildingContacts().add(bc);
            }
            else if (Boolean.parseBoolean(request.getParameter("security")))
            {
                bc.setBuildingContactType(BuildingContactType.SECURITY);
                bc.setCompany(new Company(CompanyTypeEnum.SECURITY));
                myform.getSecurityBuildingContacts().add(bc);
            }
            else if (Boolean.parseBoolean(request.getParameter("fire")))
            {
                bc.setBuildingContactType(BuildingContactType.FIRE);
                bc.setCompany(new Company(CompanyTypeEnum.FIRE));
                myform.getFireBuildingContacts().add(bc);
            }
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

    public ActionForward removeBuildingContact(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE removeBuildingContact()..");
        try
        {
            // get index to remove
            int index = WebUtils.getIndex(request);
            // set form data
            FileForm myform = (FileForm) form;
            if (Boolean.parseBoolean(request.getParameter("owner")))
            {
                BuildingContact buildingContact = myform.getOwnerBuildingContacts().get(index);
                myform.getOwnerBuildingContacts().remove(buildingContact);
            }
            else if (Boolean.parseBoolean(request.getParameter("security")))
            {
                BuildingContact buildingContact = myform.getSecurityBuildingContacts().get(index);
                myform.getSecurityBuildingContacts().remove(buildingContact);
            }
            else if (Boolean.parseBoolean(request.getParameter("fire")))
            {
                BuildingContact buildingContact = myform.getFireBuildingContacts().get(index);
                myform.getFireBuildingContacts().remove(buildingContact);
            }
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

    /** copy from 'daytime' to 'afterhours' */
    public ActionForward copyBuildingContactDaytime(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE copyBuildingContactDaytime()..");
        try
        {
            //set form data
            FileForm myform = (FileForm) form;
            for (BuildingContact daytimeBuildingContact : myform.getDaytimeBuildingContacts())
            {
                BuildingContact buildingContact = new BuildingContact();
                buildingContact.setDocument(new Document());
                ConvertUtils.copyProperties(daytimeBuildingContact, buildingContact);
                buildingContact.setBuildingContactType(BuildingContactType.AFTER_HOURS);
                myform.getAfterHoursBuildingContacts().add(buildingContact);
            }
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
            // save Building changes (should exist by now)
            //getFileService().saveBuilding(file.getBuilding());
            // save BuildingContact(s) changes
            List<BuildingContact> buildingContacts = new ArrayList<BuildingContact>();
            buildingContacts.addAll(myform.getOwnerBuildingContacts());
            //buildingContacts.addAll(myform.getDaytimeBuildingContacts());
            //buildingContacts.addAll(myform.getAfterHoursBuildingContacts());
            buildingContacts.addAll(myform.getSecurityBuildingContacts());
            buildingContacts.addAll(myform.getFireBuildingContacts());
            //
            // save buildingContact(s) document
            List<Document> attachments = myform.getAttachments();
            int attachmentCount = attachments.size();
            int i = 0;
            for (BuildingContact bc : buildingContacts) {
            	String salutationTitle = bc.getContact().getSalutationTitle();
                bc.getContact().setSalutation(StringUtils.isBlank(salutationTitle) ? Salutation.UNKNOWN : new Salutation(salutationTitle));
            	//
            	if (!file.equals(bc.getFile())) {
                	// parent used, lets copy it to child
            		bc.setBuildingContactId(null);
            		bc.setFile(file);
            		// hibernate enhanced - need deep copy (could not just reset id)
            		Contact parentContact = bc.getContact();
                    ConvertUtils.copyProperties(parentContact, bc.getContact());
                    if (bc.getDocument() != null) {
                        bc.getDocument().setDocumentId(null);
                    }
            	}
            	//
                Document document = attachmentCount > i ? attachments.get(i) : null;
                if (document != null) {
                	if (bc.getDocument() == null || bc.getDocument().getId() == null) {
                    	bc.setDocument(document);
                	}
                	else {
                    	ConvertUtils.copyProperties(document, bc.getDocument());
                	}
                }
                i++;
            }
            getFileService().saveBuildingContacts(file.getFileId(), buildingContacts);

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