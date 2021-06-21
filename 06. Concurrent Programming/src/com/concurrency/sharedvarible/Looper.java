package com.concurrency.sharedvarible;

/**
 * To demonstrate a non-volatile variable that uses in a thread are not visible or accessible to other threads for MultiThread application
 *  
 * One issue with running a multi threaded application on hardware which contains several CPU cores is that 
 * we may have multiple threads accessing a SharedResource which run on separate course. 
 * And, when an update is performed to the shared resource, they will write to their own CPU cache, rather than to the main memory, 
 * which is visible to all threads.
 * 
 * Warning : this program will not going to reach a complete / finish state
 * 
 * volatile keyword can guarantee visibility of any updates to a variable to all threads which share it.
 */
public class Looper implements Runnable {

	public static boolean keepLooping = true;
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

		// this line is never being triggered, although "keepLooping" variable has been changed to false on main function
		System.out.println(threadName + " is done!"); 
	}

	public static void main (String args[]) throws InterruptedException {
		for (int i=0; i<10; i++) {
			Thread t = new Thread(new Looper(), "Looper-" + i);
			t.start();
		}
		
		number = 13;
		System.out.println("number changed by MAIN");
		
		Thread.sleep(10000);
		System.out.println("wake up after 10 second to update keepLooping to false");
		keepLooping=false;
	}
}
