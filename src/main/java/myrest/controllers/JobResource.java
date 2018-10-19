package myrest.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import myrest.model.DAO;
import myrest.model.Job;

@Path("rest/jobs") //task лучше бы подошло
public class JobResource {
	
	/**
	 * Изменение работы
	 * 
	 * @param job
	 * @param Id
	 * @return
	 */
	@PUT
	@Path("/{id: [0-9]*}/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProject(Job j, @PathParam("id") int id) {
		if (j.getId() != id) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}else {	
			boolean ret = DAO.updateJob(j);			
			if (ret == false)
				return Response.status(Response.Status.BAD_REQUEST).build();
			else
				return Response.ok().build();
		}
	}
	
	/**
	 * Удаление работы
	 * 
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("/{id: [0-9]*}/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delProject(@PathParam("id") int id) {
		boolean ret = DAO.delJob(id);
		if (ret)
			return Response.ok().build();
		else
			return Response.status(Response.Status.BAD_REQUEST).build();
	}
	
	/**
	 * Получение всех просроченных работ
	 * 
	 * @return Jobs
	 */
	@GET
	@Path("/prosroch")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Job> getProjectsNerasp() {
		List<Job> ret = DAO.getProsrochJobs();
		return ret;
	}

}
