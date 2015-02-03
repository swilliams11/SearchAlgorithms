package com.sw.search;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import aima.core.search.framework.Metrics;
import com.sw.puzzle.Action;
import com.sw.puzzle.Puzzle;
import com.sw.search.framework.ActionsFunction;
import com.sw.search.framework.Problem;

public abstract class AbstractSearch implements Search<Node, Puzzle> {
	/* Get actual class name to be printed on */
	// static Logger log = Logger.getLogger(BreadthFirstSearch.class.getName());
	static Logger log = Logger.getLogger("BFSLog");
	static Logger logExplored = Logger.getLogger("ExploredLog");
	// FIFO Queue
	protected Queue<Node<Puzzle>> frontier; // = new LinkedList<>();
	// private Map<String, Node<Puzzle>> explored;
	// private Map<String, Node<Puzzle>> onFrontier;
	protected Set<String> explored = new HashSet<>();
	protected Set<String> onFrontier = new HashSet<>();
	protected Metrics metrics = new Metrics();
	protected final String SOLUTION_LENGTH = "Solution length";
	protected final String TIME = "Time (ms)";
	protected final String HIGHEST_COUNT_ON_QUEUE = "Highest Number of Items In Queue";
	protected final StopWatch watch = new StopWatch();
	private int highestCountOfItemsInQueue = 0;
	protected int depthLimit;

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
		// is the current node the goal then return the node
		if (p.isGoalState(node.getState())) {
			metrics.set(SOLUTION_LENGTH, 1);
			stopAndSetTime();
			return node.getPathFromRoot();
		}

		push(node); // push onto frontier
		setMaxItemsInQueue(frontier.size());
		onFrontier.add(node.getState().toString());
		if (node.getDepth() == depthLimit)
			return null;

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
					if (child.getDepth() < depthLimit
							&& !explored.contains(child.getState().toString())
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

	/*
	 * Stops the stopwatch and sets the metrics.
	 */
	protected void stopAndSetTime() {
		watch.stop();
		metrics.set(TIME, watch.getTime());
		metrics.set(HIGHEST_COUNT_ON_QUEUE, highestCountOfItemsInQueue);
	}

	/*
	 * Tracks the highest number of number of items in the queue.
	 * 
	 * @param number the current size of the queue.
	 */
	protected void setMaxItemsInQueue(int number) {
		highestCountOfItemsInQueue = (number > highestCountOfItemsInQueue) ? number
				: highestCountOfItemsInQueue;
	}

	/*
	 * Returns the metrics for the search.
	 * 
	 * @return returns the metrics for the search.
	 * 
	 * @see com.sw.search.Search#getMetrics()
	 */
	public Metrics getMetrics() {
		return metrics;
	}

	abstract public void push(Node<Puzzle> n);

	abstract public Node<Puzzle> pop();
}
