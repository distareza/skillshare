package com.mytutorial.springbootroutefilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.mytutorial.springbootroutefilter.filter.ErrorFilter;
import com.mytutorial.springbootroutefilter.filter.PostFilter;
import com.mytutorial.springbootroutefilter.filter.PreFilter;
import com.mytutorial.springbootroutefilter.filter.RouteFilter;

@SpringBootApplication
@EnableZuulProxy
public class SpringbootroutefilterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootroutefilterApplication.class, args);
	}
	
	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}

	@Bean
	public RouteFilter routeFilter() {
		return new RouteFilter();
	}
	
	@Bean
	public PostFilter postFilter() {
		return new PostFilter();
	}
	
	@Bean
	public ErrorFilter errorFilter() {
		return new ErrorFilter();
	}
	
}
