package com.graph;

import java.util.List;

public interface Graph {

	void addEdge(int v1, int v2);
	void addEdge(int v1, int v2, int weight);
	
	int getWeightedEdge(int currentVertex, Integer neightbour);
	
	List<Integer> getAdjacentVertices(int v);
	int getNumVertices();
	int getIndegree(int v);
	void displayGraph();

	public enum GraphType {
		DIRECTED,
		UNDIRECTED
	}

}
