/**
 * Christopher Ansbach
 * 9/21/18
 * CIT 360 - 25
 * Assignment 2: IP Packet Handler
 */

package packetHandler;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Producer implements Runnable
{
	
	private String name;						//Name of the producer
	private LinkedList list;					//List to insert the packet into
	private ReentrantLock lock;					//Lock to be used for the run method
	private Random rand = new Random();			//New random to be used when generating data.
	
	/**
	 * Constructor used to create an instance of the Producer class.
	 * 
	 * @param lock
	 * @param name
	 * @param list
	 */
	
	public Producer(ReentrantLock lock, String name, LinkedList list)
	{
		this.name = name;
		this.list = list;
		this.lock = lock;
	}
	
	/**
	 * Method used to generate a packet and insert it into the list.
	 * 
	 */
	
	public void generatePacket()
	{
		
		int length = rand.nextInt(100);					//Random length
		int messageNum = rand.nextInt(9);				//Random message
		int from = rand.nextInt(255);					//Random sender
		int to = rand.nextInt(255);						//Random destination
		Date date = new Date();							//Date sent
		String message = "";							//String to hold the message
		
		//Determines the message to be sent
		
		if (messageNum == 0)
			message = "Hello!";
		else if (messageNum == 1)
			message = "This is a message.";
		else if (messageNum == 2)
			message = "This is also a message.";
		else if (messageNum == 3)
			message = "How are you?";
		else if (messageNum == 4)
			message = "I am a packet of data.";
		else if (messageNum == 5)
			message = "This is message option 6.";
		else if (messageNum == 6)
			message = "I am also a packet of data.";
		else if (messageNum == 7)
			message = "This is a program.";
		else if (messageNum == 8)
			message = "This is a program for CIT 360.";
		else if (messageNum == 9)
			message = "This program has threads.";
		
		Packet packet = new Packet(message, from, to, length, date);	//Generate the packet
		
		list.insertLast(packet);					//Insert the packet
	}
	
	/**
	 * Method used to run the Producer class as a thread.
	 * 
	 */
	
	@Override
	public void run() 
	{
		boolean finished = false;			//Boolean to keep the process going
		while(!finished)					//While the process is not finished
		{
			boolean unlocked = lock.tryLock();		//Checks to see if the lock is available
			
			if (unlocked)					//If the lock is available
			{
				try
				{
					int timeInt = rand.nextInt(500);						//Random integer to be used as a sleep time
					long time = Long.parseLong(Integer.toString(timeInt));	//Turns the random integer into a long for use
					generatePacket();					//Generate a packet
					Thread.sleep(time);					//Sleep for a random amount of time
					lock.lock();						//lock the outer lock
				
					try
					{
						generatePacket();				//Generate a packet
						Thread.sleep(time);				//Sleep for time
					}
				
					catch(InterruptedException e)
					{
						System.out.println("An interruption in the thread has occurred in the inner lock.");
						e.printStackTrace();
					}
				
					finally
					{
						lock.unlock();					//unlock the inner lock
					}
					
					finished = true;					//Process is finished
				}
				
				catch (InterruptedException e)
				{
					System.out.println("An interruption in the thread has occurred in the outer lock.");
					e.printStackTrace();
				}
				
				finally
				{
					lock.unlock();						//Unlock the outer lock
				}
			}
			
			else 
			{
				try
				{
					Thread.sleep(500);
				}
				
				catch (InterruptedException e)
				{
					System.out.println("An Interruption has occurred in the outer lock of the thread.");
					e.printStackTrace();
				}
			}
		}
	}
}
