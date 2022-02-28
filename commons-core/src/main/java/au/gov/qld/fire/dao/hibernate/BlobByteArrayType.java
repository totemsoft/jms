package au.gov.qld.fire.dao.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

/**
 * This is a Hibernate mapping user type, which maps Database BLOBs to byte [].
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class BlobByteArrayType implements UserType
{
    
    private static LobHandler lobHandler;

    public static LobHandler getLobHandler()
    {
        if (lobHandler == null)
        {
            lobHandler = new DefaultLobHandler();
        }
        return lobHandler;
    }

    public int[] sqlTypes()
    {
        return new int[] {Types.BLOB};
    }

    public Class<?> returnedClass()
    {
        return byte[].class;
    }

    public boolean equals(Object x, Object y) throws HibernateException
    {
        return (x == y)
            || (x instanceof byte[] && y instanceof byte[] && Arrays.equals((byte[]) x, (byte[]) y));
    }

    public int hashCode(Object x) throws HibernateException
    {
        return x.hashCode();
    }

    public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
        throws HibernateException, SQLException
    {
        return getLobHandler().getBlobAsBytes(rs, names[0]);
    }
    public void nullSafeSet(PreparedStatement st, Object value, int index)
        throws HibernateException, SQLException
    {
        getLobHandler().getLobCreator().setBlobAsBytes(st, index, (byte[]) value);
    }

    public Object nullSafeGet(ResultSet rs, String[] names, org.hibernate.engine.spi.SessionImplementor si, Object owner)
        throws HibernateException, SQLException
    {
        return getLobHandler().getBlobAsBytes(rs, names[0]);
    }
    public void nullSafeSet(PreparedStatement st, Object value, int index, org.hibernate.engine.spi.SessionImplementor si)
        throws HibernateException, SQLException
    {
        getLobHandler().getLobCreator().setBlobAsBytes(st, index, (byte[]) value);
    }

    public Object deepCopy(Object value) throws HibernateException
    {
        return value;
    }

    public boolean isMutable()
    {
        return true;
    }

    public Serializable disassemble(Object value) throws HibernateException
    {
        return (Serializable) value;
    }

    public Object assemble(Serializable cached, Object owner) throws HibernateException
    {
        return cached;
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException
    {
        return original;
    }

}