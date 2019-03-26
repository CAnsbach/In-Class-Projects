/**
 * Christopher Ansbach
 * 12/3/18
 * CIT 360 - 25
 * Assignment 5: Graphs
 */
package application;

import java.util.*;

/**
 * Modified WDGraph class worked on in class.
 * Added shortest path algorithms and shortest path output and made a matrix of doubles
 * 
 * @author Christopher
 *
 * @param <T>
 */

public class WDGraph<T> implements GraphADT<T> 
{
	
	private int capacity = 20;									//Initial capacity of the graph
	private final double INFINITY = Double.POSITIVE_INFINITY;	//Infinity for paths with no edges
	private double[][] graph;									//2D array used for implementation of the adjacency matrix
	private int numVertices, numEdges;							//Number of vertices and edges
	private T[] vertices;										//Array to hold vertices
	
	/**
	 * Constructor for WDGraph
	 */
	
	public WDGraph()
	{
		numVertices = 0;							//Initialize number of vertices and edges
		numEdges = 0;
		graph = new double[capacity][capacity];		//Initialize graph
		vertices = (T[]) new Object[capacity];		//Initialize array of vertices
		
		//For each element in the array, initialize it to infinity
		for (int i = 0; i< graph.length; i++)
		{
			for (int j = 0; j < graph.length; j++)
			{
				graph[i][j] = INFINITY;
			}
		}
	}
	
	/**
	 * Method to return the number of vertices
	 * 
	 */
	
	@Override
	public int numVertices() 
	{
		return numVertices;
	}

	/**
	 * Method to return the number of edges
	 * 
	 */
	
	@Override
	public int numEdges() 
	{
		return numEdges;
	}

	/**
	 * Method used to add a vertex to the graph
	 * 
	 */
	
	@Override
	public void addVertex(T vertex) 
	{
		//If it is a valid vertex, add it to the array
		if(!isValidVertex(vertex))
		{
			//If the matrix is full, expand it and then add vertex
			if(numVertices == capacity)
				expand();							//Expand the vertex
				vertices[numVertices] = vertex;		//Add the vertex to the array
				numVertices++;						//increment the number of vertices
		}
	}
	
	/**
	 * Method used to check if the vertex is valid
	 * 
	 * @param vertex
	 * @return
	 */
	
	private boolean isValidVertex(T vertex)
	{
		//For every element in the vertex array, check if the given vertex matches
		for(int i = 0; i < numVertices; i++)
			if(vertices[i].equals(vertex))			//If it does, return true
				return true;
		
		//Else, return false
		return false;
	}
	
	/**
	 * Method used to get the index of the given vertex
	 * Protected to allow use in ImportData
	 * 
	 * @param vertex
	 * @return
	 */
	
	protected int vertexIndex(T vertex)
	{
		//For each element in the array, check to see if the vertex exists
		for(int i = 0; i < numVertices; i++)
			if(vertices[i].equals(vertex))				//If it does, return the index
				return i;
		
		//Else, return -1
		return -1;
	}

	/**
	 * Method used to add an edge to the graph
	 * 
	 */
	
	@Override
	public void addEdge(T vertex1, T vertex2, double weight) 
	{
		//Check to see if the vertices are good
		if(isValidVertex(vertex1)  && isValidVertex(vertex2) && vertex1 != vertex2 && weight >= 0)
		{
			//If the edge does not exist, increment the number of edges and add it
			if(!this.existEdge(vertex1, vertex2))
				numEdges++;
			
			//Else, just replace the current one
			graph[vertexIndex(vertex1)][vertexIndex(vertex2)] = weight;
		}
	}

	/**
	 * Method used to remove and edge from the graph
	 * 
	 */
	
	@Override
	public void removeEdge(T vertex1, T vertex2)
	{
		//If an edge exists, get rid of it
		if(existEdge(vertex1, vertex2))
		{
			graph[vertexIndex(vertex1)][vertexIndex(vertex2)] = INFINITY;		//Remove the edge
			numEdges--;															//Decrement number of edges
		}
	}

	/**
	 * Method to check if the graph is empty
	 */
	
	@Override
	public boolean isEmpty() 
	{
		return numVertices == 0;		//If there are no vertices, it is empty
	}

	/**
	 * Method used to check if an edge exists between two given vertices
	 */
	
	@Override
	public boolean existEdge(T vertex1, T vertex2) 
	{
		//Check for an existing edge
		return isValidVertex(vertex1) && isValidVertex(vertex2) && (graph[vertexIndex(vertex1)][vertexIndex(vertex2)] != INFINITY);
	}
	
	/**
	 * Method used to return a list of neighbors of the given vertex
	 * 
	 * @param vertex
	 * @return
	 */
	public List<T> neighbors(T vertex)
	{
		List<T> neighbors = new ArrayList<T>();		//List to store neighbors
		
		//If it is not a vertex, return null
		if (!isValidVertex(vertex))
			return null;
		
		int index = vertexIndex(vertex);			//Index of the given vertex
		
		//For each vertex, check to see if it is a neighbor of the given vertex
		for(int i = 0; i < numVertices; i++)
			if(graph[index][i] != INFINITY)			//If it is, add it to the list
				neighbors.add(vertices[i]);
		
		//Return the list of neighbors
		return neighbors;
	}
	
	/**
	 * Method used to get the next neighbor of the given vertex and its current neighbor
	 * 
	 * @param vertex
	 * @param currentNeighbor
	 * @return
	 */
	
	public T nextNeighbor(T vertex, T currentNeighbor)
	{
		//If neither of them are vertices, return null
		if (!isValidVertex(vertex) || !isValidVertex(currentNeighbor))
			return null;
		
		int row = vertexIndex(vertex);					//Current row in adjacency matrix
		int column=  vertexIndex(currentNeighbor);		//Current column in adjacency matrix
		
		//For each element past the current one, check for the next neighbor
		for (int i = column + 1; i < numVertices; i++)
			if(graph[row][i] != INFINITY)				//If one is found, return it
				return vertices[i];
		
		//Else, return null
		return null;
	}
	
	
	/**
	 * Method to use for assignment
	 * 
	 * Method uses Dijksra's single source shortest path algorithm
	 * It returns the previous (predecessors) array containing the shortest distance from the given source to all other vertices
	 * 
	 * @param vertex
	 * @return
	 */
	
	private T[] singleSourceShortestPath(T initialVertex)
	{
		int vertexIndex = vertexIndex(initialVertex);			//Index of given vertex
		double[] distance = new double[numVertices];			//Distance array to hold the shortest distance to vertices
		T[] previous = (T[]) new Object[numVertices];			//Previous array used to generate shortest
		boolean[] processed = new boolean[numVertices];			//Boolean array to keep track of processed values
		boolean done = false;									//Boolean to keep track of if the loop is finished
		int lowest = 0;											//Int to keep track of the vertex with the shortest distance
		List<T> neighbors;										//List to hold neighbors
		
		//Initialize the arrays
		for (int i = 0; i < numVertices; i++)
		{
			//If the index is the initial vertex, initialize it to zero
			if (i == vertexIndex)
				distance[i] = 0;
			
			//Else, initialize it to infinity
			else 
				distance[i] = Double.POSITIVE_INFINITY;
			
			processed[i] = false;		//Initialize processed to false
		}
		
		//While the loop is not finished, process the vertices
		while (!done)
		{
			//Get the unprocessed vertex with the lowest distance
			for (int i = 0; i < numVertices; i++)
				if (processed[i] != true && distance[i] < distance[lowest])
					lowest = i;
			
			//Process it
			processed[lowest] = true;
			neighbors = neighbors(vertices[lowest]);	//Get the vertex's neighbors
			
			//For every neighbor, calculate the distance to it from the current vertex
			for(int i = 0; i < neighbors.size(); i++)
			{
				int index = vertexIndex(neighbors.get(i));				//Index of neighbor
				
				double calculated = distance[lowest] + graph[lowest][index];	//Calculate the distance to it from the current vertex
				if (distance[index] > calculated)		//If the calculated value is less than the current one, replace it
				{
					//Change the current distance
					distance[index] = calculated;	
					previous[index] = vertices[lowest];			//Set the previous of the neighbor to the current vertex
				}
			}
			
			
			done = true;
			
			//The loop is done if there are no more vertices to process.
			for (int i = 0; i < numVertices; i++)
			{
				if (processed[i] == false)
				{
					lowest = i;
					done = false;
				}
			}
		}
		return previous;			//Return the previous array to find the shortest path
	}
	
	/**
	 * Method used to generate a shortest path from a given start to a given destination
	 * 
	 * @param start
	 * @param destination
	 * @return
	 */
	public List<T> shortestPath(T start, T destination)
	{
		List<T> path = new ArrayList<T>();						//List to hold the path
		boolean done = false;									//Boolean to determine if the loop is done
		int startIndex = vertexIndex(start);					//Index of the start
		int destIndex = vertexIndex(destination);				//Index of the destination
		int curIndex = destIndex;								//Current index
		T[] previous = (T[]) new Object[numVertices];			//Array to hold the output from the shortest path algorithm
		
		previous = singleSourceShortestPath(start);				//Get the output from shortest path algorithm
		path.add(destination);									//Add the destination to the path
		
		//While the path is not finished
		while (!done)
		{	
			//If there is a vertex in the array at the current index, add the vertex to the path
			if(previous[curIndex] != null)
			{
				path.add(previous[curIndex]);					//Add the vertex to the path
				curIndex = vertexIndex(previous[curIndex]);		//Get the index of the added vertex
			}
			
			//Else, there is no path
			else
				return null;		//Return null
			
			//If the current index is the start index, the method is done
			if(curIndex == startIndex)
				done = true;
		}
		
		//Flip the path around
		List<T> pathCopy = new ArrayList<T>();			//List to hold path copy
		//For each element in the array, add it to the path, done backwards to output path from start to finish
		for(int i = path.size() - 1; i >= 0; i--)
			pathCopy.add(path.get(i));
		
		path = pathCopy;		//Update the path
		
		return path;			//Return the path
	}
	
	/**
	 * Method used to expand the graph if needed
	 */
	
	private void expand()
	{
		int newCapacity = capacity * 2;			//New capacity
		
		double[][] newAdjMatrix = new double[newCapacity][newCapacity];		//New graph
		T[] newVertices = (T[]) new Object[newCapacity];					//New array of vertices
		
		//Add the values to the new graph and array
		for(int i = 0; i < numVertices; i++)
			newVertices[i] = vertices[i];
		
		for (int i = 0; i < newAdjMatrix.length; i++) 
			for(int j = 0; j < newAdjMatrix.length; j++)
				newAdjMatrix[i][j] = INFINITY;
			
		for (int i = 0; i < numVertices; i++) 
			for(int j = 0; j < numVertices; j++)
				newAdjMatrix[i][j] = graph[i][j];
		
		//Update current capacity, graph, and vertex array
		graph = newAdjMatrix;
		vertices = newVertices;
		capacity = newCapacity;
	}
	
	/**
	 * toString method used to print the adjacency matrix form of the graph
	 */
	
	public String toString()
	{
		//If there are no vertices, the graph is empty
		if(numVertices == 0)
			return "Graph is empty";
		
		String matrix = "";										//String version of the matrix
		
		//Format the array and add values
		matrix += String.format("%7s", "");
		for (int i = 0; i < numVertices; i++)
			matrix += String.format("%7s", vertices[i]);
		
		matrix += "\n";
		
		for (int i = 0; i < numVertices; i++) 
		{
			matrix += String.format("%7s", vertices[i]);
			
			for (int j = 0; j < numVertices; j++) 
			{
				if(graph[i][j] == INFINITY)
					matrix += String.format("%7c", '\u221e');
				else
				    matrix += String.format("%7.2f", graph[i][j]);
			}
			matrix += "\n";
		}
		return matrix;		//Return the matrix
	}
	
	/**
	 * Unimplemented methods
	 */
	
	@Override
	public void removeVertex(T vertex) 
	{
		
	}

	@Override
	public int numComponents() 
	{
		return 0;
	}

	@Override
	public boolean isConnected() 
	{
		return false;
	}
}
