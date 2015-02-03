package com.sw.search;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

import com.sw.puzzle.Puzzle;

public class DepthFirstSearch extends AbstractSearch {
	public DepthFirstSearch() {
		this.frontier = new ArrayDeque<>();
		this.depthLimit = Integer.MAX_VALUE;
	}	
	
	@Override
	public Node<Puzzle> pop(){
		return ((Deque<Node<Puzzle>>)frontier).remove();
	}
	
	@Override
	public void push(Node<Puzzle> n){
		((Deque<Node<Puzzle>>)frontier).push(n);
	}


}
