package com.sw.puzzle;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.sw.search.framework.GoalTest;

public class EightPuzzleTest {
	Puzzle puzzle;
	Puzzle puzzle2;
	Puzzle puzzle3;// prof. easy
	GoalTest<Puzzle> goal;
	final String _123456780 = "1,2,3,4,5,6,7,8,0";
	final String _123456708 = "1,2,3,4,5,6,7,0,8";
	final String _123456078 = "1,2,3,4,5,6,0,7,8";
	final String _123450678 = "1,2,3,4,5,0,6,7,8";
	final String _123405678 = "1,2,3,4,0,5,6,7,8";
	final String _123045678 = "1,2,3,0,4,5,6,7,8";
	final String _120345678 = "1,2,0,3,4,5,6,7,8";
	final String _102345678 = "1,0,2,3,4,5,6,7,8";
	final String _012345678 = "0,1,2,3,4,5,6,7,8";

	@Before
	public void setUp() throws Exception {
		puzzle = new EightPuzzle("1,2,3,4,0,5,6,7,8");
		puzzle2 = new EightPuzzle("1,2,0,3,4,5,6,7,8");
		puzzle3 = new EightPuzzle("1,3,4,8,6,2,7,0,5");// prof. easy
		goal = new EightPuzzle("1,2,3,4,5,6,7,8,0");
	}

	@Test
	public void testEightPuzzleString() {
		fail("not implemented");

	}

	@Test
	public void testIsGoalState() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		String s = puzzle.toString();
		assertEquals("1,2,3,4,0,5,6,7,8", s);

		s = puzzle2.toString();
		assertEquals("1,2,0,3,4,5,6,7,8", s);

		s = puzzle3.toString();
		assertEquals("1,3,4,8,6,2,7,0,5", s);
	}

	@Test
	public void testActions() {
		Set<Action> actions = puzzle.actions();
		assertEquals(4, actions.size());
		assertTrue(actions.contains(Action.LEFT));
		assertTrue(actions.contains(Action.RIGHT));
		assertTrue(actions.contains(Action.UP));
		assertTrue(actions.contains(Action.DOWN));

		puzzle = new EightPuzzle(this._012345678);
		actions = puzzle.actions();
		assertEquals(2, actions.size());
		assertTrue(actions.contains(Action.DOWN));
		assertTrue(actions.contains(Action.RIGHT));

		puzzle = new EightPuzzle(this._102345678);
		actions = puzzle.actions();
		assertEquals(3, actions.size());
		assertTrue(actions.contains(Action.LEFT));
		assertTrue(actions.contains(Action.DOWN));
		assertTrue(actions.contains(Action.RIGHT));

		puzzle = new EightPuzzle(this._120345678);
		actions = puzzle.actions();
		assertEquals(2, actions.size());
		assertTrue(actions.contains(Action.LEFT));
		assertTrue(actions.contains(Action.DOWN));

		puzzle = new EightPuzzle(this._123045678);
		actions = puzzle.actions();
		assertEquals(3, actions.size());
		assertTrue(actions.contains(Action.UP));
		assertTrue(actions.contains(Action.DOWN));
		assertTrue(actions.contains(Action.RIGHT));

		puzzle = new EightPuzzle(this._123405678);
		actions = puzzle.actions();
		assertEquals(4, actions.size());
		assertTrue(actions.contains(Action.UP));
		assertTrue(actions.contains(Action.DOWN));
		assertTrue(actions.contains(Action.RIGHT));
		assertTrue(actions.contains(Action.LEFT));

		puzzle = new EightPuzzle(this._123450678);
		actions = puzzle.actions();
		assertEquals(3, actions.size());
		assertTrue(actions.contains(Action.UP));
		assertTrue(actions.contains(Action.DOWN));
		assertTrue(actions.contains(Action.LEFT));

		puzzle = new EightPuzzle(this._123456078);
		actions = puzzle.actions();
		assertEquals(2, actions.size());
		assertTrue(actions.contains(Action.UP));
		assertTrue(actions.contains(Action.RIGHT));

		puzzle = new EightPuzzle(this._123456708);
		actions = puzzle.actions();
		assertEquals(3, actions.size());
		assertTrue(actions.contains(Action.UP));
		assertTrue(actions.contains(Action.LEFT));
		assertTrue(actions.contains(Action.RIGHT));

		puzzle = new EightPuzzle(this._123456780);
		actions = puzzle.actions();
		assertEquals(2, actions.size());
		assertTrue(actions.contains(Action.UP));
		assertTrue(actions.contains(Action.LEFT));
	}

	@Test
	public void testGetState() {
		fail("Not yet implemented");
	}

	@Test
	public void testSuccessor() {
		Puzzle childPuzzle = puzzle.successor(Action.LEFT);
		assertEquals("1,2,3,4,0,5,6,7,8", puzzle.toString());
		assertEquals("1,2,3,0,4,5,6,7,8", childPuzzle.toString());

		childPuzzle = puzzle.successor(Action.RIGHT);
		assertEquals("1,2,3,4,0,5,6,7,8", puzzle.toString());
		assertEquals("1,2,3,4,5,0,6,7,8", childPuzzle.toString());

		childPuzzle = puzzle.successor(Action.UP);
		assertEquals("1,2,3,4,0,5,6,7,8", puzzle.toString());
		assertEquals("1,0,3,4,2,5,6,7,8", childPuzzle.toString());

		childPuzzle = puzzle.successor(Action.DOWN);
		assertEquals("1,2,3,4,0,5,6,7,8", puzzle.toString());
		assertEquals("1,2,3,4,7,5,6,0,8", childPuzzle.toString());

		// test down in 1st column
		puzzle = new EightPuzzle("0,1,2,3,4,5,6,7,8");
		childPuzzle = puzzle.successor(Action.DOWN);
		assertEquals("0,1,2,3,4,5,6,7,8", puzzle.toString());
		assertEquals("3,1,2,0,4,5,6,7,8", childPuzzle.toString());

		childPuzzle = childPuzzle.successor(Action.DOWN);
		assertEquals("3,1,2,6,4,5,0,7,8", childPuzzle.toString());

		try {
			childPuzzle = childPuzzle.successor(Action.DOWN);
			assertEquals("3,1,2,6,4,5,0,7,8", childPuzzle.toString());
			fail("Runtime Exception not thrown!");
		} catch (RuntimeException e) {
		}

		// test down in 3rd column
		puzzle = new EightPuzzle("1,2,0,3,4,5,6,7,8");
		childPuzzle = puzzle.successor(Action.DOWN);
		assertEquals("1,2,0,3,4,5,6,7,8", puzzle.toString());
		assertEquals("1,2,5,3,4,0,6,7,8", childPuzzle.toString());

		childPuzzle = childPuzzle.successor(Action.DOWN);
		assertEquals("1,2,5,3,4,8,6,7,0", childPuzzle.toString());

		try {
			childPuzzle = childPuzzle.successor(Action.DOWN);
			assertEquals("1,2,5,3,4,8,6,7,0", childPuzzle.toString());
			fail("Runtime Exception not thrown!");
		} catch (RuntimeException e) {
		}

		// test UP in 3rd column
		puzzle = new EightPuzzle("1,2,0,3,4,5,6,7,8");
		assertEquals(2, puzzle.actions().size());
		assertTrue(puzzle.actions().contains(Action.LEFT));
		assertTrue(puzzle.actions().contains(Action.DOWN));
		try {
			childPuzzle = puzzle.successor(Action.UP);
			fail("Runtime exception not thrown!");
		} catch (RuntimeException e) {
		}
		assertEquals("1,2,0,3,4,5,6,7,8", puzzle.toString());
		// System.out.println(childPuzzle.toString());
		// assertEquals("1,2,0,3,4,5,6,7,8", childPuzzle.toString());

		// test RIGHT FROM 3rd column
		puzzle = new EightPuzzle("1,2,0,3,4,5,6,7,8");
		assertEquals(2, puzzle.actions().size());
		assertTrue(puzzle.actions().contains(Action.LEFT));
		assertTrue(puzzle.actions().contains(Action.DOWN));
		try {
			childPuzzle = puzzle.successor(Action.RIGHT);
			fail("Runtime exception not thrown!");
		} catch (RuntimeException e) {
		}
		assertEquals("1,2,0,3,4,5,6,7,8", puzzle.toString());
		// System.out.println(childPuzzle.toString());

		// test LEFT FROM 1st column
		puzzle = new EightPuzzle("0,2,1,3,4,5,6,7,8");
		assertEquals(2, puzzle.actions().size());
		assertTrue(puzzle.actions().contains(Action.RIGHT));
		assertTrue(puzzle.actions().contains(Action.DOWN));
		try {
			childPuzzle = puzzle.successor(Action.LEFT);
			fail("Runtime exception not thrown!");
		} catch (RuntimeException e) {
		}
		assertEquals("0,2,1,3,4,5,6,7,8", puzzle.toString());

		// test LEFT FROM 1st column
		puzzle = new EightPuzzle(this._012345678);
		try {
			puzzle.successor(Action.LEFT);
			fail("Runtime exception not thrown!");
		} catch (RuntimeException e) {
		}

		try {
			childPuzzle = puzzle.successor(Action.UP);
			fail("Runtime exception not thrown!");
		} catch (RuntimeException e) {
		}

		childPuzzle = puzzle.successor(Action.DOWN);
		assertEquals("3,1,2,0,4,5,6,7,8", childPuzzle.toString());
		childPuzzle = puzzle.successor(Action.RIGHT);
		assertEquals("1,0,2,3,4,5,6,7,8", childPuzzle.toString());

		// test FROM [R0,C1] 1st column
		puzzle = new EightPuzzle(this._102345678);
		try {
			puzzle.successor(Action.UP);
			fail("Runtime exception not thrown!");
		} catch (RuntimeException e) {
		}

		childPuzzle = puzzle.successor(Action.DOWN);
		assertEquals("1,4,2,3,0,5,6,7,8", childPuzzle.toString());
		childPuzzle = puzzle.successor(Action.RIGHT);
		assertEquals("1,2,0,3,4,5,6,7,8", childPuzzle.toString());
		childPuzzle = puzzle.successor(Action.LEFT);
		assertEquals("0,1,2,3,4,5,6,7,8", childPuzzle.toString());

		// test FROM [R0,C2] 1st column
		puzzle = new EightPuzzle(this._120345678);
		try {
			puzzle.successor(Action.UP);
			fail("Runtime exception not thrown!");
		} catch (RuntimeException e) {
		}		
		try {
			puzzle.successor(Action.RIGHT);
			fail("Runtime exception not thrown!");
		} catch (RuntimeException e) {
		}

		childPuzzle = puzzle.successor(Action.DOWN);
		assertEquals("1,2,5,3,4,0,6,7,8", childPuzzle.toString());
		childPuzzle = puzzle.successor(Action.LEFT);
		assertEquals("1,0,2,3,4,5,6,7,8", childPuzzle.toString());

	}

}
