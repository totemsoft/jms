package au.gov.qld.fire.web;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.util.IOUtils;
import au.gov.qld.fire.web.module.Link;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class WebUtils
{

    /** logger. */
    //private static final Logger LOG = Logger.getLogger(WebUtils.class);

    public static String getWebappRoot(HttpServletRequest request)
    {
    	return request.getSession().getServletContext().getRealPath("/");
    }

    /**
     * 
     * @param session
     * @return
     */
    public static User getUser(HttpSession session)
    {
        return (User) session.getAttribute(SessionConstants.USER);
    }

    /**
     * 
     * @param request
     * @return
     */
    public static final User getUser(HttpServletRequest request)
    {
        return getUser(request.getSession());
    }

    /**
     * 
     * @param request
     * @param user
     */
    public static final void setUser(HttpSession session, User user)
    {
        session.setAttribute(SessionConstants.USER, user);
    }

    /**
     * 
     * @param session
     * @return
     */
    public static Subject getSubject(HttpSession session)
    {
        return (Subject) session.getAttribute(SessionConstants.SUBJECT_KEY);
    }

    /**
     * 
     * @param request
     * @return
     */
    public static final Subject getSubject(HttpServletRequest request)
    {
        return getSubject(request.getSession());
    }

    /**
     * 
     * @param request
     * @param subject
     */
    public static final void setSubject(HttpSession session, Subject subject)
    {
        session.setAttribute(SessionConstants.SUBJECT_KEY, subject);
    }

    /**
     * 
     * @param session
     * @return
     */
    public static LoginContext getLoginContext(HttpSession session)
    {
        return session == null ? null : (LoginContext) session.getAttribute(SessionConstants.LOGIN_CONTEXT_KEY);
    }

    /**
     * 
     * @param session
     * @param loginContext
     */
    public static void setLoginContext(HttpSession session, LoginContext loginContext)
    {
        session.setAttribute(SessionConstants.LOGIN_CONTEXT_KEY, loginContext);
    }

    /**
     * 
     * @param servletContext
     * @return
     */
    public static WebApplicationContext getApplicationContext(ServletContext servletContext)
    {
        return WebApplicationContextUtils.getWebApplicationContext(servletContext);
    }

    /**
     *
     * @param servletContext
     * @param name Spring bean name.
     * @return Spring bean instance.
     */
    public static Object getBean(ServletContext servletContext, String name)
    {
        return getApplicationContext(servletContext).getBean(name);
    }
    public static <T> T getBean(ServletContext servletContext, String name, Class<T> requiredType)
    {
        return getApplicationContext(servletContext).getBean(name, requiredType);
    }

    /**
     *
     * @param request
     * @return
     */
    public static Integer getPropogationBehavior(HttpServletRequest request)
    {
        String value = request.getParameter(SessionConstants.PROPOGATION_BEHAVIOR);
        return StringUtils.isBlank(value) ? null : Integer.valueOf(value);
    }

    /**
     * Default readOnly = true
     * @param request
     * @return
     */
    public static boolean isReadOnly(HttpServletRequest request)
    {
        String value = request.getParameter(SessionConstants.READ_ONLY);
        return StringUtils.isBlank(value) || Boolean.valueOf(value).booleanValue();
    }

    public static Boolean getBoolean(HttpServletRequest request, String name)
    {
        String s = request.getParameter(name);
        return StringUtils.isBlank(s) ? null : Boolean.valueOf(s);
    }

    /**
     *
     * @param request
     * @param name
     * @return
     */
    public static final Long getLongId(HttpServletRequest request, String name)
    {
        try
        {
            Long id = Long.valueOf(request.getParameter(name));
            return id == null || id == 0L ? null : id;
        }
        catch (Exception ignore)
        {
            return null;
        }
    }

    /**
     *
     * @param request
     * @param name
     * @return
     */
    private static final String getStringId(HttpServletRequest request, String name)
    {
        String id = request.getParameter(name);
        return StringUtils.isBlank(id) ? null : id;
    }

    /**
     *
     * @param request
     * @param name
     * @return
     */
    private static final int getIntId(HttpServletRequest request, String name)
    {
        try
        {
            Integer index = Integer.valueOf(request.getParameter(name));
            return index == null ? -1 : index;
        }
        catch (Exception ignore)
        {
            return -1;
        }
    }

    /**
     * @param request
     * @return
     */
    public static final Long getLongId(HttpServletRequest request)
    {
        return getLongId(request, SessionConstants.ID);
    }

    /**
     * @param request
     * @return
     */
    public static final String getStringId(HttpServletRequest request)
    {
        return getStringId(request, SessionConstants.ID);
    }

    /**
     * @param request
     * @return
     */
    public static String getStringValue(HttpServletRequest request)
    {
        return request.getParameter(SessionConstants.VALUE);
	}

    /**
     * @param request
     * @return
     */
    public static final int getIntId(HttpServletRequest request)
    {
        return getIntId(request, SessionConstants.ID);
    }

    /**
     * @param request
     * @return
     */
    public static final int getIndex(HttpServletRequest request)
    {
        return getIntId(request, SessionConstants.INDEX);
    }

    /**
     * @param request
     * @return
     */
    public static Boolean getCompleted(HttpServletRequest request)
    {
        // ?completed=false
        return getBoolean(request, SessionConstants.COMPLETED);
    }

    /**
     * @param request
     * @return
     */
    public static final int getYear(HttpServletRequest request)
    {
        String sYear = request.getParameter(SessionConstants.YEAR);
        if (StringUtils.isBlank(sYear))
        {
            sYear = (String) request.getSession().getAttribute(SessionConstants.YEAR);
        }
        else
        {
            request.getSession().setAttribute(SessionConstants.YEAR, sYear);
        }
        try
        {
            return Integer.parseInt(sYear);
        }
        catch (Exception ignore)
        {
            return -1;
        }
    }

    /**
     * @param request
     * @return
     */
    public static final int getMonth(HttpServletRequest request)
    {
        String sMonth = request.getParameter(SessionConstants.MONTH);
        if (StringUtils.isBlank(sMonth))
        {
            sMonth = (String) request.getSession().getAttribute(SessionConstants.MONTH);
        }
        else
        {
            request.getSession().setAttribute(SessionConstants.MONTH, sMonth);
        }
        try
        {
            return Integer.parseInt(sMonth);
        }
        catch (Exception ignore)
        {
            return -1;
        }
    }

    /**
     * @param request
     * @return
     */
    public static final Long getActionId(HttpServletRequest request)
    {
        return getLongId(request, SessionConstants.ACTION_ID);
    }

    /**
     * @param request
     * @return
     */
    public static Long getActionTypeId(HttpServletRequest request)
    {
        return getLongId(request, SessionConstants.ACTION_TYPE_ID);
    }

    /**
     * @param request
     * @return
     */
    public static final Long getDocumentId(HttpServletRequest request)
    {
        return getLongId(request, SessionConstants.DOCUMENT_ID);
    }

    /**
     * @param request
     * @return
     */
    public static final Long getFileId(HttpServletRequest request)
    {
        return getLongId(request, SessionConstants.FILE_ID);
    }

    /**
     * @param request
     * @return
     */
    public static final Long getJobTypeId(HttpServletRequest request)
    {
        return getLongId(request, SessionConstants.JOB_TYPE_ID);
    }

    /**
     * @param request
     * @return
     */
    public static Long getOwnerTypeId(HttpServletRequest request)
    {
        return getLongId(request, SessionConstants.OWNER_TYPE_ID);
    }

    /**
     * @param request
     * @return
     */
    public static final Long getReportId(HttpServletRequest request)
    {
        return getLongId(request, SessionConstants.REPORT_ID);
    }

    /**
     * @param session
     * @return
     */
    public static final String getStateDefault(HttpSession session)
    {
        return (String) session.getAttribute(SessionConstants.STATE_DEFAULT);
    }

    /**
     * @param session
     * @param state
     * @return
     */
    public static final void setStateDefault(HttpSession session, String state)
    {
        session.setAttribute(SessionConstants.STATE_DEFAULT, state);
    }

    /**
     * @param request
     * @return
     */
    public static final Long getSupplierId(HttpServletRequest request)
    {
        return getLongId(request, SessionConstants.SUPPLIER_ID);
    }

    /**
     * @param request
     * @return
     */
    public static final Long getUserId(HttpServletRequest request)
    {
        return getLongId(request, SessionConstants.USER_ID);
    }

    /**
     * @param request
     * @return
     */
    public static Long getWorkGroupId(HttpServletRequest request)
    {
        return getLongId(request, SessionConstants.WORK_GROUP_ID);
    }

    /**
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Link> getLinks(HttpServletRequest request)
    {
        return (List<Link>) request//.getSession(false)
            .getAttribute(SessionConstants.LINKS);
    }

    /**
     *
     * @param request
     * @param values
     */
    public static void setLinks(HttpServletRequest request, List<Link> values)
    {
        request//.getSession(false)
            .setAttribute(SessionConstants.LINKS, values);
    }

    /**
     *
     * @param request
     * @param values
     */
    public static void setLinks(HttpServletRequest request, Link[] values)
    {
        request//.getSession(false)
            .setAttribute(SessionConstants.LINKS, values);
    }

    /**
     *
     * @param response
     * @param content
     * @param contentType
     * @param contentName
     * @throws Exception
     */
    public static void downloadContent(HttpServletResponse response,
    	byte[] content, String contentType, String contentName) throws Exception
    {
        ByteArrayOutputStream contentStream = new ByteArrayOutputStream();
        if (content != null) {
            contentStream.write(content);
        }
        downloadContent(response, contentStream, contentType, contentName);
    }

    public static void downloadContent(HttpServletResponse response,
        String pathname) throws Exception
    {
    	java.io.File file = new java.io.File(pathname);
    	String name = file.getName();
		String ext = name.substring(name.lastIndexOf('.') + 1);
		ContentTypeEnum contentType = ContentTypeEnum.findByExt(ext);
		ByteArrayOutputStream contentStream = new ByteArrayOutputStream();
		IOUtils.copy(file, contentStream);
        downloadContent(response, contentStream, contentType.getContentType(), name);
    }

    /**
     *
     * @param response
     * @param contentStream
     * @param contentType
     * @param contentName
     * @throws Exception
     */
    public static void downloadContent(HttpServletResponse response,
        ByteArrayOutputStream contentStream, String contentType, String contentName)
        throws Exception
    {
        //set expires in 1 second
        response.setDateHeader("Expires", System.currentTimeMillis() + 1 * 1000L);
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setHeader("content-disposition", "inline; filename=\"" + contentName + "\"");
        response.setContentType(contentType);
        response.setContentLength(contentStream.size());
        contentStream.writeTo(response.getOutputStream());
        response.getOutputStream().flush();
    }

}