package com.concurrency;

public class SynchronizationExample3 {

	private static final int NUM_ITERATIOHS = 10000;
	
	
	private class CommonCounter {

		private int firstNum = 0;
		private int secondNum = 0;

		public void incrementCounter() {
			/**
			 * synchronized block to avoid race condition for multithread process
			 */
			synchronized (this) {
				firstNum ++;
			}
			/**
			 * unsychronized code will having race condition
			 */
			secondNum ++;
		}

		public int getFirstNum() {
			return firstNum;
		}

		public int getSecondNum() {
			return secondNum;
		}
		
	}
	
	private class CounterIncrementor implements Runnable {

		private CommonCounter myCounter;
		private int numIterations;
		
		public CounterIncrementor(CommonCounter myCounter, int numIterations) {
			this.myCounter = myCounter;
			this.numIterations = numIterations;
		}


		@Override
		public void run() {
			for (int i=0; i<numIterations; i++) {
				myCounter.incrementCounter();
			}
		}

	}	
	
	public void runMultiThreadAccessingSameResources() {
		
		CommonCounter commonCounter = new CommonCounter();
		
		Thread threadOne = new Thread(new CounterIncrementor(commonCounter, NUM_ITERATIOHS));
		Thread threadTwo = new Thread(new CounterIncrementor(commonCounter, NUM_ITERATIOHS));
		
		System.out.println("Start value of firstNumn :" + commonCounter.getFirstNum());
		System.out.println("Start value of secondNumn :" + commonCounter.getSecondNum());
		
		try {
			threadOne.start();
			threadTwo.start();
			
			threadOne.join();
			threadTwo.join();
			
			 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		System.out.println("End value of firstNumn :" + commonCounter.getFirstNum());
		System.out.println("End value of secondNumn :" + commonCounter.getSecondNum());
	}
	
//	public void runMultiThreadAccessingSameResources2() {
//		CommonCounter commonCounterOne = new CommonCounter();
//		CommonCounter commonCounterTwo = new CommonCounter();
//		
//		Thread thread1 = new Thread(new CounterIncrementor(commonCounterOne, NUM_ITERATIOHS));
//		Thread thread2 = new Thread(new CounterIncrementor(commonCounterTwo, NUM_ITERATIOHS));
//		Thread thread3 = new Thread(new CounterIncrementor(commonCounterTwo, NUM_ITERATIOHS));
//		
//		System.out.println("Start value of counter one :" + commonCounterOne.getMyNum());
//		System.out.println("Start value of counter two :" + commonCounterTwo.getMyNum());
//		
//		try {
//			thread1.start();
//			thread2.start();
//			thread3.start();
//			
//			thread1.join();
//			thread2.join();
//			thread3.join();
//			
//			 
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		
//		System.out.println("End value of Counter One : " + commonCounterOne.getMyNum());
//		System.out.println("End value of Counter Two : " + commonCounterTwo.getMyNum());
//		
//	}

	/**
	 * To demonstrate how 2 Thread can accessing same resources (CommonCounter Class)
	 * which the process inside CommonCounter Class having synchronized method to avoid race condition 
	 * 
	 */
	public static void main(String[] args) {
		SynchronizationExample3 syncMain = new SynchronizationExample3();
		syncMain.runMultiThreadAccessingSameResources();
//		syncMain.runMultiThreadAccessingSameResources2();
	}
	
}
