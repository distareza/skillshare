package com.concurrency;

public class MyThread3 implements Runnable{

	@Override
	public void run() {
		System.out.println("This has been executed by a Thread" + Thread.currentThread().getName());
		
		for (int i = 0 ; i < 5; i ++) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Thread " + Thread.currentThread().getName() + " says :" + i);
		}
		
	}
	
	public static void main(String[] args) {
		Thread firstThread = new Thread(new MyThread3(), 	"1st Thread");
		Thread secondThread = new Thread(new MyThread3(), 	"2nd Thread");
	
		firstThread.start();
		secondThread.start();
		
		firstThread.run();
		secondThread.run();
		
	}
	

}
