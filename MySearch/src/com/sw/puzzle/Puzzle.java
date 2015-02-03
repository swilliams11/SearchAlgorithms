package com.sw.puzzle;

import java.util.Set;

import com.sw.search.framework.ActionsFunction;
import com.sw.search.framework.ResultFunction;
import com.sw.search.framework.GoalTest;

public interface Puzzle extends GoalTest<Puzzle> {

	public String getState();
	public Puzzle successor(Action a);
	public Set<Action> actions();
}
