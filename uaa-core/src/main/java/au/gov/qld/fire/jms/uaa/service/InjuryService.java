package au.gov.qld.fire.jms.uaa.service;

import java.io.OutputStream;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.jms.uaa.domain.IncidentSearchCriteria;
import au.gov.qld.fire.jms.uaa.domain.Injury;
import au.gov.qld.fire.service.ServiceException;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface InjuryService
{

	/**
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	Injury findInjuryById(Long id)
	    throws ServiceException;

    /**
     * 
     * @param criteria
     * @return
     * @throws ServiceException
     */
    List<Injury> findInjuryByCriteria(IncidentSearchCriteria criteria)
        throws ServiceException;

    /**
     * 
     * @param entities
     * @param contentStream
     * @param contentType
     * @throws ServiceException
     */
	void exportInjury(List<Injury> entities,
	    OutputStream contentStream, ContentTypeEnum contentType)
	    throws ServiceException;

}