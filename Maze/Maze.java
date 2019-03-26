/**
 * Christopher Ansbach
 * 11/4/18
 * CIT 360 - 25
 * Assignment 4: Mazes
 */

import java.awt.Point;
import java.util.*;

public class Maze 
{
	private Random rand = new Random();				//Random used throughout program
	private Stack<Point> stack;						//Stack for DFS algorithm
	private Queue<Point> queue;						//Stack for BFS algorithm
	private Point point = new Point();				//Point used to create points that are used in the array.
													//(x, y) = (column, row)
	private int[][] maze, mazeCopy, distanceArray;	//Arrays used to generate maze and keep track of distance
	private boolean[][] visited, visitedCopy; 		//Arrays used to track visited elements
	private int columns, rows, currentR, currentC;	//Integers used to generate and solve the maze.		
	private int min = 10, max = 15;					//Integers used determine the size of the array
	
	
	/**
	 * Method used to generate the maze and call the methods to solve it.
	 * 
	 */
	public void generateMaze()
	{
		Scanner keyboard = new Scanner(System.in);	//Scanner used to get input to stop the loop
		String response;							//String to hold the input
		
		//Loop to allow for multiple maze generations
		do								 
		{
			stack = new Stack();								//New stack
			queue = new Queue();								//New queue
			rows = rand.nextInt((max - min) + 1) + min; 		//Determine the number of columns
			columns = rand.nextInt((max - min) + 1) + min;		//Determine the number of rows
	
			/*	Generate the maze and a copy to ensure there is no interference during execution of the 
			 * DFS and BFS algorithms.
			 * The row and column variable are increased by two to make room for the border
			 */
			maze = new int[rows + 2][columns + 2];
			mazeCopy = new int[rows + 2][columns + 2];
			distanceArray = new int[rows + 2][columns + 2];
			visited = new boolean[rows + 2][columns + 2];
			visitedCopy = new boolean[rows + 2][columns + 2];
		
			//Loop to generate the border to prevent the algorithms for leaving the array
			for (int i = 0; i < maze.length; i++)
			{
				for (int j = 0; j < maze[i].length; j++)
				{
					if (i == 0 || j == 0)						//Top and bottom borders
					{
						//Set visited = to true and maze to -1 to show walls
						visited[i][j] = true;
						visitedCopy[i][j] = true;
						maze[i][j] = -1;
						mazeCopy[i][j] = -1;
						distanceArray[i][j] = -1;
					}
				
					else if (i == visited.length - 1 || j == visited[i].length - 1)		//Side borders
					{
						visited[i][j] = true;
						visitedCopy[i][j] = true;
						maze[i][j] = -1;
						mazeCopy[i][j] = -1;
						distanceArray[i][j] = -1;
					}
				}
			}
			//Call the startAndEnd method to generate a random start and end point and solve the maze
			startAndEnd();
		
			System.out.println("Would you like to generate anyother maze?");	//Output to ask if loop should continue
			response = keyboard.next();											//Get response
		}
		while (response.equalsIgnoreCase("Yes"));								//If it is yes continue
	}
	
	
	/**
	 * Method used to generate a random start and end point and solve the array
	 *
	 */
	
	private void startAndEnd()
	{
		int startRow, startColumn, endRow, endColumn;					//Variables to help the start and end information
		startRow = rand.nextInt((maze.length - 1) - 1) + 1;				//Random start row
		startColumn = rand.nextInt((maze[startRow].length - 1) - 1) + 1;//Random start column
		endRow = rand.nextInt((maze.length - 1) - 1) + 1;				//Random end row
		endColumn = rand.nextInt((maze[endRow].length - 1) - 1) + 1;	//Random end column
		
		addWalls();														//Add the walls
		
		while(maze[startRow][startColumn] == -1)						//If the start point is on a wall...
		{
			//Generate a new start point
			startRow = rand.nextInt((maze.length - 1) - 1) + 1;
			startColumn = rand.nextInt((maze[startRow].length - 1) - 1) + 1;
			//Continue generation until the start point is not on a wall
		}
		
		while (maze[endRow][endColumn] == -1)							//If the end point is on a wall...
		{
			//Generate a new end point
			endRow = rand.nextInt((maze.length - 1) - 1) + 1;
			endColumn = rand.nextInt((maze[endRow].length - 1) - 1) + 1;				
			//Continue generation until the end point is not on a wall
		}
		
		maze[startRow][startColumn] = 55;				//Graphical representation of the start point
		mazeCopy[startRow][startColumn] = 55;			//Same on the copy
		visited[startRow][startColumn] = true;			//Set the start point as visited
		visitedCopy[startRow][startColumn] = true;
		maze[endRow][endColumn] = 77;					//Graphical representation of the end point
		mazeCopy[endRow][endColumn] = 77;				//Same on the copy
		
		System.out.println("Maze:");
		outputMaze(maze);								//Output the maze
		
		
		//Output format: 3 = path, 55 = start, 77 = end
		System.out.println("\nMaze with path (Depth):");
		solveMazeDepth(startRow, startColumn, endRow, endColumn);		//Solve the maze using the DFS algorithm
		outputMaze(maze);
		
		//Output format: path is represented by the distance from the start, 55 = start, 77 = end
		System.out.println("\nMaze with path (Breadth):");
		solveMazeBreadth(startRow, startColumn, endRow, endColumn);		//Solve the maze using the BFS algorithm		
		outputMaze(mazeCopy);
	}
	
	/**
	 * Method used to generate the walls
	 * 
	 */
	
	private void addWalls()
	{
		double density;					//Density to determine number of walls
		int decider = rand.nextInt(3);	//Random decider to determine what density
		int area = rows * columns;		//Calculate the area
		int startR, startC;				//Integers to determine the start of the wall
		double aTD;						//Area Times Density - Determines number of blocks
		
		//Decide which density
		if (decider == 0)
			density = .2;				//20%
		else if (decider == 1)
			density = .25;				//25%
		else if (decider == 2)
			density = .3;				//30%
		else
			density = .35;				//35%
		
		aTD = area * density;			//Determine number of blocks
		int numOfBlocks = (int) aTD;	//Cast it to an integer for implementation
		
		//Generate the walls based on the number of blocks left to fulfill the density
		while (numOfBlocks != 0)
		{
			startR = rand.nextInt((maze.length - 1) - 1) + 1;			//Random start for the wall
			startC = rand.nextInt((maze[startR].length - 1) - 1) + 1;
			
			while(maze[startR][startC] == -1)							//If the wall starts on a wall
			{
				//Get a new start to ensure density is reached
				startR = rand.nextInt((maze.length - 1) - 1) + 1;
				startC = rand.nextInt((maze[startR].length - 1) - 1) + 1;
			}
			
			currentR = startR;											//Current Row
			currentC = startC;											//Current Column
			
			//Implement the wall at the current point
			visited[currentR][currentC] = true;
			visitedCopy[currentR][currentC] = true;
			maze[currentR][currentC] = -1;
			mazeCopy[currentR][currentC] = -1;
			distanceArray[currentR][currentC] = -1;
			
			//Each wall will be approx. 5 blocks long (can be less based on the number of blocks left)
			for(int i = 0; i < 5; i++)
			{
				//If there are blocks to place and north is currently not a wall
				if (numOfBlocks != 0 && !checkNorth(visited))
				{
					//Generate a wall to the North
					currentR -= 1;
					visited[currentR][currentC] = true;
					visitedCopy[currentR][currentC] = true;
					maze[currentR][currentC] = -1;
					mazeCopy[currentR][currentC] = -1;
					distanceArray[currentR][currentC] = -1;
					numOfBlocks--;								//Reduce the number of blocks
				}
				//If there are blocks to place and south is currently not a wall
				else if (numOfBlocks != 0 && !checkSouth(visited))
				{
					currentR += 1;
					visited[currentR][currentC] = true;
					visitedCopy[currentR][currentC] = true;
					maze[currentR][currentC] = -1;
					mazeCopy[currentR][currentC] = -1;
					distanceArray[currentR][currentC] = -1;
					numOfBlocks--;								//Reduce the number of blocks
				}
				//If there are blocks to place and east is currently not a wall
				else if (numOfBlocks != 0 && !checkEast(visited))
				{
					currentC += 1;
					visited[currentR][currentC] = true;
					visitedCopy[currentR][currentC] = true;
					maze[currentR][currentC] = -1;
					mazeCopy[currentR][currentC] = -1;
					distanceArray[currentR][currentC] = -1;
					numOfBlocks--;								//Reduce the number of blocks
				}
				//If there are blocks to place and west is currently not a wall
				else if (numOfBlocks != 0 && !checkWest(visited))
				{
					currentC -= 1;
					visited[currentR][currentC] = true;
					visitedCopy[currentR][currentC] = true;
					maze[currentR][currentC] = -1;
					mazeCopy[currentR][currentC] = -1;
					distanceArray[currentR][currentC] = -1;
					numOfBlocks--;								//Reduce the number of blocks
				}
			}
		}
		
	}
	
	/**
	 * Method used to solve the maze using the Depth First Search Algorithm
	 * 
	 * @param startRow
	 * @param startColumn
	 * @param endRow
	 * @param endColumn
	 */
	private void solveMazeDepth(int startRow, int startColumn, int endRow, int endColumn)
	{
		Point end = new Point(endColumn, endRow);			//End point
		Point start = new Point(startColumn, startRow);		//Start point
		stack.push(start);									//Push the start point to the stack
		currentR = startRow;								//Current Row
		currentC = startColumn;								//Current Column
		boolean done = false;								//Boolean to end the loop
		
		//While the end has not been found
		while (!done)
		{
			
			//Peek into the stack to see if the end has been found
			if (!stack.isEmpty() && stack.peek().equals(end))
			{
				//Output the path
				System.out.println(outputPathDepth());

				done = true;		//End has been found and the stack has been output
			}
			
			//Check the element to the north
			else if (!checkNorth(visited))
			{
				//If it is not visited, visit it and add it to the stack
				currentR -= 1;
				point = new Point(currentC,currentR);
				stack.push(point);
				visited[currentR][currentC] = true;
				maze[currentR][currentC] = 3;
			}
			
			//Check the element to the north
			else if (!checkSouth(visited))
			{
				//If it is not visited, visit it and add it to the stack
				currentR += 1;
				point = new Point(currentC,currentR);
				stack.push(point);
				visited[currentR][currentC] = true;
				maze[currentR][currentC] = 3;
			}
			
			//Check the element to the north
			else if (!checkEast(visited))
			{
				//If it is not visited, visit it and add it to the stack
				currentC += 1;
				point = new Point(currentC,currentR);
				stack.push(point);
				visited[currentR][currentC] = true;
				maze[currentR][currentC] = 3;
			}
			
			//Check the element to the north
			else if (!checkWest(visited))
			{
				//If it is not visited, visit it and add it to the stack
				currentC -= 1;
				point = new Point(currentC,currentR);
				stack.push(point);
				visited[currentR][currentC] = true;
				maze[currentR][currentC] = 3;
			}
			
			//If the stack is not empty
			else if (!stack.isEmpty())
			{
				//Pop the point and check it's neighbors again
				Point temp = (Point) stack.pop();
				currentC = (int) temp.getX();				//Get the column
				currentR = (int) temp.getY();				//Get the row
				maze[currentR][currentC] = 0;
				
				//If you can move north, south, east, or west, add it to the stack again and continue the loop
				if (!checkNorth(visited) || !checkSouth(visited) || !checkEast(visited) || !checkWest(visited))
				{
					point = new Point(currentC,currentR);
					
					//If the point is the beginning, do not alter the number representation and push it to the stack
					if (maze[currentR][currentC] == 55)
						stack.push(point);
					
					else
					{
						maze[currentR][currentC] = 3;
						stack.push(point);
					}
				}
			}
			
			//If the stack is empty
			else
			{
				done = true;						//Done
				System.out.println("No Path");   	//There is no path to the end
			}
		}
	}
	
	/**
	 * Method used to output the path of the Depth First Search Algorithm in a formatted manner
	 * 
	 * @return
	 */
	private String outputPathDepth()
	{
		String[] pathArray;					//String array to help formatted points
		Point temp = new Point();			//Point to hold the points from the stack
		String path = "";					//String that is returned
		int stackMax = stack.size();
		
		pathArray = new String[stack.size()];	//The size of the path is equal to the size of the stack
		
		//Add the points to the array backwards to make the path go from beginning to end
		for (int i = stack.size() -1; i >= 0; i--)
		{
			temp = (Point) stack.pop();				//Pop the point
			
			if (stack.size() == stackMax - 1)		//The first item in the stack is the end point
				maze[(int) temp.getY()][(int) temp.getX()] = 77;	//Label it as such
			
			//Add it to the stack in a formatted way
			pathArray[i] = "(" + (int) temp.getX() + ", " + (int) temp.getY() + ")";
		}
		
		//Create the String to be returned by appending the points in the array to the string
		for (int i = 0; i < pathArray.length; i++)
		{
			if (i == 0)
				path += "Depth Path: " + pathArray[i] + ", ";
			
			else if (i > 0 && i < pathArray.length - 1)
				path += pathArray[i] + ", ";
			
			else
				path += pathArray[i];
		}
		
		return path;		//Return the path
	}
	
	/**
	 * Method used to solve the maze using the Breadth First Search Algorithm
	 * 
	 * @param startRow		Used to make the start point
	 * @param startColumn
	 * @param endRow		Used to make the end point
	 * @param endColumn
	 */
	
	private void solveMazeBreadth(int startRow, int startColumn, int endRow, int endColumn)
	{
		Point end = new Point(endColumn, endRow);			//End point
		Point start = new Point(startColumn, startRow);		//Start point
		queue.enqueue(start);								//Enqueue the start point
		currentR = startRow;								//Current Row
		currentC = startColumn;								//Current Column
		int distance = 0;									//Distance used for the distance array (used for path creation)
		boolean done = false, path = true;					//Booleans to determine when the loop is done and if there is a path
		
		//While the queue is not empty and the algorithm is not done
		while (!done && !queue.isEmpty())
		{
			Point temp = (Point) queue.dequeue();		//Dequeue the point from the queue
			currentC = (int) temp.getX();				//Update the current row
			currentR = (int) temp.getY();				//Update the current column
			distance  = distanceArray[currentR][currentC] + 1;	//Update the distance
			
			//If the end has been reached
			if (temp.equals(end))
			{
				done = true;		//Done
			}

			//Visit all possible neighbors and add them to the queue for processing
			else 
			{
				//If you can go North
				if (!checkNorth(visitedCopy))
				{
					//Visit the point and add it to the queue
					currentR -= 1;
					point = new Point(currentC,currentR);
					queue.enqueue(point);
					visitedCopy[currentR][currentC] = true;
					//mazeCopy[currentR][currentC] = 3;
					distanceArray[currentR][currentC] = distance;	//The point's distance from the start
					currentR += 1;		//Return to the original point
				}

				//If you can go South
				if (!checkSouth(visitedCopy))
				{
					//Visit the point and add it to the queue
					currentR += 1;
					point = new Point(currentC,currentR);
					queue.enqueue(point);
					visitedCopy[currentR][currentC] = true;
					//mazeCopy[currentR][currentC] = 3;
					distanceArray[currentR][currentC] = distance;	//The point's distance from the start
					currentR -= 1;		//Return to the original point
				}

				//If you can go East
				if (!checkEast(visitedCopy))
				{
					//Visit the point and add it to the queue
					currentC += 1;
					point = new Point(currentC,currentR);
					queue.enqueue(point);
					visitedCopy[currentR][currentC] = true;
					//mazeCopy[currentR][currentC] = 3;
					distanceArray[currentR][currentC] = distance;	//The point's distance from the start
					currentC -= 1;		//Return to the original point
				}

				//If you can go West
				if (!checkWest(visitedCopy))
				{
					//Visit the point and add it to the queue
					currentC -= 1;
					point = new Point(currentC,currentR);
					queue.enqueue(point);
					visitedCopy[currentR][currentC] = true;
					//mazeCopy[currentR][currentC] = 3;
					distanceArray[currentR][currentC] = distance;	//The point's distance from the start
					currentC += 1;		//Return to the original point
				}
				
				//If there are no points to process and the loop is not done
				if (queue.isEmpty() && !done)
				{
					done = true;				//Finished
					path = false;				//There is no path to output
					System.out.println("No Path");
				}
			}
		}
		//If there is a path
		if (path)
			//Output it
			System.out.println(outputPathBreadth(end, distanceArray[(int) end.getY()][(int) end.getX()]));
	}
	
	/**
	 * Method used to output the path for the Breadth First Search Algorithm
	 * 
	 * @param end				The end point
	 * @param distance			The distance to the end from the start
	 * @return
	 */
	
	private String outputPathBreadth(Point end, int distance)
	{
		String path = "";							//String to hold the formatted path
		Point temp = new Point();					//Point used to hold the information for path generation
		int row = (int) end.getY();					//Current row
		int column = (int) end.getX();				//Current column
		int index = 0;								//index for the array
		String[] orderPath;							//Array to hold the points

		
		orderPath = new String[distance + 1];		//The size of the array is equal to the distance + 1
		orderPath[index] = "(" + column +", " + row + ")";	//Insert the end point first
		mazeCopy[row][column] = 77;					//End point
		index++;									//Increment the index
		
		
		//While distance is greater than zero
		while (distance > 0)
		{
			
			//If the distance to the North is equal to the distance - 1
			if (distanceArray[row - 1][column] == distance - 1)
			{
				//Add it to the array in a formatted manner
				temp = new Point(column, row - 1);
				orderPath[index] = "(" + (int) temp.getX() + ", " + (int) temp.getY() + ")";
				row -= 1;			//Go to that point
				distance -= 1;		//Decrement the distance
				mazeCopy[row][column] = distance;
				
				if(distance == 0)				//If the distance is zero, it is the beginning
					mazeCopy[row][column] = 55;				
				
				index++;			//Increment the index
			}
			
			//If the distance to the South is equal to the distance - 1
			else if (distanceArray[row + 1][column] == distance - 1)
			{
				//Add it to the array in a formatted manner
				temp = new Point(column, row + 1);
				orderPath[index] = "(" + (int) temp.getX() + ", " + (int) temp.getY() + ")";
				row += 1;			//Go to that point
				distance -= 1;		//Decrement the distance
				mazeCopy[row][column] = distance;	
				
				if(distance == 0)				//If the distance is zero, it is the beginning
					mazeCopy[row][column] = 55;				
				index++;			//Increment the index
			}			
			
			//If the distance to the East is equal to the distance - 1
			else if (distanceArray[row][column + 1] == distance - 1)
			{
				//Add it to the array in a formatted manner
				temp = new Point(column + 1, row);
				orderPath[index] = "(" + (int) temp.getX() + ", " + (int) temp.getY() + ")";
				column += 1;		//Go to that point
				distance -= 1;		//Decrement the distance
				mazeCopy[row][column] = distance;				
				
				if(distance == 0)				//If the distance is zero, it is the beginning
					mazeCopy[row][column] = 55;		
					
				index++;			//Increment the index
			}			
			
			//If the distance to the West is equal to the distance - 1
			else if (distanceArray[row][column - 1] == distance - 1)
			{
				//Add it to the array in a formatted manner
				temp = new Point(column - 1, row);
				orderPath[index] = "(" + (int) temp.getX() + ", " + (int) temp.getY() + ")";
				column -= 1;		//Go to that point
				distance -= 1;		//Decrement the distance
				mazeCopy[row][column] = distance;				
				
				if(distance == 0)				//If the distance is zero, it is the beginning
					mazeCopy[row][column] = 55;				
				
				index++;			//Increment the index
			}
		}
		
		//Append the points to the String from the array backwards to make the path go from beginning to end
		for (int i = orderPath.length - 1; i >= 0; i--)
		{
			if (i == orderPath.length - 1)
				path += "Shortest Path: " + orderPath[i] + ", ";
			else if (i > 0 && i < orderPath.length - 1)
				path += orderPath[i] + ", ";
			else
				path += orderPath[i];
		}
		return path;		//Return the path
	}
	
	/**
	 * Method used to check if the North element is visited
	 * 
	 * @param visited
	 * @return
	 */
	
	private boolean checkNorth(boolean[][] visited)
	{
		return visited[currentR - 1][currentC];
	}
	
	/**
	 * Method used to check if the South element is visited
	 * 
	 * @param visited
	 * @return
	 */
	
	private boolean checkSouth(boolean[][] visited)
	{
		return visited[currentR + 1][currentC];
	}
	
	/**
	 * Method used to check if the East element is visited
	 * 
	 * @param visited
	 * @return
	 */
	
	private boolean checkEast(boolean[][] visited)
	{
		return visited[currentR][currentC + 1];
	}
	
	/**
	 * Method used to check if the West element is visited
	 * 
	 * @param visited
	 * @return
	 */
	private boolean checkWest(boolean[][] visited)
	{
		return visited[currentR][currentC - 1];
	}
	
	/**
	 * Method used to output the given maze in a formatted manner
	 * 
	 * @param maze
	 */
	
	public void outputMaze(int[][] maze)
	{
		for (int i = 0; i < maze.length; i++)
		{
			for (int j = 0; j < maze[i].length; j++)
			{
				if (j == 0)
				{
					System.out.printf("|%s", maze[i][j]);
				}
				
				else if (j == visited[i].length - 1)
				{
					System.out.printf("%5s|\n", maze[i][j]);
				}
				
				else
					System.out.printf("%5s", maze[i][j]);
			}
		}
	}
}