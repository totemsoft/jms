package au.gov.qld.fire.domain;

/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface IBase<T> {

    /** ID property name */
    String ID = "id";

    /** LOGICALLY_DELETED property name */
    String LOGICALLY_DELETED = "logicallyDeleted";

    /** IGNORE property(s) */
    String[] IGNORE = new String[] {};

    /** context bean */
    String CONTEXT_BEAN = "bean";

    /**
     * @return
     */
    T getId();

}