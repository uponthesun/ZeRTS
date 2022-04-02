package mechanicsobjects;

public class PathNode {

	PathNode previous;
	int x, y;
	int number;
	
	double distanceTraveled;
	double hValue;
	static final double width = .2;
	
	public PathNode(int x, int y,  PathNode previous, double distanceTraveled){
		this.x = x;
		this.y = y;
		this.previous = previous;
		this.distanceTraveled = distanceTraveled;
	}
	
}
