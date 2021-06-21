package com.concurrency.sharedvarible;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * To demonstrate Atomic Manner 
 * uses synchronization and atomic variables
 *
 */
public class SynchronizationWithAtomicVariable {

	private static final int NUM_ITERATIONS = 1000000;
	
	private class CommonCounter {
		
		private int firstNum = 0;
		private AtomicInteger secondNum = new AtomicInteger(0);
		
		public void incementCounter() {
			
			// automic variables can achive uses 1. synchronization
			synchronized (this) {
				firstNum++;
			}
			
			/** 
			 * Any thread which invokes this incrementAndGet is guaranteed to read its own update. 
			 * Similar to incrementAndGet, there are a number of other functions available for AtomicIntegers. 
			 * This includes one like GetAndAdd, GetAndDecrement, and so on. Furthermore, you will observe that in the getter which we have for firstNum, we invoke the Get() function of the AtomicInteger.
			 */
			secondNum.incrementAndGet(); 
		}
		
		public int getFirstNum() {
			return firstNum;
		}

		public int getSecondNum() {
			return secondNum.get();
		}

	}
	
	private class CounterIncrementor implements Runnable {

		private CommonCounter myCounter;
		private int numIterations;
		
		public CounterIncrementor(CommonCounter commonCounter, int numIterations) {
			this.myCounter = commonCounter;
			this.numIterations = numIterations;
		}

		@Override
		public void run() {
			for (int i = 0 ;i<numIterations; i++) {
				myCounter.incementCounter();
			}
		}

	}	
	
	public void run() {
		CommonCounter commonCounter = new CommonCounter();
		Thread threadOne = new Thread(new CounterIncrementor(commonCounter, NUM_ITERATIONS));
		Thread threadTwo = new Thread(new CounterIncrementor(commonCounter, NUM_ITERATIONS));

		System.out.println("Start value of firstNum " + commonCounter.getFirstNum());
		System.out.println("Start value of secondNum " + commonCounter.getSecondNum());
		
		try {
			threadOne.start();
			threadTwo.start();
			
			threadOne.join();
			threadTwo.join();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		System.out.println("End value of firstNum " + commonCounter.getFirstNum());
		System.out.println("End value of secondNum " + commonCounter.getSecondNum());		
	}
	
	public static void main(String[] args) {
		(new SynchronizationWithAtomicVariable()).run();
	}

}
