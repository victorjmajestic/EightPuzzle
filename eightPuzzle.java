package project1Package;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class eightPuzzle {
	
	private int[] puzzle = new int[9];
	private int[] goal = {0,1,2,3,4,5,6,7,8};
	
	public eightPuzzle(int[] puzzle) {
		puzzle = this.puzzle;
	}
	
	public boolean setState(int[] setPuzzle) {
		if (setPuzzle.length == puzzle.length) {
			for (int i=0; i < puzzle.length; i++) {
				puzzle[i] = setPuzzle[i];
			}
			return true;
		}
		return false;
	}
	
	public void printState() {
		System.out.println(Arrays.toString(puzzle));	
	}
	
	public boolean moveLeft() {
		for (int i = 0; i < puzzle.length; i++) {
			if (puzzle[i]==0 && i != 0 && i != 3 && i != 6) {
				puzzle[i]=puzzle[i-1];
				puzzle[i-1]=0;
				return true;
			}
		}
		return false;
	}
	
	public boolean moveRight() {
		for (int i = 0; i < puzzle.length; i++) {
			if (puzzle[i]==0 && i != 2 && i != 5 && i != 8) {
				puzzle[i]=puzzle[i+1];
				puzzle[i+1]=0;
				return true;
			}
		}
		return false;
	}
	
	public boolean moveUp() {
		for (int i = 3; i < puzzle.length; i++) {
			if (puzzle[i]==0) {
				puzzle[i]=puzzle[i-3];
				puzzle[i-3]=0;
				return true;
			}
		}
		return false;
	}
	
	public boolean moveDown() {
		for (int i = 0; i < puzzle.length - 3; i++) {
			if (puzzle[i]==0) {
				puzzle[i]=puzzle[i+3];
				puzzle[i+3]=0;
				return true;
			}
		}
		return false;
	}
	
	public void randomizeState(int n) {
		setState(goal);
		printState();
		Random randomNum = new Random();
		int i = 0;
		while (i < n) {
			int random = 1 + randomNum.nextInt(4);
			if (random == 1 && moveLeft()) {
				i++;
				//System.out.println("left");
				//printState();
			}
			else if (random == 2 && moveRight()) {
				i++;
				//System.out.println("right");
				//printState();
			}
			else if (random == 3 && moveUp()) {
				i++;
				//System.out.println("up");
				//printState();
			}
			else if (random == 4 && moveDown()) {
				i++;
				//System.out.println("down");
				//printState();
			}
		}
	}
	
	public int calculateH1() {
        int wrongBlocks = 0;
        for (int i = 0; i < puzzle.length; i++)    
                if (puzzle[i] != i && puzzle[i] != 0)
                    wrongBlocks++;
        return wrongBlocks;
    }
	
	public int calculateH2() {
        int sum = 0;
        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] != i && puzzle[i] != 0)
                sum += calculateManhattan(puzzle[i], i);
        }
        return sum;
    }
	
	public int calculateH3() {
		int sum = 0;
		int wrongBlocks = 0;
		for (int i = 0; i < puzzle.length; i++) {
			if (puzzle[i] != i && puzzle[i] != 0) {
				sum += calculateManhattan(puzzle[i], i);
				wrongBlocks++;
			}
		}
		return sum + wrongBlocks;
	}
    
    private int calculateManhattan(int goal, int current) {
        int row;
        int column;
        row = Math.abs(goal / 3 - current / 3);
        column = Math.abs(goal % 3 - current % 3);
        return row + column;
    }
    
    public Iterable<eightPuzzle> findAdjacentBoards() {
    	eightPuzzle adjacent;
    	Queue<eightPuzzle> puzzleQueue = new LinkedList<eightPuzzle>();
    	if (moveLeft()) {
    		adjacent = new eightPuzzle(puzzle);
    		moveRight();
    		puzzleQueue.add(adjacent);
    	}
    	if (moveRight()) {
    		adjacent = new eightPuzzle(puzzle);
    		moveLeft();
    		puzzleQueue.add(adjacent);
    	}
    	if (moveUp()) {
    		adjacent = new eightPuzzle(puzzle);
    		moveDown();
    		puzzleQueue.add(adjacent);
    	}
    	if (moveDown()) {
    		adjacent = new eightPuzzle(puzzle);
    		moveUp();
    		puzzleQueue.add(adjacent);
    	}
    	return puzzleQueue;
    }
    
    public boolean checkGoal() {
    	if (puzzle == goal)
    		return true;
    	return false;
    }
}
