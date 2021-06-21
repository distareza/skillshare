package com.concurrency;

public class ThreadLifeCycle {

	public static class Walk implements Runnable {
		@Override
		public void run() {
			for (int i=0; i<5;i++) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("\tIam walking... My State is " + Thread.currentThread().getState() + " is Alive : " + Thread.currentThread().isAlive()); // Iam walking... My State is RUNNABLE is Alive : true
			}
		}
	}
	
	public static void main(String[] args) {
		Thread walkThread = new Thread(new Walk());
		
		System.out.println("State of walkThread after init: " + walkThread.getState() + " is Alive : " + walkThread.isAlive()); //State of walkThread after init: NEW is Alive : true
		
		try {
			walkThread.start();
			System.out.println("State of walkThread after start: " + walkThread.getState() + " is Alive : " + walkThread.isAlive()); // State of walkThread after start: RUNNABLE is Alive : true
			
			walkThread.join(5000); // Waits at 5000 milliseconds for this thread to die. A timeout of 0 means to wait forever. 
			System.out.println("State of walkThread after join : " + walkThread.getState() + " is Alive : " + walkThread.isAlive()); // State of walkThread after start: TIMED_WAITING is Alive : true
			
			System.out.println("Main Thread will sleep");
			Thread.sleep(1000);
			System.out.println("State of walkThread after sleep 1 second : " + walkThread.getState() + " is Alive : " + walkThread.isAlive()); // State of walkThread after sleep 1s: RUNNABLE is Alive : true
			
			//Thread.sleep(10000); // make sure all thread is completed
           	//System.out.println("State of walkThread after sleep 10 seconds: " + walkThread.getState() + " is Alive : " + walkThread.isAlive()); // State of walkThread after sleep 10s: TERMINATED is Alive : false
			
			while (walkThread.isAlive()) {
				// do nothing until the walkthread is completed
			}
			System.out.println("State of walkThread after wait until not alive: " + walkThread.getState() + " is Alive : " + walkThread.isAlive()); // State of walkThread after sleep 10s: TERMINATED is Alive : false
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("done");
		
		/**
		 * output
			State of walkThread after init: NEW is Alive : false
			State of walkThread after start: RUNNABLE is Alive : true
				Iam walking... My State is RUNNABLE is Alive : true
			State of walkThread after join : TIMED_WAITING is Alive : true
			Main Thread will sleep
				Iam walking... My State is RUNNABLE is Alive : true
			State of walkThread after sleep 1s: RUNNABLE is Alive : true
				Iam walking... My State is RUNNABLE is Alive : true
				Iam walking... My State is RUNNABLE is Alive : true
				Iam walking... My State is RUNNABLE is Alive : true
			State of walkThread after sleep 10s: TERMINATED is Alive : false
			done
		 * 
		 */
	}
}
