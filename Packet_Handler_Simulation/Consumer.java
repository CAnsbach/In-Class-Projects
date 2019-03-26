/**
 * Christopher Ansbach
 * 9/21/18
 * CIT 360 - 25
 * Assignment 2: IP Packet Handler
 */

package packetHandler;

import java.io.BufferedWriter;
import java.io.*;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

public class Consumer implements Runnable
{
	private ReentrantLock lock;						//ReentrantLock for the run method.
	private String logFile = "activity.log";		//Name of the file used to log activities
	private String name;							//Name of the consumer
	private LinkedList list;						//Name of the list

	/**
	 * Constructor for Consumer
	 * 
	 * @param lock
	 * @param name
	 * @param list
	 */
	
	public Consumer(ReentrantLock lock, String name, LinkedList list)
	{
		this.name = name;
		this.list = list;
		this.lock = lock;
	}
	
	/**
	 * Writes the log entries to the file.
	 * 
	 * @param fPacket
	 */
	
	public void writeToFile(String fPacket)
	{
		try (PrintWriter writer = new PrintWriter(new BufferedWriter (new FileWriter("Activity.log", true))))	//Create the writer for the file
		{
			writer.println(fPacket);				//Print the formatted packet to the log file.
		} 
		
		catch (IOException e) 
		{
			System.out.println("An IOException has occurred.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Method used to process the packets
	 * 
	 */
	
	public void packetProcessing()
	{
		Packet packet = list.removeFirst();				//Remove the packet from the list
		writeToFile(formatPacket(packet));					//Output the information to a file
		
		if (((Integer)packet.getTo()) >= 0 && ((Integer)packet.getTo()) <=99)	//Output the information to the console if to is between 0 and 99
			System.out.println(packet.toString());
	}
	
	/**
	 * Formats the packet for output to a file.
	 * 
	 * @param packet
	 * @return
	 */
	
	public String formatPacket(Packet packet)
	{
		String fPacket = "";		//String to hold the formatted packet
		
		fPacket = fPacket.format("[From: " + packet.getFrom() + ", To: " + packet.getTo() + ", Date: " + packet.getTime() + "]");	//Format the output
		return fPacket;				//Return the formatted packet
	}
	
	/**
	 * Method to run the consumer class as a thread.
	 * 
	 */
	
	@Override
	public void run() 
	{
		boolean finished = false;			//Boolean to stop the processes when false
		while(!finished)					//While the program is not finished
		{
			boolean unlocked = lock.tryLock();		//Checks to see if the lock is available
			
			if (unlocked)			//If the outer lock is available
			{
				try
				{
					packetProcessing();	//Process a packet
					Thread.sleep(1500);	//Sleep for 1.5 seconds
					lock.lock();		//Lock the outer lock
				
					try
					{
						packetProcessing();	//Process the packet
						Thread.sleep(1500);	//Sleep for 1.5 seconds
					}
				
					catch(InterruptedException e)
					{
						System.out.println("An interruption in the thread has occurred in the inner lock.");
						e.printStackTrace();
					}
				
					finally
					{
						lock.unlock();		//Unlock the inner lock
					}
					
					finished = true;	//Finished the process
				}
				
				catch (InterruptedException e)
				{
					System.out.println("An interruption in the thread has occurred in the outer lock.");
					e.printStackTrace();
				}
				
				finally
				{
					lock.unlock();	//Unlock the outer lock for use
				}
			}
			
			//If a lock is not available
			else 
			{
				try
				{
					Thread.sleep(500);		//Sleep and then check again
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
