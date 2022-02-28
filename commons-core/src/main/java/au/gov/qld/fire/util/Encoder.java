package au.gov.qld.fire.util;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public final class Encoder
{

    /** MD5 algorithm. */
    public static final String MD5 = "MD5";

    /** HEXSTRING. */
    private static final String HEXSTRING = "0123456789ABCDEF";

    /** HEXCHAR. */
    private static final char[] HEXCHAR = HEXSTRING.toCharArray();

    /** Mapping table from 6-bit nibbles to Base64 characters. */
    private static final char[] MAP_NIBBLES_CHAR = new char[64];
    static
    {
        int i = 0;
        for (char c = 'A'; c <= 'Z'; c++)
        {
            MAP_NIBBLES_CHAR[i++] = c;
        }
        for (char c = 'a'; c <= 'z'; c++)
        {
            MAP_NIBBLES_CHAR[i++] = c;
        }
        for (char c = '0'; c <= '9'; c++)
        {
            MAP_NIBBLES_CHAR[i++] = c;
        }
        MAP_NIBBLES_CHAR[i++] = '+';
        MAP_NIBBLES_CHAR[i++] = '/';
    }

    /** Mapping table from Base64 characters to 6-bit nibbles. */
    private static final byte[] MAP_CHAR_NIBBLES = new byte[128];
    static
    {
        for (int i = 0; i < MAP_CHAR_NIBBLES.length; i++)
        {
            MAP_CHAR_NIBBLES[i] = -1;
        }
        for (int i = 0; i < 64; i++)
        {
            MAP_CHAR_NIBBLES[MAP_NIBBLES_CHAR[i]] = (byte) i;
        }
    }

    /** Hide ctor. */
    private Encoder()
    {
        super();
    }

    /**
     * 
     * @param input
     * @return
     */
    public static String digest(byte[] input)
    {
        try
        {
            return digest(input, MD5);
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * @param input
     * @param algorithm
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String digest(byte[] input, String algorithm) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] output = md.digest(input);
        md.reset();
        return encodeHex(output);
    }

    /**
     * Converts a byte array to hex string.
     * @param block
     * @return
     */
    public static String encodeHex(byte[] block)
    {
        if (block == null)
        {
            return null;
        }

        int len = block.length;
        StringBuffer buf = new StringBuffer(len * 2);
        for (int i = 0; i < len; i++)
        {
            byte2hex(block[i], buf);
        }
        return buf.toString();
    }

    /**
     * Converts a byte to hex digit and writes to the supplied buffer
     * @param b
     * @param buf
     */
    private static void byte2hex(byte b, StringBuffer buf)
    {
        int high = (b & 0xf0) >> 4;
        int low = b & 0x0f;
        buf.append(HEXCHAR[high]);
        buf.append(HEXCHAR[low]);
    }

    /**
     * Encodes a byte array into Base16 format.
     * @param in - the byte array to be transformed.
     * @return the hexadecimal string, or null.
     */
    public static String base16(byte[] in)
    {
        if (in == null)
        {
            return null;
        }

        int len = in.length;
        StringBuffer buf = new StringBuffer(len * 2);
        for (int i = 0; i < len; i++)
        {
            byte b = in[i];
            int high = (b & 0xF0) >> 4;
            int low = b & 0x0F;
            buf.append(HEXCHAR[high]);
            buf.append(HEXCHAR[low]);
        }
        return buf.toString();
    }

    /**
     * Decodes Base16 data.
     * @param in - a hexadecimal string to be transformed.
     * @return the byte array, or null if the passed String is null.
     * @throws IllegalArgumentException if the passed String is not haxadecimal encoded.
     */
    public static byte[] base16(String in) throws IllegalArgumentException
    {
        if (in == null)
        {
            return null;
        }
        if ((in.length() % 2) != 0)
        {
            throw new IllegalArgumentException(
                "Length of Base16 encoded input string is not a multiple of 2.");
        }

        int length = in.length() / 2;
        byte[] result = new byte[length];
        String h = in.toUpperCase();
        for (int i = 0; i < length; i++)
        {
            char c = h.charAt(2 * i);
            int index = HEXSTRING.indexOf(c);
            if (index == -1)
            {
                throw new IllegalArgumentException("Illegal character in Base16 encoded data.");
            }

            int j = 16 * index;
            c = h.charAt((2 * i) + 1);
            index = HEXSTRING.indexOf(c);
            if (index == -1)
            {
                throw new IllegalArgumentException("Illegal character in Base16 encoded data.");
            }

            j += index;

            result[i] = (byte) (j & 0xFF);
        }
        return result;
    }

    /**
    * Encodes a byte array into Base64 format.
    * see http://www.source-code.biz/snippets/java/2.htm
    * No blanks or line breaks are inserted.
    * @param in An array containing the data bytes to be encoded.
    * @return A character array with the Base64 encoded data.
    */
    public static String base64(byte[] in)
    {
        if (in == null || in.length == 0)
        {
            return null;
        }

        int iLen = in.length;
        // output length without padding
        int oDataLen = (iLen * 4 + 2) / 3;
        // output length including padding
        final int oLen = ((iLen + 2) / 3) * 4;
        StringBuffer buf = new StringBuffer(oLen);
        int ip = 0;
        int op = 0;
        while (ip < iLen)
        {
            int i0 = in[ip++] & 0xff;
            int i1 = ip < iLen ? in[ip++] & 0xFF : 0;
            int i2 = ip < iLen ? in[ip++] & 0xFF : 0;
            int o0 = i0 >>> 2;
            int o1 = ((i0 & 3) << 4) | (i1 >>> 4);
            int o2 = ((i1 & 0xF) << 2) | (i2 >>> 6);
            int o3 = i2 & 0x3F;
            buf.append(MAP_NIBBLES_CHAR[o0]);
            op++;
            buf.append(MAP_NIBBLES_CHAR[o1]);
            op++;
            buf.append(op < oDataLen ? MAP_NIBBLES_CHAR[o2] : '=');
            op++;
            buf.append(op < oDataLen ? MAP_NIBBLES_CHAR[o3] : '=');
            op++;
        }
        return buf.toString();
    }

    /**
    * Decodes Base64 data.
    * see http://www.source-code.biz/snippets/java/2.htm
    * No blanks or line breaks are allowed within the Base64 encoded data.
    * @param in A character array containing the Base64 encoded data.
    * @return An array containing the decoded data bytes.
    * @throws IllegalArgumentException if the input is not valid Base64 encoded data.
    */
    public static byte[] base64(String in) throws IllegalArgumentException
    {
        if (in == null)
        {
            return null;
        }

        byte[] bytes = in.getBytes();
        int iLen = bytes.length;
        if (iLen % 4 != 0)
        {
            throw new IllegalArgumentException(
                "Length of Base64 encoded input string is not a multiple of 4.");
        }
        while (iLen > 0 && bytes[iLen - 1] == '=')
        {
            iLen--;
        }
        final int oLen = (iLen * 3) / 4;
        byte[] out = new byte[oLen];
        int ip = 0;
        int op = 0;
        while (ip < iLen)
        {
            int i0 = bytes[ip++];
            int i1 = bytes[ip++];
            int i2 = ip < iLen ? bytes[ip++] : 'A';
            int i3 = ip < iLen ? bytes[ip++] : 'A';
            if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127)
            {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            int b0 = MAP_CHAR_NIBBLES[i0];
            int b1 = MAP_CHAR_NIBBLES[i1];
            int b2 = MAP_CHAR_NIBBLES[i2];
            int b3 = MAP_CHAR_NIBBLES[i3];
            if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0)
            {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            int o0 = (b0 << 2) | (b1 >>> 4);
            int o1 = ((b1 & 0xF) << 4) | (b2 >>> 2);
            int o2 = ((b2 & 3) << 6) | b3;
            out[op++] = (byte) o0;
            if (op < oLen)
            {
                out[op++] = (byte) o1;
            }
            if (op < oLen)
            {
                out[op++] = (byte) o2;
            }
        }
        return out;
    }

    public static String encode(String s) {
    	if (StringUtils.isBlank(s)) {
    		return null;
    	}
        try {
            Class<?> encryptionClass = Class.forName("org.jboss.resource.security.SecureIdentityLoginModule");
            // private static String encode(String secret)
            final Method encodeMethod = encryptionClass.getDeclaredMethod("encode", String.class);
            encodeMethod.setAccessible(true);
            return (String) encodeMethod.invoke(null, s);
        } catch (Exception e) {
        	Throwable t = ExceptionUtils.getCause(e);
            throw new RuntimeException(t == null ? e.getMessage() : t.getMessage());
        }
    }

    public static String decode(String s) {
    	if (StringUtils.isBlank(s)) {
    		return null;
    	}
        try {
            Class<?> encryptionClass = Class.forName("org.jboss.resource.security.SecureIdentityLoginModule");
            // private static char[] decode(String secret)
            final Method decodeMethod = encryptionClass.getDeclaredMethod("decode", String.class);
            decodeMethod.setAccessible(true);
            return new String((char[]) decodeMethod.invoke(null, s));
        } catch (Exception e) {
        	Throwable t = ExceptionUtils.getCause(e);
            throw new RuntimeException(t == null ? e.getMessage() : t.getMessage());
        }
    }

}