package edu.nmsu.cs.circles;

/***
 * Example JUnit testing class for Circle1 (and Circle)
 *
 * - must have your classpath set to include the JUnit jarfiles - to run the test do: java
 * org.junit.runner.JUnitCore Circle1Test - note that the commented out main is another way to run
 * tests - note that normally you would not have print statements in a JUnit testing class; they are
 * here just so you see what is happening. You should not have them in your test cases.
 ***/

import org.junit.*;

public class Circle1Test
{
	// Data you need for each test case
	private Circle1 circle1;

	//
	// Stuff you want to do before each test case
	//
	@Before
	public void setup()
	{
		System.out.println("\nTest starting...");
		circle1 = new Circle1(1, 2, 3);
	}

	//
	// Stuff you want to do after each test case
	//
	@After
	public void teardown()
	{
		System.out.println("\nTest finished.");
	}

	public void intersectNoIntersection() {
		System.out.println("Testing intersectNoIntersection: ");

		Circle1 firstCircle = new Circle1(0, 50, 10);
		Circle1 secondCircle = new Circle1(0, 0, 5);
		Assert.assertFalse(firstCircle.intersects(secondCircle));
		Assert.assertFalse(secondCircle.intersects(firstCircle));

		firstCircle = new Circle1(1, 10, 2.99);
		secondCircle = new Circle1(1, 5, 2);
		Assert.assertFalse(firstCircle.intersects(secondCircle));
		Assert.assertFalse(secondCircle.intersects(firstCircle));
	}

	public void intersectCompleteOverlap() {
		System.out.println("Testing intersectCompleteOverlap: ");

		Circle1 firstCircle = new Circle1(0, 0, 5);
		Circle1 secondCircle = new Circle1(0, 0, 5);
		Assert.assertTrue(firstCircle.intersects(secondCircle));
		Assert.assertTrue(secondCircle.intersects(firstCircle));

	}

	public void intersectAtOnePoint() {
		System.out.println("Testing intersectCompleteOverlap: ");

		Circle1 firstCircle = new Circle1(0, 0, 3);
		Circle1 secondCircle = new Circle1(7, 0, 4);
		Assert.assertTrue(firstCircle.intersects(secondCircle));
		Assert.assertTrue(secondCircle.intersects(firstCircle));

	}

	public void intersectMultiplePoints() {
		System.out.println("Running test: intersectMultiplePoints");
		
		Circle1 circleA = new Circle1(0,0,10); 
		Circle1 circleB = new Circle1(10,0,10);
		Assert.assertTrue(circleA.intersects(circleB));
		Assert.assertTrue(circleB.intersects(circleA));
		
		circleA = new Circle1(0,0,10); 
		circleB = new Circle1(0,10,10);
		Assert.assertTrue(circleA.intersects(circleB));
		Assert.assertTrue(circleB.intersects(circleA));
	}
	
	public void intersectSmallerCircleInside() {
		System.out.println("Running test: smallerCircleInside()");
		
		Circle1 circleA = new Circle1(0,0,10); 
		Circle1 circleB = new Circle1(0,0,5);
		Assert.assertTrue(circleA.intersects(circleB));
		Assert.assertTrue(circleB.intersects(circleA));
	}

	public void simpleMove()
	{
		Point p;
		System.out.println("Running test simpleMove.");
		p = circle1.moveBy(1, 1);
		Assert.assertTrue(p.x == 2 && p.y == 3);
	}

	//
	// Test a simple negative move
	//
	@Test
	public void simpleMoveNeg()
	{
		Point p;
		System.out.println("Running test simpleMoveNeg.");
		p = circle1.moveBy(-1, -1);
		Assert.assertTrue(p.x == 0 && p.y == 1);
	}

	/***
	 * NOT USED public static void main(String args[]) { try { org.junit.runner.JUnitCore.runClasses(
	 * java.lang.Class.forName("Circle1Test")); } catch (Exception e) { System.out.println("Exception:
	 * " + e); } }
	 ***/

}
