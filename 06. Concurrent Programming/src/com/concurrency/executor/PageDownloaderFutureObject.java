package com.concurrency.executor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * To demonstrate Multithread process uses Executor with FutureObject
 * 
 * Need to install certificate on your running JVM of https://www.skillsoft.com before you can run this
 * 1. Download the certificate "https://www.skillsoft.com" and save it to C:\tmp\www.skillsoft.com.cer
 * 2. run command : (as administrator)	
 * 		C:> cd 'C:\Program Files\Java\jdk1.8.0_202\bin\'
 * 		C:\Program Files\Java\jdk1.8.0_202\bin> .\keytool.exe -import -alias skillsoft.com -keystore ..\jre\lib\security\cacerts -file C:\tmp\www.skillsoft.com.cer -storepass changeit
 *
 */
public class PageDownloaderFutureObject implements Runnable {

	String[] urlsList;
	
	public PageDownloaderFutureObject(String[] urlsList) {
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
				
				System.out.println(threadName + " has downloaded " + fileName);
				
				writer.close();
				
				Thread.sleep(100); // allowing this thread to be interrupted
				
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

	/**
	 * Demonstrate a multithread application with exectors with Future Class Object to determine if each thread is completed / done
	 */
	public static void doDownload() {
		
		Thread downloaderOne = new Thread(new PageDownloaderFutureObject(Arrays.copyOfRange(urls, 0, 6)));
		Thread downloaderTwo = new Thread(new PageDownloaderFutureObject(Arrays.copyOfRange(urls, 6, urls.length)));
		
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		try {

			
			System.out.println("start Download");
			long startTime = System.currentTimeMillis();
			Future<?> fOne = executorService.submit(downloaderOne);
			Future<?> fTwo = executorService.submit(downloaderTwo);
			
			boolean isFOneDone = false;
			boolean isFTwoDone = false;
			while(!executorService.isTerminated()) {
				if (fOne.isDone() && !isFOneDone) {
					isFOneDone = true;
					System.out.println("fOne is Done");
				}
				if (fTwo.isDone() && !isFTwoDone) {
					isFTwoDone = true;
					System.out.println("fTwo is Done");
				}
				if (fOne.isDone() && fTwo.isDone()) break;
			}
			
			executorService.shutdown();
			
			
			long endTime = System.currentTimeMillis();
			
			System.out.println("Total time taken " + (endTime - startTime)/1000 + "s");
		
		} catch (Exception ex) {
			System.err.println("Thread is interupted");
			ex.printStackTrace();
		}
	}	

	/**
	 * Demonstrate how Future Object can cancling thread process after n tries count looping check 
	 */
	public static void doPartialDownload() {
		Thread downloaderOne = new Thread(new PageDownloaderFutureObject(Arrays.copyOfRange(urls, 0, 6)));
		Thread downloaderTwo = new Thread(new PageDownloaderFutureObject(Arrays.copyOfRange(urls, 6, urls.length)));
		
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		try {

			
			System.out.println("start Download");
			long startTime = System.currentTimeMillis();
			Future<?> fOne = executorService.submit(downloaderOne);
			Future<?> fTwo = executorService.submit(downloaderTwo);
			
			int checkCount = 0;
			while(true) {
				checkCount++;
				if (checkCount > 3) {
					fOne.cancel(true);
					fTwo.cancel(true);
					System.out.println("the downlaoders have been Cancelled");
					break;
				}
				
				if (fOne.isDone() && fTwo.isDone()) {
					System.out.println("the downloaers are Done");
					break;
				}
				
				System.out.println("Check #" + checkCount + " : Downloaders are still on...");
				Thread.sleep(5000);
			}
			
			executorService.shutdown();
			
			
			long endTime = System.currentTimeMillis();
			
			System.out.println("Total time taken " + (endTime - startTime)/1000 + "s");
		
		} catch (Exception ex) {
			System.err.println("Thread is interupted");
			ex.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
//		doDownload();
		doPartialDownload();
	}
	
}
