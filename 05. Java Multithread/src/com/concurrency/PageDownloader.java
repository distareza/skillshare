package com.concurrency;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

/**
 * Need to install certificate on your running JVM of https://www.skillsoft.com before you can run this
 * 1. Downlaod the certificate "https://www.skillsoft.com" and save it to C:\tmp\www.skillsoft.com.cer
 * 2. run command : (as administrator)	
 * 		C:> cd 'C:\Program Files\Java\jdk1.8.0_202\bin\'
 * 		C:\Program Files\Java\jdk1.8.0_202\bin> .\keytool.exe -import -alias skillsoft.com -keystore ..\jre\lib\security\cacerts -file C:\tmp\www.skillsoft.com.cer -storepass changeit
 * 
 * 
 */
public class PageDownloader implements Runnable {
	
	String[] urlsList;
	
	public PageDownloader(String[] urlsList) {
		this.urlsList = urlsList;
	}

	@Override
	public void run() {
		try {
			for (String urlString : urlsList) {
				
				if (Thread.currentThread().isInterrupted()) {
					throw new Exception("downloader " + Thread.currentThread().getName() +  " got interrupted");
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

	

	public static void doDownload() {
		
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
			
			/**
			 * output
				start Download
				Page downloaded to C:\temp\download\www.hpe.com.html
				Page downloaded to C:\temp\download\leadership-team.html
				Page downloaded to C:\temp\download\meet-skillsoft-percipio.html
				Page downloaded to C:\temp\download\courses.html
				Page downloaded to C:\temp\download\culture.html
				Page downloaded to C:\temp\download\partners.html
				Page downloaded to C:\temp\download\global-career-opportunities.html
				Page downloaded to C:\temp\download\about.html
				Page downloaded to C:\temp\download\skillsoft-effect.html
				Page downloaded to C:\temp\download\industries.html
				Page downloaded to C:\temp\download\integration-partners.html
				Page downloaded to C:\temp\download\awards.html
				Page downloaded to C:\temp\download\manufacturing.html
				Total time taken 40s
			 * 
			 */
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

	public static void doInteruptedDownload() {
		
		Thread downloaderOne = new Thread(new PageDownloader(Arrays.copyOfRange(urls, 0, 6)));
		Thread downloaderTwo = new Thread(new PageDownloader(Arrays.copyOfRange(urls, 6, urls.length)));

		try {
			System.out.println("start Download");
			long startTime = System.currentTimeMillis();
			downloaderOne.start();
			downloaderTwo.start();
			
			Thread.sleep(10000);
			
			// This interrupt flag will only be checked by the downloaderOne thread when the downloaderOne goes into sleep mode. 
			// So if at the time of issuing this interrupt, it was already in the middle of a download process that will go on unimpeded.
			// we will allow it to download whatever it can in 10 seconds and then interrupted beyond that.
			downloaderOne.interrupt();
			System.out.println("downloaer one got interrupted");
			
			// However downlaoderTwo still allow to take as much time as it wants and only then will the main thread proceed
			downloaderTwo.join();			
			
			long endTime = System.currentTimeMillis();
			
			System.out.println("Total time taken " + (endTime - startTime)/1000 + "s");
			
			/*
			 * output :
				start Download
				Page downloaded to C:\temp\download\www.hpe.com.html
				Page downloaded to C:\temp\download\leadership-team.html
				downloaer one got interrupted
				Page downloaded to C:\temp\download\meet-skillsoft-percipio.html
				Page downloaded to C:\temp\download\courses.html
				java.lang.InterruptedException: sleep interrupted
					at java.lang.Thread.sleep(Native Method)
					at com.concurrency.PageDownloader.run(PageDownloader.java:48)
					at java.lang.Thread.run(Thread.java:748)
				Page downloaded to C:\temp\download\culture.html
				Page downloaded to C:\temp\download\global-career-opportunities.html
				Page downloaded to C:\temp\download\skillsoft-effect.html
				Page downloaded to C:\temp\download\integration-partners.html
				Page downloaded to C:\temp\download\manufacturing.html
				Total time taken 39s			 
			 * 
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//doDownload();
		doInteruptedDownload();
	}
}
