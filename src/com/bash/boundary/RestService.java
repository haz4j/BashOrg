package com.bash.boundary;

import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bash.control.MappingService;
import com.bash.entity.Approver;
import com.bash.entity.Quote;

@Path("quote")
public class RestService {
	
	@EJB
	MappingService service;
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.TEXT_XML)
	public Response getQuote(@PathParam("id") Long id) {
		return Response.ok(service.getQuote(id)).build();
	}
	
	@POST
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.TEXT_XML)
	public Response addQuote(@NotNull Quote quote) {
		return Response.ok(service.addQuote(quote).toString()).build();
	}
	
	@PUT
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.TEXT_XML)
	public Quote updateQuote(@NotNull Quote quote) {
		return service.updateQuote(quote);
	}
	
	@DELETE
	@Path("/delete/{id}")
	public void deleteQuote(@PathParam("id") Long id) {
		service.deleteQuote(id);
	}
	
	@GET
	@Path("voteup/{id}")
	@Produces(MediaType.TEXT_XML)
	public void voteUp(@PathParam("id") Long id) {
		service.voteUp(id);
	}
	
	@GET
	@Path("votedown/{id}")
	@Produces(MediaType.TEXT_XML)
	public void voteDown(@PathParam("id") Long id) {
		service.voteDown(id);
	}
	
}