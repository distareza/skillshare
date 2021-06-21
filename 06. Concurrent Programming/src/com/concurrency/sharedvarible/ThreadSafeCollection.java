package com.concurrency.sharedvarible;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Demonstrate a thread safe Synchronized Collections
 *  
 *
 */
public class ThreadSafeCollection implements Runnable {

	private static final int NUM_ITERARTION = 10000;
	
	public Collection<String> commonResources;
	
	/**
	 * 
	 */
	public ThreadSafeCollection(Collection<String> commonResources) {
		this.commonResources = commonResources;
	}

	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();
		try {
			for (int i =0; i<NUM_ITERARTION ; i++) {
				commonResources.add(threadName + "-data-" + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(threadName + " has finished execution");
	}

	
	public static void main(String[] args) throws Exception {
		//ArrayList<String> commonRes = new ArrayList<>(); <-- not thread safe collection
		Collection<String> commonRes = Collections.synchronizedCollection(new ArrayList<>()); // <-- a safe thread collection of array list
		
		Thread firstThread = new Thread(new ThreadSafeCollection(commonRes), "firstThread");
		Thread secondThread = new Thread(new ThreadSafeCollection(commonRes), "secondThread");
		
		firstThread.start();
		secondThread.start();
		
		firstThread.join();
		secondThread.join();
		
		System.out.println("#elements in common res : " + commonRes.size());
	}
	
}
