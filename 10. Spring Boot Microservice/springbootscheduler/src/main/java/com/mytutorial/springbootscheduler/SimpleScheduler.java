package com.mytutorial.springbootscheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SimpleScheduler {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@Scheduled(fixedRateString = "${scheduler.rate}")
	public void scheduleLookup() throws InterruptedException {
		System.out.println(String.format("The time is now : %s", dateFormat.format(new Date())));
		
		Thread.sleep(5000);
	}
	
}
