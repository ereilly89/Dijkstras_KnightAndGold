package hw4;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class KnightGold {
	public static void main(String[] args) {
		
		int rowLength=0;
		int colLength=0; 
		int knightLoc_x=0;
		int knightLoc_y=0;
		int goldLoc_x=0;
		int goldLoc_y=0;
		
		boolean goldFound = false;
		
		Scanner input = new Scanner(System.in);
		
		//STORE LENGTH OF BOARD, CHECKING FOR ERRORS
		boolean valid=false;
		while(valid==false) {
			valid = true;
			System.out.print("How big is the board? (nxn) where n is: ");
		    rowLength = input.nextInt();
			colLength = rowLength;
			
			//CHECK FOR LENGTH ERROR
			if(rowLength<3) {
				System.out.println("Please enter a length greater than or equal to 3.");
				valid = false;
			}else if(rowLength>200) {
				System.out.println("Please enter a length 200 or less.");
				valid = false;
			}
		}
		
		//STORE KNIGHT POSITION ON BOARD, CHECKING FOR ERRORS
		valid=false;
		while(valid==false) {
			valid = true;
			System.out.print("Knight Position (X Y): ");
			knightLoc_x = input.nextInt()-1;
			knightLoc_y = input.nextInt()-1;
			
			if(knightLoc_x<0||knightLoc_y<0) {
				System.out.println("Please enter positive coordinates.");
				valid = false;
			}else if(knightLoc_x>rowLength-1||knightLoc_y>colLength-1){
				System.out.println("Please enter coordinates within your row and column range.");
				valid = false;
			}
		}
		
		//STORE GOLD POSITION ON BOARD, CHECKING FOR ERRORS
		valid=false;
		while(valid==false) {
			valid = true;
			System.out.print("Gold Position (X Y): ");
			goldLoc_x = input.nextInt()-1;
			goldLoc_y = input.nextInt()-1;
			
			if(goldLoc_x<0||goldLoc_y<0) {
				System.out.println("Please enter positive coordinates.");
				valid = false;
			}else if(goldLoc_x>rowLength-1||goldLoc_y>colLength-1){
				System.out.println("Please enter coordinates within your row and column range.");
				valid = false;
			}
		}
		
		//CREATE ADJACENCY MATRIX & BOARD
		int[][] adjacencyMatrix = new int[rowLength*colLength][rowLength*colLength];
		
		//GENERATE EDGES VIA ADJACENCY MATRIX
		for(int row=0;row<=rowLength*colLength-1;row++) {
			
			for(int col=0;col<=rowLength*colLength-1;col++) {
		
				if(Math.abs(Math.abs(row/rowLength - col/colLength)*Math.abs(row%rowLength - col%colLength))==2) {
					adjacencyMatrix[row][col]=1;
				}
				
			}
		}
		
		/*PRINT THE ADJACENCY MATRIX
		for(int x=0;x<rowLength*colLength;x++) {
			System.out.print("[");
			for(int y=0;y<rowLength*colLength;y++) {
				System.out.print(adjacencyMatrix[x][y]+", ");
			}
			System.out.println("]");
		}*/
		
		//If board is 3x3, make sure the knight or gold isn't in an unreachable location
		if(rowLength == 3 && (getMatrixIndex(knightLoc_x,knightLoc_y,rowLength)==4||getMatrixIndex(goldLoc_x,goldLoc_y,rowLength)==4)) {
			System.out.println("There isn't a path from the knight to the gold.");
		}
		
		//FIND THE SHORTEST PATH FROM THE KNIGHT TO THE GOLD USING DIJKSTRAS ALGORITHM******************
		
		//Declare the table containing vertices, distance, predecessor, and visited to use dijkstras algorithm
		ArrayList<Vertex> dijkstra = new ArrayList<Vertex>();
		
		//Populate the table
		for(int i=0;i<rowLength*colLength;i++) {
			Vertex v1 = new Vertex(i);
			dijkstra.add(v1);
		}
		
		//Initialize the vertex with the knight
		dijkstra.get(getMatrixIndex(knightLoc_x, knightLoc_y, rowLength)).setDistance(0);
		int currentRow = knightLoc_x;
		int currentCol = knightLoc_y;
		
		while(goldFound==false) {
			
			//find the minimum unvisited vertex
			int min = Integer.MAX_VALUE;
			int minVertex = -1;
			for(int i=0;i<dijkstra.size();i++) {
				
				if(dijkstra.get(i).getDistance()<=min && dijkstra.get(i).getVisited()==false) {
					min = dijkstra.get(i).getDistance();
					minVertex = i;
				}
			}
		
			//Set current Row and current Col to the minimum unvisited vertex
			currentRow = getBoardRow(minVertex, rowLength);
			currentCol = getBoardCol(minVertex, colLength);
			int currentMatrixIndex = getMatrixIndex(currentRow, currentCol, rowLength);
			
			//Loop through possible moves
			for(int i=0;i<adjacencyMatrix[0].length;i++) {
	
				//If a possible move
				if(adjacencyMatrix[currentMatrixIndex][i]==1) {
						//If the current distance for the vertex is greater than what it could be
						if(dijkstra.get(i).getDistance()>dijkstra.get(currentMatrixIndex).getDistance()+1) {
							
							//Set new shortest distance
							dijkstra.get(i).setDistance(dijkstra.get(currentMatrixIndex).getDistance()+1);
							
							//Set predecessor
							dijkstra.get(i).setPredecessor(currentMatrixIndex);
							
							//Exit condition
							if(dijkstra.get(getMatrixIndex(goldLoc_x,goldLoc_y,rowLength)).getDistance()!=Integer.MAX_VALUE) {
								goldFound = true;
							}
						}
						//Set current vertex as visited
						dijkstra.get(currentMatrixIndex).setVisited(true);
				}
			}
		}
		
		int shortestPathDistance = dijkstra.get(getMatrixIndex(goldLoc_x,goldLoc_y,rowLength)).getDistance();
		
		//STORE THE SHORTEST PATH INTO STACK
		int knightMatrix = getMatrixIndex(knightLoc_x, knightLoc_y, rowLength);
		int goldMatrix = getMatrixIndex(goldLoc_x, goldLoc_y, rowLength);
		Stack<Integer> stack = new Stack<Integer>();
		
		int predecessor = goldMatrix; 
		stack.add(predecessor);
		
		for(int i=0;i<shortestPathDistance;i++) {//
			predecessor = dijkstra.get(predecessor).getPredecessor();
			stack.push(predecessor);
		}
		
		//PRINT THE SHORTEST PATH FROM STACK
		System.out.println("\nShortest Path To Gold: "+shortestPathDistance);
		System.out.print("Path: ");
		int loop = stack.size();
		for(int i=0;i<loop-1;i++) {
			int nextMatrixIndex = stack.pop();
			System.out.print("("+(getBoardRow(nextMatrixIndex, rowLength)+1)+", "+(getBoardCol(nextMatrixIndex, rowLength)+1)+")-->");
		}
		int nextMatrixIndex = stack.pop();
		System.out.print("("+(getBoardRow(nextMatrixIndex, rowLength)+1)+", "+(getBoardCol(nextMatrixIndex, rowLength)+1)+")");
	}
	
	public static int getMatrixIndex(int row, int col, int rowLength) {
		return rowLength*(row)+col;
	}
	
	public static int getBoardRow(int matrixIndex, int rowLength) {
		return matrixIndex/rowLength;
	}
	
	public static int getBoardCol(int matrixIndex, int colLength) {
		return matrixIndex%colLength;
	}
}


