package com.graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Directed Adjacency Matrix Graph
 * 
 */
public class AdjacencyMatrixGraph implements Graph {

	protected int[][] adjacencyMatrix;
	protected int numVertices = 0;
	protected GraphType graphType = GraphType.DIRECTED;
	
	public AdjacencyMatrixGraph(int numVertices) {
		this.numVertices = numVertices;
		this.adjacencyMatrix = new int[numVertices][numVertices];
		for (int i = 0 ; i < numVertices; i ++) {
			for (int j = 0 ; j < numVertices; j++) {
				adjacencyMatrix[i][j] = 0;
			}
		}
	}
	
	public AdjacencyMatrixGraph(int numVertices, GraphType graphType) {
		this.numVertices = numVertices;
		this.graphType = graphType;
		this.adjacencyMatrix = new int[numVertices][numVertices];
		for (int i = 0 ; i < numVertices; i ++) {
			for (int j = 0 ; j < numVertices; j++) {
				adjacencyMatrix[i][j] = 0;
			}
		}
	}
	
	@Override
	public void addEdge(int v1, int v2) {
		addEdge(v1, v2, 1);
	}

	@Override
	public void addEdge(int v1, int v2, int weight) {
		if ( v1 >= numVertices) throw new IllegalArgumentException("Vertex number is not valid");
		if ( v2 >= numVertices) throw new IllegalArgumentException("Vertex number is not valid");
		if ( v1 < 0) throw new IllegalArgumentException("Vertex number is not valid");
		if ( v2 < 0) throw new IllegalArgumentException("Vertex number is not valid");

		if (graphType == GraphType.UNDIRECTED) {
			adjacencyMatrix[v2][v1] = weight;
		}
		
		adjacencyMatrix[v1][v2] = weight;
	}

	@Override
	public List<Integer> getAdjacentVertices(int v) {
		if (v < 0) throw new IllegalArgumentException("Vertex number is not valid");
		if (v >= numVertices) throw new IllegalArgumentException("Vertex number is not valid");
		
		List<Integer> adjacentVerticesList = new ArrayList<>();
		for (int i =0 ; i< numVertices; i++) {
			if (adjacencyMatrix[v][i] != 0) {
				adjacentVerticesList.add(i);
			}
		}
		
		Collections.sort(adjacentVerticesList);
		return adjacentVerticesList;
	}

	@Override
	public int getNumVertices() {
		return numVertices;
	}

	@Override
	public int getIndegree(int v) {
		if (v < 0) throw new IllegalArgumentException("Vertex number is not valid");
		if (v >= numVertices) throw new IllegalArgumentException("Vertex number is not valid");
		
		int indegree =0;
		for (int i = 0 ; i < numVertices; i ++) {
			if (adjacencyMatrix[i][v] != 0)
				indegree ++;
		}
		
		return indegree;
	}
	
	@Override
	public int getWeightedEdge(int currentVertex, Integer neightbour) {
		return this.adjacencyMatrix[currentVertex][neightbour];
	}

	@Override
	public void displayGraph() {

		//System.out.println("Adjacency Matrix");
		
		for (int i = 0 ; i < numVertices; i ++) {
			for (int j = 0 ; j < numVertices; j++) {
				System.out.print(adjacencyMatrix[i][j]+ " ");
			}
			System.out.println();
		}

		System.out.println();
//		for (int i = 0 ; i < numVertices; i ++) {
//			System.out.print("Edges from " + i + " to : ");
//			for (int j = 0 ; j < numVertices; j++) {
//				if ( adjacencyMatrix[i][j] > 0)
//					System.out.print(j + " ");
//			}
//			System.out.println();
//		}
		
	}
	
}
