package com.graph;
public class MainAdjacencyMatrixUndirectedWeight {


	public static void main(String[] args) {
		Graph graph = new AdjacencyMatrixGraph(8, Graph.GraphType.UNDIRECTED);
		graph.addEdge( 1, 0, 2);
		graph.addEdge( 1, 2, 1);
		graph.addEdge( 1, 5, 3);
		
		graph.addEdge( 3, 4);
		
		graph.addEdge( 2, 7);
		graph.addEdge( 2, 4);
		graph.addEdge( 2, 3, 5);
		
		graph.addEdge( 5, 6, 4);
		
		graph.addEdge( 6, 3, 2);
		
		System.out.println("Weighted Undirected Adjacency Matrix");
		graph.displayGraph();
		/**
		    0 1 2 3 4 5 6 7
		===================    	
		0:	2 0 1 0 0 3 0 0 
		1:	0 1 0 5 1 0 0 1 
		2:	0 0 5 0 1 0 2 0 
		3:	0 0 1 1 0 0 0 0 
		4:	0 3 0 0 0 0 4 0 
		5:	0 0 0 2 0 4 0 0 
		6:	0 0 1 0 0 0 0 0 
		 */
		
		System.out.println(String.format("Indegree of 1 : %d", graph.getIndegree(1))); // : 3
		System.out.println(String.format("Indegree of 3 : %d", graph.getIndegree(3))); // : 3
		System.out.println(String.format("Indegree of 5 : %d", graph.getIndegree(5))); // : 2
		
		System.out.println(String.format("Vertices adjacent to 1 : %s", graph.getAdjacentVertices(1))); // [0, 2, 5]
		System.out.println(String.format("Vertices adjacent to 2 : %s", graph.getAdjacentVertices(2))); // [1, 3, 4, 7]
		System.out.println(String.format("Vertices adjacent to 5 : %s", graph.getAdjacentVertices(5))); // [1, 6]
		System.out.println(String.format("Vertices adjacent to 6 : %s", graph.getAdjacentVertices(6))); // [3, 5]
		System.out.println(String.format("Vertices adjacent to 7 : %s", graph.getAdjacentVertices(7))); // [2]
	}
}
