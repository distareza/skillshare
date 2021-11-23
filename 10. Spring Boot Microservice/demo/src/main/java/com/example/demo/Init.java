package com.example.demo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Service;

@Service
@Path("/")
public class Init {

	@GET
	@Produces("text/plain")
	public String init() {
		return "hello";
	}
	

}
