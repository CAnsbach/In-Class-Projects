/**
 * Christopher Ansbach
 * 11/4/18
 * CIT 360 - 25
 * Assignment 4: Mazes
 */

public class Stack<N>
{
	private LinkedList<N> stack = new LinkedList();		//List used to make the stack
	
	/**
	 * Constructor for stack
	 * 
	 */
	
	public Stack()
	{
		this.stack = stack;
	}
	
	/**
	 * Return the size of the stack
	 * 
	 * @return
	 */
	
	public int size()
	{
		return stack.size();
	}
	
	/**
	 * Check if the stack is empty
	 * @return
	 */
	
	public boolean isEmpty()
	{
		return stack.isEmpty();
	}
	
	/**
	 * Get the data of the head node
	 * 
	 * @return
	 */
	
	public Object peek()
	{
		return stack.getFirst();
	}
	
	/**
	 * Remove and return the node in the stack
	 * @return
	 */
	
	public Object pop()
	{
		return stack.removeFirst();
	}
	
	/**
	 * Insert a new node into the stack
	 * 
	 * @param e
	 */
	
	public void push(Object e)
	{
		stack.insertFirst((N) e);
	}
	
	/**
	 * toString method used to output the stack
	 * 
	 */
	 
	public String toString()
	{
		return "Stack: " + stack;
	}
}
