package au.gov.qld.fire.jms.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.InvoiceBatchDao;
import au.gov.qld.fire.jms.domain.finance.InvoiceBatch;
import au.gov.qld.fire.jms.domain.finance.InvoiceSearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class InvoiceBatchDaoImpl extends BaseDaoImpl<InvoiceBatch> implements InvoiceBatchDao
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.InvoiceBatchDao#findFiscalYears()
	 */
    @SuppressWarnings("unchecked")
	public List<Integer> findFiscalYears()
	{
		List<Number> years = getEntityManager()
	        .createNamedQuery("invoiceBatch.findFiscalYears")
	        .getResultList();
		List<Integer> result = new ArrayList<Integer>();
		for (Number year : years)
		{
			if (year != null)
			{
				result.add(year.intValue());
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
    public List<InvoiceBatch> findByCriteria(InvoiceSearchCriteria criteria)
    {
        return getEntityManager()
            .createNamedQuery("invoiceBatch.findByCriteria")
            .setParameter("fiscalYear", criteria.getFiscalYear())
            .setParameter("invoiceTypeId", criteria.getInvoiceTypeId())
            .setParameter("invoiceAreaId", criteria.getInvoiceAreaId())
            .getResultList();
    }

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.InvoiceBatchDao#findMaxSequence(int)
	 */
	public int findMaxSequence(int fiscalYear)
	{
        try
        {
    		Number result = (Number) getEntityManager()
	            .createNamedQuery("invoiceBatch.findMaxSequence")
	            .setParameter("fiscalYear", fiscalYear)
	            .getSingleResult();
			return result == null ? 0 : result.intValue();
        }
        catch (NoResultException e)
        {
            return 0;
        }
	}

}