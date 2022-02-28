package au.gov.qld.fire.jms.uaa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.jms.uaa.domain.refdata.InjuryType;
import au.gov.qld.fire.service.ServiceException;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface EntityService extends au.gov.qld.fire.service.EntityService
{

    /**
     * Update database with sql scripts from db/update folder.
     * (no transaction, will use synchroniseDatabase(URL source) from super)
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    void synchroniseDatabase() throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    InjuryType findInjuryTypeById(Long id) throws ServiceException;

    /**
     * 
     * @param name
     * @return
     * @throws ServiceException
     */
    InjuryType findInjuryTypeByName(String name) throws ServiceException;

    /**
     * 
     * @param multiple
     * @return
     * @throws ServiceException
     */
    List<InjuryType> findInjuryTypes(Boolean multiple) throws ServiceException;

}