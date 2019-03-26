/**
 * Christopher Ansbach
 * 12/3/18
 * CIT 360 - 25
 * Assignment 5: Graphs
 */

package application;

import java.util.Iterator;

/**
 * Modified interface for a Graph
 * Created in class
 * 
 * @author Christopher
 * 
 * @param <T>
 */

public interface GraphADT<T> 
{
	public int numVertices();
	public int numEdges();
	public void addVertex(T vertex);
	public void addEdge(T vertex1, T vertex2, double weight);	//Made a double
	public void removeVertex(T vertex);
	public void removeEdge(T vertex1, T vertex2);
	public boolean isEmpty();
	public boolean existEdge(T vertex1, T vertex2);
	int numComponents();
	boolean isConnected();
}

