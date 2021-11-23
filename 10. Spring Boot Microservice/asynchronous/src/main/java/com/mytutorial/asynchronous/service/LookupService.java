package com.mytutorial.asynchronous.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mytutorial.asynchronous.model.User;

@Service
public class LookupService {
	
	private static final Logger logger = LoggerFactory.getLogger(LookupService.class);
	
	private static final String GITHUB_USERS_URL = "https://api.github.com/users/%s";
	
	private final RestTemplate restTemplate;

	public LookupService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	@Async
	public CompletableFuture<User> findUser(String user) throws InterruptedException {
		logger.info(String.format("Looking up %s", user));
		String url = String.format(GITHUB_USERS_URL, user);
		User result = restTemplate.getForObject(url, User.class);
		Thread.sleep(1000L);
		return CompletableFuture.completedFuture(result);
	}
	
}
