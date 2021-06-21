package com.graph;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class ShortestPath {

	public static Map<Integer, DistanceEntry> buildDistanceTable(Graph graph, int source) {
		Map<Integer, DistanceEntry> distanceTable = new HashMap<>();
		
		// 1. Initialized the Distance Table with Empty Distance and Last Vertex
		for (int j =0; j < graph.getNumVertices(); j++) {
			distanceTable.put(j, new DistanceEntry());
		}
		
		// 2. update distance for distance of source to 0
		distanceTable.get(source).setDistance(0);
		distanceTable.get(source).setLastVertex(source);
		
		LinkedList<Integer> queue = new LinkedList<>();
		queue.add(source);
		
		while (!queue.isEmpty()) {
			int currentVertex = queue.pollFirst();
			
			//System.out.println(String.format("current vertex %d, adjacentVErtices(%d) : %s", currentVertex, currentVertex, graph.getAdjacentVertices(currentVertex)));
			for (int i : graph.getAdjacentVertices(currentVertex)) {
				int currentDistance = distanceTable.get(i).getDistance();
				if (currentDistance == -1) {
					currentDistance = 1 + distanceTable.get(currentVertex).getDistance();
					
					distanceTable.get(i).setDistance(currentDistance);
					distanceTable.get(i).setLastVertex(currentVertex);
					
					if (!graph.getAdjacentVertices(i).isEmpty()) {
						queue.add(i);
					}
				}
			}
		}
		
		return distanceTable;
	}
	
	public static void shortestPath(Graph graph, int source, int destination) {
		System.out.println("Build Distance Table");
		Map<Integer, DistanceEntry> distanceTable = buildDistanceTable(graph, source);
		System.out.println(String.format("%s\t%s\t%s", "vertex", "distance", "last vertex"));
		for (int vertex : distanceTable.keySet()) {
			System.out.println(String.format("%d\t%d\t\t%d", vertex, distanceTable.get(vertex).getDistance(), distanceTable.get(vertex).getLastVertex()));
		}
		
		Stack<Integer> stack = new Stack<>(); 
		stack.push(destination);
		
		int previousVertex = distanceTable.get(destination).getLastVertex();
		System.out.println(String.format("stack : %s, previous vertex : %d ", stack, previousVertex));		
		while (previousVertex != -1 && previousVertex != source) {
			stack.push(previousVertex);
			previousVertex = distanceTable.get(previousVertex).getLastVertex();
			
			System.out.println(String.format("stack : %s, previous vertex : %d ", stack, previousVertex));
		}
		
		if (previousVertex == -1) {
			System.out.println("There is no path from node : " + source + " to node " + destination);
		} else {
			System.out.print("\n\nShortest Path is : " + source);
			
			while (!stack.isEmpty()) {
				System.out.print(" -> " + stack.pop());
			}
			
			System.out.println("\n\nShortest PAth Unweighted DONE!");
		}
	}
	
	
	public static void main(String[] args) {
		
		Graph graph = new AdjacencyListGraph(8, Graph.GraphType.DIRECTED);
		
		graph.addEdge(2, 7);
		graph.addEdge(3, 0);
		graph.addEdge(0, 4);
		graph.addEdge(0, 1);
		graph.addEdge(2, 1);
		graph.addEdge(1, 3);
		graph.addEdge(3, 5);
		graph.addEdge(6, 3);
		graph.addEdge(4, 7);
		
		graph.displayGraph();
		/**
		 *         (7) <- (2)
		 *          ^      |
		 *          |      v
		 *  (0) -> (4) -> (1)
		 *   ^             |
		 *   |             v
		 *   |_-----------(3) <- (6) 
		 *                 |
		 *                 v
		 *                (5)
		 */
		
		shortestPath(graph, 1, 7);
		/**
		 * Get Adjacent Vertex ( Last Vertex that connected to its vertex )
		 * Vertex	Last Vertex
		 * 0		3
		 * 1  		2
		 * 2		N/A
		 * 3		1, 6
		 * 4		0
		 * 5		3
		 * 6		N/A
		 * 7		4
		 * 
		 *  
		 * shortest path of unweighted directed graph : (1) -> (3) -> (0) -> (4) -> (7)
		 */

	}

}
