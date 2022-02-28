package au.gov.qld.fire.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * @author vchibaev (Valeri SHIBAEV)
 */
public class HandlerExceptionResolver extends SimpleMappingExceptionResolver {

    /** Used for logging. */
    private static final Logger LOG = Logger.getLogger(HandlerExceptionResolver.class);

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.handler.SimpleMappingExceptionResolver#doResolveException(
     * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
        HttpServletResponse response, Object handler, Exception e) {
        // will have "e" as "exception" (set by base class)
        ModelAndView model = super.doResolveException(request, response, handler, e);
        // get application exception (if any)
        Throwable appEx = null;
        if (e instanceof TransactionSystemException) {
            appEx = ((TransactionSystemException) e).getApplicationException();
        }
        if (appEx == null) {
            appEx = e; // if none application exception set
        }
        //if (ExceptionUtils.getRootCause(appEx) != null) {
        //    appEx = ExceptionUtils.getRootCause(appEx); // the root cause
        //}
//        if (appEx instanceof ValidationException) {
//            LOG.warn(e.getMessage());
//            model.addObject("errors", ((ValidationException) appEx).getErrors());
//            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
//        } else {
            LOG.error(e.getMessage(), e);
            model.addObject("name", appEx.getClass().getSimpleName());
            model.addObject("message", StringUtils.isNotBlank(appEx.getMessage()) ? appEx.getMessage() : appEx.getClass());
            model.addObject("stackTrace", ExceptionUtils.getStackTrace(appEx));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        }
        //
        return model;
    }

}