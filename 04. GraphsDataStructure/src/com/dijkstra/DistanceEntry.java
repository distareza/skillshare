package com.dijkstra;

public class DistanceEntry {

	private int distance;
	private int lastVertex;
	
	public DistanceEntry() {
		distance = Integer.MAX_VALUE;
		lastVertex = -1;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getLastVertex() {
		return lastVertex;
	}

	public void setLastVertex(int lastVertex) {
		this.lastVertex = lastVertex;
	}
	
}
