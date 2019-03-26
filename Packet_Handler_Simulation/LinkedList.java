/**
 * Christopher Ansbach
 * 9/21/18
 * CIT 360 - 25
 * Assignment 2: IP Packet Handler
 */

package packetHandler;

/**
 * This class is based off of the list we did in class with some modification to certain methods in order to be used for this assignment.
 * 
 * @author Christopher
 *
 * @param <N>
 */
public class LinkedList<N extends Comparable<N>>{

	private Packet<N> head;		//Variable for the first packet
	private Packet<N> tail;		//Variable for the last packet
	private int size;			//Variable for the size of the list.
	
	/**
	 * Constructor for the LinkedList
	 */
	
	public LinkedList() 	//Constructor for a Single Linked List object
	{
		super();			//Calls the super constructor.
		size = 0;			//Set the list size = 0;
	}
	
	/**
	 * Method to insert a packet first in the list
	 * 
	 * @param packet
	 */
	
	public void insertFirst(Packet packet) 
	{
		Packet<N> temp = new Packet(packet.getData(), packet.getFrom(), packet.getTo(), packet.getTime(), packet.getLength());	//Create the new node.
		
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
	
	public void insertFirst(N n)
	{
		Packet<N> temp = new Packet<N>();
		
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
	 * Method to insert the packet last in the list.
	 * This method is ued by the producer in order to insert the generated packets at the tail of the list.
	 * The consumer then processes the packets in the order received.
	 * 
	 * @param packet
	 */

	public void insertLast(Packet packet) 
	{
		Packet<N> temp = packet;		//Create the new node.
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
	 * Inserts a packet of given data at the given position in the list.
	 * 
	 * @param data
	 * @param from
	 * @param to
	 * @param length
	 * @param time
	 * @param index
	 */
	
	public void insert(N data, N from, N to, N length, N time, int index)
	{
		Packet packet = new Packet(data, from, to, length, time);		//Create the new packet
		
		if (index > size || index < 0)				//Check if the index is withing the bounds of the list
			return;
		
		if (isEmpty())								//Check if the list is empty
			head = tail = packet;					//If it is the new packet is the head and tail
		
		if (index == size)							//Check if the packet should be the new tail
			this.insertLast(packet);
		
		if (index == 0)								//Check if the packet should be the new head.
			this.insertFirst(packet);
		
		//The general case of this method
		Packet temp = head;							//Create a temp packet
		
		for (int i = 0; i < index; i++)				//Move through the list until the specified index is reached
		{
			temp = temp.getNext();
		}
		
		//Insert the packet
		packet.setNext(temp.getNext());
		temp.setNext(packet);
	}
	
	/**
	 * Checks if the list is empty
	 * 
	 * @return
	 */
	
	public boolean isEmpty() 
	{	
		return (size == 0);
	}
	
	/**
	 * Gives the size of the list
	 * 
	 * @return
	 */

	public int size() 
	{	
		return size;
	}

	/**
	 * Method used to remove the first packet from the list.
	 * This method is used in the program by the consumer in order to process the packets.
	 * 
	 * @return
	 */
	
	public Packet removeFirst() 
	{
		if(isEmpty())	//If the list is empty return null.
			return null;
		
		else if(size == 1)	//If the list is only one node long
		{
			Packet<N> temp = head;
			head = null;			//Get rid of the head
			tail = null;			//Get rid of the tail (since it was the same as the head)
			size--;					//Reduce the size of the list.
			return temp;			//Return the data from the head.
		}
		
		else
		{
			Packet<N> temp = head;
			head = head.getNext();
			size--;					//Reduce the size of the list.
			return temp;			//Return the head node data.
		}
	}
	
	/**
	 * Removes and returns the tail packet.
	 * @return
	 */
	
	public Packet removeLast() 
	{
		if (isEmpty())
			return null;
		
		else if (size == 1)
		{
			Packet<N> temp = tail;
			head = null;
			tail = null;
			size--;
			return temp;
			
		}
		else 
		{
			Packet<N> temp = new Packet();
			
			while(temp.getNext() != tail)
			{
				temp = temp.getNext();
			}
			
			tail = temp;
			tail.setNext(null);
			size--;
			return temp;
		}
	}

	/**
	 * Removes and returns a specified packet.
	 * @param index
	 * @return
	 */
	
	public Packet remove(int index) 
	{
		if (index < 0 || index > size)				//If there is nothing in the list there is nothing to remove
			return null;
		
		if (index == 0)								//If you are removing the head call the previous method
			return removeFirst();
		
		if (index == size - 1)						//If your are removing the tail call the previous method
			return removeLast();
		
		else 
		{
			Packet<N> temp = head;			//Create the temporary node variable
			
			for (int i = 0; i < index - 1; i++)
			{
				temp = temp.getNext();					//Get to the node before the indexed node
			}
			
			temp.setNext(temp.getNext().getNext());		//Set the next node to the node after the deleted node
			size--;
			return temp;								//Return the data from the deleted node
		}
	}
	
	/**
	 * Checks to see if the packet containing the given data exists.
	 * 
	 * @param e
	 * @return
	 */
	
	public boolean contains(N e) {
		Packet<N> temp = head;					//Set the temp variable equal to the head node.
		
		while(temp != null){					//While the temp variable has a new node.
			
			if(temp.getData().compareTo(e) == 0)//If the node matches the temp node
				
				return true;					//Return true
			
			temp = temp.getNext();				//Go to the next node.
		}
		return false;							//Once all nodes are searched and temp = null return false because it was not found.
	}
	
	/**
	 * Returns the position of the packet that contains the given data.
	 * 
	 * @param e
	 * @return
	 */
	
	public int position(N e) 
	{
		Packet<N> temp = head;			//Create temporary packet
		int counter = 0;				//Counter
		while (temp != null)			//While the temporary packet is not null
		{
			if (temp.getData().compareTo(e) == 0)	//If the temp data value is equal to the given data value
				return counter;			//Return the position of the packet
			else 
				counter++;				//Increase the counter
				temp = temp.getNext();	//Get the next packet
		}
		return -1;					//The packet does not exist
	}
	
	/**
	 * Method used to replace a packet in the list.
	 * 
	 * @param e
	 * @param value
	 * @param value2
	 * @param value3
	 * @param value4
	 * @param value5
	 */
	
	public void replace(N e, N value, N value2, N value3, N value4, N value5)
	{
		if (!contains(e))				//If a packet with the specified data is not in the list
			return;
		
		else
		{
			int position = position(e);						//FInd the position of the old packet
			insert(value, value2, value3, value4, value5, position);		//Inserts a new packet with the new values in place of the old packet.
			remove(position + 1);							//Remove the old packet
		}
	}
	
	/**
	 * Returns the information enclosed in the head packet.
	 * 
	 * @return
	 */
	
	public String getFirst() 
	{
		String allData = "";
		allData += head.getData() + " ";
		allData += head.getFrom() + " ";
		allData += head.getTo() + " ";
		allData += head.getTime() + " ";
		allData += head.getLength() + " ";
		return allData;
	}

	/**
	 * Returns the information enclosed in the tail packet.
	 * 
	 * @return
	 */
	
	public String getLast() 
	{
		String allData = "";
		allData += tail.getData() + " ";
		allData += tail.getFrom() + " ";
		allData += tail.getTo() + " ";
		allData += tail.getTime() + " ";
		allData += tail.getLength() + " ";
		return allData;
	}
	
	/**
	 * Returns the information enclosed in the packet at the given index.
	 * 
	 * @param index
	 * @return
	 */
	
	public String get(int index) 
	{
		Packet<N> temp = new Packet();			//Create the temporary node variable
		
		for (int i = 0; i < index; i++)
		{
			temp = temp.getNext();					//Get to the indexed node
		}
		String allData = "";
		allData += tail.getData() + " ";
		allData += tail.getFrom() + " ";
		allData += tail.getTo() + " ";
		allData += tail.getTime() + " ";
		allData += tail.getLength() + " ";
		return allData;
	}
	
	public N largest()
	{
		Packet<N> temp = head;
		N largest = head.getData();
		
		while (temp != null)
		{
			if (largest.compareTo(temp.getData()) < 0)
			{
				largest = temp.getData();
			}
			
			temp = temp.getNext();
		}
		
		return largest;
	}
	
	/**
	 * Prints the list in a formatted manner.
	 * 
	 */

	public String toString()	//Format the output of the data.
	{
		String str = "";			//Create the string for outputting the data.
		Packet<N> temp = head;		//Set the temp variable to reference the head node.
		while(temp != null)			//While the temp variable references a node
		{
			str += temp.toString();	//Concatenate the node data to the String.
			temp = temp.getNext();	//Move the temp variable to the next node.
		}
		return str;					//Return the String.
	}
}
