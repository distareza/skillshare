package com.concurrency.executor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * To demonstrate Multithread process uses Executor
 * 
 * Need to install certificate on your running JVM of https://www.skillsoft.com before you can run this
 * 1. Download the certificate "https://www.skillsoft.com" and save it to C:\tmp\www.skillsoft.com.cer
 * 2. run command : (as administrator)	
 * 		C:> cd 'C:\Program Files\Java\jdk1.8.0_202\bin\'
 * 		C:\Program Files\Java\jdk1.8.0_202\bin> .\keytool.exe -import -alias skillsoft.com -keystore ..\jre\lib\security\cacerts -file C:\tmp\www.skillsoft.com.cer -storepass changeit
 *
 */
public class PageDownloader implements Runnable {

	String[] urlsList;
	
	public PageDownloader(String[] urlsList) {
		this.urlsList = urlsList;
	}

	@Override
	public void run() {
		
		String threadName = Thread.currentThread().getName();
		
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
				
				System.out.println("Page downloaded to " + fileName);
				
				writer.close();
				
				Thread.sleep(1000); // allowing this thread to be interrupted
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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

	public static void doSimpleDownload() {
		
		Thread downloaderOne = new Thread(new PageDownloader(Arrays.copyOfRange(urls, 0, 6)));
		Thread downloaderTwo = new Thread(new PageDownloader(Arrays.copyOfRange(urls, 6, urls.length)));

		try {
			System.out.println("start Download");
			long startTime = System.currentTimeMillis();
			downloaderOne.start();
			downloaderTwo.start();
			
			// by spliting into 2 thread, the downloader process is taking sorter time to complete
			downloaderOne.join(); // downloaderone will run until it completed 
			downloaderTwo.join(); // downloaderone will run until it completed			
			
			long endTime = System.currentTimeMillis();
			
			System.out.println("Total time taken " + (endTime - startTime)/1000 + "s");
		
		} catch (Exception ex) {
			System.err.println("Thread is interupted");
			ex.printStackTrace();
		}
	}	
	
	public static void doDownloadWithExecutor() {
		Thread downloaderOne = new Thread(new PageDownloader(Arrays.copyOfRange(urls, 0, 6)));
		Thread downloaderTwo = new Thread(new PageDownloader(Arrays.copyOfRange(urls, 6, urls.length)));

		try {
			
			ExecutorService executorService = Executors.newFixedThreadPool(2);
			
			System.out.println("start Download");
			long startTime = System.currentTimeMillis();
			
			executorService.submit(downloaderOne);
			executorService.submit(downloaderTwo);

			/**
			 * this will block the thread until one of the following occurs.
			 * the thread gets interrupted, or the executor service is shut down explicitly from another thread or the set timeout value expires.
			 * 
			 * This is similar to the join method which we have used previously with a set timeout. 
			 */
			executorService.awaitTermination(15, TimeUnit.SECONDS); 
			
			/**
			 *  There is no guarantee that the threads will indeed have completed by the time control is returned to the thread. 
			 *  In order to sync up with the execution of the threads themselves, well, we can use a countdown latch
			 */
			
			long endTime = System.currentTimeMillis();
			System.out.println("Total time taken " + (endTime - startTime)/1000 + "s");
		
		} catch (Exception ex) {
			System.err.println("Thread is interupted");
			ex.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		//doSimpleDownload();
		doDownloadWithExecutor();
	}
	
}
