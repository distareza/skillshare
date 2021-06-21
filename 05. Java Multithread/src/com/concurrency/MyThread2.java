package com.concurrency;

public class MyThread2 extends Thread {

	@Override
	public void run() {
		System.out.println("Thread is in running state");
	}

	public static void main(String[] args) {
		MyThread2 thread = new MyThread2();
		thread.start();
		
		System.out.println("is Runnable :? " + (thread instanceof Runnable));
		System.out.println("is Thread :? " + (thread instanceof Thread));
		System.out.println("is Object :? " + (thread instanceof Object));		
	}

}
