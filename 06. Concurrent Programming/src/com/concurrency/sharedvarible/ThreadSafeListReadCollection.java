package com.concurrency.sharedvarible;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadSafeListReadCollection implements Runnable {

	private static final int NUM_ITERARTION = 20;
	
	public List<String> commonResources;
	
	/**
	 * 
	 */
	public ThreadSafeListReadCollection(List<String> commonResources) {
		this.commonResources = commonResources;
	}

	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();
		try {
			for (int i =0; i<NUM_ITERARTION ; i++) {
				Thread.sleep(100);
				commonResources.add(threadName + "-data-" + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(threadName + " has finished execution");
	}

	
	public static void main(String[] args) throws Exception {
		//List<String> commonRes = new ArrayList<>(); <-- not thread safe collection
		//List<String> commonRes = Collections.synchronizedList(new ArrayList<>()); // <-- a thread-safe collection of array list, but it does not allow two threads to perform writes while another performs a read operation from it
		CopyOnWriteArrayList<String> commonRes = new CopyOnWriteArrayList<>(); // <--  use CopyOnWriteArrayList data structure to allow multiple thread perform writes and read concurrently
		
		Thread firstThread = new Thread(new ThreadSafeListReadCollection(commonRes), "firstThread");
		Thread secondThread = new Thread(new ThreadSafeListReadCollection(commonRes), "secondThread");
		
		firstThread.start();
		secondThread.start();
		
		Thread.sleep(1000);
		
		System.out.println("#elements in common res : " + commonRes.size());
		Iterator<String> iter = commonRes.iterator();
		while (iter.hasNext()) {
			String str = (String) iter.next();
			
			System.out.println("str : " + str);
		}
	}

}
