package au.gov.qld.fire.jms.dao;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.jms.domain.ase.AseKeyInvoice;

/**
 * AseKeyInvoice DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface AseKeyInvoiceDao extends BaseDao<AseKeyInvoice>
{

}