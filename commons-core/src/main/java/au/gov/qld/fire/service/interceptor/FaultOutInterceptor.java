package au.gov.qld.fire.service.interceptor;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FaultOutInterceptor extends AbstractPhaseInterceptor<Message> {

    //@Resource private MessageContext jaxrsContext;

    public FaultOutInterceptor() {
        super(Phase.PRE_STREAM);
    }

    public FaultOutInterceptor(String phase) {
        super(Phase.MARSHAL);
    } 

    /* (non-Javadoc)
     * @see org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message.Message)
     */
    public void handleMessage(Message message) throws Fault {
        Exception ex = message.getContent(Exception.class);
        if (ex == null) {
            throw new RuntimeException("Exception is expected");
        }
        if (!(ex instanceof Fault)) {
            throw new RuntimeException("Fault is expected");
        }
        // deal with the actual exception : fault.getCause()
        HttpServletResponse response = (HttpServletResponse) message.getExchange()
            .getInMessage().get(AbstractHTTPDestination.HTTP_RESPONSE);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        try {
        	String msg = ExceptionUtils.getRootCauseMessage(ex);
        	if (msg == null) {
        		msg = ex.getMessage();
        	}
        	if (msg == null) {
        		msg = ExceptionUtils.getStackTrace(ex);
        	}
            response.getOutputStream().write(msg.getBytes());
            response.getOutputStream().flush();
            message.getInterceptorChain().abort();           
        } catch (Exception e) {
            throw new RuntimeException("Error writing the response");
        }
    }

}