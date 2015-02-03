package com.sw.search;

import java.util.PriorityQueue;
import java.util.Comparator;

public class GreedyBestFirstSearch extends AbstractHeuristicSearch {
	
	public GreedyBestFirstSearch() {
		this.frontier = new PriorityQueue<>(20, new Comparator<Node>(){
			@Override
			public int compare(Node o1, Node o2) {
				return (int)(o1.getHueristic() - o2.getHueristic());
			}			
		});
	}	
}
