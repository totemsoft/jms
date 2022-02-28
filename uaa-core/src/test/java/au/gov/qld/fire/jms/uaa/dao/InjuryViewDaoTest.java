package au.gov.qld.fire.jms.uaa.dao;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.jms.uaa.domain.IncidentSearchCriteria;
import au.gov.qld.fire.jms.uaa.domain.firu.InjuryView;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class InjuryViewDaoTest extends BaseTestCase {

	@Inject
	private InjuryViewDao injuryViewDao;

	@Test
	public void testFindByCriteria() {
		IncidentSearchCriteria criteria = new IncidentSearchCriteria();
	    List<InjuryView> result = injuryViewDao.findByCriteria(criteria);
	    Assert.assertTrue(!result.isEmpty());
	}

	@Test
	public void testFindById() {

	}

	@Test
	public void testFindAll() {

	}

	@Test
	public void testFindActive() {

	}

	@Test
	public void testSave() {

	}

	@Test
	public void testDelete() {

	}

}