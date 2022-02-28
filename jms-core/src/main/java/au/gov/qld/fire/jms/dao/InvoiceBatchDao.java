package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.jms.domain.finance.InvoiceBatch;
import au.gov.qld.fire.jms.domain.finance.InvoiceSearchCriteria;

/**
 * InvoiceBatch DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface InvoiceBatchDao extends BaseDao<InvoiceBatch>
{

	/**
	 * 
	 * @return
	 */
	List<Integer> findFiscalYears();

	/**
	 * 
	 * @param criteria
	 * @return
	 */
	List<InvoiceBatch> findByCriteria(InvoiceSearchCriteria criteria);

	/**
	 * 
	 * @param fiscalYear
	 * @return
	 */
	int findMaxSequence(int fiscalYear);

}