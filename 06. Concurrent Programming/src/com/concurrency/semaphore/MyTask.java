package com.concurrency.semaphore;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * This class is to demonstrate the purpose of how Semaphore works
 * 
 * Semaphores is to allow to specify an upper limit on the number of concurrent threads which can access a resource.
 *
 */
public class MyTask implements Runnable {

	@SuppressWarnings("unused")
	private class SharedResource {
		public int myInt = 0;
		String myString = "";
		public ArrayList<Integer> myList = new ArrayList<>();
	}
	
	SharedResource sr;
	Semaphore sem;
	String threadName;

	/**
	 * For each MyTask instance will be initialized with an instance of the SharedResource and also a Semaphore. 
	 * So the goal is for the MyTask instance to access the SharedResource after it has acquired the Semaphore
	 * 
	 * @param sr
	 * @param sem
	 */
	public MyTask(SharedResource sr, Semaphore sem) {
		this.sr = sr;
		this.sem = sem;
	}

	public MyTask() {
	}

	@Override
	public void run() {
		try {
			threadName = Thread.currentThread().getName();
			
			System.out.println(threadName + " is waiting for the semaphoe");
			
			/**
			 * Invoke the Semaphore's acquire function to Triggering a blocking call
			 * If the Semaphore does have capacity, the Semaphore is acquired immediately and the code execution can proceed. 
			 * However, if the capacity has been hit for the Semaphore. 
			 * The thread will simply have to wait until one of the other threads which have acquired the Semaphore release it. 
			 *  
			 */
			sem.acquire();
			System.out.println(threadName + " has ACQUIRED the semaphore ! ");
			
			// Do Work by simulate some workload on a resources on random delay
			Thread.sleep( (long)(Math.random() * 1000) * 5 );
			
			/**
			 *  invoking the release function. 
			 *  At this point, one more slot becomes available on the Semaphore and another thread can acquire it and access the SharedResource. 
			 */
			sem.release();
			System.out.println(threadName + " has RELEASED the semaphore. ");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void execute() {
		SharedResource sharedRes = new SharedResource();
		
		/**
		 * Initialize a number of permits of thread can be run concurrently
		 * in this below example is 4
		 * when the acquire function is invoked, one of the permits is taken.
		 * And similarly, one permit is released when the release function is invoked. 
		 * This means that there can be at most 4 concurrent instances of my task which acquire the Semaphore. 
		 */
		Semaphore sem = new Semaphore(4);

		/**
		 * To demonstrate that we create ten different threads, 
		 * each of them running an instance of MyTask using the same SharedResource and Semaphore. 
		 * And we do this within the for loop.
		 * 
		 * we will have ten concurrent threads, but only 4 of them at any given point in time can acquire the Semaphore. 
		 */
		for (int i=0; i<10; i++) {
			Thread t = new Thread(new MyTask(sharedRes, sem), "Task-" + i);
			t.start();	
		}
		
		
	}
	
	public static void main (String[] args) {
		(new MyTask()).execute();
	}
	
}
