package libraries;


import gamemechanics.*;
import ingameobjects.Thing;
import ingameobjects.Unit;

import java.awt.Point;
import java.util.ArrayList;

import mechanicsobjects.UnitAction;

import zerts.PointD;

public class ActionLibrary {

	public static UnitAction moveAction(Unit u, double x, double y){
		return new UnitAction("move", u, x, y){
			
			int pathIndex = 1;
			int stallTime = 0;
			int stallThreshold = 50;
			int numAttempts = 0;
			int maxAttempts = 10;

			
			public void initialize(){
				unit.manualControl = false;
				new Thread(){
					public void run(){
						ArrayList path = findPath(unit.x, unit.y, targetX, targetY);
						setPath(path);			
						unit.currentAnimation = AnimationLibrary.moveAnimation(unit);
					}
				}.start();
				
			}

			private void isStuck(){
				double maxDistance = 1;
				double distance = Engine.realDistance(unit.x , unit.y, targetX, targetY);
				if(distance < maxDistance){
//					System.out.println("end 3 ");
//					System.out.println(unit.x + ", " + unit.y + "     " + targetX + ", " +targetY);
					terminate();
					return;
				}						
				numAttempts++;
				if(numAttempts > maxAttempts){
//					System.out.println("end 4");
					terminate();
					return;
				}
				ArrayList path = findPath(unit.x, unit.y, targetX, targetY);
				setPath(path);
				stallTime = 0;
			}
			
			
			public void doAction(){
				if(path == null){
					stallTime++;
					if(stallTime >= stallThreshold){
						isStuck();
					}
					return;
				}				
				if(pathIndex>=path.size()){
/*					PointD p = (PointD)path.get(path.size()-1);
/*					if(Engine.approxEq(p.x, unit.x) && Engine.approxEq(p.y, unit.y)){
//						System.out.println("end 2");
						terminate();
						return;
					}

					double maxDistance = 1;
					double distance = Engine.realDistance(unit.x , unit.y, targetX, targetY);
					if(distance < maxDistance){
//						System.out.println("end 3 ");
//						System.out.println(unit.x + ", " + unit.y + "     " + targetX + ", " +targetY);
						terminate();
						return;
					}	
					
					unit.currentAction = ActionLibrary.moveAction(unit, targetX, targetY);
*/					isStuck();
					return;
				}
				PointD localGoal = (PointD)path.get(pathIndex);
				unit.stepTowards(localGoal.x, localGoal.y);
				stallTime++;
				if( Engine.approxEq(unit.x, localGoal.x) && Engine.approxEq(unit.y, localGoal.y)){
					pathIndex++;
					stallTime = 0;
				}
				if(stallTime > stallThreshold)
					isStuck();
					
			}
		};
	}

	
	public static UnitAction attackTargetAction(Unit u, Thing t){
		return new UnitAction("attack target", u, t){
			public void initialize(){
				
			}
			public void doAction(){
				
			}
		};
	}
	
/*	public static UnitAction attackAction(Unit u, Thing target){
		return new UnitAction("attack", u, target){

			int pathIndex = 1;
			int stallTime = 0;

			public void initialize(){
			}
			public void doAction(){
				try{
				
				}
				catch(Exception e){
					System.out.println("bad stuff");
				}
			}
		};
	}
	*/
}
