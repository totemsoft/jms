package au.gov.qld.fire.jms.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.service.ServiceException;

/**
 * oauth/?_wadl
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Path("/entity")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
@Service
public interface EntityServiceRS
{

    /**
     * oauth/entity/actionWorkflow/active/10001/1/2/true
     * @param actionWorkflowId
     * @param actionCodeId
     * @param outcomeId
     * @param value
     * @throws ServiceException
     */
    @GET
    @Path("/actionWorkflow/active/{actionWorkflowId}/{actionCodeId}/{outcomeId}/{value}")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Long active(@PathParam("actionWorkflowId") Long actionWorkflowId,
        @PathParam("actionCodeId") Long actionCodeId,
        @PathParam("outcomeId") Long outcomeId,
        @PathParam("value") boolean value)
        throws ServiceException;

}