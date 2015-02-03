package com.sw.search;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import com.sw.puzzle.Action;
import com.sw.puzzle.Puzzle;
import com.sw.search.framework.ActionsFunction;
import com.sw.search.framework.Problem;

public class IDFS extends AbstractSearch {

	public IDFS(int depthLimit) {
		if (depthLimit < 0)
			throw new RuntimeException("Invalid depth limit");
		this.frontier = new ArrayDeque<>();
		this.depthLimit = depthLimit;
	}	

	@Override
	public Node<Puzzle> pop() {
		return ((Deque<Node<Puzzle>>) frontier).remove();
	}

	@Override
	public void push(Node<Puzzle> n) {
		((Deque<Node<Puzzle>>) frontier).push(n);
	}
	
	/*@Override
	public List<Node> search(Problem<Puzzle> p) {
		watch.start();
		Node<Puzzle> node = new Node<>(p.getInitialState());
		
		if (p.isGoalState(node.getState())) {
			metrics.set(SOLUTION_LENGTH, 1);
			stopAndSetTime();
			return node.getPathFromRoot();
		}

		push(node); // push onto frontier
		setMaxItemsInQueue(frontier.size());
		onFrontier.add(node.getState().toString());
		if(node.getDepth() == depthLimit) return null;		
		
		while (!frontier.isEmpty()) {			
			node = pop(); // pop the current node
			onFrontier.remove(node.getState().toString());
			log.debug(node.getState() + " - Popped - remaining on stack: "
					+ frontier.size());
			explored.add(node.getState().toString());
			logExplored.debug(node.getState().toString()
					+ " - ADDED to Explored");
			// get actions that can be performed on the Node
			ActionsFunction<Puzzle> f = p.getActionsFunction();
			Puzzle puz = node.getState();
			// perform each action on the parent node to generate children
			for (Action action : f.actions(puz)) {
				// get the successor of the parent by applying the action
				Puzzle childPuzzle = puz.successor(action);
				// create a new node with the child
				Node<Puzzle> child = new Node<>(childPuzzle, node, action, 1);
				log.debug(child.getState() + " - Child - " + action);
				if (!explored.contains(childPuzzle.toString())
						&& p.isGoalState(child.getState())) {
					log.debug("Solution FOUND!");
					List<Node> solution = child.getPathFromRoot();
					metrics.set(SOLUTION_LENGTH, solution.size());
					stopAndSetTime();
					return solution;
				} else {
					if (child.getDepth() < depthLimit && 
						!explored.contains(child.getState().toString())
							&& !onFrontier.contains(child.getState().toString())) {
						push(child);
						setMaxItemsInQueue(frontier.size());
						onFrontier.add(child.getState().toString());
						logExplored.debug(child.getState().toString()
								+ " - ADDED to FRONTIER");
					}
				}				
			}
		}
		this.stopAndSetTime();
		return null;
	}*/

}
