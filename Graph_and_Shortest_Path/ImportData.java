/**
 * Christopher Ansbach
 * 12/3/18
 * CIT 360 - 25
 * Assignment 5: Graphs
 */

package application;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ImportData 
{
	private WGraph graph = new WGraph();	//New weighted graph
	private String[] pointNames;			//String array to hold point names
	private int[] xValues, yValues;			//Int arrays to hold x and y values
	private String from, to;					//String to hold the beginning and end for the shortest path algorithm
	
	/**
	 * Constructor
	 * 
	 */
	
	public ImportData()
	{}
	
	/**
	 * Method used to input the file data, generate the graph, and output shortest path.
	 * 
	 */
	public void inputFileData()
	{
		//Try Catch block for exceptions
		try 
		{
			File file = new File("graph.dat");					//Data file to be read for graph data
			Scanner fileIn = new Scanner(file);					//Scanner for the file
			System.out.println("Checking: " + file + "\n");
			int vertices, edges;								//Integer to hold the number of vertices and edges

			//While there is more in the file to be input
			while (fileIn.hasNext())
			{
				vertices = Integer.parseInt(fileIn.nextLine());			//The number of vertices is on the first line
				xValues = new int[vertices];							//Initialize the arrays
				yValues = new int[vertices];
				pointNames = new String[vertices];
				String data;											//String to hold input
				StringTokenizer tokens;									//Tokenizer to separate input
				
				//For each vertex in the file, add it to the graph
				for (int i = 0; i < vertices; i++)
				{
					data = fileIn.nextLine();							//Get the next line
					tokens = new StringTokenizer(data, " ");			//Separate the data
					
					xValues[i] = Integer.parseInt(tokens.nextToken());	//Get the vertex's xValue
					yValues[i] = Integer.parseInt(tokens.nextToken());	//Get the vertex's yValue
					pointNames[i] = tokens.nextToken();					//Get the vertex's name
					graph.addVertex(pointNames[i]);						//Add it to the graph
				}
			
				edges = Integer.parseInt(fileIn.nextLine());			//Get the number of edges
				String vertex1, vertex2;								//Strings to hold the vertex names
				int index1, index2;										//Indexes of the vertices
					
				//For ever edge in the file, add it to the graph.
				for (int i = 0; i < edges; i++)
				{
					data = fileIn.nextLine();					//Get the data
					tokens = new StringTokenizer(data, " ");	//Separate the data
					
					vertex1 = tokens.nextToken();				//The first token is the first vertex
					vertex2 = tokens.nextToken();				//Second token is the second vertex
					index1 = getIndex(vertex1);					//Get the vertex's index
					index2 = getIndex(vertex2);
					
					//Add the edge to the graph
					graph.addEdge(vertex1, vertex2, calculateEdge(index1, index2, index1, index2));
				}
				
				System.out.println("Adjacency Matrix:\n" + graph.toString());			//Output the graph in adjacency matrix form
				
				fileIn.nextLine();								//Next line is blank and not needed
				
				//Following line is the path needed
				data = fileIn.nextLine();						//Get data about the path		
				tokens = new StringTokenizer(data, " ");		//Separate the path
				
				from = tokens.nextToken();						//First token is the start
				to = tokens.nextToken();						//Second is the end
				
				//If there is a path, Output it
				if (graph.shortestPath(from, to) != null)
					System.out.println("Path from " + from + " to " + to + ": "+ graph.shortestPath(from, to));
				
				//Else, output no path
				else
					System.out.println("No Path from " + from + " to " + to);
			}
		}
		
		//Catch a FileNotFoundException
		catch (FileNotFoundException e)
		{
			System.out.println("File not found");
			e.printStackTrace();
		}
		
		//Catch other exceptions
		catch(Exception e)
		{
			System.out.println("An exception has occurred");
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the index of the given point in String form
	 * 
	 * @param point
	 * @return
	 */
	
	private int getIndex(String point)
	{
		//For every point in the array, check to see if it is a valid point
		for (int i = 0; i < pointNames.length; i++)
			if (point.equals(pointNames[i]))			//If it is, return the index
				return i;
		
		//Else, return -1
		return -1;
	}
	
	/**
	 * Method used to calculate the length of the edge between two points using the pythagorean theorem
	 * 
	 * @param x1
	 * @param x2
	 * @param y1
	 * @param y2
	 * @return
	 */
	
	private double calculateEdge(int x1, int x2, int y1, int y2)
	{
		double edge = 0.0;										//Double to hold the edge
		double a, b;											//Values used to calculate the edge
		
		a = xValues[x1] - xValues[x2];							//A is the difference in X
		b = yValues[y1] - yValues[y2];							//B is the difference in Y
		edge = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));		//C = the square root of A^2 + B^2
		
		return edge;		//Return the calculated edge
	}
	
	/**
	 * Method to return the number of vertices
	 * 
	 * @return
	 */
	
	public int numVertices()
	{
		return graph.numVertices();
	}
	
	/**
	 * Method to return the number of edges
	 * @return
	 */
	
	public int numEdges()
	{
		return graph.numEdges();
	}
	
	/**
	 * Method to return the x values of the vertices
	 * 
	 * @return
	 */
	
	public int[] getPointsX()
	{
		return xValues;
	}
	
	/**
	 * Method to return the y values of the vertices
	 * 
	 * @return
	 */
	
	public int[] getPointsY()
	{
		return yValues;
	}
	
	/**
	 * Method to return the neighbors of the given point index
	 * 
	 * @param point
	 * @return
	 */
	
	public <T> int[] neighbors(int point)
	{
		List<T> neighbors = graph.neighbors(pointNames[point]);			//List of the neighbors of the given point index
		
		//If there are neighbors, return them in an Int array form
		if (neighbors != null)
		{
			int[] neighborIndexes = new int[neighbors.size()];		//Int array to hold the number of neighbors in the list
			
			//For every neighbor in the array, input their index into the array
			for(int i = 0; i < neighbors.size(); i++)
			{
				neighborIndexes[i] =  graph.vertexIndex(neighbors.get(i));
			}
		
			return neighborIndexes;		//Return the array
		}
		
		//Else, return null
		else
			return null;
	}
	
	/**
	 * Method use to return the shortest path in an int array
	 * 
	 * @return
	 */
	
	public <T> int[] shortestPath()
	{
		List<T> pathInfo = graph.shortestPath(from, to);			//List to hold the path
		
		//If there is a path, put it into an array and return it.
		if (pathInfo != null)
		{
			int[] path = new int[pathInfo.size()];				//Int array to hold path
		
			//For every vertex in the path, add it's index to the array.
			for(int i = 0; i < pathInfo.size(); i++)
				path[i] = graph.vertexIndex(pathInfo.get(i));
		
			return path;
		}
		
		//Else, return null
		else 
			return null;
	}
}