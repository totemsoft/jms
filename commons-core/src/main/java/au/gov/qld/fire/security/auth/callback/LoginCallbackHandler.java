package au.gov.qld.fire.security.auth.callback;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 * @author Valeri CHIBAEV (mailto:mail@apollosoft.net.au)
 */
public class LoginCallbackHandler implements CallbackHandler
{

    /** Login name */
    private String name;

    /** Login password */
    private char[] password;

    /**
     * 
     * @param applicationContext
     * @param name
     * @param password
     */
    public LoginCallbackHandler(String name, char[] password)
    {
        this.name = name;
        this.password = password;
    }

    /* (non-Javadoc)
     * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
     */
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException
    {
        for (int i = 0; i < callbacks.length; i++)
        {
            Callback callback = callbacks[i];
            if (callback instanceof NameCallback)
            {
                NameCallback nameCallback = (NameCallback) callback;
                nameCallback.setName(name);
            }
            else if (callback instanceof PasswordCallback)
            {
                PasswordCallback passwordCallback = (PasswordCallback) callback;
                passwordCallback.setPassword(password);
            }
        }
    }

}