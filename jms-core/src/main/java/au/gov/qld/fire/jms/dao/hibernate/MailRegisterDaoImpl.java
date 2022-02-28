package au.gov.qld.fire.jms.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.FileDao;
import au.gov.qld.fire.jms.dao.MailRegisterDao;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.mail.ActiveMailRegister;
import au.gov.qld.fire.jms.domain.mail.MailRegister;
import au.gov.qld.fire.jms.domain.mail.MailRegisterSearchCriteria;
import au.gov.qld.fire.jms.domain.refdata.FileTypeEnum;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class MailRegisterDaoImpl extends BaseDaoImpl<MailRegister> implements MailRegisterDao
{

	@Autowired private FileDao fileDao;

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(MailRegister entity) throws DaoException {
        try
        {
        	if (entity.getFile() == null)
        	{
        		entity.setFile(new File());
        	}
        	if (entity.getFile().getId() == null)
        	{
        		if (entity.getFile().getFileType() == null)
        		{
        			entity.getFile().setFileType(FileTypeEnum.MAIL_REGISTER);
        		}
        		fileDao.save(entity.getFile());
        	}
            if (entity.getFile().getSapHeader() != null && entity.getFile().getSapHeader().getId() == null)
            {
            	entity.getFile().setSapHeader(null);
            }
        	if (entity.getWorkGroup() != null && entity.getWorkGroup().getId() == null)
        	{
        		entity.setWorkGroup(null);
        	}
        	if (entity.getUser() != null && entity.getUser().getId() == null)
        	{
        		entity.setUser(null);
        	}
        	if (entity.getFileAction() != null && entity.getFileAction().getId() == null)
        	{
        		entity.setFileAction(null);
        	}
        	if (entity.getJobAction() != null && entity.getJobAction().getId() == null)
        	{
        		entity.setJobAction(null);
        	}
        	super.saveOrUpdate(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.AseFileDao#findByCriteria(au.gov.qld.fire.jms.domain.mail.MailRegisterSearchCriteria)
	 */
    @SuppressWarnings("unchecked")
	public List<MailRegister> findByCriteria(MailRegisterSearchCriteria criteria) throws DaoException
	{
        try
        {
        	String[] names = StringUtils.split(criteria.getContactName(), ' ');
        	List<ActiveMailRegister> items = getEntityManager()
           		.createNamedQuery("mailRegister.findByCriteria")
        		.setMaxResults(criteria.getMaxResults())
        		.setParameter("date", criteria.getDate())
        		.setParameter("mailIn", getYesNo(criteria.getMailIn()))
        		.setParameter("rts", getYesNo(criteria.getRts()))
                .setParameter("sapCustNo", getValueLike(criteria.getSapCustNo()))
                .setParameter("fcaNo", getValueLike(criteria.getFcaNo()))
        		.setParameter("mailTypeId", criteria.getMailTypeId())
                .setParameter("mailRegisterNo", getValueLike(criteria.getMailRegisterNo()))
                .setParameter("firstName", names != null && names.length > 0 ? getValueLike(names[0]) : null)
                .setParameter("surname", names != null && names.length > 1 ? getValueLike(names[1]) : (names != null && names.length > 0 ? "%" : null))
        		.getResultList();
        	List<MailRegister> result = new ArrayList<MailRegister>();
        	for (ActiveMailRegister item : items)
        	{
        		result.add(findById(item.getMailRegisterId()));
        	}
        	return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

}