package com.concurrency.producerconsumerlock;

public class Consumer implements Runnable {

	SharedQueue sharedQueue;
	String consumerName;
	int consumerCapacity;

	public Consumer(SharedQueue sharedQueue, String consumerName, int consumerCapacity) {
		this.sharedQueue = sharedQueue;
		this.consumerName = consumerName;
		this.consumerCapacity = consumerCapacity;
	}

	public void consume() throws InterruptedException {

		sharedQueue.queueLock.lock();
		if (sharedQueue.queue.size() == 0) {
			System.out.println("Queue is empty. " + consumerName + " is waiting...");
			sharedQueue.notEmpty.await();
			System.out.println(consumerName + " has woken up");
		}
		
		String item = sharedQueue.queue.remove();
		System.out.println(consumerName + " has consumed " + item);
		sharedQueue.notFull.signal();
		sharedQueue.queueLock.unlock();
	}

	@Override
	public void run() {
		for (int i = 0; i< consumerCapacity; i++) {
			try {
				Thread.sleep((long)(Math.random() * 1000) * 5);
				consume();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		System.out.println(consumerName + " has run its course");
	}

}
