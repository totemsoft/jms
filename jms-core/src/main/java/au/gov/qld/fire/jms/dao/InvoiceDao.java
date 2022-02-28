package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.jms.domain.finance.Invoice;
import au.gov.qld.fire.jms.domain.finance.InvoiceSearchCriteria;

/**
 * Invoice DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface InvoiceDao extends BaseDao<Invoice>
{

	/**
	 * 
	 * @return
	 */
	List<Integer> findFiscalYears();

	/**
	 * 
	 * @param reference
	 * @return
	 */
	Invoice findByReference(String reference);

	/**
	 * 
	 * @param criteria
	 * @return
	 */
	List<Invoice> findByCriteria(InvoiceSearchCriteria criteria);

}