package com.graph;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VertexSet {

	private int vertexNumber;
	private int weight;
	
	private Set<VertexSet> adjacencySet = new HashSet<>();

	public VertexSet(int vertexNumber) {
		this.vertexNumber = vertexNumber;
		this.weight = 0;
	}

	public VertexSet(int vertexNumber, int weight) {
		this.vertexNumber = vertexNumber;
		this.weight = weight;
	}

	public int getVertexNumber() {
		return vertexNumber;
	}
	
	public int getWeight() {
		return weight;
	}

	public void addEdge(int vertexNumber, int weight) {
		adjacencySet.add(new VertexSet(vertexNumber, weight));
	}
	
	public List<VertexSet> getAdjacentVertices() {
		return new ArrayList<>( adjacencySet );
	}
}
