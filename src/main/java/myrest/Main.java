package myrest;

import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import myrest.controllers.JobResource;
import myrest.controllers.ProjectResource;
import myrest.model.DAO;

public class Main {

	public static void main(String[] args) {
		// hibernate		
		if (!DAO.init())
			return;				

		// jersey http://localhost:8080/rest
		URI baseUri = UriBuilder.fromUri("http://localhost/").port(8080).build();
		ResourceConfig config = new ResourceConfig(ProjectResource.class, JobResource.class);
		//HttpServer server = 
		JdkHttpServerFactory.createHttpServer(baseUri, config);
	}
}