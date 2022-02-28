package au.gov.qld.fire.service.interceptor;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.cxf.interceptor.AbstractOutDatabindingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;

import au.gov.qld.fire.util.ThreadLocalUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SecurityOutDatabindingInterceptor extends AbstractOutDatabindingInterceptor {

	//public static final String HEADER_PRINCIPAL = "userId";

	//@Context private MessageContext context;

    //@Autowired private com.xcelerate.elixir.service.SecurityManager securityManager;

	//private ResourceBundle b;

	public SecurityOutDatabindingInterceptor() {
        super(Phase.MARSHAL);
    }

    /* (non-Javadoc)
     * @see org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message.Message)
     */
    @SuppressWarnings("unchecked")
    public void handleMessage(Message message) throws Fault {
        Map<String, List<String>> headers = (Map<String, List<String>>) message.get(Message.PROTOCOL_HEADERS);
        if (headers == null) {
            headers = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);
            message.put(Message.PROTOCOL_HEADERS, headers);
        }
        
//        // This is set by CXF
//        //AuthorizationPolicy policy = message.get(AuthorizationPolicy.class);
//        User user = ThreadLocalUtils.getUser();
//        if (user == null) {
//        	org.apache.cxf.common.i18n.Message fault = new org.apache.cxf.common.i18n.Message("user.notfound", b);
//        	throw new Fault(fault);
//        }
//        String userId = user.getLogin();
//        headers.put(HEADER_PRINCIPAL, Arrays.asList(userId));
//        // modify headers
//        Exchange e = message.getExchange();
//        e.put(HEADER_PRINCIPAL, userId);
//        //Session s = e.getSession();
        ThreadLocalUtils.clear();
    }

}