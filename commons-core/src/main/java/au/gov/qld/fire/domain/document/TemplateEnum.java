package au.gov.qld.fire.domain.document;

import au.gov.qld.fire.domain.refdata.TemplateTypeEnum;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum TemplateEnum
{

    EMAIL_CHANGE_PASSWORD(TemplateTypeEnum.EMAIL, "changePassword",
        "/au/gov/qld/fire/jms/template/email/changePassword.xml"),
    EMAIL_NEW_USER(TemplateTypeEnum.EMAIL, "newUser",
        "/au/gov/qld/fire/jms/template/email/newUser.xml"),
    EMAIL_ASE_CHANGE(TemplateTypeEnum.EMAIL, "aseChange",
        "/au/gov/qld/fire/jms/template/email/aseChange.xml"),
    EMAIL_REJECT_JOB_REQUEST(TemplateTypeEnum.EMAIL, "rejectJobRequest",
        "/au/gov/qld/fire/jms/template/email/rejectJobRequest.xml"),
    EMAIL_COMPLETED_ACTIONS(TemplateTypeEnum.EMAIL, "completedActions",
        "/au/gov/qld/fire/jms/template/email/completedActions.vm"),

    REPORT_ASE_CHANGE(TemplateTypeEnum.REPORT_FOP, "R001",
        "/au/gov/qld/fire/jms/template/report/R001.xslt"),

    EXPORT_INVOICE(TemplateTypeEnum.EXPORT, "exportInvoice",
        null),
    ;

    /** Logger */
    //private static final Logger LOG = Logger.getLogger(TemplateEnum.class);

    private TemplateTypeEnum templateType;

    private String code;

    private String path;

    /**
     * Ctor.
     * @param value
     * @param code
     * @param path
     */
    private TemplateEnum(TemplateTypeEnum templateType, String code, String path)
    {
        this.templateType = templateType;
        this.code = code;
        this.path = path;
    }

    /**
     * @return the code
     */
    public TemplateTypeEnum getTemplateType()
    {
        return templateType;
    }

    /**
     * @return the code
     */
    public String getCode()
    {
        return code;
    }

    /**
     * @return the path
     */
    public String getPath()
    {
        return path;
    }

}