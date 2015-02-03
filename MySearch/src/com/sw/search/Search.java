package com.sw.search;

import java.util.List;
import aima.core.search.framework.Metrics;
import com.sw.puzzle.Action;
import com.sw.search.framework.Problem;

public interface Search<T,U> {
	public List<T> search(Problem<U> p);	
	public Metrics getMetrics();
}
