package com.wordpress.martinsdeveloperworld.boundary;

import com.wordpress.martinsdeveloperworld.control.Controller;
import com.wordpress.martinsdeveloperworld.entity.AuditLog;
import com.wordpress.martinsdeveloperworld.entity.Person;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("rest")
@Produces("text/json")
public class RestResource {
	@Inject
	private Controller controller;

	@GET
	@Path("persist")
	public Person persist() {
		return controller.persist();
	}

	@GET
	@Path("list")
	public List<Person> list() {
		return controller.list();
	}

	@GET
	@Path("listAudits")
	public List<AuditLog> listAudits() {
		return controller.listAudits();
	}
}
