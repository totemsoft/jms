package au.gov.qld.fire.jms.uaa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.jms.uaa.domain.IncidentSearchCriteria;
import au.gov.qld.fire.jms.uaa.domain.firu.InjuryView;
import au.gov.qld.fire.jms.uaa.domain.firu.InjuryViewPK;
import au.gov.qld.fire.service.ServiceException;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface FiruImportService
{

	/**
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
    InjuryView findInjuryViewById(InjuryViewPK id) throws ServiceException;

    /**
     * 
     * @param criteria
     * @return
     * @throws ServiceException
     */
    List<InjuryView> findInjuryViewByCriteria(IncidentSearchCriteria criteria) throws ServiceException;

    /**
     * 
     * @param ids
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	void importInjury(List<InjuryViewPK> ids) throws ServiceException;

}