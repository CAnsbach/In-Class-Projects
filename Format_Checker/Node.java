/**
 * Christopher Ansbach
 * 10/11/18
 * CIT 360 - 25
 * Assignment 3: Simple Stack Assignment
 */

package simpleStack;

public class Node<N>{
	private N data;				//Data in the node
	private Node<N> next;		//Next node
	
	/**
	 * Constructor for a node with both a value for data and next
	 * 
	 * @param data
	 * @param next
	 */
	
	public Node(N data, Node<N> next) {
		super();
		this.data = data;
		this.next = next;
	}
	
	/**
	 * Constructor for a node with a value for data
	 * 
	 * @param data
	 */
	
	public Node(N data) {
		super();
		this.data = data;
		this.next = null;
	}
	
	/**
	 * Constructor for a node with no passed values
	 * 
	 */
	
	public Node() {
		super();
	}
	
	/**
	 * Setters and Getters for the information held in the node
	 * 
	 * @return
	 */
	
	public N getData() {
		return data;
	}
	
	public void setData(N data) {
		this.data = data;
	}
	
	public Node<N> getNext() {
		return next;
	}
	
	public void setNext(Node<N> next) {
		this.next = next;
	}
	
	@Override
	/**
	 * toString method used to output the node's data
	 * 
	 * @return
	 */
	
	public String toString() {
		return "[" + data + "]";
	}
}
