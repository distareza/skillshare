package com.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

public class ReadLockExample {
	private class ResourceOne {
		public int myVar = 100;
		public StampedLock rOneLock = new StampedLock();
	}

	private class ResourceTwo {
		public int myVar = 1000;		
		public StampedLock rTwoLock = new StampedLock();		
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
				long writeLockOneStamp = rOne.rOneLock.readLock();
				System.out.println("Lock acquired on ResourceOne by " + Thread.currentThread().getName() + ". Lock stamp is :" + writeLockOneStamp);
					
				rOne.myVar++;
				Thread.sleep(2000);
				
				rOne.rOneLock.unlock(writeLockOneStamp);
				System.out.println("Lock on ResourceOne released by " + Thread.currentThread().getName());

				long writeLockTwoStamp = rTwo.rTwoLock.readLock();
				System.out.println("Lock acquired on ResourceTwo by " + Thread.currentThread().getName() + ". Lock stamp is : " + writeLockTwoStamp);

				rTwo.myVar--;
				Thread.sleep(2000);
					
				rTwo.rTwoLock.unlock(writeLockTwoStamp);
				System.out.println("Lock on ResourceTwo released by " + Thread.currentThread().getName());
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

				long writeLockTwoStamp = rTwo.rTwoLock.readLock();
				System.out.println("Lock acquired on ResourceTwo by " + Thread.currentThread().getName() + ". Lock stamp is : " + writeLockTwoStamp);

				rTwo.myVar--;
				Thread.sleep(1000);
					
				rTwo.rTwoLock.unlock(writeLockTwoStamp);
				System.out.println("Lock on ResourceTwo released by " + Thread.currentThread().getName());

				long writeLockOneStamp = rOne.rOneLock.readLock();
				System.out.println("Lock acquired on ResourceOne by " + Thread.currentThread().getName() + ". Lock stamp is :" + writeLockOneStamp);
					
				rOne.myVar++;
				Thread.sleep(1000);
				
				rOne.rOneLock.unlock(writeLockOneStamp);
				System.out.println("Lock on ResourceOne released by " + Thread.currentThread().getName());
				
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
	
	private void triggerDeadLock2() throws InterruptedException {
		ResourceOne r1 = new ResourceOne();
		ResourceTwo r2 = new ResourceTwo();
		
		r1.myVar = 0;
		r2.myVar = 100;
		
		Thread firstTaskThread = new Thread ( new FirstTask(r1, r2), "firstTaskThread");
		Thread secondTaskThread = new Thread ( new SecondTask(r1, r2), "secondTaskThread");
		Thread anotherSecondTaskThread = new Thread ( new SecondTask(r1, r2), "anotherSecondTaskThread");
		
		System.out.println("starting the three threads...");
		System.out.println("r1.myVar : " + r1.myVar);
		System.out.println("r2.myVar : " + r2.myVar);
		
		firstTaskThread.start();
		secondTaskThread.start();
		anotherSecondTaskThread.start();
		
		firstTaskThread.join();
		secondTaskThread.join();
		anotherSecondTaskThread.join();
		
		System.out.println("The three threads are done"); // <-- prevents dead lock , 
		// however the drawback is the value is inconsistent due it skip the lock object
		System.out.println("r1.myVar : " + r1.myVar);
		System.out.println("r2.myVar : " + r2.myVar);
	}
	
	public static void main(String[] args) throws Exception {
		ReadLockExample example = new ReadLockExample();
		//example.triggerDeadLock();
		example.triggerDeadLock2();
	}
}
