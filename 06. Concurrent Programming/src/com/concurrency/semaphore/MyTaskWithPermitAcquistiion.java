package com.concurrency.semaphore;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * To Demonstrate and explore a different variation of Semaphores where different threads acquire different numbers of permits.
 * Please refer to MyTask.java to check on simple implementation of semaphore 
 *  
 * Semaphores is to allow to specify an upper limit on the number of concurrent threads which can access a resource.
 * A simple semaphore in a multithreaded application assumes that all of the threads which access the semaphore are equal. 
 * This is conveyed through the fact that every single thread acquires and releases exactly one permit on the semaphore.
 * 
 * When implemented a multi-threading application, where in terms of acquiring a semaphore, not all threads are created equal. 
 * Permit Acquisition in Semaphores allows the flexibility of setting threads, which put a higher load on the resources to require more permits in order to acquire the SharedResource. 	
 * 
 */
public class MyTaskWithPermitAcquistiion implements Runnable {

	@SuppressWarnings("unused")
	private class SharedResource {
		public int myInt = 0;
		String myString = "";
		public ArrayList<Integer> myList = new ArrayList<>();
	}
	
	SharedResource sr;
	Semaphore sem;
	String threadName;
	int numPermits = 1;

	/**
	 * For each MyTaskWithPermitAcquistiion instance will be initialized with an instance of 
	 * 	- the SharedResource 
	 *  - a Semaphore.
	 *  - a number of permits 
	 *  
	 * So the goal is for this instance to access the SharedResource after it has acquired the Semaphore
	 * each thread does not just acquire and release a single permit on the semaphore but this value can be greater than one.
	 * 
	 * @param sr
	 * @param sem
	 * @param permits
	 */	
	public MyTaskWithPermitAcquistiion(SharedResource sr, Semaphore sem, int permits) {
		this.sr = sr;
		this.sem = sem;
		this.numPermits = permits;
	}

	public MyTaskWithPermitAcquistiion() {
	}

	@Override
	public void run() {
		try {
			threadName = Thread.currentThread().getName();
			// Printing out a message to the Console with the thread Name and the number of permits associated with the thread. 
			System.out.println(threadName + " is waiting for " + numPermits + " semaphoe permits..");
			
			/**
			 * Invokes the semaphore's acquire method and passes the number of permits which it needs.
			 * It is Possible for a thread to acquire all of the associated permits and in effect have exclusive access to the SharedResource.
			 * you can have a large number of threads which require just a few permits running concurrently 
			 * and just a few threads which require a high number of permits, which can concurrently acquire the semaphore
			 */
			sem.acquire(numPermits);
			System.out.println(threadName + " has ACQUIRED " + numPermits + " permits! ");
			
			// Do Work by simulate some workload on a resources on random delay
			Thread.sleep( (long)(Math.random() * 1000) * 5 );
			
			/**
			 * the thread invokes the release function on the semaphore, where it releases all of the permits which had required.
			 */
			sem.release(numPermits);			
			System.out.println(threadName + " has RELEASED " + numPermits + " permits. ");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void execute () {
		
		/**
		 * create an instance of the SharedResource and to start with,
		 */
		SharedResource sharedRes = new SharedResource();
		
		/**
		 * create a variable called maxPermits to a value of 4 when initialize an instance of our semaphore. 
		 * So it will currently only have 4 permits to offer, but in subsequent runs, we'll make sure to increase the number of permits.
		 *  
		 * The goal is to create a number of concurrent threads, which require a varying number of permits to access the semaphore. 
		 * And this number can vary from one up to maxPermits. 
		 * 
		 */
		int maxPermits = 4;
		Semaphore sem = new Semaphore(maxPermits);
		
		Random random = new Random();
		
		/**
		 * we will have 10 concurrent threads. 
		 * And each of them will require either one or two or three or Four permits to access the semaphore.
		 * 
		 * in the following execution Some of them require one permit, while others need 2, 3 or 4. 
		 * the more permits that a thread can have, is more exclusive access that the thread can have to a shared resources. 
		 */
		for (int i=0; i<10; i++) {
			
			/**
			 * in each iteration of the for loop, initialize an integer called permits, 
			 * which randomly contains a value between one and the max number of permits. 
			 * And this permit value will be used when we initialize an instance of MyTask.
			 */
			int permits = random.nextInt(maxPermits) + 1;
			
			Thread t = new Thread(new MyTaskWithPermitAcquistiion(sharedRes, sem, permits), "Task-" + i);
			t.start();	
		}
	}
	
	public static void main (String[] args) {
			(new MyTaskWithPermitAcquistiion()).execute();
	}
	
}
