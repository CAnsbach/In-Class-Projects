/**
 * Christopher Ansbach
 * 9/21/18
 * CIT 360 - 25
 * Assignment 2: IP Packet Handler
 * 
 * 
 */

package packetHandler;

import java.io.*;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class PacketHandlingDriver 
{

/**
 * Sample Output:
 * 
 * 	Beginning Simulation...
 *	This is message option 5
 *	I am also a packet of data
 *	I am a packet of data
 *	This is a message
 *	Hello
 *	I am also a packet of data
 *	This is a program for CIT 360
 */

	public static void main(String[] args) throws SecurityException, IOException 
	{
		PacketHandlingDriver driver = new PacketHandlingDriver();
		driver.StartSimulation();
	}
	
	public void StartSimulation()
	{
		final int MAX_LOOPS = 10;								//Limits he number of times the simulation is run
		PrintWriter clear;										//Clears the file
		LinkedList<String>  list = new LinkedList<String>();	//Creates a list
		String consumerName = "Consumer";						//Name for Consumer
		String producerName = "Producer 1";						//Name for Producer1
		String producer2Name = "Producer 2";					//Name for Producer2
		String producer3Name = "Producer 3";					//Name for Producer3
		ReentrantLock lock = new ReentrantLock();				//ReentrantLock for runnables
		ExecutorService executor = Executors.newFixedThreadPool(2);	//Executor for the threads
		Runnable consumer = new Consumer(lock, consumerName, list);		//Instance of the Consumer class
		Runnable producer1 = new Producer(lock, producerName, list);	//Instance for Producer1
		Runnable producer2 = new Producer(lock, producer2Name, list);	//Instance for Producer2
		Runnable producer3 = new Producer(lock, producer3Name, list);	//Instance for Producer3
		
		//Clear the file
		try
		{
			clear = new PrintWriter("activity.log");
		}
		
		catch (FileNotFoundException e)
		{
			System.out.println("The given file was not found.");
			e.getStackTrace();
		}
		
		//Begin the simulation
		System.out.println("Beginning Simulation...");
		
		for (int i = 0; i < 10; i++)		//Allows the threads to run for 10 iterations which allowing 20 packets before shutdown
		{
			//Execute the Threads
			executor.execute(producer1);
			executor.execute(producer2);
			executor.execute(producer3);
			executor.execute(consumer);
		}
		executor.shutdown();			//End the simulation.	
	}
}

