package com.graph;
public class MainAdjacencyListUndirected {

	public static void main(String[] args) {
		Graph graph = new AdjacencyListGraph(8, Graph.GraphType.UNDIRECTED);
		graph.addEdge(1,  0);
		graph.addEdge(1,  2);
		graph.addEdge(1,  5);
		
		graph.addEdge(3,  4);
		
		graph.addEdge(2,  7);
		graph.addEdge(2,  4);
		graph.addEdge(2,  3);
		
		graph.addEdge(5,  6);
		
		graph.addEdge(6,  3);
		
		System.out.println("Undirected Adjacency List");
		graph.displayGraph();
		/**
			( 0 ) --> [1]
			( 1 ) --> [0, 2, 5]
			( 2 ) --> [1, 7, 4, 3]
			( 3 ) --> [4, 2, 6]
			( 4 ) --> [3, 2]
			( 5 ) --> [1, 6]
			( 6 ) --> [5, 3]
			( 7 ) --> [2]	   
		 */
		System.out.println();
		
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
