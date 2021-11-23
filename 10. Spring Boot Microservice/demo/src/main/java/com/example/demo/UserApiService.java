package com.example.demo;

import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

@Service
@Path("/user")
public class UserApiService {

	@GET
	@Path("/{user}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("user") String name) {	
		System.out.println("get employee of " + name);
		
		String userName = String.format("%s%s", name.substring(0, 1).toUpperCase(), name.substring(1).toLowerCase());
		String blog = String.format("https://www.%s.com", name.toLowerCase());
		String type = (new Random().nextInt(9999999) % 2 ==0)? "Organization" : "User";
		String url = String.format("https://api.github.com/users/%s", name.toLowerCase());
		
		return new User(userName, blog, type, url);		
	}
	
}
