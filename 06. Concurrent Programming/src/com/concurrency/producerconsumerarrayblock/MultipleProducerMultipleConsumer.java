package com.concurrency.producerconsumerarrayblock;

import java.util.concurrent.ArrayBlockingQueue;

public class MultipleProducerMultipleConsumer {

	public static void main(String[] args) {
		ArrayBlockingQueue<String> sharedQueue = new ArrayBlockingQueue<>(2);
		
		Producer producerOne = new Producer(sharedQueue);
		Producer producerTwo = new Producer(sharedQueue);
		
		Consumer consumerOne = new Consumer(sharedQueue, "ConsumerOne", 7);
		Consumer consumerTwo = new Consumer(sharedQueue, "ConsumerTwo", 8);
		Consumer consumerThree = new Consumer(sharedQueue, "ConsumerThree", 5);
		
		Thread p1 = new Thread(producerOne, "ProducerOne Thread");
		Thread p2 = new Thread(producerTwo, "ProducerTwo Thread");
		
		Thread c1 = new Thread(consumerOne, "ConsumerOne Thread");
		Thread c2 = new Thread(consumerTwo, "ConsumerTwo Thread");
		Thread c3 = new Thread(consumerThree, "ConsumerThree Thread");
		
		p1.start();
		p2.start();
		
		c1.start();
		c2.start();
		c3.start();
	}

}
