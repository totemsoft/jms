package au.gov.qld.fire.service.interceptor;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.cxf.interceptor.AbstractInDatabindingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

import au.gov.qld.fire.domain.SessionConstants;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.util.ThreadLocalUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SecurityInDatabindingInterceptor extends AbstractInDatabindingInterceptor {

	//public static final String HEADER_PRINCIPAL = "userId";

	//@Context private MessageContext context;

    public SecurityInDatabindingInterceptor() {
        super(Phase.UNMARSHAL);
    }

    /* (non-Javadoc)
     * @see org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message.Message)
     */
//    @SuppressWarnings("unchecked")
    public void handleMessage(Message message) throws Fault {
//        Map<String, List<String>> headers = (Map<String, List<String>>) message.get(Message.PROTOCOL_HEADERS);
//        if (headers == null) {
//            headers = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);
//            message.put(Message.PROTOCOL_HEADERS, headers);
//        }
        // modify headers
        //Exchange e = message.getExchange();
        //String userId = (String) e.get(HEADER_PRINCIPAL);

        HttpServletRequest httpRequest = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        HttpSession session = httpRequest.getSession(false);
        //HttpServletRequest httpRequest = context.getHttpServletRequest();
        //HttpServletResponse httpResponse = context.getHttpServletResponse();
        Subject subject = (Subject) session.getAttribute(SessionConstants.SUBJECT_KEY);
        // eg /jms/login.do
        if (subject == null || session == null || !httpRequest.isRequestedSessionIdValid()) {
            return; // throw ???
        }
        // get/set user in http session
        User user = (User) session.getAttribute(SessionConstants.USER);
        if (user == null) {
            user = new User(subject);
            session.setAttribute(SessionConstants.USER, user);
        }
        ThreadLocalUtils.setUser(user);
        ThreadLocalUtils.setDate(DateUtils.getCurrentDateTime());
    }

}