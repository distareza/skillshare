package com.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockExample2 {

	private class ResourceOne {
		public int myVar = 100;
		public Lock rOneLock = new ReentrantLock();
	}

	private class ResourceTwo {
		public int myVar = 1000;		
		public Lock rTwoLock = new ReentrantLock();		
	}
	
	private class FirstTask implements Runnable {
		
		ResourceOne rOne;
		ResourceTwo rTwo;
		
		public FirstTask(ResourceOne rOne, ResourceTwo rTwo) {
			this.rOne = rOne;
			this.rTwo = rTwo;
		}


		@Override
		public void run() {
			try {

				if ( rOne.rOneLock.tryLock(10, TimeUnit.SECONDS) ) {
					System.out.println("Lock acquired on ResourceOne by " + Thread.currentThread().getName());
					
					for (int i=0; i<10; i++) {
						rOne.myVar++;
						Thread.sleep(100);
					}

					if (rTwo.rTwoLock.tryLock(10, TimeUnit.SECONDS)) {
						System.out.println("Lock acquired on ResourceTwo by " + Thread.currentThread().getName());

						for (int i=0;i<10;i++) {
							rTwo.myVar--;
							Thread.sleep(100);
						}						
						
						rTwo.rTwoLock.unlock();
						System.out.println("Lock on ResourceTwo released by " + Thread.currentThread().getName());
					}
					
					rOne.rOneLock.unlock();
					System.out.println("Lock on ResourceOne released by " + Thread.currentThread().getName());
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private class SecondTask implements Runnable {

		ResourceOne rOne;
		ResourceTwo rTwo;
		
		public SecondTask(ResourceOne rOne, ResourceTwo rTwo) {
			this.rOne = rOne;
			this.rTwo = rTwo;
		}

		@Override
		public void run() {
			try {
				
				if (rTwo.rTwoLock.tryLock(10, TimeUnit.SECONDS)) {
					System.out.println("Lock acquired on ResourceTwo by " + Thread.currentThread().getName());

					for (int i=0;i<10;i++) {
						rTwo.myVar--;
						Thread.sleep(100);
					}
	
					if (rOne.rOneLock.tryLock(10, TimeUnit.SECONDS)) {
						System.out.println("Lock acquired on ResourceOne by " + Thread.currentThread().getName());
						
						for (int i=0;i<10;i++) {
							rOne.myVar++;				
							Thread.sleep(100);
						}
						
						rOne.rOneLock.unlock();
						System.out.println("Lock on ResourceTwo released by " + Thread.currentThread().getName());
					}
					
					rTwo.rTwoLock.unlock();
					System.out.println("Lock on ResourceTwo released by " + Thread.currentThread().getName());
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
	}
	
	private void triggerDeadLock() throws InterruptedException {
		ResourceOne r1 = new ResourceOne();
		ResourceTwo r2 = new ResourceTwo();
		
		r1.myVar = 0;
		r2.myVar = 100;
		
		Thread firstTaskThread = new Thread ( new FirstTask(r1, r2), "firstTaskThread");
		Thread secondTaskThread = new Thread ( new SecondTask(r1, r2), "secondTaskThread");
		
		System.out.println("starting the two threads...");
		
		firstTaskThread.start();
		secondTaskThread.start();
		
		firstTaskThread.join();
		secondTaskThread.join();
		
		System.out.println("The two threads are done"); // <-- prevents dead lock , 
		// however the drawback is the value is inconsistent due it skip the lock object
		System.out.println("r1.myVar : " + r1.myVar);
		System.out.println("r2.myVar : " + r2.myVar);
	}
	
	
	public static void main(String[] args) throws Exception {
		TryLockExample2 example = new TryLockExample2();
		example.triggerDeadLock();
	}

}
