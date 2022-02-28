package au.gov.qld.fire.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Component
public interface Pdf
{

    /**
     * FILLING OUT INTERACTIVE FORMS
     * http://itextpdf.com/book/chapter.php?id=8
     * http://examples.itextpdf.com/src/part2/chapter08/ReaderEnabledForm.java
     * @param template
     * @param params
     * @throws IOException
     */
    void read(InputStream template, Map<String, Object> params)
    	throws IOException;

    /**
     * @param template
     * @param dataMap - template parameters
     * @param dataStream - xml feed
     * @param output
     * @throws IOException
     */
    void write(byte[] template, Map<String, Object> dataMap, InputStream dataStream, OutputStream output)
        throws IOException;
    void write(InputStream template, Map<String, Object> dataMap, InputStream dataStream, OutputStream output)
        throws IOException;

}