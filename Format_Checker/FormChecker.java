/**
 * Christopher Ansbach
 * 10/11/18
 * CIT 360 - 25
 * Assignment 3: Simple Stack Assignment
 */

package simpleStack;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FormChecker 
{
	private String[] tokensArray;		//Create a string array to hold tokens
	private Stack stack = new Stack();	//Create a stack
	private Object  popped;				//Object to hold the popped object from the stack
	public FormChecker() 				//Constructor
	{

	}
	
	/**
	 * Input and check the problems in the file
	 */
	
	public void inputAndCheckFile()
	{
		String problem;					//String to hold the problem
		try 
		{
			File file = new File("problem1.txt");		//New file
			Scanner fileIn = new Scanner(file);			//New scanner for the file
			System.out.println("Checking: "+ file +"\n");
			
			while (fileIn.hasNext())					//While there are problems in the file
			{
				problem = fileIn.nextLine();			//Get the problems
				if (formatChecker(problem))				//Check if they are well formed or not
					System.out.println("Correct");
				else
					System.out.println("Incorrect");
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
	 * Method used to check if the problem is well formed or not
	 * 
	 * @param problem
	 * @return
	 */
	
	public boolean formatChecker(String problem)
	{
		boolean isCorrect = true;						//Boolean to return
		int counter = 0;								//Counter used to fill the array
		
		//Add white space to the problem
		problem = problem.replace("(", " ( ").replace(")", " ) ").replace("[", " [ ").replace("]", " ] ").replace("{", " { ").replace("}", " } ")
				.replace("+", " + ").replace("-", " - ").replace("%", " % ").replace("*", " * ").replace("/", " / ");
		
		StringTokenizer tokens = new StringTokenizer(problem, " ");			//Tokenize the problem
		
		tokensArray = new String[tokens.countTokens()];						//Create an array to hold the tokens
		
		//Fill the array with the tokens
		while (tokens.hasMoreTokens())
		{
			tokensArray[counter] = tokens.nextToken();
			counter++;
		}
		
		stack = new Stack();
		
		//Loop used to check the problems
		for (int i = 0; i < tokensArray.length; i++)
		{
			if (i == 0 && tokensArray[i].matches("[\\*\\%\\/\\-\\+]"))			//If there is an operator at the beginning
				return isCorrect = false;												//Ill-formed
			
			else if (tokensArray[i].matches("[\\{\\(\\[]"))	//If there is an opening grouping symbol
				stack.push(tokensArray[i]);						//Push it into the stack
			
			else if (tokensArray[i].matches("[\\}\\)\\]]"))	//If there is a closing grouping symbol
			{
				if (stack.isEmpty())							//If the stack is empty
					return isCorrect = false;							//Ill-formed
				
				//Check for mismatching grouping symbols and if there are the problem is Ill-formed
				else if (tokensArray[i].equals(")") && !stack.peek().equals("("))
					return isCorrect = false;
				
				else if (tokensArray[i].equals("]") && !stack.peek().equals("["))
					return isCorrect = false;
				
				else if (tokensArray[i].equals("}") && !stack.peek().equals("{"))
					return isCorrect = false;
				
				popped = stack.pop();							//pop the stack
			}
			
			//If the element is an operator
			else if (tokensArray[i].matches("[\\*\\%\\/\\-\\+]"))
			{
				//If there is an operator followed by nothing
				if (i == tokensArray.length -1)
					return isCorrect = false;						//Ill-formed
			
				//If there is an operator followed by another operator symbol
				else if (tokensArray[i + 1].matches("[\\*\\%\\/\\-\\+]"))
					return isCorrect = false;						//Ill-formed
			
				//If there is an operator followed by a closed grouping symbol
				else if (tokensArray[i + 1].matches("[\\}\\)\\]]"))
					return isCorrect = false;						//Ill-formed
			}
		}
		
		if (!stack.isEmpty())			//If the stack is not empty in the end
			return isCorrect = false;	//Ill-formed
		
		return isCorrect;				//Return true
	}
}
