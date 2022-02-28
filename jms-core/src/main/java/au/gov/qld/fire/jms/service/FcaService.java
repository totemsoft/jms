package au.gov.qld.fire.jms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.fca.FcaSearchCriteria;
import au.gov.qld.fire.service.ServiceException;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface FcaService
{

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    Fca findFcaById(String id) throws ServiceException;

    /**
     * 
     * @param criteria
     * @return
     * @throws ServiceException
     */
    List<Fca> findFca(FcaSearchCriteria criteria) throws ServiceException;

    /**
     * 
     * @param fca
     * @throws ServiceException
     */
    void saveFca(Fca fca) throws ServiceException;

    /**
     * 
     * @param fca
     * @throws ServiceException
     */
    void refreshFca(Fca fca) throws ServiceException;

}