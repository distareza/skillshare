package com.concurrency.executor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * To demonstrate Multithread process uses Executor with CountDownLatch
 * with executorService.awaitTermination, there is no guarantee that the threads will indeed have completed by the time control is returned to the thread.
 * 
 * This is a synchronization device which allows a thread to wait for other threads to finish their tasks. 
 * This is precisely what we need for the main thread to wait for each of the downloader task to finish. 
 * There is some form of countdown called the CountDownLatch to a specific value when we initialize it, and then once each thread finishes its job, 
 * it will decrement the count and awaiting thread can resume its own operations once this latch value drops to 0. 
 * 
 * 
 * Need to install certificate on your running JVM of https://www.skillsoft.com before you can run this
 * 1. Download the certificate "https://www.skillsoft.com" and save it to C:\tmp\www.skillsoft.com.cer
 * 2. run command : (as administrator)	
 * 		C:> cd 'C:\Program Files\Java\jdk1.8.0_202\bin\'
 * 		C:\Program Files\Java\jdk1.8.0_202\bin> .\keytool.exe -import -alias skillsoft.com -keystore ..\jre\lib\security\cacerts -file C:\tmp\www.skillsoft.com.cer -storepass changeit
 *
 */
public class PageDownloaderWithCountDownLatch implements Runnable {

	String[] urlsList;
	CountDownLatch latch;
	
	public PageDownloaderWithCountDownLatch(String[] urlsList, CountDownLatch latch) {
		this.urlsList = urlsList;
		this.latch = latch;
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
				
				System.out.println(threadName + " has downloaded to " + fileName);
				
				writer.close();
				
				Thread.sleep(1000); // allowing this thread to be interrupted
				
			}
		} catch (InterruptedException ex) {
			System.err.println("downloader " + threadName + " got interrupted before downloading");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		latch.countDown();
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

	public static void doDownloadWithExecutor() {
		
		int maxThread = 2;
		CountDownLatch latch = new CountDownLatch(maxThread);
		
		Thread downloaderOne = new Thread(new PageDownloaderWithCountDownLatch(Arrays.copyOfRange(urls, 0, 6), latch));
		Thread downloaderTwo = new Thread(new PageDownloaderWithCountDownLatch(Arrays.copyOfRange(urls, 6, urls.length), latch));

		try {
			
			ExecutorService executorService = Executors.newFixedThreadPool(2);
			
			System.out.println("start Download");
			long startTime = System.currentTimeMillis();
			
			executorService.submit(downloaderOne);
			executorService.submit(downloaderTwo);

			/**
			 * This is where the thread will block until the latch drops to a value of 0. 
			 * So it effectively starts a countdown and we have coded each thread to drop this value by 1 once they have finished execution. 
			 * And given that this is initialized with a value of 2, once two threads have finished their executions, the latch will drop to zero, 
			 * in which case, control is returned to the main function, and it can proceed with its own execution. 
			 * And in this case, when the endTime is calculated, 
			 * it will be precisely when the last of the two threads has run its course which means that the Total time taken which is printed to the console at the end will be accurate.
			 *  
			 */
			latch.await();
			
			long endTime = System.currentTimeMillis();
			System.out.println("Total time taken " + (endTime - startTime)/1000 + "s");
		
		} catch (Exception ex) {
			System.err.println("Thread is interupted");
			ex.printStackTrace();
		}
	}
	
	public static void doDownloadWithExecutorDynamic() {
		
		int maxThread = 4;
		CountDownLatch latch = new CountDownLatch(maxThread);
		
		try {
			
			ExecutorService executorService = Executors.newFixedThreadPool(maxThread);
			
			System.out.println("start Download");
			long startTime = System.currentTimeMillis();
			
			for (String url : urls) {
				Thread downloader = new Thread(new PageDownloaderWithCountDownLatch(new String[] {url}, latch));
				executorService.submit(downloader);
			}
			
			/**
			 * This is where the thread will block until the latch drops to a value of 0. 
			 * So it effectively starts a countdown and we have coded each thread to drop this value by 1 once they have finished execution. 
			 * And given that this is initialized with a value of 2, once two threads have finished their executions, the latch will drop to zero, 
			 * in which case, control is returned to the main function, and it can proceed with its own execution. 
			 * And in this case, when the endTime is calculated, 
			 * it will be precisely when the last of the two threads has run its course which means that the Total time taken which is printed to the console at the end will be accurate.
			 *  
			 */
			latch.await();
			
			/**
			 * all of the PageDownloader tasks which have been submitted will get scheduled for execution and the actual termination of the executorService will occur once the last job is finished.
			 */
			executorService.shutdown();
			
			/**
			 * How to handle a gracefull shutdown of the executor service and synchronize a thread :  
			 * When we invoke the shutdown function of the ExecutorService, this initiates a shutdown. 
			 * But the actual termination of the service does not occur until the last submitted thread has been fully processed.
			 * 
			 * Once all the submitted tasks have been processed, isTerminated returns true. the thread exits from this while loop 
			 */
			while(!executorService.isTerminated()) {
				Thread.sleep(100);
			}
			
			long endTime = System.currentTimeMillis();
			System.out.println("Total time taken " + (endTime - startTime)/1000 + "s");
		
		} catch (Exception ex) {
			System.err.println("Thread is interupted");
			ex.printStackTrace();
		}
		
	}
	
	public static void doDownloadWithExecutorDynamicShutDownNow() {
		
		int maxThread = 4;
		CountDownLatch latch = new CountDownLatch(maxThread);
		
		try {
			
			ExecutorService executorService = Executors.newFixedThreadPool(maxThread);
			
			System.out.println("start Download");
			long startTime = System.currentTimeMillis();
			
			for (String url : urls) {
				Thread downloader = new Thread(new PageDownloaderWithCountDownLatch(new String[] {url}, latch));
				executorService.submit(downloader);
			}
			
			/**
			 * This is where the thread will block until the latch drops to a value of 0. 
			 * So it effectively starts a countdown and we have coded each thread to drop this value by 1 once they have finished execution. 
			 * And given that this is initialized with a value of 2, once two threads have finished their executions, the latch will drop to zero, 
			 * in which case, control is returned to the main function, and it can proceed with its own execution. 
			 * And in this case, when the endTime is calculated, 
			 * it will be precisely when the last of the two threads has run its course which means that the Total time taken which is printed to the console at the end will be accurate.
			 *  
			 */
			latch.await();
			
			/**
			 * shutdownNow() function will cause : any submitted tasks which have not been executed just yet will be terminated. 
			 * it'll also try its best to terminate those tasks which are currently running.
			 * 
			 */
			executorService.shutdownNow();
			
			long endTime = System.currentTimeMillis();
			System.out.println("Total time taken " + (endTime - startTime)/1000 + "s");
		
		} catch (Exception ex) {
			System.err.println("Thread is interupted");
			ex.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		//doDownloadWithExecutor();
		//doDownloadWithExecutorDynamic();
		doDownloadWithExecutorDynamicShutDownNow();
	}
	
}
