package com.concurrency.sharedvarible;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionPerformance {

	private ArrayList<String> myList = new ArrayList<>();
	List<String> mySyncList = Collections.synchronizedList(new ArrayList<>());
	private Vector<String> myVector = new Vector<>();
	private CopyOnWriteArrayList<String> myCWList = new CopyOnWriteArrayList<>();
	
	private HashMap<String, String> myHashMap = new HashMap<>();
	private Hashtable<String, String> myHashTable = new Hashtable<>();
	private ConcurrentHashMap<String, String> myConcurrentHashMap = new ConcurrentHashMap<>();
	
	private static final int NUM_ITERATIONS = 100000;
	
	public void testArrayList() {
		long startTime = System.currentTimeMillis();
		for (int i=0; i<NUM_ITERATIONS; i++) {
			myList.add("data-" + i);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Total Time ArrayList : " + (endTime - startTime) + " ms");
	}

	public void testSynchronizedList() {
		long startTime = System.currentTimeMillis();
		for (int i=0; i<NUM_ITERATIONS; i++) {
			mySyncList.add("data-" + i);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Total Time Synchronize List : " + (endTime - startTime) + " ms");
	}

	public void testVectorList() {
		long startTime = System.currentTimeMillis();
		for (int i=0; i<NUM_ITERATIONS; i++) {
			myVector.add("data-" + i);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Total Time Vector : " + (endTime - startTime) + " ms");
	}

	public void testCWList() {
		long startTime = System.currentTimeMillis();
		for (int i=0; i<NUM_ITERATIONS; i++) {
			myCWList.add("data-" + i);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Total Time CopyOnWriteArrayList : " + (endTime - startTime) + " ms");
	}
	
	public void testHashMap() {
		long startTime = System.currentTimeMillis();
		for (int i=0; i<NUM_ITERATIONS; i++) {
			myHashMap.put("key" + i, "data-" + i);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Total Time HashMap : " + (endTime - startTime) + " ms");
	}
	

	public void testHashTable() {
		long startTime = System.currentTimeMillis();
		for (int i=0; i<NUM_ITERATIONS; i++) {
			myHashTable.put("key" + i, "data-" + i);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Total Time HashTable : " + (endTime - startTime) + " ms");
	}

	public void testConccurentHashMap() {
		long startTime = System.currentTimeMillis();
		for (int i=0; i<NUM_ITERATIONS; i++) {
			myConcurrentHashMap.put("key" + i, "data-" + i);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Total Time Conccurent HashMap : " + (endTime - startTime) + " ms");
	}

	public static void main(String[] args) {
		CollectionPerformance cp = new CollectionPerformance();
		cp.testArrayList();
		cp.testSynchronizedList();
		cp.testVectorList();
		cp.testCWList();
		
		cp.testHashMap();
		cp.testHashTable();
		cp.testConccurentHashMap();
	}
	
}
