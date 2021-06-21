package com.concurrency.producerconsumer;

import java.util.Queue;

public class SharedQueue {

	Queue<String> queue;
	int capacity;
	
	public SharedQueue(Queue<String> queue, int capacity) {
		this.queue = queue;
		this.capacity = capacity;
	}
	
}
