package com.concurrency;

public class ThreadGrouping {
	
	public static class Walk implements Runnable {
		@Override
		public void run() {
			for (int i=0; i<5;i++) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				String threadGroup = Thread.currentThread().getThreadGroup().getName();
				int activeThread = Thread.activeCount();
				
				System.out.println("\tIam walking , Group (" + threadGroup + ") , active thread : " + activeThread);
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
				
				String threadGroup = Thread.currentThread().getThreadGroup().getName();
				int activeThread = Thread.activeCount();
				
				System.out.println("\tIam chewing , Group (" + threadGroup + ") , active thread : " + activeThread);
			}			
		}
	}
	
	public static void main(String[] args) {
		ThreadGroup groupOne = new ThreadGroup("GroupOne");
		ThreadGroup groupTwo = new ThreadGroup("GroupTwo");
		
		Thread walkThread1 = new Thread(groupOne, new Walk());
		Thread walkThread2 = new Thread(groupTwo, new Walk());
		Thread walkThread3 = new Thread(groupTwo, new Walk());
		
		Thread chewThread1 = new Thread(groupOne, new ChewGum());
		Thread chewThread2 = new Thread(groupTwo, new ChewGum());
		
		walkThread1.start();
		walkThread2.start();
		walkThread3.start();
		chewThread1.start();
		chewThread2.start();
		
		System.out.println("Active threads for main : " + Thread.activeCount());
		System.out.println("Active threads for GroupOne : " + groupOne.activeCount());
		System.out.println("Active threads for GroupTwo : " + groupTwo.activeCount());

		/**
		 * output :
			Active threads for main : 6
			Active threads for GroupOne : 2
			Active threads for GroupTwo : 3
				Iam walking , Group (GroupOne) , active thread : 2
				Iam walking , Group (GroupTwo) , active thread : 3
				Iam chewing , Group (GroupOne) , active thread : 2
				Iam walking , Group (GroupTwo) , active thread : 3
				Iam chewing , Group (GroupTwo) , active thread : 3
				Iam walking , Group (GroupOne) , active thread : 2
				Iam walking , Group (GroupTwo) , active thread : 3
				Iam walking , Group (GroupTwo) , active thread : 3
				Iam chewing , Group (GroupTwo) , active thread : 3
				Iam chewing , Group (GroupOne) , active thread : 2
				Iam walking , Group (GroupTwo) , active thread : 3
				Iam walking , Group (GroupOne) , active thread : 2
				Iam chewing , Group (GroupTwo) , active thread : 3
				Iam chewing , Group (GroupOne) , active thread : 2
				Iam walking , Group (GroupTwo) , active thread : 3
				Iam walking , Group (GroupTwo) , active thread : 3
				Iam walking , Group (GroupOne) , active thread : 2
				Iam chewing , Group (GroupTwo) , active thread : 3
				Iam chewing , Group (GroupOne) , active thread : 2
				Iam walking , Group (GroupTwo) , active thread : 3
				Iam walking , Group (GroupOne) , active thread : 2
				Iam walking , Group (GroupTwo) , active thread : 3
				Iam walking , Group (GroupTwo) , active thread : 2
				Iam chewing , Group (GroupOne) , active thread : 1
				Iam chewing , Group (GroupTwo) , active thread : 2
			 
		 */
	}

}
