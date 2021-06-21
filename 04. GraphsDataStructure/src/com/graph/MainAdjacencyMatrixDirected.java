package com.graph;
public class MainAdjacencyMatrixDirected {

	public static void main(String[] args) {
		Graph graph = new AdjacencyMatrixGraph(8);
		graph.addEdge(1,  0);
		graph.addEdge(1,  2);
		graph.addEdge(1,  5);
		
		graph.addEdge(3,  4);
		
		graph.addEdge(2,  7);
		graph.addEdge(2,  4);
		graph.addEdge(2,  3);
		
		graph.addEdge(5,  6);
		
		graph.addEdge(6,  3);
		
		System.out.println("Directed Adjacency Matrix");
		graph.displayGraph();
		/**
			0 1 2 3 4 5 6 7
		===================	
		0:  0 0 0 0 0 0 0 0
		1: 	1 0 1 0 0 1 0 0
		2:  0 0 0 1 1 0 0 1
		3:  0 0 0 0 1 0 0 0
		4:  0 0 0 0 0 0 0 0
		5:  0 0 0 0 0 0 1 0
		6:  0 0 0 1 0 0 0 0
		7:  0 0 0 0 0 0 0 0 
		 */
		
		System.out.println(String.format("Indegree of 1 : %d", graph.getIndegree(1))); // : 0
		System.out.println(String.format("Indegree of 3 : %d", graph.getIndegree(3))); // : 2
		System.out.println(String.format("Indegree of 5 : %d", graph.getIndegree(5))); // : 1
		
		System.out.println(String.format("Vertices adjacent to 1 : %s", graph.getAdjacentVertices(1))); // [0, 2, 5]
		System.out.println(String.format("Vertices adjacent to 2 : %s", graph.getAdjacentVertices(2))); // [3, 4, 7]
		System.out.println(String.format("Vertices adjacent to 5 : %s", graph.getAdjacentVertices(5))); // [6]
		System.out.println(String.format("Vertices adjacent to 7 : %s", graph.getAdjacentVertices(7))); // []		
	}

}
