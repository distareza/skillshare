package com.graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TopologicalSort {

	public static List<Integer> sort(Graph graph) {
		// Initiate all nodes within the graph that can be processed,
		// the next node that can be process are the nodes that have 
		// 	1. indegree = 0 (which are not dependent on any other nodes), or
		//  2. all of the previous node that point into that node have already been processed
		LinkedList<Integer> queue = new LinkedList<>();
		
		// Hold a Map of an vertex and its indegree value 
		Map<Integer, Integer> indegreeMap = new HashMap<>();
		
		// Find all the vertex that can be process and Iterates available vertex / node and find their Indegree 
		for (int vertex = 0; vertex < graph.getNumVertices(); vertex++) {
			int indegree = graph.getIndegree(vertex);
			indegreeMap.put(vertex, indegree);
			if (indegree == 0) {
				queue.add(vertex);
			}
		}
		
		List<Integer> sortedList = new ArrayList<>();
		while (!queue.isEmpty()) {
			//System.out.print(queue);			
			int vertex = queue.remove();
			
			//System.out.print("\t");
			sortedList.add(vertex);
			//System.out.print(sortedList);
			
			List<Integer> adjacentVertices = graph.getAdjacentVertices(vertex);
			for (int adjacentVertex : adjacentVertices) {
				int updateIndegree = indegreeMap.get(adjacentVertex) -1;
				indegreeMap.put(adjacentVertex, updateIndegree);
				if (updateIndegree == 0) {
					queue.add(adjacentVertex);
				}
			}
			
			//System.out.println();
		}
		
		if (sortedList.size() != graph.getNumVertices())
			throw new RuntimeException("The Graph had a cycle");
		
		return sortedList;
	}

	public static void main(String[] args) {
		
		//Graph graph = new AdjacencyMatrixGraph(8, Graph.GraphType.DIRECTED);
		Graph graph = new AdjacencyListGraph(8, Graph.GraphType.DIRECTED);
		//Graph graph = new AdjacencySetGraph(8, Graph.GraphType.DIRECTED);

		graph.addEdge(2, 7);
		graph.addEdge(0, 3);
		graph.addEdge(0, 4);
		graph.addEdge(0, 1);
		graph.addEdge(2, 1);
		graph.addEdge(1, 3);
		graph.addEdge(3, 5);
		graph.addEdge(3, 6);
		graph.addEdge(4, 7);
		
		graph.displayGraph();
		/**
		 *        
		 * (0) -> (4) <- (7) <- (2)
		 *  |_--> (1) <!--------_|
		 *  |      |
		 *  |      v
		 *  |_--> (3) -> (5)
		 *  	   |_--> (6)
		 *         	   
		 * 
		 */
		
		System.out.println();
		System.out.println(sort(graph));
		/**
		 * Loop		queue	Sorted List
		 * 0		[0, 2]	[0]						--> always start from vertex indegree = 0 (which are not dependent on any other nodes)
		 * 1		[2, 4]	[0, 2]					--> if having multiple vertex destination from same source vertex, put it in order vertex in a queue 
		 * 2		[4, 1]	[0, 2, 4]
		 * 3		[1, 7]	[0, 2, 4, 1]
		 * 4		[7, 3]	[0, 2, 4, 1, 7]
		 * 5		[3]		[0, 2, 4, 1, 7, 3]
		 * 6		[5, 6]	[0, 2, 4, 1, 7, 3, 5]
		 * 7		[6]		[0, 2, 4, 1, 7, 3, 5, 6]
		 * 
		 */
		// [0, 2, 4, 1, 7, 3, 5, 6]
		

		// If graph is cyclic graph
		graph.addEdge(6, 2);
		graph.displayGraph(); 
		sort(graph); // this will return error

	}

	
}
