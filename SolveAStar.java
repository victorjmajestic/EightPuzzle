package project1Package;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

public class SolveAStar {
    
	private Node goal;
	private static int maxNodes;
	
	private class Node {
		private eightPuzzle puzzle;
		private int numMoves;
		private Node previous;
		
		public Node(eightPuzzle puzzle) {
			this.puzzle = puzzle;
			numMoves = 0;
			previous = null;
		}
	}
	
	private class setQueueOrder implements Comparator<Node> {		
		private String heuristic;
		
		public setQueueOrder(String heuristic) {
			this.heuristic = heuristic;
		}
		
		public int compare(Node n1, Node n2) {
			int hx1 = 0;
			int hx2 = 0;
			if (heuristic == "h1") {
				hx1 = n1.puzzle.calculateH1() + n1.numMoves;
				hx2 = n2.puzzle.calculateH1() + n2.numMoves;
			}
			else if (heuristic == "h2") {
				hx1 = n1.puzzle.calculateH2() + n1.numMoves;
				hx2 = n2.puzzle.calculateH2() + n2.numMoves;
			}
			if (hx1 > hx2)
				return 1;
			if (hx2 > hx1)
				return -1;
			else
				return 0;
			
		}
	}
	
	public SolveAStar(eightPuzzle puzzle, String heuristic) {
		setQueueOrder queueOrder = new setQueueOrder(heuristic);
		PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>(maxNodes, queueOrder);
		Node node = new Node(puzzle);
		priorityQueue.add(node);
		Node minimum = priorityQueue.peek();
		while (!minimum.puzzle.checkGoal()) {
			for (eightPuzzle puzzleState : minimum.puzzle.findAdjacentBoards()) {
				//System.out.println("test");
				if (minimum.previous == null || !puzzleState.equals(minimum.previous.puzzle)) {
					Node pointer = new Node(puzzleState);
					pointer.numMoves = minimum.numMoves++;
					pointer.previous = minimum;
					priorityQueue.add(pointer);
				}
			}
			minimum = priorityQueue.peek();
		}
		if (minimum.puzzle.checkGoal())
			this.goal = minimum;
	}
	
	public Iterable<eightPuzzle> solutionSequence() {
		Stack<eightPuzzle> solution = new Stack<eightPuzzle>();
		for (Node node = goal; node != null; node = node.previous) {
			solution.push(node.puzzle);
		}
		return solution;
	}
	
	public int numMoves() {
		return goal.numMoves;
	}
	
    public static int maxNodes(int n) {
    	maxNodes = n;
    	return maxNodes;
    }
    
    public static void main (String[] args) {
    	int[] puzzle = new int[9];
    	int[] puzzleSet = {1, 3, 5, 4, 0, 2, 8, 7, 6};
    	eightPuzzle testPuzzle = new eightPuzzle(puzzle);
    	testPuzzle.setState(puzzleSet);
    	testPuzzle.printState();
    	testPuzzle.moveLeft();
    	testPuzzle.printState();
    	testPuzzle.moveRight();
    	testPuzzle.printState();
    	testPuzzle.moveRight();
    	testPuzzle.printState();
    	testPuzzle.moveRight();
    	testPuzzle.printState();
    	testPuzzle.moveUp();
    	testPuzzle.printState();
    	testPuzzle.moveUp();
    	testPuzzle.printState();
    	testPuzzle.moveDown();
    	testPuzzle.printState();
    	testPuzzle.randomizeState(100);
    	System.out.println("");
    	testPuzzle.printState();
    	maxNodes(3);
    	SolveAStar aStar = new SolveAStar(testPuzzle, "h2");
    	System.out.println("The total number of moves = " + aStar.numMoves());
    	System.out.println("The solution sequence is as follows:");
    	System.out.println("");
    	System.out.println(aStar.solutionSequence());
    }
}