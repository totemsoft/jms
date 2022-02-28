package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import org.hibernate.Query;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.KeyReceiptDao;
import au.gov.qld.fire.jms.domain.KeyReceipt;
import au.gov.qld.fire.jms.domain.file.File;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class KeyReceiptDaoImpl extends BaseDaoImpl<KeyReceipt> implements KeyReceiptDao
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.KeyReceiptDao#findByFile(au.gov.qld.fire.jms.domain.file.File)
     */
    @SuppressWarnings("unchecked")
    public List<KeyReceipt> findByFile(File file) throws DaoException
    {
        try
        {
            Query qry = getSession().getNamedQuery("keyReceipt.findByFile");
            qry.setParameter("file", file);
            return (List<KeyReceipt>) qry.list();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.KeyReceiptDao#findByKeyReceiptNo(java.lang.String)
     */
    public KeyReceipt findByKeyReceiptNo(String keyReceiptNo) throws DaoException
    {
        try
        {
            Query qry = getSession().getNamedQuery("keyReceipt.findByKeyReceiptNo");
            qry.setParameter("keyReceiptNo", keyReceiptNo);
            return (KeyReceipt) qry.uniqueResult();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}