package com.graph;
import java.util.List;

public class DeepthFirstTraversal extends BreathFirstTraversal {

	public static void depthFirstTraversal(Graph graph, boolean[] visited, int currentVertex) {
		if ( visited[currentVertex]) return;
		visited[currentVertex] = true;
		
		List<Integer> list = graph.getAdjacentVertices(currentVertex);
		
		for (int vertex : list) {
			depthFirstTraversal(graph, visited, vertex);
		}
		System.out.print(String.format("%d->", currentVertex));
	}
	
	
	public static void main(String[] args) {
		Graph graph = new AdjacencyListGraph(8, Graph.GraphType.DIRECTED);
		graph.addEdge(1,  0);
		graph.addEdge(1,  2);
		graph.addEdge(1,  5);
		
		graph.addEdge(3,  4);
		
		graph.addEdge(2,  7);
		graph.addEdge(2,  4);
		graph.addEdge(2,  3);
		
		graph.addEdge(5,  6);
		
		graph.addEdge(6,  3);
		
		graph.displayGraph();
		
		System.out.println("Depth First Traversal, start from node 1");
		depthFirstTraversal(graph, new boolean[graph.getNumVertices()], 1);
		System.out.println();
		/**
		 * 0->7->4->3->2->6->5->1->
		 */

		System.out.println("Depth First Traversal, start from node 6");
		depthFirstTraversal(graph, new boolean[graph.getNumVertices()], 6);
		System.out.println();
		/**
		 * 4->3->6->
		 */
		
		boolean[] visited = new boolean[graph.getNumVertices()];
		for (int i=0; i<graph.getNumVertices(); i++) {
			depthFirstTraversal(graph, visited, i);
		}
		System.out.println();
		/**
		 * 0->7->4->3->2->6->5->1->
		 */
		


	}

}
