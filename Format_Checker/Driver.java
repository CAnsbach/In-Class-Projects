/**
 * Christopher Ansbach
 * 10/11/18
 * CIT 360 - 25
 * Assignment 3: Simple Stack Assignment
 */

package simpleStack;

public class Driver {

	public static void main(String[] args) 
	{
		FormChecker fc = new FormChecker();				//Create an instance of the FormChecker class
		fc.inputAndCheckFile();							//Check the file.
		
		PostFixed pf = new PostFixed();					//Instance of the PostFixed Class
		pf.inputAndCalculateFile();						//Perform the check and calculation.
	}
}
