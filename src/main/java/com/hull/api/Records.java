package com.hull.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("record")
public class Records {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getRecords() {
		return "Records\n";
	}
}
