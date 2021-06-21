package com.concurrency.producerconsumerarrayblock;

import java.util.concurrent.ArrayBlockingQueue;

public class Consumer implements Runnable {

	ArrayBlockingQueue<String> sharedQueue;
	String consumerName;
	int consumerCapacity;

	public Consumer(ArrayBlockingQueue<String> sharedQueue, String consumerName, int consumerCapacity) {
		this.sharedQueue = sharedQueue;
		this.consumerName = consumerName;
		this.consumerCapacity = consumerCapacity;
	}

	public void consume() throws InterruptedException {
		String item = sharedQueue.take();
		System.out.println(consumerName + " has consumed " + item);
	}

	@Override
	public void run() {
		for (int i = 0; i< consumerCapacity; i++) {
			try {
				//Thread.sleep((long)(Math.random() * 1000) * 5);
				consume();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		System.out.println(consumerName + " has run its course");
	}

}
