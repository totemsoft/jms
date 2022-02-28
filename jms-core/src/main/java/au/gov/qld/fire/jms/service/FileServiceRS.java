package au.gov.qld.fire.jms.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.domain.LabelValues;
import au.gov.qld.fire.jms.domain.refdata.OwnerTypeEnum;
import au.gov.qld.fire.service.ServiceException;

/**
 * oauth/?_wadl
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Path("/file")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
@Service
public interface FileServiceRS
{

    /**
     * oauth/file/defaultOwnerType/10001/2
     * @param fileId
     * @param value - file.defaultOwnerTypeId
     * @throws ServiceException
     */
    @GET
    @Path("/defaultOwnerType/{fileId}/{ownerTypeId}")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Long defaultOwnerType(@PathParam("fileId") Long fileId,
        @PathParam("ownerTypeId") OwnerTypeEnum ownerType)
        throws ServiceException;

    /**
     * oauth/file/mailMethod/10001/2
     * @param fileId
     * @param value - file.mailMethodId
     * @throws ServiceException
     */
    @GET
    @Path("/mailMethod/{fileId}/{value}")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Integer mailMethod(@PathParam("fileId") Long fileId,
        @PathParam("value") Integer value)
        throws ServiceException;

    /**
     * oauth/file/noMailOut/10001/true
     * @param fileId
     * @param value - file.noMailOut
     * @throws ServiceException
     */
    @GET
    @Path("/noMailOut/{fileId}/{value}")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    boolean noMailOut(@PathParam("fileId") Long fileId,
        @PathParam("value") boolean value)
        throws ServiceException;

    /**
     * oauth/file/mailSent/10001
     * @param fileId
     * @throws ServiceException
     */
    @GET
    @Path("/mailSent/{fileId}")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Long mailSent(@PathParam("fileId") Long fileId)
        throws ServiceException;

    /**
     * oauth/file/mailReceived/10001
     * @param fileId
     * @throws ServiceException
     */
    @GET
    @Path("/mailReceived/{fileId}")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Long mailReceived(@PathParam("fileId") Long fileId)
        throws ServiceException;

    /**
     * oauth/file/findOwnerLegalName?ownerTypeId=1&query=a
     * CXF JAXRS checks for static fromString(String s) method, so types with no valueOf(String) factory methods can also be dealt with (http://cxf.apache.org/docs/jax-rs-basics.html)
     * @param ownerTypeId
     * @param legalName
     * @throws ServiceException
     */
    @GET
    @Path("/findOwnerLegalName")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    LabelValues findOwnerLegalName(
        @QueryParam("ownerTypeId") OwnerTypeEnum ownerType,
        @QueryParam("query") String legalName)
        throws ServiceException;

    /**
     * oauth/file/findOwnerContactName?ownerTypeId=1&query=a
     * CXF JAXRS checks for static fromString(String s) method, so types with no valueOf(String) factory methods can also be dealt with (http://cxf.apache.org/docs/jax-rs-basics.html)
     * @param ownerTypeId
     * @param contactName
     * @throws ServiceException
     */
    @GET
    @Path("/findOwnerContactName")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    LabelValues findOwnerContactName(
        @QueryParam("ownerTypeId") OwnerTypeEnum ownerType,
        @QueryParam("query") String contactName)
        throws ServiceException;

    /**
     * oauth/file/aseKeyContactName?query=a
     * @param contactName
     * @throws ServiceException
     */
    @GET
    @Path("/aseKeyContactName")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    LabelValues aseKeyContactName(
        @QueryParam("query") String contactName)
        throws ServiceException;

    /**
     * oauth/file/aseKeyOrderContactName?query=a
     * @param contactName
     * @throws ServiceException
     */
    @GET
    @Path("/aseKeyOrderContactName")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    LabelValues aseKeyOrderContactName(
        @QueryParam("query") String contactName)
        throws ServiceException;

    /**
     * oauth/file/fcaDoc/updateName/10001
     * @param fileId
     * @throws ServiceException
     */
    @GET
    @Path("/fcaDoc/updateName/{fileId}")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    String updateName(@PathParam("fileId") Long fileId,
        @QueryParam("oldName") String oldName,
        @QueryParam("newName") String newName)
        throws ServiceException;

    @POST
    @Path("/fcaDoc/uploadFile/{fileId}")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.TEXT_PLAIN})
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    String uploadFile(@PathParam("fileId") Long fileId,
        @QueryParam("dir") String dir,
        List<Attachment> attachments)
        throws ServiceException;

}