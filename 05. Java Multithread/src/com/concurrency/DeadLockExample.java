package com.concurrency;

public class DeadLockExample {

	private class ResourceOne {
		@SuppressWarnings("unused")
		public int myVar = 100;
	}

	private class ResourceTwo {
		@SuppressWarnings("unused")
		public int myVar = 1000;
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
				
				synchronized (rOne) {
					System.out.println("Lock acquired on ResourceOne by " + Thread.currentThread().getName());
					rOne.myVar++;
					Thread.sleep(1000);
					
					synchronized (rTwo) {
						System.out.println("Lock acquired on ResourceTwo by " + Thread.currentThread().getName());
						rTwo.myVar--;
						Thread.sleep(1000);
					}
					
					System.out.println("Lock on ResourceTwo released by " + Thread.currentThread().getName());
				}
				
				System.out.println("Lock on ResourceOne released by " + Thread.currentThread().getName());
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
				
				synchronized (rTwo) {
					System.out.println("Lock acquired on ResourceTwo by " + Thread.currentThread().getName());
					rTwo.myVar++;
					Thread.sleep(1000);
					
					synchronized (rOne) {
						System.out.println("Lock acquired on ResourceOne by " + Thread.currentThread().getName());
						rOne.myVar--;
						Thread.sleep(1000);
					}
					System.out.println("Lock on ResourceTwo released by " + Thread.currentThread().getName());
					
				}
				System.out.println("Lock on ResourceTwo released by " + Thread.currentThread().getName());
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
	}
	
	private void triggerDeadLock() throws InterruptedException {
		ResourceOne r1 = new ResourceOne();
		ResourceTwo r2 = new ResourceTwo();
		
		Thread firstTaskThread = new Thread ( new FirstTask(r1, r2), "firstTaskThread");
		Thread secondTaskThread = new Thread ( new SecondTask(r1, r2), "secondTaskThread");
		
		System.out.println("starting the two threads...");
		
		firstTaskThread.start();
		secondTaskThread.start();
		
		firstTaskThread.join();
		secondTaskThread.join();
		
		System.out.println("Thetwo threads are done"); // <!-- never going to be print, as it experincing race condition / DeadLock
	}
	
	
	public static void main(String[] args) throws Exception {
		DeadLockExample example = new DeadLockExample();
		example.triggerDeadLock();
	}

}
