package com.wordpress.martinsdeveloperworld.boundary;

import com.wordpress.martinsdeveloperworld.control.Controller;
import com.wordpress.martinsdeveloperworld.entity.Person;

import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import java.io.Serializable;
import java.util.List;

@Path("rest")
@Produces("text/json")
@SessionScoped
public class RestResource implements Serializable {
	@Inject
	private Controller controller;

	@GET
	@Path("persist")
	public Person persist(@Context HttpServletRequest request) {
		return controller.persist();
	}

	@GET
	@Path("list")
	public List<Person> list() {
		return controller.list();
	}

	@GET
	@Path("commit")
	public void commit() {
		controller.commit();
	}

	@PreDestroy
	public void preDestroy() {

	}
}
