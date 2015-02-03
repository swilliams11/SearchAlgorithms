package com.sw.search.framework;

import com.sw.puzzle.Puzzle;

public class GoalTestImpl implements GoalTest<Puzzle> {

	@Override
	public boolean isGoalState(Puzzle state) {
		return false;
	}

}
