package com.concurrency;

public class SynchronizationExample1 implements Runnable {

	private static int myNum;
	private static final int NUM_ITERATIONS = 10000;
	
	private static boolean enableSync = false;
	
	public SynchronizationExample1() {
	}
	

	public static class CommonCounter {
		
		// synchronized method
		public static synchronized void incrementCounter() {
			myNum++;
		}
		
		// unsynchronized method
		public static void incrementUnsyncCounter() {
			myNum++;
		}
	}
	
	@Override
	public void run() {
		for (int i =0; i<NUM_ITERATIONS; i++) {
			if (enableSync)
				CommonCounter.incrementCounter();
			else
				CommonCounter.incrementUnsyncCounter();
		}
	}
	
	public static void runSynchronizedProcess() {
		Thread threadOne = new Thread(new SynchronizationExample1());
		Thread threadTwo = new Thread(new SynchronizationExample1());
	
		try {
			threadOne.start();
			threadTwo.start();
			
			threadOne.join();
			threadTwo.join();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		System.out.println("End value of myNum : " + myNum);
	}

	public static void main(String[] args) {
		
		System.out.println("running unsync method");
		myNum = 0;
		runSynchronizedProcess(); // a race condition will occurred here

		System.out.println();
		System.out.println("running sync method");
		enableSync = true;
		myNum = 0;
		runSynchronizedProcess();
		

	}
	
		
}
