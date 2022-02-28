package au.gov.qld.fire.dao.hibernate;

import au.gov.qld.fire.dao.SystemFunctionDao;
import au.gov.qld.fire.domain.security.SystemFunction;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SystemFunctionDaoImpl extends BaseDaoImpl<SystemFunction> implements SystemFunctionDao
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllOrderBy()
	 */
	@Override
	protected String findAllOrderBy() {
		return "ORDER BY module, name";
	}

}