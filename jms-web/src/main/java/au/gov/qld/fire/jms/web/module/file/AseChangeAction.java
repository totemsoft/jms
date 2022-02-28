package au.gov.qld.fire.jms.web.module.file;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.refdata.SupplierTypeEnum;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.jms.domain.ase.AseChange;
import au.gov.qld.fire.jms.domain.ase.AseChangeSearchCriteria;
import au.gov.qld.fire.jms.domain.ase.AseChangeSupplier;
import au.gov.qld.fire.jms.domain.ase.AseFile;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.util.ThreadLocalUtils;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseChangeAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        //set references
        request.setAttribute(SessionConstants.ASE_CONN_TYPES, getEntityService().findAseConnTypesActive());
        request.setAttribute(SessionConstants.ASE_INSTALLATION_SUPPLIERS, getSupplierService()
            .findSuppliers(SupplierTypeEnum.ASE_INSTALLATION));
        request.setAttribute(SessionConstants.TELCO_SUPPLIERS, getSupplierService().findSuppliers(
            SupplierTypeEnum.TELCO));
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

        //aseChangeSuppliers
        List<AseChangeSupplier> aseChangeSuppliers = entity.getAseFile().getAseChange()
            .getAseChangeSuppliers();
        myform.getAseInstallationAseChangeSuppliers().clear();
        myform.getTelcoAseChangeSuppliers().clear();
        for (AseChangeSupplier aseChangeSupplier : aseChangeSuppliers)
        {
            Supplier supplier = aseChangeSupplier.getSupplier();
            if (supplier.isAseInstallation())
            {
                myform.getAseInstallationAseChangeSuppliers().add(aseChangeSupplier);
            }
            else
            //if (supplier.isTelco())
            {
                myform.getTelcoAseChangeSuppliers().add(aseChangeSupplier);
            }
        }
    }

    /**
     * Search by FCA, FileNo or Building Name (%)
     * Exclude completed ASE Changes
     * Show only ASE Installation or Carrier Installations or Both that are incomplete
     * Show Only FCA/Files/Buildings that no ASE change job times have been created
     * Limit Search by Supplier Name
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
        LOG.debug("INSIDE find().. ");
        try
        {
            //Search by FCA, FileNo or Building Name (%)
            String fileId = request.getParameter(SessionConstants.FILE_ID);
            String fcaId = request.getParameter(SessionConstants.FCA_ID);
            String buildingId = request.getParameter(SessionConstants.BUILDING_ID);
            String buildingName = request.getParameter(SessionConstants.BUILDING_NAME);
            //Limit Search by Supplier Name
            User user = getUserService().findUserById(ThreadLocalUtils.getUser().getId());
            Supplier supplier = user.getSupplier();
            String supplierName = supplier == null ? null : supplier.getName();

            AseChangeSearchCriteria criteria = new AseChangeSearchCriteria();
            criteria.setFileNo(fileId);
            criteria.setFcaNo(fcaId);
            criteria.setBuildingId(NumberUtils.toLong(buildingId, 0L));
            criteria.setBuildingName(buildingName);
            criteria.setSupplierName(supplierName);
            request.setAttribute(SessionConstants.ENTITIES, getFileService().findAseChangeFiles(criteria));

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
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addAseChangeSupplier(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE addAseChangeSupplier()..");
        try
        {
            //set form data
            FileForm myform = (FileForm) form;
            AseFile aseFile = myform.getEntity().getAseFile();
            AseChange aseChange = aseFile.getAseChange();
            //
            AseChangeSupplier aseChangeSupplier = new AseChangeSupplier();
            //set parent
            aseChangeSupplier.setAseChange(aseChange);
            Supplier supplier = aseChangeSupplier.getSupplier();
            if (Boolean.parseBoolean(request.getParameter("aseInstallation")))
            {
                supplier.getSupplierType().setSupplierTypeId(
                    SupplierTypeEnum.ASE_INSTALLATION.getId());
                myform.getAseInstallationAseChangeSuppliers().add(aseChangeSupplier);
            }
            else
            //if (Boolean.parseBoolean(request.getParameter("telco")))
            {
                supplier.getSupplierType().setSupplierTypeId(SupplierTypeEnum.TELCO.getId());
                myform.getTelcoAseChangeSuppliers().add(aseChangeSupplier);
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
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward removeAseChangeSupplier(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE removeAseChangeSupplier()..");
        try
        {
            //get index to remove
            int index = WebUtils.getIndex(request);
            //set form data
            FileForm myform = (FileForm) form;
            //get aseInstallation/telco aseChangeSuppliers
            List<AseChangeSupplier> aseChangeSuppliers = null;
            if (Boolean.parseBoolean(request.getParameter("aseInstallation")))
            {
                aseChangeSuppliers = myform.getAseInstallationAseChangeSuppliers();
            }
            else
            //if (Boolean.parseBoolean(request.getParameter("telco")))
            {
                aseChangeSuppliers = myform.getTelcoAseChangeSuppliers();
            }
            AseChangeSupplier aseChangeSupplier = aseChangeSuppliers.get(index);
            if (aseChangeSupplier.getId() == null)
            {
                //was never saved
                aseChangeSuppliers.remove(aseChangeSupplier);
            }
            else
            {
                aseChangeSupplier.setLogicallyDeleted(Boolean.TRUE);
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
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward completeAseChangeSupplier(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE completeAseChangeSupplier()..");
        try
        {
            Long aseChangeSupplierId = WebUtils.getLongId(request);
            if (aseChangeSupplierId != null)
            {
                //set form data
                FileForm myform = (FileForm) form;
                //
                List<AseChangeSupplier> aseChangeSuppliers;
                if (Boolean.parseBoolean(request.getParameter("aseInstallation")))
                {
                    aseChangeSuppliers = myform.getAseInstallationAseChangeSuppliers();
                }
                else
                //if (Boolean.parseBoolean(request.getParameter("telco")))
                {
                    aseChangeSuppliers = myform.getTelcoAseChangeSuppliers();
                }
                for (AseChangeSupplier aseChangeSupplier : aseChangeSuppliers)
                {
                    if (aseChangeSupplierId.equals(aseChangeSupplier.getId()))
                    {
                        aseChangeSupplier.setDateCompleted(DateUtils.getCurrentDateTime());
                        break;
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
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE view()..");
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
            AseFile aseFile = myform.getEntity().getAseFile();
            AseChange entity = aseFile.getAseChange();
            //update date change
            String dateString = myform.getAseDateChange();
            String timeString = myform.getAseTimeChange();
            entity.setDateChange(DateUtils.parse(dateString, timeString, DateUtils.D_M_YYYY_H_mm));
            //remove AseInstallation and Telco
            List<AseChangeSupplier> aseChangeSuppliers = new ArrayList<AseChangeSupplier>();
            CollectionUtils.select(entity.getAseChangeSuppliers(), new Predicate()
            {
                /* (non-Javadoc)
                 * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
                 */
                public boolean evaluate(Object obj)
                {
                    AseChangeSupplier aseChangeSupplier = (AseChangeSupplier) obj;
                    Supplier supplier = aseChangeSupplier.getSupplier();
                    return !supplier.isAseInstallation() && !supplier.isTelco();
                }
            }, aseChangeSuppliers);
            //add modified items
            aseChangeSuppliers.addAll(myform.getAseInstallationAseChangeSuppliers());
            aseChangeSuppliers.addAll(myform.getTelcoAseChangeSuppliers());
            entity.setAseChangeSuppliers(aseChangeSuppliers);
            //save changes (if any)
            getFileService().saveAseChange(entity);

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