package com.concurrency.forkjoin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 * Demonstrate Fork and Join Framework : to break up large task into smaller subtasks
 * 
 * A ForkJoinTask is similar to a thread in many ways. But it is much more lightweight than a regular thread.
 * ForkJoinPool is similar to an Executor Service. But it's meant to execute Fork/Join tasks rather than runnable or callable instances. 
 *
 * A reason why this scales very well is because even if the size of the URLs array were to grow to say 1000, we don't really need to change the logic. 
 * Each RecursiveDownloader instance at the end will only process three URLs. 
 * Furthermore, if you'd like to increase the amount of URLs processed by each individual downloader, 
 * all we need to do is modify the threshold value. 
 * 
 */
@SuppressWarnings("serial")
public class RecursiveDownloader extends RecursiveAction {

	String[] urlsList;
	private static final int THRESHOLD = 3;
	
	public RecursiveDownloader(String[] urlsList) {
		this.urlsList = urlsList;
	}

	@Override
	protected void compute() {
		if (urlsList.length>THRESHOLD) {
			// A ForkJoinTask is similar to a thread in many ways. But it is much more lightweight than a regular thread.
			ForkJoinTask.invokeAll(createSubtasks());
		} else {
			download(urlsList);
		}
	}

	private List<RecursiveDownloader> createSubtasks() {
		List<RecursiveDownloader> subtasks = new ArrayList<>();
		String[] firstSet = Arrays.copyOfRange(urlsList, 0, urlsList.length / 2);
		String[] secondSet = Arrays.copyOfRange(urlsList, urlsList.length / 2, urlsList.length);
		
		subtasks.add(new RecursiveDownloader(firstSet));
		subtasks.add(new RecursiveDownloader(secondSet));
		
		return subtasks;
	}

	private void download(String[] urlsList2) {
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName + " has Started");
		try {
			for (String urlString : urlsList) {
				
				URL url = new URL(urlString);
				String fileName = "C:\\temp\\download\\" + urlString.substring(urlString.lastIndexOf("/") + 1).trim() + ".html";
				BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
				BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
				
				String line;
				while ((line = reader.readLine()) != null)
					writer.write(line);
				
				System.out.println(threadName + " has downloaded " + fileName);
				
				writer.close();
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		System.out.println(threadName + " has FINISHED");
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
	
	private static void runJobs() {
		// submit a list of 13 urls, which is exceed the threshold
		// we should expect that at least 4 RecursiveDownloader tasks will be created, which operate on 3 threads or less
		RecursiveDownloader task = new RecursiveDownloader(urls);
		
		// ForkJoinPool is similar to an Executor Service. But it's meant to execute Fork/Join tasks rather than runnable or callable instances. 
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(task);
	}
	
	public static void main (String[] args) {
		runJobs();
	}
}
