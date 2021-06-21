package com.graph;
public class MainAdjacencyListDirectedWeight {

	public static void main(String[] args) {
		Graph graph = new AdjacencyListGraph(8, Graph.GraphType.DIRECTED);
		graph.addEdge( 1, 0, 2);
		graph.addEdge( 1, 2, 1);
		graph.addEdge( 1, 5, 3);
		
		graph.addEdge( 3, 4);
		
		graph.addEdge( 2, 7);
		graph.addEdge( 2, 4);
		graph.addEdge( 2, 3, 5);
		
		graph.addEdge( 5, 6, 4);
		
		graph.addEdge( 6, 3, 2);
		
		System.out.println("Weighted Directed Adjacency List");
		graph.displayGraph();
		/**
		 ( 0 ) -> []
		( 1 ) -> [0 :{2}, 2 :{1}, 5 :{3}]
		( 2 ) -> [7 :{1}, 4 :{1}, 3 :{5}]
		( 3 ) -> [4 :{1}]
		( 4 ) -> []
		( 5 ) -> [6 :{4}]
		( 6 ) -> [3 :{2}]
		( 7 ) -> []
		 */
		
		System.out.println(String.format("Indegree of 1 : %d", graph.getIndegree(1))); // : 0
		System.out.println(String.format("Indegree of 3 : %d", graph.getIndegree(3))); // : 2
		System.out.println(String.format("Indegree of 5 : %d", graph.getIndegree(5))); // : 1
		
		System.out.println(String.format("Vertices adjacent to 1 : %s", graph.getAdjacentVertices(1))); // [0, 2, 5]
		System.out.println(String.format("Vertices adjacent to 2 : %s", graph.getAdjacentVertices(2))); // [3, 4, 7]
		System.out.println(String.format("Vertices adjacent to 5 : %s", graph.getAdjacentVertices(5))); // [6]
		System.out.println(String.format("Vertices adjacent to 6 : %s", graph.getAdjacentVertices(6))); // [3]
		System.out.println(String.format("Vertices adjacent to 7 : %s", graph.getAdjacentVertices(7))); // []
		
		System.out.println();
		System.out.println(String.format("Weight of Vertices %d to Vertiece %d is %d", 1, 2, graph.getWeightedEdge(1, 2)));		
		System.out.println(String.format("Weight of Vertices %d to Vertiece %d is %d", 1, 5, graph.getWeightedEdge(1, 5)));

		
	}

}
