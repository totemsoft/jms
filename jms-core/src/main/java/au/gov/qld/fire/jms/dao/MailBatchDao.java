package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.jms.domain.action.MailSearchCriteria;
import au.gov.qld.fire.jms.domain.mail.MailBatch;

/**
 * MailBatch DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface MailBatchDao extends BaseDao<MailBatch>
{

    MailBatch findByName(String name);

    List<MailBatch> findByCriteria(MailSearchCriteria criteria);

}