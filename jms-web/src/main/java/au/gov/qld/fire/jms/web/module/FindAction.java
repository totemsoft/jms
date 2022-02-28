package au.gov.qld.fire.jms.web.module;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.BasePair;
import au.gov.qld.fire.domain.StringPair;
import au.gov.qld.fire.domain.security.ISystemFunction;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.domain.security.UserSearchCriteria;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.domain.supplier.SupplierSearchCriteria;
import au.gov.qld.fire.web.SessionConstants;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FindAction extends AbstractDispatchAction
{

    public ActionForward findActionCode(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE findActionCode().. ");
        try
        {
            String actionCode = request.getParameter(SessionConstants.QUERY);
            List<String> entityCodes = getEntityService().findActionCode(actionCode);
            request.setAttribute("entityCodes", entityCodes);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward findArchive(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE findArchive().. ");
        try
        {
            String archiveCode = request.getParameter(SessionConstants.QUERY);
            List<StringPair> entities = getFileService().findArchives(archiveCode);
            request.setAttribute("entities", entities);
            return mapping.findForward("labelValue");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward findAseKeyNo(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            String aseKeyNo = request.getParameter(SessionConstants.QUERY);
            List<String> entityCodes = getFileService().findAseKeyNo(aseKeyNo);
            request.setAttribute("entityCodes", entityCodes);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward findAseKeyOrderNo(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            String orderNo = request.getParameter(SessionConstants.QUERY);
            List<String> entityCodes = getFileService().findAseKeyOrderNo(orderNo);
            request.setAttribute("entityCodes", entityCodes);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward findFileNo(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE findFileNo().. ");
        try
        {
            String fileId = request.getParameter(SessionConstants.QUERY);
            List<BasePair> entities = getFileService().findFileNo(fileId);
            request.setAttribute("entities", entities);
            return mapping.findForward("labelValue");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * File search criteria.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward findFcaNo(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE findFcaNo().. ");
        try
        {
            String fcaId = request.getParameter(SessionConstants.QUERY);
            boolean unassignedFca = Boolean.parseBoolean(request.getParameter(SessionConstants.UNASSIGNED_FCA));
            List<BasePair> entities = getFileService().findFca(fcaId, unassignedFca);
            request.setAttribute("entities", entities);
            return mapping.findForward("labelValue");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Job search criteria.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward findInvoiceArea(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            String name = request.getParameter(SessionConstants.QUERY);
            List<BasePair> entities = getInvoiceService().findInvoiceArea(name);
            request.setAttribute("entities", entities);
            return mapping.findForward("labelValue");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Job search criteria.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward findJobNo(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE findJobNo().. ");
        try
        {
            String jobId = request.getParameter(SessionConstants.QUERY);
            List<String> entityCodes = getJobService().findJobNo(jobId);
            request.setAttribute("entityCodes", entityCodes);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Job search criteria.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward findJobRequestNo(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE findJobRequestNo().. ");
        try
        {
            String jobRequestId = request.getParameter(SessionConstants.QUERY);
            List<String> entityCodes = getJobService().findJobRequestNo(jobRequestId);
            request.setAttribute("entityCodes", entityCodes);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * File search criteria.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward findBuildingName(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE findBuildingName().. ");
        try
        {
            String buildingName = request.getParameter(SessionConstants.QUERY);
            //List<String> entityCodes = getFileService().findBuildingName(buildingName);
            //request.setAttribute("entityCodes", entityCodes);
            //return mapping.getInputForward();
            List<BasePair> entities = getFileService().findBuildings(buildingName);
            request.setAttribute("entities", entities);
            return mapping.findForward("labelValue");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * File search criteria.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward findBuildingAddress(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE findBuildingAddress().. ");
        try
        {
            String buildingAddress = request.getParameter(SessionConstants.QUERY);
            List<String> entityCodes = getFileService().findBuildingAddress(buildingAddress);
            request.setAttribute("entityCodes", entityCodes);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * File search criteria.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward findOwnerName(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE findOwnerName().. ");
        try
        {
            String ownerName = request.getParameter(SessionConstants.QUERY);
            List<String> entityCodes = getFileService().findOwnerName(ownerName);
            request.setAttribute("entityCodes", entityCodes);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * File search criteria.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward findOwnerContact(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE findOwnerContact().. ");
        try
        {
            String ownerContact = request.getParameter(SessionConstants.QUERY);
            List<String> entityCodes = getFileService().findOwnerContact(ownerContact);
            request.setAttribute("entityCodes", entityCodes);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Fca search criteria.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward findRegion(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE findRegion().. ");
        try
        {
            String regionLike = request.getParameter(SessionConstants.QUERY);
            List<String> entityCodes = getEntityService().findRegion(regionLike);
            request.setAttribute("entityCodes", entityCodes);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Fca search criteria.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward findArea(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE findArea().. ");
        try
        {
            String region = request.getParameter(SessionConstants.REGION);
            //Long regionId = WebUtils.getLongId(request, SessionConstants.REGION_ID);
            String areaLike = request.getParameter(SessionConstants.QUERY); //AREA
            List<String> entityCodes = getEntityService().findAreaByRegion(areaLike, region);
            request.setAttribute("entityCodes", entityCodes);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Fca search criteria.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward findStation(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE findStation().. ");
        try
        {
            String region = request.getParameter(SessionConstants.REGION);
            //Long regionId = WebUtils.getLongId(request, SessionConstants.REGION_ID);
            String area = request.getParameter(SessionConstants.AREA);
            //Long areaId = WebUtils.getLongId(request, SessionConstants.AREA_ID);
            String stationLike = request.getParameter(SessionConstants.QUERY); //STATION
            List<String> entityCodes = getEntityService().findStationByRegionArea(stationLike, region, area);
            request.setAttribute("entityCodes", entityCodes);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward findSapCustNo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE findSapCustNo().. ");
        try
        {
            String sapCustNo = request.getParameter(SessionConstants.QUERY);
            List<String> entityCodes = getFileService().findSapCustNo(sapCustNo);
            request.setAttribute("entityCodes", entityCodes);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Supplier search criteria.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward findSupplierName(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE findSupplierName().. ");
        try
        {
            String supplierName = request.getParameter(SessionConstants.QUERY);
            String ase = request.getParameter("ase");
            SupplierSearchCriteria criteria = new SupplierSearchCriteria();
            criteria.setSupplierName(supplierName);
            criteria.setAse("true".equals(ase));
            List<BasePair> entities = new ArrayList<BasePair>();
            boolean addNew = hasSystemFunction(ISystemFunction.MODULE_SUPPLIER, ISystemFunction.PATH_SUPPLIER_EDIT, null);
            if (addNew) {
            	entities.add(BasePair.NEW);
            }
            for (Supplier entity : getSupplierService().findSuppliers(criteria)) {
            	entities.add(new BasePair(entity.getId(), entity.getName()));
            }
            request.setAttribute("entities", entities);
            return mapping.findForward("labelValue");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Supplier search criteria.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward findSupplierContact(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE findSupplierContact().. ");
        try
        {
            String supplierContact = request.getParameter(SessionConstants.QUERY);
            List<String> entityCodes = getSupplierService().findSupplierContact(supplierContact);
            request.setAttribute("entityCodes", entityCodes);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Supplier search criteria.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward findSupplierPhone(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE findSupplierPhone().. ");
        try
        {
            String supplierPhone = request.getParameter(SessionConstants.QUERY);
            List<String> entityCodes = getSupplierService().findSupplierPhone(supplierPhone);
            request.setAttribute("entityCodes", entityCodes);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward userName(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            String userNameLike = request.getParameter(SessionConstants.QUERY);
            UserSearchCriteria criteria = new UserSearchCriteria();
            criteria.setLogin(userNameLike);
            List<BasePair> entities = new ArrayList<BasePair>();
            for (User entity : getUserService().findUsers(criteria)) {
            	entities.add(new BasePair(entity.getId(), entity.getContact().getName()));
            }
            request.setAttribute("entities", entities);
            return mapping.findForward("labelValue");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Supplier search criteria.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward findWorkGroup(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE findWorkGroup().. ");
        try
        {
            String workGroup = request.getParameter(SessionConstants.QUERY);
            List<String> entityCodes = getEntityService().findWorkGroup(workGroup);
            request.setAttribute("entityCodes", entityCodes);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }
}