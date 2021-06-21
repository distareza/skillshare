package com.concurrency;

public class MyThread implements Runnable {

	@Override
	public void run() {
		System.out.println("This has been executed by a Thread " + Thread.currentThread().getName());
		
	}
	
	public static void main(String[] args) {
		Thread thread = new Thread(new MyThread());
		thread.start();
	}
}
