package au.gov.qld.fire.security;

import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Perform security checks in the sensitive parts of your
 * code, such as the code that retrieves or updates sensitive data.
 *
 * @author Valeri CHIBAEV (mailto:mail@apollosoft.net.au)
 */
public class CompositePolicy extends Policy
{
    private List<Policy> policies;

    /**
     * @param policies
     */
    public CompositePolicy(List<Policy> policies)
    {
        this.policies = new ArrayList<Policy>(policies);
    }

    /* (non-Javadoc)
     * @see java.security.Policy#getPermissions(java.security.CodeSource)
     */
    @Override
    public PermissionCollection getPermissions(CodeSource codesource)
    {
        final Permissions result = new Permissions();
        for (Policy policy : policies)
        {
            PermissionCollection permissions = policy.getPermissions(codesource);
            for (Enumeration<Permission> en = permissions.elements(); en.hasMoreElements(); )
            {
                result.add(en.nextElement());
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see java.security.Policy#getPermissions(java.security.ProtectionDomain)
     */
    @Override
    public PermissionCollection getPermissions(ProtectionDomain domain)
    {
        final Permissions result = new Permissions();
        for (Policy policy : policies)
        {
            PermissionCollection permissions = policy.getPermissions(domain);
            for (Enumeration<Permission> en = permissions.elements(); en.hasMoreElements(); )
            {
                result.add(en.nextElement());
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see java.security.Policy#implies(java.security.ProtectionDomain, java.security.Permission)
     */
    @Override
    public boolean implies(ProtectionDomain domain, Permission permission)
    {
        for (Policy policy : policies)
        {
            if (policy.implies(domain, permission))
            {
                return true;
            }
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.security.Policy#refresh()
     */
    @Override
    public void refresh()
    {
        for (Policy policy : policies)
        {
            policy.refresh();
        }
    }

}
