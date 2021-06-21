package com.concurrency.sharedvarible;

import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadSafeListImproved implements Runnable {

	private static final int NUM_ITERARTION = 10000;
	
	public CopyOnWriteArrayList<String> commonResources;
	
	/**
	 * 
	 */
	public ThreadSafeListImproved(CopyOnWriteArrayList<String> commonResources) {
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
		//List<String> commonRes = Collections.synchronizedList(new ArrayList<>()); // <-- a thread-safe collection of array list
		CopyOnWriteArrayList<String> commonRes = new CopyOnWriteArrayList<>(); //<-- improved thread-safe collection of array list
		
		Thread firstThread = new Thread(new ThreadSafeListImproved(commonRes), "firstThread");
		Thread secondThread = new Thread(new ThreadSafeListImproved(commonRes), "secondThread");
		
		firstThread.start();
		secondThread.start();
		
		firstThread.join();
		secondThread.join();
		
		System.out.println("#elements in common res : " + commonRes.size());
	}

}
