package au.gov.qld.fire.jms.uaa.dao;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.jms.uaa.domain.refdata.InjuryType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class InjuryTypeDaoTest extends BaseTestCase {

	@Inject	private InjuryTypeDao injuryTypeDao;

	@Test
	public void testFindByName() {
		InjuryType result = injuryTypeDao.findByName("burns");
		Assert.assertNotNull(result);
	}

	@Test
	public void testFindAllBoolean() {
		List<InjuryType> result = injuryTypeDao.findAll(null);
		Assert.assertTrue(result.size() >= 0);
	}

}