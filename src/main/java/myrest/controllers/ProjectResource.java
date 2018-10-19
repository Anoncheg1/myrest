package myrest.controllers;

import java.util.List;

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

import myrest.model.DAO;
import myrest.model.Job;
import myrest.model.Project;

@Path("rest/projects")
public class ProjectResource {
	
	/**
	 * Получение всех проектов
	 * 
	 * @return Projects
	 */
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> getProjects() {
		List<Project> ret = DAO.getProjects();
		return ret;
	}

	/**
	 * Получение всех работ требуемого проекта
	 * 
	 * @param printerId
	 * @return Jobs
	 */
	@GET
	@Path("/{id: [0-9]*}/jobs")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Job> getProjectJobs(@PathParam("id") String id) {
		List<Job> ret = DAO.getProjectJobs(Integer.parseInt(id));
		return ret;
	}

	
	/**
	 * Создание проекта
	 * 
	 * @param project
	 * @return
	 */
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProject(Project p) {
		Project project = new Project(p.getName(), p.getFb(), p.getRb());
		if (DAO.createProject(project) == true)
			return Response.ok().build();
		else
			return Response.status(Response.Status.BAD_REQUEST).build();
	}

	/**
	 * Получение требуемого проекта
	 * 
	 * @return Project
	 */
	@GET
	@Path("/{id: [0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Project getProject(@PathParam("id") int id) {
		Project ret = DAO.getProject(id);
		return ret;
	}

	/**
	 * Изменение проекта
	 * 
	 * @param project
	 * @param id
	 * @return
	 */
	@PUT
	@Path("/{id: [0-9]*}/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProject(Project p, @PathParam("id") int id) {
		if (p.getId() != id) {			
			return Response.status(Response.Status.BAD_REQUEST).build();
		}else {	
			boolean ret = DAO.updateProject(p);			
			if (ret == false)
				return Response.status(Response.Status.BAD_REQUEST).build();
			else
				return Response.ok().build();
		}
	}
	
	/**
	 * Удаление проекта
	 * 
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("/{id: [0-9]*}/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delProject(@PathParam("id") int id) {
		boolean ret = DAO.delProject(id);
		if (ret)
			return Response.ok().build();
		else
			return Response.status(Response.Status.BAD_REQUEST).build();
	}
	
	/**
	 * Получение всех проектов с нераспределенным бюджетом
	 * 
	 * @return
	 */
	@GET
	@Path("/nerasp")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> getProjectsNerasp() {
		List<Project> ret = DAO.getProjectsNerasp();
		return ret;
	}
	
	/**
	 * Создание работы
	 * 
	 * @param job
	 * @param idProject
	 * @return
	 */
	@POST
	@Path("/{id: [0-9]*}/jobs/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createJob(Job j, @PathParam("id") int idProject) {
		Job job = new Job(idProject, j.getName(),j.getFb(),j.getRb(),j.getNach(),j.getDliteln());
		if (DAO.addJob(job) == true)
			return Response.ok().build();
		else
			return Response.status(Response.Status.BAD_REQUEST).build();
	}
}