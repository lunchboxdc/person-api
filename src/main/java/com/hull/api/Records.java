package com.hull.api;

import com.hull.domain.Person;
import com.hull.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/records")
@Service
public class Records {

	@Autowired
	private PersonService personService;

	@GET
	@Path("/gender")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSortedByGender() {
		List<Person> people = personService.getAllSortedByGender();
		return Response.status(200).entity(people).build();
	}

	@GET
	@Path("/birthdate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSortedByBirthDate() {
		List<Person> people = personService.getAllSortedByBirthDate();
		return Response.status(200).entity(people).build();
	}

	@GET
	@Path("/name")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSortedByLastName() {
		List<Person> people = personService.getAllSortedByLastName();
		return Response.status(200).entity(people).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postPerson(Person person) {
		personService.addPerson(person);
		return Response.status(200).build();
	}
}
