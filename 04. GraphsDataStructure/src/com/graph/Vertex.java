package com.graph;
import java.util.ArrayList;
import java.util.List;

public class Vertex {

	private int vertexNumber;
	private int weight;
	
	private List<Vertex> adjacencyList = new ArrayList<>();

	public Vertex(int vertexNumber) {
		this.vertexNumber = vertexNumber;
		this.weight = 0;
	}

	public Vertex(int vertexNumber, int weight) {
		this.vertexNumber = vertexNumber;
		this.weight = weight;
	}

	public int getVertexNumber() {
		return vertexNumber;
	}
	
	public int getWeight() {
		return weight;
	}

	public void addEdge(int vertexNumber) {
		adjacencyList.add(new Vertex(vertexNumber, 1));
	}
	
	public void addEdge(int vertexNumber, int weight) {
		adjacencyList.add(new Vertex(vertexNumber, weight));
	}
	
	
	public List<Vertex> getAdjacentVertices() {
		return adjacencyList;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%d :{%d}", this.vertexNumber, this.weight);
	}
	
}
