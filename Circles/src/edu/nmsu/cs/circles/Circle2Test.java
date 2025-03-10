package edu.nmsu.cs.circles;

/***
 * Example JUnit testing class for Circle2 (and Circle)
 *
 * - must have your classpath set to include the JUnit jarfiles - to run the test do: java
 * org.junit.runner.JUnitCore Circle1Test - note that the commented out main is another way to run
 * tests - note that normally you would not have print statements in a JUnit testing class; they are
 * here just so you see what is happening. You should not have them in your test cases.
 ***/

import org.junit.*;

public class Circle2Test
{
	// Data you need for each test case
	private Circle2 circle2;

	//
	// Stuff you want to do before each test case
	//
	@Before
	public void setup()
	{
		System.out.println("\nTest starting...");
		circle2 = new Circle2(1, 2, 3);
	}

	//
	// Stuff you want to do after each test case
	//
	@After
	public void teardown()
	{
		System.out.println("\nTest finished.");
	}

	//
	// Test a simple positive move
	//
	@Test
	public void simpleMove()
	{
		Point p;
		System.out.println("Running test simpleMove.");
		p = circle2.moveBy(1, 1);
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
		p = circle2.moveBy(-1, -1);
		Assert.assertTrue(p.x == 0 && p.y == 1);
	}
	
	public void noIntersection() {
		System.out.println("Running test: noIntersection");
		
		Circle2 circleA = new Circle2(0,50,10); 
		Circle2 circleB = new Circle2(0,0,5);
		Assert.assertFalse(circleA.intersects(circleB));
		Assert.assertFalse(circleB.intersects(circleA));
		
		circleA = new Circle2(1,10,2.9); 
		circleB = new Circle2(1,5,2);
		Assert.assertFalse(circleA.intersects(circleB));
		Assert.assertFalse(circleB.intersects(circleA));
	}
	
	public void intersectCompleteOverlap() {
		System.out.println("Running test: intersectCompleteOverlap");
		
		Circle2 circleA = new Circle2(0,0,10); 
		Circle2 circleB = new Circle2(0,0,10);
		Assert.assertTrue(circleA.intersects(circleB));
		Assert.assertTrue(circleB.intersects(circleA));
	}
	
	public void intersectOnePoint() {
		System.out.println("Running test: intersectOnePoint");
		
		System.out.println("onePointOverlap -- touch on x-axis");
		Circle2 circleA = new Circle2(0,0,10); 
		Circle2 circleB = new Circle2(20,0,10);
		Assert.assertTrue(circleA.intersects(circleB));
		Assert.assertTrue(circleB.intersects(circleA));
		
		System.out.println("onePointOverlap -- touch on y-axis");
		circleA = new Circle2(0,0,10); 
		circleB = new Circle2(0,20,10);
		Assert.assertTrue(circleA.intersects(circleB));
		Assert.assertTrue(circleB.intersects(circleA));
	}
	
	public void intersectMultiplePoints() {
		System.out.println("Running test: intersectMultiplePoints");
		
		Circle2 circleA = new Circle2(0,0,10); 
		Circle2 circleB = new Circle2(10,0,10);
		Assert.assertTrue(circleA.intersects(circleB));
		Assert.assertTrue(circleB.intersects(circleA));
		
		circleA = new Circle2(0,0,10); 
		circleB = new Circle2(0,10,10);
		Assert.assertTrue(circleA.intersects(circleB));
		Assert.assertTrue(circleB.intersects(circleA));
	}
	
	public void intersectSmallerCircleInside() {
		System.out.println("Running test: intersectSmallerCircleInside()");
		
		Circle2 circleA = new Circle2(0,0,10); 
		Circle2 circleB = new Circle2(0,0,5);
		Assert.assertTrue(circleA.intersects(circleB));
		Assert.assertTrue(circleB.intersects(circleA));
	}
	
	
	
	
	

	/***
	 * NOT USED public static void main(String args[]) { try { org.junit.runner.JUnitCore.runClasses(
	 * java.lang.Class.forName("Circle1Test")); } catch (Exception e) { System.out.println("Exception:
	 * " + e); } }
	 ***/

}