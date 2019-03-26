/**
 * Christopher Ansbach
 * 12/3/18
 * CIT 360 - 25
 * Assignment 5: Graphs
 */

package application;

/**
 * Modified WGraph class that we worked on in class
 * Made a matrix of doubles
 * 
 * @author Christopher
 *
 * @param <T>
 */

public class WGraph<T> extends WDGraph<T>
{
	/**
	 * Constructor
	 */
	
	public WGraph() 
	{
		super();
	}
	
	/**
	 * The edges in a weighted graph are bidirectional, and has half as many as the weighted directional graph
	 */
	
	@Override
	public int numEdges() 
	{
		return super.numEdges()/2;
	}
	
	/**
	 * An edge does both ways in a weighted graph
	 */
	
	@Override
	public void addEdge(T vertex1, T vertex2, double weight)
	{
			super.addEdge(vertex1, vertex2,weight );
			super.addEdge(vertex2, vertex1,weight );
	}
	
	/**
	 * Removing an edges is done both ways in a weighted graph
	 */
	
	@Override
	public void removeEdge(T vertex1, T vertex2) 
	{
		super.removeEdge(vertex1, vertex2);
		super.removeEdge(vertex2, vertex1);
	}
}
