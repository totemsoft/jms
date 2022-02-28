package au.gov.qld.fire.aspect;

import javax.inject.Inject;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.service.UserService;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.util.ThreadLocalUtils;

/**
 * Aspect to inject System user into ThreadLocal
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Aspect
public class SystemAspect
{

	@Inject private UserService userService;

//    @SuppressWarnings("unused")
//    @Pointcut("execution(public * *(..))")
//    private void anyPublicMethod() {}
//
//    @SuppressWarnings("unused")
//    @Pointcut("within(au.gov.qld.fire..*)")
//    private void inService() {}

    @Before("@annotation(systemAccess)")
    public void before(SystemAccess systemAccess) throws Throwable
    {
        boolean noUser = ThreadLocalUtils.getUser() == null;
        if (noUser)
        {
            User user = userService.findSystemUser();
            ThreadLocalUtils.setUser(user);
            ThreadLocalUtils.setDate(DateUtils.getCurrentDateTime());
        }
    }

    @After("@annotation(systemAccess)")
    public void after(SystemAccess systemAccess) throws Throwable
    {
        User user = ThreadLocalUtils.getUser();
        if (user == null || user.isSystem())
        {
            ThreadLocalUtils.clear();
        }
    }

}