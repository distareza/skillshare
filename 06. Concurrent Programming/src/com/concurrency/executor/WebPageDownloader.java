package com.concurrency.executor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrate Callable Interface to get the result from a thread
 *
 * Need to install certificate on your running JVM of https://www.skillsoft.com before you can run this
 * 1. Download the certificate "https://www.skillsoft.com" and save it to C:\tmp\www.skillsoft.com.cer
 * 2. run command : (as administrator)	
 * 		C:> cd 'C:\Program Files\Java\jdk1.8.0_202\bin\'
 * 		C:\Program Files\Java\jdk1.8.0_202\bin> .\keytool.exe -import -alias skillsoft.com -keystore ..\jre\lib\security\cacerts -file C:\tmp\www.skillsoft.com.cer -storepass changeit
 */
public class WebPageDownloader implements Callable<Long> {


	String[] urlsList;
	
	public WebPageDownloader(String[] urlsList) {
		this.urlsList = urlsList;
	}

	@Override
	public Long call() throws Exception {
		String threadName = Thread.currentThread().getName();
		
		long startTime = System.currentTimeMillis();
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
				
				System.out.println(threadName + " has downloaded " + fileName);
				
				writer.close();
				
				Thread.sleep(100); // allowing this thread to be interrupted
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		long endTime = System.currentTimeMillis();

		return endTime - startTime;
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
	 * Demonstrate a multithread application with exectors with Future Class Object to determine if each thread is completed / done
	 */
	public static void doDownload() {
		
		Callable<Long> downloaderOne = new WebPageDownloader(Arrays.copyOfRange(urls, 0, 6));
		Callable<Long> downloaderTwo = new WebPageDownloader(Arrays.copyOfRange(urls, 6, urls.length));
		
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		try {

			
			System.out.println("start Download");
			Future<Long> fOne = executorService.submit(downloaderOne);
			Future<Long> fTwo = executorService.submit(downloaderTwo);

			System.out.println("begin and start process and will wait until fOne.get() return a value");
			
			/**
			 * by calling Future Object get() method, the process will eventualy synchronized to main thread and block the next task until it completed having return value from the callable method
			 */
			System.out.println("Execution time for downloaderOne : " + fOne.get() + " ms"); 
			System.out.println("Execution time for downloaderTwo : " + fTwo.get() + " ms");
			
			executorService.shutdown();
		
		} catch (Exception ex) {
			System.err.println("Thread is interupted");
			ex.printStackTrace();
		}
	}	

	/**
	 * Demonstrate a multithread application with callable object
	 */
	public static void doDownloadCallable() {
		
		Callable<Long> downloaderOne = new WebPageDownloader(Arrays.copyOfRange(urls, 0, 6));
		Callable<Long> downloaderTwo = new WebPageDownloader(Arrays.copyOfRange(urls, 6, urls.length));
		
		try {
			System.out.println("begin and start process and will wait until downloaderOne.call() return a value");
			
			/**
			 * by calling Callable Object call() method, the process will eventualy synchronized to main thread and block the next task until it completed having return value from the callable method
			 * but the callable process not run separately or conccurently
			 */
			System.out.println("Execution time for downloaderOne : " + downloaderOne.call() + " ms"); 

			System.out.println("begin and start process and will wait until downloaderTwo.call() return a value");
			System.out.println("Execution time for downloaderTwo : " + downloaderTwo.call() + " ms");
			
		} catch (Exception ex) {
			System.err.println("Thread is interupted");
			ex.printStackTrace();
		}
	}	

	/**
	 * Demonstrate a multithread application with callable object with schedule executor service 
	 */
	public static void doDownloadCallableMultiThread() {
		
		Callable<Long> downloaderOne = new WebPageDownloader(Arrays.copyOfRange(urls, 0, 6));
		Callable<Long> downloaderTwo = new WebPageDownloader(Arrays.copyOfRange(urls, 6, urls.length));
		
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
		Future<Long> fOne = executorService.schedule(downloaderOne, 3, TimeUnit.SECONDS); // will start download after 3 second
		Future<Long> fTwo = executorService.schedule(downloaderTwo, 4, TimeUnit.SECONDS); // will start download after 4 second
		try {
			
			System.out.println("the Jobs have been scheduled");
			long startTime = System.currentTimeMillis();
			
			System.out.println("Execution time for downloaderOne : " + fOne.get() + " ms"); 
			System.out.println("Execution time for downloaderTwo : " + fTwo.get() + " ms");
			
			long endTime = System.currentTimeMillis();
			System.out.println("Elapsed time sionce scheduling : " + (endTime - startTime) + " ms");
			
			executorService.shutdown();
		
		} catch (Exception ex) {
			System.err.println("Thread is interupted");
			ex.printStackTrace();
		}
	}	
	
	public static void main (String[] args) {
//		doDownload();
//		doDownloadCallable();
		doDownloadCallableMultiThread();
	}
}
