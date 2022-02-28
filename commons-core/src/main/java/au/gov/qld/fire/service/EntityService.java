package au.gov.qld.fire.service;

import java.net.URL;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.document.TemplateEnum;
import au.gov.qld.fire.domain.location.Area;
import au.gov.qld.fire.domain.location.Region;
import au.gov.qld.fire.domain.location.State;
import au.gov.qld.fire.domain.location.Station;
import au.gov.qld.fire.domain.refdata.DocType;
import au.gov.qld.fire.domain.refdata.Salutation;
import au.gov.qld.fire.domain.refdata.SupplierType;
import au.gov.qld.fire.domain.refdata.TemplateSearchCriteria;
import au.gov.qld.fire.domain.refdata.TemplateType;
import au.gov.qld.fire.domain.refdata.TemplateTypeEnum;
import au.gov.qld.fire.domain.refdata.UserType;
import au.gov.qld.fire.domain.refdata.WorkGroup;
import au.gov.qld.fire.domain.security.SecurityGroup;
import au.gov.qld.fire.domain.security.SystemFunction;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface EntityService
{

    /**
     * Update database - internal method.
     * @param source - sql script
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    void synchroniseDatabase(URL source) throws Exception;

    /**
     * 
     * @param areaLike
     * @param region
     * @return
     * @throws ServiceException
     */
    List<String> findAreaByRegion(String areaLike, String region) throws ServiceException;

    /**
     * Find all Area.
     * @return
     * @throws ServiceException
     */
    List<Area> findAreas() throws ServiceException;

    /**
     * Find all active Area.
     * @return
     * @throws ServiceException
     */
    List<Area> findAreasActive() throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    Area findAreaById(String id) throws ServiceException;

    /**
     * 
     * @param area
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveArea(Area area) throws ServiceException;

    /**
     * Find all active DocType.
     * @return
     * @throws ServiceException
     */
    List<DocType> findDocTypes() throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    DocType findDocTypeById(Long id) throws ServiceException;

    /**
     * 
     * @param docType
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveDocType(DocType docType) throws ServiceException;

    /**
     * 
     * @param regionLike
     * @return
     * @throws ServiceException
     */
    List<String> findRegion(String regionLike) throws ServiceException;

    /**
     * Find all Region.
     * @return
     * @throws ServiceException
     */
    List<Region> findRegions() throws ServiceException;

    /**
     * Find all active Region.
     * @return
     * @throws ServiceException
     */
    List<Region> findRegionsActive() throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    Region findRegionById(Long id) throws ServiceException;

    /**
     * 
     * @param region
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveRegion(Region region) throws ServiceException;

    /**
     * Find all active Salutation.
     * @return
     * @throws ServiceException
     */
    List<Salutation> findSalutations() throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    Salutation findSalutationById(String id) throws ServiceException;

    /**
     * 
     * @param salutation
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveSalutation(Salutation salutation) throws ServiceException;

    /**
     * Find all active SecurityGroup.
     * @return
     * @throws ServiceException
     */
    List<SecurityGroup> findSecurityGroups() throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    SecurityGroup findSecurityGroupById(Long id) throws ServiceException;

    /**
     * 
     * @param dto
     * @param systemFunctionIds New SystemFunctionId(s)
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveSecurityGroup(SecurityGroup dto, Long[] systemFunctionIds)
        throws ServiceException;

    /**
     * Find all active State.
     * @return
     * @throws ServiceException
     */
    List<State> findStates() throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    State findStateById(String id) throws ServiceException;

    /**
     * 
     * @return
     * @throws ServiceException
     */
    List<State> findStateDefault() throws ServiceException;

    /**
     * 
     * @param state
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveState(State state) throws ServiceException;

    /**
     * 
     * @param stationLike
     * @param region
     * @param area
     * @return
     * @throws ServiceException
     */
    List<String> findStationByRegionArea(String stationLike, String region, String area) throws ServiceException;

    /**
     * Find all Station.
     * @return
     * @throws ServiceException
     */
    List<Station> findStations() throws ServiceException;

    /**
     * Find all active Station.
     * @return
     * @throws ServiceException
     */
    List<Station> findStationsActive() throws ServiceException;

    /**
     * Find all active Station for Region.
     * @param region
     * @return
     * @throws ServiceException
     */
    List<Station> findStationsByRegion(Region region) throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    Station findStationById(String id) throws ServiceException;

    /**
     * 
     * @param station
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveStation(Station station) throws ServiceException;

    /**
     * 
     * @return
     * @throws ServiceException
     */
    List<SupplierType> findSupplierTypes() throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    SupplierType findSupplierTypeById(Long id) throws ServiceException;

    /**
     * 
     * @param supplierType
     * @throws ServiceException
     */
    void saveSupplierType(SupplierType supplierType) throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
	SystemFunction findSystemFunctionById(Long id) throws ServiceException;

	/**
	 * 
	 * @param systemFunction
	 * @throws ServiceException
	 */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	void saveSystemFunction(SystemFunction systemFunction) throws ServiceException;

	/**
     * 
     * @return
     * @throws ServiceException
     */
    List<SystemFunction> findSystemFunctions() throws ServiceException;

    /**
     * Find all active Template.
     * @return
     * @throws ServiceException
     */
    List<Template> findTemplates() throws ServiceException;

    /**
     * 
     * @param templateType
     * @return
     * @throws ServiceException
     */
    List<Template> findTemplates(TemplateTypeEnum templateType) throws ServiceException;

    /**
     * Find all active Template by criteria.
     * @param criteria
     * @return
     * @throws ServiceException
     */
    List<Template> findTemplates(TemplateSearchCriteria criteria) throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    Template findTemplateById(Long id) throws ServiceException;

    /**
     * 
     * @param templateEnum
     * @return
     * @throws ServiceException
     */
    Template getTemplate(TemplateEnum templateEnum) throws ServiceException;

    /**
     * 
     * @param template
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	void deleteTemplate(Template template) throws ServiceException;

    /**
     * 
     * @param template
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveTemplate(Template template) throws ServiceException;

    /**
     * 
     * @return
     * @throws ServiceException
     */
    List<TemplateType> findTemplateTypes() throws ServiceException;

    /**
     * Find all active UserType.
     * @return
     * @throws ServiceException
     */
    List<UserType> findUserTypes() throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    UserType findUserTypeById(Long id) throws ServiceException;

    /**
     * 
     * @param userType
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveUserType(UserType userType) throws ServiceException;

    /**
     * Find all WorkGroup.
     * @return
     * @throws ServiceException
     */
    List<WorkGroup> findWorkGroups() throws ServiceException;

    /**
     * Find all active WorkGroup.
     * @return
     * @throws ServiceException
     */
    List<WorkGroup> findWorkGroupsActive() throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    WorkGroup findWorkGroupById(Long id) throws ServiceException;

    /**
     * 
     * @param workGroup
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveWorkGroup(WorkGroup workGroup) throws ServiceException;

}