package com.concurrency.producerconsumer;

import java.util.LinkedList;

public class ProducerConsumer {

	public static void main(String[] args) {
		SharedQueue sharedQueue = new SharedQueue(new LinkedList<String>(), 2);
		
		Producer producer = new Producer(sharedQueue);
		Consumer consumer = new Consumer(sharedQueue, "ConsumerOne", 10);
		
		Thread p = new Thread(producer, "Producer Thread");
		Thread c = new Thread(consumer, "Consumer Thread");
		
		p.start();
		c.start();
		
		
	}

}
