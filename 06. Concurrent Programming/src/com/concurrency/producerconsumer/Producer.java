package com.concurrency.producerconsumer;

public class Producer implements Runnable {

	SharedQueue sharedQueue;
	
	public Producer(SharedQueue sharedQueue) {
		this.sharedQueue = sharedQueue;
	}


	String[] items = {"itemOne", "itemTwo", "itemThree", "itemFour", "itemFive",
			"itemSix", "itemSeven", "itemEight", "itemNine", "itemTen"};

	public void produce(String item) throws InterruptedException {
		synchronized (sharedQueue) {
			if (sharedQueue.queue.size() >= sharedQueue.capacity) {
				System.out.println("Queue is full.. Producer is waiting ...");
				sharedQueue.wait();
				System.out.println("Producer has woken up");
			}
		}
		
		synchronized (sharedQueue) {
			sharedQueue.queue.add(item);
			System.out.println("Produced : " + item);
			sharedQueue.notify();
			
		}
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
		System.out.println("The Producer has run its course");
	}

}
