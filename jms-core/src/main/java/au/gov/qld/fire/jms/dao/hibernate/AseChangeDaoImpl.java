package au.gov.qld.fire.jms.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.AseChangeDao;
import au.gov.qld.fire.jms.domain.ase.AseChange;
import au.gov.qld.fire.jms.domain.ase.AseFile;
import au.gov.qld.fire.jms.domain.file.FileSearchCriteria;
import au.gov.qld.fire.jms.domain.refdata.FileStatusEnum;
import au.gov.qld.fire.util.DateUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseChangeDaoImpl extends BaseDaoImpl<AseChange> implements AseChangeDao
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(AseChange entity) throws DaoException {
		super.saveOrUpdate(entity);
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.AseChangeDao#findByAseFile(au.gov.qld.fire.jms.domain.ase.AseFile)
     */
    @SuppressWarnings("unchecked")
    public List<AseChange> findByAseFile(AseFile aseFile) throws DaoException
    {
        try
        {
            Query qry = getSession().getNamedQuery("aseChange.findByAseFile");
            qry.setParameter("aseFile", aseFile);
            return (List<AseChange>) qry.list();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.AseChangeDao#findByDateChange(FileSearchCriteria, java.util.Date)
     */
    @SuppressWarnings("unchecked")
    public List<AseChange> findByDateChange(FileSearchCriteria criteria, Date dateChange)
        throws DaoException
    {
        try
        {
            //FROM AseChange WHERE dateChange >= :dateChangeFrom AND dateChange < :dateChangeTo
            Query qry = getSession().getNamedQuery("aseChange.findByDateChange");
            qry.setParameter("fileStatusId", FileStatusEnum.CONNECTED.getId());
            qry.setParameter("fileNo", StringUtils.isBlank(criteria.getFileNo()) ? null : (criteria
                .getFileNo() + "%"));
            //qry.setParameter("supplierName", StringUtils.isBlank(criteria.getSupplierName()) ? null
            //    : (criteria.getSupplierName() + "%"));
            qry.setParameter("dateStart", DateUtils.getStartOfDay(dateChange));
            qry.setParameter("dateEnd", DateUtils.getEndOfDay(dateChange));
            List<AseChange> result = (List<AseChange>) qry.list();
            //FIXME: could not work out hql for aseChangeSuppliers.supplier.name (done in service)
            return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}