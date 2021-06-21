package com.concurrency;

public class ThreadJoin {

	public static class Walk implements Runnable {
		@Override
		public void run() {
			for (int i=0; i<5;i++) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("\tIam walking... My State is " + Thread.currentThread().getState() + " is alive : " + Thread.currentThread().isAlive()); // Iam walking... My State is RUNNABLE is alive : true
			}
		}
	}
	
	public static class ChewGum implements Runnable {
		@Override
		public void run() {
			for (int i=0; i<5;i++) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("\tIam chewing gum...");
			}			
		}
	}
	
	public static void join() {
		Thread walkThread = new Thread(new Walk());
		Thread chewThread = new Thread(new ChewGum());
		
		//walkThread.start();
		//chewThread.start();
		
		System.out.println("State of walkThread after init: " + walkThread.getState() + " is alive : " + walkThread.isAlive()); 	// State of walkThread after init: NEW
		
		try {
			walkThread.start();
			System.out.println("State of walkThread after start: " + walkThread.getState() + " is alive : " + walkThread.isAlive()); //State of walkThread after start: RUNNABLE
			
			walkThread.join(); // main thread will wait until walk thread is completed to execute the rest of command in main thread
			System.out.println("State of walkThread after join : " + walkThread.getState() + " is alive : " + walkThread.isAlive()); //State of walkThread after join : TERMINATED
			
			chewThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("done");
		
		/**
		 * output :
		 * 
			State of walkThread after init: NEW is alive : false
			State of walkThread after start: RUNNABLE is alive : true
				Iam walking... My State is RUNNABLE is alive : true
				Iam walking... My State is RUNNABLE is alive : true
				Iam walking... My State is RUNNABLE is alive : true
				Iam walking... My State is RUNNABLE is alive : true
				Iam walking... My State is RUNNABLE is alive : true
			State of walkThread after join : TERMINATED is alive : false
			done
				Iam chewing gum...
				Iam chewing gum...
				Iam chewing gum...
				Iam chewing gum...
				Iam chewing gum...
		 *
		 *
		 */
	}
	
	public static void joinWait() {
		Thread walkThread = new Thread(new Walk());
		Thread chewThread = new Thread(new ChewGum());
		
		//walkThread.start();
		//chewThread.start();
		
		System.out.println("State of walkThread after init: " + walkThread.getState() + " is alive : " + walkThread.isAlive()); 	// State of walkThread after init: NEW
		
		try {
			walkThread.start();
			System.out.println("State of walkThread after start: " + walkThread.getState() + " is alive : " + walkThread.isAlive()); //State of walkThread after start: RUNNABLE
			
			walkThread.join(5000); // main thread will wait 5 second after walkthread is started to execute the rest of command in main thread
			System.out.println("State of walkThread after join : " + walkThread.getState() + " is alive : " + walkThread.isAlive()); //State of walkThread after join : TERMINATED
			
			chewThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("done");
		
		/**
		 * output :
		 * 
				State of walkThread after init: NEW is alive : false
				State of walkThread after start: RUNNABLE is alive : true
					Iam walking... My State is RUNNABLE is alive : true
				State of walkThread after join : TIMED_WAITING is alive : true
				done
					Iam walking... My State is RUNNABLE is alive : true
					Iam chewing gum...
					Iam walking... My State is RUNNABLE is alive : true
					Iam chewing gum...
					Iam walking... My State is RUNNABLE is alive : true
					Iam chewing gum...
					Iam walking... My State is RUNNABLE is alive : true
					Iam chewing gum...
					Iam chewing gum...
	
		 * @return 
		 *
		 *
		 */
	}
	
	public static void main(String[] args) {
		join();
		//joinWait();
	}
}
