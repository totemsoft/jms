package au.gov.qld.fire.service.impl;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import au.gov.qld.fire.service.PermissionService;
import au.gov.qld.fire.service.ServiceException;

/**
 * @author Valeri CHIBAEV (mailto:mail@apollosoft.net.au)
 */
public class PermissionServiceImpl implements PermissionService
{
    /** Logger */
    //private static final Logger LOG = Logger.getLogger(PermissionServiceImpl.class);

    /* (non-Javadoc)
     * @see PermissionService#findPermissions(java.util.Set)
     */
    public List<Permission> findPermissions(Set<String> groups) throws ServiceException
    {
        // TODO: implement
        List<Permission> result = new ArrayList<Permission>();
        return result;
    }

}