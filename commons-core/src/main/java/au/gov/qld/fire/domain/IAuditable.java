package au.gov.qld.fire.domain;

import java.util.Date;

import au.gov.qld.fire.domain.security.User;

/**
 * Base interface for all auditable domain objects.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface IAuditable {

    /** IGNORE property(s) - !!! ALPHABETICAL ORDER !!!
        ignore these properties when copy object, eg BeanUtils */
    String[] IGNORE = new String[] {
        "createdBy", "createdDate", "updatedBy", "updatedDate", "version"
    };

    /** CREATED_BY property name */
    String CREATED_BY = "createdBy";

    /** CREATED_DATE property name */
    String CREATED_DATE = "createdDate";

    /** MODIFIED_BY property name */
    String MODIFIED_BY = "updatedBy";

    /** MODIFIED_DATE property name */
    String MODIFIED_DATE = "updatedDate";

    String WHERE = "LOGICALLY_DELETED IS NULL";

    Date getCreatedDate();

    User getCreatedBy();

    Date getUpdatedDate();

    User getUpdatedBy();

    long getLockVersion();

}