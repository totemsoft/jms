package au.gov.qld.fire.jms.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.DocumentDao;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.ActionCodeDao;
import au.gov.qld.fire.jms.dao.FileActionDao;
import au.gov.qld.fire.jms.dao.FileDao;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.action.CallSearchCriteria;
import au.gov.qld.fire.jms.domain.action.FileAction;
import au.gov.qld.fire.jms.domain.action.FileActionTodo;
import au.gov.qld.fire.jms.domain.action.MailSearchCriteria;
import au.gov.qld.fire.jms.domain.action.TodoSearchCriteria;
import au.gov.qld.fire.jms.domain.building.Building;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.mail.MailBatchFile;
import au.gov.qld.fire.jms.domain.mail.MailStatusEnum;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;
import au.gov.qld.fire.jms.domain.refdata.ActionType;
import au.gov.qld.fire.jms.domain.sap.SapHeader;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FileActionDaoImpl extends BaseDaoImpl<FileAction> implements FileActionDao
{

    @Inject private ActionCodeDao actionCodeDao;
    @Inject private DocumentDao documentDao;
    @Inject private FileDao fileDao;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#saveOrUpdate(java.lang.Object)
     */
    @Override
    public void saveOrUpdate(FileAction entity) throws DaoException {
        try
        {
            if (entity.getDocument() != null)
            {
                documentDao.saveOrUpdate(entity.getDocument());
            }
            super.saveOrUpdate(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.FileActionDao#findByFile(au.gov.qld.fire.jms.domain.file.File, ActionType)
     */
    @SuppressWarnings("unchecked")
    public List<FileAction> findByFileActionType(File file, ActionType actionType) throws DaoException
    {
        try
        {
            return getEntityManager()
                .createNamedQuery("fileAction.findByFileActionType")
                .setParameter("file", file)
                .setParameter("actionType", actionType == null || actionType.getId() == null ? null : actionType)
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.hibernate.BaseDaoImpl#delete(java.lang.Object)
     */
    @Override
    public void delete(FileAction entity) throws DaoException
    {
        if (entity == null)
        {
            return;
        }
        try
        {
            entity.setLogicallyDeleted(Boolean.TRUE);
            saveOrUpdate(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.FileActionDao#findFileActionTodoByCriteria(au.gov.qld.fire.jms.domain.action.TodoSearchCriteria)
     */
    @SuppressWarnings("unchecked")
    public List<FileActionTodo> findFileActionToDoByCriteria(final TodoSearchCriteria criteria)
        throws DaoException
    {
        try
        {
            // join query (File, Fca, Building)
            List<FileActionTodo> result = getEntityManager()
                .createNamedQuery("fileAction.findFileActionToDoByCriteria")
                .setParameter("fileNo", getValueLike(criteria.getFileNo()))
                .setParameter("fcaNo", getValueLike(criteria.getFcaNo()))
                .setParameter("completed", criteria.getCompleted())
                .setParameter("actionTypeId", criteria.getActionTypeId())
                .setParameter("actionCodeId", criteria.getActionCodeId())
                .setParameter("actionCode", getValueLike(criteria.getActionCode()))
                .setParameter("workGroupId", criteria.getWorkGroupId())
                .setParameter("workGroup", getValueLike(criteria.getWorkGroup()))
                .setMaxResults(criteria.getMaxResults())
                .getResultList();
            // Calls UI require more details than ToDo UI
            if (criteria instanceof CallSearchCriteria) {
                for (FileActionTodo item : result) {
                    FileAction a = findById(item.getId());
                    File f = (File) a.getFile();
                    SapHeader sapHeader = f.getSapHeader();
                    item.setSapCustNo(sapHeader == null ? null : sapHeader.getSapCustNo());
                    item.setCompletedBy(a.getCompletedBy() == null ? null : a.getCompletedBy().getContact().getName());
                    item.setCompletedDate(a.getCompletedDate());
                    item.setCreatedBy(a.getCreatedBy() == null ? null : a.getCreatedBy().getContact().getName());
                    item.setResponsibleUser(a.getResponsibleUser() == null ? null : a.getResponsibleUser().getContact().getName());
                    item.setDestination(a.getDestination());
                    item.setNotation(a.getNotation());
                    item.setContact(a.getContact());
                }
            }
            return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.FileActionDao#findMailOutActionByCriteria(au.gov.qld.fire.jms.domain.action.MailSearchCriteria)
	 */
    @SuppressWarnings("unchecked")
	public List<FileActionTodo> findMailOutActionByCriteria(MailSearchCriteria criteria) throws DaoException
	{
        try
        {
            final List<FileActionTodo> result = new ArrayList<FileActionTodo>();
            //
            final Long actionCodeId = criteria.getActionCodeId();
            ActionCode actionCode = actionCodeDao.findById(actionCodeId);
            criteria.setActionCode(actionCode == null ? null : actionCode.getCode());
            //if (actionCode == null) {
            //	return result;
            //}
            //
            boolean noStatus = false;
            List<Integer> status = new ArrayList<Integer>();
    		if (criteria.getStatus() == null) {
    			status.add(0);
    			noStatus = true;
            } else {
            	for (MailStatusEnum s : criteria.getStatus()) {
    				if (s == null || MailStatusEnum.NONE.equals(s)) {
    					noStatus = true;
    				} else {
            		    status.add(s.getId());
    				}
            	}
            }
            //
        	List<Object[]> datas = new ArrayList<Object[]>();
        	// native sql - @see FileActionTodo class
            datas.addAll(getEntityManager()
                .createNamedQuery("fileAction.findMailOutAction")
                .setParameter("actionCodeId", actionCodeId)
                .setParameter("noStatus", getYesNo(noStatus)).setParameter("status", status)
                .setParameter("doNotMail", getYesNo(criteria.isDoNotMail()))
                .setParameter("fileNo", getValueLike(criteria.getFileNo()))
                .setParameter("fcaNo", getValueLike(criteria.getFcaNo()))
                .setParameter("regionId", criteria.getRegionId())
                .setParameter("sentDateOption", criteria.getSentDateOption().getId())
                .setParameter("sentDateFrom", criteria.getSentDateFrom())
                .setParameter("sentDateTo", criteria.getSentDateTo())
                .setParameter("receivedDateOption", criteria.getReceivedDateOption().getId())
                .setParameter("receivedDateFrom", criteria.getReceivedDateFrom())
                .setParameter("receivedDateTo", criteria.getReceivedDateTo())
                .setParameter("ownerTypeId", criteria.getOwnerType().getId())
                .setParameter("ownerId", criteria.getOwnerId()) // by legalName
                .setParameter("ownerIdContact", criteria.getOwnerIdContact()) // by contact first/last name
                .getResultList());
            for (Object[] data : datas) {
            	Number fileActionId = (Number) data[0];
                Long fileId = ((Number) data[1]).longValue();
                Date completedDate = null;
                if (fileActionId != null) {
                	FileAction fa = findById(fileActionId.longValue());
                	completedDate = fa.getCompletedDate();
                }
                //
                File f = fileDao.findById(fileId.longValue());
                FileActionTodo item = new FileActionTodo(fileActionId == null ? null : fileActionId.longValue());
                SapHeader sapHeader = f.getSapHeader();
                item.setSapCustNo(sapHeader == null ? null : sapHeader.getSapCustNo());
                Building building = f.getBuilding();
                item.setBuildingName(building == null ? null : building.getName());
                item.setFileId(f.getFileId());
                Fca fca = f.getFca();
                item.setFcaId(fca == null ? null : fca.getFcaId());
                item.setActionCodeId(actionCode == null ? null : actionCode.getId());
                item.setNextAction(actionCode == null || completedDate == null ? null : actionCode.getCode());
                item.setNextActionDate(completedDate);
                MailBatchFile mbf = f.getLastMailBatchFile();
                item.setLastSent(mbf == null ? null : mbf.getMailStatus().getSentDate());
                item.setLastReceived(mbf == null ? null : mbf.getMailStatus().getReceivedDate());
                item.setMailMethod(f.getMailMethod());
                item.setNoMailOut(f.isNoMailOut());
                result.add(item);
            }
            //
            return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

}