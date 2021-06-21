package com.concurrency.executor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrate how recuring job can be implemented using multithread programming
 *
 */
public class WebPageDownloaderRecuring implements Runnable {

	String[] urlsList;
	
	public WebPageDownloaderRecuring(String[] urlsList) {
		this.urlsList = urlsList;
	}

	@Override
	public void run() {
		
		String threadName = Thread.currentThread().getName();
		
		System.out.println(threadName + " has STARTED a run!");
		try {
			for (String urlString : urlsList) {
				
				if (Thread.currentThread().isInterrupted()) {
					throw new Exception("downloader " + threadName +  " got interrupted");
				}

				
				URL url = new URL(urlString);
				String fileName = "C:\\temp\\download\\" + urlString.substring(urlString.lastIndexOf("/") + 1).trim() + ".html";
				BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
				BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
				
				String line;
				while ((line = reader.readLine()) != null)
					writer.write(line);
				
				System.out.println(threadName + " downloaded to " + fileName);
				
				writer.close();
				
				Thread.sleep(1000); // allowing this thread to be interrupted
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println(threadName + " has COMPLETED a run!");
	}
	
	private static String[] urls = new String[] {
			"https://www.hpe.com",
			"https://www.skillsoft.com/courses",
			"https://www.skillsoft.com/partners",
			"https://www.skillsoft.com/about",
			"https://www.skillsoft.com/industries",
			"https://www.skillsoft.com/about/awards",
			"https://www.skillsoft.com/leadership-team",
			"https://www.skillsoft.com/meet-skillsoft-percipio",
			"https://www.skillsoft.com/about/culture",
			"https://www.skillsoft.com/about/global-career-opportunities",
			"https://www.skillsoft.com/skillsoft-effect",
			"https://www.skillsoft.com/integration-partners",
			"https://www.skillsoft.com/industries/manufacturing"
   };
	
	/**
	 * Demonstrate schedule with periodical delay
	 */
	public static void startSchedule() {

		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

		
		Runnable downloaderOne = new WebPageDownloaderRecuring(Arrays.copyOfRange(urls, 0, 6));
		Runnable downloaderTwo = new WebPageDownloaderRecuring(Arrays.copyOfRange(urls, 6, urls.length));

		ScheduledFuture<?> fOne = executorService.scheduleWithFixedDelay(downloaderOne, 10, 3, TimeUnit.MICROSECONDS);
		ScheduledFuture<?> fTwo = executorService.scheduleWithFixedDelay(downloaderTwo, 10, 3, TimeUnit.MICROSECONDS);
		
		System.out.println("The jobs have been scheduled");
		long startTime = System.currentTimeMillis();
		try {
			System.out.println("Exec time for downlaoderOne : " + fOne.get());
			System.out.println("Exec time for downlaoderTwo : " + fTwo.get());
		} catch (Exception ex) {
			System.err.println("Thread is interupted");
			ex.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Elapse time since scheduling : " + (endTime - startTime) + " ms");
		executorService.shutdown();
	}
	
	/**
	 * Demonstrate schedule job with fix rate
	 */
	public static void startScheduleFixRate() {

		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

		
		Runnable downloaderOne = new WebPageDownloaderRecuring(Arrays.copyOfRange(urls, 0, 6));
		Runnable downloaderTwo = new WebPageDownloaderRecuring(Arrays.copyOfRange(urls, 6, urls.length));

		ScheduledFuture<?> fOne = executorService.scheduleAtFixedRate(downloaderOne, 10, 3, TimeUnit.MICROSECONDS);
		ScheduledFuture<?> fTwo = executorService.scheduleAtFixedRate(downloaderTwo, 10, 3, TimeUnit.MICROSECONDS);
		
		System.out.println("The jobs have been scheduled");
		long startTime = System.currentTimeMillis();
		try {
			System.out.println("Exec time for downlaoderOne : " + fOne.get());
			System.out.println("Exec time for downlaoderTwo : " + fTwo.get());
		} catch (Exception ex) {
			System.err.println("Thread is interupted");
			ex.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Elapse time since scheduling : " + (endTime - startTime) + " ms");
		executorService.shutdown();
	}
	
	public static void main (String[] args) {
		//startSchedule();
		startScheduleFixRate();
	}
}
