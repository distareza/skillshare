package com.concurrency.producerconsumerlock;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedQueue {

	Queue<String> queue;
	int capacity;
	
	Lock queueLock = new ReentrantLock();
	Condition notFull = queueLock.newCondition();
	Condition notEmpty = queueLock.newCondition();
	
	public SharedQueue(Queue<String> queue, int capacity) {
		this.queue = queue;
		this.capacity = capacity;
	}
	
}
