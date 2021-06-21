package com.concurrency.sharedvarible;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreadSafeList implements Runnable {

	private static final int NUM_ITERARTION = 10000;
	
	public List<String> commonResources;
	
	/**
	 * 
	 */
	public ThreadSafeList(List<String> commonResources) {
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
		List<String> commonRes = Collections.synchronizedList(new ArrayList<>()); // <-- a thread-safe collection of array list
		
		Thread firstThread = new Thread(new ThreadSafeList(commonRes), "firstThread");
		Thread secondThread = new Thread(new ThreadSafeList(commonRes), "secondThread");
		
		firstThread.start();
		secondThread.start();
		
		firstThread.join();
		secondThread.join();
		
		System.out.println("#elements in common res : " + commonRes.size());
	}

}
