package au.gov.qld.fire.jms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import au.gov.qld.fire.dao.StateDao;
import au.gov.qld.fire.domain.Request;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.location.State;
import au.gov.qld.fire.jms.dao.BuildingDao;
import au.gov.qld.fire.jms.dao.FcaDao;
import au.gov.qld.fire.jms.dao.FileDao;
import au.gov.qld.fire.jms.dao.IsolationHistoryDao;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.building.Building;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.isolation.IsolationHistory;
import au.gov.qld.fire.jms.domain.isolation.IsolationHistoryRequest;
import au.gov.qld.fire.jms.domain.isolation.IsolationHistorySearchCriteria;
import au.gov.qld.fire.jms.validation.IsolationHistoryRequestValidator;
import au.gov.qld.fire.service.Importer;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.validation.ValidationException.ValidationMessage;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class IsolationImporterImpl implements Importer<List<List<String>>>
{

    //private static final Logger LOG = Logger.getLogger(IsolationImporterImpl.class);

	@Inject private IsolationHistoryRequestValidator isolationHistoryRequestValidator;

	@Inject private IsolationHistoryDao isolationHistoryDao;

	@Inject private FcaDao fcaDao;

	@Inject private FileDao fileDao;

	@Inject private BuildingDao buildingDao;

	@Inject private StateDao stateDao;

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.Importer#importData(java.lang.String, java.util.List)
	 */
	public Map<? extends Request, List<ValidationMessage>> importData(String name, List<List<String>> tableData) throws ServiceException
	{
		// CS Number,ASE/FCA,Site Name,Address,City,Postcode,Isolation Time,De-Isolation Time,Time Isolated (HH:MM:SS),Input,Details,Key Details (if available)
		// 01071228,10005-01,Qld Nickel Unloading Facility,Benwell RD,SOUTH TOWNSVILLE,4810,17/08/2012 01:37:54 PM,17/08/2012 01:53:14 PM,00:15:20,1,10005-01 Front of Bldg,Key# 66000003F4A16A14 - Shannon O'Connor Chubb Townsville
        try
        {
        	Map<IsolationHistoryRequest, List<ValidationMessage>> errors = new LinkedHashMap<IsolationHistoryRequest, List<ValidationMessage>>();
        	List<IsolationHistoryRequest> requests = new ArrayList<IsolationHistoryRequest>();
    		boolean isData = false;
    		for (List<String> rowData : tableData)
    		{
    			if (isData)
    			{
        			// process data rows
    				int i = 0, size = rowData.size();
    				IsolationHistoryRequest request = new IsolationHistoryRequest();
    				if (size > i) request.setCsNumber(rowData.get(i++));
    				if (size > i) request.setFcaId(rowData.get(i++));
    				if (size > i) request.setSiteName(rowData.get(i++));
    				if (size > i) request.setAddress(rowData.get(i++));
    				if (size > i) request.setSuburb(rowData.get(i++));
    				if (size > i) request.setPostcode(rowData.get(i++));
    				if (size > i) request.setIsolationDate(rowData.get(i++));
    				if (size > i) request.setDeIsolationDate(rowData.get(i++));
    				if (size > i) request.setTimeIsolated(rowData.get(i++));
    				if (size > i) request.setInput(rowData.get(i++));
    				if (size > i) request.setDetails(rowData.get(i++));
    				if (size > i) request.setKeyDetails(rowData.get(i++));
    				List<ValidationMessage> vm = isolationHistoryRequestValidator.validate(request);
    				if (CollectionUtils.isNotEmpty(vm))
    				{
       					errors.put(request, vm);
    				}
    				requests.add(request);
    			}
    			else
    			{
        			// skip first 6 header rows
        			isData = rowData.contains("CS Number") && rowData.contains("ASE/FCA");
    			}
    		}
    		// return requests for validation
    		if (!errors.isEmpty())
    		{
    			return errors;
    		}
    		
    		// save
    		List<State> states = stateDao.findDefault();
    		if (states.isEmpty())
    		{
                //LOG.warn("Failed to find default State");
    			throw new ServiceException("Failed to find default State");
    		}
    		State defaultState = states.get(0);
    		for (IsolationHistoryRequest request : requests)
    		{
    			// process FCA data (create - if not found)
    			String fcaId = request.getFcaId();
    		    Integer input = Integer.valueOf(request.getInput());
    			Date isolationDate = DateUtils.DD_MM_YYYY_hh_mm_ss_aaa.parse(request.getIsolationDate());
    			Date deIsolationDate;
    			if (StringUtils.isBlank(request.getDeIsolationDate()))
    			{
    				String[] t = request.getTimeIsolated().split(":"); // 35:57:53
    				int seconds = (Integer.valueOf(t[0]) * 60 + Integer.valueOf(t[1])) * 60 + Integer.valueOf(t[2]);
        			deIsolationDate = DateUtils.addSeconds(isolationDate, seconds);
    			}
    			else
    			{
        			deIsolationDate = DateUtils.DD_MM_YYYY_hh_mm_ss_aaa.parse(request.getDeIsolationDate());
    			}
    			Fca fca = fcaDao.findById(fcaId);
    			if (fca == null)
    			{
    				fca = new Fca(fcaId);
    				fcaDao.save(fca);
    			}
    			File file = fca.getFile();
    			if (file == null)
    			{
    				// TODO: find existing file (without FCA) by building details
    				file = new File();
    				file.setFca(fca);
    				fileDao.save(file);
        			// save building details
    				Building building = new Building();
    				building.setName(request.getSiteName());
    				Address address = new Address();
    				address.setAddrLine1(request.getAddress());
    				address.setSuburb(request.getSuburb());
    				address.setPostcode(request.getPostcode());
    				address.setState(defaultState);
    				building.setAddress(address);
    				building.setFile(file);
    	            buildingDao.saveOrUpdate(building);
    			}
    			// find existing (if loaded before)
    			IsolationHistorySearchCriteria criteria = new IsolationHistorySearchCriteria();
    			criteria.setFcaId(fcaId);
    			criteria.setInput(input);
    			criteria.setIsolationDate(isolationDate);
    			criteria.setDeIsolationDate(deIsolationDate);
    			IsolationHistory entity = isolationHistoryDao.findUniqueByCriteria(criteria);
    			// save
    			boolean newEntity = entity == null;
    			entity = newEntity ? new IsolationHistory(fca) : entity;
    			entity.setCsNumber(request.getCsNumber());
    			entity.setInput(input);
    			entity.setIsolationDate(isolationDate);
    			entity.setDeIsolationDate(deIsolationDate);
    			entity.setDetails(request.getDetails());
    			entity.setKeyDetails(request.getKeyDetails());
    			isolationHistoryDao.saveOrUpdate(entity);
    		}
			return null;
        }
        catch (Exception e)
        {
        	throw new ServiceException(e);
        }
	}

}