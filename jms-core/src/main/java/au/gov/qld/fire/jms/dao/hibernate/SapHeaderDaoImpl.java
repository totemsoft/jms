package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.SapHeaderDao;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.sap.SapHeader;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SapHeaderDaoImpl extends BaseDaoImpl<SapHeader> implements SapHeaderDao
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.SapHeaderDao#findByFile(au.gov.qld.fire.jms.domain.file.File)
     */
    public SapHeader findByFile(File file) throws DaoException
    {
        try
        {
        	return (SapHeader) getEntityManager()
            	.createNamedQuery("sapHeader.findByFile")
                .setParameter("file", file)
                .getSingleResult();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.SapHeaderDao#findSapCustNo(java.lang.String)
	 */
	public List<String> findSapCustNo(String sapCustNo) throws DaoException
	{
       	return findValueLike("sapHeader.findSapCustNo", sapCustNo);
	}

}