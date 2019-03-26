/**
 * Christopher Ansbach
 * 10/11/18
 * CIT 360 - 25
 * Assignment 3: Simple Stack Assignment
 */

package simpleStack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class PostFixed 
{
	private String[] tokensArray;				//Create a string array to hold tokens
	private Stack stack = new Stack();			//Create a stack
	private Object popped;						//Object to hold the popped objects from the stack
	public PostFixed() 							//Constructor
	{

	}
	
	/**
	 * Method used to input the information from the file and then perform the calculations.
	 * 
	 */
	
	public void inputAndCalculateFile()
	{
		String problem;						//String to hold the problem
		try 
		{
			File file = new File("problem2.txt");	//Create a new file
			Scanner fileIn = new Scanner(file);		//Scanner to scan the file
			System.out.println("Checking and calculating: " + file  + "\n");
			
			while (fileIn.hasNext())				//While there is another equation
			{
				problem = fileIn.nextLine();		//Get the  current equation
				if (isWellFormed(problem))			//If the equation is well formed
					System.out.println(solve(problem));	//Output the answer
				else
					System.out.println("Ill-Formed");	//The equation is ill-formed
			}
			System.out.println("\nFinished\n");
		}
		
		//Catch exceptions
		catch (FileNotFoundException e)
		{
			System.out.println("File not found");
			e.printStackTrace();
		}
		
		catch (Exception e)
		{
			System.out.println("An exception has occurred");
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if the given equation is well formed.
	 * 
	 * @param problem
	 * @return
	 */
	
	public boolean isWellFormed(String problem)
	{
		boolean isCorrect = false;				//Boolean to return
		int counter = 0;						//Counter used to make the array
		
		//Create spaces
		problem = problem.replace("+", " + ").replace("-", " - ").replace("%", " % ")
				.replace("*", " * ").replace("/", " / ");
		
		StringTokenizer tokens = new StringTokenizer(problem, " ");		//Tokenize the string
		
		tokensArray = new String[tokens.countTokens()];					//create an array to hold the tokens
		
		//Put the tokens into the array
		while (tokens.hasMoreTokens())
		{
			tokensArray[counter] = tokens.nextToken();
			counter++;
		}
		
		//Loop to check if the equation is well formed
		for (int i = 0; i < tokensArray.length; i++)
		{
			if (i == 0 && tokensArray[i].matches("[\\*\\%\\/\\-\\+]"))		//IF the element is an operator in the first spot
				return isCorrect;											//Ill-formed
			
			else if (!tokensArray[i].matches("[\\*\\%\\/\\-\\+]"))			//If the element is not an operator
				stack.push(Integer.parseInt(tokensArray[i]));				//Push it into the stack
			
			else if (tokensArray[i].matches("[\\*\\%\\/\\-\\+]"))			//If the element is an operator.
			{
				if (stack.isEmpty())										//If the stack is empty
					return isCorrect;										//Ill-formed
					
				else 
				{
					popped =  stack.pop();									//Pop from the stack
					if (stack.isEmpty())									//If the stack empty
						return isCorrect;									//Ill-formed
					else 
					{
						popped = stack.pop();								//Pop form the stack
						stack.push(popped);									//push a value back into the stack
					}
				}
			}
		}
		if (stack.size() > 1)
			return isCorrect;
		
		isCorrect = true;													//The equation is well formed.
		return isCorrect;													//return true
	}

	/**
	 * Method used to calculate the answer to the given postfix equation.
	 * 
	 * @param problem
	 * @return
	 */
	
	public int solve(String problem)
	{
		int counter = 0;		//Counter used to create the array
		Object number1;			//Objects used to hold the numbers for calculations.
		Object number2;
		Object answer = 0;
		
		problem = problem.replace("+", " + ").replace("-", " - ").replace("%", " % ")
				.replace("*", " * ").replace("/", " / ");	//Create whitespace
		
		StringTokenizer tokens = new StringTokenizer(problem, " ");	//create tokenizer
		
		tokensArray = new String[tokens.countTokens()];				//Create a new array to hold tokens
		
		while (tokens.hasMoreTokens())						//While there are move tokens
		{
			tokensArray[counter] = tokens.nextToken();		//Add them to the array
			counter++;										//Increment the counter
		}
		
		//Loop used to calculate the answer
		for (int i = 0; i < tokensArray.length; i++)
		{
			if (!tokensArray[i].matches("[\\*\\%\\/\\-\\+]"))		//If the element is not an operator.
				stack.push(Integer.parseInt(tokensArray[i]));		//Push the element.
			
			else if (tokensArray[i].matches("[\\*\\%\\/\\-\\+]"))	//If the element is an operator.
			{
				number1 =  stack.pop();								//Pop the first number.

				number2 = stack.pop();								//Pop the second number.
				
				//Check which operator to use and then perform the calculation and push the calculated value back into the stack.
				if (tokensArray[i].equals("+"))
					stack.push( (int) number2 + (int) number1);
				else if (tokensArray[i].equals("-"))
					stack.push((int) number2 - (int)number1);
				else if (tokensArray[i].equals("/"))
					stack.push((int) number2 / (int)number1);
				else if (tokensArray[i].equals("*"))
					stack.push((int)number2 * (int) number1);
				else if (tokensArray[i].equals("%"))
					stack.push((int) number2 % (int) number1);
			}
		}
		answer = stack.pop();			//Pop the answer.
		return (int) answer;			//Return the answer casted as an integer.
	}
}
