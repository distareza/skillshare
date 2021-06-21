package com.graph;
import java.util.ArrayList;
import java.util.List;

public class AdjacencySetGraph implements Graph {

	private List<VertexSet> vertexList = new ArrayList<>();
	private int numVertices = 0;
	private GraphType graphType = GraphType.DIRECTED;
	
	public AdjacencySetGraph(int numVertices, GraphType graphType) {
		this.numVertices = numVertices;
		for (int i =0; i<numVertices; i++) {
			vertexList.add(new VertexSet(i));
		}
		this.graphType = graphType;
	}

	@Override
	public void addEdge(int v1, int v2) {
		addEdge(v1, v2, 1);
	}

	@Override
	public void addEdge(int v1, int v2, int weight) {
		if (v1 >= numVertices) throw new IllegalArgumentException(String.format("Vertex number is not valid: %d, %d", v1,v2) );
		if (v2 >= numVertices) throw new IllegalArgumentException(String.format("Vertex number is not valid: %d, %d", v1,v2) );
		if (v1 < 0) throw new IllegalArgumentException(String.format("Vertex number is not valid: %d, %d", v1,v2) );
		if (v2 < 0) throw new IllegalArgumentException(String.format("Vertex number is not valid: %d, %d", v1,v2) );
		
		vertexList.get(v1).addEdge(v2, weight);
		if (graphType == GraphType.UNDIRECTED)
			vertexList.get(v2).addEdge(v1, weight);		

	}

	@Override
	public List<Integer> getAdjacentVertices(int v) {
		if (v >= numVertices) throw new IllegalArgumentException(String.format("Vertex number is not valid : %d", v));
		if (v <0 ) throw new IllegalArgumentException(String.format("Vertex number is not valid : %d", v));
		
		List<Integer> adjacentVerticesList = new ArrayList<>();
		for (VertexSet vertex : vertexList.get(v).getAdjacentVertices()) {
			adjacentVerticesList.add(vertex.getVertexNumber());
		}
		return adjacentVerticesList;
	}

	@Override
	public int getNumVertices() {
		return this.numVertices;
	}

	@Override
	public int getIndegree(int v) {
		int indegree = 0;
		for (VertexSet vertex: vertexList) {
			List<VertexSet> adjacenciesList = vertex.getAdjacentVertices();
			for (VertexSet adjacentVertex : adjacenciesList) {
				if (adjacentVertex.getVertexNumber() == v)
					indegree++;
			}
				
		}
		return indegree;
	}
	
	@Override
	public int getWeightedEdge(int v, Integer neightbour) {
		if (v >= numVertices) throw new IllegalArgumentException(String.format("Vertex number is not valid : %d", v));
		if (v <0 ) throw new IllegalArgumentException(String.format("Vertex number is not valid : %d", v));
		
		for (VertexSet vertex :  vertexList.get(v).getAdjacentVertices()) {
			if (vertex.getVertexNumber() == neightbour) {
				return vertex.getWeight();
			}
		}
		
		return 0;
	}

	@Override
	public void displayGraph() {
		for (VertexSet vertex : vertexList) {
			List<VertexSet> adjacentVertices = vertex.getAdjacentVertices();
			//System.out.println(String.format("Edges from %d to : %s", vertex.getVertexNumber(), adjacentVertices));
			System.out.println(String.format("( %d ) -> %s", vertex.getVertexNumber(), adjacentVertices));
		}

	}

	
	
}
