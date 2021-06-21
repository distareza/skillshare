package com.concurrency.sharedvarible;

/**
 * To demonstrate a volatile variable that Java guarantees that the update to the number variable will not just be performed within the CPU cache of the updating thread, but it will be performed in main memory. 
 * Furthermore, any reads on this variable will also be performed through main memory, which means that all of the threads which share this variable will in effect, be working on the same copy. 
 * And not on their local copies in the CPU cache. 
 *   
 * One issue with running a multi threaded application on hardware which contains several CPU cores is that 
 * we may have multiple threads accessing a SharedResource which run on separate course. 
 * And, when an update is performed to the shared resource, they will write to their own CPU cache, rather than to the main memory, 
 * which is visible to all threads.
 * 
 *  
 * volatile keyword can guarantee visibility of any updates to a variable to all threads which share it.
 */
public class LooperWithVolatile implements Runnable {

	public static volatile boolean keepLooping = true; // <-- set variable with volatile keyword
	public static int number = 0;
	
	private int localNum = 0;
	
	@Override
	public void run() {
		
		String threadName = Thread.currentThread().getName();
		
		while (keepLooping) {
			if (localNum != number) {
				System.out.println(threadName + " has picked up the change in number");
				localNum = number;
			}
		}

		System.out.println(threadName + " is done!"); 
	}

	public static void main (String args[]) throws InterruptedException {
		for (int i=0; i<10; i++) {
			Thread t = new Thread(new LooperWithVolatile(), "Looper-" + i);
			t.start();
		}
		
		number = 13;
		System.out.println("number changed by MAIN");
		
		Thread.sleep(10000);
		System.out.println("wake up after 10 second to update keepLooping to false");
		keepLooping=false;
	}
}
