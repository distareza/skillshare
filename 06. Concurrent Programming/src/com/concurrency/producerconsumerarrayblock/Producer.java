package com.concurrency.producerconsumerarrayblock;

import java.util.concurrent.ArrayBlockingQueue;

public class Producer implements Runnable {

	ArrayBlockingQueue<String> sharedQueue;
	
	public Producer(ArrayBlockingQueue<String> sharedQueue) {
		this.sharedQueue = sharedQueue;
	}

	String[] items = {"itemOne", "itemTwo", "itemThree", "itemFour", "itemFive",
			"itemSix", "itemSeven", "itemEight", "itemNine", "itemTen"};

	public void produce(String item) throws InterruptedException {
		String threadName = Thread.currentThread().getName();
		
		sharedQueue.put(item);
		System.out.println(threadName + " produced : " + item);
	}
	
	
	@Override
	public void run() {
		for (int i = 0; i < items.length; i++) {
			try {
				//Thread.sleep((long)(Math.random()*1000)*5);
				produce(items[i]);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + " has run its course");
	}

}
