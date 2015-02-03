package com.sw.search.framework;

import java.util.Set;

import com.sw.puzzle.Action;
import com.sw.puzzle.Puzzle;

public class ActionFunctionImpl implements ActionsFunction<Puzzle> {
	@Override
	public Set<Action> actions(Puzzle s){
		return s.actions();
	}
}
