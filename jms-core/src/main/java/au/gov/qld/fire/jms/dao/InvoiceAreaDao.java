package au.gov.qld.fire.jms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.jms.domain.finance.InvoiceArea;

/**
 * InvoiceArea DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface InvoiceAreaDao extends BaseDao<InvoiceArea>
{

	/**
	 * 
	 * @param name
	 * @return
	 */
	List<InvoiceArea> findByNameLike(String name);

}