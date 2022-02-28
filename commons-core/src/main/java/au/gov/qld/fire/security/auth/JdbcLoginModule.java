package au.gov.qld.fire.security.auth;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import au.gov.qld.fire.IBeanNames;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.security.GroupPrincipal;
import au.gov.qld.fire.security.UserCredential;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.service.UserService;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Component
public class JdbcLoginModule implements LoginModule
{

    /** Logger */
    private static final Logger LOG = Logger.getLogger(JdbcLoginModule.class);

    /** applicationContext */
    private static ApplicationContext applicationContext;

    /** Login subject */
    private Subject subject;

    /** Login callbackHandler */
    private CallbackHandler callbackHandler;

    /** sharedState */
    //private Map<String, ?> sharedState;
    /** sharedState */
    //private Map<String, ?> options;

    /** login */
    private String login;

    /** password */
    private char[] password;

    /**
     * @param applicationContext the applicationContext to set
     */
    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext)
    {
    	JdbcLoginModule.applicationContext = applicationContext;
    }

    /* (non-Javadoc)
     * @see javax.security.auth.spi.LoginModule#abort()
     */
    public boolean abort() throws LoginException
    {
        cleanup();
        return true;
    }

    /* (non-Javadoc)
     * @see javax.security.auth.spi.LoginModule#commit()
     */
    public boolean commit() throws LoginException
    {
        //If an error occurs in commit(), a LoginException may be thrown.
        //If this LoginModule should be ignored, as with the login() method,
        //commit() should return false.

        //If this method is called, the user successfully authenticated,
        //and we can add the appropriate Principles to the Subject.
        try
        {
            GroupPrincipal group = getUserService().findUserGroup(login);
            subject.getPrincipals().add(group);

            //get all user security details and save into UserCredential
            User user = getUserService().findByLogin(login);
            UserCredential cred = new UserCredential(login, user.getId());
            subject.getPublicCredentials().add(cred);

            return true;
        }
        catch (ServiceException e)
        {
            LOG.error(e.getMessage(), e);
            LoginException ex = new LoginException(e.getMessage());
            ex.initCause(e);
            throw ex;
        }
        finally
        {
            //either way, cleanup
            cleanup();
        }
    }

    /* (non-Javadoc)
     * @see javax.security.auth.spi.LoginModule#initialize(javax.security.auth.Subject, javax.security.auth.callback.CallbackHandler, java.util.Map, java.util.Map)
     */
    public void initialize(Subject subject, CallbackHandler callbackHandler,
        Map<String, ?> sharedState, Map<String, ?> options)
    {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        //this.sharedState = sharedState;
        //this.options = options;
    }

    /* (non-Javadoc)
     * @see javax.security.auth.spi.LoginModule#login()
     */
    public boolean login() throws LoginException
    {
        //Each callback is responsible for collecting a credential needed to authenticate the user.
        NameCallback nameCallback = new NameCallback("username");
        PasswordCallback passwordCallback = new PasswordCallback("password", false);
        //Delegate to the provided CallbackHandler to gather the username and password.
        try
        {
            callbackHandler.handle(new Callback[]
            {
                nameCallback, passwordCallback,
            });

            //Now that the CallbackHandler has gathered the username and password,
            //use them to authenticate the user against the expected passwords.
            login = nameCallback.getName();
            password = passwordCallback.getPassword();

            getUserService().checkLogin(login, password);
            return true;
        }
        catch (LoginException e)
        {
            //LOG.error(e.getMessage(), e);
            throw e;
        }
        catch (IOException e)
        {
            LOG.error(e.getMessage(), e);
            LoginException ex = new LoginException("IOException logging in: " + e.getMessage());
            ex.initCause(e);
            throw ex;
        }
        catch (UnsupportedCallbackException e)
        {
            LOG.error(e.getMessage(), e);
            LoginException ex = new LoginException(e.getCallback().getClass().getName()
                + " is not a supported Callback: " + e.getMessage());
            ex.initCause(e);
            throw ex;
        }
    }

    /* (non-Javadoc)
     * @see javax.security.auth.spi.LoginModule#logout()
     */
    public boolean logout() throws LoginException
    {
        if (subject.isReadOnly())
        {
            return false;
        }

        Set<GroupPrincipal> principals = subject.getPrincipals(GroupPrincipal.class);
        subject.getPrincipals().removeAll(principals);
        Set<UserCredential> publicCredentials = subject.getPublicCredentials(UserCredential.class);
        subject.getPublicCredentials().removeAll(publicCredentials);

        return true;
    }

    /**
     * Helper method to clear secure data.
     */
    private void cleanup()
    {
        login = null;
        if (password != null)
        {
            Arrays.fill(password, (char) 0);
            password = null;
        }
        callbackHandler = null;
    }

    /**
     * 
     * @return
     */
    private UserService getUserService()
    {
        return (UserService) applicationContext.getBean(IBeanNames.USER_SERVICE);
    }

}