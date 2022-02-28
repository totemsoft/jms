package au.gov.qld.fire.domain.refdata;

/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum TaskStatusEnum {

    N(null, "None"),
    P("P", "Processing"),
    S("S", "Success"),
    F("F", "Failure"),
    ;

    private final String code;

    private final String description;

    private TaskStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return description;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return
     */
    public String getDescription() {
        return description;
    }

}