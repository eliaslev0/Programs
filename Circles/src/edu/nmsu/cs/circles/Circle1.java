package edu.nmsu.cs.circles;

public class Circle1 extends Circle
{

	public Circle1(double x, double y, double radius)
	{
		super(x, y, radius);
	}

	public boolean oldIntersects(Circle other)
	{
		if (Math.abs(center.x - other.center.x) < radius &&
				Math.abs(center.y - other.center.y) < radius)
			return true;
		return false;
	}

	public boolean intersects(Circle other)
	{
		double distance = Math.sqrt(Math.pow((center.x - other.center.x), 2) + Math.pow((center.y - other.center.y), 2));
		if (distance <= (radius + other.radius))
			return true;
		return false;
	}

}
