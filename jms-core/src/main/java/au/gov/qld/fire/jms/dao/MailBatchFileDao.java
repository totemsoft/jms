package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.jms.domain.action.MailSearchCriteria;
import au.gov.qld.fire.jms.domain.mail.MailBatchFile;

/**
 * MailBatchFile DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface MailBatchFileDao extends BaseDao<MailBatchFile>
{

	List<MailBatchFile> findByCriteria(MailSearchCriteria criteria);

}