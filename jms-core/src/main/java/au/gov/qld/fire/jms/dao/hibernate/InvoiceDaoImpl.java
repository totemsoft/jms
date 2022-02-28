package au.gov.qld.fire.jms.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.InvoiceDao;
import au.gov.qld.fire.jms.domain.finance.Invoice;
import au.gov.qld.fire.jms.domain.finance.InvoiceSearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class InvoiceDaoImpl extends BaseDaoImpl<Invoice> implements InvoiceDao
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.InvoiceDao#findFiscalYears()
	 */
    @SuppressWarnings("unchecked")
	public List<Integer> findFiscalYears()
	{
		List<Number> years = getEntityManager()
	        .createNamedQuery("invoice.findFiscalYears")
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

	public Invoice findByReference(String reference)
    {
        try
        {
            return (Invoice) getEntityManager()
                .createNamedQuery("invoice.findByReference")
                .setParameter("reference", reference)
                .getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Invoice> findByCriteria(InvoiceSearchCriteria criteria)
    {
        return getEntityManager()
            .createNamedQuery("invoice.findByCriteria")
            .setParameter("fiscalYear", criteria.getFiscalYear())
            .setParameter("invoiceTypeId", criteria.getInvoiceTypeId())
            .setParameter("invoiceAreaId", criteria.getInvoiceAreaId())
            .getResultList();
    }

}