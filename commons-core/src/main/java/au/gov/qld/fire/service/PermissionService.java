package au.gov.qld.fire.service;

import java.security.Permission;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;


/**
 * @author Valeri CHIBAEV (mailto:mail@apollosoft.net.au)
 */
@Service
public interface PermissionService
{
    /**
     * 
     * @param groups
     * @return
     * @throws ServiceException
     */
    List<Permission> findPermissions(Set<String> groups) throws ServiceException;

}