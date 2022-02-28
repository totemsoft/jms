package au.gov.qld.fire.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class IOUtils
{

    private static final Logger LOG = Logger.getLogger(IOUtils.class);

    public static InputStream toInputStream(String inputFile) throws IOException
	{
        InputStream feeder = new FileInputStream(new File(inputFile));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        copy(feeder, baos);
        feeder.close();
        return new ByteArrayInputStream(baos.toByteArray());
	}

    /**
     * Copy bytes from an InputStream to an OutputStream.
     * @param input the InputStream to read from
     * @param output the OutputStream to write to
     * @return the number of bytes copied, or -1 if > Integer.MAX_VALUE
     * @throws IOException
     */
    public static int copy(InputStream input, OutputStream output) throws IOException
    {
        return org.apache.commons.io.IOUtils.copy(input, output);
    }

    public static void copy(InputStream input, File outputFile) throws IOException
    {
        OutputStream output = new FileOutputStream(outputFile);
        try
        {
        	copy(input, output);
        }
        finally
        {
            closeQuietly(output);
        }
    }

	public static void copy(java.io.File inputFile, OutputStream output) throws IOException
	{
		InputStream input = new FileInputStream(inputFile);
        try
        {
        	copy(input, output);
        }
        finally
        {
            closeQuietly(input);
        }
	}

    public static void closeQuietly(InputStream intput)
    {
        try {
        	if (intput != null) intput.close();
        }
        catch (IOException ignore) {
        	LOG.warn(ignore.getMessage());
        }
    }

    public static void closeQuietly(OutputStream output)
    {
        try {
        	if (output != null) output.close();
        }
        catch (IOException ignore) {
        	LOG.warn(ignore.getMessage());
        }
    }

    public static void closeQuietly(ZipFile input)
    {
        try {
        	if (input != null) input.close();
        }
        catch (IOException ignore) {
        	LOG.warn(ignore.getMessage());
        }
    }

    public static void deleteFile(File file) throws IOException
    {
    	if (file != null && file.exists() && !file.delete()) {
    		throw new IOException("Failed to delete file: " + file);
    	}
    }

}