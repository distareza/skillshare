package com.dijkstra;

public class VertexInfo {

	private int vertexId;
	private int distance;
	
	public VertexInfo(int vertexId, int distance) {
		this.vertexId = vertexId;
		this.distance = distance;
	}

	public int getVertexId() {
		return vertexId;
	}

	public int getDistance() {
		return distance;
	}

	@Override
	public String toString() {
		return String.format("Vertex: %d Edge weight : %d", this.vertexId, this.distance);
	}
	
	
}
