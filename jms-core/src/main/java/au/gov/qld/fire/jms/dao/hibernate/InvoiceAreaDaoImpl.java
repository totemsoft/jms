package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.InvoiceAreaDao;
import au.gov.qld.fire.jms.domain.finance.InvoiceArea;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class InvoiceAreaDaoImpl extends BaseDaoImpl<InvoiceArea> implements InvoiceAreaDao
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.InvoiceAreaDao#findByNameLike(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<InvoiceArea> findByNameLike(String name)
	{
        return getEntityManager()
            .createNamedQuery("invoiceArea.findByNameLike")
            .setParameter("name", getValueLike(name))
            .getResultList();
	}

}