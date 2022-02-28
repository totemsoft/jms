package au.gov.qld.fire.jms.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import au.gov.qld.fire.dao.RegionDao;
import au.gov.qld.fire.dao.StateDao;
import au.gov.qld.fire.domain.Request;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.location.State;
import au.gov.qld.fire.jms.uaa.dao.InjuryDao;
import au.gov.qld.fire.jms.uaa.dao.InjuryTypeDao;
import au.gov.qld.fire.jms.uaa.domain.Injury;
import au.gov.qld.fire.service.Importer;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.util.BeanUtils;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.validation.ValidationException.ValidationMessage;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class UaaIncidentImporterImpl implements Importer<List<List<String>>>
{

    private static final Logger LOG = Logger.getLogger(UaaIncidentImporterImpl.class);

	@Inject private InjuryDao injuryDao;

	@Inject private InjuryTypeDao injuryTypeDao;

	@Inject private RegionDao regionDao;

	@Inject private StateDao stateDao;

    /** dd/MM/yyyy date format */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.Importer#importData(java.lang.String, java.util.List)
     */
    public Map<? extends Request, List<ValidationMessage>> importData(String name, List<List<String>> tableData) throws ServiceException
    {
        try
        {
        	List<State> defaultStates = stateDao.findDefault();
        	State defaultState = defaultStates.isEmpty() ? null : defaultStates.get(0);
            //Map<Request, List<ValidationMessage>> errors = new LinkedHashMap<Request, List<ValidationMessage>>();
            List<Injury> injuries = new ArrayList<Injury>();
            //#Fire Call No,Region,Year,Incident_date,Inj_no,Surname,Given Names,Gender,DOB,Age,Address No,Street,Town,Postcode,Type of Injuries,Multiple Injury Types,Injuries to,Multiple Injuries to,Alcohol Level,Drugs,Treated at
            //15,SE,2002,NULL,1,ARNOLD,LEE,1,31/07/1983,20,10,10  21st  AVENUE,PALM BEACH,4221,Burn 2nd degree,NULL,Forearm,"Arms, hands & feet.",0,No,TWEED HEADS / BRISBANE BURNS UNIT
            for (List<String> rowData : tableData)
            {
                // process data rows
                int i = 0;
                String fireCallNo = rowData.get(i++);
                String region = rowData.get(i++);
                String incidentYear = rowData.get(i++);
                String incidentDate = rowData.get(i++);
                Integer injuryNo = NumberUtils.createInteger(rowData.get(i++));
                Injury injury = injuryDao.findByUniqueKey(fireCallNo, injuryNo);
                if (injury == null)
                {
                	// check in current import data
                	for (Injury item : injuries)
                	{
                		if (BeanUtils.compare(item.getFireCallNo(), fireCallNo) == 0
                		    && BeanUtils.compare(item.getInjuryNo(), injuryNo) == 0)
                		{
                			injury = item;
                			break;
                		}
                	}
                	if (injury == null)
                	{
                        injury = new Injury();
                        injury.setFireCallNo(fireCallNo);
                        injury.setInjuryNo(injuryNo);
                	}
                }
                injury.setRegion(regionDao.findByCode(region));
                injury.setIncidentYear(NumberUtils.createInteger(incidentYear));
                injury.setIncidentDate(DateUtils.parse(incidentDate, DATE_FORMAT, null));
                //
                Contact contact = new Contact();
                contact.setSurname(rowData.get(i++));
                contact.setFirstName(rowData.get(i++));
                /*String gender = */rowData.get(i++);
                contact.setDateOfBirth(DateUtils.parse(rowData.get(i++), DATE_FORMAT, null));
                /*String age = */rowData.get(i++);
                injury.setContact(contact);
                Address address = new Address();
                /*String streetNo = */rowData.get(i++);
                address.setAddrLine1(rowData.get(i++));
                address.setSuburb(rowData.get(i++));
                address.setPostcode(rowData.get(i++));
                address.setState(defaultState);
                injury.setAddress(address);
                injury.setInjuryType(injuryTypeDao.findByName(rowData.get(i++)));
                injury.setMultipleInjuryType(injuryTypeDao.findByName(rowData.get(i++)));
                injury.setInjuryTo(rowData.get(i++));
                injury.setMultipleInjuryTo(rowData.get(i++));
                injury.setAlcoholLevel(rowData.get(i++));
                injury.setDrugs(rowData.get(i++));
                injury.setTreatedAt(rowData.get(i++));
                injuries.add(injury);
            }
            // save
            for (Injury injury : injuries)
            {
            	injuryDao.save(injury);
            }
            LOG.debug(injuries.size() + " injuries imported.");
            return null;
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }

}