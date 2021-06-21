package com.dijkstra;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

import com.graph.AdjacencyListGraph;
import com.graph.AdjacencyMatrixGraph;
import com.graph.Graph;

import java.util.Comparator;

public class DijkstraAlgorithm {
	
	public static Map<Integer, DistanceEntry> buildDistanceTable(Graph graph, int source) {
		Map<Integer, DistanceEntry> distanceTable = new HashMap<>();

		PriorityQueue<VertexInfo> queue = new PriorityQueue<>(new Comparator<VertexInfo>() {
			@Override
			public int compare(VertexInfo v1, VertexInfo v2) {
				return ((Integer) v1.getDistance()).compareTo((v2.getDistance()));
			}			
		});
		
		for (int j =0; j < graph.getNumVertices(); j++) {
			distanceTable.put(j, new DistanceEntry());
		}

		distanceTable.get(source).setDistance(0);
		distanceTable.get(source).setLastVertex(source);

		VertexInfo sourceVertexInfo = new VertexInfo(source, 0);
		queue.add(sourceVertexInfo);
		
		Map<Integer, VertexInfo> vertexInfoMap = new HashMap<>();
		vertexInfoMap.put(source, sourceVertexInfo);
		
		while (!queue.isEmpty()) {
			
			VertexInfo vertexInfo = queue.poll();
			
			int currentVertex = vertexInfo.getVertexId();
			
			for (Integer neightbour : graph.getAdjacentVertices(currentVertex)) {
				
				// Get the new distance , account for the weighted edge
				int distance = distanceTable.get(currentVertex).getDistance() + graph.getWeightedEdge(currentVertex, neightbour);
				
				// If we find a new shortest path to the neighbour update the distance and the last vertex
				if (distanceTable.get(neightbour).getDistance() > distance) {
					
					distanceTable.get(neightbour).setDistance(distance);
					distanceTable.get(neightbour).setLastVertex(currentVertex);
					
					// We've found a new short path to the neighbour so remove the old node frmo the priority queue
					VertexInfo neighbourVertexInfo = vertexInfoMap.get(neightbour);
					
					if (neighbourVertexInfo != null) {
						queue.remove(neighbourVertexInfo);
					}
					
					// add the neighbor back with a new updated distance
					neighbourVertexInfo  = new VertexInfo(neightbour, distance);
					
					queue.add(neighbourVertexInfo);
					vertexInfoMap.put(neightbour, neighbourVertexInfo);
					
				}
				
			}
			
		}
		
		return distanceTable;
	}

	public static void shortestPath(Graph graph, Integer source, Integer destination) {
		Map<Integer, DistanceEntry> distanceTable = buildDistanceTable(graph, source);
		
		Stack<Integer> stack = new Stack<>();
		stack.push(destination);
		
		int previousVertex = distanceTable.get(destination).getLastVertex();
		
		while (previousVertex != -1 && previousVertex != source) {
			stack.push(previousVertex);
			previousVertex = distanceTable.get(previousVertex).getLastVertex();
		}
		
		if (previousVertex == -1)
			System.out.println("Tgereis no path from node : " + source + " to node : " + destination);
		else {
			System.out.print("Shortest Path is : " + source);
			while (!stack.isEmpty()) {
				System.out.print(" -> " + stack.pop());
			}
			System.out.println();
			System.out.println("Dijkstra Done");
		}
			
	}

	public static void main(String[] args) {
		
		System.out.println("Shortest Path using Dijkstra Algorithm");

		Graph graph = new AdjacencyMatrixGraph(8, Graph.GraphType.DIRECTED);
		
		graph.addEdge(0, 3, 2);
		graph.addEdge(0, 4, 2);
		graph.addEdge(0, 1, 1);
		
		graph.addEdge(1, 3, 2);
		
		graph.addEdge(2, 7, 4);
		graph.addEdge(2, 1, 3);

		graph.addEdge(3, 6, 3);
		
		graph.addEdge(4, 7, 2);
		graph.addEdge(7, 5, 4);

		graph.displayGraph();
		/**
		 * 
		 *                 (6)
		 *                  ^ 
		 *        2     2   |3  
		 *   (4) <- (0) -> (3)  
		 * 	  |      |1     ^  
		 *    |      v      |
		 *   2|     (1) ----2
		 *    |      ^
		 *    v   4  | 3
		 *   (7) <- (2)
		 *    |                    
		 *    4------------------> (5)
		 */
		
		shortestPath(graph, 0, 5);
		// Shortest Path is : 0 -> 4 -> 7 -> 5
		
		graph.addEdge(3, 5, 2);
		/**
		 * 
		 *                 (6)
		 *                  ^ 
		 *        2     2   |3  
		 *   (4) <- (0) -> (3)---- 2  
		 * 	  |      |1     ^      |
		 *    |      v      |      |
		 *   2|     (1) ----2      |
		 *    |      ^             |
		 *    v   4  |3            |
		 *   (7) <- (2)            |
		 *    |                    v
		 *    4-----------------> (5)
		 */
		shortestPath(graph, 0, 5);
		// Shortest Path is : 0 -> 3 -> 5
		
		
	}

}
