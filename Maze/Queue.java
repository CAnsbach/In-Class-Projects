/**
 * Christopher Ansbach
 * 11/4/18
 * CIT 360 - 25
 * Assignment 4: Mazes
 */

public class Queue<N>
{
	private LinkedList<N> queue = new LinkedList();		//List used to make the stack
	
	/**
	 * Constructor for queue
	 * 
	 */
	
	public Queue()
	{
		this.queue = queue;
	}
	
	/**
	 * Return the size of the queue
	 * 
	 * @return
	 */
	
	public int size()
	{
		return queue.size();
	}
	
	/**
	 * Check if the queue is empty
	 * @return
	 */
	
	public boolean isEmpty()
	{
		return queue.isEmpty();
	}
	
	/**
	 * Get the data of the head node
	 * 
	 * @return
	 */
	
	public Object peek()
	{
		return queue.getFirst();
	}
	
	/**
	 * Remove and return the node in the queue
	 * @return
	 */
	
	public Object dequeue()
	{
		return queue.removeFirst();
	}
	
	/**
	 * Insert a new node into the queue
	 * 
	 * @param e
	 */
	
	public void enqueue(Object e)
	{
		queue.insertLast((N) e);
	}
	
	/**
	 * toString method used to output the queue
	 * 
	 */
	 
	public String toString()
	{
		return "Queue: " + queue;
	}
}
