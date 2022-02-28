package au.gov.qld.fire.jms.util;

import au.gov.qld.fire.util.Encoder;
import junit.framework.TestCase;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class EncoderTest extends TestCase
{

    /** logger. */
    //private static final Logger LOG = Logger.getLogger(EncoderTest.class);

    /**
     * Test method for {@link au.gov.qld.fire.util.Encoder#digest(java.lang.String)}.
     */
    public final void testDigest()
    {
        String value = "";
        String result = Encoder.digest(value.getBytes());
        assertEquals("D41D8CD98F00B204E9800998ECF8427E", result);
        value = "Passw0rd";
        result = Encoder.digest(value.getBytes());
        assertEquals("D41E98D1EAFA6D6011D3A70F1A5B92F0", result);
        value = "password";
        result = Encoder.digest(value.getBytes());
        assertEquals("5F4DCC3B5AA765D61D8327DEB882CF99", result);
    }

    /**
     * Test method for {@link au.gov.qld.fire.util.Encoder#digest(byte[], java.lang.String)}.
     */
    public final void testDigestWithAlgorithm()
    {
        //fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.gov.qld.fire.util.Encoder#encodeHex(byte[])}.
     */
    public final void testEncodeHex()
    {
        //fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.gov.qld.fire.util.Encoder#base16(byte[])}.
     */
    public final void testBase16ByteArray()
    {
        //fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.gov.qld.fire.util.Encoder#base16(java.lang.String)}.
     */
    public final void testBase16String()
    {
        //fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.gov.qld.fire.util.Encoder#base64(byte[])}.
     */
    public final void testBase64ByteArray()
    {
        //fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.gov.qld.fire.util.Encoder#base64(java.lang.String)}.
     */
    public final void testBase64String()
    {
        //fail("Not yet implemented"); // TODO
    }

}