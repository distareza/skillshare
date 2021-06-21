package com.concurrency.producerconsumerlock;

import java.util.LinkedList;

public class ProducerMultipleConsumer {

	public static void main(String[] args) {
		SharedQueue sharedQueue = new SharedQueue(new LinkedList<String>(), 2);
		
		Producer producer = new Producer(sharedQueue);
		Consumer consumerOne = new Consumer(sharedQueue, "ConsumerOne", 7);
		Consumer consumerTwo = new Consumer(sharedQueue, "ConsumerTwo", 3);
		
		Thread p = new Thread(producer, "Producer Thread");
		Thread c1 = new Thread(consumerOne, "ConsumerOne Thread");
		Thread c2 = new Thread(consumerTwo, "ConsumerTwo Thread");
		
		p.start();
		c1.start();
		c2.start();
		
		
	}

}
