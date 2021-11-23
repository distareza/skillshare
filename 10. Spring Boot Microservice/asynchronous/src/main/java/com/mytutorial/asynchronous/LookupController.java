package com.mytutorial.asynchronous;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mytutorial.asynchronous.model.User;
import com.mytutorial.asynchronous.service.LookupService;

@Component
public class LookupController {

	private static final Logger logger = LoggerFactory.getLogger(LookupController.class);

	@Autowired
	private LookupService lookupService;
	
	private static int userIndex = 0;
	
	private static final List<String> usersList = new ArrayList<>();
	
	static {
		usersList.add("Pytorch");
		usersList.add("Tensorflow");
		usersList.add("Scikit-learn");
		usersList.add("Evanphx");
		usersList.add("Takeo");
		usersList.add("Macournoyer");
	}
	
	@Scheduled(fixedRate = 2000)
	public void scheduledTasks() throws Exception {
		CompletableFuture<User> info = lookupService.findUser(usersList.get(userIndex));
		userIndex = (userIndex + 1) % usersList.size();
		logger.info("---> " + info.get());
	}
	
}
