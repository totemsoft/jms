package au.gov.qld.fire.jms.web.module.setup;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;

import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class TemplateForm extends AbstractValidatorForm<Template>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -1051476496995433387L;

    private FormFile templateFile;

    private FormFile configFile;
    private Document config;

    private String htmlInput;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(Template entity)
    {
        super.setEntity(entity);
        //
        config = entity == null ? null : entity.getConfig();
        if (config == null) {
        	config = new Document();
        }
        // FCKEditor in IE failed to display uploaded content
        //this.htmlInput = null;
        //if (/*entity.getUploadDate() == null || */StringUtils.isBlank(entity.getLocation()))
        //{
            this.htmlInput = entity.getContent() != null && ContentTypeEnum.isHtml(entity.getContentType()) ? new String(entity.getContent()) : null;
        //}
    }

    /**
     * Convert byte[] to String for ckeditor
     * @return
     */
    public String getHtmlInput()
    {
        return htmlInput;
    }

    public void setHtmlInput(String htmlInput)
    {
        this.htmlInput = htmlInput;
    }

    /**
     * for file upload
     * @return the templateFile
     */
    public FormFile getTemplateFile()
    {
        return templateFile;
    }

    public void setTemplateFile(FormFile templateFile) throws Exception
    {
        this.templateFile = templateFile;
        if (templateFile != null && StringUtils.isNotBlank(templateFile.getFileName()))
        {
            getEntity().setContent(templateFile.getFileData());
            getEntity().setContentType(templateFile.getContentType());
        }
    }

    /**
     * for file upload
     * @return the configFile
     */
    public FormFile getConfigFile()
    {
        return configFile;
    }

    public void setConfigFile(FormFile configFile) throws Exception
    {
        this.configFile = configFile;
        if (configFile != null && StringUtils.isNotBlank(configFile.getFileName()))
        {
        	config.setName(configFile.getFileName());
        	//config.setDescription();
        	config.setContent(configFile.getFileData());
        	config.setContentType(configFile.getContentType());
        }
    }

	/**
	 * @return the config
	 */
	public Document getConfig()
	{
		return config;
	}

}