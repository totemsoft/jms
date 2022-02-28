package au.gov.qld.fire.jms.web.taglib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import au.gov.qld.fire.util.Encoder;

import com.opensymphony.module.sitemesh.HTMLPage;

/**
 * Responsible for concatenation and compression of *.js and *.css resources found in html/head section
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Component
public final class HeadTag extends com.opensymphony.module.sitemesh.taglib.AbstractTag {

    /** serialVersionUID */

    private static final Logger LOG = Logger.getLogger(HeadTag.class);

    private static final String CRLF = System.getProperty("line.separator");

    private static String webappContext;

    private static String webappRoot;

    private static File cacheDir;

    private boolean cssConcatenate = true;

    private boolean jsConcatenate = true;

    private boolean cssMinify = false;

    private boolean jsMinify = false;

    private String[] cssList;
    
    private String[] jsList;

    /**
     * 
     * @param webappRoot
     * @throws IOException
     */
    private void setWebappRoot(String webappRoot) throws IOException {
        HeadTag.webappRoot = webappRoot;
        LOG.info("webappRoot=" + webappRoot);
        checkCacheDir();
    }

    /**
     * 
     * @throws IOException
     */
    private void checkCacheDir() throws IOException {
        if (StringUtils.isNotBlank(webappRoot) && cacheDir == null) {
            cacheDir = new File(webappRoot, "cache");
            LOG.info("cacheDir=" + cacheDir.getCanonicalPath());
            if ((!cacheDir.exists() || !cacheDir.isDirectory()) && !cacheDir.mkdir()) {
                throw new IOException("FAILED to create cache directory: " + cacheDir.getCanonicalPath());
            }
            deleteResources();
        }
    }

    /**
     * @param webappContext the webappContext to set
     */
    public void setWebappContext(String webappContext) {
        HeadTag.webappContext = webappContext;
    }

    /**
     * @param cssConcatenate the cssConcatenate to set
     */
    public void setCssConcatenate(boolean cssConcatenate) {
        this.cssConcatenate = cssConcatenate;
    }

    /**
     * @param jsConcatenate the jsConcatenate to set
     */
    public void setJsConcatenate(boolean jsConcatenate) {
        this.jsConcatenate = jsConcatenate;
    }

    /**
     * @param cssMinify the cssMinify to set
     */
    public void setCssMinify(boolean cssMinify) {
        this.cssMinify = cssMinify;
    }

    /**
     * @param jsMinify the jsMinify to set
     */
    public void setJsMinify(boolean jsMinify) {
        this.jsMinify = jsMinify;
    }

    /**
     * @param cssList the cssList to set
     */
    public void setCssList(String cssList) {
        this.cssList = StringUtils.split(cssList, ",");
    }

    /**
     * @param jsList the jsList to set
     */
    public void setJsList(String jsList) {
        this.jsList = StringUtils.split(jsList, ",");
    }

    /* (non-Javadoc)
     * @see com.opensymphony.module.sitemesh.taglib.AbstractTag#doEndTag()
     */
    @Override
    public final int doEndTag() throws JspException {
        HTMLPage htmlPage = (HTMLPage) getPage();
        try {
            if (StringUtils.isBlank(webappRoot)) {
                setWebappRoot(htmlPage.getRequest().getSession().getServletContext().getRealPath("/"));
            }
            if (cacheDir == null) {
                // no optimisation, eg "local" profile - no caching
                if (!ArrayUtils.isEmpty(cssList)) {
                    for (String resource : cssList) {
                        addResourceCss(getOut(), resource);
                    }
                }
                if (!ArrayUtils.isEmpty(jsList)) {
                    for (String resource : jsList) {
                        addResourceJs(getOut(), resource);
                    }
                }
                htmlPage.writeHead(getOut());
            } else {
                IOUtils.copy(new ByteArrayInputStream(optimiseHead(htmlPage.getHead().getBytes())), getOut());
            }
        }
        catch (IOException e) {
            throw new JspException("Error writing head element: " + e.toString(), e);
        }
        catch (Exception e) {
            throw new JspException(e.getMessage(), e);
        }
        return EVAL_PAGE;
    }

    /**
     * 
     * @param head
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException 
     */
    private byte[] optimiseHead(byte[] head) throws IOException, NoSuchAlgorithmException {
        assert cacheDir != null;
        // unique head id
        String id = new String(Encoder.base16(Encoder.digest(head)));
        // css file(s)
        String cssName = id + ".css";
        String printCssName = "print." + cssName;
        String cssMinifyName = id + "-min.css";
        File cssFile = new File(cacheDir, cssName);
        final boolean cssExists = cssFile.exists();
        if (cssConcatenate && !cssExists && !cssFile.createNewFile()) {
            throw new IOException("FAILED to create cssFile: " + cssFile.getCanonicalPath());
        }
        FileWriter cssWriter = !cssConcatenate || cssExists ? null : new FileWriter(cssFile);
        // js file(s)
        String jsName = id + ".js";
        String jsMinifyName = id + "-min.js";
        File jsFile = new File(cacheDir, jsName);
        final boolean jsExists = jsFile.exists();
        if (jsConcatenate && !jsExists && !jsFile.createNewFile()) {
            throw new IOException("FAILED to create jsFile: " + jsFile.getCanonicalPath());
        }
        FileWriter jsWriter = !jsConcatenate || jsExists ? null : new FileWriter(jsFile);
        //
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        LineNumberReader headReader = new LineNumberReader(new InputStreamReader(new ByteArrayInputStream(head)));
        try {
            //
            if (cssConcatenate) {
                addResourceCss(result, cacheDir.getName() + '/' + (cssMinify ? cssMinifyName : cssName));
            }
            if (jsConcatenate) {
                addResourceJs(result, cacheDir.getName() + '/' + (jsMinify ? jsMinifyName : jsName));
            }
            //
            List<String> lines = new ArrayList<String>();
            String line;
            while ((line = headReader.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0) {
                    continue; // skip blank lines
                }
                if (lines.contains(line)) {
                	continue; // skip duplicate lines
                }
            	lines.add(line);
                // css
                if (StringUtils.startsWithIgnoreCase(line, "<link") && StringUtils.containsIgnoreCase(line, "href=\"")) {
                    if (cssConcatenate) {
                        // skip print css for now
                        if (StringUtils.containsIgnoreCase(line, "media=\"print")) {
                            // TODO: add to printCssName
                            continue;
                        }
                        // add
                        result.write(("<!-- " + line + " -->" + CRLF).getBytes()); // logging resource
                        try {
                            addResource(cssWriter, getResource(line, "href=\""));
                        } catch (Exception e) {
                            result.write(("<!-- " + e.getMessage() + " -->" + CRLF).getBytes()); // logging error
                        }
                        continue;
                    }
                }
                // js
                if (StringUtils.startsWithIgnoreCase(line, "<script") && StringUtils.containsIgnoreCase(line, "src=\"")) {
                    if (jsConcatenate) {
                        result.write(("<!-- " + line + " -->" + CRLF).getBytes()); // logging resource
                        try {
                            addResource(jsWriter, getResource(line, "src=\""));
                        } catch (Exception e) {
                            result.write(("<!-- " + e.getMessage() + " -->" + CRLF).getBytes()); // logging error
                        }
                        continue;
                    }
                }
                // anything else
                result.write((line + CRLF).getBytes());
            }
            //
            // others css/js resources
            if (!ArrayUtils.isEmpty(cssList)) {
                for (String resource : cssList) {
                    if (cssConcatenate) {
                        result.write(("<!-- " + resource + " -->" + CRLF).getBytes()); // logging resource
                        try {
                            addResource(cssWriter, resource);
                        } catch (Exception e) {
                            result.write(("<!-- " + e.getMessage() + " -->").getBytes()); // logging error
                        }
                    } else {
                        addResourceCss(result, resource);
                    }
                }
            }
            if (!ArrayUtils.isEmpty(jsList)) {
                for (String resource : jsList) {
                    if (jsConcatenate) {
                        result.write(("<!-- " + resource + " -->" + CRLF).getBytes()); // logging resource
                        try {
                            addResource(jsWriter, resource);
                        } catch (Exception e) {
                            result.write(("<!-- " + e.getMessage() + " -->" + CRLF).getBytes()); // logging error
                        }
                    } else {
                        addResourceJs(result, resource);
                    }
                }
            }
        } finally {
            IOUtils.closeQuietly(headReader);
            //IOUtils.closeQuietly(cssWriter);
            if (cssWriter != null) {
                try {
                    cssWriter.close();
                } catch (Exception e) {
                    cssWriter = null;
                    LOG.error(e.getMessage(), e);
                    deleteResource(cssName);
                }
            }
            //IOUtils.closeQuietly(jsWriter);
            if (jsWriter != null) {
                try {
                    jsWriter.close();
                } catch (Exception e) {
                    jsWriter = null;
                    LOG.error(e.getMessage(), e);
                    deleteResource(jsName);
                }
            }
        }
        //
        if (cssMinify && cssWriter != null) {
            minify(cssFile, new File(cacheDir, cssMinifyName));
        }
        if (jsMinify && jsWriter != null) {
            minify(jsFile, new File(cacheDir, jsMinifyName));
        }
        //
        return result.toByteArray();
    }

    /**
     * 
     * @param line
     * @param open
     * @return
     */
    private String getResource(String line, String open) {
        String resource = StringUtils.substringBetween(line, open, "\"");
        if (resource == null) {
            resource = StringUtils.substringBetween(line, open.toUpperCase(), "\"");
        }
        if (resource.startsWith('/' + webappContext)) {
            resource = StringUtils.substringAfter(resource, '/' + webappContext);
        }
        return resource;
    }

    /**
     * 
     * @param writer
     * @param resource
     * @throws IOException
     */
    private void addResource(Writer writer, String resource) throws IOException {
        if (writer == null || StringUtils.isBlank(resource)) {
            return;
        }
        Reader reader = null;
        try {
            reader = new FileReader(new File(webappRoot, resource));
            writer.write(CRLF + "/* " + resource + " */" + CRLF); // safe comment for css and js files
            IOUtils.copy(reader, writer);
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    /**
     * 
     * @param result
     * @param resource
     * @throws IOException
     */
    private void addResourceCss(OutputStream result, String resource) throws IOException {
        result.write(("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + '/' + webappContext + '/' + resource + "\"/>" + CRLF).getBytes());
    }
    private void addResourceCss(Writer result, String resource) throws IOException {
        result.write(("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + '/' + webappContext + '/' + resource + "\"/>" + CRLF));
    }

    /**
     * 
     * @param result
     * @param resource
     * @throws IOException
     */
    private void addResourceJs(OutputStream result, String resource) throws IOException {
        result.write(("<script type=\"text/javascript\" src=\"" + '/' + webappContext + '/' + resource + "\"></script>" + CRLF).getBytes());
    }
    private void addResourceJs(Writer result, String resource) throws IOException {
        result.write(("<script type=\"text/javascript\" src=\"" + '/' + webappContext + '/' + resource + "\"></script>" + CRLF));
    }

    /**
     * http://developer.yahoo.com/yui/compressor/
     * @param inputFile
     * @param outputFile
     * @throws IOException
     */
    private void minify(File inputFile, File outputFile) throws IOException {
        if (!outputFile.exists() && !outputFile.createNewFile()) {
            throw new IOException("FAILED to create outputFile: " + outputFile.getCanonicalPath());
        }
        //com.yahoo.platform.yui.compressor.YUICompressor.main(new String[] {"-o", outputFile.getCanonicalPath(), inputFile.getCanonicalPath()});
    }

	/* (non-Javadoc)
     * @see au.com.macquarie.rmg.mro.service.ICacheService#deleteResource(java.lang.String)
     */
    public void deleteResource(String name) throws IOException {
        if (StringUtils.isBlank(name)) {
            deleteResources();
        } else {
            File file = new File(cacheDir, name);
            if (!file.exists()) {
                throw new IOException("Could not find resource: " + file.getCanonicalPath());
            }
            if (!file.delete()) {
                throw new IOException("Could not delete resource: " + file.getCanonicalPath());
            }
        }
    }

    /**
     * cleanup cacheDir
     * @throws IOException
     */
    private void deleteResources() throws IOException {
        for (File f : cacheDir.listFiles()) {
            if (f.exists() && !f.delete()) {
                throw new IOException("FAILED to delete file: " + f.getCanonicalPath());
            }
        }
    }

}