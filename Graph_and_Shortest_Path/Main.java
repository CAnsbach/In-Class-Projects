/**
 * Christopher Ansbach
 * 12/3/18
 * CIT 360 - 25
 * Assignment 5: Graphs
 */

package application;

import javafx.scene.paint.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * This class is based on the SimpleGraphics class we were given for this assignment.
 * 
 * @author Christopher
 */

public class Main extends Application 
{
	ImportData data = new ImportData();			//Instance of the ImportData
	
	Scene scene = null;							//New scene for the GUI
	final int WIDTH = 600;						//Width
	final int HEIGHT = 600;						//Height
	int points;									//Number of points
	int lines;									//Number of lines
	
	/**
	 * Main method to run the GUI program
	 * 
	 * @param args
	 */
	
    public static void main(String[] args) 
    {
    	
        launch(args);							//Launch the program
    }
	
    /**
     * Start method to generate the GUI
     */
    
    @Override
    public void start(Stage stage) 
    {
    	data.inputFileData();									//Input the data and make the graph
    	points = data.numVertices();							//Get the number of points and lines in the graph
    	lines = data.numEdges();
    	
        stage.setTitle("Graph for Assignment 5");				//Title for GUI
        Group root = new Group();								//New group
        Canvas canvas = new Canvas(WIDTH, HEIGHT);				//Create new canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();		//New GraphicsContext to output graph
        
    	drawImage(gc);											//Create the graph
        
        root.getChildren().add(canvas);							//Add the canvas to the group
        stage.setScene(new Scene(root));						//Create a new scene
        stage.show();											//Show the scene
    }
    
    /**
     * Method use to create the graph image
     * 
     * @param gc
     */
    
    private void drawImage(GraphicsContext gc) 
    {
        int[] xValues = data.getPointsX();					//Arrays to hold the x and y values of the points in the graph
        int[] yValues = data.getPointsY();
        ArrayList<Point> list = new ArrayList<Point>();		//ArrayList of points in the graph
        
        //For every point in the graph, add it to the ArrayList
        for (int i = 0; i < points; i++)
           list.add(new Point(xValues[i], yValues[i]));
        

        drawLines(gc, list);        //Draw the lines between the points
        highlightPath(gc, list);	//Highlight the shortest path
        drawPoints(gc, list);		//Draw the points (done last to make the text look better)
    }
    
    /**
     * Method used to create the points (or vertices)
     * @param gc
     * @param list
     */
    
    private void drawPoints(GraphicsContext gc, List<Point> list) 
    {
    	//For every point in the array, draw it.
    	for(int i = 0; i < list.size(); i++)
    		drawPoint(gc, list.get(i), i);			//Call the drawPoint method
    }
    
    /**
     * Method used to draw the point (or vertex)
     * 
     * @param gc
     * @param p
     * @param index
     */
    
    private void drawPoint(GraphicsContext gc, Point p, int index) 
    {
    	int width = 20;   											//Point width

    	gc.setFill(Color.GREEN);											//Make the point green
        gc.fillOval(p.getX() - width/2, p.getY()- width/2, width, width);	//Fill it
        gc.setFill(Color.BLACK);											//Make the text black
        gc.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));		//Change the font
    	gc.fillText("Pt" + index, p.getX() + 20, p.getY() + 20);			//Place it near point
    }
    
    /**
     * Method used to draw the lines (or edges) that connect the points (or vertices)
     * 
     * @param gc
     * @param list
     */
    
    private void drawLines(GraphicsContext gc, List<Point> list) 
    {
    	Point p1, p2;											//Points to have the connection
    	int[] neighbors;										//Array to hold their neighbors

    	//For every point, get it's neighbors
    	for(int i = 0; i < points; i++) 
    	{
    		p1 = list.get(i);				//Get the point from the list
    		neighbors = data.neighbors(i);	//Get the neighbors of the point
    		
    		//If the point has neighbors
    		if (neighbors != null)
    		{
    			//For every neighbor, draw a line
    			for (int j = 0; j < neighbors.length; j ++)
    			{
    				p2 = list.get(neighbors[j]);	//Get the neighbor
    				drawLine(gc, p1, p2);			//Draw the line
    			}
    		}
    	}
    }
    
    /**
     * Method used to draw the line (or edge) connecting the points (or vertices)
     * 
     * @param gc
     * @param p1
     * @param p2
     */
    
    private void drawLine(GraphicsContext gc, Point p1, Point p2)
    {
        gc.setStroke(Color.ORANGE);				//Make the line orange
        gc.setLineWidth(5);						//Set the line width to 5
        gc.strokeLine(p1.x, p1.y, p2.x, p2.y);	//Draw the line
    }
    
    /**
     * Method used to highlight the shortest path from one point (or vertex) to another
     * @param gc
     * @param list
     */
    
    private void highlightPath(GraphicsContext gc, List<Point> list)
    {
    	Point p1, p2;											//Points to have the line highlighted
    	
    	//If there is a path, highlight it
    	if (data.shortestPath() != null)
    	{
    		int[] path = data.shortestPath();				//Array to hold the points in the path
    		int index = 1;									//Index to get the next point in the array
    		
    		//For each point in the path
    		for(int i = 0; i < path.length - 1; i++)
    		{
    			p1 = list.get(path[i]);					//Get the point
    			p2 = list.get(path[index]);				//Get the next point
    		
    			highlightLine(gc, p1, p2);				//Highlight the path between them
    			
    			index++;								//Increment the index
    		}
    		
    		//Output the path
    		gc.setFill(Color.BLACK);										//Black text
    		gc.setTextAlign(TextAlignment.CENTER);							//Centered
    		gc.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));	//Font change
    		gc.fillText("Path: " + path(), WIDTH/2, HEIGHT-100);			//Output text in middle bottom of the window
    	}
    	
    	//Else, output a message saying no path
    	else
    	{
    		//Same formatting as path output
    		gc.setFill(Color.BLACK);
    		gc.setTextAlign(TextAlignment.CENTER);
    		gc.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
    		gc.fillText("No path avaiable", WIDTH/2, HEIGHT-100);
    	}
    }
    
    /**
     * Method used to return the path in a string form
     * 
     * @return
     */
    
    private String path()
    {
    	String path = "";						//String to hold the path
    	int[] pathArray = data.shortestPath();	//Path in array
    	
    	//For every element in the array, concatenate it to the path
    	for(int i = 0; i < pathArray.length; i++)
    		path += "Pt" + pathArray[i] + " ";
    	
    	return path;							//Return the path
    }
    
    /**
     * Method used to highlight the line between two points (or vertices)
     * 
     * @param gc
     * @param p1
     * @param p2
     */
    
    private void highlightLine(GraphicsContext gc, Point p1, Point p2)
    {
        gc.setStroke(Color.RED);				//Make the line red
        gc.setLineWidth(5);						//Width is the same
        gc.strokeLine(p1.x, p1.y, p2.x, p2.y);	//Draw the line
    }
}
