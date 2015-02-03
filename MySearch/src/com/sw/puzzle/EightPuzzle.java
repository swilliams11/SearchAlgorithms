package com.sw.puzzle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import com.sw.puzzle.Action;

public class EightPuzzle implements Puzzle {
	//static Logger log = Logger.getLogger(EightPuzzle.class.getName());
	static Logger log = Logger.getLogger("PuzzleLog");
	private final int length = 9;
	private final int rows = 3;
	private final int cols = 3;
	private final int [][] numbers = new int[rows][cols]; //store numbers
	private int zeroPositionR, zeroPositionC, priorPositionR, priorPositionC;
	private Set<Action> legalMoves = new HashSet<>(4);
	private String state;
	
	/*
	 * Create Eight puzzle.
	 */
	@SuppressWarnings("unchecked")
	public EightPuzzle(int [] numbers){
		if (numbers.length != length) throw new RuntimeException("Invalid puzzle length");
		int counter = 0;
		
		for (int r = 0; r < rows; r++) {			
			for(int c = 0; c < cols;  c++){
				this.numbers[r][c] = numbers[c+counter];
				if(numbers[c+counter] == 0){
					zeroPositionC = c;
					zeroPositionR = r;
					priorPositionR = zeroPositionR;
					priorPositionC = zeroPositionC;
				}					
			}
			counter+=cols;
		}		
		updateState();;
	}	
	
	/*
	 * Parses the string and stores in variable.
	 * @param numbers A string of numbers separated by commas.
	 */
	public EightPuzzle(String nums){
		String [] numbers = null;
		if(!nums.contains(",")){			
			numbers = nums.split(" ");
		} else {
			numbers = nums.split(",");
		}
		if (numbers.length != length) throw new RuntimeException("Invalid puzzle length");
		int counter = 0;
		
		for (int r = 0; r < rows; r++) {			
			for(int c = 0; c < cols;  c++){
				this.numbers[r][c] = Integer.parseInt(numbers[c+counter]);
				if(Integer.parseInt(numbers[c+counter]) == 0){
					zeroPositionC = c;
					zeroPositionR = r;
					priorPositionR = zeroPositionR;
					priorPositionC = zeroPositionC;
				}					
			}
			counter+=cols;
		}
		updateState();
	}	

	/*public static void main(String[] args) {
		EightPuzzle puzzle = new EightPuzzle(new int []{1,3,4,8,6,2,7,0,5});
		System.out.println(puzzle);		
	}*/
	private void moveBlank(Action a){
		updateLegalMoves();
		if(legalMoves.contains(a)){
			updateZeroPosition(a);
			swap(a);
			updateState();
			//numbers[zeroPositionR][zeroPositionC] = 0;
		} else {
			throw new RuntimeException("Illegal Move - " + a + "; current state : " + state);
		}
	}
	
	/*
	 * Update the legalMoves list with the legal
	 * moves that can be performed for the next action.
	 */
	private void updateLegalMoves(){
		legalMoves.clear();
		if(zeroPositionR - 1 >= 0){
			legalMoves.add(Action.UP);
		}
		if(zeroPositionR + 1 < rows){
			legalMoves.add(Action.DOWN);
		}
		if(zeroPositionC + 1 < cols){
			legalMoves.add(Action.RIGHT);
		}
		if(zeroPositionC - 1 >= 0){
			legalMoves.add(Action.LEFT);
		}
	}
	
	/*
	 * Update the zero position row or column attribute.
	 */
	private void updateZeroPosition(Action a){
		switch(a){
		case UP:
			priorPositionR = zeroPositionR;
			zeroPositionR -= 1;			
			break;
		case DOWN:
			priorPositionR = zeroPositionR;
			zeroPositionR += 1;
			break;
		case RIGHT:
			priorPositionC = zeroPositionC;
			zeroPositionC += 1;
			break;
		case LEFT:
			priorPositionC = zeroPositionC;
			zeroPositionC -= 1;
		}
	}
	
	/*
	 * Swap the 0 with the number above, below, left or right of it.
	 */
	private void swap(Action a){
		int temp = numbers[zeroPositionR][zeroPositionC]; //number from new zero position
		numbers[priorPositionR][priorPositionC] = temp; //put number in zero's prior position
		numbers[zeroPositionR][zeroPositionC] = 0; //put zero in new position		
	}
	
	/*
	 * NOT USED
	 * Check if the move is legal.
	 * Calculate the new position. 
	 */
	private boolean isLegalMove(Action a){
		int temp = 0;
		switch(a){
			case UP:
				temp = zeroPositionR - 1;
				break;
			case DOWN:
				temp = zeroPositionR + 1;
				break;
			case RIGHT:
				temp = zeroPositionC + 1;
				break;
			case LEFT:
				temp = zeroPositionC - 1;
		}
		return checkMove(temp, a);
	}
	
	/*
	 * NOT USED
	 * Helper function for isLegalMove(). Returns true if
	 * move is legal otherwise it returns false.
	 */
	private boolean checkMove(int number, Action a){
		if(number >= 0 && a == Action.UP){
			return true;
		} else if(number < rows && Action.DOWN == a){
			return true;
		} else if (number >= 0 && Action.LEFT == a){
			return true;
		} else if (number < cols && Action.RIGHT == a){
			return true;
		}
		return false;
	}	
	
	/**
	 * @param puz The puzzle to compare.
	 * 
	 *Check that the puz matches the current puzzle. Return true if the puzzle matches
	 *otherwise return false.
	 *
	 * @return Return true if the puzzle matches
	 *otherwise return false. 
	 */
	@Override
	public boolean isGoalState(Puzzle puz){		
		log.debug(this.toString() 
				+ " ? " + puz.toString() 
				+ " = " + this.toString().equals(puz.toString()));
		return this.state.equals(puz.toString());
		
	}	
	
	/*
	 * Update the state of the puzzle.
	 */
	private void updateState(){
		final StringBuilder s = new StringBuilder();
		//final String NEWLINE = System.getProperty("line.separator");
		int counter = 0;
		for (int r = 0; r < rows ; r++) {
			for (int c = 0; c < cols; c++){
				s.append(numbers[r][c]);
				if((c + counter) < (length - 1)){
					s.append(",");
				}
			}			
			counter += cols;
		}
		this.state = s.toString();
	}
	/**
	 * Return a string representation of the graph.
	 */
	public String toString() {
		return this.state;		
	}

	@Override
	public Set<Action> actions() {
		//if(this.legalMoves.isEmpty()){
			updateLegalMoves();
		//}
		return this.legalMoves;
	}

	@Override
	public String getState() {
		return state;
	}

	@Override
	public Puzzle successor(Action a) {
		Puzzle temp = new EightPuzzle(this.getState());
		((EightPuzzle)temp).moveBlank(a);
		return temp;
	}	
}
