package com.sw.search;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Astar extends AbstractHeuristicSearch {
	/*
	 * Constructor to create a new priority queue.	
	 */
	public Astar() {
		this.frontier = new PriorityQueue<>(20, new Comparator<Node>(){
			@Override
			public int compare(Node o1, Node o2) {
				return (int)(o1.getHueristic() - o2.getHueristic());
			}			
		});
	}
	
	/*
	 * Create new A* search with a comparator.
	 * 
	 * @param the comparator used to order the prioity queue.
	 */
	public Astar(Comparator<Node> cmp) {
		this.frontier = new PriorityQueue<>(20, cmp);
	}
}
