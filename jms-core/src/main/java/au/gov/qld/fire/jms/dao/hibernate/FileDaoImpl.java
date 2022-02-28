package au.gov.qld.fire.jms.dao.hibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.domain.BasePair;
import au.gov.qld.fire.domain.SearchCriteria;
import au.gov.qld.fire.jms.dao.FileDao;
import au.gov.qld.fire.jms.domain.ase.AseChangeSearchCriteria;
import au.gov.qld.fire.jms.domain.file.ActiveAseFile;
import au.gov.qld.fire.jms.domain.file.ActiveFile;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.file.FileSearchCriteria;
import au.gov.qld.fire.jms.domain.refdata.FileStatusEnum;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FileDaoImpl extends BaseDaoImpl<File> implements FileDao
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.FileDao#findByStatus(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public List<File> findByStatus(Long fileStatus) throws DaoException
    {
        try
        {
        	return getEntityManager()
        		.createNamedQuery("file.findByStatus")
        		.setParameter("fileStatus", fileStatus)
				.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.FileDao#findBySapCustNo(java.lang.Long)
	 */
    @SuppressWarnings("unchecked")
	public File findBySapCustNo(Long sapCustNo) throws DaoException
	{
        try
        {
        	List<File> result = getEntityManager()
        		.createNamedQuery("file.findBySapCustNo")
        		.setParameter("sapCustNo", sapCustNo)
				.getResultList();
        	// TODO: one-to-one or many-to-one ???
        	if (result.isEmpty())
        	{
        		return null;
        	}
        	if (result.size() == 1)
        	{
        		return result.get(0);
        	}
        	LOG.warn("FileDaoImpl::findBySapCustNo(" + sapCustNo +
        		") - more then one file found for this customer number");
        	return null;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.FileDao#findFilesByCriteria(au.gov.qld.fire.jms.domain.file.FileSearchCriteria)
     */
    @SuppressWarnings("unchecked")
    public List<ActiveFile> findFilesByCriteria(FileSearchCriteria criteria)
        throws DaoException
    {
    	final List<Long> disconnected = Arrays.asList(FileStatusEnum.DISCONNECTED.getId());
    	final List<Long> connected = Arrays.asList(FileStatusEnum.CONNECTED.getId(), FileStatusEnum.PENDING.getId());
        try
        {
        	List<ActiveFile> result = getEntityManager()
        		.createNamedQuery("file.findFilesByCriteria")
	            .setMaxResults(criteria.getMaxResults())
	            .setParameter("fileTypeId", criteria.getFileTypeId())
	            .setParameter("fileStatusIds", criteria.isDisconnectedFile() ? disconnected : connected)
	            .setParameter("fileNo", getValueLike(criteria.getFileNo()))
	            .setParameter("fcaNo", getValueLike(criteria.getFcaNo()))
                .setParameter("buildingId", criteria.getBuildingId())
	            .setParameter("buildingName", getValueLike(criteria.getBuildingName()))
	            .setParameter("buildingAddress", getValueLike(criteria.getBuildingAddress()))
	            .setParameter("ownerName", getValueLike(criteria.getOwnerName()))
	            .setParameter("ownerContact", getValueLike(criteria.getOwnerContact()))
	            .setParameter("supplierName", getValueLike(criteria.getSupplierName()))
	            .getResultList();
            return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.FileDao#findAseChangeFiles(au.gov.qld.fire.jms.domain.ase.AseChangeSearchCriteria)
     */
    @SuppressWarnings("unchecked")
    public List<ActiveAseFile> findAseChangeFiles(AseChangeSearchCriteria criteria)
        throws DaoException
    {
        try
        {
        	return getEntityManager()
        		.createNamedQuery("file.findAseChangeFiles")
	            .setMaxResults(criteria.getMaxResults())
	            .setParameter("fileStatusId", FileStatusEnum.CONNECTED.getId())
	            .setParameter("fileNo", getValueLike(criteria.getFileNo()))
	            .setParameter("fcaNo", getValueLike(criteria.getFcaNo()))
                .setParameter("buildingId", criteria.getBuildingId())
	            .setParameter("buildingName", getValueLike(criteria.getBuildingName()))
	            //.setParameter("supplierName", getValueLike(criteria.getSupplierName()))
	            .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.FileDao#findFileNo(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<BasePair> findFileNo(String fileNoLike) throws DaoException
    {
        try
        {
        	List<Object[]> entities = getEntityManager()
        		.createNamedQuery("file.findFileNo")
				.setMaxResults(SearchCriteria.DEFAULT_MAX)
	            .setParameter("fileNo", getValueLike(fileNoLike))
	            .getResultList();
            List<BasePair> result = new ArrayList<BasePair>();
            for (Object[] entity : entities) {
            	Number fileId = (Number) entity[0];
            	//String fcaId = (String) entity[1];
            	result.add(new BasePair(fileId.longValue()));
            }
            return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}