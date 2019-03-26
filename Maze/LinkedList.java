/**
 * Christopher Ansbach
 * 11/4/18
 * CIT 360 - 25
 * Assignment 4: Mazes
 */



public class LinkedList<N>{

	private Node<N> head;				//Head
	private Node<N> tail;				//Tail
	private int size;					//Size
	
	/**
	 * Constructor
	 * 
	 */
	
	public LinkedList() //Constructor for a Single Linked List object
	{
		super();			//Calls the super constructor.
		size = 0;			//Set the list size = 0;
	}
	
	/**
	 * Insert the node at the head.
	 * 
	 * @param e
	 */
	public void insertFirst(N e) 
	{
		Node temp = new Node(e, null);	//Create the new node.
		
		if(isEmpty())				//If the list is empty the new node is the head and tail.
		{
			head = temp;
			tail = temp;
		}
		
		else
		{
			temp.setNext(head);		//Set the new node next element equal to the next node
			head = temp;			//Make the new node the head.
		}
		
		size++;				//Increase the list size
	}

	/**
	 * Insert the node at the tail.
	 * 
	 * @param e
	 */
	
	public void insertLast(N e) 
	{
		Node temp = new Node(e, null);		//Create the new node.
		if(isEmpty())				//If the list is empty the new node is the head and tail.
		{
			head = temp;
			tail = temp;
		}
		
		else
		{
			tail.setNext(temp);		//set the current tail node's next element to the new node.
			tail = temp;			//Make the new node the tail.
		}
		
		size++;				//Increase the list size.
	}


	/**
	 * Insert the element e at the position index
	 */
	public void insert(N e, int index) {
		if (index > size  || index < 0)
			return;		//If the index is outside of the boundaries of the list.
		
		if(this.isEmpty())
		{
			head = tail = new Node(e);		//Set the head and tail equal to the new node.
		}
		
		if(index == size)
			this.insertLast(e);					//If the given index is at the end of the list (tail).
		
		if(index == 0)
			this.insertFirst(e);				//If the given index is at the front of the list (head).
		
		//The general case, the insertion is inserted at any other point of the array.
		
		Node<N> temp = head;					//Set the temp variable to represent the head node.
		
		for(int i = 0; i < index - 1;  i++)
			temp = temp.getNext();				//Locate the Node before the insertion point.
				
		Node<N> newNode = new Node(e);			//Create the new node to be inserted into the list.
		newNode.setNext(temp.getNext());		//Set the next element of the node to the next node using the temp's next element.
		temp.setNext(newNode);					//Set the temp's node next element to the newly inserted node.
	}
	
	/**
	 * Check is the list is empty
	 * 
	 * @return
	 */
	
	public boolean isEmpty() 
	{	
		return (size == 0);
	}
	
	/**
	 * Return the size of the list
	 * 
	 * @return
	 */
	
	public int size() 
	{	
		return size;
	}
	
	/**
	 * Remove the head
	 * @return
	 */
	
	public N removeFirst() 
	{
		N data;			//Create the variable data.
		
		if(isEmpty())	//If the list is empty return null.
			return null;
		
		else if(size == 1)	//If the list is only one node long
		{
			data = head.getData();	//Get the data from the head
			head = null;			//Get rid of the head
			tail = null;			//Get rid of the tail (since it was the same as the head)
			size--;					//Reduce the size of the list.
			return data;			//Return the data from the head.
		}
		
		else
		{
			data = head.getData();	//Get the data from the head node
			head = head.getNext();	//The head is now the next node in the list.
			size--;					//Reduce the size of the list.
			return data;			//Return the head node data.
		}
	}
	
	/**
	 * Remove the tail
	 * 
	 * @return
	 */
	
	public N removeLast() 
	{
		N data;					//variable to hold the data
		
		if (isEmpty())			//If it is empty
		{
			return null;		//return null
		}
		
		else if (size == 1)		//If the size is 1
		{
			data = tail.getData();	//Get the data from the tail
			head = null;			//The head and tail are now null
			tail = null;
			size--;					//Decrease the size
			return data;			//return the data
			
		}
		
		else 
		{
			data = tail.getData();			//Get the data from the tail
			Node<N> temp = new Node();		//Temp node
			while(temp.getNext() != tail)	//Find the node behind the tail
			{
				temp = temp.getNext();
			}
			
			tail = temp;					//The temp node is now the tail
			tail.setNext(null);				//set the next to null, deleting the previous tail node
			size--;							//Decrease the tail size
			return data;					//Return the data
		}
	}

	/**
	 * Removes the node at the given index
	 * 
	 * @param index
	 * @return
	 */
	public N remove(int index) 
	{
		if (index < 0 || index > size)				//If there is nothing in the list there is nothing to remove
			return null;
		
		if (index == 0)								//If you are removing the head call the previous method
			return removeFirst();
		
		if (index == size - 1)						//If your are removing the tail call the previous method
			return removeLast();
		
		else 
		{
			Node<N> temp = head;			//Create the temporary node variable
			
			for (int i = 0; i < index - 1; i++)
			{
				temp = temp.getNext();					//Get to the node before the indexed node
			}
			
			N data = temp.getNext().getData();			//Get the data of the node you are deleting
			
			temp.setNext(temp.getNext().getNext());		//Set the next node to the node after the deleted node
			
			return data;								//Return the data from the deleted node
		}
	}
	
	/**
	 * Checks to see if the given value is in the list
	 * 
	 * @param e
	 * @return
	 */
	
	public boolean contains(N e) {
		Node<N> temp = head;					//Set the temp variable equal to the head node.
		
		while(temp != null){					//While the temp variable has a new node.
			
			if((temp.getData()).equals(e))//If the node matches the temp node
				
				return true;					//Return true
			
			temp = temp.getNext();				//Go to the next node.
		}
		return false;							//Once all nodes are searched and temp = null return false because it was not found.
	}
	
	/**
	 * returns the position of the node containing the given value
	 * 
	 * @param e
	 * @return
	 */
	
	public int position(N e) 
	{
		Node<N> temp = head;			//Create a temp node
		int counter = 0;				//Counter to keep track of the position
		while (temp != null)			//If the node is in the list, find it
		{
			if (temp.getData().equals(e))		//If you found the node
				return counter;			//Return the position
			else 
				counter++;				//Increase the counter
				temp = temp.getNext();	//Get the next node
		}
		return -1;						//If the node was not found return -1
	}

	/**
	 * Replaces the node of the given value with a node of new given value
	 * 
	 * @param e
	 * @param value
	 */
	
	public void replace(N e, N value) 
	{
		if (!contains(e))			//If the value is not in the list.
			return;					//Done
		
		else
		{
			int position = position(e);		//Find the position of the old node
			insert(value, position);		//Insert the new node into the position
			remove(position + 1);			//Remove the old node
		}
	}
	
	/**
	 * Get the data in the head without removing it
	 * 
	 * @return
	 */
	
	public N getFirst() 
	{
		return head.getData();		//return the data
	}
	
	/**
	 * Get the data in the tail without removing it
	 * 
	 * @return
	 */

	public N getLast() 
	{
		return tail.getData();			//Return the data
	}
	
	/**
	 * Get the data from the node at the given index
	 * 
	 * @param index
	 * @return
	 */
	
	public N get(int index) 
	{
		Node<N> temp = new Node();			//Create the temporary node variable
		
		for (int i = 0; i < index; i++)
		{
			temp = temp.getNext();					//Get to the indexed node
		}
		return temp.getData();				//Return the data of the indexed node
	}

	/**
	 * toString method to output the list
	 * 
	 */
	public String toString()	//Format the output of the data.
	{
		String str = "";			//Create the string for outputting the data.
		Node temp = head;		//Set the temp variable to reference the head node.
		while(temp != null)			//While the temp variable references a node
		{
			str += temp.toString();	//Concatenate the node data to the String.
			temp = temp.getNext();	//Move the temp variable to the next node.
		}
		return str;					//Return the String.
	}
}