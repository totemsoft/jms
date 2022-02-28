package au.gov.qld.fire.security;

import java.io.Serializable;
import java.security.Principal;

import org.apache.commons.lang.StringUtils;

import au.gov.qld.fire.domain.security.SystemFunction;

/**
 * @author Valeri CHIBAEV (mailto:mail@apollosoft.net.au)
 */
public class GroupPrincipal extends AbstractName implements Principal, Serializable
{

    /** serialVersionUID */
    private static final long serialVersionUID = 99915605254897033L;

    /** systemFunctions */
    private SystemFunction[] systemFunctions = new SystemFunction[0];

    /**
     * 
     * @param groupName
     * @param systemFunctions
     */
    public GroupPrincipal(String groupName, SystemFunction[] systemFunctions)
    {
        super(groupName);
        this.systemFunctions = systemFunctions;
    }

    /**
     * @return the systemFunctions
     */
    public SystemFunction[] getSystemFunctions()
    {
        return systemFunctions;
    }

    /**
     * Helper method.
     * @param module
     * @return
     */
    public boolean hasSystemFunction(String module)
    {
        if (StringUtils.isBlank(module))
        {
            module = "/";
        }
        for (SystemFunction systemFunction : systemFunctions)
        {
            if (module.equals(systemFunction.getModule()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method.
     * @param module
     * @param path
     * @param query
     * @return
     */
    public boolean hasSystemFunction(String module, String path, String query)
    {
        return findSystemFunction(module, path, query) != null;
    }

    /**
     * Helper method.
     * @param module
     * @param path
     * @param query
     * @return
     */
    public SystemFunction findSystemFunction(String module, String path, String query)
    {
        if (StringUtils.isBlank(module)) {
            module = "/";
        }
        SystemFunction all = null;
        for (SystemFunction item : systemFunctions) {
            if (module.equals(item.getModule())) {
                if ("/".equals(item.getName())) {
                	all = item; // all items in this module
                	break;
                }
                if (item.getName().equals(path) || item.getName().startsWith(path)) {
                    if (StringUtils.isBlank(query)) {
                        return item;
                    }
                    if (StringUtils.isNotBlank(item.getQuery()) && query.equals(item.getQuery())) {
                        return item; // exact match (module, path and query)
                    }
                }
            }
        }
        return all;
    }

}