package au.gov.qld.fire.security;

import java.security.AccessController;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.Principal;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import au.gov.qld.fire.service.PermissionService;

/**
 * @author Valeri CHIBAEV (mailto:mail@apollosoft.net.au)
 */
public class SecurityPolicy extends Policy
{
    /** Logger */
    private static final Logger LOG = Logger.getLogger(SecurityPolicy.class);

    /** permissionService */
    private PermissionService permissionService;

    /**
     * @param permissionService the permissionService to set
     */
    public void setPermissionService(PermissionService permissionService)
    {
        this.permissionService = permissionService;
    }

    /* (non-Javadoc)
     * @see java.security.Policy#getPermissions(java.security.CodeSource)
     */
    @Override
    public PermissionCollection getPermissions(CodeSource codesource)
    {
        //others may add to this, so use heterogeneous Permissions
        final Permissions result = new Permissions();
        result.add(new AllPermission());
        return result;
    }

    /* (non-Javadoc)
     * @see java.security.Policy#getPermissions(java.security.ProtectionDomain)
     */
    @Override
    public PermissionCollection getPermissions(ProtectionDomain domain)
    {
        final Permissions result = new Permissions();
        // Look up permissions
        final Set<String> groups = new HashSet<String>();
        Principal[] principals = domain.getPrincipals();
        if (principals != null && principals.length > 0)
        {
            for (Principal principal : principals)
            {
                if (principal instanceof GroupPrincipal)
                {
                    GroupPrincipal groupPrincipal = (GroupPrincipal) principal;
                    groups.add(groupPrincipal.getName());
                }
            }
            if (!groups.isEmpty())
            {
                try
                {
                    List<Permission> permissions = (List<Permission>) AccessController
                        .doPrivileged(new PrivilegedExceptionAction<List<Permission>>()
                        {
                            public List<Permission> run() throws Exception
                            {
                                return permissionService.findPermissions(groups);
                            }
                        });

                    for (Permission permission : permissions)
                    {
                        result.add(permission);
                    }
                }
                catch (PrivilegedActionException e)
                {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
        else if (domain.getCodeSource() != null)
        {
            PermissionCollection codeSrcPerms = getPermissions(domain.getCodeSource());
            for (Enumeration<Permission> en = codeSrcPerms.elements(); en.hasMoreElements();)
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
        PermissionCollection permissions = getPermissions(domain);
        return permissions.implies(permission);
    }

    /* (non-Javadoc)
     * @see java.security.Policy#refresh()
     */
    @Override
    public void refresh()
    {
        //does nothing
    }

}
