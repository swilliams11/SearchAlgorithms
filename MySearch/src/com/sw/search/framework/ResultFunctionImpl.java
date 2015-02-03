package com.sw.search.framework;

import com.sw.puzzle.Action;
import com.sw.puzzle.Puzzle;

public class ResultFunctionImpl implements ResultFunction<Puzzle> {

	@Override
	public Puzzle result(Puzzle s, Action a) {
		return s.successor(a);
	}

}
