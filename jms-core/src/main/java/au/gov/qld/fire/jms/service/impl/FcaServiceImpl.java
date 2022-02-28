package au.gov.qld.fire.jms.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import au.gov.qld.fire.dao.StationDao;
import au.gov.qld.fire.domain.location.Station;
import au.gov.qld.fire.jms.dao.FcaDao;
import au.gov.qld.fire.jms.dao.FileDao;
import au.gov.qld.fire.jms.domain.ConvertUtils;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.fca.FcaSearchCriteria;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.service.FcaService;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.service.ValidationException;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FcaServiceImpl implements FcaService
{

    /** logger. */
    private static final Logger LOG = Logger.getLogger(FcaServiceImpl.class);

    @Autowired private FcaDao fcaDao;

    @Autowired private FileDao fileDao;

    @Autowired private StationDao stationDao;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FcaService#findFcaById(java.lang.Long)
     */
    public Fca findFcaById(String id) throws ServiceException
    {
        try
        {
            return fcaDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FcaService#findFca(au.gov.qld.fire.jms.domain.fca.FcaSearchCriteria)
     */
    @SuppressWarnings("unchecked")
    public List<Fca> findFca(FcaSearchCriteria criteria) throws ServiceException
    {
        try
        {
            // enforce region criteria (to limit result set) - if no fca like set
            if (StringUtils.isBlank(criteria.getRegion()) && StringUtils.isBlank(criteria.getFcaNo()))
            {
                return Collections.EMPTY_LIST;
            }
            return fcaDao.findByCriteria(criteria);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FcaService#saveFca(au.gov.qld.fire.jms.domain.Fca)
     */
    public void saveFca(Fca fca) throws ServiceException
    {
        if (fca == null || fca.getId() == null)
        {
            return;
        }
        try
        {
            // validate fileId
            Long fileId = fca.getFile().getFileId();
            File file = fileDao.findById(fileId);
            if (file == null)
            {
                throw new ValidationException("File No: " + fileId + " not found");
            }
            // implement UNIQUE fileId constraint (https://connect.microsoft.com/SQLServer/feedback/ViewFeedback.aspx?FeedbackID=299229)
            fcaDao.clearFileId(fileId);

            // refresh
            refreshFca(fca);

            // find by id
            Fca entity = fcaDao.findById(fca.getId());

            // and copy
            ConvertUtils.copyProperties(fca, entity);

            // optional

            // save
            fcaDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (ValidationException e) {
        	throw e;
        }
        catch (Exception e) {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FcaService#refreshFca(au.gov.qld.fire.jms.domain.Fca)
     */
    public void refreshFca(Fca fca) throws ServiceException
    {
        if (fca == null || fca.getId() == null)
        {
            return;
        }
        try
        {
            //set area for easy query
            Station station = fca.getStation();
            if (station != null && station.getId() != null)
            {
                station = stationDao.findById(station.getId());
                fca.setStation(station);
                fca.setArea(station.getArea());
            }
            else
            {
                fca.setStation(null);
                fca.setArea(null);
            }
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

}