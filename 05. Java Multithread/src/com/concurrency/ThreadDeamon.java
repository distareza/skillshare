package com.concurrency;

public class ThreadDeamon {
	
	public static class Walk implements Runnable {
		@Override
		public void run() {
			for (int i=0; i<5;i++) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("\tIam walking");
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
	
	

	public static void PrintThreadProperty() {
		Thread walkThread = new Thread(new Walk());
		Thread chewThread = new Thread(new ChewGum());
		
		walkThread.start();
		chewThread.start();
		
		System.out.println("walkThread Id " + walkThread.getId());
		System.out.println("chewThread Id " + chewThread.getId());
		System.out.println("mainThread Id " + Thread.currentThread().getId());
		System.out.println();
		System.out.println("walkThread Name " + walkThread.getName());
		System.out.println("chewThread Name " + chewThread.getName());
		System.out.println("mainThread Name " + Thread.currentThread().getName());
		System.out.println();		
		System.out.println("walkThread thread group " + walkThread.getThreadGroup());
		System.out.println("chewThread thread group " + chewThread.getThreadGroup());
		System.out.println("mainThread thread group " + Thread.currentThread().getThreadGroup());
		System.out.println();		
		 // default Priority Thread : 5, Priority Thread : 0 (Low) to 10 (High) 
		System.out.println("walkThread priority " + walkThread.getPriority());
		System.out.println("chewThread priority " + chewThread.getPriority());  
		System.out.println("mainThread priority " + Thread.currentThread().getPriority());  
		System.out.println();		
		System.out.println("Active Thread : " + Thread.activeCount());

		/** 
		 * output :
		chewThread Id 12
		mainThread Id 1
		
		walkThread Name Thread-0
		chewThread Name Thread-1
		mainThread Name main
		
		walkThread thread group java.lang.ThreadGroup[name=main,maxpri=10]
		chewThread thread group java.lang.ThreadGroup[name=main,maxpri=10]
		mainThread thread group java.lang.ThreadGroup[name=main,maxpri=10]
		
		walkThread priority 5
		chewThread priority 5
		mainThread priority 5
		
		Active Thread : 3
			Iam chewing gum...
			Iam walking
			Iam walking
			Iam chewing gum...
			Iam walking
			Iam chewing gum...
			Iam chewing gum...
			Iam walking
			Iam walking
			Iam chewing gum...		 
		 * 
		 */
	}


	public static void DaemonThread() {
		Thread walkThread = new Thread(new Walk());
		Thread chewThread = new Thread(new ChewGum());
		
		chewThread.setDaemon(true); // set Deamon Thread, jvm will not wait chewThread to complete 
		
		try {
			walkThread.start();
			walkThread.join(8000); // wait 5 second for chweThread called by main thread
			chewThread.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		/**
		 * output :
			Iam walking
			Iam walking
			Iam walking
			Iam chewing gum...
			Iam walking
			Iam chewing gum...
			Iam walking
		 *
		 * Notice that "Iam chewing gum.." only called twice due its Thread set as Deamon Thread, the main Thread is not wait until chewThread is completed
		 */

	}
	
	public static void main(String[] args) {
		//PrintThreadProperty();
		DaemonThread();
	}
}
