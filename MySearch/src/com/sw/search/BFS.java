package com.sw.search;

import java.util.LinkedList;

import com.sw.puzzle.Puzzle;

public class BFS extends AbstractSearch {
	
	
	public BFS() {
		this.frontier = new LinkedList<>();
		this.depthLimit = Integer.MAX_VALUE;
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
