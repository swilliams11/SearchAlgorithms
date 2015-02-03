package com.sw.search;

import java.util.List;
import com.sw.puzzle.Action;
import com.sw.puzzle.Puzzle;
import com.sw.search.framework.ActionsFunction;
import com.sw.search.framework.HeuristicFunction;
import com.sw.search.framework.Problem;
import com.sw.search.framework.StepCostFunction;

public abstract class AbstractHeuristicSearch extends AbstractSearch{	
	
	/*
	 * Searches the problem for a solution using the standard Bread First Search
	 * Algorithm.
	 * 
	 * @param Problem to search
	 * 
	 * @return List of nodes solutions or null if no solution is found.
	 * 
	 * @see com.sw.search.Search#search(com.sw.search.framework.Problem)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Node> search(Problem<Puzzle> p) {
		watch.start();
		Node<Puzzle> node = new Node<>(p.getInitialState());
		HeuristicFunction<Node> hf = p.getHeuristicFunction();
		StepCostFunction<Node> sf = p.getStepCostFunction();
		
		// is the current node the goal then return the node
		if (p.isGoalState(node.getState())) {
			metrics.set(SOLUTION_LENGTH, 1);
			stopAndSetTime();
			return node.getPathFromRoot();
		}

		hf.updateHeuristic(node);
		push(node); // push onto frontier
		setMaxItemsInQueue(frontier.size());
		onFrontier.add(node.getState().toString());

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
			// perform each action on the parent node
			for (Action action : f.actions(puz)) {
				// get the successor of the parent by applying the action
				Puzzle childPuzzle = puz.successor(action);
				// create a new node with the child
				Node<Puzzle> child = 
						new Node<>(childPuzzle, node, action, sf.c());
				hf.updateHeuristic(child);
				log.debug(child.getState() + " - Child - " + action);

				if (!explored.contains(childPuzzle.toString())
						&& p.isGoalState(child.getState())) {
					log.debug("Solution FOUND!");
					List<Node> solution = child.getPathFromRoot();
					metrics.set(SOLUTION_LENGTH, solution.size());
					stopAndSetTime();
					return solution;
				} else {
					if (!explored.contains(child.getState().toString())
							&& !onFrontier
									.contains(child.getState().toString())) {
						push(child);
						setMaxItemsInQueue(frontier.size());
						onFrontier.add(child.getState().toString());
						logExplored.debug(child.getState().toString()
								+ " - ADDED to FRONTIER");
					}
				}
			}
		}
		stopAndSetTime();
		return null;
	}
	
	@Override
	public Node<Puzzle> pop(){
		return frontier.poll();
	}
	
	@Override
	public void push(Node<Puzzle> n){
		this.frontier.add(n);
	}

}
