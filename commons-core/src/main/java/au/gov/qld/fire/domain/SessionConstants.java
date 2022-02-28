package au.gov.qld.fire.domain;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface SessionConstants
{

    // ====================================================================== //

    /** SUBJECT_KEY */
    String SUBJECT_KEY = Subject.class.getName();

    /** LOGIN_CONTEXT_KEY */
    String LOGIN_CONTEXT_KEY = LoginContext.class.getName();

    /** USER */
    String USER = "loggedUser";//User.class.getName();

    /** DATE_PATTERN_KEY */
    String DATE_PATTERN_KEY = "datePattern";

    /** DATETIME_PATTERN_KEY */
    String DATETIME_PATTERN_KEY = "dateTimePattern";

    /** TIME_PATTERN_KEY */
    String TIME_PATTERN_KEY = "timePattern";

}