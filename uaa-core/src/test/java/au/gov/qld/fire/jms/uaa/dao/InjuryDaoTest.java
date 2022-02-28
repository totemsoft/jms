package au.gov.qld.fire.jms.uaa.dao;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.jms.uaa.domain.Injury;
import au.gov.qld.fire.jms.uaa.domain.IncidentSearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class InjuryDaoTest extends BaseTestCase {

	@Inject	private InjuryDao injuryDao;

	@Test
	public void testFindByCriteria() {
		IncidentSearchCriteria criteria = new IncidentSearchCriteria();
	    List<Injury> result = injuryDao.findByCriteria(criteria);
	    Assert.assertTrue(result.size() >= 0);
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