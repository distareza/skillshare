package com.graph;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BreathFirstTraversal {

	public static void breathFirstTraversal(Graph graph, boolean[] visited, int currentVertex) {
		Queue<Integer> queue = new LinkedList<>();
		queue.add(currentVertex);
		
		while (!queue.isEmpty()) {
			int vertex = queue.remove();
			
			if (visited[vertex]) continue;
			visited[vertex] = true;
			
			System.out.print(String.format("%s->", vertex));
			
			List<Integer> list = graph.getAdjacentVertices(vertex);
			for (int v : list) {
				if (!visited[v])
					queue.add(v);
			}
		}
		
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
		
		System.out.println("Directed Adjacency List");
		graph.displayGraph();
		/**
			( 0 ) --> []
			( 1 ) --> [0, 2, 5]
			( 2 ) --> [7, 4, 3]
			( 3 ) --> [4]
			( 4 ) --> []
			( 5 ) --> [6]
			( 6 ) --> [3]
			( 7 ) --> [] 
		 */
		System.out.println();
		
		System.out.println("Breath First Traversal of Directed Adjacency Graph: start from node 1");
		breathFirstTraversal(graph, new boolean[graph.getNumVertices()] , 1);
		System.out.println();
		/**
		 1->0->2->5->7->4->3->6->		 
		 */

		System.out.println("Breath First Traversal of undirected Adjacency Graph : start from node 2");
		breathFirstTraversal(graph, new boolean[graph.getNumVertices()] , 2);
		System.out.println();
		/**
		 2->7->4->3->
		 */

		
		graph = new AdjacencyListGraph(8, Graph.GraphType.UNDIRECTED);
		graph.addEdge(1,  0);
		graph.addEdge(1,  2);
		graph.addEdge(1,  5);
		
		graph.addEdge(3,  4);
		
		graph.addEdge(2,  7);
		graph.addEdge(2,  4);
		graph.addEdge(2,  3);
		
		graph.addEdge(5,  6);
		
		graph.addEdge(6,  3);
		
		System.out.println("Breath First Traversal  of Undirected Adjacency Graph: start from node 2");
		breathFirstTraversal(graph, new boolean[graph.getNumVertices()] , 2);
		System.out.println();
		/**
		 * 2->1->7->4->3->0->5->6->
		 */

		System.out.println("");
		boolean[] visited = new boolean[graph.getNumVertices()];
		for (int i =0; i<graph.getNumVertices(); i++) {
			breathFirstTraversal(graph, visited, i);
		}
		System.out.println();
		/**
		 0->1->2->5->7->4->3->6->
		 */
	}

}
