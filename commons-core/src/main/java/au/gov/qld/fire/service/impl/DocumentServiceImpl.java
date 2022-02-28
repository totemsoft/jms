package au.gov.qld.fire.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.LoopTool;

import au.gov.qld.fire.dao.DocumentDao;
import au.gov.qld.fire.dao.ReportDao;
import au.gov.qld.fire.dao.ReportDocDao;
import au.gov.qld.fire.domain.BaseModel;
import au.gov.qld.fire.domain.ConvertUtils;
import au.gov.qld.fire.domain.IBase;
import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.refdata.TemplateTypeEnum;
import au.gov.qld.fire.domain.report.ReportDoc;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.service.DocumentService;
import au.gov.qld.fire.service.Pdf;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.util.IOUtils;
import au.gov.qld.fire.util.ThreadLocalUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class DocumentServiceImpl implements DocumentService
{

    /** logger. */
    private static final Logger LOG = Logger.getLogger(DocumentServiceImpl.class);

    /** parameter delimiter */
    private static final String DELIM_START = "%[";
    private static final String DELIM_END = "]%";

    /** parameter delimiter regex */
    private static final String DELIM_START_REGEX = "\\%\\[";
    private static final String DELIM_END_REGEX = "\\]\\%";

    @Inject private DocumentDao documentDao;

    @Inject private ReportDao reportDao;

    @Inject private ReportDocDao reportDocDao;

    private VelocityEngine ve;

    private Pdf pdf;
    protected final boolean pdfRequreXml;

    public DocumentServiceImpl()
    {
        InputStream input = null;
        try {
            input = getClass().getClassLoader().getResourceAsStream("au/gov/qld/fire/spring/velocity.properties");
            Properties p = new Properties();
            p.load(input);
            ve = new VelocityEngine(p);
            ve.init();
        }
        catch (Exception silent) {
        	LOG.error(silent.getMessage(), silent); // will never happen
        }
        finally {
            IOUtils.closeQuietly(input);
        }
        //
        pdfRequreXml = false;
        pdf = new PdfIText(); // free ($2,000 license for commercial distribution)
        //pdf = new PdfBox(); // free
        //pdf = new PdfJt();  // $5,000 license
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.DocumentService#updateParameters(java.lang.String, au.gov.qld.fire.jms.domain.security.User)
     */
    public String updateParameters(String text, User user)
    {
        return updateParameters(text, user, null, null);
    }

    /**
     * 
     * @param text
     * @param user
     * @param model eg file instance
     * @param modelName eg "file."
     * @return
     */
    protected String updateParameters(String text, User user, BaseModel<?> model, String modelName)
    {
        JXPathContext userCtx = JXPathContext.newContext(user);
        JXPathContext modelCtx = model == null ? null : JXPathContext.newContext(model);

        //parameter replacement
        int from;
        int fromOffset = DELIM_START.length();
        int to;
        //int toOffset = DELIM_END.length();
        while ((from = text.lastIndexOf(DELIM_START)) < (to = text.lastIndexOf(DELIM_END)))
        {
            final String param = text.substring(from + fromOffset, to);
            String value;
            Object paramValue;
            try
            {
                //get value from user
                if (param.startsWith("user."))
                {
                    String name = param.substring(5);
                    value = BeanUtils.getProperty(user, name);
                    paramValue = userCtx.getValue(name.replace('.', '/'));
                }
                //get value from model object
                else if (modelName != null && param.startsWith(modelName))
                {
                    final String name = param.substring(modelName.length());
                    //special handling List/Array parameter file.afterHoursBuildingContacts[0].buildingContactType.name
                    if (model != null)
                    {
                        if (name.contains("[") && name.contains("]"))
                        {
                            int idx1 = name.indexOf('[');
                            int idx2 = name.indexOf(']');
                            String indexedPropertyName = name.substring(0, idx1);
                            int index = Integer.parseInt(name.substring(idx1 + 1, idx2));
                            Object indexedProperty = BeanUtilsBean.getInstance().getPropertyUtils()
                                .getIndexedProperty(model, indexedPropertyName, index);
                            value = BeanUtils.getProperty(indexedProperty, name.substring(idx2 + 2)); // ].prop1
                            //paramValue = value;
                            //FIXME: does not like array [] ???
                            paramValue = modelCtx == null ? param : modelCtx.getValue(name.replace('.', '/'));
                        }
                        else
                        {
                            value = BeanUtils.getProperty(model, name);
                            paramValue = modelCtx == null ? param : modelCtx.getValue(name.replace('.', '/'));
                        }
                    }
                    else
                    {
                        value = null;
                        paramValue = modelCtx == null ? param : modelCtx.getValue(name.replace('.', '/'));
                    }
                }
                //fixed/hard-coded parameters
                else if (param.equalsIgnoreCase("date"))
                {
                    value = formatDate(ThreadLocalUtils.getDate());
                    paramValue = param;
                }
                else if (param.equalsIgnoreCase("time"))
                {
                    value = formatTime(ThreadLocalUtils.getDate());
                    paramValue = param;
                }
                //incorrect/not found parameters
                else
                {
                    value = ">>>[" + param + "]<<<";
                    paramValue = ">>>[" + param + "]<<<";
                }

                if (value == null)
                {
                    value = param;
                    paramValue = param;
                    if (LOG.isDebugEnabled())
                        LOG.debug("Parameter [" + param + "] not set.");
                }
                //format date/time
                else if (paramValue instanceof Date)
                {
                    if (param.indexOf("date") >= 0)
                    {
                        value = formatDate((Date) paramValue);
                    }
                    else if (param.indexOf("time") >= 0)
                    {
                        value = formatTime((Date) paramValue);
                    }
                }
            }
            catch (Exception e)
            {
                value = param;
                paramValue = param;
                LOG.warn("Parameter [" + StringUtils.abbreviate(param, 100) + "] not found. " + e.getMessage());
            }

            //has to be executed to avoid infinite loop
            try
            {
                String paramRegex = param;
                String replacement = value;
                //An array index [0..n] may be included as a literal
                if (paramRegex.indexOf('[') >= 0)
                {
                    paramRegex = paramRegex.replace("[", "\\[");
                }
                if (paramRegex.indexOf(']') >= 0)
                {
                    paramRegex = paramRegex.replace("]", "\\]");
                }
                //A dollar sign ($) may be included as a literal in the replacement string by preceding it with a backslash (\$).
                if (replacement.indexOf('$') >= 0)
                {
                    replacement = replacement.replace("$", "\\$");
                }
                text = text.replaceAll(DELIM_START_REGEX + paramRegex + DELIM_END_REGEX, replacement);
            }
            catch (RuntimeException e)
            {
                LOG.error("Failed to replaceAll: [" + StringUtils.abbreviate(param, 100) + "] with value [" + value + "].");
                throw e;
            }
        }
        return text;
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.DocumentService#getReportParameters(au.gov.qld.fire.jms.domain.document.Template)
     */
    public String[] getReportParameters(Template reportTemplate) throws ServiceException
    {
        try
        {
            String queryName = reportTemplate.getCode();
            String sqlQuery = reportTemplate.getSqlQuery();
            return reportDao.getNamedParameters(sqlQuery == null ? queryName : sqlQuery);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.DocumentService#findDocumentById(java.lang.Long)
     */
    public Document findDocumentById(Long documentId) throws ServiceException
    {
        try
        {
            return documentDao.findById(documentId);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.DocumentService#saveReportDoc(au.gov.qld.fire.jms.domain.report.ReportDoc)
     */
    public void saveReportDoc(ReportDoc reportDoc) throws ServiceException
    {
        try
        {
            //find by id
            Long id = reportDoc.getId();
            ReportDoc entity = null;
            if (id != null)
            {
                entity = reportDocDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = reportDoc;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(reportDoc, entity);
            }

            //optional

            //save
            reportDocDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.DocumentService#findReportDocs(au.gov.qld.fire.jms.domain.document.Template)
     */
    public List<ReportDoc> findReportDocs(Template template) throws ServiceException
    {
        try
        {
            User user = ThreadLocalUtils.getUser();
            return reportDocDao.findByTemplateUser(template, user);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.DocumentService#findReportDocById(java.lang.Long)
     */
    public ReportDoc findReportDocById(Long reportDocId) throws ServiceException
    {
        try
        {
            return reportDocDao.findById(reportDocId);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.DocumentService#deleteReportDoc(au.gov.qld.fire.jms.domain.report.ReportDoc)
     */
    public void deleteReportDoc(ReportDoc reportDoc) throws ServiceException
    {
        try
        {
        	reportDoc = reportDocDao.findById(reportDoc.getId());
            reportDocDao.delete(reportDoc);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.service.DocumentService#readDocument(au.gov.qld.fire.domain.refdata.TemplateTypeEnum, java.io.InputStream, java.util.Map)
	 */
	public void readDocument(TemplateTypeEnum templateType, InputStream template, Map<String, Object> params)
		throws ServiceException
	{
        try
        {
    		if (TemplateTypeEnum.PDF_FORM.equals(templateType)) {
    			pdf.read(template, params);
    		} else {
    			throw new ServiceException("Unhandled templateType: " + templateType);
    		}
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.DocumentService#writeDocument(au.gov.qld.fire.domain.refdata.TemplateTypeEnum, java.io.InputStream, java.io.InputStream, java.io.OutputStream)
	 */
	public void writeDocument(TemplateTypeEnum templateType, InputStream templateStream, Map<String, Object> dataMap, InputStream dataStream, OutputStream output)
		throws ServiceException
	{
        try {
    		if (TemplateTypeEnum.EMAIL.equals(templateType)) {
    			fromEmailTemplate(templateStream, dataMap, output);
    		} else if (TemplateTypeEnum.PDF_FORM.equals(templateType)) {
    			pdf.write(org.apache.commons.io.IOUtils.toByteArray(templateStream), dataMap, dataStream, output);
    		} else {
    			throw new ServiceException("Unhandled templateType: " + templateType);
    		}
        }
        catch (ServiceException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ServiceException(e);
        }
	}

	private void fromEmailTemplate(InputStream templateStream, Map<String, Object> params, OutputStream output) throws IOException
	{
        VelocityContext context = new VelocityContext();
        for (Map.Entry<String, Object> entry : params.entrySet())
        {
            context.put(entry.getKey(), entry.getValue());
        }
        context.put("dateTool", new DateTool());
        context.put("listTool", new LoopTool());
        context.put("datePattern", DateUtils.DD_MM_YYYY.toPattern());
        // generate document based on template
        User user = ThreadLocalUtils.getUser();
        String logTag = user.getLogin() + ".log";
        StringWriter writer = new StringWriter();
        BufferedReader reader = new BufferedReader(new InputStreamReader(templateStream));
        if (!ve.evaluate(context, writer, logTag, reader))
        {
            throw new ServiceException("see Velocity runtime log for more details: " + logTag);
        }
        output.write(writer.getBuffer().toString().getBytes());
	}

	protected void fromPdfTemplate(Template template, Object contextBean, InputStream dataStream, OutputStream output) throws Exception
	{
    	Map<String, Object> params = new LinkedHashMap<String, Object>();
    	// context bean
    	if (contextBean != null) {
        	params.put(IBase.CONTEXT_BEAN, contextBean);
    	}
    	// config field(s)
    	Document config = template.getConfig();
    	if (config != null) {
    		Properties props = new Properties();
    		props.load(new ByteArrayInputStream(config.getContent()));
    		for (Entry<Object, Object> entry : props.entrySet()) {
    			params.put(entry.getKey().toString(), entry.getValue());
    		}
    	}
    	//
    	pdf.write(template.getContent(), params, dataStream, output);
	}

	/**
     * 
     * @param value
     * @return
     */
    private String formatDate(Date value)
    {
        return DateUtils.DD_MMMMM_YYYY.format(value);
    }

    /**
     * 
     * @param value
     * @return
     */
    private String formatTime(Date value)
    {
        return DateUtils.HH_mm.format(value);
    }

}