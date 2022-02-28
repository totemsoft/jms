package au.gov.qld.fire.jms.uaa.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;

import au.gov.qld.fire.jms.uaa.dao.InjuryDao;
import au.gov.qld.fire.jms.uaa.dao.InjuryViewDao;
import au.gov.qld.fire.jms.uaa.domain.IncidentSearchCriteria;
import au.gov.qld.fire.jms.uaa.domain.Injury;
import au.gov.qld.fire.jms.uaa.domain.firu.InjuryView;
import au.gov.qld.fire.jms.uaa.domain.firu.InjuryViewPK;
import au.gov.qld.fire.jms.uaa.service.FiruImportService;
import au.gov.qld.fire.service.ServiceException;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FiruImportServiceImpl implements FiruImportService {

	@Inject private InjuryDao injuryDao;

	@Inject private InjuryViewDao injuryViewDao;

	public InjuryView findInjuryViewById(InjuryViewPK id) throws ServiceException {
		try {
			InjuryView result = injuryViewDao.findById(id);
			Injury injury = injuryDao.findByUniqueKey(id.getFireCallNo(), id.getInjuryNo());
			result.setInjuryId(injury == null ? null : injury.getId());
			return result;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public List<InjuryView> findInjuryViewByCriteria(IncidentSearchCriteria criteria) throws ServiceException {
		try {
			List<InjuryView> result = injuryViewDao.findByCriteria(criteria);
			for (InjuryView item : result) {
				Injury injury = injuryDao.findByUniqueKey(item.getFireCallNo(), item.getInjuryNo());
				item.setInjuryId(injury == null ? null : injury.getId());
			}
			return result;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void importInjury(List<InjuryViewPK> ids) throws ServiceException {
		try {
			for (InjuryViewPK id : ids) {
				InjuryView source = injuryViewDao.findById(id);
				Injury target = injuryDao.findByUniqueKey(source.getFireCallNo(), source.getInjuryNo());
				if (target == null) {
					target = new Injury();
				}
				BeanUtils.copyProperties(source, target, new String[] {"id"});
				injuryDao.save(target);
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}