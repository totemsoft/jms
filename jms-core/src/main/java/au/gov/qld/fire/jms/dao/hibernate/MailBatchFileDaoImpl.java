package au.gov.qld.fire.jms.dao.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.ActionCodeDao;
import au.gov.qld.fire.jms.dao.MailBatchFileDao;
import au.gov.qld.fire.jms.domain.action.MailSearchCriteria;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.mail.MailBatchFile;
import au.gov.qld.fire.jms.domain.mail.MailStatusEnum;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;
import au.gov.qld.fire.jms.domain.refdata.OwnerTypeEnum;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class MailBatchFileDaoImpl extends BaseDaoImpl<MailBatchFile> implements MailBatchFileDao
{

    @Inject private ActionCodeDao actionCodeDao;

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.MailBatchFileDao#findByCriteria(au.gov.qld.fire.jms.domain.action.MailSearchCriteria)
	 */
	@SuppressWarnings("unchecked")
	public List<MailBatchFile> findByCriteria(MailSearchCriteria criteria)
	{
        final Long actionCodeId = criteria.getActionCodeId();
        ActionCode actionCode = actionCodeDao.findById(actionCodeId);
        criteria.setActionCode(actionCode == null ? null : actionCode.getCode());
        //if (actionCode == null) {
        //	return result;
        //}
        //
		boolean noStatus = false;
		List<MailStatusEnum> status = new ArrayList<MailStatusEnum>();
		if (criteria.getStatus() == null) {
			status.add(MailStatusEnum.NONE);
			noStatus = true;
		}
		else {
			for (MailStatusEnum s : criteria.getStatus()) {
				if (s == null || MailStatusEnum.NONE.equals(s)) {
					noStatus = true;
				} else {
					status.add(s);
				}
			}
		}
		List<MailBatchFile> result = getEntityManager()
            .createNamedQuery("mailBatchFile.findByCriteria")
            .setParameter("batchId", criteria.getBatchId())
            .setParameter("actionCodeId", actionCodeId)
            .setParameter("noStatus", noStatus).setParameter("status", status)
            .setParameter("doNotMail", criteria.isDoNotMail())
            .setParameter("fcaNo", getValueLike(criteria.getFcaNo()))
            .setParameter("fileNo", getValueLike(criteria.getFileNo()))
            .setParameter("regionId", criteria.getRegionId())
            .setParameter("sentDateOption", criteria.getSentDateOption().getId())
            .setParameter("sentDateFrom", criteria.getSentDateFrom())
            .setParameter("sentDateTo", criteria.getSentDateTo())
            .setParameter("receivedDateOption", criteria.getReceivedDateOption().getId())
            .setParameter("receivedDateFrom", criteria.getReceivedDateFrom())
            .setParameter("receivedDateTo", criteria.getReceivedDateTo())
            //.setParameter("ownerTypeId", criteria.getOwnerType().getId())
            //.setParameter("ownerId", criteria.getOwnerId()) // by legalName
            //.setParameter("ownerIdContact", criteria.getOwnerIdContact()) // by contact first/last name
            .getResultList();
		// apply owner filter (to hard in hql)
        //" AND (:ownerTypeId IS NULL OR o.OWNER_TYPE_ID = :ownerTypeId)" +
        //" AND (:ownerId IS NULL OR o.OWNER_ID = :ownerId)" +
        //" AND (:ownerIdContact IS NULL OR o.OWNER_ID = :ownerIdContact)" +
		OwnerTypeEnum ownerType = criteria.getOwnerType();
		if (ownerType == null) {
			ownerType = OwnerTypeEnum.NONE;
		}
		Long ownerId = criteria.getOwnerId(); // by legalName
		Long ownerIdContact = criteria.getOwnerIdContact(); // by contact first/last name
		for (Iterator<MailBatchFile> iter = result.iterator(); iter.hasNext(); ) {
			File f = iter.next().getFile();
			boolean remove = false;
			if (!OwnerTypeEnum.NONE.equals(ownerType) && f.getOwnerByType(ownerType) == null) {
				remove = true;
			} else if (ownerId != null && f.getOwnerById(ownerId) == null) {
				remove = true;
			} else if (ownerIdContact != null && f.getOwnerById(ownerIdContact) == null) {
				remove = true;
			}
			if (remove) {
				iter.remove();
			}
		}
		return result;
	}

}