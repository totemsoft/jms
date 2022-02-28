package au.gov.qld.fire.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import au.gov.qld.fire.dao.AreaDao;
import au.gov.qld.fire.dao.DocTypeDao;
import au.gov.qld.fire.dao.DocumentDao;
import au.gov.qld.fire.dao.JdbcDao;
import au.gov.qld.fire.dao.RegionDao;
import au.gov.qld.fire.dao.SalutationDao;
import au.gov.qld.fire.dao.SecurityGroupDao;
import au.gov.qld.fire.dao.StateDao;
import au.gov.qld.fire.dao.StationDao;
import au.gov.qld.fire.dao.SupplierTypeDao;
import au.gov.qld.fire.dao.SystemFunctionDao;
import au.gov.qld.fire.dao.TemplateDao;
import au.gov.qld.fire.dao.TemplateTypeDao;
import au.gov.qld.fire.dao.UserTypeDao;
import au.gov.qld.fire.dao.WorkGroupDao;
import au.gov.qld.fire.domain.ConvertUtils;
import au.gov.qld.fire.domain.document.Document;
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
import au.gov.qld.fire.service.EntityService;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.util.ThreadLocalUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class EntityServiceImpl implements EntityService
{

    /** logger. */
    private static final Logger LOG = Logger.getLogger(EntityServiceImpl.class);

    @Inject private AreaDao areaDao;

    @Inject private DocTypeDao docTypeDao;

    @Inject private DocumentDao documentDao;

    @Inject private JdbcDao jdbcDao;

    @Inject private RegionDao regionDao;

    @Inject private SalutationDao salutationDao;

    @Inject private SecurityGroupDao securityGroupDao;

    @Inject private StateDao stateDao;

    @Inject private StationDao stationDao;

    @Inject private SupplierTypeDao supplierTypeDao;

    @Inject private SystemFunctionDao systemFunctionDao;

    @Inject private TemplateDao templateDao;

    @Inject private TemplateTypeDao templateTypeDao;

    @Inject private UserTypeDao userTypeDao;

    @Inject private WorkGroupDao workGroupDao;

    /**
     * @return the jdbcDao
     */
    protected JdbcDao getJdbcDao()
    {
        return jdbcDao;
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#synchroniseDatabase(java.net.URL)
     */
    public void synchroniseDatabase(URL source) throws Exception
    {
        LOG.info("\tSTART synchroniseDatabase from [" + source.toExternalForm() + "]");
        BufferedReader reader = new BufferedReader(new InputStreamReader(source.openStream()));
        try
        {
            List<String> list = new ArrayList<String>();
            String sql = "";
            String line;
            while ((line = reader.readLine()) != null)
            {
                // check for comments
                int index = line.indexOf("--");
                if (index == 0)
                {
                    continue; // comment at the beginning
                }
                if (index > 0)
                {
                    line = line.substring(0, index); // comment at the end
                }
                sql += " " + line + " ";
                // check for end of statement (";" or " GO ")
                index = sql.indexOf(";");
                if (index < 0)
                {
                    index = sql.indexOf(" GO ");
                }
                if (index < 0)
                {
                    continue;
                }
                sql = sql.substring(0, index).trim();
                if (sql.length() > 0)
                {
                    list.add(sql);
                }
                sql = "";
            }
            //
            for (String query : list) {
                LOG.info(query);
                /*int count = */jdbcDao.executeUpdate(query);
            }
        }
        finally
        {
            IOUtils.closeQuietly(reader);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findWorkGroup(java.lang.String)
     */
    public List<String> findWorkGroup(String workGroup) throws ServiceException
    {
        try
        {
            return workGroupDao.findWorkGroup(workGroup);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findAreaByRegion(java.lang.String, java.lang.String)
     */
    public List<String> findAreaByRegion(String areaLike, String region) throws ServiceException
    {
        try
        {
            return areaDao.findAreaByRegion(areaLike, region);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findAreas()
     */
    public List<Area> findAreas() throws ServiceException
    {
        try
        {
            return areaDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findAreasActive()
     */
    public List<Area> findAreasActive() throws ServiceException
    {
        try
        {
            return areaDao.findAllActive();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findAreaById(java.lang.String)
     */
    public Area findAreaById(String id) throws ServiceException
    {
        try
        {
            return areaDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#saveArea(au.gov.qld.fire.jms.domain.location.Area)
     */
    public void saveArea(Area area) throws ServiceException
    {
        try
        {
            //find by id
            String id = area.getId();
            Area entity = null;
            if (id != null)
            {
                entity = areaDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = area;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(area, entity);
            }

            //optional

            //save
            areaDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findDocTypes()
     */
    public List<DocType> findDocTypes() throws ServiceException
    {
        try
        {
            return docTypeDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findDocTypeById(java.lang.Long)
     */
    public DocType findDocTypeById(Long id) throws ServiceException
    {
        try
        {
            return docTypeDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#saveDocType(au.gov.qld.fire.jms.domain.refdata.DocType)
     */
    public void saveDocType(DocType docType) throws ServiceException
    {
        try
        {
            //find by id
            Long id = docType.getId();
            DocType entity = null;
            if (id != null)
            {
                entity = docTypeDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = docType;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(docType, entity);
            }

            //optional

            //save
            docTypeDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findRegions()
     */
    public List<Region> findRegions() throws ServiceException
    {
        try
        {
            return regionDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findRegionsActive()
     */
    public List<Region> findRegionsActive() throws ServiceException
    {
        try
        {
            return regionDao.findAllActive();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findRegion(java.lang.String)
     */
    public List<String> findRegion(String regionLike) throws ServiceException
    {
        try
        {
            return regionDao.findRegion(regionLike);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findRegionById(java.lang.Long)
     */
    public Region findRegionById(Long id) throws ServiceException
    {
        try
        {
            return regionDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#saveRegion(au.gov.qld.fire.jms.domain.location.Region)
     */
    public void saveRegion(Region region) throws ServiceException
    {
        try
        {
            //find by id
            Long id = region.getId();
            Region entity = null;
            if (id != null)
            {
                entity = regionDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = region;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(region, entity);
            }

            //optional

            //save
            regionDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findSalutations()
     */
    public List<Salutation> findSalutations() throws ServiceException
    {
        try
        {
            return salutationDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findSalutationById(java.lang.String)
     */
    public Salutation findSalutationById(String id) throws ServiceException
    {
        try
        {
            return salutationDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#saveSalutation(au.gov.qld.fire.jms.domain.refdata.Salutation)
     */
    public void saveSalutation(Salutation salutation) throws ServiceException
    {
        try
        {
            //find by id
            String id = salutation.getId();
            Salutation entity = null;
            if (id != null)
            {
                entity = salutationDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = salutation;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                //ConvertUtils.copyProperties(salutation, entity); // no PK edit
            	throw new ServiceException("no PK edit");
            }

            //optional

            //save
            salutationDao.save(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findSecurityGroups()
     */
    public List<SecurityGroup> findSecurityGroups() throws ServiceException
    {
        try
        {
            return securityGroupDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findSecurityGroupById(java.lang.Long)
     */
    public SecurityGroup findSecurityGroupById(Long id) throws ServiceException
    {
        try
        {
            return securityGroupDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#saveSecurityGroup(au.gov.qld.fire.jms.domain.security.SecurityGroup, Long[])
     */
    public void saveSecurityGroup(SecurityGroup dto, Long[] systemFunctionIds)
        throws ServiceException
    {
        try
        {
            //find by id
            Long id = dto.getId();
            SecurityGroup entity = null;
            if (id != null)
            {
                entity = securityGroupDao.findById(id);
            }

            //transform systemFunctionIds to systemFunctions
            List<SystemFunction> systemFunctions = dto.getSystemFunctions();
            systemFunctions.clear();
            CollectionUtils.collect(Arrays.asList(systemFunctionIds), new Transformer()
            {
                /* (non-Javadoc)
                 * @see org.apache.commons.collections.Transformer#transform(java.lang.Object)
                 */
                public Object transform(Object obj)
                {
                    Long systemFunctionId = (Long) obj;
                    return new SystemFunction(systemFunctionId);
                }
            }, systemFunctions);

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = dto;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(dto, entity);
            }

            //optional

            //save
            securityGroupDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findStates()
     */
    public List<State> findStates() throws ServiceException
    {
        try
        {
            return stateDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findStateById(java.lang.String)
     */
    public State findStateById(String id) throws ServiceException
    {
        try
        {
            return stateDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findStateDefault()
     */
    public List<State> findStateDefault() throws ServiceException
    {
        try
        {
            return stateDao.findDefault();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#saveState(au.gov.qld.fire.jms.domain.location.State)
     */
    public void saveState(State state) throws ServiceException
    {
        try
        {
            //find by id
            String id = state.getId();
            State entity = null;
            if (id != null)
            {
                entity = stateDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = state;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(state, entity);
            }

            //optional

            //save
            stateDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findStationByRegionArea(java.lang.String, java.lang.String, java.lang.String)
     */
    public List<String> findStationByRegionArea(String stationLike, String region, String area)
        throws ServiceException
    {
        try
        {
            return stationDao.findStationByRegionArea(stationLike, region, area);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findStations()
     */
    public List<Station> findStations() throws ServiceException
    {
        try
        {
            return stationDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findStationsActive()
     */
    public List<Station> findStationsActive() throws ServiceException
    {
        try
        {
            return stationDao.findAllActive();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findStationsByRegion(au.gov.qld.fire.jms.domain.location.Region)
     */
    public List<Station> findStationsByRegion(Region region) throws ServiceException
    {
        try
        {
            return stationDao.findByRegion(region);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findStationById(java.lang.String)
     */
    public Station findStationById(String id) throws ServiceException
    {
        try
        {
            return stationDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#saveStation(au.gov.qld.fire.jms.domain.location.Station)
     */
    public void saveStation(Station station) throws ServiceException
    {
        try
        {
            //find by id
            String id = station.getId();
            Station entity = null;
            if (id != null)
            {
                entity = stationDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = station;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(station, entity);
            }

            //optional

            //save
            stationDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findSupplierTypes()
     */
    public List<SupplierType> findSupplierTypes() throws ServiceException
    {
        try
        {
            return supplierTypeDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findSupplierTypeById(java.lang.Long)
     */
    public SupplierType findSupplierTypeById(Long id) throws ServiceException
    {
        try
        {
            return supplierTypeDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#saveSupplierType(au.gov.qld.fire.jms.domain.refdata.SupplierType)
     */
    public void saveSupplierType(SupplierType supplierType) throws ServiceException
    {
        try
        {
            // find by id
            Long id = supplierType.getId();
            SupplierType entity = null;
            if (id != null)
            {
                entity = supplierTypeDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = supplierType;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(supplierType, entity);
            }

            // optional

            //save
            supplierTypeDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.service.EntityService#findSystemFunctionById(java.lang.Long)
	 */
	public SystemFunction findSystemFunctionById(Long id) throws ServiceException
	{
        try
        {
        	return systemFunctionDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.EntityService#saveSystemFunction(au.gov.qld.fire.domain.security.SystemFunction)
	 */
	public void saveSystemFunction(SystemFunction systemFunction) throws ServiceException
	{
        try
        {
            // find by id
            Long id = systemFunction.getId();
            SystemFunction entity = null;
            if (id != null)
            {
                entity = systemFunctionDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = systemFunction;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(systemFunction, entity);
            }

            // optional

            // save
            systemFunctionDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findSystemFunctions()
     */
    public List<SystemFunction> findSystemFunctions() throws ServiceException
    {
        try
        {
            return systemFunctionDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findTemplates()
     */
    public List<Template> findTemplates() throws ServiceException
    {
        try
        {
            return templateDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findTemplates(TemplateTypeEnum)
     */
    public List<Template> findTemplates(TemplateTypeEnum templateType) throws ServiceException
    {
        try
        {
            return templateDao.findByTemplateType(new TemplateType(templateType.getId()));
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findTemplates(au.gov.qld.fire.jms.domain.refdata.TemplateSearchCriteria)
     */
    public List<Template> findTemplates(TemplateSearchCriteria criteria) throws ServiceException
    {
        try
        {
            String templateTypes = criteria.getTemplateTypes();
            TemplateType templateType = StringUtils.isBlank(templateTypes) ? null : templateTypeDao
                .findByName(templateTypes);
            return templateType == null ? findTemplates() : templateDao
                .findByTemplateType(templateType);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findTemplateById(java.lang.Long)
     */
    public Template findTemplateById(Long id) throws ServiceException
    {
        try
        {
            return templateDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#getTemplate(au.gov.qld.fire.jms.domain.document.TemplateEnum)
     */
    public Template getTemplate(TemplateEnum templateEnum) throws ServiceException
    {
        try
        {
            return templateDao.getTemplate(templateEnum);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.service.EntityService#deleteTemplate(au.gov.qld.fire.domain.document.Template)
	 */
	public void deleteTemplate(Template template) throws ServiceException
	{
        try
        {
            // find by id
            Long id = template.getId();
            Template entity = templateDao.findById(id);

            // logical delete
            entity.setLogicallyDeleted(true);
            templateDao.saveOrUpdate(entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#saveTemplate(au.gov.qld.fire.jms.domain.document.Template)
     */
    public void saveTemplate(Template template) throws ServiceException
    {
        try
        {
            // find by id
            Long id = template.getId();
            Template entity = id == null ? null : templateDao.findById(id);

            // and copy
            boolean newEntity = entity == null;
            if (newEntity) {
                entity = template;
            } else {
                ConvertUtils.copyProperties(template, entity);
            }

            // optional
            entity.setUploadDate(entity.getContentType() == null ? null : ThreadLocalUtils.getDate());
            Document config = template.getConfig();
            if (config != null && StringUtils.isNotBlank(config.getContentType())) {
            	if (entity.getConfig() == null) {
            		entity.setConfig(config);
            	} else {
            		ConvertUtils.copyProperties(config, entity.getConfig());
            	}
            	documentDao.saveOrUpdate(entity.getConfig());
            } else {
            	entity.setConfig(null);
            }

            // save
            templateDao.saveOrUpdate(entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findTemplateTypes()
     */
    public List<TemplateType> findTemplateTypes() throws ServiceException
    {
        try
        {
            return templateTypeDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findUserTypes()
     */
    public List<UserType> findUserTypes() throws ServiceException
    {
        try
        {
            return userTypeDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findUserTypeById(java.lang.Long)
     */
    public UserType findUserTypeById(Long id) throws ServiceException
    {
        try
        {
            return userTypeDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#saveUserType(au.gov.qld.fire.jms.domain.refdata.UserType)
     */
    public void saveUserType(UserType userType) throws ServiceException
    {
        try
        {
            //find by id
            Long id = userType.getId();
            UserType entity = null;
            if (id != null)
            {
                entity = userTypeDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = userType;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(userType, entity);
            }

            //optional

            //save
            userTypeDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findWorkGroups()
     */
    public List<WorkGroup> findWorkGroups() throws ServiceException
    {
        try
        {
            return workGroupDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findWorkGroupsActive()
     */
    public List<WorkGroup> findWorkGroupsActive() throws ServiceException
    {
        try
        {
            return workGroupDao.findAllActive();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#findWorkGroupById(java.lang.Long)
     */
    public WorkGroup findWorkGroupById(Long id) throws ServiceException
    {
        try
        {
            return workGroupDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.EntityService#saveWorkGroup(au.gov.qld.fire.jms.domain.refdata.WorkGroup)
     */
    public void saveWorkGroup(WorkGroup workGroup) throws ServiceException
    {
        try
        {
            // find by id
            Long id = workGroup.getId();
            WorkGroup entity = null;
            if (id != null)
            {
                entity = workGroupDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = workGroup;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(workGroup, entity);
            }

            //optional

            //save
            workGroupDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

}