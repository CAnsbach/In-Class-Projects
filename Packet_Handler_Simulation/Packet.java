/**
 * Christopher Ansbach
 * 9/21/18
 * CIT 360 - 25
 * Assignment 2: IP Packet Handler
 */

package packetHandler;

public class Packet<N>
{
	private N data;							//Data for the packet
	private N from;							//Sender information
	private N to;							//Destination information
	private N time;							//Time the packet was sent
	private N length;						//Length of the packet
	private Packet<N> next;					//Next Packet

	/**
	 * Constructor1 for packet
	 * 
	 * @param data
	 * @param from
	 * @param to
	 * @param time
	 * @param length
	 * @param next
	 */
	
	public Packet(N data, N from, N to, N time, N length, Packet<N> next) 
	{
		super();
		this.data = data;
		this.from = from;
		this.to= to;
		this.time = time;
		this.length = length;
		this.next = next;
	}
	
	/**
	 * Constructor2 for packet
	 * 
	 * @param data
	 * @param from
	 * @param to
	 * @param length
	 * @param time
	 */
	
	public Packet(N data, N from, N to, N length, N time) 
	{
		super();
		this.data = data;
		this.from = from;
		this.to= to;
		this.time = time;
		this.length = length;
		this.next = null;
	}
	
	/**
	 * Constructor for an empty packet
	 */
	
	public Packet() 
	{
		super();
	}
	
	//Getters and setters for the information provided by the packet
	
	public N getData() 
	{
		return data;
	}
	
	public void setData(N data) 
	{
		this.data = data;
	}
	
	public N getFrom() 
	{
		return from;
	}
	
	public void setFrom(N from) 
	{
		this.from = from;
	}
	
	public N getTo() 
	{
		return to;
	}
	
	public void setTo(N to) 
	{
		this.to = to;
	}
	
	public N getTime() 
	{
		return time;
	}
	
	public void setTime(N time) 
	{
		this.time = time;
	}
	
	public N getLength() 
	{
		return length;
	}
	
	public void setLength(N size) 
	{
		this.length = length;
	}
	
	public Packet<N> getNext() 
	{
		return next;
	}
	
	public void setNext(Packet<N> next) 
	{
		this.next = next;
	}
	
	/**
	 * The data (message) will be printed on the console while the program is being executed.
	 * 
	 */
	@Override
	public String toString() 
	{
		return (String) data;	//Cast the data to a String for output
	}
}
