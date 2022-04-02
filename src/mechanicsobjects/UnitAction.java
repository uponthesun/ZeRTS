package mechanicsobjects;

import gamemechanics.*;

import ingameobjects.Thing;
import ingameobjects.Unit;

import java.util.ArrayList;

import zerts.PointD;


public abstract class UnitAction {

	public String type;
	public Unit unit;
	public ArrayList path;
	public double targetX, targetY;
	public Thing targetThing;
	
	public UnitAction(String t, Unit u, Thing th){
		type = t;
		unit = u;
		targetThing = th;
		initialize();
	}

	public UnitAction(String t, Unit u, double tx, double ty){
		type = t;
		unit = u;
		targetX = tx;
		targetY = ty;
		initialize();
	}
	
	protected abstract void initialize();
	public abstract void doAction();
	
	public void terminate(){
		unit.currentAction = null;
		if(unit.currentAnimation != null)
			unit.currentAnimation.end = true;			
	}
	
	public ArrayList findPath(double startX, double startY, double goalX, double goalY){
		//System.out.println(startX + " " + startY + " " + goalX+ " " + goalY);
		double startTime = System.currentTimeMillis();

		int relativeGoalX = (int)Math.round(  (goalX - startX)/PathNode.width );
		int relativeGoalY = (int)Math.round(  (goalY - startY)/PathNode.width );
		//System.out.println(relativeGoalX + " " + relativeGoalY);
		GameInstance env = unit.controller.game;
		
//		if(!env.unitCanBeAt(unit, startX + PathNode.width*relativeGoalX, startY + PathNode.width*relativeGoalY)){
//			//System.out.println("can't go there");
//			return null;
//		}
		
		double rt2 = Math.sqrt(2);
		ArrayList pqueue = new ArrayList();		
		PathNode startNode = new PathNode(0, 0, null, 0);
		PathNode endNode = null;
		PathNode nearest = null;
		double shortestDistance = Engine.distance(0, 0, relativeGoalX, relativeGoalY);
		startNode.hValue = shortestDistance;
		addToPathQueue(startNode, pqueue);
		int iterations = 0;
		//System.out.println(startX + " " + startY + " " + goalX+ " " + goalY);
		
		int maxDepth = 500;
		int maxIterations = 5000;
		
		boolean visited[][] = new boolean[maxDepth*2 + 1][maxDepth*2 + 1];		
		
		while(pqueue.size() > 0){
			PathNode currentNode = (PathNode)pqueue.remove(0);
		//	System.out.println(currentNode.x + " " + currentNode.y + ", "+ relativeGoalX+ " " + relativeGoalY + " h: " + currentNode.hValue);
			long sTime1 = System.nanoTime();
			if(currentNode.x == relativeGoalX && currentNode.y == relativeGoalY){
				endNode = currentNode;
				break;
			}
			if(iterations > maxIterations){
				endNode = nearest;
				break;
			}
			int arrayX = currentNode.x + maxDepth;
			int arrayY = currentNode.y + maxDepth;
			double distance = Engine.distance(currentNode.x, currentNode.y, relativeGoalX, relativeGoalY);
			if(distance < shortestDistance){
				shortestDistance = distance;
				nearest = currentNode;
			}
			
			if( !(arrayX < 0 || arrayX >= visited.length || arrayY < 0 || arrayY >= visited[0].length) )
				if(!visited[arrayX][arrayY])
					if(env.unitCanBeAt(unit, startX + PathNode.width*currentNode.x, startY + PathNode.width*currentNode.y)){					
						for(int dx = -1; dx < 2; dx++){
							for(int dy = -1; dy < 2; dy++){	
								int newArrayX = arrayX + dx;
								int newArrayY = arrayY + dy;
								if( !(newArrayX < 0 || newArrayX >= visited.length || newArrayY < 0 || newArrayY >= visited[0].length) )
									if(!visited[newArrayX][newArrayY]){
										int ex = dx + currentNode.x;
										int why = dy + currentNode.y;
										double distanceIncrement = 1;
										if( dx != 0 && dy != 0)
											distanceIncrement = rt2;											
										PathNode pn = new PathNode(ex, why, currentNode, currentNode.distanceTraveled + distanceIncrement);
										pn.hValue = pn.distanceTraveled + Engine.distance(pn.x, pn.y, relativeGoalX, relativeGoalY);
										pn.number =  iterations;
										visited[arrayX][arrayY] = true;
										addToPathQueue(pn, pqueue);				
									}
							}
						}
					}
			iterations++;
		//	System.out.println("current: " + currentNode.x + " " + currentNode.y + ", "+ relativeGoalX+ " " + relativeGoalY + " h: " + currentNode.hValue + " distance: " + currentNode.distanceTraveled + " iteration: " + iterations);
			
		}  
	//	if(endNode != null){
		if(endNode == null)
			endNode = nearest;
	
		ArrayList path = new ArrayList();
		PathNode currentNode = endNode;
		while(currentNode != null){
			PointD temp = new PointD(currentNode.x*PathNode.width + startX, currentNode.y*PathNode.width + startY);
			path.add(0, temp);
			//System.out.println("Path: " + temp.x + ", " + temp.y);
			currentNode = currentNode.previous;
		}
//		System.out.println("Path Distance: " + path.size() + " Elapsed Time: " + (System.currentTimeMillis()-startTime) + " Iterations: " + iterations);

		return path;

	}	
	
	
	private static void addToPathQueue(PathNode node, ArrayList pqueue){
		double h = node.hValue;
		for(int n = 0; n < pqueue.size(); n++){
			PathNode current = (PathNode)pqueue.get(n);
			if( h < current.hValue ){
				pqueue.add(n, node);
				return;
			}
		}
		pqueue.add(node);
	}
	
	public void setPath(ArrayList path){
		this.path = path;
	}
	
}
