package au.gov.qld.fire.domain.refdata;

import org.apache.commons.lang.StringUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class TemplateSearchCriteria
{

    private String templateTypes;

    /**
     * @return the templateTypes
     */
    public String getTemplateTypes()
    {
        if (StringUtils.isBlank(templateTypes))
        {
            templateTypes = null;
        }
        return templateTypes;
    }

    /**
     * @param templateTypes the templateTypes to set
     */
    public void setTemplateTypes(String templateTypes)
    {
        this.templateTypes = templateTypes;
    }

}